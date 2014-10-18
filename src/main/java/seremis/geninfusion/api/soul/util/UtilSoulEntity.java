package seremis.geninfusion.api.soul.util;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.soul.allele.AlleleBoolean;

import java.util.Random;

public class UtilSoulEntity {

    public static void extinguish(IEntitySoulCustom entity) {
        entity.setInteger("fire", 0);
    }
    
    public static ItemStack getEquipmentInSlot(IEntitySoulCustom entity, int slot) {
        return entity.getItemStack("equipment." + slot);
    }
    
    public static void setEquipmentInSlot(IEntitySoulCustom entity, int slot, ItemStack stack) {
        entity.setItemStack("equipment." + slot, stack);
    }
    
    public static boolean handleLavaMovement(IEntitySoulCustom entity) {
        return entity.getWorld().isMaterialInBB(entity.getBoundingBox().expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.lava);
    }
    
    public static boolean isEntityInsideOpaqueBlock(IEntitySoulCustom entity) {
    	float width = entity.getFloat("width");
    	double posX = entity.getDouble("posX");
    	double posY = entity.getDouble("posY");
    	double posZ = entity.getDouble("posZ");
        //TODO added the + 1.0F to compensate for the absence of a calculation for eyeHeight
        float eyeHeight = entity.getFloat("eyeHeight") + 1.0F;

        for (int i = 0; i < 8; ++i) {
            float f = ((float)((i >> 0) % 2) - 0.5F) * width * 0.8F;
            float f1 = ((float)((i >> 1) % 2) - 0.5F) * 0.1F;
            float f2 = ((float)((i >> 2) % 2) - 0.5F) * width * 0.8F;
            int x = MathHelper.floor_double(posX + (double)f);
            int y = MathHelper.floor_double(posY + (double)eyeHeight + (double)f1);
            int z = MathHelper.floor_double(posZ + (double)f2);

            if (entity.getWorld().getBlock(x, y, z).isNormalCube()) {
                return true;
            }
        }
        return false;
    }
    
    public static void travelToDimension(IEntitySoulCustom entity, int dimensionId) {
		boolean isDead = entity.getBoolean("isDead");
		
        if (CommonProxy.instance.isServerWorld(entity.getWorld()) && !isDead) {
            entity.getWorld().theProfiler.startSection("changeDimension");
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            int currentDimension = entity.getInteger("dimension");
            WorldServer worldserver = minecraftserver.worldServerForDimension(currentDimension);
            WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionId);
            entity.setInteger("dimension", dimensionId);

            if (currentDimension == 1 && dimensionId == 1) {
                worldserver1 = minecraftserver.worldServerForDimension(0);
                entity.setInteger("dimension", 0);
            }

            if (entity.getWorld().getEntityByID(entity.getInteger("riddenByEntityID")) != null) {
            	entity.getWorld().getEntityByID(entity.getInteger("riddenByEntityID")).mountEntity(null);
            }

            if (entity.getWorld().getEntityByID(entity.getInteger("ridingEntityID")) != null) {
            	entity.getWorld().getEntityByID(entity.getEntityId()).mountEntity(null);
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

            entity.setBoolean("isDead", true);
            entity.getWorld().theProfiler.endSection();
            worldserver.resetUpdateEntityTick();
            worldserver1.resetUpdateEntityTick();
            entity.getWorld().theProfiler.endSection();
        }
    }
    
	public static float applyArmorCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {
        if (!source.isUnblockable()) {
            int i = 25 - ((EntityLiving)entity).getTotalArmorValue();
            float f1 = damage * (float)i;
            damage = f1 / 25.0F;
        }
        return damage;
    }
	
	public static float applyPotionDamageCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {
        if (source.isDamageAbsolute()) {
            return damage;
        } else {

            int i;
            int j;
            float f1;

            if (((EntityLiving)entity).isPotionActive(Potion.resistance) && source != DamageSource.outOfWorld) {
                i = (((EntityLiving)entity).getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
                j = 25 - i;
                f1 = damage * (float)j;
                damage = f1 / 25.0F;
            }

            if (damage <= 0.0F) {
                return 0.0F;
            } else {
                i = EnchantmentHelper.getEnchantmentModifierDamage(((EntityLiving)entity).getLastActiveItems(), source);

                if (i > 20) {
                    i = 20;
                }

                if (i > 0 && i <= 20) {
                    j = 25 - i;
                    f1 = damage * (float)j;
                    damage = f1 / 25.0F;
                }

                return damage;
            }
        }
    }
	
	public static void setPosition(IEntitySoulCustom entity, double x, double y, double z) {
        entity.setDouble("posX", x);
        entity.setDouble("posY", y);
        entity.setDouble("posZ", z);
        float f = entity.getFloat("width") / 2.0F;
        float f1 = entity.getFloat("height");
        float yOffset = entity.getFloat("yOffset");
        float ySize = entity.getFloat("ySize");
        
        entity.getBoundingBox().setBounds(x - (double)f, y - (double)yOffset + (double)ySize, z - (double)f, x + (double)f, y - (double)yOffset + (double)ySize + (double)f1, z + (double)f);
    }
	
    public static void setRotation(IEntitySoulCustom entity, float yaw, float pitch) {
        entity.setFloat("rotationYaw", yaw % 360.0F);
        entity.setFloat("rotationPitch", pitch % 360.0F);
    }
    
    public static void jump(IEntitySoulCustom entity) {
        entity.setDouble("motionY", 0.41999998688697815D);

        if (((EntityLiving)entity).isPotionActive(Potion.jump)) {
        	entity.setDouble("motionY", entity.getDouble("motionY") + (double)((float)(((EntityLiving)entity).getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F));
        }

        if (((EntityLiving)entity).isSprinting()) {
            float f = entity.getFloat("rotationYaw") * 0.017453292F;
            entity.setDouble("motionX", entity.getDouble("motionX") - (double)(MathHelper.sin(f) * 0.2F));
            entity.setDouble("motionZ", entity.getDouble("motionZ") + (double)(MathHelper.cos(f) * 0.2F));
        }

        entity.setBoolean("isAirBorne", true);
        ForgeHooks.onLivingJump((EntityLivingBase) entity);
    }
    
    public static void despawnEntity(IEntitySoulCustom entity) {
        Result result = null;
        boolean shouldDespawn = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_SHOULD_DESPAWN)).value;
        
        int entityAge = entity.getInteger("entityAge");
        
        if (!shouldDespawn) {
        	entity.setInteger("entityAge", 0);
        } else if ((entityAge & 0x1F) == 0x1F && (result = ForgeEventFactory.canEntityDespawn((EntityLiving) entity)) != Result.DEFAULT) {
            if (result == Result.DENY) {
            	entity.setInteger("entityAge", 0);
            } else {
                ((EntityLiving)entity).setDead();
            }
        } else {
            EntityPlayer entityplayer = entity.getWorld().getClosestPlayerToEntity((Entity) entity, -1.0D);

            if (entityplayer != null) {
            	double posX = entity.getDouble("posX");
            	double posY = entity.getDouble("posY");
            	double posZ = entity.getDouble("posZ");
            	
                double d0 = entityplayer.posX - posX;
                double d1 = entityplayer.posY - posY;
                double d2 = entityplayer.posZ - posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (shouldDespawn && d3 > 16384.0D) {
                	((EntityLiving)entity).setDead();
                }

                if (entity.getInteger("entityAge") > 600 && new Random().nextInt(800) == 0 && d3 > 1024.0D && shouldDespawn) {
                	((EntityLiving)entity).setDead();
                } else if (d3 < 1024.0D) {
                	entity.setInteger("entityAge", 0);
                }
            }
        }
    }
    
    public static EntityItem dropItem(IEntitySoulCustom entity, ItemStack droppedStack, float dropHeight) {
        if(droppedStack.stackSize != 0 && droppedStack.getItem() != null) {
        	double posX = entity.getDouble("posX");
        	double posY = entity.getDouble("posY");
        	double posZ = entity.getDouble("posZ");

            EntityItem entityitem = new EntityItem(entity.getWorld(), posX, posY + (double)dropHeight, posZ, droppedStack);
            entityitem.delayBeforeCanPickup = 10;

            if(entity.getBoolean("captureDrops")) {
            	int capturedDropsSize = entity.getInteger("capturedDrops.size");
            	entity.setItemStack("capturedDrops." + capturedDropsSize, droppedStack);
                entity.forceVariableSync();
            } else {
                entity.getWorld().spawnEntityInWorld(entityitem);
            }
            return entityitem;
        } else {
            return null;
        }
    }

    public static boolean isBurning(IEntitySoulCustom entity) {
        boolean flag = entity.getWorld() != null && entity.getWorld().isRemote;
        return !((AlleleBoolean)SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_IMMUNE_TO_FIRE)).value && (entity.getInteger("fire") > 0 || flag && entity.getFlag(0));
    }

    public static void faceEntity(IEntitySoulCustom entity, Entity lookEntity, float maxYawIncrement, float maxPitchIncrement) {
        double d0 = lookEntity.posX - entity.getInteger("posX");
        double d2 = lookEntity.posZ - entity.getInteger("posZ");
        double d1;

        if (lookEntity instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)lookEntity;
            d1 = entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (entity.getInteger("posY") + (double)getEyeHeight(entity));
        } else {
            d1 = (lookEntity.boundingBox.minY + lookEntity.boundingBox.maxY) / 2.0D - (entity.getInteger("posY") + (double)getEyeHeight(entity));
        }

        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
        float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));

        float rotationYaw = entity.getFloat("rotationYaw");
        float rotationPitch = entity.getFloat("rotationPitch");
        setRotation(entity, updateRotation(rotationYaw, f2, maxYawIncrement), updateRotation(rotationPitch, f3, maxPitchIncrement));
    }

    public static float getEyeHeight(IEntitySoulCustom entity) {
        return entity.getFloat("height") * 0.85F;
    }

    public static float updateRotation(float current, float intended, float maxIncrement) {
        float f3 = MathHelper.wrapAngleTo180_float(intended - current);

        if (f3 > maxIncrement) {
            f3 = maxIncrement;
        }

        if (f3 < -maxIncrement) {
            f3 = -maxIncrement;
        }

        return current + f3;
    }

    public static boolean canEntityBeSeen(IEntitySoulCustom entity, Entity ent) {
        double posX = entity.getDouble("posX");
        double posY = entity.getDouble("posY");
        double posZ = entity.getDouble("posZ");
        return entity.getWorld().rayTraceBlocks(Vec3.createVectorHelper(posX, posY + (double) getEyeHeight(entity), posZ), Vec3.createVectorHelper(ent.posX, ent.posY + (double)ent.getEyeHeight(), ent.posZ)) == null;
    }
}