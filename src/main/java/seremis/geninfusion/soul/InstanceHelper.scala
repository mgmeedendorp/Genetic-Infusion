package seremis.geninfusion.soul

import net.minecraft.entity.EntityList
import net.minecraft.nbt.NBTTagCompound
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
        new Allele(args(0).asInstanceOf[Boolean], args(1), SoulHelper.alleleTypeRegistry.getAlleleTypeForClass(args(1).getClass))
    }

    override def getSoulEntityInstance(compound: NBTTagCompound, world: World): IEntitySoulCustom = {
        EntityList.createEntityFromNBT(compound, world).asInstanceOf[IEntitySoulCustom]
    }

    override def getIAlleleInstance(compound: NBTTagCompound): IAllele = {
        new Allele(compound)
    }

    override def getISoulInstance(compound: NBTTagCompound): ISoul = {
        new Soul(compound)
    }

    override def getIChromosomeInstance(compound: NBTTagCompound): IChromosome = {
        new Chromosome(compound)
    }
}
