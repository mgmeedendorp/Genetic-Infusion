package seremis.soulcraft.api.soul.util;

import seremis.soulcraft.api.soul.IEntitySoulCustom;
import seremis.soulcraft.core.proxy.CommonProxy;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;

public class UtilSoulEntity {

    public static void extinguish(IEntitySoulCustom entity) {
        entity.setPersistentVariable("fire", 0);
    }
    
    public static ItemStack getEquipmentInSlot(IEntitySoulCustom entity, int slot) {
        return entity.getPersistentItemStack("equipment" + slot);
    }
    
    public static void setEquipmentInSlot(IEntitySoulCustom entity, int slot, ItemStack stack) {
        entity.setPersistentVariable("equipment" + slot, stack);
    }
    
    public static boolean handleLavaMovement(IEntitySoulCustom entity) {
        return entity.getWorld().isMaterialInBB(entity.getBoundingBox().expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.lava);
    }
    
    public static boolean isEntityInsideOpaqueBlock(IEntitySoulCustom entity)
    {
    	float width = entity.getFloat("width");
    	double posX = entity.getPersistentDouble("posX");
    	double posY = entity.getPersistentDouble("posY");
    	double posZ = entity.getPersistentDouble("posZ");
    	
        for (int i = 0; i < 8; ++i)
        {
            float f = ((float)((i >> 0) % 2) - 0.5F) * width * 0.8F;
            float f1 = ((float)((i >> 1) % 2) - 0.5F) * 0.1F;
            float f2 = ((float)((i >> 2) % 2) - 0.5F) * width * 0.8F;
            int x = MathHelper.floor_double(posX + (double)f);
            //int y = MathHelper.floor_double(posY + (double)this.getEyeHeight() + (double)f1);
            int y = MathHelper.floor_double(posY + (double)f1);
            int z = MathHelper.floor_double(posZ + (double)f2);

            if (entity.getWorld().getBlock(x, y, z).isNormalCube()) {
                return true;
            }
        }

        return false;
    }
    
    public static void travelToDimension(IEntitySoulCustom entity, int dimensionId) {
		boolean isDead = entity.getPersistentBoolean("isDead");
		
        if (CommonProxy.proxy.isServerWorld(entity.getWorld()) && !isDead) {
            entity.getWorld().theProfiler.startSection("changeDimension");
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            int currentDimension = entity.getPersistentInteger("dimension");
            WorldServer worldserver = minecraftserver.worldServerForDimension(currentDimension);
            WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionId);
            entity.setPersistentVariable("dimension", dimensionId);

            if (currentDimension == 1 && dimensionId == 1) {
                worldserver1 = minecraftserver.worldServerForDimension(0);
                entity.setPersistentVariable("dimension", 0);
            }

            if (entity.getWorld().getEntityByID(entity.getInteger("riddenByEntityID")) != null) {
            	entity.getWorld().getEntityByID(entity.getInteger("riddenByEntityID")).mountEntity((Entity)null);
            }

            if (entity.getWorld().getEntityByID(entity.getPersistentInteger("ridingEntityID")) != null) {
            	entity.getWorld().getEntityByID(entity.getEntityId()).mountEntity((Entity)null);
            }
            entity.getWorld().theProfiler.startSection("reposition");            
            minecraftserver.getConfigurationManager().transferEntityToWorld((Entity) entity, currentDimension, worldserver, worldserver1);
            entity.getWorld().theProfiler.endStartSection("reloading");
            Entity ent = EntityList.createEntityByName(EntityList.getEntityString((Entity) entity), worldserver1);
            
            if (ent != null) {
                ent.copyDataFrom((Entity) entity, true);

                if (currentDimension == 1 && dimensionId == 1)
                {
                    ChunkCoordinates chunkcoordinates = worldserver1.getSpawnPoint();
                    chunkcoordinates.posY = entity.getWorld().getTopSolidOrLiquidBlock(chunkcoordinates.posX, chunkcoordinates.posZ);
                    ent.setLocationAndAngles((double)chunkcoordinates.posX, (double)chunkcoordinates.posY, (double)chunkcoordinates.posZ, ent.rotationYaw, ent.rotationPitch);
                }

                worldserver1.spawnEntityInWorld(ent);
            }

            entity.setPersistentVariable("isDead", true);
            entity.getWorld().theProfiler.endSection();
            worldserver.resetUpdateEntityTick();
            worldserver1.resetUpdateEntityTick();
            entity.getWorld().theProfiler.endSection();
        }
    }
    
	public static float applyArmorCalculations(IEntitySoulCustom entity, DamageSource source, float damage)
    {
        if (!source.isUnblockable())
        {
            int i = 25 - ((EntityLiving)entity).getTotalArmorValue();
            float f1 = damage * (float)i;
            entity.damageArmor(damage);
            damage = f1 / 25.0F;
        }
        return damage;
    }
	
	public static float applyPotionDamageCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {
        if (source.isDamageAbsolute())
        {
            return damage;
        }
        else
        {
            if (entity instanceof EntityZombie)
            {
                //par2 = par2; // Forge: Noop Warning
            }

            int i;
            int j;
            float f1;

            if (((EntityLiving)entity).isPotionActive(Potion.resistance) && source != DamageSource.outOfWorld)
            {
                i = (((EntityLiving)entity).getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
                j = 25 - i;
                f1 = damage * (float)j;
                damage = f1 / 25.0F;
            }

            if (damage <= 0.0F)
            {
                return 0.0F;
            }
            else
            {
                i = EnchantmentHelper.getEnchantmentModifierDamage(((EntityLiving)entity).getLastActiveItems(), source);

                if (i > 20)
                {
                    i = 20;
                }

                if (i > 0 && i <= 20)
                {
                    j = 25 - i;
                    f1 = damage * (float)j;
                    damage = f1 / 25.0F;
                }

                return damage;
            }
        }
    }
}
