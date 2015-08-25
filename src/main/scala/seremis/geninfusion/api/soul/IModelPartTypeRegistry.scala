package seremis.geninfusion.api.soul

trait IModelPartTypeRegistry {

    def register(name: String, modelPartType: IModelPartType)

    def getModelPartType(name: String): Option[IModelPartType]

    def getName(modelPartType: IModelPartType): Option[String]

    def getModelPartTypes: Array[IModelPartType]

    def getModelPartTypeNames: Array[String]
}
