package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.UtilSoulEntity;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.soul.allele.AlleleBoolean;

import java.util.Random;

public class TraitFire extends Trait {

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        boolean burnsInDayLight = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_BURNS_IN_DAYLIGHT)).value;
        boolean isImmuneToFire = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_IMMUNE_TO_FIRE)).value;

        double posX = entity.getDouble("posX");
        double posY = entity.getDouble("posY");
        double posZ = entity.getDouble("posZ");
        
        float fallDistance = entity.getFloat("fallDistance");
        
        if(burnsInDayLight && !isImmuneToFire) {
            if(CommonProxy.instance.isServerWorld(entity.getWorld()) && entity.getWorld().isDaytime()) {

                float brightness = getBrightness(entity);
                Random rand = new Random();

                if(brightness > 0.5F && rand.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F && entity.getWorld().canBlockSeeTheSky((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ))) {
                    ItemStack headwear = UtilSoulEntity.getEquipmentInSlot(entity, 4);

                    if(headwear != null) {
                        if(headwear.isItemStackDamageable()) {
                            headwear.setItemDamage(headwear.getItemDamageForDisplay() + new Random().nextInt(2));
                            if(headwear.getItemDamageForDisplay() >= headwear.getMaxDamage()) {
                                breakHeadwear(entity, headwear);
                                UtilSoulEntity.setEquipmentInSlot(entity, 4, null);
                            }
                        }
                    } else {
                        entity.setInteger("fire", 8*20);
                    }
                }
            }
        }

        int fireTicks = entity.getInteger("fire");
        
        if(CommonProxy.instance.isRenderWorld(entity.getWorld())) {
            UtilSoulEntity.extinguish(entity);
        } else if(fireTicks > 0) {
            if(isImmuneToFire) {
                UtilSoulEntity.extinguish(entity);
            } else {
                if(fireTicks % 20 == 0) {
                    entity.attackEntityFrom(DamageSource.onFire, 1.0F);
                }
                entity.setInteger("fire", fireTicks - 1);
                entity.setFlag(0, fireTicks > 0);
            }
        } else if(fireTicks <= 0) {
        	entity.setFlag(0, false);
        }
        
        if(UtilSoulEntity.handleLavaMovement(entity)) {
            if(!isImmuneToFire) {
                entity.attackEntityFrom(DamageSource.lava, 4.0F);
                entity.setInteger("fire", 15*20);
            }
            entity.setFloat("fallDistance", fallDistance * 0.5F);
        }
    }
    
    public float getBrightness(IEntitySoulCustom entity) {
        double posX = entity.getDouble("posX");
        double posY = entity.getDouble("posY");
        double posZ = entity.getDouble("posZ");

        float yOffset = entity.getFloat("yOffset");

        AxisAlignedBB boundingBox = entity.getBoundingBox();
        
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posZ);

        if(entity.getWorld().blockExists(i, 0, j)) {
            double d0 = (boundingBox.maxY - boundingBox.minY) * 0.66D;
            int k = MathHelper.floor_double(posY - yOffset + d0);
            return entity.getWorld().getLightBrightness(i, k, j);
        } else {
            return 0.0F;
        }
    }

    public void breakHeadwear(IEntitySoulCustom entity, ItemStack stack) {
        float pitch = entity.getFloat("rotationPitch");
        float yaw = entity.getFloat("rotationYaw");
        
        double posX = entity.getDouble("posX");
        double posY = entity.getDouble("posY");
        double posZ = entity.getDouble("posZ");
        
        float eyeHeight = entity.getFloat("eyeHeight");
        
        entity.playSound("random.break", 0.8F, 0.8F + entity.getRandom().nextFloat() * 0.4F);

        for(int i = 0; i < 5; ++i) {
            Vec3 vec3 = Vec3.createVectorHelper(((double)entity.getRandom().nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vec3.rotateAroundX(-pitch * (float) Math.PI / 180.0F);
            vec3.rotateAroundY(-yaw * (float) Math.PI / 180.0F);
            Vec3 vec31 = Vec3.createVectorHelper(((double)entity.getRandom().nextFloat() - 0.5D) * 0.3D, (double)(-entity.getRandom().nextFloat()) * 0.6D - 0.3D, 0.6D);
            vec31.rotateAroundX(-pitch * (float) Math.PI / 180.0F);
            vec31.rotateAroundY(-yaw * (float) Math.PI / 180.0F);
            vec31 = vec31.addVector(posX, posY + eyeHeight, posZ);
            entity.getWorld().spawnParticle("iconcrack_" + stack.getItem(), vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord);
        }
    }

    @Override
    public boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack) {
        int i = entity.getWorld().difficultySetting.getDifficultyId();

        if (UtilSoulEntity.getEquipmentInSlot(entity, 0) == null && UtilSoulEntity.isBurning(entity) && entity.getRandom().nextFloat() < (float)i * 0.3F) {
            entityToAttack.setFire(2 * i);
        }
        return true;
    }
}
