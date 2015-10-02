package seremis.geninfusion.api.soul

trait IModelPartTypeRegistry {

    def register(modelPartType: String)

    def getModelPartTypes: Array[String]
}
