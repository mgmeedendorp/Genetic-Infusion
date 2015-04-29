package seremis.geninfusion.soul.standardSoul

import net.minecraft.client.model.ModelCreeper
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.{EntitySkeleton, EntityCreeper}
import net.minecraft.entity.passive.EntityOcelot
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import seremis.geninfusion.api.soul.EnumAlleleType._
import seremis.geninfusion.api.soul.IChromosome
import seremis.geninfusion.api.soul.lib.Genes._
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.soul.{Allele, Chromosome}

class StandardSoulCreeper extends StandardSoul {

    override def isStandardSoulForEntity(entity: EntityLiving): Boolean = {
        entity.isInstanceOf[EntityCreeper]
    }

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        if(gene == GENE_DEATH_SOUND)
            return new Chromosome(new Allele(true, "mob.creeper.death", STRING))
        if(gene == GENE_HURT_SOUND)
            return new Chromosome(new Allele(true, "mob.creeper.say", STRING))
        if(gene == GENE_ITEM_DROPS)
            return new Chromosome(new Allele(true, Array(new ItemStack(Items.gunpowder)), ITEMSTACK_ARRAY))
        if(gene == GENE_PICKS_UP_ITEMS)
            return new Chromosome(new Allele(true, false, BOOLEAN))
        if(gene == GENE_RARE_ITEM_DROP_CHANCES)
            return new Chromosome(new Allele(true, Array(0.0F), FLOAT_ARRAY))
        if(gene == GENE_RARE_ITEM_DROPS)
            return new Chromosome(new Allele(true, null, ITEMSTACK_ARRAY))
        if(gene == GENE_SPLASH_SOUND)
            return new Chromosome(new Allele(true, "game.hostile.swim.splash", STRING))
        if(gene == GENE_SWIM_SOUND)
            return new Chromosome(new Allele(true, "game.hostile.swim", STRING))
        if(gene == GENE_USE_NEW_AI)
            return new Chromosome(new Allele(true, true, BOOLEAN))
        if(gene == GENE_USE_OLD_AI)
            return new Chromosome(new Allele(true, false, BOOLEAN))
        if(gene == GENE_CAN_BE_CHARGED)
            return new Chromosome(new Allele(false, true, BOOLEAN))

        //AI
        if(gene == GENE_AI_SWIMMING)
            return new Chromosome(new Allele(true, true, BOOLEAN))
        if(gene == GENE_AI_SWIMMING_INDEX)
            return new Chromosome(new Allele(false, 1, INTEGER))

        if(gene == GENE_AI_CREEPER_SWELL)
            return new Chromosome(new Allele(false, true, BOOLEAN))
        if(gene == GENE_AI_CREEPER_SWELL_INDEX)
            return new Chromosome(new Allele(true, 2, INTEGER))

        if(gene == GENE_AI_AVOID_ENTITY)
            return new Chromosome(new Allele(false, true, BOOLEAN))
        if(gene == GENE_AI_AVOID_ENTITY_TARGET)
            return new Chromosome(new Allele(false, Array(classOf[EntityOcelot]), CLASS_ARRAY))

        if(gene == GENE_AI_ATTACK_ON_COLLIDE)
            return new Chromosome(new Allele(true, true, BOOLEAN))
        if(gene == GENE_AI_ATTACK_ON_COLLIDE_INDEX)
            return new Chromosome(new Allele(false, Array(4), INTEGER_ARRAY))
        if(gene == GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY)
            return new Chromosome(new Allele(false, Array(false), BOOLEAN_ARRAY))
        if(gene == GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED)
            return new Chromosome(new Allele(false, Array(1.0D), DOUBLE_ARRAY))
        if(gene == GENE_AI_ATTACK_ON_COLLIDE_TARGET)
            return new Chromosome(new Allele(false, null, CLASS_ARRAY))

        if(gene == GENE_AI_WANDER)
            return new Chromosome(new Allele(true, true, BOOLEAN))
        if(gene == GENE_AI_WANDER_INDEX)
            return new Chromosome(new Allele(true, 5, INTEGER))
        if(gene == GENE_AI_WANDER_MOVE_SPEED)
            return new Chromosome(new Allele(false, 0.8D, DOUBLE))

        if(gene == GENE_AI_WATCH_CLOSEST)
            return new Chromosome(new Allele(true, true, BOOLEAN))
        if(gene == GENE_AI_WATCH_CLOSEST_INDEX)
            return new Chromosome(new Allele(true, Array(6), INTEGER_ARRAY))
        if(gene == GENE_AI_WATCH_CLOSEST_CHANCE)
            return new Chromosome(new Allele(true, Array(0.02F), FLOAT_ARRAY))
        if(gene == GENE_AI_WATCH_CLOSEST_RANGE)
            return new Chromosome(new Allele(true, Array(8.0F), FLOAT_ARRAY))
        if(gene == GENE_AI_WATCH_CLOSEST_TARGET)
            return new Chromosome(new Allele(true, Array(classOf[EntityPlayer]), CLASS_ARRAY))

        if(gene == GENE_AI_LOOK_IDLE)
            return new Chromosome(new Allele(true, true, BOOLEAN))
        if(gene == GENE_AI_LOOK_IDLE_INDEX)
            return new Chromosome(new Allele(true, 6, INTEGER))

        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET)
            return new Chromosome(new Allele(true, true, BOOLEAN))
        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX)
            return new Chromosome(new Allele(false, Array(1), INTEGER_ARRAY))
        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR)
            return new Chromosome(new Allele(true, Array(""), STRING_ARRAY))
        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY)
            return new Chromosome(new Allele(true, Array(false), BOOLEAN_ARRAY))
        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET)
            return new Chromosome(new Allele(false, Array(classOf[EntityPlayer]), CLASS_ARRAY))
        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE)
            return new Chromosome(new Allele(true, Array(0), INTEGER_ARRAY))
        if(gene == GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE)
            return new Chromosome(new Allele(true, Array(true), BOOLEAN_ARRAY))

        if(gene == GENE_AI_HURT_BY_TARGET)
            return new Chromosome(new Allele(true, true, BOOLEAN))
        if(gene == GENE_AI_HURT_BY_TARGET_INDEX)
            return new Chromosome(new Allele(true, 2, INTEGER))
        if(gene == GENE_AI_HURT_BY_TARGET_CALL_HELP)
            return new Chromosome(new Allele(true, false, BOOLEAN))

        //Rendering related Genes.
        if(gene == GENE_MODEL)
            return new Chromosome(new Allele(true, ModelPart.getModelPartsFromModel(new ModelCreeper(), entity), MODELPART_ARRAY))
        if(gene == GENE_TEXTURE)
            return new Chromosome(new Allele(true, "textures/entity/creeper/creeper.png", STRING), new Allele(false, "textures/entity/creeper/creeper.png", STRING))

        //Explosion related genes
        if(gene == GENE_EXPLODES)
            return new Chromosome(new Allele(false, true, BOOLEAN))

        if(gene == GENE_DROPS_ITEM_WHEN_KILLED_BY_SPECIFIC_ENTITY)
            return new Chromosome(new Allele(false, true, BOOLEAN))
        if(gene == GENE_KILLED_BY_SPECIFIC_ENTITY_DROPS)
            return new Chromosome(new Allele(true, Array(Items.record_11, Items.record_13, Items.record_blocks, Items.record_cat, Items.record_chirp, Items.record_far, Items.record_mall, Items.record_mellohi, Items.record_stal, Items.record_strad, Items.record_wait, Items.record_ward).map(b => new ItemStack(b)), ITEMSTACK_ARRAY))
        if(gene == GENE_KILLED_BY_SPECIFIC_ENTITY_ENTITY)
            return new Chromosome(new Allele(true, classOf[EntitySkeleton], CLASS))

        super.getChromosomeFromGene(entity, gene)
    }
}
