package seremis.geninfusion.api.soul

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import seremis.geninfusion.api.util.AncestryNode

trait IInstanceHelper {

    def getSoulEntityInstance(world: World, soul: ISoul, x: Double, y: Double, z: Double): IEntitySoulCustom
    def getSoulEntityInstance(compound: NBTTagCompound, world: World): IEntitySoulCustom

    def getISoulInstance(chromosomes: Array[IChromosome], name: Option[String], ancestry: AncestryNode): Option[ISoul]
    def getISoulInstance(compound: NBTTagCompound): Option[ISoul]

    def getIChromosomeInstance(geneName: String, allele1: IAllele, allele2: IAllele): IChromosome
    def getIChromosomeInstance(compound: NBTTagCompound): IChromosome

    def getIAlleleInstance(args: AnyRef*): IAllele
    def getIAlleleInstance(compound: NBTTagCompound): IAllele
}
