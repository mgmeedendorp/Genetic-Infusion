package seremis.geninfusion.util

import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

object Utils {

    implicit class PrimitiveUtil[T : TypeTag, AnyValCompanion](prm:T){
        def toNBT(compound: NBTTagCompound, name: String): NBTTagCompound = {
            compound.setString(name + ".type", typeOf[T].toString)
            compound.setString(name + ".data", prm.toString)
            compound
        }

        def fromNBT(compound: NBTTagCompound, name: String):T = typeOf[T] match {
            case t if t == typeOf[Int] => compound.getString(name + ".data").toInt.asInstanceOf[T]
            case t if t == typeOf[Float] => compound.getString(name + ".data").toFloat.asInstanceOf[T]
            case t if t == typeOf[Double] => compound.getString(name + ".data").toDouble.asInstanceOf[T]
            case t if t == typeOf[Boolean] => compound.getString(name + ".data").toBoolean.asInstanceOf[T]
            case t if t == typeOf[Byte] => compound.getString(name + ".data").toByte.asInstanceOf[T]
            case t if t == typeOf[Short] => compound.getString(name + ".data").toShort.asInstanceOf[T]
            case t if t == typeOf[Long] => compound.getString(name + ".data").toLong.asInstanceOf[T]
            case _ => null.asInstanceOf[T]
        }
    }

    implicit class ArrayUtils[T: TypeTag](array: Array[T]) {
        def toNBT(compound: NBTTagCompound, name: String) {
            val list = new NBTTagList

            for(i <- 0 until array.length) {
                val arrayCompound = new NBTTagCompound
                array(i).toNBT(arrayCompound, name + "." + i)
                list.appendTag(arrayCompound)
            }

            compound.setTag(name + ".data", list)
            compound.setString(name + ".type", typeOf[T].toString)
        }

        def fromNBT(compound: NBTTagCompound, name: String)(implicit m: ClassTag[T]): Array[T] = {
            val list = compound.getTag(name + ".data").asInstanceOf[NBTTagList]
            val result = new Array[T](list.tagCount())

            for(i <- 0 until list.tagCount()) {
                result(i).fromNBT(list.getCompoundTagAt(i), name + "." + i)
            }
            result
        }
    }

    implicit class StringUtils(str: String) {
        def toNBT(compound: NBTTagCompound, name: String) = {
            compound.setString(name + ".data", str)
            compound.setString(name + ".type", typeOf[String].toString)
        }
        def fromNBT(compound: NBTTagCompound, name: String): String = compound.getString(name + ".data")
    }

    implicit class ItemStackUtils(stack: ItemStack) {
        def toNBT(compound: NBTTagCompound, name: String) = {
            compound.setTag(name + ".data", stack.writeToNBT(new NBTTagCompound))
            compound.setString(name + ".type", typeOf[ItemStack].toString)
        }
        def fromNBT(compound: NBTTagCompound, name: String): ItemStack = ItemStack.loadItemStackFromNBT(compound.getTag(name + ".data").asInstanceOf[NBTTagCompound])
    }

    implicit class ClassUtils(clss: Class[_]) {
        def toNBT(compound: NBTTagCompound, name: String) {
            compound.setString(name + ".data", clss.getName)
            compound.setString(name + ".type", typeOf[String].toString)
            compound.setString(name + ".isClass", "a")
        }
    }
}
