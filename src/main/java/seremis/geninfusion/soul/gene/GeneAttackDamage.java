package seremis.geninfusion.soul.gene;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleDouble;
import seremis.geninfusion.soul.allele.AlleleFloat;

public class GeneAttackDamage extends Gene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleDouble.class;
    }
}
