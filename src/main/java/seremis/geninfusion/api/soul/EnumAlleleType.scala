package seremis.geninfusion.api.soul

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.util.ModelPart
//remove if not needed

object EnumAlleleType extends Enumeration {

    val BOOLEAN = new EnumAlleleType(classOf[Boolean])

    val BYTE = new EnumAlleleType(classOf[Byte])

    val SHORT = new EnumAlleleType(classOf[Short])

    val INTEGER = new EnumAlleleType(classOf[Integer])

    val FLOAT = new EnumAlleleType(classOf[Float])

    val DOUBLE = new EnumAlleleType(classOf[Double])

    val LONG = new EnumAlleleType(classOf[Long])

    val STRING = new EnumAlleleType(classOf[String])

    val CLASS = new EnumAlleleType(classOf[Class[_]])

    val MODELPART = new EnumAlleleType(classOf[ModelPart])

    val ITEMSTACK = new EnumAlleleType(classOf[ItemStack])

    val BOOLEAN_ARRAY = new EnumAlleleType(classOf[Array[Boolean]])

    val BYTE_ARRAY = new EnumAlleleType(classOf[Array[Byte]])

    val SHORT_ARRAY = new EnumAlleleType(classOf[Array[Short]])

    val INTEGER_ARRAY = new EnumAlleleType(classOf[Array[Int]])

    val FLOAT_ARRAY = new EnumAlleleType(classOf[Array[Float]])

    val DOUBLE_ARRAY = new EnumAlleleType(classOf[Array[Double]])

    val LONG_ARRAY = new EnumAlleleType(classOf[Array[Long]])

    val STRING_ARRAY = new EnumAlleleType(classOf[Array[String]])

    val CLASS_ARRAY = new EnumAlleleType(classOf[Array[Class[_]]])

    val ITEMSTACK_ARRAY = new EnumAlleleType(classOf[Array[ItemStack]])

    val MODELPART_ARRAY = new EnumAlleleType(classOf[Array[ModelPart]])

    class EnumAlleleType(var clzz: Class[_]) extends Val {

        def writeValueToNBT(compound: NBTTagCompound, name: String, value: AnyRef): NBTTagCompound = {
            this match {
                case BOOLEAN => compound.setBoolean(name, value.asInstanceOf[java.lang.Boolean])
                case BYTE => compound.setByte(name, value.asInstanceOf[java.lang.Byte])
                case SHORT => compound.setShort(name, value.asInstanceOf[java.lang.Short])
                case INTEGER => compound.setInteger(name, value.asInstanceOf[java.lang.Integer])
                case FLOAT => compound.setFloat(name, value.asInstanceOf[java.lang.Float])
                case DOUBLE => compound.setDouble(name, value.asInstanceOf[java.lang.Double])
                case LONG => compound.setLong(name, value.asInstanceOf[java.lang.Long])
                case STRING => compound.setString(name, value.asInstanceOf[String])
                case CLASS => compound.setString(name, value.asInstanceOf[Class[_]].getName)
                case MODELPART => compound.setTag(name, value.asInstanceOf[ModelPart].writeToNBT(new NBTTagCompound()))
                case ITEMSTACK => compound.setTag(name, value.asInstanceOf[ItemStack].writeToNBT(new NBTTagCompound()))
                case BOOLEAN_ARRAY => {
                    for(i <- 0 until value.asInstanceOf[Array[Boolean]].length) {
                        compound.setBoolean(name + "." + i, value.asInstanceOf[Array[Boolean]](i))
                    }
                    compound.setInteger(name + ".length", value.asInstanceOf[Array[Boolean]].length)

                }
                case BYTE_ARRAY => {
                    for(i <- 0 until value.asInstanceOf[Array[Byte]].length) {
                        compound.setByte(name + "." + i, value.asInstanceOf[Array[Byte]](i))
                    }
                    compound.setInteger(name + ".length", value.asInstanceOf[Array[Byte]].length)

                }
                case SHORT_ARRAY => {
                    for(i <- 0 until value.asInstanceOf[Array[Short]].length) {
                        compound.setShort(name + "." + i, value.asInstanceOf[Array[Short]](i))
                    }
                    compound.setInteger(name + ".length", value.asInstanceOf[Array[Short]].length)

                }
                case INTEGER_ARRAY => {
                    for(i <- 0 until value.asInstanceOf[Array[Int]].length) {
                        compound.setInteger(name + "." + i, value.asInstanceOf[Array[Int]](i))
                    }
                    compound.setInteger(name + ".length", value.asInstanceOf[Array[Int]].length)

                }
                case FLOAT_ARRAY => {
                    for(i <- 0 until value.asInstanceOf[Array[Float]].length) {
                        compound.setFloat(name + "." + i, value.asInstanceOf[Array[Float]](i))
                    }
                    compound.setInteger(name + ".length", value.asInstanceOf[Array[Float]].length)

                }
                case DOUBLE_ARRAY => {
                    for(i <- 0 until value.asInstanceOf[Array[Double]].length) {
                        compound.setDouble(name + "." + i, value.asInstanceOf[Array[Double]](i))
                    }
                    compound.setInteger(name + ".length", value.asInstanceOf[Array[Double]].length)

                }
                case LONG_ARRAY => {
                    for(i <- 0 until value.asInstanceOf[Array[Long]].length) {
                        compound.setLong(name + "." + i, value.asInstanceOf[Array[Long]](i))
                    }
                    compound.setInteger(name + ".length", value.asInstanceOf[Array[Long]].length)

                }
                case STRING_ARRAY => {
                    for(i <- 0 until value.asInstanceOf[Array[String]].length) {
                        compound.setString(name + "." + i, value.asInstanceOf[Array[String]](i))
                    }
                    compound.setInteger(name + ".length", value.asInstanceOf[Array[String]].length)

                }
                case CLASS_ARRAY => {
                    for(i <- 0 until value.asInstanceOf[Array[Class[_]]].length) {
                        compound.setString(name + "." + i, value.asInstanceOf[Array[Class[_]]](i).getName)
                    }
                    compound.setInteger(name + ".length", value.asInstanceOf[Array[Class[_]]].length)

                }
                case ITEMSTACK_ARRAY => {
                    for(i <- 0 until value.asInstanceOf[Array[ItemStack]].length) {
                        compound.setTag(name + "." + i, value.asInstanceOf[Array[ItemStack]](i).writeToNBT(new NBTTagCompound()))
                    }
                    compound.setInteger(name + ".length", value.asInstanceOf[Array[ItemStack]].length)

                }
                case MODELPART_ARRAY => {
                    for(i <- 0 until value.asInstanceOf[Array[ModelPart]].length) {
                        compound.setTag(name + "." + i, value.asInstanceOf[Array[ModelPart]](i).writeToNBT(new NBTTagCompound()))
                    }
                    compound.setInteger(name + ".length", value.asInstanceOf[Array[ModelPart]].length)
                }
            }
            compound
        }

        def readValueFromNBT(compound: NBTTagCompound, name: String): AnyRef = {
            var result: AnyRef = null
            this match {
                case BOOLEAN => result = compound.getBoolean(name)
                case BYTE => result = compound.getByte(name)
                case SHORT => result = compound.getShort(name)
                case INTEGER => result = compound.getInteger(name)
                case FLOAT => result = compound.getFloat(name)
                case DOUBLE => result = compound.getDouble(name)
                case LONG => result = compound.getLong(name)
                case STRING => result = compound.getString(name)
                case CLASS => try {
                    result = Class.forName(compound.getString(name))
                } catch {
                    case e: Exception =>
                }
                case ITEMSTACK => ItemStack.loadItemStackFromNBT(compound.getCompoundTag(name))
                case MODELPART => ModelPart.fromNBT(compound.getCompoundTag(name))
                case BOOLEAN_ARRAY => {
                    val length = compound.getInteger(name + ".length")
                    val out = Array.ofDim[Boolean](length)
                    for (i <- 0 until length) {
                        out(i) = compound.getBoolean(name + "." + i)
                    }
                    result = out

                }
                case BYTE_ARRAY => {
                    val length = compound.getInteger(name + ".length")
                    val out = Array.ofDim[Byte](length)
                    for (i <- 0 until length) {
                        out(i) = compound.getByte(name + "." + i)
                    }
                    result = out

                }
                case SHORT_ARRAY => {
                    val length = compound.getInteger(name + ".length")
                    val out = Array.ofDim[Short](length)
                    for (i <- 0 until length) {
                        out(i) = compound.getShort(name + "." + i)
                    }
                    result = out

                }
                case INTEGER_ARRAY => {
                    val length = compound.getInteger(name + ".length")
                    val out = Array.ofDim[Int](length)
                    for (i <- 0 until length) {
                        out(i) = compound.getInteger(name + "." + i)
                    }
                    result = out

                }
                case FLOAT_ARRAY => {
                    val length = compound.getInteger(name + ".length")
                    val out = Array.ofDim[Float](length)
                    for (i <- 0 until length) {
                        out(i) = compound.getFloat(name + "." + i)
                    }
                    result = out

                }
                case DOUBLE_ARRAY => {
                    val length = compound.getInteger(name + ".length")
                    val out = Array.ofDim[Double](length)
                    for (i <- 0 until length) {
                        out(i) = compound.getDouble(name + "." + i)
                    }
                    result = out

                }
                case LONG_ARRAY => {
                    val length = compound.getInteger(name + ".length")
                    val out = Array.ofDim[Long](length)
                    for (i <- 0 until length) {
                        out(i) = compound.getLong(name + "." + i)
                    }
                    result = out

                }
                case STRING_ARRAY => {
                    val length = compound.getInteger(name + ".length")
                    val out = Array.ofDim[String](length)
                    for (i <- 0 until length) {
                        out(i) = compound.getString(name + "." + i)
                    }
                    result = out

                }
                case CLASS_ARRAY => {
                    val length = compound.getInteger(name + ".length")
                    val out = Array.ofDim[Class[_]](length)
                    try {
                        for (i <- 0 until length) {
                            out(i) = Class.forName(compound.getString(name + "." + i))
                        }
                    } catch {
                        case e: Exception =>
                    }
                    result = out
                }
                case ITEMSTACK_ARRAY => {
                    val length = compound.getInteger(name + ".length")
                    val out = Array.ofDim[ItemStack](length)
                    for (i <- 0 until length) {
                        out(i) = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(name + "." + i))
                    }
                    result = out
                }
                case MODELPART_ARRAY => {
                    val length = compound.getInteger(name + ".length")
                    val out = Array.ofDim[ModelPart](length)
                    for (i <- 0 until length) {
                        out(i) = ModelPart.fromNBT(compound.getCompoundTag(name + "." + i))
                    }
                    result = out
                }
            }
            result
        }
    }

    def forClass(clzz: Class[_]): EnumAlleleType = {
        values.find(_.clzz == clzz).getOrElse(null)
    }

    implicit def convertValue(v: Value): EnumAlleleType = v.asInstanceOf[EnumAlleleType]
}