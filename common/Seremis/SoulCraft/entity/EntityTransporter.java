package Seremis.SoulCraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityTransporter extends Entity {

    protected double yaw;
    protected double pitch;
    @SideOnly(Side.CLIENT)
    protected double velocityX;
    @SideOnly(Side.CLIENT)
    protected double velocityY;
    @SideOnly(Side.CLIENT)
    protected double velocityZ;

    public boolean isOpen = true;
    public int openPhase = 0;
    public ItemStack[] inv = new ItemStack[4];

    public EntityTransporter(World par1World) {
        super(par1World);
        setSize(1.5F, 0.6F);
        isImmuneToFire = true;
    }

    public EntityTransporter(World world, double x, double y, double z) {
        super(world);
        setPosition(x, y + 0.5F, z);
        motionX = 0.0D;
        motionY = 1.0D;
        motionZ = 0.0D;
    }

    @Override
    protected void entityInit() {

    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entity) {
        return entity.boundingBox;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return boundingBox;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canTriggerWalking() {
        return false;
    }

    // @Override
    // @SideOnly(Side.CLIENT)
    // public String getTexture() {
    // return Localizations.LOC_MODEL_TEXTURES +
    // Localizations.ENTITY_TRANSPORTER;
    // }

    /**
     * (entity.interact)
     */
    @Override
    public boolean func_130002_c(EntityPlayer player) {
        if(isOpen) {
            for(int i = 0; i < inv.length; i++) {
                if(inv[i] == null || inv[i].stackSize == 0) {
                    isOpen = false;
                    return true;
                }
            }
        } else {
            isOpen = true;
            return true;
        }
        return false;
    }

    @Override
    public void onUpdate() {
        extinguish();
    }

    // @Override
    // public boolean attackEntityFrom(DamageSource source, int i) {
    // this.kill();
    // return true;
    // }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        // isOpen = compound.getBoolean("isOpen");
        //
        // NBTTagList var2 = compound.getTagList("Items");
        // this.inv = new ItemStack[this.getSizeInventory()];
        //
        // for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
        // NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
        // int var5 = var4.getByte("Slot") & 255;
        //
        // if (var5 >= 0 && var5 < this.inv.length) {
        // this.inv[var5] = ItemStack.loadItemStackFromNBT(var4);
        // }
        // }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        // compound.setBoolean("isOpen", isOpen);
        //
        // NBTTagList var2 = new NBTTagList();
        //
        // for (int var3 = 0; var3 < this.inv.length; ++var3) {
        // if (this.inv[var3] != null) {
        // NBTTagCompound var4 = new NBTTagCompound();
        // var4.setByte("Slot", (byte) var3);
        // this.inv[var3].writeToNBT(var4);
        // var2.appendTag(var4);
        // }
        // }
        // compound.setTag("Items", var2);
    }
}
