package seremis.geninfusion.soul

import seremis.geninfusion.api.soul.IModelPartTypeRegistry
import seremis.geninfusion.api.util.render.model.{ModelPart, Model}

import scala.collection.mutable.ListBuffer

class ModelPartTypeRegistry extends IModelPartTypeRegistry {

    var types: ListBuffer[String] = ListBuffer()

    override def register(partType: String) {
        types += partType
    }

    override def getModelPartTypes: Array[String] = types.to[Array]
}
