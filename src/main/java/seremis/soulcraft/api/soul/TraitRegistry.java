package seremis.soulcraft.api.soul;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class TraitRegistry {

	public static LinkedHashMap<String, ITrait> traits = new LinkedHashMap<String, ITrait>();
	public static LinkedHashMap<ITrait, String> names = new LinkedHashMap<ITrait, String>();
	public static LinkedHashMap<ITrait, Integer> ids = new LinkedHashMap<ITrait, Integer>();
	
	public static void registerTrait(String name, ITrait trait) {
		if(!traits.containsKey(name)) {
			traits.put(name, trait);
			names.put(trait, name);
			ids.put(trait, ids.size());
		}
	}
	
	public static ITrait getTrait(String name) {
		return traits.containsKey(name) ? traits.get(name) : null;
	}
	
	public static String getName(ITrait trait) {
		return names.containsKey(trait) ? names.get(trait) : null;
	}
	
	public static int getId(String name) {
		return ids.containsKey(name) ? ids.get(name) : null;
	}
	
	public static int getId(ITrait trait) {
		return getId(getName(trait));
	}
	
	public static LinkedList<ITrait> getTraits() {
		return new LinkedList<ITrait>(traits.values());
	}
	
	/**
	 * This gets called in postinit, register your ITraits before that.
	 * Do NOT call this method yourself!
	 */
	public static void orderTraits() {
		
		TraitHandler.entityInit = new LinkedList<ITrait>();
		TraitHandler.entityUpdate = new LinkedList<ITrait>();
		TraitHandler.entityRightClicked = new LinkedList<ITrait>();
		TraitHandler.entityDeath = new LinkedList<ITrait>();
		TraitHandler.onKillEntity = new LinkedList<ITrait>();
		TraitHandler.attackEntityFrom = new LinkedList<ITrait>();
		TraitHandler.spawnEntityFromEgg = new LinkedList<ITrait>();
		TraitHandler.playSoundAtEntity = new LinkedList<ITrait>();
		TraitHandler.damageEntity = new LinkedList<ITrait>();
		TraitHandler.updateAITick = new LinkedList<ITrait>();
		
		for(ITrait trait : getTraits()) {
			TraitHandler.entityInit.add(trait);
			TraitHandler.entityUpdate.add(trait);
			TraitHandler.entityRightClicked.add(trait);
			TraitHandler.entityDeath.add(trait);
			TraitHandler.onKillEntity.add(trait);
			TraitHandler.attackEntityFrom.add(trait);
			TraitHandler.spawnEntityFromEgg.add(trait);
			TraitHandler.playSoundAtEntity.add(trait);
			TraitHandler.damageEntity.add(trait);
			TraitHandler.updateAITick.add(trait);
		}
		
		for(ITrait trait : getTraits()) {
			Class clazz = trait.getClass();
			Method[] methods = clazz.getMethods();
			for(Method method : methods) {
				Annotation[] annotations = method.getAnnotations();
				for(Annotation annotation : annotations) {
					if(annotation instanceof TraitDependencies) {
						String[] dependencies = ((TraitDependencies) annotation).dependencies();
						for(String dep : dependencies) {
							if(method.getName() == "onInit") {
								if(traits.containsKey(dep)) {
									ITrait trait2 = traits.get(dep);
									int traitIndex = TraitHandler.entityInit.indexOf(trait2);
									TraitHandler.entityInit.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.entityInit.addFirst(trait);
								} else if(dep.equals("last")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.entityInit.addLast(trait);
								}
							} else if(method.getName() == "onUpdate") {
								if(traits.containsKey(dep)) {
									ITrait trait2 = traits.get(dep);
									int traitIndex = TraitHandler.entityUpdate.indexOf(trait2);
									TraitHandler.entityUpdate.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.entityUpdate.addFirst(trait);
								} else if(dep.equals("last")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.entityUpdate.addLast(trait);
								}
							} else if(method.getName() == "onInteract") {
								if(traits.containsKey(dep)) {
									ITrait trait2 = traits.get(dep);
									int traitIndex = TraitHandler.entityRightClicked.indexOf(trait2);
									TraitHandler.entityRightClicked.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.entityRightClicked.addFirst(trait);
								} else if(dep.equals("last")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.entityRightClicked.addLast(trait);
								}
							} else if(method.getName() == "onDeath") {
								if(traits.containsKey(dep)) {
									ITrait trait2 = traits.get(dep);
									int traitIndex = TraitHandler.entityDeath.indexOf(trait2);
									TraitHandler.entityDeath.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.entityDeath.addFirst(trait);
								} else if(dep.equals("last")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.entityDeath.addLast(trait);
								}
							} else if(method.getName() == "onKillEntity") {
								if(traits.containsKey(dep)) {
									ITrait trait2 = traits.get(dep);
									int traitIndex = TraitHandler.onKillEntity.indexOf(trait2);
									TraitHandler.onKillEntity.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.onKillEntity.addFirst(trait);
								} else if(dep.equals("last")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.onKillEntity.addLast(trait);
								}
							} else if(method.getName() == "onEntityAttacked") {
								if(traits.containsKey(dep)) {
									ITrait trait2 = traits.get(dep);
									int traitIndex = TraitHandler.attackEntityFrom.indexOf(trait2);
									TraitHandler.attackEntityFrom.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.attackEntityFrom.addFirst(trait);
								} else if(dep.equals("last")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.attackEntityFrom.addLast(trait);
								}
							} else if(method.getName() == "onSpawnWithEgg") {
								if(traits.containsKey(dep)) {
									ITrait trait2 = traits.get(dep);
									int traitIndex = TraitHandler.spawnEntityFromEgg.indexOf(trait2);
									TraitHandler.spawnEntityFromEgg.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.spawnEntityFromEgg.addFirst(trait);
								} else if(dep.equals("last")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.spawnEntityFromEgg.addLast(trait);
								}
							} else if(method.getName() == "playSound") {
								if(traits.containsKey(dep)) {
									ITrait trait2 = traits.get(dep);
									int traitIndex = TraitHandler.playSoundAtEntity.indexOf(trait2);
									TraitHandler.playSoundAtEntity.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.playSoundAtEntity.addFirst(trait);
								} else if(dep.equals("last")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.playSoundAtEntity.addLast(trait);
								}
							} else if(method.getName() == "damageEntity") {
								if(traits.containsKey(dep)) {
									ITrait trait2 = traits.get(dep);
									int traitIndex = TraitHandler.damageEntity.indexOf(trait2);
									TraitHandler.damageEntity.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.damageEntity.addFirst(trait);
								} else if(dep.equals("last")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.damageEntity.addLast(trait);
								}
							} else if(method.getName() == "updateAITask") {
								if(traits.containsKey(dep)) {
									ITrait trait2 = traits.get(dep);
									int traitIndex = TraitHandler.updateAITick.indexOf(trait2);
									TraitHandler.updateAITick.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.updateAITick.addFirst(trait);
								} else if(dep.equals("last")) {
									ITrait trait2 = traits.get(dep);
									TraitHandler.updateAITick.addLast(trait);
								}
							}
						}
					}
				}
			}
		}
	}
}
