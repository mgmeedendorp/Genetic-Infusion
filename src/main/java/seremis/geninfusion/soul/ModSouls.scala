package seremis.geninfusion.soul

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.EnumAlleleType._
import seremis.geninfusion.api.soul.SoulHelper._
import seremis.geninfusion.api.soul.lib.Animations._
import seremis.geninfusion.api.soul.lib.Genes._
import seremis.geninfusion.api.soul.lib.Traits._
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.soul.entity.animation.{AnimationFourLegged, AnimationHead, AnimationTwoArmed, AnimationTwoLegged}
import seremis.geninfusion.soul.gene._
import seremis.geninfusion.soul.gene.newAI._
import seremis.geninfusion.soul.standardSoul.{StandardSoulCreeper, StandardSoulSkeleton, StandardSoulZombie}
import seremis.geninfusion.soul.traits._

object ModSouls {

    def init() {
        geneRegistry.registerGene(GENE_MAX_HEALTH, DOUBLE)
        geneRegistry.registerGene(GENE_INVULNERABLE, BOOLEAN).noMutations
        geneRegistry.registerGene(GENE_ATTACK_DAMAGE, DOUBLE)
        geneRegistry.registerGene(GENE_MOVEMENT_SPEED, DOUBLE)
        geneRegistry.registerGene(GENE_FOLLOW_RANGE, DOUBLE)
        geneRegistry.registerGene(GENE_BURNS_IN_DAYLIGHT, BOOLEAN)
        geneRegistry.registerGene(GENE_DROWNS_IN_WATER, BOOLEAN)
        geneRegistry.registerGene(GENE_DROWNS_IN_AIR, BOOLEAN)
        geneRegistry.registerGene(GENE_IMMUNE_TO_FIRE, BOOLEAN)
        geneRegistry.registerGene(GENE_MAX_HURT_RESISTANT_TIME, INTEGER)
        geneRegistry.registerGene(GENE_PICKS_UP_ITEMS, BOOLEAN)
        geneRegistry.registerGene(GENE_ITEM_DROPS, ITEMSTACK_ARRAY)
        geneRegistry.registerGene(GENE_RARE_ITEM_DROPS, ITEMSTACK_ARRAY)
        geneRegistry.registerGene(GENE_RARE_ITEM_DROP_CHANCES, FLOAT_ARRAY)
        geneRegistry.registerGene(GENE_EQUIPMENT_DROP_CHANCES, FLOAT_ARRAY)
        geneRegistry.registerGene(GENE_LIVING_SOUND, STRING)
        geneRegistry.registerGene(GENE_HURT_SOUND, STRING)
        geneRegistry.registerGene(GENE_DEATH_SOUND, STRING)
        geneRegistry.registerGene(GENE_WALK_SOUND, STRING)
        geneRegistry.registerGene(GENE_SPLASH_SOUND, STRING)
        geneRegistry.registerGene(GENE_SWIM_SOUND, STRING)
        geneRegistry.registerGene(GENE_SOUND_VOLUME, FLOAT)
        geneRegistry.registerGene(GENE_CREATURE_ATTRIBUTE, INTEGER)
        geneRegistry.registerGene(GENE_TELEPORT_TIME_IN_PORTAL, INTEGER)
        geneRegistry.registerGene(GENE_PORTAL_COOLDOWN, INTEGER)
        geneRegistry.registerGene(GENE_KNOCKBACK_RESISTANCE, DOUBLE)
        geneRegistry.registerGene(GENE_SHOULD_DESPAWN, BOOLEAN)
        geneRegistry.registerGene(GENE_TALK_INTERVAL, INTEGER)
        geneRegistry.registerGene(GENE_SET_ON_FIRE_FROM_ATTACK, BOOLEAN)
        geneRegistry.registerGene(GENE_EXPERIENCE_VALUE, INTEGER)
        geneRegistry.registerGene(GENE_VERTICAL_FACE_SPEED, INTEGER)
        geneRegistry.registerGene(GENE_IS_CREATURE, BOOLEAN).noMutations
        geneRegistry.registerGene(GENE_CEASE_AI_MOVEMENT, BOOLEAN)
        geneRegistry.registerGene(GENE_CHILDREN_BURN_IN_DAYLIGHT, BOOLEAN)
        geneRegistry.registerGene(GENE_IS_TAMEABLE, BOOLEAN)
        geneRegistry.registerGene(GENE_WIDTH, FLOAT).noMutations
        geneRegistry.registerGene(GENE_HEIGHT, FLOAT).noMutations

        geneRegistry.registerGene(GENE_AI_ARROW_ATTACK_MAX_RANGED_ATTACK_TIME, INTEGER)
        geneRegistry.registerGene(GENE_AI_ARROW_ATTACK_MIN_RANGED_ATTACK_TIME, INTEGER)
        geneRegistry.registerGene(GENE_AI_ARROW_ATTACK_MOVE_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_ARROW_ATTACK_RANGED_ATTACK_TIME_MODIFIER, FLOAT)
        geneRegistry.registerGene(GENE_AI_ARROW_ATTACK_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_ARROW_ATTACK, new GeneAIArrowAttack).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_ATTACK_ON_COLLIDE_TARGET, CLASS_ARRAY)
        geneRegistry.registerGene(GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED, new GeneMoveSpeed(DOUBLE_ARRAY))
        geneRegistry.registerGene(GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY, BOOLEAN_ARRAY)
        geneRegistry.registerGene(GENE_AI_ATTACK_ON_COLLIDE_INDEX, INTEGER_ARRAY).noMutations
        geneRegistry.registerMasterGene(GENE_AI_ATTACK_ON_COLLIDE, new GeneAIAttackOnCollide).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_AVOID_ENTITY_TARGET, CLASS_ARRAY)
        geneRegistry.registerGene(GENE_AI_AVOID_ENTITY_RANGE, FLOAT_ARRAY)
        geneRegistry.registerGene(GENE_AI_AVOID_ENTITY_NEAR_SPEED, new GeneMoveSpeed(DOUBLE_ARRAY))
        geneRegistry.registerGene(GENE_AI_AVOID_ENTITY_FAR_SPEED, new GeneMoveSpeed(DOUBLE_ARRAY))
        geneRegistry.registerGene(GENE_AI_AVOID_ENTITY_INDEX, INTEGER_ARRAY).noMutations
        geneRegistry.registerMasterGene(GENE_AI_AVOID_ENTITY, new GeneAIAvoidEntity).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_BEG_RANGE, FLOAT)
        geneRegistry.registerGene(GENE_AI_BEG_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_BEG, new GeneAIBeg).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_BREAK_DOOR_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_BREAK_DOOR, new GeneAIBreakDoor).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_CONTROLLED_BY_PLAYER_MAX_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_CONTROLLED_BY_PLAYER_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_CONTROLLED_BY_PLAYER, new GeneAIControlledByPlayer).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_CREEPER_SWELL_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_CREEPER_SWELL, new GeneAICreeperSwell).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_DEFEND_VILLAGE_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_DEFEND_VILLAGE, new GeneAIDefendVillage).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_EAT_GRASS_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_EAT_GRASS, new GeneAIEatGrass).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_FLEE_SUN_MOVE_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_FLEE_SUN_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_FLEE_SUN, new GeneAIFleeSun).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_FOLLOW_GOLEM_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_FOLLOW_GOLEM, new GeneAIFollowGolem).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_FOLLOW_OWNER_MOVE_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_FOLLOW_OWNER_MIN_DISTANCE, FLOAT)
        geneRegistry.registerGene(GENE_AI_FOLLOW_OWNER_MAX_DISTANCE, FLOAT)
        geneRegistry.registerGene(GENE_AI_FOLLOW_OWNER_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_FOLLOW_OWNER, new GeneAIFollowOwner).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_FOLLOW_PARENT_MOVE_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_FOLLOW_PARENT_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_FOLLOW_PARENT, new GeneAIFollowParent).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_HURT_BY_TARGET_CALL_HELP, BOOLEAN)
        geneRegistry.registerGene(GENE_AI_HURT_BY_TARGET_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_HURT_BY_TARGET, new GeneAIHurtByTarget).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_LEAP_AT_TARGET_MOTION_Y, FLOAT)
        geneRegistry.registerGene(GENE_AI_LEAP_AT_TARGET_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_LEAP_AT_TARGET, new GeneAILeapAtTarget).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_LOOK_AT_TRADE_PLAYER_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_LOOK_AT_TRADE_PLAYER, new GeneAILookAtTradePlayer).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_LOOK_AT_VILLAGER_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_LOOK_AT_VILLAGER, new GeneAILookAtVillager).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_LOOK_IDLE_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_LOOK_IDLE, new GeneAILookIdle).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_MATE_MOVE_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_MATE_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_MATE, new GeneAIMate).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_MOVE_INDOORS_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_MOVE_INDOORS, new GeneAIMoveIndoors).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_MOVE_THROUGH_VILLAGE_MOVE_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_MOVE_THROUGH_VILLAGE_IS_NOCTURNAL, BOOLEAN)
        geneRegistry.registerGene(GENE_AI_MOVE_THROUGH_VILLAGE_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_MOVE_THROUGH_VILLAGE, new GeneAIMoveThroughVillage).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_MOVE_TOWARDS_RESTRICTION_MOVE_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_MOVE_TOWARDS_RESTRICTION_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_MOVE_TOWARDS_RESTRICTION, new GeneAIMoveTowardsRestriction).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_MOVE_TOWARDS_TARGET_MOVE_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_MOVE_TOWARDS_TARGET_MAX_DISTANCE, FLOAT)
        geneRegistry.registerGene(GENE_AI_MOVE_TOWARDS_TARGET_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_MOVE_TOWARDS_TARGET, new GeneAIMoveTowardsTarget).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET, CLASS_ARRAY)
        geneRegistry.registerGene(GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE, INTEGER_ARRAY)
        geneRegistry.registerGene(GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE, BOOLEAN_ARRAY)
        geneRegistry.registerGene(GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY, BOOLEAN_ARRAY)
        geneRegistry.registerGene(GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR, STRING_ARRAY)
        geneRegistry.registerGene(GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX, INTEGER_ARRAY).noMutations
        geneRegistry.registerMasterGene(GENE_AI_NEAREST_ATTACKABLE_TARGET, new GeneAINearestAttackableTarget).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_OCELOT_ATTACK_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_OCELOT_ATTACK, new GeneAIOcelotAttack).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_OCELOT_SIT_MOVE_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_OCELOT_SIT_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_OCELOT_SIT, new GeneAIOcelotSit).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_OPEN_DOOR_CLOSE_DOOR, BOOLEAN)
        geneRegistry.registerGene(GENE_AI_OPEN_DOOR_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_OPEN_DOOR, new GeneAIOpenDoor).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_OWNER_HURT_BY_TARGET_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_OWNER_HURT_BY_TARGET, new GeneAIOwnerHurtByTarget).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_OWNER_HURT_TARGET_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_OWNER_HURT_TARGET, new GeneAIOwnerHurtTarget).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_PANIC_MOVE_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_PANIC_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_PANIC, new GeneAIPanic).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_PLAY_MOVE_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_PLAY_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_PLAY, new GeneAIPlay).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_RESTRICT_OPEN_DOOR_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_RESTRICT_OPEN_DOOR, new GeneAIRestrictOpenDoor).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_RESTRICT_SUN_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_RESTRICT_SUN, new GeneAIRestrictSun).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_RUN_AROUND_LIKE_CRAZY_MOVE_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_RUN_AROUND_LIKE_CRAZY_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_RUN_AROUND_LIKE_CRAZY, new GeneAIRunAroundLikeCrazy).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_SIT_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_SIT, new GeneAISit).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_SWIMMING_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_SWIMMING, new GeneAISwimming).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_TARGET_NON_TAMED_VISIBLE, BOOLEAN_ARRAY)
        geneRegistry.registerGene(GENE_AI_TARGET_NON_TAMED_TARGET_CHANCE, INTEGER_ARRAY)
        geneRegistry.registerGene(GENE_AI_TARGET_NON_TAMED_TARGET, CLASS_ARRAY)
        geneRegistry.registerGene(GENE_AI_TARGET_NON_TAMED_INDEX, INTEGER_ARRAY).noMutations
        geneRegistry.registerMasterGene(GENE_AI_TARGET_NON_TAMED, new GeneAITargetNonTamed).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_TEMPT_ITEM, ITEMSTACK_ARRAY)
        geneRegistry.registerGene(GENE_AI_TEMPT_MOVE_SPEED, new GeneMoveSpeed(DOUBLE_ARRAY))
        geneRegistry.registerGene(GENE_AI_TEMPT_SCARED_BY_PLAYER, BOOLEAN_ARRAY)
        geneRegistry.registerGene(GENE_AI_TEMPT_INDEX, INTEGER_ARRAY).noMutations
        geneRegistry.registerMasterGene(GENE_AI_TEMPT, new GeneAITempt).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_TRADE_PLAYER_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_TRADE_PLAYER, new GeneAITradePlayer).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_VILLAGER_MATE_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_VILLAGER_MATE, new GeneAIVillagerMate).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_WANDER_MOVE_SPEED, new GeneMoveSpeed(DOUBLE))
        geneRegistry.registerGene(GENE_AI_WANDER_INDEX, INTEGER).noMutations
        geneRegistry.registerMasterGene(GENE_AI_WANDER, new GeneAIWander).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_TARGET, CLASS_ARRAY)
        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_RANGE, FLOAT_ARRAY)
        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_CHANCE, FLOAT_ARRAY)
        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_INDEX, INTEGER_ARRAY).noMutations
        geneRegistry.registerMasterGene(GENE_AI_WATCH_CLOSEST, new GeneAIWatchClosest).setCombinedInherit

        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_2_TARGET, CLASS_ARRAY)
        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_2_RANGE, FLOAT_ARRAY)
        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_2_CHANCE, FLOAT_ARRAY)
        geneRegistry.registerGene(GENE_AI_WATCH_CLOSEST_2_INDEX, INTEGER_ARRAY).noMutations
        geneRegistry.registerMasterGene(GENE_AI_WATCH_CLOSEST_2, new GeneAIWatchClosest2).setCombinedInherit


        geneRegistry.registerMasterGene(GENE_USE_NEW_AI, new GeneUseNewAI)
        geneRegistry.registerMasterGene(GENE_USE_OLD_AI, new GeneUseOldAI)

        geneRegistry.registerGene(GENE_TEXTURE, STRING)
        geneRegistry.registerGene(GENE_MODEL, new GeneModel)
        geneRegistry.registerCustomInheritance(GENE_MODEL)

        geneRegistry.registerGene(GENE_FUSE_TIME, INTEGER)
        geneRegistry.registerGene(GENE_EXPLOSION_RADIUS, INTEGER)
        geneRegistry.registerGene(GENE_CAN_BE_CHARGED, BOOLEAN)
        geneRegistry.registerGene(GENE_FLINT_AND_STEEL_IGNITE, BOOLEAN)

        geneRegistry.registerGene(GENE_KILLED_BY_SPECIFIC_ENTITY_DROPS, ITEMSTACK_ARRAY)
        geneRegistry.registerGene(GENE_KILLED_BY_SPECIFIC_ENTITY_ENTITY, CLASS)
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

        standardSoulRegistry.register(new StandardSoulZombie)
        standardSoulRegistry.register(new StandardSoulSkeleton)
        standardSoulRegistry.register(new StandardSoulCreeper)

        animationRegistry.register(ANIMATION_WALK_TWO_LEGGED, new AnimationTwoLegged)
        animationRegistry.register(ANIMATION_WALK_TWO_ARMED, new AnimationTwoArmed)
        animationRegistry.register(ANIMATION_HEAD, new AnimationHead)
        animationRegistry.register(ANIMATION_WALK_FOUR_LEGGED, new AnimationFourLegged)

        alleleTypeRegistry.registerAlleleType(new AlleleType(classOf[Boolean]) {
            override def writeToNBT(compound: NBTTagCompound, value: Boolean) = compound.setBoolean("value", value)
            override def readFromNBT(compound: NBTTagCompound): Boolean = compound.getBoolean("value")
        })
        alleleTypeRegistry.registerAlleleType(new AlleleType(classOf[Byte]) {
            override def writeToNBT(compound: NBTTagCompound, value: Byte) = compound.setByte("value", value)
            override def readFromNBT(compound: NBTTagCompound): Byte = compound.getByte("value")
        })
        alleleTypeRegistry.registerAlleleType(new AlleleType(classOf[Short]) {
            override def writeToNBT(compound: NBTTagCompound, value: Short) = compound.setShort("value", value)
            override def readFromNBT(compound: NBTTagCompound): Short = compound.getShort("value")
        })
        alleleTypeRegistry.registerAlleleType(new AlleleType(classOf[Int]) {
            override def writeToNBT(compound: NBTTagCompound, value: Int) = compound.setInteger("value", value)
            override def readFromNBT(compound: NBTTagCompound): Int = compound.getInteger("value")
        })
        alleleTypeRegistry.registerAlleleType(new AlleleType(classOf[Float]) {
            override def writeToNBT(compound: NBTTagCompound, value: Float) = compound.setFloat("value", value)
            override def readFromNBT(compound: NBTTagCompound): Float = compound.getFloat("value")
        })
        alleleTypeRegistry.registerAlleleType(new AlleleType(classOf[Double]) {
            override def writeToNBT(compound: NBTTagCompound, value: Double) = compound.setDouble("value", value)
            override def readFromNBT(compound: NBTTagCompound): Double = compound.getDouble("value")
        })
        alleleTypeRegistry.registerAlleleType(new AlleleType(classOf[Long]) {
            override def writeToNBT(compound: NBTTagCompound, value: Long) = compound.setLong("value", value)
            override def readFromNBT(compound: NBTTagCompound): Long = compound.getLong("value")
        })
        alleleTypeRegistry.registerAlleleType(new AlleleType(classOf[String]) {
            override def writeToNBT(compound: NBTTagCompound, value: String) = compound.setString("value", value)
            override def readFromNBT(compound: NBTTagCompound): String = compound.getString("value")
        })
        alleleTypeRegistry.registerAlleleType(new AlleleType(classOf[Class[_]]) {
            override def writeToNBT(compound: NBTTagCompound, value: Class[_]) = compound.setString("value", value.getName)
            override def readFromNBT(compound: NBTTagCompound): Class[_] = Class.forName(compound.getString("value"))
        })
        alleleTypeRegistry.registerAlleleType(new AlleleType(classOf[ItemStack]) {
            override def writeToNBT(compound: NBTTagCompound, value: ItemStack) = compound.setTag("value", value.writeToNBT(compound))
            override def readFromNBT(compound: NBTTagCompound): ItemStack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("value"))
        })
        alleleTypeRegistry.registerAlleleType(new AlleleType(classOf[ModelPart]) {
            override def writeToNBT(compound: NBTTagCompound, value: ModelPart) = compound.setTag("value", value.writeToNBT(compound))
            override def readFromNBT(compound: NBTTagCompound): ModelPart = ModelPart.fromNBT(compound.getCompoundTag("value"))
        })
    }
}
