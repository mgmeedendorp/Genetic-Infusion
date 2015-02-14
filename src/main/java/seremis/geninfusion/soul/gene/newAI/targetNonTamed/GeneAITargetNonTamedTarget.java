package seremis.geninfusion.soul.gene.newAI.targetNonTamed;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleClass;
import seremis.geninfusion.soul.allele.AlleleString;
import seremis.geninfusion.soul.gene.newAI.GeneTarget;

public class GeneAITargetNonTamedTarget extends GeneTarget {
    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleClass.class;
    }
}
