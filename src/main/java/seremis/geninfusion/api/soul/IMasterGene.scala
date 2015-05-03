package seremis.geninfusion.api.soul

trait IMasterGene extends IGene {

    def addControlledGene(name: String)

    def getControlledGenes: List[String]

    /**
     * //TODO this is vague
     * When this function is called, the IMasterGene will be set to only inherit together with it's controlled Genes.
     * @return This IMasterGene.
     */
    def setCombinedInherit: IMasterGene

    /**
     * Whether this Gene should inherit combined with it's controlled genes.
     */
    def hasCombinedInherit: Boolean
}
