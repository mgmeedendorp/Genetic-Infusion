package seremis.soulcraft.soul.handler;

import java.util.Random;

import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.soul.EnumChromosome;
import seremis.soulcraft.soul.SoulHandler;
import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleFloat;
import seremis.soulcraft.soul.allele.AlleleInteger;
import seremis.soulcraft.soul.allele.AlleleString;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeHooks;

public class ChromosomeHandlerAttacked extends EntityEventHandler {

    private Random rand = new Random();
    
    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        if(entity.getHealth() <= 0) {
            entity.onDeathUpdate();
        }
        
        if (entity.getAttackTime() > 0) {
            entity.setAttackTime(entity.getAttackTime()-1);;
        }

        if (entity.getHurtTime() > 0) {
            entity.setHurtTime(entity.getHurtTime()-1);;
        }
        
        if (entity.getHurtResistantTime() > 0) {
            entity.setHurtResistantTime(entity.getHurtResistantTime()-1);;
        }
        
        if (entity.getRecentlyHit() > 0) {
            entity.setRecentlyHit(entity.getRecentlyHit()-1);;
        } else {
            entity.setRevengePlayer(null);
        }
    }

    @Override
    public boolean onEntityAttacked(IEntitySoulCustom entity, DamageSource source, float damage) {
        if(CommonProxy.proxy.isServerWorld(entity.getWorld())) {
            entity.setAge(0);
            
            boolean isInvulnerable = ((AlleleBoolean)SoulHandler.getChromosomeFrom(entity, EnumChromosome.INVULNERABLE).getActive()).value;
            
            if(isInvulnerable) {
                return false;
            } else if (entity.getHealth() <= 0.0F) {
                return false;
            } else if (source.isFireDamage() && entity.isPotionActive(Potion.fireResistance)) {
                return false;
            } else {
                if ((source == DamageSource.anvil || source == DamageSource.fallingBlock) && entity.getCurrentItemOrArmor(4) != null) {
                    entity.getCurrentItemOrArmor(4).damageItem((int)(damage * 4.0F + this.rand.nextFloat() * damage * 2.0F), (EntityLiving) entity);
                    damage *= 0.75F;
                }
                
                entity.setLimbSwingAmount(1.5F);
                boolean flag = true;

                int maxHurtTime = ((AlleleInteger)SoulHandler.getChromosomeFrom(entity, EnumChromosome.MAX_HURT_TIME).getActive()).value;
                int maxHurtResistantTime = ((AlleleInteger)SoulHandler.getChromosomeFrom(entity, EnumChromosome.MAX_HURT_RESISTANT_TIME).getActive()).value;

                if ((float)entity.getHurtResistantTime() > maxHurtResistantTime / 2.0F) {
                    if (damage <= entity.getLastDamage()) {
                        return false;
                    }

                    entity.damageEntity(source, damage - entity.getLastDamage());
                    entity.setLastDamage(damage);
                    flag = false;
                } else {
                    entity.setLastDamage(damage);
                    entity.setPrevHealth(entity.getHealth());
                    entity.setHurtResistantTime(maxHurtResistantTime);
                    entity.damageEntity(source, damage);
                    entity.setHurtTime(maxHurtTime);
                }
                
                Entity attacker = source.getEntity();

                if (attacker != null) {
                    if (attacker instanceof EntityLivingBase) {
                        entity.setRevengeTarget((EntityLivingBase)attacker);
                    }

                    if (attacker instanceof EntityPlayer) {
                        entity.setRecentlyHit(100);
                        entity.setRevengePlayer((EntityPlayer)attacker);
                    } else if (attacker instanceof EntityWolf) {
                        EntityWolf entitywolf = (EntityWolf)attacker;

                        if (entitywolf.isTamed()) {
                            entity.setRecentlyHit(100);
                            entity.setRevengePlayer(null);
                        }
                    }
                }

                if (flag) {
                    entity.getWorld().setEntityState((EntityLiving) entity, (byte)2);

                    if (source != DamageSource.drown) {
                        entity.setBeenAttacked();
                    }

                    if (attacker != null) {
                        double d0 = attacker.posX - entity.getPosX();
                        double d1;

                        for (d1 = attacker.posZ - entity.getPosZ(); d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
                            d0 = (Math.random() - Math.random()) * 0.01D;
                        }

                        entity.setAttackedAtYaw((float)(Math.atan2(d1, d0) * 180.0D / Math.PI) - entity.getRotationYaw());
                        entity.knockBack(attacker, damage, d0, d1);
                    } else {
                        entity.setAttackedAtYaw((float)((int)(Math.random() * 2.0D) * 180));
                    }
                }

                if (entity.getHealth() <= 0.0F) {
                    if (flag) {
                        String deathSound = ((AlleleString)SoulHandler.getChromosomeFrom(entity, EnumChromosome.DEATH_SOUND).getActive()).value;
                        float volume = ((AlleleFloat)SoulHandler.getChromosomeFrom(entity, EnumChromosome.SOUND_VOLUME).getActive()).value;
                        entity.playSound(deathSound, volume, entity.getSoundPitch());
                    }

                    entity.onDeath(source);
                } else if (flag) {
                    String hurtSound = ((AlleleString)SoulHandler.getChromosomeFrom(entity, EnumChromosome.HURT_SOUND).getActive()).value;
                    float volume = ((AlleleFloat)SoulHandler.getChromosomeFrom(entity, EnumChromosome.SOUND_VOLUME).getActive()).value;
                    entity.playSound(hurtSound, volume, entity.getSoundPitch());
                }
                
                return true;
            }
        } else {
            return false;
        }
    }
    
    @Override
    public void damageEntity(IEntitySoulCustom entity, DamageSource source, float damage) {
        boolean isInvulnerable = ((AlleleBoolean)SoulHandler.getChromosomeFrom(entity, EnumChromosome.INVULNERABLE).getActive()).value;

        if(!isInvulnerable) {
            damage = ForgeHooks.onLivingHurt((EntityLiving) entity, source, damage);
            if (damage <= 0) return;
            damage = entity.applyArmorCalculations(source, damage);
            damage = entity.applyPotionDamageCalculations(source, damage);
            float f1 = damage;
            damage = Math.max(damage - entity.getAbsorptionAmount(), 0.0F);
            entity.setAbsorptionAmount(entity.getAbsorptionAmount() - (f1 - damage));
 
            if (damage != 0.0F) {
                float health = entity.getHealth();
                entity.setHealth(health - damage);
                entity.getCombatTracker().func_94547_a(source, health, damage);
                entity.setAbsorptionAmount(entity.getAbsorptionAmount() - damage);
            }
        }
    }
}
