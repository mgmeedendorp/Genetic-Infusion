package seremis.geninfusion.api.soul;

import java.util.LinkedList;

/**
 * @author Seremis
 */
public interface ITraitRegistry {

    public void registerTrait(String name, ITrait trait);

    public void registerTrait(String name, ITrait trait, String... methods);

    public void registerTraitMethod(ITrait trait, String method);

    public ITrait getTrait(String name);

    public String getName(ITrait trait);

    public int getId(String name);

    public int getId(ITrait trait);

    public LinkedList<ITrait> getTraits();
}
