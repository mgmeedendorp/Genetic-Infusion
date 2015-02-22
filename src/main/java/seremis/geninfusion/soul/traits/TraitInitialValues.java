package seremis.geninfusion.soul.traits;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.soul.allele.AlleleDouble;
import seremis.geninfusion.soul.allele.AlleleFloat;
import seremis.geninfusion.soul.allele.AlleleInteger;

public class TraitInitialValues extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        BaseAttributeMap attributeMap = ((EntityLiving) entity).getAttributeMap();

        float maxHealth = SoulHelper.geneRegistry.getValueFloat(entity, Genes.GENE_MAX_HEALTH);
        attributeMap.getAttributeInstance(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);

        entity.setFloat("health", maxHealth);

        float attackDamage = SoulHelper.geneRegistry.getValueFloat(entity, Genes.GENE_ATTACK_DAMAGE);
        attributeMap.getAttributeInstance(SharedMonsterAttributes.attackDamage).setBaseValue(attackDamage);

        double movementSpeed = SoulHelper.geneRegistry.getValueDouble(entity, Genes.GENE_MOVEMENT_SPEED);
        attributeMap.getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(movementSpeed);

        float knockbackResistance = SoulHelper.geneRegistry.getValueFloat(entity, Genes.GENE_KNOCKBACK_RESISTANCE);
        attributeMap.getAttributeInstance(SharedMonsterAttributes.knockbackResistance).setBaseValue(knockbackResistance);

        float followRange = SoulHelper.geneRegistry.getValueFloat(entity, Genes.GENE_FOLLOW_RANGE);
        attributeMap.getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(followRange);

        entity.setInteger("creatureAttribute", SoulHelper.geneRegistry.getValueInteger(entity, Genes.GENE_CREATURE_ATTRIBUTE));
        entity.setInteger("experienceValue", SoulHelper.geneRegistry.getValueInteger(entity, Genes.GENE_EXPERIENCE_VALUE));

        entity.setFloatArray("equipmentDropChances", SoulHelper.geneRegistry.getValueFloatArray(entity, Genes.GENE_EQUIPMENT_DROP_CHANCES));

        entity.setBoolean("aiEnabled", SoulHelper.geneRegistry.getValueBoolean(entity, Genes.GENE_USE_NEW_AI));

        if(!entity.getBoolean("aiEnabled")) {
            attributeMap.getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612D);
        }
    }
}
