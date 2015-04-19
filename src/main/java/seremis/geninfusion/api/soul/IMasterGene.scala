package seremis.geninfusion.api.soul

trait IMasterGene {

    def addControlledGene(name: String)

    def getControlledGenes: List[String]
}
