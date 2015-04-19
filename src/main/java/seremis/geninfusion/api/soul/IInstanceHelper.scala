package seremis.geninfusion.api.soul

import net.minecraft.world.World

trait IInstanceHelper {

    def getSoulEntityInstance(world: World, soul: ISoul, x: Double, y: Double, z: Double): IEntitySoulCustom

    def getISoulInstance(chromosomes: Array[IChromosome]): ISoul

    def getIChromosomeInstance(allele1: IAllele, allele2: IAllele): IChromosome

    def getIAlleleInstance(args: AnyRef*): IAllele
}
