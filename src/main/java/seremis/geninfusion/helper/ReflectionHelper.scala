package seremis.geninfusion.helper

import scala.util.control.Breaks

object ReflectionHelper {

  def setField(obj: Object, name: String, value: Any) {
    var superClass: Any = obj.getClass
    val outer = new Breaks

    outer.breakable {
      while (superClass != null) {
        for (field <- superClass.asInstanceOf[Class[_]].getDeclaredFields()) {
          if (field.getName().equals(name)) {
            field.setAccessible(true)
            field.set(obj, value)
            outer.break
          }
        }
        superClass = superClass.asInstanceOf[Class[_]].getSuperclass
      }
      outer.break
    }
  }

  def getField(obj: Object, name: String): Any = {
    var value: Any = null

    var superClass: Any = obj.getClass
    val outer = new Breaks

    outer.breakable {
      while (superClass != null) {
        for (field <- superClass.asInstanceOf[Class[_]].getDeclaredFields()) {
          if (field.getName().equals(name)) {
            field.setAccessible(true)
            value = field.get(obj)
            outer.break
          }
        }
        superClass = superClass.asInstanceOf[Class[_]].getSuperclass
      }
      outer.break
    }
    value
  }
}
