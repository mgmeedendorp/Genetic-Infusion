package seremis.geninfusion.api.soul

import seremis.geninfusion.api.util.render.model.{ModelPart, Model}

trait IModelPartTypeRegistry {

    def register(modelPartType: String)

    def getModelPartTypes: Array[String]
}
