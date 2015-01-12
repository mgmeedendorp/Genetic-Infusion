package seremis.geninfusion.api.soul;

public interface ITraitHandler {

    /**
     * Call the 'parent' of the trait that is being called at the moment. This method only works if it's called from a
     * method in an ITrait.
     *
     * @param entity The IEntitySoulCustom that is executing the ITrait now.
     * @param args   The arguments of the method.
     * @return An object containing the value the super method returned.
     */
    public Object callSuperTrait(IEntitySoulCustom entity, Object... args);
}
