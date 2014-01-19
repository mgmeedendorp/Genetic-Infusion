package seremis.soulcraft.soul;

import seremis.soulcraft.soul.actions.ActionBurnsInDaylight;
import seremis.soulcraft.soul.allele.AlleleBoolean;

public enum EnumChromosome {
    
    IS_TEMPLATE_GENOME(null, null), BURNS_IN_DAYLIGHT(new ActionBurnsInDaylight(), AlleleBoolean.class);
    
    public IChromosomeAction action;
    
    public Class<? extends IAllele>[] possibleAlleles;
    
    private EnumChromosome(IChromosomeAction action, Class<? extends IAllele>... possibleAlleles) {
        this.action = action;
        this.possibleAlleles = possibleAlleles;
    }
}
