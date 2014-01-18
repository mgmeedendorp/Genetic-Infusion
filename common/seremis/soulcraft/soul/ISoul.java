package seremis.soulcraft.soul;

import seremis.soulcraft.util.INBTTagable;

public interface ISoul extends INBTTagable {
    
    IChromosome[] getChromosomes();
}
