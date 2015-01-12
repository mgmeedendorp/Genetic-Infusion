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
    public ITrait getTrait(String name) {
        return traits.containsKey(name) ? traits.get(name) : null;
    }

    @Override
    public String getName(ITrait trait) {
        return names.containsKey(trait) ? names.get(trait) : null;
    }

    @Override
    public int getId(String name) {
        return ids.containsKey(name) && getTrait(name) != null ? ids.get(getTrait(name)) : null;
    }

    @Override
    public int getId(ITrait trait) {
        return getId(getName(trait));
    }

    @Override
    public LinkedList<ITrait> getTraits() {
        return new LinkedList<ITrait>(traits.values());
    }

    public HashMap<ITrait, ArrayList<ITrait>> overrideSupers = new HashMap<ITrait, ArrayList<ITrait>>();
    public HashMap<ITrait, ArrayList<ITrait>> overrideSubs = new HashMap<ITrait, ArrayList<ITrait>>();

    @Override
    public void makeTraitOverride(ITrait superTrait, ITrait subTrait) {
        if(overrideSupers.isEmpty() || !overrideSupers.containsKey(superTrait)) {
            ArrayList<ITrait> superList = new ArrayList<ITrait>();
            superList.add(superTrait);
            overrideSupers.put(subTrait, superList);
        } else {
            ArrayList<ITrait> superList = overrideSupers.get(superTrait);
            superList.add(superTrait);
            overrideSupers.put(subTrait, superList);
        }
        if(overrideSubs.isEmpty() || !overrideSubs.containsKey(superTrait)) {
            ArrayList<ITrait> subList = new ArrayList<ITrait>();
            subList.add(subTrait);
            overrideSubs.put(superTrait, subList);
        } else {
            ArrayList<ITrait> subList = overrideSubs.get(superTrait);
            subList.add(subTrait);
            overrideSubs.put(superTrait, subList);
        }
    }

    @Override
    public void makeTraitOverride(String superName, String subName) {
        makeTraitOverride(getTrait(superName), getTrait(subName));
    }

    @Override
    public ArrayList<ITrait> getOverridden(ITrait trait) {
        return overrideSupers.get(trait);
    }

    @Override
    public ArrayList<ITrait> getOverriding(ITrait trait) {
        return overrideSubs.get(trait);
    }

    @Override
    public ArrayList<ITrait> getOverridden(String name) {
        return getOverridden(getTrait(name));
    }

    @Override
    public ArrayList<ITrait> getOverriding(String name) {
        return getOverriding(getTrait(name));
    }

    @Override
    public boolean isOverridden(ITrait trait) {
        return overrideSubs.containsKey(trait);
    }

    @Override
    public boolean isOverriding(ITrait trait) {
        return overrideSupers.containsKey(trait);
    }

    public LinkedList<ITrait> orderedTraits = new LinkedList<ITrait>();

    @Override
    public LinkedList<ITrait> getOrderedTraits() {
        if(orderedTraits.isEmpty()) {
            for(ITrait trait : getTraits()) {
                if(!isOverridden(trait)) {
                    orderedTraits.add(trait);
                }
            }
        }
        return orderedTraits;
    }
}
