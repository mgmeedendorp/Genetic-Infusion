package seremis.geninfusion.api.soul.util

import java.lang.reflect.Modifier

//remove if not needed

import scala.collection.JavaConversions._

object DataHelper {

    def writePrimitives(obj: AnyRef, doPublic: Boolean, doProtected: Boolean, doPrivate: Boolean, doEmpty: Boolean, doFinal: Boolean, doBoolean: Boolean, doByte: Boolean, doShort: Boolean, doInt: Boolean, doFloat: Boolean, doDouble: Boolean, doLong: Boolean, doString: Boolean, doBooleanArr: Boolean, doByteArr: Boolean, doShortArr: Boolean, doIntArr: Boolean, doFloatArr: Boolean, doDoubleArr: Boolean, doLongArr: Boolean, doStringArr: Boolean): Data = {
        val data = new Data()

        var superClass: Any = obj.getClass
        while(superClass != null) {
            for(field <- superClass.asInstanceOf[Class[_]].getDeclaredFields) {
                try {
                    if(!Modifier.isStatic(field.getModifiers) && ((Modifier.isPublic(field.getModifiers) || !doPublic) || (Modifier.isProtected(field.getModifiers) || !doProtected) || (Modifier.isPrivate(field.getModifiers) || !doPrivate) || (field.getModifiers == 0 || !doEmpty) || (Modifier.isFinal(field.getModifiers) || !doFinal))) {
                        field.setAccessible(true)

                        if(doBoolean && field.getType == classOf[Boolean])
                            data.setBoolean(field.getName, field.getBoolean(obj))
                        else if(doByte && field.getType == classOf[Byte])
                            data.setByte(field.getName, field.getByte(obj))
                        else if(doShort && field.getType == classOf[Short])
                            data.setShort(field.getName, field.getShort(obj))
                        else if(doInt && field.getType == classOf[Int])
                            data.setInteger(field.getName, field.getInt(obj))
                        else if(doFloat && field.getType == classOf[Float])
                            data.setFloat(field.getName, field.getFloat(obj))
                        else if(doDouble && field.getType == classOf[Double])
                            data.setDouble(field.getName, field.getDouble(obj))
                        else if(doLong && field.getType == classOf[Long])
                            data.setLong(field.getName, field.getLong(obj))
                        else if(doString && field.getType == classOf[String])
                            data.setString(field.getName, field.get(obj).asInstanceOf[String])
                        else if(doBooleanArr && field.getType == classOf[Array[Boolean]])
                            data.setBooleanArray(field.getName, field.get(obj).asInstanceOf[Array[Boolean]])
                        else if(doByteArr && field.getType == classOf[Array[Byte]])
                            data.setByteArray(field.getName, field.get(obj).asInstanceOf[Array[Byte]])
                        else if(doShortArr && field.getType == classOf[Array[Short]])
                            data.setShortArray(field.getName, field.get(obj).asInstanceOf[Array[Short]])
                        else if(doIntArr && field.getType == classOf[Array[Int]])
                            data.setIntegerArray(field.getName, field.get(obj).asInstanceOf[Array[Int]])
                        else if(doFloatArr && field.getType == classOf[Array[Float]])
                            data.setFloatArray(field.getName, field.get(obj).asInstanceOf[Array[Float]])
                        else if(doDoubleArr && field.getType == classOf[Array[Double]])
                            data.setDoubleArray(field.getName, field.get(obj).asInstanceOf[Array[Double]])
                        else if(doLongArr && field.getType == classOf[Array[Long]])
                            data.setLongArray(field.getName, field.get(obj).asInstanceOf[Array[Long]])
                        else if(doStringArr && field.getType == classOf[Array[String]])
                            data.setStringArray(field.getName, field.get(obj).asInstanceOf[Array[String]])
                    }
                } catch {
                    case e: Exception => e.printStackTrace()
                }
            }
            superClass = superClass.asInstanceOf[Class[_]].getSuperclass
        }
        data
    }

    def writePrimitives(obj: AnyRef): Data = {
        writePrimitives(obj, true, true, false, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)
    }

    def writeAllPrimitives(obj: AnyRef): Data = {
        writePrimitives(obj, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)
    }

    def applyData(data: Data, obj: AnyRef, doPublic: Boolean, doProtected: Boolean, doPrivate: Boolean, doEmpty: Boolean, doFinal: Boolean, doBoolean: Boolean, doByte: Boolean, doShort: Boolean, doInt: Boolean, doFloat: Boolean, doDouble: Boolean, doLong: Boolean, doString: Boolean, doBooleanArr: Boolean, doByteArr: Boolean, doShortArr: Boolean, doIntArr: Boolean, doFloatArr: Boolean, doDoubleArr: Boolean, doLongArr: Boolean, doStringArr: Boolean): AnyRef = {
        var superClass: Any = obj.getClass
        while(superClass != null) {
            for(field <- superClass.asInstanceOf[Class[_]].getDeclaredFields) {
                try {
                    if(!Modifier.isStatic(field.getModifiers) && ((Modifier.isPublic(field.getModifiers) || !doPublic) || (Modifier.isProtected(field.getModifiers) || !doProtected) || (Modifier.isPrivate(field.getModifiers) || !doPrivate) || (field.getModifiers == 0 || !doEmpty) || (Modifier.isFinal(field.getModifiers) || !doFinal))) {
                        field.setAccessible(true)
                        if(doBoolean && field.getType == classOf[Boolean] && data.booleanDataMap.containsKey(field.getName))
                            field.setBoolean(obj, data.booleanDataMap.get(field.getName).get)
                        else if(doByte && field.getType == classOf[Byte] && data.byteDataMap.containsKey(field.getName))
                            field.setByte(obj, data.byteDataMap.get(field.getName).get)
                        else if(doShort && field.getType == classOf[Short] && data.shortDataMap.containsKey(field.getName))
                            field.setShort(obj, data.shortDataMap.get(field.getName).get)
                        else if(doInt && field.getType == classOf[Int] && data.integerDataMap.containsKey(field.getName))
                            field.setInt(obj, data.integerDataMap.get(field.getName).get)
                        else if(doFloat && field.getType == classOf[Float] && data.floatDataMap.containsKey(field.getName))
                            field.setFloat(obj, data.floatDataMap.get(field.getName).get)
                        else if(doDouble && field.getType == classOf[Double] && data.doubleDataMap.containsKey(field.getName))
                            field.setDouble(obj, data.doubleDataMap.get(field.getName).get)
                        else if(doLong && field.getType == classOf[Long] && data.longDataMap.containsKey(field.getName))
                            field.setLong(obj, data.longDataMap.get(field.getName).get)
                        else if(doString && field.getType == classOf[String] && data.stringDataMap.containsKey(field.getName))
                            field.set(obj, data.stringDataMap.get(field.getName).get)
                        else if(doBooleanArr && field.getType == classOf[Array[Boolean]] && data.dataDataMap.containsKey(field.getName))
                            field.set(obj, data.getBooleanArray(field.getName))
                        else if(doByteArr && field.getType == classOf[Array[Byte]] && data.dataDataMap.containsKey(field.getName))
                            field.set(obj, data.getByteArray(field.getName))
                        else if(doShortArr && field.getType == classOf[Array[Short]] && data.dataDataMap.containsKey(field.getName))
                            field.set(obj, data.getShortArray(field.getName))
                        else if(doIntArr && field.getType == classOf[Array[Int]] && data.dataDataMap.containsKey(field.getName))
                            field.set(obj, data.getIntegerArray(field.getName))
                        else if(doFloatArr && field.getType == classOf[Array[Float]] && data.dataDataMap.containsKey(field.getName))
                            field.set(obj, data.getFloatArray(field.getName))
                        else if(doDoubleArr && field.getType == classOf[Array[Double]] && data.dataDataMap.containsKey(field.getName))
                            field.set(obj, data.getDoubleArray(field.getName))
                        else if(doLongArr && field.getType == classOf[Array[Long]] && data.dataDataMap.containsKey(field.getName))
                            field.set(obj, data.getLongArray(field.getName))
                        else if(doStringArr && field.getType == classOf[Array[String]] && data.dataDataMap.containsKey(field.getName))
                            field.set(obj, data.getStringArray(field.getName))
                    }
                } catch {
                    case e: Exception => e.printStackTrace()
                }
            }
            superClass = superClass.asInstanceOf[Class[_]].getSuperclass
        }
        obj
    }

    def applyData(data: Data, obj: AnyRef): AnyRef = {
        applyData(data, obj, true, true, false, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)
    }

    def applyAllData(data: Data, obj: AnyRef): AnyRef = {
        applyData(data, obj, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)
    }
}