package seremis.geninfusion.api.soul.lib

//remove if not needed

object Genes {

    val ID = "geninfusion"

    val GENE_MAX_HEALTH = ID + ".gene.maxHealth"
    val GENE_INVULNERABLE = ID + ".gene.invulnerable"
    val GENE_ATTACK_DAMAGE = ID + ".gene.attackDamage"
    val GENE_MOVEMENT_SPEED = ID + ".gene.movementSpeed"
    val GENE_FOLLOW_RANGE = ID + ".gene.followRange"
    val GENE_BURNS_IN_DAYLIGHT = ID + ".gene.burnsInDaylight"
    val GENE_DROWNS_IN_WATER = ID + ".gene.drownsInWater"
    val GENE_DROWNS_IN_AIR = ID + ".gene.drownsInAir"
    val GENE_IMMUNE_TO_FIRE = ID + ".gene.isImmuneToFire"
    val GENE_MAX_HURT_RESISTANT_TIME = ID + ".gene.maxHurtResistantTime"
    val GENE_PICKS_UP_ITEMS = ID + ".gene.picksUpItems"
    val GENE_ITEM_DROPS = ID + ".gene.itemDrops"
    val GENE_RARE_ITEM_DROPS = ID + ".gene.rareItemDrops"
    val GENE_RARE_ITEM_DROP_CHANCES = ID + ".gene.rareItemDropChances"
    val GENE_EQUIPMENT_DROP_CHANCES = ID + ".gene.equipmentDropChances"
    val GENE_LIVING_SOUND = ID + ".gene.livingSound"
    val GENE_HURT_SOUND = ID + ".gene.hurtSound"
    val GENE_DEATH_SOUND = ID + ".gene.deathSound"
    val GENE_WALK_SOUND = ID + ".gene.walkSound"
    val GENE_SPLASH_SOUND = ID + ".gene.splashSound"
    val GENE_SWIM_SOUND = ID + ".gene.swimSound"
    val GENE_SOUND_VOLUME = ID + ".gene.soundVolume"
    val GENE_CREATURE_ATTRIBUTE = ID + ".gene.creatureAttribute"
    val GENE_TELEPORT_TIME_IN_PORTAL = ID + ".gene.teleportTimeInPortal"
    val GENE_PORTAL_COOLDOWN = ID + ".gene.portalCooldown"
    val GENE_KNOCKBACK_RESISTANCE = ID + ".gene.knockbackResistance"
    val GENE_SHOULD_DESPAWN = ID + ".gene.shouldDespawn"
    val GENE_TALK_INTERVAL = ID + ".gene.talkInterval"
    val GENE_SET_ON_FIRE_FROM_ATTACK = ID + ".gene.setOnFireFromAttack"
    val GENE_EXPERIENCE_VALUE = ID + ".gene.experienceValue"
    val GENE_USE_NEW_AI = ID + ".gene.useNewAI"
    val GENE_USE_OLD_AI = ID + ".gene.useOldAI"
    val GENE_VERTICAL_FACE_SPEED = ID + ".gene.verticalFaceSpeed"
    val GENE_IS_CREATURE = ID + ".gene.isCreature"
    val GENE_CEASE_AI_MOVEMENT = ID + ".gene.ceaseAIMovement"
    val GENE_CHILDREN_BURN_IN_DAYLIGHT = ID + ".gene.childrenBurn"
    val GENE_IS_TAMEABLE = ID + ".gene.isTameable"

    val GENE_AI_ARROW_ATTACK = ID + ".gene.aiArrowAttack"
    val GENE_AI_ARROW_ATTACK_INDEX = ID + ".gene.aiArrowAttackIndex"
    val GENE_AI_ARROW_ATTACK_MAX_RANGED_ATTACK_TIME = ID + ".gene.aiArrowAttackMaxRangedAttackTime"
    val GENE_AI_ARROW_ATTACK_MIN_RANGED_ATTACK_TIME = ID + ".gene.aiArrowAttackMinRangedAttackTime"
    val GENE_AI_ARROW_ATTACK_MOVE_SPEED = ID + ".gene.aiArrowAttackMoveSpeed"
    val GENE_AI_ARROW_ATTACK_RANGED_ATTACK_TIME_MODIFIER = ID + ".gene.aiArrowAttackRangedAttackTimeModifier"

    val GENE_AI_ATTACK_ON_COLLIDE = ID + ".gene.aiAttackOnCollide"
    val GENE_AI_ATTACK_ON_COLLIDE_INDEX = ID + ".gene.aiAttackOnCollideIndex"
    val GENE_AI_ATTACK_ON_COLLIDE_TARGET = ID + ".gene.aiAttackOnCollideTarget"
    val GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED = ID + ".gene.aiAttackOnCollideMoveSpeed"
    val GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY = ID + ".gene.aiAttackOnCollideLongMemory"

    val GENE_AI_AVOID_ENTITY = ID + ".gene.aiAvoidEntity"
    val GENE_AI_AVOID_ENTITY_INDEX = ID + ".gene.aiAvoidEntityIndex"
    val GENE_AI_AVOID_ENTITY_TARGET = ID + ".gene.aiAvoidEntityTarget"
    val GENE_AI_AVOID_ENTITY_RANGE = ID + ".gene.aiAvoidEntityRange"
    val GENE_AI_AVOID_ENTITY_FAR_SPEED = ID + ".gene.aiAvoidEntityFarSpeed"
    val GENE_AI_AVOID_ENTITY_NEAR_SPEED = ID + ".gene.aiAvoidEntityNearSpeed"

    val GENE_AI_BEG = ID + ".gene.aiBeg"
    val GENE_AI_BEG_INDEX = ID + ".gene.aiBegIndex"
    val GENE_AI_BEG_RANGE = ID + ".gene.aiBegRange"

    val GENE_AI_BREAK_DOOR = ID + ".gene.aiBreakDoor"
    val GENE_AI_BREAK_DOOR_INDEX = ID + ".gene.aiBreakDoorIndex"

    val GENE_AI_CONTROLLED_BY_PLAYER = ID + ".gene.aiControlledByPlayer"
    val GENE_AI_CONTROLLED_BY_PLAYER_INDEX = ID + ".gene.aiControlledByPlayerIndex"
    val GENE_AI_CONTROLLED_BY_PLAYER_MAX_SPEED = ID + ".gene.aiControlledByPlayerMaxSpeed"

    val GENE_AI_CREEPER_SWELL = ID + ".gene.aiCreeperSwell"
    val GENE_AI_CREEPER_SWELL_INDEX = ID + ".gene.aiCreeperSwellIndex"

    val GENE_AI_DEFEND_VILLAGE = ID + ".gene.aiDefendVillage"
    val GENE_AI_DEFEND_VILLAGE_INDEX = ID + ".gene.aiDefendVillageIndex"

    val GENE_AI_EAT_GRASS = ID + ".gene.aiEatGrass"
    val GENE_AI_EAT_GRASS_INDEX = ID + ".gene.aiEatGrassIndex"

    val GENE_AI_FLEE_SUN = ID + ".gene.aiFleeSun"
    val GENE_AI_FLEE_SUN_INDEX = ID + ".gene.aiFleeSunIndex"
    val GENE_AI_FLEE_SUN_MOVE_SPEED = ID + ".gene.aiFleeSunMoveSpeed"

    val GENE_AI_FOLLOW_GOLEM = ID + ".gene.aiFollowGolem"
    val GENE_AI_FOLLOW_GOLEM_INDEX = ID + ".gene.aiFollowGolemIndex"

    val GENE_AI_FOLLOW_OWNER = ID + ".gene.aiFollowOwner"
    val GENE_AI_FOLLOW_OWNER_INDEX = ID + ".gene.aiFollowOwnerIndex"
    val GENE_AI_FOLLOW_OWNER_MOVE_SPEED = ID + ".gene.aiFollowOwnerMoveSpeed"
    val GENE_AI_FOLLOW_OWNER_MAX_DISTANCE = ID + ".gene.aiFollowOwnerMaxDistance"
    val GENE_AI_FOLLOW_OWNER_MIN_DISTANCE = ID + ".gene.aiFollowOwnerMinDistance"

    val GENE_AI_FOLLOW_PARENT = ID + ".gene.aiFollowParent"
    val GENE_AI_FOLLOW_PARENT_INDEX = ID + ".gene.aiFollowParentIndex"
    val GENE_AI_FOLLOW_PARENT_MOVE_SPEED = ID + ".gene.aiFollowParentMoveSpeed"

    val GENE_AI_HURT_BY_TARGET = ID + ".gene.aiHurtByTarget"
    val GENE_AI_HURT_BY_TARGET_INDEX = ID + ".gene.aiHurtByTargetIndex"
    val GENE_AI_HURT_BY_TARGET_CALL_HELP = ID + ".gene.aiHurtByTargetCallHelp"

    val GENE_AI_LEAP_AT_TARGET = ID + ".gene.aiLeapAtTarget"
    val GENE_AI_LEAP_AT_TARGET_INDEX = ID + ".gene.aiLeapAtTargetIndex"
    val GENE_AI_LEAP_AT_TARGET_MOTION_Y = ID + ".gene.aiLeapAtTargetMotionY"

    val GENE_AI_LOOK_AT_TRADE_PLAYER = ID + ".gene.aiLookAtTradePlayer"
    val GENE_AI_LOOK_AT_TRADE_PLAYER_INDEX = ID + ".gene.aiLookAtTradePlayerIndex"

    val GENE_AI_LOOK_AT_VILLAGER = ID + ".gene.aiLookAtVillager"
    val GENE_AI_LOOK_AT_VILLAGER_INDEX = ID + ".gene.aiLookAtVillagerIndex"

    val GENE_AI_LOOK_IDLE = ID + ".gene.aiLookIdle"
    val GENE_AI_LOOK_IDLE_INDEX = ID + ".gene.aiLookIdleIndex"

    val GENE_AI_MATE = ID + ".gene.aiMate"
    val GENE_AI_MATE_INDEX = ID + ".gene.aiMateIndex"
    val GENE_AI_MATE_MOVE_SPEED = ID + ".gene.aiMateMoveSpeed"

    val GENE_AI_MOVE_INDOORS = ID + ".gene.aiMoveIndoors"
    val GENE_AI_MOVE_INDOORS_INDEX = ID + ".gene.aiMoveIndoorsIndex"
    val GENE_AI_MOVE_THROUGH_VILLAGE = ID + ".gene.aiMoveThroughVillage"
    val GENE_AI_MOVE_THROUGH_VILLAGE_INDEX = ID + ".gene.aiMoveThroughVillageIndex"
    val GENE_AI_MOVE_THROUGH_VILLAGE_MOVE_SPEED = ID + ".gene.aiMoveThroughVillageMoveSpeed"
    val GENE_AI_MOVE_THROUGH_VILLAGE_IS_NOCTURNAL = ID + ".gene.aiMoveThroughVillageIsNocturnal"

    val GENE_AI_MOVE_TOWARDS_RESTRICTION = ID + ".gene.aiMoveTowardsRestriction"
    val GENE_AI_MOVE_TOWARDS_RESTRICTION_INDEX = ID + ".gene.aiMoveTowardsRestrictionIndex"
    val GENE_AI_MOVE_TOWARDS_RESTRICTION_MOVE_SPEED = ID + ".gene.aiMoveTowardsRestrictionMoveSpeed"

    val GENE_AI_MOVE_TOWARDS_TARGET = ID + ".gene.aiMoveTowardsTarget"
    val GENE_AI_MOVE_TOWARDS_TARGET_INDEX = ID + ".gene.aiMoveTowardsTargetIndex"
    val GENE_AI_MOVE_TOWARDS_TARGET_MOVE_SPEED = ID + ".gene.aiMoveTowardsTargetMoveSpeed"
    val GENE_AI_MOVE_TOWARDS_TARGET_MAX_DISTANCE = ID + ".gene.aiMoveTowardsTargetMaxDistance"

    val GENE_AI_NEAREST_ATTACKABLE_TARGET = ID + ".gene.aiNearestAttackableTarget"
    val GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX = ID + ".gene.aiNearestAttackableTargetIndex"
    val GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET = ID + ".gene.aiNearestAttackableTargetTarget"
    val GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE = ID + ".gene.aiNearestAttackableTargetTargetChance"
    val GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE = ID + ".gene.aiNearestAttackableTargetVisible"
    val GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY = ID + ".gene.aiNearestAttackableTargetNearbyOnly"
    val GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR = ID + ".gene.aiNearestAttackableTargetEntitySelector"

    val GENE_AI_OCELOT_ATTACK = ID + ".gene.aiOcelotAttack"
    val GENE_AI_OCELOT_ATTACK_INDEX = ID + ".gene.aiOcelotAttackIndex"

    val GENE_AI_OCELOT_SIT = ID + ".gene.aiOcelotSit"
    val GENE_AI_OCELOT_SIT_INDEX = ID + ".gene.aiOcelotSitIndex"
    val GENE_AI_OCELOT_SIT_MOVE_SPEED = ID + ".gene.aiOcelotSitMoveSpeed"

    val GENE_AI_OPEN_DOOR = ID + ".gene.aiOpenDoor"
    val GENE_AI_OPEN_DOOR_INDEX = ID + ".gene.aiOpenDoorIndex"
    val GENE_AI_OPEN_DOOR_CLOSE_DOOR = ID + ".gene.aiOpenDoorCloseDoor"

    val GENE_AI_OWNER_HURT_BY_TARGET = ID + ".gene.aiOwnerHurtByTarget"
    val GENE_AI_OWNER_HURT_BY_TARGET_INDEX = ID + ".gene.aiOwnerHurtByTargetIndex"

    val GENE_AI_OWNER_HURT_TARGET = ID + ".gene.aiOwnerHurtTarget"
    val GENE_AI_OWNER_HURT_TARGET_INDEX = ID + ".gene.aiOwnerHurtTargetIndex"

    val GENE_AI_PANIC = ID + ".gene.aiPanic"
    val GENE_AI_PANIC_INDEX = ID + ".gene.aiPanicIndex"
    val GENE_AI_PANIC_MOVE_SPEED = ID + ".gene.aiPanicMoveSpeed"

    val GENE_AI_PLAY = ID + ".gene.aiPlay"
    val GENE_AI_PLAY_INDEX = ID + ".gene.aiPlayIndex"
    val GENE_AI_PLAY_MOVE_SPEED = ID + ".gene.aiPlayMoveSpeed"

    val GENE_AI_RESTRICT_OPEN_DOOR = ID + ".gene.aiRestrictOpenDoor"
    val GENE_AI_RESTRICT_OPEN_DOOR_INDEX = ID + ".gene.aiRestrictOpenDoorIndex"

    val GENE_AI_RESTRICT_SUN = ID + ".gene.aiRestrictSun"
    val GENE_AI_RESTRICT_SUN_INDEX = ID + ".gene.aiRestrictSunIndex"

    val GENE_AI_RUN_AROUND_LIKE_CRAZY = ID + ".gene.aiRunAroundLikeCrazy"
    val GENE_AI_RUN_AROUND_LIKE_CRAZY_INDEX = ID + ".gene.aiRunAroundLikeCrazyIndex"
    val GENE_AI_RUN_AROUND_LIKE_CRAZY_MOVE_SPEED = ID + ".gene.aiRunAroundLikeCrazyMoveSpeed"

    val GENE_AI_SIT = ID + ".gene.aiSit"
    val GENE_AI_SIT_INDEX = ID + ".gene.aiSitIndex"

    val GENE_AI_SWIMMING = ID + ".gene.aiSwimming"
    val GENE_AI_SWIMMING_INDEX = ID + ".gene.aiSwimmingIndex"

    val GENE_AI_TARGET_NON_TAMED = ID + ".gene.aiTargetNonTamed"
    val GENE_AI_TARGET_NON_TAMED_INDEX = ID + ".gene.aiTargetNonTamedIndex"
    val GENE_AI_TARGET_NON_TAMED_TARGET = ID + ".gene.aiTargetNonTamedTarget"
    val GENE_AI_TARGET_NON_TAMED_TARGET_CHANCE = ID + ".gene.aiTargetNonTamedTargetChance"
    val GENE_AI_TARGET_NON_TAMED_VISIBLE = ID + ".gene.aiTargetNonTamedVisible"

    val GENE_AI_TEMPT = ID + ".gene.aiTempt"
    val GENE_AI_TEMPT_INDEX = ID + ".gene.aiTemptIndex"
    val GENE_AI_TEMPT_MOVE_SPEED = ID + ".gene.aiTemptMoveSpeed"
    val GENE_AI_TEMPT_ITEM = ID + ".gene.aiTemptItem"
    val GENE_AI_TEMPT_SCARED_BY_PLAYER = ID + ".gene.aiTemptScaredByPlayer"

    val GENE_AI_TRADE_PLAYER = ID + ".gene.aiTradePlayer"
    val GENE_AI_TRADE_PLAYER_INDEX = ID + ".gene.aiTradePlayerIndex"

    val GENE_AI_VILLAGER_MATE = ID + ".gene.aiVillagerMate"
    val GENE_AI_VILLAGER_MATE_INDEX = ID + ".gene.aiVillagerMateIndex"

    val GENE_AI_WANDER = ID + ".gene.aiWander"
    val GENE_AI_WANDER_INDEX = ID + ".gene.aiWanderIndex"
    val GENE_AI_WANDER_MOVE_SPEED = ID + ".gene.aiWanderMoveSpeed"

    val GENE_AI_WATCH_CLOSEST = ID + ".gene.aiWatchClosest"
    val GENE_AI_WATCH_CLOSEST_INDEX = ID + ".gene.aiWatchClosestIndex"
    val GENE_AI_WATCH_CLOSEST_TARGET = ID + ".gene.aiWatchClosestTarget"
    val GENE_AI_WATCH_CLOSEST_RANGE = ID + ".gene.aiWatchClosestRange"
    val GENE_AI_WATCH_CLOSEST_CHANCE = ID + ".gene.aiWatchClosestChance"

    val GENE_AI_WATCH_CLOSEST_2 = ID + ".gene.aiWatchClosest2"
    val GENE_AI_WATCH_CLOSEST_2_INDEX = ID + ".gene.aiWatchClosest2Index"
    val GENE_AI_WATCH_CLOSEST_2_TARGET = ID + ".gene.aiWatchClosest2Target"
    val GENE_AI_WATCH_CLOSEST_2_RANGE = ID + ".gene.aiWatchClosest2Range"
    val GENE_AI_WATCH_CLOSEST_2_CHANCE = ID + ".gene.aiWatchClosest2Chance"

    val GENE_MODEL = ID + ".gene.model"
    val GENE_TEXTURE = ID + ".gene.texture"

    val GENE_EXPLODES = ID + ".gene.expodes"
    val GENE_FUSE_TIME = ID + ".gene.fuseTime"
    val GENE_EXPLOSION_RADIUS = ID + ".gene.explosionRadius"
    val GENE_FLINT_AND_STEEL_IGNITE = ID + ".gene.flintAndSteelIgnite"
    val GENE_CAN_BE_CHARGED = ID + ".gene.canBeCharged"
}