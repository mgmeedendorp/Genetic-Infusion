package seremis.soulcraft.soul;

import seremis.soulcraft.soul.actions.ActionBurnsInDaylight;
import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleFloat;
import seremis.soulcraft.soul.constants.ConstantMaxHealth;


public enum EnumChromosome {
    
    IS_TEMPLATE_GENOME(EnumChromosomeType.CONSTANT, null, AlleleBoolean.class), 
    BURNS_IN_DAYLIGHT(EnumChromosomeType.UPDATE, new ActionBurnsInDaylight(), AlleleBoolean.class),
    HEALTH(EnumChromosomeType.CONSTANT, new ConstantMaxHealth(), AlleleFloat.class);
    
    public EnumChromosomeType type;
    public IChromosomeAction action;
    
    public Class<? extends IAllele>[] possibleAlleles;
    
    private EnumChromosome(EnumChromosomeType type, IChromosomeAction action, Class<? extends IAllele>... possibleAlleles) {
        this.type = type;
        this.action = action;
        this.possibleAlleles = possibleAlleles;
    }
}
