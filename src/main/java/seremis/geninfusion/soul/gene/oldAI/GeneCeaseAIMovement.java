package seremis.geninfusion.soul.gene.oldAI;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

/**
 * Use this Gene as replacement for the isMovementCeased() method in EntityCreature.java and only applies to creatures.
 */
public class GeneCeaseAIMovement extends Gene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
