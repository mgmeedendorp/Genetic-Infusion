package seremis.geninfusion.soul.traits;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;

public class TraitInitialValues extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        BaseAttributeMap attributeMap = ((EntityLiving) entity).getAttributeMap();

        double maxHealth = SoulHelper.geneRegistry().getValueFromAllele(entity, Genes.GENE_MAX_HEALTH);
        attributeMap.getAttributeInstance(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);

        entity.setFloat("health", (float) maxHealth);

        double attackDamage = SoulHelper.geneRegistry().getValueFromAllele(entity, Genes.GENE_ATTACK_DAMAGE);
        attributeMap.getAttributeInstance(SharedMonsterAttributes.attackDamage).setBaseValue(attackDamage);

        double movementSpeed = SoulHelper.geneRegistry().getValueFromAllele(entity, Genes.GENE_MOVEMENT_SPEED);
        attributeMap.getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(movementSpeed);

        double knockbackResistance = SoulHelper.geneRegistry().getValueFromAllele(entity, Genes.GENE_KNOCKBACK_RESISTANCE);
        attributeMap.getAttributeInstance(SharedMonsterAttributes.knockbackResistance).setBaseValue(knockbackResistance);

        double followRange = SoulHelper.geneRegistry().getValueFromAllele(entity, Genes.GENE_FOLLOW_RANGE);
        attributeMap.getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(followRange);

        entity.setInteger("creatureAttribute", SoulHelper.geneRegistry().<Integer>getValueFromAllele(entity, Genes.GENE_CREATURE_ATTRIBUTE));
        entity.setInteger("experienceValue", SoulHelper.geneRegistry().<Integer>getValueFromAllele(entity, Genes.GENE_EXPERIENCE_VALUE));

        entity.setFloatArray("equipmentDropChances", SoulHelper.geneRegistry().<float[]>getValueFromAllele(entity, Genes.GENE_EQUIPMENT_DROP_CHANCES));

        entity.setBoolean("aiEnabled", SoulHelper.geneRegistry().<Boolean>getValueFromAllele(entity, Genes.GENE_USE_NEW_AI));
    }
}
