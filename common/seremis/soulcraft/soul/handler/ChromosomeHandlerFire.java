package seremis.soulcraft.soul.handler;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.soul.EnumChromosome;
import seremis.soulcraft.soul.SoulHandler;
import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;

public class ChromosomeHandlerFire extends EntityEventHandler {

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        boolean burnsInDayLight = ((AlleleBoolean)SoulHandler.getSoulFrom(entity).getChromosomes()[EnumChromosome.BURNS_IN_DAYLIGHT.ordinal()].getActive()).value;
        boolean isImmuneToFire = ((AlleleBoolean)SoulHandler.getSoulFrom(entity).getChromosomes()[EnumChromosome.IMMUNE_TO_FIRE.ordinal()].getActive()).value;
        
        if(burnsInDayLight && !isImmuneToFire) {
            if(CommonProxy.proxy.isServerWorld(entity.getWorld()) && entity.getWorld().isDaytime()) {

                float brightness = entity.getBrightness();
                
                if(brightness > 0.5F && entity.getWorld().canBlockSeeTheSky((int) Math.floor(entity.getPosX()), (int) Math.floor(entity.getPosY()), (int) Math.floor(entity.getPosZ()))) {
                    ItemStack headwear = entity.getCurrentItemOrArmor(4);
                                        
                    if(headwear != null) {
                        if(headwear.isItemStackDamageable()) {
                            headwear.setItemDamage(headwear.getItemDamageForDisplay() + new Random().nextInt(2));
                            
                            if(headwear.getItemDamageForDisplay() >= headwear.getMaxDamage()) {
                                breakHeadwear(entity, headwear);
                                entity.setArmor(3, null);
                            }
                        }
                    } else {
                        entity.setFire(8);
                    }
                }   
            }
        }
        
        if(CommonProxy.proxy.isRenderWorld(entity.getWorld())) {
            entity.setFireNew(0);
        } else if(entity.getFire() > 0) {
            if(isImmuneToFire) {
                entity.extinguish();
            } else {
                if(entity.getFire() % 20 == 0) {
                    entity.attackEntityFrom(DamageSource.onFire, 1.0F);
                }
                if(entity.handleLavaMovement() && entity.getFire() % 20 == 0) {
                    entity.setFire(15);
                    entity.attackEntityFrom(DamageSource.lava, 4.0F);
                }
                entity.setFireNew(entity.getFire()-1);
                entity.setFlag(0, entity.getFire() > 0);
            }
        }
    }
    
    public void breakHeadwear(IEntitySoulCustom entity, ItemStack stack) {
        Random rand = new Random();
        
        entity.playSound("random.break", 0.8F, 0.8F + rand.nextFloat() * 0.4F);

        for (int i = 0; i < 5; ++i) {
            Vec3 vec3 = entity.getWorld().getWorldVec3Pool().getVecFromPool(((double)rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vec3.rotateAroundX(-entity.getRotationPitch() * (float)Math.PI / 180.0F);
            vec3.rotateAroundY(-entity.getRotationYaw() * (float)Math.PI / 180.0F);
            Vec3 vec31 = entity.getWorld().getWorldVec3Pool().getVecFromPool(((double)rand.nextFloat() - 0.5D) * 0.3D, (double)(-rand.nextFloat()) * 0.6D - 0.3D, 0.6D);
            vec31.rotateAroundX(-entity.getRotationPitch() * (float)Math.PI / 180.0F);
            vec31.rotateAroundY(-entity.getRotationYaw() * (float)Math.PI / 180.0F);
            vec31 = vec31.addVector(entity.getPosX(), entity.getPosY() + (double)entity.getEyeHeight(), entity.getPosZ());
            entity.getWorld().spawnParticle("iconcrack_" + stack.getItem().itemID, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord);
        }
    }
}
