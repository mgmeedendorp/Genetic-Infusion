package SoulCraft.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.Property;
import SoulCraft.soul.Soul;

public class EntityCustom extends EntityLiving {

	public Soul soul;
	public static Property property;
	
	//These values get set by properties.
	public boolean canDespawn;
	public boolean canBeSteered;
	public boolean canBreatheUnderwater;
	public int maxHealth;
	public int maxSpeed;
	
	public EntityCustom(World world) {
		super(world);
	}
	
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
    }
	
	 public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	 {
		 super.writeEntityToNBT(par1NBTTagCompound);
		 par1NBTTagCompound.setTag("Soul", new NBTTagList());
	 }
	
	@Override
	public boolean canDespawn() {
		return canDespawn;
	}

	@Override
	public int getMaxHealth() {
		return maxHealth;
	}
	
	@Override
	public boolean canBeSteered() {
		return canBeSteered;
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return canBreatheUnderwater;
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
	}
	
	
}
