package seremis.geninfusion.soul.traits;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.soul.Trait;

/**
 * @author Seremis
 */
public class TraitAttack extends Trait {

    @Override
    public boolean attackEntityAsMob(IEntitySoulCustom entity, Entity entityToAttack) {
        float f = (float)entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int i = 0;

        if (entityToAttack instanceof EntityLivingBase) {
            f += EnchantmentHelper.getEnchantmentModifierLiving((EntityLivingBase) entity, (EntityLivingBase) entityToAttack);
            i += EnchantmentHelper.getKnockbackModifier((EntityLivingBase) entity, (EntityLivingBase)entityToAttack);
        }

        boolean flag = entityToAttack.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) entity), f);

        if (flag) {
            if (i > 0) {
                float rotationYaw = entity.getPersistentFloat("rotationYaw");

                entityToAttack.addVelocity((double)(-MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                entity.setPersistentVariable("motionX", entity.getPersistentDouble("motionX")*0.6D);
                entity.setPersistentVariable("motionZ", entity.getPersistentDouble("motionZ")*0.6D);
            }

            int j = EnchantmentHelper.getFireAspectModifier((EntityLivingBase) entity);

            if (j > 0) {
                entityToAttack.setFire(j * 4);
            }

            if (entityToAttack instanceof EntityLivingBase) {
                EnchantmentHelper.func_151384_a((EntityLivingBase)entityToAttack, (Entity) entity);
            }

            EnchantmentHelper.func_151385_b((EntityLivingBase) entity, entityToAttack);
        }

        return flag;
    }
}