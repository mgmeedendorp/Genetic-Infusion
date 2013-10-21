package Seremis.SoulCraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet30Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.tileentity.TileStationController;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityTransporter extends SCEntity implements IEntityAdditionalSpawnData {
    
    private boolean hasEngine = false;
    private boolean hasInventory = false;
    private float speed = 1.0F;

    private ItemStack[] inv = new ItemStack[9];
    
    private EntityTransporterLogic logic;

    public EntityTransporter(World world) {
        super(world);
        setSize(1F, 0.6F);
        isImmuneToFire = true;
        this.noClip = true;
    }

    public EntityTransporter(World world, double x, double y, double z, EntityTransporterLogic logic) {
        this(world);
        setPosition(x, y, z);
        
        this.logic = logic;        
        logic.init(this);
        System.out.println(logic);
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
        return this.boundingBox;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canTriggerWalking() {
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(CommonProxy.proxy.isServerWorld(worldObj)) {
            if(!logic.hasEntity) {
                logic.init(this);
            }
         //  setDead();
            logic.update();
         //   System.out.println(new Coordinate3D(this).toString());
        }
    }
 
//    TODO decide if this has to be overridden
//    @Override
//    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
//        
//    }
    
    public void arrive() {
        System.out.println("arrival");
        TileEntity tile = worldObj.getBlockTileEntity((int)posX, (int)posY, (int)posZ);
        
        if(tile != null && tile instanceof TileStationController) {
            ((TileStationController)tile).handleIncoming(this);
        }
        setDead();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {        
        NBTTagList list = compound.getTagList("Items");
        this.inv = new ItemStack[9];
        
        for(int var3 = 0; var3 < list.tagCount(); ++var3) {
            NBTTagCompound compound1 = (NBTTagCompound) list.tagAt(var3);
            int slotNr = compound1.getByte("Slot") & 255;
            
            if(slotNr >= 0 && slotNr < this.inv.length) {
                this.inv[slotNr] = ItemStack.loadItemStackFromNBT(compound1);
            }
        }
        
        logic = new EntityTransporterLogic();
        logic.readFromNBT(compound);
        
        hasInventory = compound.getBoolean("hasInventory");
        hasEngine = compound.getBoolean("hasEngine");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {        
        NBTTagList list = new NBTTagList();
        
        for (int i = 0; i < this.inv.length; ++i) {
            if (this.inv[i] != null) {
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

    @Override
    public void writeSpawnData(ByteArrayDataOutput data) {
        data.writeBoolean(hasEngine);
        data.writeBoolean(hasInventory);
        data.writeDouble(rotationYaw);
    }

    @Override
    public void readSpawnData(ByteArrayDataInput data) {
        hasEngine = data.readBoolean();
        hasInventory = data.readBoolean();
        rotationYaw = data.readFloat();
    }
    
    @Override
    public void receivePacketOnClient(int id, byte[] data) {
        if(id == 0) {
            rotationYaw = (float)data[0]*(float)90F;
        }
    }
    
    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER);
    }
}
