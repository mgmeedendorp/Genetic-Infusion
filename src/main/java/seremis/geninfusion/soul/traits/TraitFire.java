package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class TraitFire extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        entity.setBoolean("isImmuneToFire", ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_IMMUNE_TO_FIRE)).value);
    }

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        EntityLiving living = (EntityLiving) entity;

        boolean burnsInDayLight = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_BURNS_IN_DAYLIGHT)).value;
        boolean childrenBurnInDaylight = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_CHILDREN_BURN_IN_DAYLIGHT)).value;

        double posX = entity.getDouble("posX");
        double posY = entity.getDouble("posY");
        double posZ = entity.getDouble("posZ");

        boolean isImmuneToFire = entity.getBoolean("isImmuneToFire");

        if(burnsInDayLight && !isImmuneToFire) {
            if(entity.getWorld().isDaytime() && !entity.getWorld().isRemote && (!living.isChild() || childrenBurnInDaylight)) {
                float f = living.getBrightness(1.0F);

                if(f > 0.5F && entity.getRandom().nextFloat() * 30.0F < (f - 0.4F) * 2.0F && entity.getWorld().canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ))) {
                    boolean flag = true;
                    ItemStack itemstack = living.getEquipmentInSlot(4);

                    if(itemstack != null) {
                        if(itemstack.isItemStackDamageable()) {
                            itemstack.setItemDamage(itemstack.getItemDamageForDisplay() + entity.getRandom().nextInt(2));

                            if(itemstack.getItemDamageForDisplay() >= itemstack.getMaxDamage()) {
                                living.renderBrokenItemStack(itemstack);
                                living.setCurrentItemOrArmor(4, null);
                            }
                        }

                        flag = false;
                    }

                    if(flag) {
                        living.setFire(8);
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

        if(((EntityLiving)entity).handleLavaMovement()) {
            entity.setOnFireFromLava();
            entity.setFloat("fallDistance", entity.getFloat("fallDistance") * 0.5F);
        }

        if(entity.getDouble("posY") < -64.0D) {
            living.setDead();
        }

        if(!entity.getWorld().isRemote) {
            entity.setFlag(0, fire > 0);
        }

        if (living.isEntityAlive() && living.isWet()) {
            living.extinguish();
        }
    }

    @Override
    public boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack) {
        EntityLiving living = (EntityLiving) entity;

        boolean setEntitiesOnFire = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_SET_ON_FIRE_FROM_ATTACK)).value;

        int difficulty = entity.getWorld().difficultySetting.getDifficultyId();

        if(setEntitiesOnFire && living.getEquipmentInSlot(0) == null && living.isBurning() && entity.getRandom().nextFloat() < (float) difficulty * 0.3F) {
            entityToAttack.setFire(2 * difficulty);
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
