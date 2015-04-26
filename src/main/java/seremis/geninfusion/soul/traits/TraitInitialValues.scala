package seremis.geninfusion.soul.traits

import net.minecraft.entity.{EntityLiving, SharedMonsterAttributes}
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitInitialValues extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        val attributeMap = entity.asInstanceOf[EntityLiving].getAttributeMap

        val maxHealth: Double = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GENE_MAX_HEALTH)
        attributeMap.getAttributeInstance(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth)

        entity.setFloat("health", maxHealth.toFloat)

        val attackDamage: Double = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GENE_ATTACK_DAMAGE)
        attributeMap.getAttributeInstance(SharedMonsterAttributes.attackDamage).setBaseValue(attackDamage)

        val movementSpeed: Double = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GENE_MOVEMENT_SPEED)
        attributeMap.getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(movementSpeed)

        val knockbackResistance: Double = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GENE_KNOCKBACK_RESISTANCE)
        attributeMap.getAttributeInstance(SharedMonsterAttributes.knockbackResistance).setBaseValue(knockbackResistance)

        val followRange: Double = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GENE_FOLLOW_RANGE)
        attributeMap.getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(followRange)

        entity.setInteger("creatureAttribute", SoulHelper.geneRegistry.getValueFromAllele[Integer](entity, Genes.GENE_CREATURE_ATTRIBUTE))
        entity.setInteger("experienceValue", SoulHelper.geneRegistry.getValueFromAllele[Integer](entity, Genes.GENE_EXPERIENCE_VALUE))

        entity.setFloatArray("equipmentDropChances", SoulHelper.geneRegistry.getValueFromAllele[Array[Float]](entity, Genes.GENE_EQUIPMENT_DROP_CHANCES))

        entity.setBoolean("aiEnabled", SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_USE_NEW_AI))
    }
}