package seremis.geninfusion.soul.traits;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeHooks;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.UtilSoulEntity;
import seremis.geninfusion.soul.allele.AlleleBoolean;

public class TraitAttacked extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        entity.setBoolean("invulnerable", ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_INVULNERABLE)).value);
    }

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        int attackTime = entity.getInteger("attackTime");
        int hurtTime = entity.getInteger("hurtTime");
        int hurtResistantTime = entity.getInteger("hurtResistantTime");
        int recentlyHit = entity.getInteger("recentlyHit");
        int ticksExisted = entity.getInteger("ticksExisted");

        EntityLivingBase lastAttacker = (EntityLivingBase) entity.getObject("lastAttacker");
        EntityLivingBase entityLivingToAttack = (EntityLivingBase) entity.getObject("entityLivingToAttack");


        if(attackTime > 0) {
            --attackTime;
        }

        if(hurtTime > 0) {
            --hurtTime;
        }

        if(hurtResistantTime > 0 && !(entity instanceof EntityPlayerMP)) {
            --hurtResistantTime;
        }

        if(UtilSoulEntity.getHealth(entity) <= 0.0F) {
            entity.onDeathUpdate();
        }

        if(recentlyHit > 0) {
            --recentlyHit;
        } else {
            entity.setObject("attackingPlayer", null);
        }

        if(lastAttacker != null && !lastAttacker.isEntityAlive()) {
            lastAttacker = null;
        }

        if(entityLivingToAttack != null) {
            if(!entityLivingToAttack.isEntityAlive()) {
                ((EntityLiving) entity).setRevengeTarget(null);
            } else if(ticksExisted - entity.getInteger("revengeTimer") > 100) {
                ((EntityLiving) entity).setRevengeTarget(null);
            }
        }

        entity.setInteger("attackTime", attackTime);
        entity.setInteger("hurtTime", hurtTime);
        entity.setInteger("hurtResistantTime", hurtResistantTime);
        entity.setInteger("recentlyHit", recentlyHit);
        entity.setInteger("ticksExisted", ticksExisted);
        entity.setObject("lastAttacker", lastAttacker);
        entity.setObject("entityLivingToAttack", entityLivingToAttack);
    }

    @Override
    public boolean attackEntityFrom(IEntitySoulCustom entity, DamageSource source, float damage) {
        if(ForgeHooks.onLivingAttack((EntityLivingBase) entity, source, damage)) return false;

        if(entity.getBoolean("invulnerable")) {
            return false;
        } else if(entity.getWorld().isRemote) {
            return false;
        } else {
            entity.setInteger("entityAge", 0);

            if(UtilSoulEntity.getHealth(entity) <= 0.0F) {
                return false;
            } else if(source.isFireDamage() && ((EntityLiving) entity).isPotionActive(Potion.fireResistance)) {
                return false;
            } else {
                if((source == DamageSource.anvil || source == DamageSource.fallingBlock) && ((EntityLiving) entity).getEquipmentInSlot(4) != null) {
                    ((EntityLiving) entity).getEquipmentInSlot(4).damageItem((int) (damage * 4.0F + entity.getRandom().nextFloat() * damage * 2.0F), (EntityLivingBase) entity);
                    damage *= 0.75F;
                }

                entity.setFloat("limbSwingAmount", 1.5F);
                boolean flag = true;

                int hurtResistantTime = entity.getInteger("hurtResistantTime");
                int maxHurtResistantTime = entity.getInteger("maxHurtResistantTime");
                int recentlyHit = entity.getInteger("recentlyHit");
                int hurtTime = entity.getInteger("hurtTime");
                int maxHurtTime = entity.getInteger("maxHurtTime");

                float attackedAtYaw;
                float lastDamage = entity.getFloat("lastDamage");

                EntityPlayer attackingPlayer = (EntityPlayer) entity.getObject("attackingPlayer");


                if((float) hurtResistantTime > (float) maxHurtResistantTime / 2.0F) {
                    if(damage <= lastDamage) {
                        return false;
                    }

                    entity.damageEntity(source, damage - lastDamage);
                    lastDamage = damage;
                    flag = false;
                } else {
                    lastDamage = damage;
                    entity.setFloat("prevHealth", UtilSoulEntity.getHealth(entity));
                    hurtResistantTime = maxHurtResistantTime;
                    entity.damageEntity(source, damage);

                    hurtTime = maxHurtTime = 10;
                }

                attackedAtYaw = 0.0F;
                Entity entity1 = source.getEntity();

                if(entity1 != null) {
                    if(entity1 instanceof EntityLivingBase) {
                        ((EntityLiving) entity).setRevengeTarget((EntityLivingBase) entity1);
                    }

                    if(entity1 instanceof EntityPlayer) {
                        recentlyHit = 100;
                        attackingPlayer = (EntityPlayer) entity1;
                    } else if(entity1 instanceof net.minecraft.entity.passive.EntityTameable) {
                        net.minecraft.entity.passive.EntityTameable entitywolf = (net.minecraft.entity.passive.EntityTameable) entity1;

                        if(entitywolf.isTamed()) {
                            recentlyHit = 100;
                            attackingPlayer = null;
                        }
                    }
                }

                if(flag) {
                    entity.getWorld().setEntityState((Entity) entity, (byte) 2);

                    if(source != DamageSource.drown) {
                        entity.setBeenAttacked();
                    }

                    if(entity1 != null) {
                        double d1 = entity1.posX - entity.getDouble("posX");
                        double d0;

                        for(d0 = entity1.posZ - entity.getDouble("posZ"); d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
                            d1 = (Math.random() - Math.random()) * 0.01D;
                        }

                        attackedAtYaw = (float) (Math.atan2(d0, d1) * 180.0D / Math.PI) - entity.getFloat("rotationYaw");
                        ((EntityLiving) entity).knockBack(entity1, damage, d1, d0);
                    } else {
                        attackedAtYaw = (float) ((int) (Math.random() * 2.0D) * 180);
                    }
                }

                entity.setInteger("hurtResistantTime", hurtResistantTime);
                entity.setInteger("maxHurtResistantTime", maxHurtResistantTime);
                entity.setInteger("recentlyHit", recentlyHit);
                entity.setInteger("hurtTime", hurtTime);
                entity.setInteger("maxHurtTime", maxHurtTime);

                entity.setFloat("attackedAtYaw", attackedAtYaw);
                entity.setFloat("lastDamage", lastDamage);

                entity.setObject("attackingPlayer", attackingPlayer);

                String s;

                if(UtilSoulEntity.getHealth(entity) <= 0.0F) {
                    s = entity.getDeathSound();

                    if(flag && s != null) {
                        entity.playSound(s, entity.getSoundVolume(), entity.getSoundPitch());
                    }

                    ((EntityLiving) entity).onDeath(source);
                } else {
                    s = entity.getHurtSound();

                    if(flag && s != null) {
                        entity.playSound(s, entity.getSoundVolume(), entity.getSoundPitch());
                    }
                }

                return true;
            }
        }
    }

    @Override
    public void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage) {
        if(!entity.getBoolean("invulnerable")) {
            damage = ForgeHooks.onLivingHurt((EntityLivingBase) entity, source, damage);
            if(damage <= 0) return;
            damage = entity.applyArmorCalculations(source, damage);
            damage = entity.applyPotionDamageCalculations(source, damage);
            float f1 = damage;
            damage = Math.max(damage - ((EntityLiving) entity).getAbsorptionAmount(), 0.0F);
            ((EntityLiving) entity).setAbsorptionAmount(((EntityLiving) entity).getAbsorptionAmount() - (f1 - damage));

            if(damage != 0.0F) {
                float f2 = UtilSoulEntity.getHealth(entity);
                ((EntityLiving) entity).setHealth(f2 - damage);
                ((EntityLiving) entity).func_110142_aN().func_94547_a(source, f2, damage);
                ((EntityLiving) entity).setAbsorptionAmount(((EntityLiving) entity).getAbsorptionAmount() - damage);
            }
        }
    }

    @Override
    public float applyArmorCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {
        if(!source.isUnblockable()) {
            int i = 25 - ((EntityLiving) entity).getTotalArmorValue();
            float f1 = damage * (float) i;
            entity.damageArmor(damage);
            damage = f1 / 25.0F;
        }
        return damage;
    }

    @Override
    public float applyPotionDamageCalculations(IEntitySoulCustom entity, DamageSource source, float damage) {
        if(source.isDamageAbsolute()) {
            return damage;
        } else {
            int i;
            int j;
            float f1;

            if(((EntityLiving) entity).isPotionActive(Potion.resistance) && source != DamageSource.outOfWorld) {
                i = (((EntityLiving) entity).getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
                j = 25 - i;
                f1 = damage * (float) j;
                damage = f1 / 25.0F;
            }

            if(damage <= 0.0F) {
                return 0.0F;
            } else {
                i = EnchantmentHelper.getEnchantmentModifierDamage(((EntityLiving) entity).getLastActiveItems(), source);

                if(i > 20) {
                    i = 20;
                }

                if(i > 0 && i <= 20) {
                    j = 25 - i;
                    f1 = damage * (float) j;
                    damage = f1 / 25.0F;
                }

                return damage;
            }
        }
    }

    @Override
    public void onDeath(IEntitySoulCustom entity, DamageSource source) {
        if(ForgeHooks.onLivingDeath((EntityLivingBase) entity, source)) return;
        Entity entity1 = source.getEntity();
        EntityLivingBase entitylivingbase = ((EntityLiving) entity).func_94060_bK();

        if(entity.getInteger("scoreValue") >= 0 && entitylivingbase != null) {
            entitylivingbase.addToPlayerScore((Entity) entity, entity.getInteger("scoreValue"));
        }

        if(entity1 != null) {
            entity1.onKillEntity((EntityLivingBase) entity);
        }

        entity.setBoolean("dead", true);
        ((EntityLiving) entity).func_110142_aN().func_94549_h();

    }
}
