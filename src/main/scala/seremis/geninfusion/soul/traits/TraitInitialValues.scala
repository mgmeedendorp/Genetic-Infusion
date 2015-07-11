package seremis.geninfusion.soul.traits

import net.minecraft.entity.{EntityLiving, SharedMonsterAttributes}
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitInitialValues extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        val attributeMap = entity.asInstanceOf[EntityLiving].getAttributeMap

        val maxHealth: Double = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneMaxHealth)
        attributeMap.getAttributeInstance(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth)

        entity.setFloat(EntityHealth, maxHealth.toFloat)

        val attackDamage: Double = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneAttackDamage)
        attributeMap.getAttributeInstance(SharedMonsterAttributes.attackDamage).setBaseValue(attackDamage)

        val movementSpeed: Double = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneMovementSpeed)
        attributeMap.getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(movementSpeed)

        val knockbackResistance: Double = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneKnockBackResistance)
        attributeMap.getAttributeInstance(SharedMonsterAttributes.knockbackResistance).setBaseValue(knockbackResistance)

        val followRange: Double = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneFollowRange)
        attributeMap.getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(followRange)

        val width: Float = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneWidth)
        val height: Float = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneHeight)

        entity.setSize(width, height)

        entity.setInteger(EntityCreatureAttribute, SoulHelper.geneRegistry.getValueFromAllele[Integer](entity, Genes.GeneCreatureAttribute))
        entity.setInteger(EntityExperienceValue, SoulHelper.geneRegistry.getValueFromAllele[Integer](entity, Genes.GeneExperienceValue))

        entity.setFloatArray(EntityEquipmentDropChances, SoulHelper.geneRegistry.getValueFromAllele[Array[Float]](entity, Genes.GeneEquipmentDropChances))

        entity.setBoolean(EntityAIEnabled, SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneUseNewAI))
    }
}