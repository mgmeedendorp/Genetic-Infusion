package seremis.soulcraft.soul.actions;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;

public class ChromosomeBurnsInDaylight extends EntityEventHandler {

    @Override
    public void onInit(IEntitySoulCustom entity) {}
    
    @Override
    public void onUpdate(IEntitySoulCustom entity) {
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
    
    @Override
    public void onInteract(IEntitySoulCustom entity, EntityPlayer player) {}

    @Override
    public void onDeath(IEntitySoulCustom entity, DamageSource source) {}

    @Override
    public void onKillEntity(IEntitySoulCustom entity, EntityLivingBase killed) {}

    @Override
    public boolean onEntityAttacked(IEntitySoulCustom entity, DamageSource source, float damage) {
        return true;
    }

    @Override
    public void onSpawnWithEgg(IEntitySoulCustom entity, EntityLivingData data) {}
}
