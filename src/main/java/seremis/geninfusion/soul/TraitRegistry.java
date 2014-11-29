package seremis.geninfusion.soul;

import seremis.geninfusion.api.soul.ITrait;
import seremis.geninfusion.api.soul.ITraitRegistry;
import seremis.geninfusion.api.soul.SoulHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class TraitRegistry implements ITraitRegistry {

    public LinkedHashMap<String, ITrait> traits = new LinkedHashMap<String, ITrait>();
    public LinkedHashMap<ITrait, String> names = new LinkedHashMap<ITrait, String>();
    public LinkedHashMap<ITrait, Integer> ids = new LinkedHashMap<ITrait, Integer>();

    public void registerTrait(String name, ITrait trait) {
        if(!traits.containsKey(name)) {
            traits.put(name, trait);
            names.put(trait, name);
            ids.put(trait, ids.size());
        }
    }

    public ITrait getTrait(String name) {
        return traits.containsKey(name) ? traits.get(name) : null;
    }

    public String getName(ITrait trait) {
        return names.containsKey(trait) ? names.get(trait) : null;
    }

    public int getId(String name) {
        return ids.containsKey(name) ? ids.get(name) : null;
    }

    public int getId(ITrait trait) {
        return getId(getName(trait));
    }

    public LinkedList<ITrait> getTraits() {
        return new LinkedList<ITrait>(traits.values());
    }

    public static void orderTraits() {

        TraitHandler.entityUpdate = new LinkedList<ITrait>();
        TraitHandler.entityRightClicked = new LinkedList<ITrait>();
        TraitHandler.entityDeath = new LinkedList<ITrait>();
        TraitHandler.onKillEntity = new LinkedList<ITrait>();
        TraitHandler.attackEntityFrom = new LinkedList<ITrait>();
        TraitHandler.spawnEntityFromEgg = new LinkedList<ITrait>();
        TraitHandler.playSoundAtEntity = new LinkedList<ITrait>();
        TraitHandler.damageEntity = new LinkedList<ITrait>();
        TraitHandler.updateAITick = new LinkedList<ITrait>();
        TraitHandler.firstTick = new LinkedList<ITrait>();
        TraitHandler.attackEntityAsMob = new LinkedList<ITrait>();
        TraitHandler.attackEntity = new LinkedList<ITrait>();

        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            TraitHandler.entityUpdate.add(trait);
            TraitHandler.entityRightClicked.add(trait);
            TraitHandler.entityDeath.add(trait);
            TraitHandler.onKillEntity.add(trait);
            TraitHandler.attackEntityFrom.add(trait);
            TraitHandler.spawnEntityFromEgg.add(trait);
            TraitHandler.playSoundAtEntity.add(trait);
            TraitHandler.damageEntity.add(trait);
            TraitHandler.updateAITick.add(trait);
            TraitHandler.firstTick.add(trait);
            TraitHandler.attackEntityAsMob.add(trait);
            TraitHandler.attackEntity.add(trait);
        }
    }
}
