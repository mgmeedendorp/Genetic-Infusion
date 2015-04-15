package seremis.geninfusion.soul

import net.minecraft.item.ItemStack
import net.minecraft.world.World
import seremis.geninfusion.api.soul._
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.soul.entity.{EntitySoulCustomCreature, EntitySoulCustom}

class InstanceHelper extends IInstanceHelper {

    override def getSoulEntityInstance(world: World, soul: ISoul, x: Double, y: Double, z: Double): IEntitySoulCustom = {
        var entity: IEntitySoulCustom = new EntitySoulCustom(world, soul, x, y, z)

        if (SoulHelper.geneRegistry.getValueBoolean(entity, Genes.GENE_IS_CREATURE)) {
            entity = new EntitySoulCustomCreature(world, soul, x, y, z)
        }

        entity
    }

    override def getISoulInstance(chromosomes: Array[IChromosome]): ISoul = new Soul(chromosomes)

    override def getIChromosomeInstance(allele1: IAllele[_], allele2: IAllele[_]): IChromosome = new Chromosome(allele1, allele2)

    override def getIAlleleInstance(alleleType: EnumAlleleType, args: AnyRef*): IAllele[_] = {
        val clazz = alleleType.clzz

        var allele: IAllele[_] = null
        
        alleleType match {
            case EnumAlleleType.BOOLEAN =>
                allele = new Allele[Boolean](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Boolean])
            case EnumAlleleType.BYTE =>
                allele = new Allele[Byte](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Byte])
            case EnumAlleleType.SHORT =>
                allele = new Allele[Short](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Short])
            case EnumAlleleType.INTEGER =>
                allele = new Allele[Int](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Int])
            case EnumAlleleType.FLOAT =>
                allele = new Allele[Float](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Float])
            case EnumAlleleType.DOUBLE =>
                allele = new Allele[Double](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Double])
            case EnumAlleleType.LONG =>
                allele = new Allele[Long](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Long])
            case EnumAlleleType.STRING =>
                allele = new Allele[String](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[String])
            case EnumAlleleType.CLASS =>
                allele = new Allele[Class[_]](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Class[_]])
            case EnumAlleleType.ITEMSTACK =>
                allele = new Allele[ItemStack](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[ItemStack])
            case EnumAlleleType.MODELPART =>
                allele = new Allele[ModelPart](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[ModelPart])
            case EnumAlleleType.BOOLEAN_ARRAY =>
                allele = new Allele[Array[Boolean]](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[Boolean]])
            case EnumAlleleType.BYTE_ARRAY =>
                allele = new Allele[Array[Byte]](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[Byte]])
            case EnumAlleleType.SHORT_ARRAY =>
                allele = new Allele[Array[Short]](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[Short]])
            case EnumAlleleType.INTEGER_ARRAY =>
                allele = new Allele[Array[Int]](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[Int]])
            case EnumAlleleType.FLOAT_ARRAY =>
                allele = new Allele[Array[Float]](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[Float]])
            case EnumAlleleType.DOUBLE_ARRAY =>
                allele = new Allele[Array[Double]](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[Double]])
            case EnumAlleleType.LONG_ARRAY =>
                allele = new Allele[Array[Long]](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[Long]])
            case EnumAlleleType.STRING_ARRAY =>
                allele = new Allele[Array[String]](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[String]])
            case EnumAlleleType.CLASS_ARRAY =>
                allele = new Allele[Array[Class[_]]](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[Class[_]]])
            case EnumAlleleType.ITEMSTACK_ARRAY =>
                allele = new Allele[Array[ItemStack]](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[ItemStack]])
            case EnumAlleleType.MODELPART_ARRAY =>
                allele = new Allele[Array[ModelPart]](args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[ModelPart]])
        }
        allele
    }
}
