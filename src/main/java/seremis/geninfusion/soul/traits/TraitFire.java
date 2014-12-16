package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.UtilSoulEntity;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class TraitFire extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        entity.setBoolean("isImmuneToFire", ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_IMMUNE_TO_FIRE)).value);
    }

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        boolean burnsInDayLight = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_BURNS_IN_DAYLIGHT)).value;
        boolean childrenBurnInDaylight = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_CHILDREN_BURN_IN_DAYLIGHT)).value;

        double posX = entity.getDouble("posX");
        double posY = entity.getDouble("posY");
        double posZ = entity.getDouble("posZ");

        boolean isImmuneToFire = entity.getBoolean("isImmuneToFire");

        float fallDistance = entity.getFloat("fallDistance");

        if(burnsInDayLight && !isImmuneToFire) {
            if(entity.getWorld().isDaytime() && !entity.getWorld().isRemote && (!((EntityLiving) entity).isChild() || childrenBurnInDaylight)) {
                float f = ((EntityLiving) entity).getBrightness(1.0F);

                if(f > 0.5F && entity.getRandom().nextFloat() * 30.0F < (f - 0.4F) * 2.0F && entity.getWorld().canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ))) {
                    boolean flag = true;
                    ItemStack itemstack = ((EntityLiving) entity).getEquipmentInSlot(4);

                    if(itemstack != null) {
                        if(itemstack.isItemStackDamageable()) {
                            itemstack.setItemDamage(itemstack.getItemDamageForDisplay() + entity.getRandom().nextInt(2));

                            if(itemstack.getItemDamageForDisplay() >= itemstack.getMaxDamage()) {
                                ((EntityLiving) entity).renderBrokenItemStack(itemstack);
                                ((EntityLiving) entity).setCurrentItemOrArmor(4, null);
                            }
                        }

                        flag = false;
                    }

                    if(flag) {
                        ((EntityLiving) entity).setFire(8);
                    }
                }
            }
        }

        int fire = entity.getInteger("fire");

        if(entity.getWorld().isRemote) {
            fire = 0;
        } else if(fire > 0) {
            if(isImmuneToFire) {
                fire -= 4;

                if(fire < 0) {
                    fire = 0;
                }
            } else {
                if(fire % 20 == 0) {
                    entity.attackEntityFrom(DamageSource.onFire, 1.0F);
                }

                --fire;
            }
        }

        if(UtilSoulEntity.handleLavaMovement(entity)) {
            entity.setOnFireFromLava();
            entity.setFloat("fallDistance", entity.getFloat("fallDistance") * 0.5F);
        }

        if(entity.getDouble("posY") < -64.0D) {
            ((EntityLiving) entity).setDead();
        }

        if(!entity.getWorld().isRemote) {
            entity.setFlag(0, fire > 0);
        }
    }

    @Override
    public boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack) {
        boolean setEntitiesOnFire = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_SET_ON_FIRE_FROM_ATTACK)).value;

        int i = entity.getWorld().difficultySetting.getDifficultyId();

        if(setEntitiesOnFire && ((EntityLiving) entity).getEquipmentInSlot(0) == null && ((EntityLiving) entity).isBurning() && entity.getRandom().nextFloat() < (float) i * 0.3F) {
            entityToAttack.setFire(2 * i);
        }
        return true;
    }

    @Override
    public void setOnFireFromLava(IEntitySoulCustom entity) {
        if(!entity.getBoolean("isImmuneToFire")) {
            entity.attackEntityFrom(DamageSource.lava, 4.0F);
            ((EntityLiving) entity).setFire(15);
        }
    }
}
