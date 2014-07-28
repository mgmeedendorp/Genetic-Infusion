package seremis.geninfusion.soul.gene;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.allele.AlleleInteger;

/**
 * @author Seremis
 */
public class GeneTalkInterval implements IGene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleInteger.class;
    }
}
