package seremis.geninfusion.api.soul;

import seremis.geninfusion.util.INBTTagable;

public interface IAllele<T> extends INBTTagable {

    boolean isDominant();

    T getAlleleData();
}
