package seremis.geninfusion.soul.traits;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;

/**
 * @author Seremis
 */
public class TraitFluids extends Trait {

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        boolean drownsInWater = SoulHelper.geneRegistry.getValueBoolean(entity, Genes.GENE_DROWNS_IN_WATER);

        if(drownsInWater) {
            if(!entity.getBoolean("isDead") && ((EntityLiving) entity).isInsideOfMaterial(Material.water)) {
                if(!((EntityLiving) entity).canBreatheUnderwater() && !((EntityLiving) entity).isPotionActive(Potion.waterBreathing.id) && !(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.disableDamage)) {
                    ((EntityLiving) entity).setAir(entity.decreaseAirSupply(((EntityLiving) entity).getAir()));

                    if(((EntityLiving) entity).getAir() == -20) {
                        ((EntityLiving) entity).setAir(0);

                        for(int i = 0; i < 8; ++i) {
                            float f = entity.getRandom().nextFloat() - entity.getRandom().nextFloat();
                            float f1 = entity.getRandom().nextFloat() - entity.getRandom().nextFloat();
                            float f2 = entity.getRandom().nextFloat() - entity.getRandom().nextFloat();

                            entity.getWorld().spawnParticle("bubble", entity.getDouble("posX") + (double) f, entity.getDouble("posY") + (double) f1, entity.getDouble("posZ") + (double) f2, entity.getDouble("motionX"), entity.getDouble("motionY"), entity.getDouble("motionZ"));
                        }

                        entity.attackEntityFrom(DamageSource.drown, 2.0F);
                    }
                }

                if(!entity.getWorld().isRemote && ((EntityLiving) entity).isRiding() && ((EntityLiving) entity).ridingEntity != null && ((EntityLiving) entity).ridingEntity.shouldDismountInWater((Entity) entity)) {
                    ((EntityLiving) entity).mountEntity(null);
                }
            } else {
                ((EntityLiving) entity).setAir(300);
            }
        }

        if(((EntityLiving) entity).isInsideOfMaterial(Material.water)) {
            ((EntityLiving) entity).extinguish();
        }

        boolean drownsInAir = SoulHelper.geneRegistry.getValueBoolean(entity, Genes.GENE_DROWNS_IN_AIR);

        if(drownsInAir) {
            int air = ((EntityLiving) entity).getAir();

            if(((EntityLiving) entity).isEntityAlive() && !((EntityLiving) entity).isInWater()) {
                --air;
                ((EntityLiving) entity).setAir(air);

                if(((EntityLiving) entity).getAir() == -20) {
                    ((EntityLiving) entity).setAir(0);
                    entity.attackEntityFrom(DamageSource.drown, 2.0F);
                }
            } else {
                ((EntityLiving) entity).setAir(300);
            }
        }
    }
}
