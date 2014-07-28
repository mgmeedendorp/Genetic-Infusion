package seremis.geninfusion.soul;

import seremis.geninfusion.api.soul.ITrait;
import seremis.geninfusion.api.soul.ITraitRegistry;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.TraitDependencies;

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
        TraitHandler.firstTick = new LinkedList<ITrait>();
		
		for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
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
            TraitHandler.firstTick.add(trait);
		}
		
		for(ITrait trait : SoulHelper.traitRegistry.getTraits()) {
			Class clazz = trait.getClass();
			Method[] methods = clazz.getMethods();
			for(Method method : methods) {
				Annotation[] annotations = method.getAnnotations();
				for(Annotation annotation : annotations) {
					if(annotation instanceof TraitDependencies) {
						String[] dependencies = ((TraitDependencies) annotation).dependencies();
						for(String dep : dependencies) {
							if(method.getName().equals("onInit")) {
								if(SoulHelper.traitRegistry.getTraits().contains(SoulHelper.traitRegistry.getTrait(dep))) {
									ITrait trait2 = SoulHelper.traitRegistry.getTrait(dep);
									int traitIndex = TraitHandler.entityInit.indexOf(trait2);
									TraitHandler.entityInit.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									TraitHandler.entityInit.addFirst(trait);
								} else if(dep.equals("last")) {
									TraitHandler.entityInit.addLast(trait);
								}
							} else if(method.getName().equals("onUpdate")) {
								if(SoulHelper.traitRegistry.getTraits().contains(SoulHelper.traitRegistry.getTrait(dep))) {
									ITrait trait2 = SoulHelper.traitRegistry.getTrait(dep);
									int traitIndex = TraitHandler.entityUpdate.indexOf(trait2);
									TraitHandler.entityUpdate.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									TraitHandler.entityUpdate.addFirst(trait);
								} else if(dep.equals("last")) {
									TraitHandler.entityUpdate.addLast(trait);
								}
							} else if(method.getName().equals("onInteract")) {
								if(SoulHelper.traitRegistry.getTraits().contains(SoulHelper.traitRegistry.getTrait(dep))) {
									ITrait trait2 = SoulHelper.traitRegistry.getTrait(dep);
									int traitIndex = TraitHandler.entityRightClicked.indexOf(trait2);
									TraitHandler.entityRightClicked.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									TraitHandler.entityRightClicked.addFirst(trait);
								} else if(dep.equals("last")) {
									TraitHandler.entityRightClicked.addLast(trait);
								}
							} else if(method.getName().equals("onDeath")) {
								if(SoulHelper.traitRegistry.getTraits().contains(SoulHelper.traitRegistry.getTrait(dep))) {
									ITrait trait2 = SoulHelper.traitRegistry.getTrait(dep);
									int traitIndex = TraitHandler.entityDeath.indexOf(trait2);
									TraitHandler.entityDeath.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									TraitHandler.entityDeath.addFirst(trait);
								} else if(dep.equals("last")) {
									TraitHandler.entityDeath.addLast(trait);
								}
							} else if(method.getName().equals("onKillEntity")) {
								if(SoulHelper.traitRegistry.getTraits().contains(SoulHelper.traitRegistry.getTrait(dep))) {
									ITrait trait2 = SoulHelper.traitRegistry.getTrait(dep);
									int traitIndex = TraitHandler.onKillEntity.indexOf(trait2);
									TraitHandler.onKillEntity.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									TraitHandler.onKillEntity.addFirst(trait);
								} else if(dep.equals("last")) {
									TraitHandler.onKillEntity.addLast(trait);
								}
							} else if(method.getName().equals("onEntityAttacked")) {
								if(SoulHelper.traitRegistry.getTraits().contains(SoulHelper.traitRegistry.getTrait(dep))) {
									ITrait trait2 = SoulHelper.traitRegistry.getTrait(dep);
									int traitIndex = TraitHandler.attackEntityFrom.indexOf(trait2);
									TraitHandler.attackEntityFrom.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									TraitHandler.attackEntityFrom.addFirst(trait);
								} else if(dep.equals("last")) {
									TraitHandler.attackEntityFrom.addLast(trait);
								}
							} else if(method.getName().equals("onSpawnWithEgg")) {
								if(SoulHelper.traitRegistry.getTraits().contains(SoulHelper.traitRegistry.getTrait(dep))) {
									ITrait trait2 = SoulHelper.traitRegistry.getTrait(dep);
									int traitIndex = TraitHandler.spawnEntityFromEgg.indexOf(trait2);
									TraitHandler.spawnEntityFromEgg.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									TraitHandler.spawnEntityFromEgg.addFirst(trait);
								} else if(dep.equals("last")) {
									TraitHandler.spawnEntityFromEgg.addLast(trait);
								}
							} else if(method.getName().equals("playSound")) {
								if(SoulHelper.traitRegistry.getTraits().contains(SoulHelper.traitRegistry.getTrait(dep))) {
									ITrait trait2 = SoulHelper.traitRegistry.getTrait(dep);
									int traitIndex = TraitHandler.playSoundAtEntity.indexOf(trait2);
									TraitHandler.playSoundAtEntity.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									TraitHandler.playSoundAtEntity.addFirst(trait);
								} else if(dep.equals("last")) {
									TraitHandler.playSoundAtEntity.addLast(trait);
								}
							} else if(method.getName().equals("damageEntity")) {
								if(SoulHelper.traitRegistry.getTraits().contains(SoulHelper.traitRegistry.getTrait(dep))) {
									ITrait trait2 = SoulHelper.traitRegistry.getTrait(dep);
									int traitIndex = TraitHandler.damageEntity.indexOf(trait2);
									TraitHandler.damageEntity.add(traitIndex, trait);
								} else if(dep.equals("first")) {
									TraitHandler.damageEntity.addFirst(trait);
								} else if(dep.equals("last")) {
									TraitHandler.damageEntity.addLast(trait);
								}
							} else if(method.getName().equals("updateAITask")) {
                                if(SoulHelper.traitRegistry.getTraits().contains(SoulHelper.traitRegistry.getTrait(dep))) {
                                    ITrait trait2 = SoulHelper.traitRegistry.getTrait(dep);
                                    int traitIndex = TraitHandler.updateAITick.indexOf(trait2);
                                    TraitHandler.updateAITick.add(traitIndex, trait);
                                } else if(dep.equals("first")) {
                                    TraitHandler.updateAITick.addFirst(trait);
                                } else if(dep.equals("last")) {
                                    TraitHandler.updateAITick.addLast(trait);
                                }
                            } else if(method.getName().equals("firstTick")) {
                                if(SoulHelper.traitRegistry.getTraits().contains(SoulHelper.traitRegistry.getTrait(dep))) {
                                    ITrait trait2 = SoulHelper.traitRegistry.getTrait(dep);
                                    int traitIndex = TraitHandler.firstTick.indexOf(trait2);
                                    TraitHandler.firstTick.add(traitIndex, trait);
                                } else if(dep.equals("first")) {
                                    TraitHandler.firstTick.addFirst(trait);
                                } else if(dep.equals("last")) {
                                    TraitHandler.firstTick.addLast(trait);
                                }
                            }
						}
					}
				}
			}
		}
	}
}
