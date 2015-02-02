package seremis.geninfusion.soul.gene;

import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleInventory;

public class GeneRareItemDrops extends Gene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleInventory.class;
    }
}
