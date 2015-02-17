package seremis.geninfusion.soul;

import seremis.geninfusion.api.soul.ITrait;
import seremis.geninfusion.api.soul.ITraitRegistry;

import java.util.*;

public class TraitRegistry implements ITraitRegistry {

    public LinkedHashMap<String, ITrait> traits = new LinkedHashMap<String, ITrait>();
    public LinkedHashMap<ITrait, String> names = new LinkedHashMap<ITrait, String>();
    public LinkedHashMap<ITrait, Integer> ids = new LinkedHashMap<ITrait, Integer>();

    public LinkedHashMap<ITrait, List<String>> methods = new LinkedHashMap<ITrait, List<String>>();

    @Override
    public void registerTrait(String name, ITrait trait) {
        if(!traits.containsKey(name)) {
            traits.put(name, trait);
            names.put(trait, name);
            ids.put(trait, ids.size());
        }
    }

    @Override
    public void unregisterTrait(ITrait trait) {
        traits.remove(getName(trait));
        names.remove(trait);
        ids.remove(trait);
    }

    @Override
    public void unregisterTrait(String name) {
        unregisterTrait(getTrait(name));
    }

    @Override
    public ITrait getTrait(String name) {
        return traits.containsKey(name) ? traits.get(name) : null;
    }

    @Override
    public String getName(ITrait trait) {
        return names.containsKey(trait) ? names.get(trait) : null;
    }

    @Override
    public int getId(String name) {
        return ids.get(getTrait(name));
    }

    @Override
    public int getId(ITrait trait) {
        return getId(getName(trait));
    }

    @Override
    public LinkedList<ITrait> getTraits() {
        return new LinkedList<ITrait>(traits.values());
    }
}
