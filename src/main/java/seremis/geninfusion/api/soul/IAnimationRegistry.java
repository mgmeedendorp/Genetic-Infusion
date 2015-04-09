package seremis.geninfusion.api.soul;

import java.util.List;

public interface IAnimationRegistry {

    /**
     * Register a new IAnimation.
     *
     * @param animation The IAnimation to register.
     * @param name      The unique name of the IAnimation.
     */
    public void register(String name, IAnimation animation);

    /**
     * Get an IAnimation.
     *
     * @param name The name of the IAnimation.
     * @return The IAnimation with the name.
     */
    public IAnimation getAnimation(String name);

    /**
     * Get an IAnimation's name from an instance of IAnimation.
     *
     * @param animation The IAnimation instance to get the name of.
     * @return The name of the IAnimation.
     */
    public String getName(IAnimation animation);

    /**
     * Get a List of all the registered IAnimations.
     */
    public List<IAnimation> getAnimations();
}
