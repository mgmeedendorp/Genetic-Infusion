package seremis.geninfusion.soul.gene;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleFloatArray;

public class GeneRareItemDropChances implements IGene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleFloatArray.class;
    }
}
