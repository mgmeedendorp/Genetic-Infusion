package seremis.geninfusion.soul

import seremis.geninfusion.api.soul.{IModelPartType, IModelPartTypeRegistry}

import scala.collection.immutable.HashMap

class ModelPartTypeRegistry extends IModelPartTypeRegistry {

    var types: HashMap[String, IModelPartType] = HashMap()
    var names: HashMap[IModelPartType, String] = HashMap()

    override def register(name: String, modelPartType: IModelPartType) {
        types += (name -> modelPartType)
        names += (modelPartType -> name)
    }

    override def getName(modelPartType: IModelPartType): Option[String] = names.get(modelPartType)

    override def getModelPartType(name: String): Option[IModelPartType] = types.get(name)

    override def getModelPartTypes: Array[IModelPartType] = types.values.to[Array]

    override def getModelPartTypeNames: Array[String] = names.values.to[Array]
}
