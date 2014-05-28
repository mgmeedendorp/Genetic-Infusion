package seremis.soulcraft.api.soul;

import seremis.soulcraft.util.INBTTagable;

public interface ISoul extends INBTTagable {
    
    IChromosome[] getChromosomes();
}
