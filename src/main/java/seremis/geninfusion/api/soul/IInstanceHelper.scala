package seremis.geninfusion.api.soul

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

trait IInstanceHelper {

    def getSoulEntityInstance(world: World, soul: ISoul, x: Double, y: Double, z: Double): IEntitySoulCustom
    def getSoulEntityInstance(compound: NBTTagCompound, world: World): IEntitySoulCustom

    def getISoulInstance(chromosomes: Array[IChromosome]): ISoul
    def getISoulInstance(compound: NBTTagCompound): ISoul

    def getIChromosomeInstance(allele1: IAllele, allele2: IAllele): IChromosome
    def getIChromosomeInstance(compound: NBTTagCompound): IChromosome

    def getIAlleleInstance(args: AnyRef*): IAllele
    def getIAlleleInstance(compound: NBTTagCompound): IAllele
}
