package seremis.geninfusion.api.soul

import seremis.geninfusion.api.util.AncestryNode
import seremis.geninfusion.util.INBTTagable

trait ISoul extends INBTTagable {

    def getChromosomes: Array[IChromosome]

    /**
     * Gets the name of the entity with this soul, None for no name.
     * @return The name of the entity with this soul.
     */
    def getName: Option[String]

    /**
     * Returns a String with the ancestry of this ISoul. Used for crystal item tooltips.
     * @return A String with the ancestry of this ISoul.
     */
    def getAncestryNode: AncestryNode
}
