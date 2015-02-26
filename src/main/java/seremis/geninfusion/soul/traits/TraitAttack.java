package seremis.geninfusion.soul.traits;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;

public class TraitAttack extends Trait {

    @Override
    public void attackEntity(IEntitySoulCustom entity, Entity entityToAttack, float distance) {
        int attackTime = entity.getInteger("attackTime");

        if(attackTime <= 0 && distance < 2.0F && entityToAttack.boundingBox.maxY > entity.getBoundingBox().minY && entityToAttack.boundingBox.minY < entity.getBoundingBox().maxY) {
            attackTime = 20;
            entity.attackEntityAsMob(entityToAttack);
        }

        entity.setInteger("attackTime", attackTime);
    }

    @Override
    public boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack) {
        EntityLiving living = (EntityLiving) entity;

        boolean touchAttack = SoulHelper.geneRegistry.getValueBoolean(entity, Genes.GENE_TOUCH_ATTACK);

        if(touchAttack) {
            float f = (float) living.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            int i = 0;

            if(entityToAttack instanceof EntityLivingBase) {
                f += EnchantmentHelper.getEnchantmentModifierLiving(living, (EntityLivingBase) entityToAttack);
                i += EnchantmentHelper.getKnockbackModifier(living, (EntityLivingBase) entityToAttack);
            }

            boolean flag = entityToAttack.attackEntityFrom(DamageSource.causeMobDamage(living), f);

            if(flag) {
                if(i > 0) {
                    entityToAttack.addVelocity((double) (-MathHelper.sin(living.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F), 0.1D, (double) (MathHelper.cos(living.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F));
                    living.motionX *= 0.6D;
                    living.motionZ *= 0.6D;
                }

                int j = EnchantmentHelper.getFireAspectModifier(living);

                if(j > 0) {
                    entityToAttack.setFire(j * 4);
                }

                if(entityToAttack instanceof EntityLivingBase) {
                    EnchantmentHelper.func_151384_a((EntityLivingBase) entityToAttack, living);
                }

                EnchantmentHelper.func_151385_b(living, entityToAttack);
            }
            return flag;
        }

        living.setLastAttacker(entityToAttack);
        return false;
    }
}
