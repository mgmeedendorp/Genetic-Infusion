package seremis.geninfusion.soul.gene.newAI.nearestAttackableTarget;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.soul.allele.AlleleClass;
import seremis.geninfusion.soul.allele.AlleleClassArray;
import seremis.geninfusion.soul.allele.AlleleString;
import seremis.geninfusion.soul.gene.newAI.GeneTarget;

public class GeneAINearestAttackableTargetTarget extends GeneTarget {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleClassArray.class;
    }
}
