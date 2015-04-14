package seremis.geninfusion.soul.standardSoul

import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.EntityCreeper
import seremis.geninfusion.api.soul.IChromosome

class StandardSoulCreeper extends StandardSoul {

    override def isStandardSoulForEntity(entity: EntityLiving): Boolean = {
        entity.isInstanceOf[EntityCreeper]
    }

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {
        

        super.getChromosomeFromGene(entity, gene)
    }
}
