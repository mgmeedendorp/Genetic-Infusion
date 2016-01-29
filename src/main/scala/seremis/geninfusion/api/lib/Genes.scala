package seremis.geninfusion.api.lib

object Genes {

    val ID = "geninfusion"

    val GeneMaxHealth = ID + ".gene.maxHealth"
    val GeneInvulnerable = ID + ".gene.invulnerable"
    val GeneAttackDamage = ID + ".gene.attackDamage"
    val GeneMovementSpeed = ID + ".gene.movementSpeed"
    val GeneFollowRange = ID + ".gene.followRange"
    val GeneBurnsInDaylight = ID + ".gene.burnsInDaylight"
    val GeneDrownsInWater = ID + ".gene.drownsInWater"
    val GeneDrownsInAir = ID + ".gene.drownsInAir"
    val GeneImmuneToFire = ID + ".gene.isImmuneToFire"
    val GeneMaxHurtResistantTime = ID + ".gene.maxHurtResistantTime"
    val GenePicksUpItems = ID + ".gene.picksUpItems"
    val GeneItemDrops = ID + ".gene.itemDrops"
    val GeneRareItemDrops = ID + ".gene.rareItemDrops"
    val GeneRareItemDropChances = ID + ".gene.rareItemDropChances"
    val GeneEquipmentDropChances = ID + ".gene.equipmentDropChances"
    val GeneLivingSound = ID + ".gene.livingSound"
    val GeneHurtSound = ID + ".gene.hurtSound"
    val GeneDeathSound = ID + ".gene.deathSound"
    val GeneWalkSound = ID + ".gene.walkSound"
    val GeneSplashSound = ID + ".gene.splashSound"
    val GeneSwimSound = ID + ".gene.swimSound"
    val GeneSoundVolume = ID + ".gene.soundVolume"
    val GeneCreatureAttribute = ID + ".gene.creatureAttribute"
    val GeneTeleportTimeInPortal = ID + ".gene.teleportTimeInPortal"
    val GenePortalCooldown = ID + ".gene.portalCooldown"
    val GeneKnockBackResistance = ID + ".gene.knockbackResistance"
    val GeneShouldDespawn = ID + ".gene.shouldDespawn"
    val GeneTalkInterval = ID + ".gene.talkInterval"
    val GeneSetOnFireFromAttack = ID + ".gene.setOnFireFromAttack"
    val GeneExperienceValue = ID + ".gene.experienceValue"
    val GeneUseNewAI = ID + ".gene.useNewAI"
    val GeneUseOldAI = ID + ".gene.useOldAI"
    val GeneVerticalFaceSpeed = ID + ".gene.verticalFaceSpeed"
    val GeneIsCreature = ID + ".gene.isCreature"
    val GeneChildrenBurnInDaylight = ID + ".gene.childrenBurn"
    val GeneIsTameable = ID + ".gene.isTameable"
    val GeneImmuneToPoison = ID + ".gene.immuneToPoison"
    val GeneAffectedByWeb = ID + ".gene.affectedByWeb"
    val GeneCanClimbWalls = ID + ".gene.canClimbWalls"

    //Entity can mate and children age (makes an EntityAgeable)
    val GeneCanProcreate = ID + ".gene.canProcreate"
    val GeneChildXPModifier = ID + ".gene.childXPModifier"
    val GeneIsChild = ID + ".gene.isChild"
    val GeneChildSpeedModifier = ID + ".gene.childSpeedModifier"

    val GeneMinAttackBrightness = ID + ".gene.minAttackBrightness"
    val GeneMaxAttackBrightness = ID + ".gene.maxAttackBrightness"
    val GeneAttackTargetVisible = ID + ".gene.attackTargetVisible"
    val GeneJumpAtAttackTarget = ID + ".gene.jumpAtAttackTarget"


    val GeneAIArrowAttack = ID + ".gene.aiArrowAttack"
    val GeneAIArrowAttackIndex = ID + ".gene.aiArrowAttackIndex"
    val GeneAIArrowAttackMaxRangedAttackTime = ID + ".gene.aiArrowAttackMaxRangedAttackTime"
    val GeneAIArrowAttackMinRangedAttackTime = ID + ".gene.aiArrowAttackMinRangedAttackTime"
    val GeneAIArrowAttackMoveSpeed = ID + ".gene.aiArrowAttackMoveSpeed"
    val GeneAIArrowAttackRangedAttackTimeModifier = ID + ".gene.aiArrowAttackRangedAttackTimeModifier"

    val GeneAIAttackOnCollide = ID + ".gene.aiAttackOnCollide"
    val GeneAIAttackOnCollideIndex = ID + ".gene.aiAttackOnCollideIndex"
    val GeneAIAttackOnCollideTarget = ID + ".gene.aiAttackOnCollideTarget"
    val GeneAIAttackOnCollideMoveSpeed = ID + ".gene.aiAttackOnCollideMoveSpeed"
    val GeneAIAttackOnCollideLongMemory = ID + ".gene.aiAttackOnCollideLongMemory"

    val GeneAIAvoidEntity = ID + ".gene.aiAvoidEntity"
    val GeneAIAvoidEntityIndex = ID + ".gene.aiAvoidEntityIndex"
    val GeneAIAvoidEntityTarget = ID + ".gene.aiAvoidEntityTarget"
    val GeneAIAvoidEntityRange = ID + ".gene.aiAvoidEntityRange"
    val GeneAIAvoidEntityFarSpeed = ID + ".gene.aiAvoidEntityFarSpeed"
    val GeneAIAvoidEntityNearSpeed = ID + ".gene.aiAvoidEntityNearSpeed"

    val GeneAIBeg = ID + ".gene.aiBeg"
    val GeneAIBegIndex = ID + ".gene.aiBegIndex"
    val GeneAIBegRange = ID + ".gene.aiBegRange"

    val GeneAIBreakDoor = ID + ".gene.aiBreakDoor"
    val GeneAIBreakDoorIndex = ID + ".gene.aiBreakDoorIndex"

    val GeneAIControlledByPlayer = ID + ".gene.aiControlledByPlayer"
    val GeneAIControlledByPlayerIndex = ID + ".gene.aiControlledByPlayerIndex"
    val GeneAIControlledByPlayerMaxSpeed = ID + ".gene.aiControlledByPlayerMaxSpeed"

    val GeneAICreeperSwell = ID + ".gene.aiCreeperSwell"
    val GeneAICreeperSwellIndex = ID + ".gene.aiCreeperSwellIndex"

    val GeneAIDefendVillage = ID + ".gene.aiDefendVillage"
    val GeneAIDefendVillageIndex = ID + ".gene.aiDefendVillageIndex"

    val GeneAIEatGrass = ID + ".gene.aiEatGrass"
    val GeneAIEatGrassIndex = ID + ".gene.aiEatGrassIndex"

    val GeneAIFleeSun = ID + ".gene.aiFleeSun"
    val GeneAIFleeSunIndex = ID + ".gene.aiFleeSunIndex"
    val GeneAIFleeSunMoveSpeed = ID + ".gene.aiFleeSunMoveSpeed"

    val GeneAIFollowGolem = ID + ".gene.aiFollowGolem"
    val GeneAIFollowGolemIndex = ID + ".gene.aiFollowGolemIndex"

    val GeneAIFollowOwner = ID + ".gene.aiFollowOwner"
    val GeneAIFollowOwnerIndex = ID + ".gene.aiFollowOwnerIndex"
    val GeneAIFollowOwnerMoveSpeed = ID + ".gene.aiFollowOwnerMoveSpeed"
    val GeneAIFollowOwnerMaxDistance = ID + ".gene.aiFollowOwnerMaxDistance"
    val GeneAIFollowOwnerMinDistance = ID + ".gene.aiFollowOwnerMinDistance"

    val GeneAIFollowParent = ID + ".gene.aiFollowParent"
    val GeneAIFollowParentIndex = ID + ".gene.aiFollowParentIndex"
    val GeneAIFollowParentMoveSpeed = ID + ".gene.aiFollowParentMoveSpeed"

    val GeneAIHurtByTarget = ID + ".gene.aiHurtByTarget"
    val GeneAIHurtByTargetIndex = ID + ".gene.aiHurtByTargetIndex"
    val GeneAIHurtByTargetCallHelp = ID + ".gene.aiHurtByTargetCallHelp"

    val GeneAILeapAtTarget = ID + ".gene.aiLeapAtTarget"
    val GeneAILeapAtTargetIndex = ID + ".gene.aiLeapAtTargetIndex"
    val GeneAILeapAtTargetMotionY = ID + ".gene.aiLeapAtTargetMotionY"

    val GeneAILookAtTradePlayer = ID + ".gene.aiLookAtTradePlayer"
    val GeneAILookAtTradePlayerIndex = ID + ".gene.aiLookAtTradePlayerIndex"

    val GeneAILookAtVillager = ID + ".gene.aiLookAtVillager"
    val GeneAILookAtVillagerIndex = ID + ".gene.aiLookAtVillagerIndex"

    val GeneAILookIdle = ID + ".gene.aiLookIdle"
    val GeneAILookIdleIndex = ID + ".gene.aiLookIdleIndex"

    val GeneAIMate = ID + ".gene.aiMate"
    val GeneAIMateIndex = ID + ".gene.aiMateIndex"
    val GeneAIMateMoveSpeed = ID + ".gene.aiMateMoveSpeed"

    val GeneAIMoveIndoors = ID + ".gene.aiMoveIndoors"
    val GeneAIMoveIndoorsIndex = ID + ".gene.aiMoveIndoorsIndex"
    
    val GeneAIMoveThroughVillage = ID + ".gene.aiMoveThroughVillage"
    val GeneAIMoveThroughVillageIndex = ID + ".gene.aiMoveThroughVillageIndex"
    val GeneAIMoveThroughVillageMoveSpeed = ID + ".gene.aiMoveThroughVillageMoveSpeed"
    val GeneAIMoveThroughVillageIsNocturnal = ID + ".gene.aiMoveThroughVillageIsNocturnal"

    val GeneAIMoveTowardsRestriction = ID + ".gene.aiMoveTowardsRestriction"
    val GeneAIMoveTowardsRestrictionIndex = ID + ".gene.aiMoveTowardsRestrictionIndex"
    val GeneAIMoveTowardsRestrictionMoveSpeed = ID + ".gene.aiMoveTowardsRestrictionMoveSpeed"

    val GeneAIMoveTowardsTarget = ID + ".gene.aiMoveTowardsTarget"
    val GeneAIMoveTowardsTargetIndex = ID + ".gene.aiMoveTowardsTargetIndex"
    val GeneAIMoveTowardsTargetMoveSpeed = ID + ".gene.aiMoveTowardsTargetMoveSpeed"
    val GeneAIMoveTowardsTargetMaxDistance = ID + ".gene.aiMoveTowardsTargetMaxDistance"

    val GeneAINearestAttackableTarget = ID + ".gene.aiNearestAttackableTarget"
    val GeneAINearestAttackableTargetIndex = ID + ".gene.aiNearestAttackableTargetIndex"
    val GeneAINearestAttackableTargetTarget = ID + ".gene.aiNearestAttackableTargetTarget"
    val GeneAINearestAttackableTargetTargetChance = ID + ".gene.aiNearestAttackableTargetTargetChance"
    val GeneAINearestAttackableTargetVisible = ID + ".gene.aiNearestAttackableTargetVisible"
    val GeneAINearestAttackableTargetNearbyOnly = ID + ".gene.aiNearestAttackableTargetNearbyOnly"
    val GeneAINearestAttackableTargetEntitySelector = ID + ".gene.aiNearestAttackableTargetEntitySelector"

    val GeneAIOcelotAttack = ID + ".gene.aiOcelotAttack"
    val GeneAIOcelotAttackIndex = ID + ".gene.aiOcelotAttackIndex"

    val GeneAIOcelotSit = ID + ".gene.aiOcelotSit"
    val GeneAIOcelotSitIndex = ID + ".gene.aiOcelotSitIndex"
    val GeneAIOcelotSitMoveSpeed = ID + ".gene.aiOcelotSitMoveSpeed"

    val GeneAIOpenDoor = ID + ".gene.aiOpenDoor"
    val GeneAIOpenDoorIndex = ID + ".gene.aiOpenDoorIndex"
    val GeneAIOpenDoorCloseDoor = ID + ".gene.aiOpenDoorCloseDoor"

    val GeneAIOwnerHurtByTarget = ID + ".gene.aiOwnerHurtByTarget"
    val GeneAIOwnerHurtByTargetIndex = ID + ".gene.aiOwnerHurtByTargetIndex"

    val GeneAIOwnerHurtTarget = ID + ".gene.aiOwnerHurtTarget"
    val GeneAIOwnerHurtTargetIndex = ID + ".gene.aiOwnerHurtTargetIndex"

    val GeneAIPanic = ID + ".gene.aiPanic"
    val GeneAIPanicIndex = ID + ".gene.aiPanicIndex"
    val GeneAIPanicMoveSpeed = ID + ".gene.aiPanicMoveSpeed"

    val GeneAIPlay = ID + ".gene.aiPlay"
    val GeneAIPlayIndex = ID + ".gene.aiPlayIndex"
    val GeneAIPlayMoveSpeed = ID + ".gene.aiPlayMoveSpeed"

    val GeneAIRestrictOpenDoor = ID + ".gene.aiRestrictOpenDoor"
    val GeneAIRestrictOpenDoorIndex = ID + ".gene.aiRestrictOpenDoorIndex"

    val GeneAIRestrictSun = ID + ".gene.aiRestrictSun"
    val GeneAIRestrictSunIndex = ID + ".gene.aiRestrictSunIndex"

    val GeneAIRunAroundLikeCrazy = ID + ".gene.aiRunAroundLikeCrazy"
    val GeneAIRunAroundLikeCrazyIndex = ID + ".gene.aiRunAroundLikeCrazyIndex"
    val GeneAIRunAroundLikeCrazyMoveSpeed = ID + ".gene.aiRunAroundLikeCrazyMoveSpeed"

    val GeneAISit = ID + ".gene.aiSit"
    val GeneAISitIndex = ID + ".gene.aiSitIndex"

    val GeneAISwimming = ID + ".gene.aiSwimming"
    val GeneAISwimmingIndex = ID + ".gene.aiSwimmingIndex"

    val GeneAITargetNonTamed = ID + ".gene.aiTargetNonTamed"
    val GeneAITargetNonTamedIndex = ID + ".gene.aiTargetNonTamedIndex"
    val GeneAITargetNonTamedTarget = ID + ".gene.aiTargetNonTamedTarget"
    val GeneAITargetNonTamedTargetChance = ID + ".gene.aiTargetNonTamedTargetChance"
    val GeneAITargetNonTamedVisible = ID + ".gene.aiTargetNonTamedVisible"

    val GeneAITempt = ID + ".gene.aiTempt"
    val GeneAITemptIndex = ID + ".gene.aiTemptIndex"
    val GeneAITemptMoveSpeed = ID + ".gene.aiTemptMoveSpeed"
    val GeneAITemptItem = ID + ".gene.aiTemptItem"
    val GeneAITemptScaredByPlayer = ID + ".gene.aiTemptScaredByPlayer"

    val GeneAITradePlayer = ID + ".gene.aiTradePlayer"
    val GeneAITradePlayerIndex = ID + ".gene.aiTradePlayerIndex"

    val GeneAIVillagerMate = ID + ".gene.aiVillagerMate"
    val GeneAIVillagerMateIndex = ID + ".gene.aiVillagerMateIndex"

    val GeneAIWander = ID + ".gene.aiWander"
    val GeneAIWanderIndex = ID + ".gene.aiWanderIndex"
    val GeneAIWanderMoveSpeed = ID + ".gene.aiWanderMoveSpeed"

    val GeneAIWatchClosest = ID + ".gene.aiWatchClosest"
    val GeneAIWatchClosestIndex = ID + ".gene.aiWatchClosestIndex"
    val GeneAIWatchClosestTarget = ID + ".gene.aiWatchClosestTarget"
    val GeneAIWatchClosestRange = ID + ".gene.aiWatchClosestRange"
    val GeneAIWatchClosestChance = ID + ".gene.aiWatchClosestChance"

    val GeneAIWatchClosest2 = ID + ".gene.aiWatchClosest2"
    val GeneAIWatchClosest2Index = ID + ".gene.aiWatchClosest2Index"
    val GeneAIWatchClosest2Target = ID + ".gene.aiWatchClosest2Target"
    val GeneAIWatchClosest2Range = ID + ".gene.aiWatchClosest2Range"
    val GeneAIWatchClosest2Chance = ID + ".gene.aiWatchClosest2Chance"

    val GeneModelAdult = ID + ".gene.model"
    val GeneModelChild = ID + ".gene.modelChild"

    val GeneFuseTime = ID + ".gene.fuseTime"
    val GeneExplosionRadius = ID + ".gene.explosionRadius"
    val GeneFlintAndSteelIgnite = ID + ".gene.flintAndSteelIgnite"
    val GeneCanBeCharged = ID + ".gene.canBeCharged"

    val GeneDropsItemWhenKilledBySpecificEntity = ID + ".gene.dropsItemWhenKilledBySpecificEntity"
    val GeneKilledBySpecificEntityDrops = ID + ".gene.killedBySpecificEntityDrops"
    val GeneKilledBySpecificEntityEntity = ID + ".gene.killedBySpecificEntityEntity"
}