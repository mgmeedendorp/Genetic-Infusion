package seremis.soulcraft.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import seremis.soulcraft.soul.SoulHandler;

public class EntitySoulCustom extends EntityLiving {

    public EntitySoulCustom(World world) {
        super(world);
    }
    
    public EntitySoulCustom(World world, double x, double y, double z) {
        super(world);
        setPosition(x, y, z);
    }

    //Modularity stuff//
    
    public float maxHealth;
    
    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }
    
    
    //Entity stuff//
    
    @Override
    public ItemStack getHeldItem() {
        return null;
    }

    @Override
    public ItemStack getCurrentItemOrArmor(int i) {
        return null;
    }

    @Override
    public void setCurrentItemOrArmor(int i, ItemStack itemstack) {
        
    }

    @Override
    public ItemStack[] getLastActiveItems() {
        return null;
    }
    
    @Override
    protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(maxHealth);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(32.0D);
      this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setAttribute(0.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.699D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(2.0D);
    }
    
    @Override
    public boolean interact(EntityPlayer player) {
        SoulHandler.entityRightClicked(this, player);
        return super.interact(player);
    }

}
