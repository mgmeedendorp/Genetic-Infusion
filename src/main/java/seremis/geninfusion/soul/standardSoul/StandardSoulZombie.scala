package seremis.geninfusion.soul.standardSoul

import net.minecraft.client.model.ModelZombie
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.entity.passive.EntityVillager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import seremis.geninfusion.api.soul.IChromosome
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.soul.Chromosome
import seremis.geninfusion.soul.allele._

class StandardSoulZombie extends StandardSoul {

    override def isStandardSoulForEntity(entity: EntityLiving): Boolean = entity.isInstanceOf[EntityZombie]

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        if(gene.equals(Genes.GENE_BURNS_IN_DAYLIGHT))
            return new Chromosome(new AlleleBoolean(false, true))
        if(gene.equals(Genes.GENE_DEATH_SOUND))
            return new Chromosome(new AlleleString(true, "mob.zombie.death"))
        if(gene.equals(Genes.GENE_HURT_SOUND))
            return new Chromosome(new AlleleString(false, "mob.zombie.hurt"))
        if(gene.equals(Genes.GENE_ITEM_DROPS))
            return new Chromosome(new AlleleInventory(false, Array(new ItemStack(Items.rotten_flesh))))
        if(gene.equals(Genes.GENE_LIVING_SOUND))
            return new Chromosome(new AlleleString(false, "mob.zombie.say"))
        if(gene.equals(Genes.GENE_PICKS_UP_ITEMS))
            return new Chromosome(new AlleleBoolean(false, true))
        if(gene.equals(Genes.GENE_RARE_ITEM_DROPS))
            return new Chromosome(new AlleleInventory(false, Array(new ItemStack(Items.iron_ingot), new ItemStack(Items.carrot), new ItemStack(Items.potato))))
        if(gene.equals(Genes.GENE_SET_ON_FIRE_FROM_ATTACK))
            return new Chromosome(new AlleleBoolean(false, true))
        if(gene.equals(Genes.GENE_SPLASH_SOUND))
            return new Chromosome(new AlleleString(false, "game.hostile.swim.splash"))
        if(gene.equals(Genes.GENE_SWIM_SOUND))
            return new Chromosome(new AlleleString(false, "game.hostile.swim"))
        if(gene.equals(Genes.GENE_WALK_SOUND))
            return new Chromosome(new AlleleString(true, "mob.zombie.step"))
        if(gene.equals(Genes.GENE_USE_NEW_AI))
            return new Chromosome(new AlleleBoolean(true, true))
        if(gene.equals(Genes.GENE_USE_OLD_AI))
            return new Chromosome(new AlleleBoolean(true, false))

        if(gene.equals(Genes.GENE_AI_SWIMMING))
            return new Chromosome(new AlleleBoolean(true, true))
        if(gene.equals(Genes.GENE_AI_SWIMMING_INDEX))
            return new Chromosome(new AlleleInteger(true, 0))

        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE))
            return new Chromosome(new AlleleBoolean(true, true))
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_INDEX))
            return new Chromosome(new AlleleIntArray(true, Array(2, 4)))
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY))
            return new Chromosome(new AlleleBooleanArray(true, Array(false, true)))
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED))
            return new Chromosome(new AlleleDoubleArray(true, Array(1.0D, 1.0D)))
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_TARGET))
            return new Chromosome(new AlleleClassArray(true, Array(classOf[EntityPlayer], classOf[EntityVillager])))

        if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION))
            return new Chromosome(new AlleleBoolean(true, true))
        if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_INDEX))
            return new Chromosome(new AlleleInteger(true, 5))
        if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_MOVE_SPEED))
            return new Chromosome(new AlleleDouble(false, 1.0D))

        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE))
            return new Chromosome(new AlleleBoolean(true, true))
        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_INDEX))
            return new Chromosome(new AlleleInteger(true, 6))
        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_IS_NOCTURNAL))
            return new Chromosome(new AlleleBoolean(false, true))
        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_MOVE_SPEED))
            return new Chromosome(new AlleleDouble(true, 1.0D))

        if(gene.equals(Genes.GENE_AI_WANDER))
            return new Chromosome(new AlleleBoolean(true, true))
        if(gene.equals(Genes.GENE_AI_WANDER_INDEX))
            return new Chromosome(new AlleleInteger(true, 7))
        if(gene.equals(Genes.GENE_AI_WANDER_MOVE_SPEED))
            return new Chromosome(new AlleleDouble(true, 1.0D))

        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST))
            return new Chromosome(new AlleleBoolean(true, true))
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_INDEX))
            return new Chromosome(new AlleleIntArray(true, Array(8)))
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_CHANCE))
            return new Chromosome(new AlleleFloatArray(true, Array(0.02F)))
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_RANGE))
            return new Chromosome(new AlleleFloatArray(true, Array(8.0F)))
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_TARGET))
            return new Chromosome(new AlleleClassArray(true, Array(classOf[EntityPlayer])))

        if(gene.equals(Genes.GENE_AI_LOOK_IDLE))
            return new Chromosome(new AlleleBoolean(true, true))
        if(gene.equals(Genes.GENE_AI_LOOK_IDLE_INDEX))
            return new Chromosome(new AlleleInteger(true, 8))

        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET))
            return new Chromosome(new AlleleBoolean(true, true))
        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_INDEX))
            return new Chromosome(new AlleleInteger(true, 1))
        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_CALL_HELP))
            return new Chromosome(new AlleleBoolean(true, true))

        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET))
            return new Chromosome(new AlleleBoolean(true, true))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX))
            return new Chromosome(new AlleleIntArray(true, Array(2, 4)))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR))
            return new Chromosome(new AlleleStringArray(true, Array("", "")))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY))
            return new Chromosome(new AlleleBooleanArray(true, Array(false, false)))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET))
            return new Chromosome(new AlleleClassArray(false, Array(classOf[EntityPlayer], classOf[EntityVillager])))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE))
            return new Chromosome(new AlleleIntArray(true, Array(0, 0)))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE))
            return new Chromosome(new AlleleBooleanArray(true, Array(true, false)))

        if(gene.equals(Genes.GENE_MODEL))
            return new Chromosome(new AlleleModelPartArray(true, ModelPart.getModelPartsFromModel(new ModelZombie(), entity)))
        if(gene.equals(Genes.GENE_TEXTURE))
            return new Chromosome(new AlleleString(true, "textures/entity/zombie/zombie.png"), new AlleleString(false, "textures/entity/zombie/zombie.png"))


        return super.getChromosomeFromGene(entity, gene)
    }
}
