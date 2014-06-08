package seremis.soulcraft.entity;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import org.apache.logging.log4j.Level;

import seremis.soulcraft.SoulCraft;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.entity.logic.EntityTransporterLogic;
import seremis.soulcraft.tileentity.TileStationController;
import seremis.soulcraft.util.UtilBlock;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityTransporter extends SCEntity implements IEntityAdditionalSpawnData {

    private boolean hasEngine = false;
    private boolean hasInventory = false;
    private float speed = 1.0F;
    private int heat = 0;

    private ItemStack[] inv = new ItemStack[9];

    private EntityTransporterLogic logic;

    public EntityTransporter(World world) {
        super(world);
        isImmuneToFire = true;
        this.noClip = true;
    }

    public EntityTransporter(World world, double x, double y, double z, EntityTransporterLogic logic) {
        this(world);
        setPosition(x + 0.5F, y + 0.5F, z + 0.5F);
        this.logic = logic;
        if(logic.getTurnPoints().size() < 1) {
            this.setDead();
        }
    }

    @Override
    protected void entityInit() {}

    public void setInventory(ItemStack[] inv) {
        this.inv = inv;
    }

    public ItemStack[] getInventory() {
        return inv;
    }

    public float getYaw() {
        return rotationYaw;
    }

    public void setYaw(float yaw) {
        this.rotationYaw = yaw;
    }

    public float getPitch() {
        return rotationPitch;
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entity) {
        return null;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canTriggerWalking() {
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(CommonProxy.instance.isServerWorld(worldObj)) {
            if(!logic.hasEntity) {
                logic.init(this);
            }
            logic.update();
        } else {
            if(logic != null) {
                logic.update();
            }
        }
    }

    public void arrive() {
        TileEntity tile = worldObj.getTileEntity((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ));

        if(tile != null && tile instanceof TileStationController && ((TileStationController)tile).isMultiblock) {
            ((TileStationController) tile).handleIncoming(this);
        } else {
            for(ItemStack stack : inv) {
                UtilBlock.dropItemInWorld((int) posX, (int) posY, (int) posZ, worldObj, stack);
            }
        }
        setDead();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        this.inv = new ItemStack[9];

        for(int var3 = 0; var3 < list.tagCount(); ++var3) {
            NBTTagCompound compound1 = list.getCompoundTagAt(var3);
            int slotNr = compound1.getByte("Slot") & 255;

            if(slotNr >= 0 && slotNr < this.inv.length) {
                this.inv[slotNr] = ItemStack.loadItemStackFromNBT(compound1);
            }
        }

        logic = new EntityTransporterLogic();
        logic.readFromNBT(compound);

        if(logic.getTurnPoints() == null || logic.getTurnPoints().size() < 1)
            setDead();
        
        hasInventory = compound.getBoolean("hasInventory");
        hasEngine = compound.getBoolean("hasEngine");
        speed = compound.getFloat("speed");
        heat = compound.getInteger("heat");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();

        for(int i = 0; i < this.inv.length; ++i) {
            if(this.inv[i] != null) {
                NBTTagCompound compound1 = new NBTTagCompound();

                compound1.setByte("Slot", (byte) i);

                this.inv[i].writeToNBT(compound1);

                list.appendTag(compound1);
            }
        }
        compound.setTag("Items", list);

        logic.writeToNBT(compound);

        compound.setBoolean("hasEngine", hasEngine);
        compound.setBoolean("hasInventory", hasInventory);
        compound.setFloat("speed", speed);
        compound.setInteger("heat", heat);
    }

    public void setHasInventory(boolean inventory) {
        this.hasInventory = inventory;
    }

    public void setHasEngine(boolean engine) {
        if(engine) {
            speed = 5.0F;
        } else {
            speed = 1.0F;
        }
        this.hasEngine = engine;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean hasEngine() {
        return hasEngine;
    }

    public boolean hasInventory() {
        return hasInventory;
    }
    
    public void setHeat(int heat) {
        this.sendEntityDataToClient(3, ByteBuffer.allocate(4).putInt(heat).array());
        this.heat = heat;
    }
    
    public int getHeat() {
        return heat;
    }

    @Override
    public void writeSpawnData(ByteBuf data) {
        data.writeBoolean(hasEngine);
        data.writeBoolean(hasInventory);
        data.writeFloat(speed);
        data.writeInt(heat);
    }

    @Override
    public void readSpawnData(ByteBuf data) {
        hasEngine = data.readBoolean();
        hasInventory = data.readBoolean();
        speed = data.readFloat();
        heat = data.readInt();
    }

    @Override
    public void receivePacketOnClient(int id, byte[] data) {
        if(id == 1) {
            SoulCraft.logger.log(Level.INFO, "Received transporter data on client!");
            logic = new EntityTransporterLogic();
            logic.receiveClientLogic(data);
            logic.init(this);
        }
        if(id == 2) {
            if(logic != null) {
                logic.nextPoint();
            }
        }
        if(id == 3) {
            heat = ByteBuffer.wrap(data).getInt();
        }
    }
}
