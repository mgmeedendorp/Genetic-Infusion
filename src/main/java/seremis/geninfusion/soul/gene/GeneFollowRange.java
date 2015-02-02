package seremis.geninfusion.soul.gene;

import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleFloat;

/**
 * @author Seremis
 */
public class GeneFollowRange extends Gene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloat.class;
    }
}
