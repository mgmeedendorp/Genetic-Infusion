package seremis.geninfusion.soul

import net.minecraft.item.ItemStack
import seremis.geninfusion.api.soul.SoulHelper._
import seremis.geninfusion.api.soul.lib.Animations._
import seremis.geninfusion.api.soul.lib.Genes._
import seremis.geninfusion.api.soul.lib.Traits._
import seremis.geninfusion.soul.AlleleType._
import seremis.geninfusion.soul.entity.animation.{AnimationFourLegged, AnimationHead, AnimationTwoArmed, AnimationTwoLegged}
import seremis.geninfusion.soul.gene._
import seremis.geninfusion.soul.gene.newAI._
import seremis.geninfusion.soul.standardSoul.{StandardSoulCreeper, StandardSoulSkeleton, StandardSoulZombie}
import seremis.geninfusion.soul.traits._

object ModSouls {

    def init() {
        geneRegistry.registerGene(GENE_MAX_HEALTH, classOf[Double])
        geneRegistry.registerGene(GENE_INVULNERABLE, classOf[Boolean]).noMutations
        geneRegistry.registerGene(GENE_ATTACK_DAMAGE, classOf[Double])
        geneRegistry.registerGene(GENE_MOVEMENT_SPEED, classOf[Double])
        geneRegistry.registerGene(GENE_FOLLOW_RANGE, classOf[Double])
        geneRegistry.registerGene(GENE_BURNS_IN_DAYLIGHT, classOf[Boolean])
        geneRegistry.registerGene(GENE_DROWNS_IN_WATER, classOf[Boolean])
        geneRegistry.registerGene(GENE_DROWNS_IN_AIR, classOf[Boolean])
        geneRegistry.registerGene(GENE_IMMUNE_TO_FIRE, classOf[Boolean])
        geneRegistry.registerGene(GENE_MAX_HURT_RESISTANT_TIME, classOf[Int])
        geneRegistry.registerGene(GENE_PICKS_UP_ITEMS, classOf[Boolean])
        geneRegistry.registerGene(GENE_ITEM_DROPS, classOf[Array[ItemStack]])
        geneRegistry.registerGene(GENE_RARE_ITEM_DROPS, classOf[Array[ItemStack]])
        geneRegistry.registerGene(GENE_RARE_ITEM_DROP_CHANCES, classOf[Array[Float]])
        geneRegistry.registerGene(GENE_EQUIPMENT_DROP_CHANCES, classOf[Array[Float]])
        geneRegistry.registerGene(GENE_LIVING_SOUND, classOf[String])
        geneRegistry.registerGene(GENE_HURT_SOUND, classOf[String])
        geneRegistry.registerGene(GENE_DEATH_SOUND, classOf[String])
        geneRegistry.registerGene(GENE_WALK_SOUND, classOf[String])
        geneRegistry.registerGene(GENE_SPLASH_SOUND, classOf[String])
        geneRegistry.registerGene(GENE_SWIM_SOUND, classOf[String])
        geneRegistry.registerGene(GENE_SOUND_VOLUME, classOf[Float])
        geneRegistry.registerGene(GENE_CREATURE_ATTRIBUTE, classOf[Int])
        geneRegistry.registerGene(GENE_TELEPORT_TIME_IN_PORTAL, classOf[Int])
        geneRegistry.registerGene(GENE_PORTAL_COOLDOWN, classOf[Int])
        geneRegistry.registerGene(GENE_KNOCKBACK_RESISTANCE, classOf[Double])
        geneRegistry.registerGene(GENE_SHOULD_DESPAWN, classOf[Boolean])
        geneRegistry.registerGene(GENE_TALK_INTERVAL, classOf[Int])
        geneRegistry.registerGene(GENE_SET_ON_FIRE_FROM_ATTACK, classOf[Boolean])
        geneRegistry.registerGene(GENE_EXPERIENCE_VALUE, classOf[Int])
        geneRegistry.registerGene(GENE_VERTICAL_FACE_SPEED, classOf[Int])
        geneRegistry.registerGene(GENE_IS_CREATURE, classOf[Boolean]).noMutations
        geneRegistry.registerGene(GENE_CEASE_AI_MOVEMENT, classOf[Boolean])
        geneRegistry.registerGene(GENE_CHILDREN_BURN_IN_DAYLIGHT, classOf[Boolean])
        geneRegistry.registerGene(GENE_IS_TAMEABLE, classOf[Boolean])
        geneRegistry.registerGene(GENE_WIDTH, classOf[Float]).noMutations
        geneRegistry.registerGene(GENE_HEIGHT, classOf[Float]).noMutations

        geneRegistry.registerGene(GENE_AI_ARROW_ATTACK_MAX_RANGED_ATTACK_TIME, classOf[Int])
        geneRegistry.registerGene(GENE_AI_ARROW_ATTACK_MIN_RANGED_ATTACK_TIME, classOf[Int])
        geneRegistry.registerGene(GENE_AI_ARROW_ATTACK_MOVE_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_ARROW_ATTACK_RANGED_ATTACK_TIME_MODIFIER, classOf[Float])
        geneRegistry.registerGene(GENE_AI_ARROW_ATTACK_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_ARROW_ATTACK, new GeneAIArrowAttack).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_ATTACK_ON_COLLIDE_TARGET, classOf[Array[Class[_]]])
        geneRegistry.registerGene(GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED, new GeneMoveSpeed(classOf[Array[Double]]))
        geneRegistry.registerGene(GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY, classOf[Array[Boolean]])
        geneRegistry.registerGene(GENE_AI_ATTACK_ON_COLLIDE_INDEX, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_ATTACK_ON_COLLIDE, new GeneAIAttackOnCollide).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_AVOID_ENTITY_TARGET, classOf[Array[Class[_]]])
        geneRegistry.registerGene(GENE_AI_AVOID_ENTITY_RANGE, classOf[Array[Float]])
        geneRegistry.registerGene(GENE_AI_AVOID_ENTITY_NEAR_SPEED, new GeneMoveSpeed(classOf[Array[Double]]))
        geneRegistry.registerGene(GENE_AI_AVOID_ENTITY_FAR_SPEED, new GeneMoveSpeed(classOf[Array[Double]]))
        geneRegistry.registerGene(GENE_AI_AVOID_ENTITY_INDEX, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_AVOID_ENTITY, new GeneAIAvoidEntity).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_BEG_RANGE, classOf[Float])
        geneRegistry.registerGene(GENE_AI_BEG_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_BEG, new GeneAIBeg).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_BREAK_DOOR_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_BREAK_DOOR, new GeneAIBreakDoor).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_CONTROLLED_BY_PLAYER_MAX_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_CONTROLLED_BY_PLAYER_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_CONTROLLED_BY_PLAYER, new GeneAIControlledByPlayer).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_CREEPER_SWELL_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_CREEPER_SWELL, new GeneAICreeperSwell).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_DEFEND_VILLAGE_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_DEFEND_VILLAGE, new GeneAIDefendVillage).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_EAT_GRASS_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_EAT_GRASS, new GeneAIEatGrass).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_FLEE_SUN_MOVE_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_FLEE_SUN_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_FLEE_SUN, new GeneAIFleeSun).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_FOLLOW_GOLEM_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_FOLLOW_GOLEM, new GeneAIFollowGolem).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_FOLLOW_OWNER_MOVE_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_FOLLOW_OWNER_MIN_DISTANCE, classOf[Float])
        geneRegistry.registerGene(GENE_AI_FOLLOW_OWNER_MAX_DISTANCE, classOf[Float])
        geneRegistry.registerGene(GENE_AI_FOLLOW_OWNER_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_FOLLOW_OWNER, new GeneAIFollowOwner).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_FOLLOW_PARENT_MOVE_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_FOLLOW_PARENT_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_FOLLOW_PARENT, new GeneAIFollowParent).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_HURT_BY_TARGET_CALL_HELP, classOf[Boolean])
        geneRegistry.registerGene(GENE_AI_HURT_BY_TARGET_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_HURT_BY_TARGET, new GeneAIHurtByTarget).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_LEAP_AT_TARGET_MOTION_Y, classOf[Float])
        geneRegistry.registerGene(GENE_AI_LEAP_AT_TARGET_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_LEAP_AT_TARGET, new GeneAILeapAtTarget).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_LOOK_AT_TRADE_PLAYER_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_LOOK_AT_TRADE_PLAYER, new GeneAILookAtTradePlayer).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_LOOK_AT_VILLAGER_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_LOOK_AT_VILLAGER, new GeneAILookAtVillager).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_LOOK_IDLE_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_LOOK_IDLE, new GeneAILookIdle).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_MATE_MOVE_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_MATE_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_MATE, new GeneAIMate).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_MOVE_INDOORS_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_MOVE_INDOORS, new GeneAIMoveIndoors).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_MOVE_THROUGH_VILLAGE_MOVE_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_MOVE_THROUGH_VILLAGE_IS_NOCTURNAL, classOf[Boolean])
        geneRegistry.registerGene(GENE_AI_MOVE_THROUGH_VILLAGE_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_MOVE_THROUGH_VILLAGE, new GeneAIMoveThroughVillage).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_MOVE_TOWARDS_RESTRICTION_MOVE_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_MOVE_TOWARDS_RESTRICTION_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_MOVE_TOWARDS_RESTRICTION, new GeneAIMoveTowardsRestriction).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_MOVE_TOWARDS_TARGET_MOVE_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_MOVE_TOWARDS_TARGET_MAX_DISTANCE, classOf[Float])
        geneRegistry.registerGene(GENE_AI_MOVE_TOWARDS_TARGET_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_MOVE_TOWARDS_TARGET, new GeneAIMoveTowardsTarget).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET, classOf[Array[Class[_]]])
        geneRegistry.registerGene(GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE, classOf[Array[Int]])
        geneRegistry.registerGene(GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE, classOf[Array[Boolean]])
        geneRegistry.registerGene(GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY, classOf[Array[Boolean]])
        geneRegistry.registerGene(GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR, classOf[Array[String]])
        geneRegistry.registerGene(GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_NEAREST_ATTACKABLE_TARGET, new GeneAINearestAttackableTarget).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_OCELOT_ATTACK_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_OCELOT_ATTACK, new GeneAIOcelotAttack).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_OCELOT_SIT_MOVE_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_OCELOT_SIT_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_OCELOT_SIT, new GeneAIOcelotSit).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_OPEN_DOOR_CLOSE_DOOR, classOf[Boolean])
        geneRegistry.registerGene(GENE_AI_OPEN_DOOR_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_OPEN_DOOR, new GeneAIOpenDoor).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_OWNER_HURT_BY_TARGET_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_OWNER_HURT_BY_TARGET, new GeneAIOwnerHurtByTarget).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_OWNER_HURT_TARGET_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_OWNER_HURT_TARGET, new GeneAIOwnerHurtTarget).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_PANIC_MOVE_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_PANIC_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_PANIC, new GeneAIPanic).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_PLAY_MOVE_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_PLAY_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_PLAY, new GeneAIPlay).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_RESTRICT_OPEN_DOOR_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_RESTRICT_OPEN_DOOR, new GeneAIRestrictOpenDoor).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_RESTRICT_SUN_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_RESTRICT_SUN, new GeneAIRestrictSun).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_RUN_AROUND_LIKE_CRAZY_MOVE_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_RUN_AROUND_LIKE_CRAZY_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_RUN_AROUND_LIKE_CRAZY, new GeneAIRunAroundLikeCrazy).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_SIT_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_SIT, new GeneAISit).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_SWIMMING_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_SWIMMING, new GeneAISwimming).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_TARGET_NON_TAMED_VISIBLE, classOf[Array[Boolean]])
        geneRegistry.registerGene(GENE_AI_TARGET_NON_TAMED_TARGET_CHANCE, classOf[Array[Int]])
        geneRegistry.registerGene(GENE_AI_TARGET_NON_TAMED_TARGET, classOf[Array[Class[_]]])
        geneRegistry.registerGene(GENE_AI_TARGET_NON_TAMED_INDEX, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_TARGET_NON_TAMED, new GeneAITargetNonTamed).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_TEMPT_ITEM, classOf[Array[ItemStack]])
        geneRegistry.registerGene(GENE_AI_TEMPT_MOVE_SPEED, new GeneMoveSpeed(classOf[Array[Double]]))
        geneRegistry.registerGene(GENE_AI_TEMPT_SCARED_BY_PLAYER, classOf[Array[Boolean]])
        geneRegistry.registerGene(GENE_AI_TEMPT_INDEX, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_TEMPT, new GeneAITempt).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_TRADE_PLAYER_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_TRADE_PLAYER, new GeneAITradePlayer).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_VILLAGER_MATE_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_VILLAGER_MATE, new GeneAIVillagerMate).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_WANDER_MOVE_SPEED, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GENE_AI_WANDER_INDEX, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_WANDER, new GeneAIWander).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_TARGET, classOf[Array[Class[_]]])
        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_RANGE, classOf[Array[Float]])
        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_CHANCE, classOf[Array[Float]])
        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_INDEX, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_WATCH_CLOSEST, new GeneAIWatchClosest).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_2_TARGET, classOf[Array[Class[_]]])
        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_2_RANGE, classOf[Array[Float]])
        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_2_CHANCE, classOf[Array[Float]])
        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_2_INDEX, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GENE_AI_WATCH_CLOSEST_2, new GeneAIWatchClosest2).setCombinedInherit


        geneRegistry.registerMasterGene(GENE_USE_NEW_AI, new GeneUseNewAI)
        geneRegistry.registerMasterGene(GENE_USE_OLD_AI, new GeneUseOldAI)

        geneRegistry.registerGene(GENE_TEXTURE, classOf[String])
        geneRegistry.registerGene(GENE_MODEL, new GeneModel)
        geneRegistry.registerCustomInheritance(GENE_MODEL)

        geneRegistry.registerGene(GENE_FUSE_TIME, classOf[Int])
        geneRegistry.registerGene(GENE_EXPLOSION_RADIUS, classOf[Int])
        geneRegistry.registerGene(GENE_CAN_BE_CHARGED, classOf[Boolean])
        geneRegistry.registerGene(GENE_FLINT_AND_STEEL_IGNITE, classOf[Boolean])

        geneRegistry.registerGene(GENE_KILLED_BY_SPECIFIC_ENTITY_DROPS, classOf[Array[ItemStack]])
        geneRegistry.registerGene(GENE_KILLED_BY_SPECIFIC_ENTITY_ENTITY, classOf[Class[_]])
        geneRegistry.registerMasterGene(GENE_DROPS_ITEM_WHEN_KILLED_BY_SPECIFIC_ENTITY, new GeneDropsItemWhenKilledBySpecificEntity)

        traitRegistry.registerTrait(TRAIT_FIRE, new TraitFire)
        traitRegistry.registerTrait(TRAIT_MOVEMENT, new TraitMovement)
        traitRegistry.registerTrait(TRAIT_ATTACKED, new TraitAttacked)
        traitRegistry.registerTrait(TRAIT_ITEM_PICKUP, new TraitItemPickup)
        traitRegistry.registerTrait(TRAIT_ITEM_DROPS, new TraitItemDrops)
        traitRegistry.registerTrait(TRAIT_FLUIDS, new TraitFluids)
        traitRegistry.registerTrait(TRAIT_SOUNDS, new TraitSounds)
        traitRegistry.registerTrait(TRAIT_ATTRIBUTES, new TraitInitialValues)
        traitRegistry.registerTrait(TRAIT_ATTACK, new TraitAttack)
        traitRegistry.registerTrait(TRAIT_AI, new TraitAI)
        traitRegistry.registerTrait(TRAIT_RENDER, new TraitRender)
        traitRegistry.registerTrait(TRAIT_HOME_AREA, new TraitHomeArea)
        traitRegistry.registerTrait(TRAIT_TEXTURE, new TraitTexture)
        traitRegistry.registerTrait(TRAIT_NAVIGATE, new TraitNavigate)
        traitRegistry.registerTrait(TRAIT_ANIMATION, new TraitAnimation)
        traitRegistry.registerTrait(TRAIT_EXPLODE, new TraitExplode)
        traitRegistry.registerTrait(TRAIT_NAME_TAG, new TraitNameTag)

        standardSoulRegistry.register(new StandardSoulZombie)
        standardSoulRegistry.register(new StandardSoulSkeleton)
        standardSoulRegistry.register(new StandardSoulCreeper)

        animationRegistry.register(ANIMATION_WALK_TWO_LEGGED, new AnimationTwoLegged)
        animationRegistry.register(ANIMATION_WALK_TWO_ARMED, new AnimationTwoArmed)
        animationRegistry.register(ANIMATION_HEAD, new AnimationHead)
        animationRegistry.register(ANIMATION_WALK_FOUR_LEGGED, new AnimationFourLegged)

        alleleTypeRegistry.registerAlleleType(typeBoolean)
        alleleTypeRegistry.registerAlleleType(typeByte)
        alleleTypeRegistry.registerAlleleType(typeShort)
        alleleTypeRegistry.registerAlleleType(typeInt)
        alleleTypeRegistry.registerAlleleType(typeFloat)
        alleleTypeRegistry.registerAlleleType(typeDouble)
        alleleTypeRegistry.registerAlleleType(typeLong)
        alleleTypeRegistry.registerAlleleType(typeString)
        alleleTypeRegistry.registerAlleleType(typeClass)
        alleleTypeRegistry.registerAlleleType(typeItemStack)
        alleleTypeRegistry.registerAlleleType(typeModelPart)
        alleleTypeRegistry.registerAlleleType(typeBooleanArray)
        alleleTypeRegistry.registerAlleleType(typeByteArray)
        alleleTypeRegistry.registerAlleleType(typeShortArray)
        alleleTypeRegistry.registerAlleleType(typeIntArray)
        alleleTypeRegistry.registerAlleleType(typeFloatArray)
        alleleTypeRegistry.registerAlleleType(typeDoubleArray)
        alleleTypeRegistry.registerAlleleType(typeLongArray)
        alleleTypeRegistry.registerAlleleType(typeStringArray)
        alleleTypeRegistry.registerAlleleType(typeClassArray)
        alleleTypeRegistry.registerAlleleType(typeItemStackArray)
        alleleTypeRegistry.registerAlleleType(typeModelPartArray)
    }
}
