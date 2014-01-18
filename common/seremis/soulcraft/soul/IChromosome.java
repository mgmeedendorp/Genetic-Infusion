package seremis.soulcraft.soul;

import seremis.soulcraft.util.INBTTagable;

public interface IChromosome extends INBTTagable {

    IAllele getActive();
    IAllele getRecessive();
    
    IAllele getPrimary();
    IAllele getSecondary();
}
