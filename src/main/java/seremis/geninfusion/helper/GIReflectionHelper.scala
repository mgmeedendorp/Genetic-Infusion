package seremis.geninfusion.helper

import java.lang.reflect.Field
import java.util

import scala.util.control.Breaks

object GIReflectionHelper {

    val fields = new util.HashMap[Class[_], util.HashMap[String, Field]]();

    def indexFields(clzz: Class[_]) {
        var superClass: Any = clzz
        val outer = new Breaks
        val fields = new util.HashMap[String, Field]();

        while(superClass != null) {
            for(field <- superClass.asInstanceOf[Class[_]].getDeclaredFields()) {
                fields.put(field.getName(), field)
            }
            superClass = superClass.asInstanceOf[Class[_]].getSuperclass
        }

        if(!fields.isEmpty) {
            this.fields.put(clzz, fields)
        }
    }

    def setField(obj: Object, name: String, value: Any) {
        val clzz = obj.getClass

        if(!fields.containsKey(clzz)) indexFields(clzz)

        val field = fields.get(clzz).get(name)

        field.setAccessible(true)
        field.set(obj, value)
    }

    def getField(obj: Object, name: String): Any = {
        val clzz = obj.getClass

        if(!fields.containsKey(clzz)) indexFields(clzz)

        val field = fields.get(clzz).get(name)

        field.setAccessible(true)
        field.get(obj)
    }
}
