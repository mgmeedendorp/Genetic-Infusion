package seremis.soulcraft.soul;

import seremis.soulcraft.soul.actions.ChromosomeBurnsInDaylight;
import seremis.soulcraft.soul.actions.ChromosomeItemDrops;
import seremis.soulcraft.soul.actions.ChromosomeMaxHealth;
import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.allele.AlleleFloat;
import seremis.soulcraft.soul.allele.AlleleItemStack;


public enum EnumChromosome {
    
    IS_TEMPLATE_GENOME(EnumChromosomeType.CONSTANT, null, AlleleBoolean.class), 
    MAX_HEALTH(EnumChromosomeType.CONSTANT, new ChromosomeMaxHealth(), AlleleFloat.class),
    BURNS_IN_DAYLIGHT(EnumChromosomeType.UPDATE, new ChromosomeBurnsInDaylight(), AlleleBoolean.class),
    ITEM_DROPS(EnumChromosomeType.UPDATE, new ChromosomeItemDrops(0), AlleleItemStack.class),
    RARE_ITEM_DROP_CHANCE(EnumChromosomeType.UPDATE, new ChromosomeItemDrops(2), AlleleFloat.class),
    RARE_ITEM_DROPS(EnumChromosomeType.UPDATE, new ChromosomeItemDrops(1), AlleleItemStack.class);
    
    
    public EnumChromosomeType type;
    public IChromosomeAction action;
    
    public Class<? extends IAllele>[] possibleAlleles;
    
    private EnumChromosome(EnumChromosomeType type, IChromosomeAction action, Class<? extends IAllele>... possibleAlleles) {
        this.type = type;
        this.action = action;
        this.possibleAlleles = possibleAlleles;
    }
}
