package seremis.geninfusion.soul

import net.minecraft.entity.monster.{EntityCreeper, EntitySkeleton, EntitySpider, EntityZombie}
import net.minecraft.item.ItemStack
import seremis.geninfusion.api.lib.Animations._
import seremis.geninfusion.api.lib.Genes._
import seremis.geninfusion.api.lib.Traits._
import seremis.geninfusion.api.soul.SoulHelper._
import seremis.geninfusion.api.soul.{ISoul, SoulHelper}
import seremis.geninfusion.soul.AlleleType._
import seremis.geninfusion.soul.entity.animation.{AnimationHead, _}
import seremis.geninfusion.soul.gene._
import seremis.geninfusion.soul.gene.newAI._
import seremis.geninfusion.soul.standardSoul.{StandardSoulCreeper, StandardSoulSkeleton, StandardSoulSpider, StandardSoulZombie}
import seremis.geninfusion.soul.traits._

object ModSouls {

    lazy final val StandardSoulCreeper = new StandardSoulCreeper
    lazy final val StandardSoulSkeleton = new StandardSoulSkeleton
    lazy final val StandardSoulZombie = new StandardSoulZombie
    lazy final val StandardSoulSpider = new StandardSoulSpider

    var SoulCreeper: ISoul = _
    var SoulSkeleton: ISoul = _
    var SoulZombie: ISoul = _
    var SoulSpider: ISoul = _

    def init() {
        geneRegistry.registerGene(GeneMaxHealth, classOf[Double])
        geneRegistry.registerGene(GeneInvulnerable, classOf[Boolean]).noMutations
        geneRegistry.registerGene(GeneAttackDamage, classOf[Double])
        geneRegistry.registerGene(GeneMovementSpeed, classOf[Double])
        geneRegistry.registerGene(GeneFollowRange, classOf[Double])
        geneRegistry.registerGene(GeneBurnsInDaylight, classOf[Boolean])
        geneRegistry.registerGene(GeneDrownsInWater, classOf[Boolean])
        geneRegistry.registerGene(GeneDrownsInAir, classOf[Boolean])
        geneRegistry.registerGene(GeneImmuneToFire, classOf[Boolean])
        geneRegistry.registerGene(GeneMaxHurtResistantTime, classOf[Int])
        geneRegistry.registerGene(GenePicksUpItems, classOf[Boolean])
        geneRegistry.registerGene(GeneItemDrops, classOf[Array[ItemStack]])
        geneRegistry.registerGene(GeneRareItemDrops, classOf[Array[ItemStack]])
        geneRegistry.registerGene(GeneRareItemDropChances, classOf[Array[Float]])
        geneRegistry.registerGene(GeneEquipmentDropChances, classOf[Array[Float]])
        geneRegistry.registerGene(GeneLivingSound, classOf[String])
        geneRegistry.registerGene(GeneHurtSound, classOf[String])
        geneRegistry.registerGene(GeneDeathSound, classOf[String])
        geneRegistry.registerGene(GeneWalkSound, classOf[String])
        geneRegistry.registerGene(GeneSplashSound, classOf[String])
        geneRegistry.registerGene(GeneSwimSound, classOf[String])
        geneRegistry.registerGene(GeneSoundVolume, classOf[Float])
        geneRegistry.registerGene(GeneCreatureAttribute, classOf[Int])
        geneRegistry.registerGene(GeneTeleportTimeInPortal, classOf[Int])
        geneRegistry.registerGene(GenePortalCooldown, classOf[Int])
        geneRegistry.registerGene(GeneKnockBackResistance, classOf[Double])
        geneRegistry.registerGene(GeneShouldDespawn, classOf[Boolean])
        geneRegistry.registerGene(GeneTalkInterval, classOf[Int])
        geneRegistry.registerGene(GeneSetOnFireFromAttack, classOf[Boolean])
        geneRegistry.registerGene(GeneExperienceValue, classOf[Int])
        geneRegistry.registerGene(GeneVerticalFaceSpeed, classOf[Int])
        geneRegistry.registerGene(GeneIsCreature, classOf[Boolean]).noMutations
        geneRegistry.registerGene(GeneChildrenBurnInDaylight, classOf[Boolean])
        geneRegistry.registerGene(GeneIsTameable, classOf[Boolean])
        geneRegistry.registerGene(GeneImmuneToPoison, classOf[Boolean])
        geneRegistry.registerGene(GeneAffectedByWeb, classOf[Boolean])
        geneRegistry.registerGene(GeneCanClimbWalls, classOf[Boolean])

        geneRegistry.registerGene(GeneCanProcreate, classOf[Boolean])
        geneRegistry.registerGene(GeneChildXPModifier, classOf[Float])
        geneRegistry.registerGene(GeneIsChild, classOf[Boolean]).noMutations.makeChangable
        geneRegistry.registerGene(GeneChildSpeedModifier, classOf[Double])

        geneRegistry.registerGene(GeneMinAttackBrightness, classOf[Float])
        geneRegistry.registerGene(GeneMaxAttackBrightness, classOf[Float])
        geneRegistry.registerGene(GeneAttackTargetVisible, classOf[Boolean])
        geneRegistry.registerGene(GeneJumpAtAttackTarget, classOf[Boolean])

        geneRegistry.registerGene(GeneAIArrowAttackMaxRangedAttackTime, classOf[Int])
        geneRegistry.registerGene(GeneAIArrowAttackMinRangedAttackTime, classOf[Int])
        geneRegistry.registerGene(GeneAIArrowAttackMoveSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIArrowAttackRangedAttackTimeModifier, classOf[Float])
        geneRegistry.registerGene(GeneAIArrowAttackIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIArrowAttack, new GeneAIArrowAttack).setCombinedInherit

        geneRegistry.registerGene(GeneAIAttackOnCollideTarget, classOf[Array[Class[_]]])
        geneRegistry.registerGene(GeneAIAttackOnCollideMoveSpeed, new GeneMoveSpeed(classOf[Array[Double]]))
        geneRegistry.registerGene(GeneAIAttackOnCollideLongMemory, classOf[Array[Boolean]])
        geneRegistry.registerGene(GeneAIAttackOnCollideIndex, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GeneAIAttackOnCollide, new GeneAIAttackOnCollide).setCombinedInherit

        geneRegistry.registerGene(GeneAIAvoidEntityTarget, classOf[Array[Class[_]]])
        geneRegistry.registerGene(GeneAIAvoidEntityRange, classOf[Array[Float]])
        geneRegistry.registerGene(GeneAIAvoidEntityNearSpeed, new GeneMoveSpeed(classOf[Array[Double]]))
        geneRegistry.registerGene(GeneAIAvoidEntityFarSpeed, new GeneMoveSpeed(classOf[Array[Double]]))
        geneRegistry.registerGene(GeneAIAvoidEntityIndex, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GeneAIAvoidEntity, new GeneAIAvoidEntity).setCombinedInherit

        geneRegistry.registerGene(GeneAIBegRange, classOf[Float])
        geneRegistry.registerGene(GeneAIBegIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIBeg, new GeneAIBeg).setCombinedInherit

        geneRegistry.registerGene(GeneAIBreakDoorIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIBreakDoor, new GeneAIBreakDoor).setCombinedInherit

        geneRegistry.registerGene(GeneAIControlledByPlayerMaxSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIControlledByPlayerIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIControlledByPlayer, new GeneAIControlledByPlayer).setCombinedInherit

        geneRegistry.registerGene(GeneAICreeperSwellIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAICreeperSwell, new GeneAICreeperSwell).setCombinedInherit

        geneRegistry.registerGene(GeneAIDefendVillageIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIDefendVillage, new GeneAIDefendVillage).setCombinedInherit

        geneRegistry.registerGene(GeneAIEatGrassIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIEatGrass, new GeneAIEatGrass).setCombinedInherit

        geneRegistry.registerGene(GeneAIFleeSunMoveSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIFleeSunIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIFleeSun, new GeneAIFleeSun).setCombinedInherit

        geneRegistry.registerGene(GeneAIFollowGolemIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIFollowGolem, new GeneAIFollowGolem).setCombinedInherit

        geneRegistry.registerGene(GeneAIFollowOwnerMoveSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIFollowOwnerMinDistance, classOf[Float])
        geneRegistry.registerGene(GeneAIFollowOwnerMaxDistance, classOf[Float])
        geneRegistry.registerGene(GeneAIFollowOwnerIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIFollowOwner, new GeneAIFollowOwner).setCombinedInherit

        geneRegistry.registerGene(GeneAIFollowParentMoveSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIFollowParentIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIFollowParent, new GeneAIFollowParent).setCombinedInherit

        geneRegistry.registerGene(GeneAIHurtByTargetCallHelp, classOf[Boolean])
        geneRegistry.registerGene(GeneAIHurtByTargetIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIHurtByTarget, new GeneAIHurtByTarget).setCombinedInherit

        geneRegistry.registerGene(GeneAILeapAtTargetMotionY, classOf[Float])
        geneRegistry.registerGene(GeneAILeapAtTargetIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAILeapAtTarget, new GeneAILeapAtTarget).setCombinedInherit

        geneRegistry.registerGene(GeneAILookAtTradePlayerIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAILookAtTradePlayer, new GeneAILookAtTradePlayer).setCombinedInherit

        geneRegistry.registerGene(GeneAILookAtVillagerIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAILookAtVillager, new GeneAILookAtVillager).setCombinedInherit

        geneRegistry.registerGene(GeneAILookIdleIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAILookIdle, new GeneAILookIdle).setCombinedInherit

        geneRegistry.registerGene(GeneAIMateMoveSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIMateIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIMate, new GeneAIMate).setCombinedInherit

        geneRegistry.registerGene(GeneAIMoveIndoorsIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIMoveIndoors, new GeneAIMoveIndoors).setCombinedInherit

        geneRegistry.registerGene(GeneAIMoveThroughVillageMoveSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIMoveThroughVillageIsNocturnal, classOf[Boolean])
        geneRegistry.registerGene(GeneAIMoveThroughVillageIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIMoveThroughVillage, new GeneAIMoveThroughVillage).setCombinedInherit

        geneRegistry.registerGene(GeneAIMoveTowardsRestrictionMoveSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIMoveTowardsRestrictionIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIMoveTowardsRestriction, new GeneAIMoveTowardsRestriction).setCombinedInherit

        geneRegistry.registerGene(GeneAIMoveTowardsTargetMoveSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIMoveTowardsTargetMaxDistance, classOf[Float])
        geneRegistry.registerGene(GeneAIMoveTowardsTargetIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIMoveTowardsTarget, new GeneAIMoveTowardsTarget).setCombinedInherit

        geneRegistry.registerGene(GeneAINearestAttackableTargetTarget, classOf[Array[Class[_]]])
        geneRegistry.registerGene(GeneAINearestAttackableTargetTargetChance, classOf[Array[Int]])
        geneRegistry.registerGene(GeneAINearestAttackableTargetVisible, classOf[Array[Boolean]])
        geneRegistry.registerGene(GeneAINearestAttackableTargetNearbyOnly, classOf[Array[Boolean]])
        geneRegistry.registerGene(GeneAINearestAttackableTargetEntitySelector, classOf[Array[String]])
        geneRegistry.registerGene(GeneAINearestAttackableTargetIndex, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GeneAINearestAttackableTarget, new GeneAINearestAttackableTarget).setCombinedInherit

        geneRegistry.registerGene(GeneAIOcelotAttackIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIOcelotAttack, new GeneAIOcelotAttack).setCombinedInherit

        geneRegistry.registerGene(GeneAIOcelotSitMoveSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIOcelotSitIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIOcelotSit, new GeneAIOcelotSit).setCombinedInherit

        geneRegistry.registerGene(GeneAIOpenDoorCloseDoor, classOf[Boolean])
        geneRegistry.registerGene(GeneAIOpenDoorIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIOpenDoor, new GeneAIOpenDoor).setCombinedInherit

        geneRegistry.registerGene(GeneAIOwnerHurtByTargetIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIOwnerHurtByTarget, new GeneAIOwnerHurtByTarget).setCombinedInherit

        geneRegistry.registerGene(GeneAIOwnerHurtTargetIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIOwnerHurtTarget, new GeneAIOwnerHurtTarget).setCombinedInherit

        geneRegistry.registerGene(GeneAIPanicMoveSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIPanicIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIPanic, new GeneAIPanic).setCombinedInherit

        geneRegistry.registerGene(GeneAIPlayMoveSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIPlayIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIPlay, new GeneAIPlay).setCombinedInherit

        geneRegistry.registerGene(GeneAIRestrictOpenDoorIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIRestrictOpenDoor, new GeneAIRestrictOpenDoor).setCombinedInherit

        geneRegistry.registerGene(GeneAIRestrictSunIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIRestrictSun, new GeneAIRestrictSun).setCombinedInherit

        geneRegistry.registerGene(GeneAIRunAroundLikeCrazyMoveSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIRunAroundLikeCrazyIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIRunAroundLikeCrazy, new GeneAIRunAroundLikeCrazy).setCombinedInherit

        geneRegistry.registerGene(GeneAISitIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAISit, new GeneAISit).setCombinedInherit

        geneRegistry.registerGene(GeneAISwimmingIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAISwimming, new GeneAISwimming).setCombinedInherit

        geneRegistry.registerGene(GeneAITargetNonTamedVisible, classOf[Array[Boolean]])
        geneRegistry.registerGene(GeneAITargetNonTamedTargetChance, classOf[Array[Int]])
        geneRegistry.registerGene(GeneAITargetNonTamedTarget, classOf[Array[Class[_]]])
        geneRegistry.registerGene(GeneAITargetNonTamedIndex, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GeneAITargetNonTamed, new GeneAITargetNonTamed).setCombinedInherit

        geneRegistry.registerGene(GeneAITemptItem, classOf[Array[ItemStack]])
        geneRegistry.registerGene(GeneAITemptMoveSpeed, new GeneMoveSpeed(classOf[Array[Double]]))
        geneRegistry.registerGene(GeneAITemptScaredByPlayer, classOf[Array[Boolean]])
        geneRegistry.registerGene(GeneAITemptIndex, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GeneAITempt, new GeneAITempt).setCombinedInherit

        geneRegistry.registerGene(GeneAITradePlayerIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAITradePlayer, new GeneAITradePlayer).setCombinedInherit

        geneRegistry.registerGene(GeneAIVillagerMateIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIVillagerMate, new GeneAIVillagerMate).setCombinedInherit

        geneRegistry.registerGene(GeneAIWanderMoveSpeed, new GeneMoveSpeed(classOf[Double]))
        geneRegistry.registerGene(GeneAIWanderIndex, classOf[Int]).noMutations
        geneRegistry.registerMasterGene(GeneAIWander, new GeneAIWander).setCombinedInherit

        geneRegistry.registerGene(GeneAIWatchClosestTarget, classOf[Array[Class[_]]])
        geneRegistry.registerGene(GeneAIWatchClosestRange, classOf[Array[Float]])
        geneRegistry.registerGene(GeneAIWatchClosestChance, classOf[Array[Float]])
        geneRegistry.registerGene(GeneAIWatchClosestIndex, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GeneAIWatchClosest, new GeneAIWatchClosest).setCombinedInherit

        geneRegistry.registerGene(GeneAIWatchClosest2Target, classOf[Array[Class[_]]])
        geneRegistry.registerGene(GeneAIWatchClosest2Range, classOf[Array[Float]])
        geneRegistry.registerGene(GeneAIWatchClosest2Chance, classOf[Array[Float]])
        geneRegistry.registerGene(GeneAIWatchClosest2Index, classOf[Array[Int]]).noMutations
        geneRegistry.registerMasterGene(GeneAIWatchClosest2, new GeneAIWatchClosest2).setCombinedInherit


        geneRegistry.registerMasterGene(GeneUseNewAI, new GeneUseNewAI).noMutations
        geneRegistry.registerGene(GeneUseOldAI, classOf[Boolean]).noMutations

        geneRegistry.registerGene(GeneModelAdult, new GeneModel)
        geneRegistry.registerCustomInheritance(GeneModelAdult)
        geneRegistry.registerGene(GeneModelChild, new GeneModel)
        geneRegistry.registerCustomInheritance(GeneModelAdult)

        geneRegistry.registerGene(GeneFuseTime, classOf[Int])
        geneRegistry.registerGene(GeneExplosionRadius, classOf[Int])
        geneRegistry.registerGene(GeneCanBeCharged, classOf[Boolean])
        geneRegistry.registerGene(GeneFlintAndSteelIgnite, classOf[Boolean])

        geneRegistry.registerGene(GeneKilledBySpecificEntityDrops, classOf[Array[ItemStack]])
        geneRegistry.registerGene(GeneKilledBySpecificEntityEntity, classOf[Class[_]])
        geneRegistry.registerMasterGene(GeneDropsItemWhenKilledBySpecificEntity, new GeneDropsItemWhenKilledBySpecificEntity)

        traitRegistry.registerTrait(TraitFire, new TraitFire)
        traitRegistry.registerTrait(TraitMovement, new TraitMovement)
        traitRegistry.registerTrait(TraitAttacked, new TraitAttacked)
        traitRegistry.registerTrait(TraitItemPickup, new TraitItemPickup)
        traitRegistry.registerTrait(TraitItemDrops, new TraitItemDrops)
        traitRegistry.registerTrait(TraitFluids, new TraitFluids)
        traitRegistry.registerTrait(TraitSounds, new TraitSounds)
        traitRegistry.registerTrait(TraitAttributes, new TraitInitialValues)
        traitRegistry.registerTrait(TraitAttack, new TraitAttack)
        traitRegistry.registerTrait(TraitAI, new TraitAI)
        traitRegistry.registerTrait(TraitRender, new TraitRender)
        traitRegistry.registerTrait(TraitHomeArea, new TraitHomeArea)
        traitRegistry.registerTrait(TraitTexture, new TraitTexture)
        traitRegistry.registerTrait(TraitNavigate, new TraitNavigate)
        traitRegistry.registerTrait(TraitAnimation, new TraitAnimation)
        traitRegistry.registerTrait(TraitExplode, new TraitExplode)
        traitRegistry.registerTrait(TraitNameTag, new TraitNameTag)
        traitRegistry.registerTrait(TraitChild, new TraitChild)

        standardSoulRegistry.register(StandardSoulZombie)
        standardSoulRegistry.register(StandardSoulSkeleton)
        standardSoulRegistry.register(StandardSoulCreeper)
        standardSoulRegistry.register(StandardSoulSpider)

        animationRegistry.register(AnimationWalkTwoLegged, new AnimationBiped)
        animationRegistry.register(AnimationWalkTwoArmed, new AnimationTwoArmed)
        animationRegistry.register(AnimationHead, new AnimationHead)
        animationRegistry.register(AnimationWalkFourLegged, new AnimationQuadruped)
        animationRegistry.register(AnimationWalkEightLegged, new AnimationOctoped)

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
        alleleTypeRegistry.registerAlleleType(typeModel)
        alleleTypeRegistry.registerAlleleType(typeNBTTagCompound)
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
        alleleTypeRegistry.registerAlleleType(typeNBTTagCompoundArray)
    }

    def postInit() {
        SoulCreeper = SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityCreeper(null)).get
        SoulSkeleton = SoulHelper.standardSoulRegistry.getSoulForEntity(new EntitySkeleton(null)).get
        SoulZombie = SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityZombie(null)).get
        SoulSpider = SoulHelper.standardSoulRegistry.getSoulForEntity(new EntitySpider(null)).get
    }
}
