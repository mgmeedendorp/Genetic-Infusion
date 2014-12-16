package seremis.geninfusion.api.soul;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Seremis
 */
public interface ITraitRegistry {

    public void registerTrait(String name, ITrait trait);

    public ITrait getTrait(String name);

    public String getName(ITrait trait);

    public int getId(ITrait trait);
    public int getId(String name);

    public LinkedList<ITrait> getTraits();


    public void makeTraitOverride(ITrait superTrait, ITrait subTrait);
    public void makeTraitOverride(String superName, String subName);

    public ArrayList<ITrait> getOverridden(ITrait trait);
    public ArrayList<ITrait> getOverridden(String name);

    public ArrayList<ITrait> getOverriding(ITrait trait);
    public ArrayList<ITrait> getOverriding(String name);

    public boolean isOverridden(ITrait trait);
    public boolean isOverriding(ITrait trait);


    public void makeTraitOverwrite(ITrait superTrait, ITrait subTrait);
    public void makeTraitOverwrite(String superName, String subName);

    public ArrayList<ITrait> getOverwritten(ITrait trait);
    public ArrayList<ITrait> getOverwritten(String name);

    public ArrayList<ITrait> getOverwriting(ITrait trait);
    public ArrayList<ITrait> getOverwriting(String name);

    public boolean isOverwritten(ITrait trait);
    public boolean isOverwriting(ITrait trait);
}
