package seremis.geninfusion.soul;

import seremis.geninfusion.api.soul.ITrait;
import seremis.geninfusion.api.soul.ITraitRegistry;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.TraitMethods;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

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
    public void registerTrait(String name, ITrait trait, String... methods) {
        registerTrait(name, trait);
        for(String str : methods) {
            registerTraitMethod(trait, str);
        }
    }

    @Override
    public void registerTraitMethod(ITrait trait, String traitMethod) {
        if(traitMethod.equals(TraitMethods.METHOD_ON_UPDATE)) {
            TraitHandler.entityUpdate.add(trait);
        } else if(traitMethod.equals(TraitMethods.METHOD_INTERACT)) {
            TraitHandler.entityRightClicked.add(trait);
        } else if(traitMethod.equals(TraitMethods.METHOD_ON_DEATH)) {
            TraitHandler.entityDeath.add(trait);
        } else if(traitMethod.equals(TraitMethods.METHOD_ON_KILL_ENTITY)) {
            TraitHandler.onKillEntity.add(trait);
        } else if(traitMethod.equals(TraitMethods.METHOD_ATTACK_ENTITY_FROM)) {
            TraitHandler.attackEntityFrom.add(trait);
        } else if(traitMethod.equals(TraitMethods.METHOD_SPAWN_FROM_EGG)) {
            TraitHandler.spawnEntityFromEgg.add(trait);
        } else if(traitMethod.equals(TraitMethods.METHOD_PLAY_SOUND_AT_ENTITY)) {
            TraitHandler.playSoundAtEntity.add(trait);
        } else if(traitMethod.equals(TraitMethods.METHOD_DAMAGE_ENTITY)) {
            TraitHandler.damageEntity.add(trait);
        } else if(traitMethod.equals(TraitMethods.METHOD_UPDATE_AI_TICK)) {
            TraitHandler.updateAITick.add(trait);
        } else if(traitMethod.equals(TraitMethods.METHOD_FIRST_TICK)) {
            TraitHandler.firstTick.add(trait);
        } else if(traitMethod.equals(TraitMethods.METHOD_ATTACK_ENTITY_AS_MOB)) {
            TraitHandler.attackEntityAsMob.add(trait);
        } else if(traitMethod.equals(TraitMethods.METHOD_ATTACK_ENTITY)) {
            TraitHandler.attackEntity.add(trait);
        } else if(traitMethod.equals(TraitMethods.METHOD_FIND_PLAYER_TO_ATTACK)) {
            TraitHandler.findPlayerToAttack.add(trait);
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
        return ids.containsKey(name) ? ids.get(name) : null;
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
