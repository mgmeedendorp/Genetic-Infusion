package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeHooks;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.UtilSoulEntity;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.helper.GIReflectionHelper;
import seremis.geninfusion.soul.allele.AlleleBoolean;
import seremis.geninfusion.soul.allele.AlleleFloat;
import seremis.geninfusion.soul.allele.AlleleInteger;
import seremis.geninfusion.soul.allele.AlleleString;

import java.util.Random;

public class TraitAttacked extends Trait {

    private Random rand = new Random();

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        float health = entity.getFloat("health");
        int attackTime = entity.getInteger("attackTime");
        int hurtTime = entity.getInteger("hurtTime");
        int hurtResistantTime = entity.getInteger("hurtResistantTime");
        int recentlyHit = entity.getInteger("recentlyHit");
        int ticksExisted = entity.getInteger("ticksExisted");
        EntityLivingBase lastAttacker = entity.getInteger("lastAttackerID") != 0 ? (EntityLivingBase) entity.getWorld().getEntityByID(entity.getInteger("lastAttacker")) : null;
        EntityLivingBase entityLivingToAttack = entity.getInteger("entityLivingToAttackID") != 0 ? (EntityLivingBase) entity.getWorld().getEntityByID(entity.getInteger("entityLivingToAttackID")) : null;

        if(attackTime > 0) {
            entity.setInteger("attackTime", --attackTime);
        }

        if(hurtTime > 0) {
            entity.setInteger("hurtTime", --hurtTime);
        }

        if(hurtResistantTime > 0) {
            entity.setInteger("hurtResistantTime", --hurtResistantTime);
        }

        if(health <= 0.0F) {
            entity.onDeathUpdate();
        }

        if(recentlyHit > 0) {
            entity.setInteger("recentlyHit", --recentlyHit);
        } else {
            entity.setInteger("attackingPlayerID", 0);
        }

        if(lastAttacker != null && !lastAttacker.isEntityAlive()) {
            entity.setInteger("lastAttackerID", 0);
        }

        if(entityLivingToAttack != null) {
            //TODO Look into this
            if(!entityLivingToAttack.isEntityAlive()) {
                ((EntityLiving) entity).setRevengeTarget(null);
            } else if(ticksExisted - ((EntityLiving) entity).func_142015_aE() > 100) {
                ((EntityLiving) entity).setRevengeTarget(null);
            }
        }
    }

    @Override
    public boolean onEntityAttacked(IEntitySoulCustom entity, DamageSource source, float damage) {
        if(ForgeHooks.onLivingAttack((EntityLivingBase) entity, source, damage)) return false;
        if(((EntityLiving) entity).isEntityInvulnerable()) {
            return false;
        } else if(CommonProxy.instance.isRenderWorld(entity.getWorld())) {
            return false;
        } else {
            entity.setInteger("entityAge", 0);

            float health = entity.getFloat("health");
            int hurtResistantTime = entity.getInteger("hurtResistantTime");
            int maxHurtResistantTime = ((AlleleInteger) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MAX_HURT_RESISTANT_TIME)).value;
            int hurtTime = entity.getInteger("hurtTime");
            int maxHurtTime = entity.getInteger("maxHurtTime");
            float lastDamage = entity.getFloat("lastDamage");


            if(health <= 0.0F) {
                return false;
            } else if(source.isFireDamage() && ((EntityLiving) entity).isPotionActive(Potion.fireResistance)) {
                return false;
            } else {
                if((source == DamageSource.anvil || source == DamageSource.fallingBlock) && UtilSoulEntity.getEquipmentInSlot(entity, 4) != null) {
                    UtilSoulEntity.getEquipmentInSlot(entity, 4).damageItem((int) (damage * 4.0F + this.rand.nextFloat() * damage * 2.0F), (EntityLivingBase) entity);
                    damage *= 0.75F;
                }

                entity.setFloat("limbSwingAmount", 1.5F);
                boolean flag = true;

                if((float) hurtResistantTime > (float) maxHurtResistantTime / 2.0F) {
                    if(damage <= lastDamage) {
                        return false;
                    }

                    entity.damageEntity(source, damage - lastDamage);
                    entity.setFloat("lastDamage", damage);
                    flag = false;
                } else {
                    entity.setFloat("lastDamage", damage);
                    entity.setFloat("prevHealth", health);
                    entity.setInteger("hurtResistantTime", maxHurtResistantTime);
                    entity.damageEntity(source, damage);
                    entity.setInteger("hurtTime", maxHurtTime);
                }

                entity.setFloat("attackedAtYaw", 0.0F);
                Entity ent = source.getEntity();

                if(ent != null) {
                    if(ent instanceof EntityLivingBase) {
                        ((EntityLiving) entity).setRevengeTarget((EntityLivingBase) ent);
                    }

                    if(ent instanceof EntityPlayer) {
                        entity.setInteger("recentlyHit", 100);
                        entity.setInteger("attackingPlayerID", ent.getEntityId());
                    } else if(ent instanceof EntityWolf) {
                        EntityWolf entitywolf = (EntityWolf) ent;

                        if(entitywolf.isTamed()) {
                            entity.setInteger("recentlyHit", 100);
                            entity.setInteger("attackingPlayerID", 0);
                        }
                    }
                }

                if(flag) {
                    entity.getWorld().setEntityState((Entity) entity, (byte) 2);

                    if(source != DamageSource.drown) {
                        double knockbackResistance = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_KNOCKBACK_RESISTANCE)).value;
                        entity.setBoolean("velocityChanged", this.rand.nextDouble() >= knockbackResistance);
                    }

                    if(ent != null) {
                        double posX = entity.getDouble("posX");
                        double posZ = entity.getDouble("posZ");
                        float rotationYaw = entity.getFloat("rotationYaw");

                        double d1 = ent.posX - posX;
                        double d0;

                        for(d0 = ent.posZ - posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
                            d1 = (Math.random() - Math.random()) * 0.01D;
                        }

                        entity.setFloat("attackedAtYaw", (float) (Math.atan2(d0, d1) * 180.0D / Math.PI) - rotationYaw);
                        ((EntityLiving) entity).knockBack(ent, damage, d1, d0);
                    } else {
                        entity.setFloat("attackedAtYaw", (float) ((int) (Math.random() * 2.0D) * 180));
                    }
                }

                String sound;
                float soundVolume = ((AlleleFloat) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_SOUND_VOLUME)).value;
                float soundPitch = ((EntityLiving) entity).isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F;

                health = entity.getFloat("health");

                if(health <= 0.0F) {
                    sound = ((AlleleString) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_DEATH_SOUND)).value;

                    if(flag && sound != null) {
                        entity.playSound(sound, soundVolume, soundPitch);
                    }

                    ((EntityLiving) entity).onDeath(source);
                } else {
                    sound = ((AlleleString) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_HURT_SOUND)).value;

                    if(flag && sound != null) {
                        entity.playSound(sound, soundVolume, soundPitch);
                    }
                }

                return true;
            }
        }
    }

    @Override
    public void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage) {
        boolean isInvulnerable = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_INVULNERABLE)).value;

        if(!isInvulnerable) {
            float health = entity.getFloat("health");
            float absorptionAmount = entity.getFloat("absorptionAmount");

            damage = ForgeHooks.onLivingHurt((EntityLivingBase) entity, source, damage);
            if(damage <= 0) return;
            damage = UtilSoulEntity.applyArmorCalculations(entity, source, damage);
            damage = UtilSoulEntity.applyPotionDamageCalculations(entity, source, damage);
            float f1 = damage;
            damage = Math.max(damage - absorptionAmount, 0.0F);
            entity.setFloat("absorptionAmount", Math.max(absorptionAmount - (f1 - damage), 0.0F));

            if(damage != 0.0F) {
                entity.setFloat("health", health - damage);

                CombatTracker combatTracker = (CombatTracker) entity.getObject("_combatTracker");

                combatTracker.func_94547_a(source, health, damage);
                entity.setFloat("absorptionAmount", Math.max(absorptionAmount - damage, 0));
            }

        }
    }

    @Override
    public void onDeath(IEntitySoulCustom entity, DamageSource source) {
        if(ForgeHooks.onLivingDeath((EntityLiving) entity, source)) return;
        Entity ent = source.getEntity();
        EntityLivingBase entitylivingbase = ((EntityLiving) entity).func_94060_bK();

        int scoreValue = entity.getInteger("scoreValue");

        if(scoreValue >= 0 && entitylivingbase != null) {
            entitylivingbase.addToPlayerScore((Entity) entity, scoreValue);
        }

        if(ent != null) {
            ent.onKillEntity((EntityLiving) entity);
        }

        entity.setBoolean("isDead", true);
    }
}
