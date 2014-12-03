package seremis.geninfusion.soul;

import seremis.geninfusion.api.soul.ITrait;
import seremis.geninfusion.api.soul.ITraitRegistry;
import seremis.geninfusion.api.soul.SoulHelper;

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

    public static void orderTraits() {
        for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
            TraitHandler.applyArmorCalculations.add(trait);
            TraitHandler.applyPotionDamageCalculations.add(trait);
            TraitHandler.attackEntity.add(trait);
            TraitHandler.attackEntityAsMob.add(trait);
            TraitHandler.attackEntityFrom.add(trait);
            TraitHandler.damageEntity.add(trait);
            TraitHandler.entityDeath.add(trait);
            TraitHandler.interact.add(trait);
            TraitHandler.entityUpdate.add(trait);
            TraitHandler.findPlayerToAttack.add(trait);
            TraitHandler.firstTick.add(trait);
            TraitHandler.onKillEntity.add(trait);
            TraitHandler.playSoundAtEntity.add(trait);
            TraitHandler.spawnEntityFromEgg.add(trait);
            TraitHandler.updateAITick.add(trait);
            TraitHandler.damageArmor.add(trait);
        }
    }
}
