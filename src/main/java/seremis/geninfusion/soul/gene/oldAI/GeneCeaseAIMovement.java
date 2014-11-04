package seremis.geninfusion.soul.gene.oldAI;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleBoolean;

/**
 * Use this Gene as replacement for the isMovementCeased() method in EntityCreature.java and only applies to creatures.
 */
public class GeneCeaseAIMovement implements IGene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleBoolean.class;
    }
}
