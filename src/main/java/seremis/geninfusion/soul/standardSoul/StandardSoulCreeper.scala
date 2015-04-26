package seremis.geninfusion.soul.standardSoul

import net.minecraft.client.model.ModelCreeper
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.EntityCreeper
import net.minecraft.entity.passive.EntityOcelot
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.api.soul.{EnumAlleleType, IChromosome}
import seremis.geninfusion.soul.{Allele, Chromosome}

class StandardSoulCreeper extends StandardSoul {

    override def isStandardSoulForEntity(entity: EntityLiving): Boolean = {
        entity.isInstanceOf[EntityCreeper]
    }

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        if(gene == Genes.GENE_DEATH_SOUND)
            return new Chromosome(new Allele(true, "mob.creeper.death", EnumAlleleType.STRING))
        if(gene == Genes.GENE_HURT_SOUND)
            return new Chromosome(new Allele(true, "mob.creeper.say", EnumAlleleType.STRING))
        if(gene == Genes.GENE_ITEM_DROPS)
            return new Chromosome(new Allele(true, Array(new ItemStack(Items.gunpowder)), EnumAlleleType.ITEMSTACK_ARRAY))
        if(gene == Genes.GENE_PICKS_UP_ITEMS)
            return new Chromosome(new Allele(true, false, EnumAlleleType.BOOLEAN))
        if(gene == Genes.GENE_RARE_ITEM_DROP_CHANCES)
            return new Chromosome(new Allele(true, Array(0.0F), EnumAlleleType.FLOAT_ARRAY))
        if(gene == Genes.GENE_RARE_ITEM_DROPS)
            return new Chromosome(new Allele(true, Array(null.asInstanceOf[ItemStack]), EnumAlleleType.ITEMSTACK_ARRAY))
        if(gene == Genes.GENE_SPLASH_SOUND)
            return new Chromosome(new Allele(true, "game.hostile.swim.splash", EnumAlleleType.STRING))
        if(gene == Genes.GENE_SWIM_SOUND)
            return new Chromosome(new Allele(true, "game.hostile.swim", EnumAlleleType.STRING))
        if(gene == Genes.GENE_USE_NEW_AI)
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene == Genes.GENE_USE_OLD_AI)
            return new Chromosome(new Allele(true, false, EnumAlleleType.BOOLEAN))
        if(gene == Genes.GENE_CAN_BE_CHARGED)
            return new Chromosome(new Allele(false, true, EnumAlleleType.BOOLEAN))

        //AI
        if(gene == Genes.GENE_AI_SWIMMING)
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene == Genes.GENE_AI_SWIMMING_INDEX)
            return new Chromosome(new Allele(false, 1, EnumAlleleType.INTEGER))

        if(gene == Genes.GENE_AI_CREEPER_SWELL)
            return new Chromosome(new Allele(false, true, EnumAlleleType.BOOLEAN))
        if(gene == Genes.GENE_AI_CREEPER_SWELL_INDEX)
            return new Chromosome(new Allele(true, 2, EnumAlleleType.INTEGER))

        if(gene == Genes.GENE_AI_AVOID_ENTITY)
            return new Chromosome(new Allele(false, true, EnumAlleleType.BOOLEAN))
        if(gene == Genes.GENE_AI_AVOID_ENTITY_TARGET)
            return new Chromosome(new Allele(false, Array(classOf[EntityOcelot]), EnumAlleleType.CLASS_ARRAY))

        if(gene == Genes.GENE_AI_ATTACK_ON_COLLIDE)
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene == Genes.GENE_AI_ATTACK_ON_COLLIDE_INDEX)
            return new Chromosome(new Allele(false, Array(4), EnumAlleleType.INTEGER_ARRAY))
        if(gene == Genes.GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY)
            return new Chromosome(new Allele(false, Array(false), EnumAlleleType.BOOLEAN_ARRAY))
        if(gene == Genes.GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED)
            return new Chromosome(new Allele(false, Array(1.0D), EnumAlleleType.DOUBLE_ARRAY))
        if(gene == Genes.GENE_AI_ATTACK_ON_COLLIDE_TARGET)
            return new Chromosome(new Allele(false, Array(null.asInstanceOf[Class[_]]), EnumAlleleType.CLASS_ARRAY))

        if(gene == Genes.GENE_AI_WANDER)
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene == Genes.GENE_AI_WANDER_INDEX)
            return new Chromosome(new Allele(true, 5, EnumAlleleType.INTEGER))
        if(gene == Genes.GENE_AI_WANDER_MOVE_SPEED)
            return new Chromosome(new Allele(false, 0.8D, EnumAlleleType.DOUBLE))

        if(gene == Genes.GENE_AI_WATCH_CLOSEST)
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene == Genes.GENE_AI_WATCH_CLOSEST_INDEX)
            return new Chromosome(new Allele(true, Array(6), EnumAlleleType.INTEGER_ARRAY))
        if(gene == Genes.GENE_AI_WATCH_CLOSEST_CHANCE)
            return new Chromosome(new Allele(true, Array(0.02F), EnumAlleleType.FLOAT_ARRAY))
        if(gene == Genes.GENE_AI_WATCH_CLOSEST_RANGE)
            return new Chromosome(new Allele(true, Array(8.0F), EnumAlleleType.FLOAT_ARRAY))
        if(gene == Genes.GENE_AI_WATCH_CLOSEST_TARGET)
            return new Chromosome(new Allele(true, Array(classOf[EntityPlayer]), EnumAlleleType.CLASS_ARRAY))

        if(gene == Genes.GENE_AI_LOOK_IDLE)
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene == Genes.GENE_AI_LOOK_IDLE_INDEX)
            return new Chromosome(new Allele(true, 6, EnumAlleleType.INTEGER))

        if(gene == Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET)
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene == Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX)
            return new Chromosome(new Allele(false, Array(1), EnumAlleleType.INTEGER_ARRAY))
        if(gene == Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR)
            return new Chromosome(new Allele(true, Array(""), EnumAlleleType.STRING_ARRAY))
        if(gene == Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY)
            return new Chromosome(new Allele(true, Array(false), EnumAlleleType.BOOLEAN_ARRAY))
        if(gene == Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET)
            return new Chromosome(new Allele(false, Array(classOf[EntityPlayer]), EnumAlleleType.CLASS_ARRAY))
        if(gene == Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE)
            return new Chromosome(new Allele(true, Array(0), EnumAlleleType.INTEGER_ARRAY))
        if(gene == Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE)
            return new Chromosome(new Allele(true, Array(true), EnumAlleleType.BOOLEAN_ARRAY))

        if(gene == Genes.GENE_AI_HURT_BY_TARGET)
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene == Genes.GENE_AI_HURT_BY_TARGET_INDEX)
            return new Chromosome(new Allele(true, 2, EnumAlleleType.INTEGER))
        if(gene == Genes.GENE_AI_HURT_BY_TARGET_CALL_HELP)
            return new Chromosome(new Allele(true, false, EnumAlleleType.BOOLEAN))

        //Rendering related Genes.
        if(gene == Genes.GENE_MODEL)
            return new Chromosome(new Allele(true, ModelPart.getModelPartsFromModel(new ModelCreeper(), entity), EnumAlleleType.MODELPART_ARRAY))
        if(gene == Genes.GENE_TEXTURE)
            return new Chromosome(new Allele(true, "textures/entity/creeper/creeper.png", EnumAlleleType.STRING), new Allele(false, "textures/entity/creeper/creeper.png", EnumAlleleType.STRING))

        super.getChromosomeFromGene(entity, gene)
    }
}
