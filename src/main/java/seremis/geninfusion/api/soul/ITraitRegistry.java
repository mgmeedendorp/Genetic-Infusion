package seremis.geninfusion.api.soul;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Seremis
 */
public interface ITraitRegistry {

    /**
     * Register an ITrait. Registering an ITrait wil enable this trait's methods to be called to change or add
     * functionality to an Entity.
     *
     * @param name  a name for the ITrait.
     * @param trait The instance of the ITrait
     */
    public void registerTrait(String name, ITrait trait);

    /**
     * Remove an ITrait from registry. This will completely stop the trait from being executed ever.
     *
     * @param name the name of the ITrait
     */
    public void unregisterTrait(String name);

    /**
     * Remove an ITrait from registry. This will completely stop the trait from being executed ever.
     *
     * @param trait the ITrait to be removed.
     */
    public void unregisterTrait(ITrait trait);

    /**
     * Returns the instance of the ITrait that was registered with the passed name.
     *
     * @param name The name of the trait
     * @return The ITrait instance that has this name
     */
    public ITrait getTrait(String name);

    /**
     * Returns the name of an ITrait from it's instance.
     *
     * @param trait The ITrait instance
     * @return The name of the ITrait
     */
    public String getName(ITrait trait);

    /**
     * Returns the Id of an ITrait from it's instance.
     *
     * @param trait The ITrait instance
     * @return The id of the ITrait
     */
    public int getId(ITrait trait);

    /**
     * Returns the Id of an ITrait from it's name.
     *
     * @param name The name of the ITrait
     * @return The id of the ITrait
     */
    public int getId(String name);

    /**
     * Returns an ArrayList of all registered ITraits.
     *
     * @return An ArrayList of ITraits.
     */
    public LinkedList<ITrait> getTraits();
}
