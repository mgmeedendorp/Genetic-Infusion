package seremis.geninfusion.helper

import java.lang.reflect.Field

import scala.util.control.Breaks

object GIReflectionHelper {

    var fields: Map[Class[_], Map[String, Field]] = Map()

    def indexFields(clzz: Class[_]) {
        var superClass: Any = clzz
        val outer = new Breaks
        var fields: Map[String, Field] = Map()

        while(superClass != null) {
            for(field <- superClass.asInstanceOf[Class[_]].getDeclaredFields) {
                fields += (field.getName -> field)
            }
            superClass = superClass.asInstanceOf[Class[_]].getSuperclass
        }

        if(fields.nonEmpty) {
            this.fields += (clzz -> fields)
        }
    }

    def setField(obj: Object, name: String, value: Any) {
        val clzz = obj.getClass

        if(!fields.contains(clzz)) indexFields(clzz)

        val field = fields.get(clzz).get(name)

        field.setAccessible(true)
        field.set(obj, value)
    }

    def getField(obj: Object, name: String): Any = {
        val clzz = obj.getClass

        if(!fields.contains(clzz)) indexFields(clzz)

        val field = fields.get(clzz).get(name)

        field.setAccessible(true)
        field.get(obj)
    }

    def getFields(obj: Object): Array[Field] = {
        val clzz = obj.getClass

        if(!fields.contains(clzz)) indexFields(clzz)

        fields.get(clzz).get.values.toArray
    }
}
