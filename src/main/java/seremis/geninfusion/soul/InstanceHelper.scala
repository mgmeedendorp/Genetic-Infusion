package seremis.geninfusion.soul

import net.minecraft.world.World
import seremis.geninfusion.api.soul._
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.soul.entity.{EntitySoulCustom, EntitySoulCustomCreature}

class InstanceHelper extends IInstanceHelper {

    override def getSoulEntityInstance(world: World, soul: ISoul, x: Double, y: Double, z: Double): IEntitySoulCustom = {
        var entity: IEntitySoulCustom = new EntitySoulCustom(world, soul, x, y, z)

        if (SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GENE_IS_CREATURE)) {
            entity = new EntitySoulCustomCreature(world, soul, x, y, z)
        }

        entity
    }

    override def getISoulInstance(chromosomes: Array[IChromosome]): ISoul = new Soul(chromosomes)

    override def getIChromosomeInstance(allele1: IAllele, allele2: IAllele): IChromosome = new Chromosome(allele1, allele2)

    override def getIAlleleInstance(args: AnyRef*): IAllele =  {
        //TODO test this
        new Allele(args(0).asInstanceOf[Boolean], args(1), EnumAlleleType.forClass(args(1).getClass))
    }
}
