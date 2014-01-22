package seremis.soulcraft.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seremis.soulcraft.soul.IEntitySoulCustom;
import seremis.soulcraft.soul.SoulHandler;

public class EntitySoulCustom extends EntityLiving implements IEntitySoulCustom {

    public EntitySoulCustom(World world) {
        super(world);
    }
    
    public EntitySoulCustom(World world, double x, double y, double z) {
        super(world);
        setPosition(x, y, z);
        SoulHandler.entityInit(this);
    }

    //Modularity stuff//
    
    public ItemStack drops;
    public ItemStack rareDrops;
    
    @Override
    public double getPosX() {
        return posX;
    }
    
    @Override
    public double getPosY() {
        return posY;
    }
    
    @Override
    public double getPosZ() {
        return posZ;
    }
    
    @Override
    public float getRotationYaw() {
        return rotationYaw;
    }
    
    @Override
    public void setRotationYaw(float yaw) {
        rotationYaw = yaw;
    }
    
    @Override
    public float getRotationPitch() {
        return rotationPitch;
    }
    
    @Override
    public void setRotationPitch(float pitch) {
        rotationPitch = pitch;
    }
    
    @Override
    public World getWorld() {
        return worldObj;
    }
    
    @Override
    public float getBrightness() {
        return getBrightness(1.0F);
    }
    
    @Override
    public ItemStack getArmor(int slot) {
        return getCurrentItemOrArmor(slot+1);
    }
    
    @Override
    public void setArmor(int slot, ItemStack stack) {
        this.setCurrentItemOrArmor(slot+1, stack);
    }
    
    @Override
    public ItemStack getHeldItem() {
        return getCurrentItemOrArmor(0);
    }
    
    @Override
    public void setHeldItem(ItemStack stack) {
        setCurrentItemOrArmor(0, stack);
    }
    
    @Override
    public boolean getCanPickUpItems() {
        return canPickUpLoot();
    }
    
    @Override
    public void setCanPickUpItems(boolean canPickUp) {
        setCanPickUpLoot(canPickUp);
    }
    
    @Override
    public void dropItems(ItemStack stack) {
        this.entityDropItem(stack, getEyeHeight());
    }
    
    //Entity stuff//
//    @Override
//    protected void applyEntityAttributes() {
//      getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(maxHealth);
//      getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(32.0D);
//      getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setAttribute(0.0D);
//      getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.699D);
//      getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(2.0D);
//    }
    
    @Override
    public boolean interact(EntityPlayer player) {
        SoulHandler.entityRightClicked(this, player);
        return super.interact(player);
    }
    
    @Override
    public void onUpdate() {
        SoulHandler.entityUpdate(this);
        super.onUpdate();
    }
    
    @Override
    public void dropFewItems(boolean recentlyHit, int lootingLevel) {
        SoulHandler.entityDropItems(this, recentlyHit, lootingLevel);
    }
}
