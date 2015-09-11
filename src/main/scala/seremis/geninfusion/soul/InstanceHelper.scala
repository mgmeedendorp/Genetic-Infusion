package seremis.geninfusion.soul

import net.minecraft.entity.{EntityLiving, EntityList}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import seremis.geninfusion.api.soul._
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.util.AncestryNode
import seremis.geninfusion.soul.entity.{EntitySoulCustom, EntitySoulCustomAgeable, EntitySoulCustomCreature}

class InstanceHelper extends IInstanceHelper {

    override def getSoulEntityInstance(world: World, soul: ISoul, x: Double, y: Double, z: Double): IEntitySoulCustom = {
        var entity: IEntitySoulCustom = null

        if(SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneIsCreature)) {
            entity = new EntitySoulCustomCreature(world, soul, x, y, z)
        }

        if(SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneCanProcreate)) {
            entity = new EntitySoulCustomAgeable(world, soul, x, y, z)
        }

        if(entity == null) {
            entity = new EntitySoulCustom(world, soul, x, y, z)
        }

        entity
    }

    override def getISoulInstance(chromosomes: Array[IChromosome], name: Option[String], ancestry: AncestryNode): Option[ISoul] = Some(new Soul(chromosomes, name, ancestry))

    override def getIChromosomeInstance(geneName: String, allele1: IAllele, allele2: IAllele): IChromosome = new Chromosome(geneName, allele1, allele2)

    override def getIAlleleInstance(args: AnyRef*): IAllele =  {
        //TODO test this
        new Allele(args(0).asInstanceOf[Boolean], args(1), SoulHelper.alleleTypeRegistry.getAlleleTypeForClass(args(1).getClass))
    }

    override def getSoulEntityInstance(compound: NBTTagCompound, world: World): IEntitySoulCustom = {
        val entity = getSoulEntityInstance(world, new Soul(compound), 0, 0, 0)
        entity.asInstanceOf[EntityLiving].readFromNBT(compound)

        entity
    }

    override def getIAlleleInstance(compound: NBTTagCompound): IAllele = {
        new Allele(compound)
    }

    override def getISoulInstance(compound: NBTTagCompound): Option[ISoul] = {
        Some(new Soul(compound))
    }

    override def getIChromosomeInstance(compound: NBTTagCompound): IChromosome = {
        new Chromosome(compound)
    }
}
