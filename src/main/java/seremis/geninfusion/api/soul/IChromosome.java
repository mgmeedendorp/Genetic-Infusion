package seremis.geninfusion.api.soul;

import seremis.geninfusion.util.INBTTagable;

public interface IChromosome extends INBTTagable {

    IAllele<?> getActive();

    IAllele<?> getRecessive();

    IAllele<?> getPrimary();

    IAllele<?> getSecondary();
}
