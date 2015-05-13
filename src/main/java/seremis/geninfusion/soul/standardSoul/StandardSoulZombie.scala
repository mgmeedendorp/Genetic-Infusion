package seremis.geninfusion.soul.standardSoul

import net.minecraft.client.model.ModelZombie
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.entity.passive.EntityVillager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.api.soul.{EnumAlleleType, IChromosome}
import seremis.geninfusion.soul.{Allele, Chromosome}

class StandardSoulZombie extends StandardSoul {

    override def isStandardSoulForEntity(entity: EntityLiving): Boolean = entity.isInstanceOf[EntityZombie]

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        if(gene.equals(Genes.GENE_BURNS_IN_DAYLIGHT))
            return new Chromosome(new Allele(false, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_DEATH_SOUND))
            return new Chromosome(new Allele(true, "mob.zombie.death", EnumAlleleType.STRING))
        if(gene.equals(Genes.GENE_HURT_SOUND))
            return new Chromosome(new Allele(false, "mob.zombie.hurt", EnumAlleleType.STRING))
        if(gene.equals(Genes.GENE_ITEM_DROPS))
            return new Chromosome(new Allele(false, Array(new ItemStack(Items.rotten_flesh)), EnumAlleleType.ITEMSTACK_ARRAY))
        if(gene.equals(Genes.GENE_LIVING_SOUND))
            return new Chromosome(new Allele(false, "mob.zombie.say", EnumAlleleType.STRING))
        if(gene.equals(Genes.GENE_PICKS_UP_ITEMS))
            return new Chromosome(new Allele(false, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_RARE_ITEM_DROPS))
            return new Chromosome(new Allele(false, Array(new ItemStack(Items.iron_ingot), new ItemStack(Items.carrot), new ItemStack(Items.potato)), EnumAlleleType.ITEMSTACK_ARRAY))
        if(gene.equals(Genes.GENE_SET_ON_FIRE_FROM_ATTACK))
            return new Chromosome(new Allele(false, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_SPLASH_SOUND))
            return new Chromosome(new Allele(false, "game.hostile.swim.splash", EnumAlleleType.STRING))
        if(gene.equals(Genes.GENE_SWIM_SOUND))
            return new Chromosome(new Allele(false, "game.hostile.swim", EnumAlleleType.STRING))
        if(gene.equals(Genes.GENE_WALK_SOUND))
            return new Chromosome(new Allele(true, "mob.zombie.step", EnumAlleleType.STRING))
        if(gene.equals(Genes.GENE_USE_NEW_AI))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_USE_OLD_AI))
            return new Chromosome(new Allele(true, false, EnumAlleleType.BOOLEAN))

        if(gene.equals(Genes.GENE_AI_SWIMMING))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_SWIMMING_INDEX))
            return new Chromosome(new Allele(true, 0, EnumAlleleType.INTEGER))

        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_INDEX))
            return new Chromosome(new Allele(true, Array(2, 4), EnumAlleleType.INTEGER_ARRAY))
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY))
            return new Chromosome(new Allele(true, Array(false, true), EnumAlleleType.BOOLEAN_ARRAY))
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED))
            return new Chromosome(new Allele(true, Array(1.0D, 1.0D), EnumAlleleType.DOUBLE_ARRAY))
        if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_TARGET))
            return new Chromosome(new Allele(true, Array(classOf[EntityPlayer], classOf[EntityVillager]), EnumAlleleType.CLASS_ARRAY))

        if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_INDEX))
            return new Chromosome(new Allele(true, 5, EnumAlleleType.INTEGER))
        if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_MOVE_SPEED))
            return new Chromosome(new Allele(false, 1.0D, EnumAlleleType.DOUBLE))

        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_INDEX))
            return new Chromosome(new Allele(true, 6, EnumAlleleType.INTEGER))
        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_IS_NOCTURNAL))
            return new Chromosome(new Allele(false, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_MOVE_SPEED))
            return new Chromosome(new Allele(true, 1.0D, EnumAlleleType.DOUBLE))

        if(gene.equals(Genes.GENE_AI_WANDER))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_WANDER_INDEX))
            return new Chromosome(new Allele(true, 7, EnumAlleleType.INTEGER))
        if(gene.equals(Genes.GENE_AI_WANDER_MOVE_SPEED))
            return new Chromosome(new Allele(true, 1.0D, EnumAlleleType.DOUBLE))

        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_INDEX))
            return new Chromosome(new Allele(true, Array(8), EnumAlleleType.INTEGER_ARRAY))
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_CHANCE))
            return new Chromosome(new Allele(true, Array(0.02F), EnumAlleleType.FLOAT_ARRAY))
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_RANGE))
            return new Chromosome(new Allele(true, Array(8.0F), EnumAlleleType.FLOAT_ARRAY))
        if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_TARGET))
            return new Chromosome(new Allele(true, Array(classOf[EntityPlayer]), EnumAlleleType.CLASS_ARRAY))

        if(gene.equals(Genes.GENE_AI_LOOK_IDLE))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_LOOK_IDLE_INDEX))
            return new Chromosome(new Allele(true, 8, EnumAlleleType.INTEGER))

        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_INDEX))
            return new Chromosome(new Allele(true, 1, EnumAlleleType.INTEGER))
        if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_CALL_HELP))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))

        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET))
            return new Chromosome(new Allele(true, true, EnumAlleleType.BOOLEAN))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX))
            return new Chromosome(new Allele(true, Array(2, 4), EnumAlleleType.INTEGER_ARRAY))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR))
            return new Chromosome(new Allele(true, Array("", ""), EnumAlleleType.STRING_ARRAY))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY))
            return new Chromosome(new Allele(true, Array(false, false), EnumAlleleType.BOOLEAN_ARRAY))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET))
            return new Chromosome(new Allele(false, Array(classOf[EntityPlayer], classOf[EntityVillager]), EnumAlleleType.CLASS_ARRAY))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE))
            return new Chromosome(new Allele(true, Array(0, 0), EnumAlleleType.INTEGER_ARRAY))
        if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE))
            return new Chromosome(new Allele(true, Array(true, false), EnumAlleleType.BOOLEAN_ARRAY))

        if(gene.equals(Genes.GENE_MODEL))
            return new Chromosome(new Allele(true, ModelPart.getModelPartsFromModel(new ModelZombie(), entity), EnumAlleleType.MODELPART_ARRAY))
        if(gene.equals(Genes.GENE_TEXTURE))
            return new Chromosome(new Allele(true, "textures/entity/zombie/zombie.png", EnumAlleleType.STRING), new Allele(false, "textures/entity/zombie/zombie.png", EnumAlleleType.STRING))


        super.getChromosomeFromGene(entity, gene)
    }
}
