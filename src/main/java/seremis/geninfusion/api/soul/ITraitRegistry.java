package seremis.geninfusion.api.soul;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Seremis
 */
public interface ITraitRegistry {

    /**
     * Register an ITrait. Registering an ITrait wil enable this trait's methods to be called
     * to change or add functionality to an Entity.
     * @param name a name for the ITrait.
     * @param trait The instance of the ITrait
     */
    public void registerTrait(String name, ITrait trait);

    /**
     * Returns the instance of the ITrait that was registered with the passed name.
     * @param name The name of the trait
     * @return The ITrait instance that has this name
     */
    public ITrait getTrait(String name);

    /**
     * Returns the name of an ITrait from it's instance.
     * @param trait The ITrait instance
     * @return The name of the ITrait
     */
    public String getName(ITrait trait);

    /**
     * Returns the Id of an ITrait from it's instance.
     * @param trait The ITrait instance
     * @return The id of the ITrait
     */
    public int getId(ITrait trait);

    /**
     * Returns the Id of an ITrait from it's name.
     * @param name The name of the ITrait
     * @return The id of the ITrait
     */
    public int getId(String name);

    /**
     * Returns an ArrayList of all registered ITraits.
     * @return An ArrayList of ITraits.
     */
    public LinkedList<ITrait> getTraits();

    /**
     * Make one trait override another. This will make sure that the subTrait is called before the superTrait.
     * The superTrait's code will only be executed if ITraitHandler.callSuperTrait is called while a method in the
     * trait is being called.
     * @param superTrait The 'parent' ITrait that is being overridden
     * @param subTrait The 'child' ITrait that is overriding the superTrait
     */
    public void makeTraitOverride(ITrait superTrait, ITrait subTrait);

    /**
     * Make one trait override another. This will make sure that the subTrait is called before the superTrait.
     * The superTrait's code will only be executed if ITraitHandler.callSuperTrait is called while a method in the
     * trait is being called.
     * @param superName The name of the 'parent' ITrait that is being overridden
     * @param subName The name of the 'child' ITrait that is overriding the superTrait
     */
    public void makeTraitOverride(String superName, String subName);

    /**
     * Returns a list of ITraits that are overridden by this trait.
     * @param trait The ITrait
     * @return A list of traits that are overridden by this trait
     */
    public ArrayList<ITrait> getOverridden(ITrait trait);

    /**
     * Returns a list of ITraits that are overridden by this trait.
     * @param name The name of the ITrait
     * @return A list of traits that are overridden by this trait
     */
    public ArrayList<ITrait> getOverridden(String name);

    /**
     * Returns a list of ITraits that are overriding this trait.
     * @param trait The ITrait
     * @return A list of traits that are overriding this trait
     */
    public ArrayList<ITrait> getOverriding(ITrait trait);

    /**
     * Returns a list of ITraits that are overriding this trait.
     * @param name The name of the ITrait
     * @return A list of traits that are overriding this trait
     */
    public ArrayList<ITrait> getOverriding(String name);

    /**
     * Checks if the passed trait is overridden by another trait.
     * @param trait The ITrait
     * @return Whether the trait is overridden by another trait.
     */
    public boolean isOverridden(ITrait trait);

    /**
     * Checks if the passed trait is overriding another trait.
     * @param trait The ITrait
     * @return Whether the trait is overriding another trait.
     */
    public boolean isOverriding(ITrait trait);

    /**
     * Returns the traits in the correct order according to the makeTraitOverride method.
     * @return An ordered ArrayList<ITrait> with all the traits
     */
    public LinkedList<ITrait> getOrderedTraits();
}
