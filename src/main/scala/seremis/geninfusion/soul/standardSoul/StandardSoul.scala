package seremis.geninfusion.soul.standardSoul

import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

import net.minecraft.client.Minecraft
import net.minecraft.client.model.ModelBiped
import net.minecraft.entity._
import net.minecraft.entity.monster.EntityCreeper
import net.minecraft.entity.passive.{EntityChicken, EntityTameable}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ResourceLocation
import seremis.geninfusion.api.lib.AttachmentPoints
import seremis.geninfusion.api.lib.Genes._
import seremis.geninfusion.api.lib.ModelPartTypes.{Biped, General}
import seremis.geninfusion.api.soul.{IChromosome, IStandardSoul}
import seremis.geninfusion.api.util.render.model.{Model, ModelPart}
import seremis.geninfusion.soul.{Allele, Chromosome}

abstract class StandardSoul extends IStandardSoul {

    override def isStandardSoulForEntity(entity: EntityLiving) = getStandardSoulEntity.isInstance(entity)

    override def getChromosomeFromGene(entity: EntityLiving, gene: String): IChromosome = {

        //Automatically detected genes.
        if(gene == GeneAttackDamage)
            return new Chromosome(gene, new Allele(true, entity.getAttributeMap.getAttributeInstance(SharedMonsterAttributes.attackDamage).getBaseValue, classOf[Double]))
        if(gene == GeneCreatureAttribute)
            return new Chromosome(gene, new Allele(true, entity.getCreatureAttribute.ordinal(), classOf[Int]))
        if(gene == GeneFollowRange)
            return new Chromosome(gene, new Allele(true, entity.getAttributeMap.getAttributeInstance(SharedMonsterAttributes.followRange).getBaseValue, classOf[Double]))
        if(gene == GeneImmuneToFire)
            return new Chromosome(gene, new Allele(true, entity.isImmuneToFire, classOf[Boolean]))
        if(gene == GeneInvulnerable)
            return new Chromosome(gene, new Allele(true, entity.isEntityInvulnerable, classOf[Boolean]))
        //TODO remove is_creature gene
        if(gene == GeneIsCreature)
            return new Chromosome(gene, new Allele(false, entity.isInstanceOf[EntityCreature], classOf[Boolean]))
        if(gene == GeneKnockBackResistance)
            return new Chromosome(gene, new Allele(true, entity.getAttributeMap.getAttributeInstance(SharedMonsterAttributes.knockbackResistance).getBaseValue, classOf[Double]))
        if(gene == GeneMaxHealth)
            return new Chromosome(gene, new Allele(true, entity.getAttributeMap.getAttributeInstance(SharedMonsterAttributes.maxHealth).getBaseValue, classOf[Double]))
        if(gene == GeneMaxHurtResistantTime)
            return new Chromosome(gene, new Allele(true, entity.maxHurtResistantTime, classOf[Int]))
        if(gene == GeneMovementSpeed)
            return new Chromosome(gene, new Allele(true, entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue, classOf[Double]))
        if(gene == GenePortalCooldown)
            return new Chromosome(gene, new Allele(true, entity.getPortalCooldown, classOf[Int]))
        if(gene == GeneTalkInterval)
            return new Chromosome(gene, new Allele(true, entity.getTalkInterval, classOf[Int]))
        if(gene == GeneTeleportTimeInPortal)
            return new Chromosome(gene, new Allele(true, entity.getMaxInPortalTime, classOf[Int]))
        if(gene == GeneVerticalFaceSpeed)
            return new Chromosome(gene, new Allele(true, entity.getVerticalFaceSpeed, classOf[Int]))
        if(gene == GeneIsTameable)
            return new Chromosome(gene, new Allele(true, entity.isInstanceOf[EntityTameable], classOf[Boolean]))
        if(gene == GeneWidth)
            return new Chromosome(gene, new Allele(true, entity.width, classOf[Float]))
        if(gene == GeneHeight)
            return new Chromosome(gene, new Allele(true, entity.height, classOf[Float]))
        if(gene == GeneIsChild)
            return new Chromosome(gene, new Allele(true, entity.isChild, classOf[Boolean]))
        if(gene == GeneCanProcreate)
            return new Chromosome(gene, new Allele(true, entity.isInstanceOf[EntityAgeable], classOf[Boolean]))

        //Manual genes.
        if(gene == GeneBurnsInDaylight)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneChildrenBurnInDaylight)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneDeathSound)
            return new Chromosome(gene, new Allele(false, "game.hostile.die", classOf[String]))
        if(gene == GeneDrownsInAir)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneDrownsInWater)
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))
        if(gene == GeneEquipmentDropChances)
            return new Chromosome(gene, new Allele(true, Array(0.085F, 0.085F, 0.085F, 0.085F, 0.085F), classOf[Array[Float]]))
        if(gene == GeneExperienceValue)
            return new Chromosome(gene, new Allele(true, 5, classOf[Int]))
        if(gene == GeneHurtSound)
            return new Chromosome(gene, new Allele(true, "mob.hostile.hurt", classOf[String]))
        if(gene == GeneItemDrops)
            return new Chromosome(gene, new Allele(true, null, classOf[Array[ItemStack]]))
        if(gene == GeneLivingSound)
            return new Chromosome(gene, new Allele(false, null, classOf[String]))
        if(gene == GenePicksUpItems)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneRareItemDropChances)
            return new Chromosome(gene, new Allele(true, Array(0.33F, 0.33F, 0.33F), classOf[Array[Float]]))
        if(gene == GeneRareItemDrops)
            return new Chromosome(gene, new Allele(true, null, classOf[Array[ItemStack]]))
        if(gene == GeneSetOnFireFromAttack)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneShouldDespawn)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneSoundVolume)
            return new Chromosome(gene, new Allele(true, 1F, classOf[Float]))
        if(gene == GeneSplashSound)
            return new Chromosome(gene, new Allele(true, "game.neutral.swim.splash", classOf[String]))
        if(gene == GeneSwimSound)
            return new Chromosome(gene, new Allele(true, "game.neutral.swim", classOf[String]))
        if(gene == GeneWalkSound)
            return new Chromosome(gene, new Allele(true, "mob.neutral.step", classOf[String]))
        if(gene == GeneUseNewAI)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneUseOldAI)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneImmuneToPoison)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAffectedByWeb)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneCanClimbWalls)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))

        if(gene == GeneChildXPModifier)
            return new Chromosome(gene, new Allele(true, 1.0F, classOf[Float]))
        if(gene == GeneChildSpeedModifier)
            return new Chromosome(gene, new Allele(true, 0.0D, classOf[Double]))

        if(gene == GeneAttackTargetVisible)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneMinAttackBrightness)
            return new Chromosome(gene, new Allele(true, 0.0F, classOf[Float]))
        if(gene == GeneMaxAttackBrightness)
            return new Chromosome(gene, new Allele(true, 1.0F, classOf[Float]))
        if(gene == GeneJumpAtAttackTarget)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))

        //AI genes
        if(gene == GeneAIArrowAttack)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIArrowAttackIndex)
            return new Chromosome(gene, new Allele(true, 2, classOf[Int]))
        if(gene == GeneAIArrowAttackMaxRangedAttackTime)
            return new Chromosome(gene, new Allele(false, 0, classOf[Int]))
        if(gene == GeneAIArrowAttackMinRangedAttackTime)
            return new Chromosome(gene, new Allele(false, 0, classOf[Int]))
        if(gene == GeneAIArrowAttackMoveSpeed)
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))
        if(gene == GeneAIArrowAttackRangedAttackTimeModifier)
            return new Chromosome(gene, new Allele(false, 1.0F, classOf[Float]))

        if(gene == GeneAIAttackOnCollide)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAIAttackOnCollideIndex)
            return new Chromosome(gene, new Allele(true, Array(2), classOf[Array[Int]]))
        if(gene == GeneAIAttackOnCollideLongMemory)
            return new Chromosome(gene, new Allele(false, Array(false), classOf[Array[Boolean]]))
        if(gene == GeneAIAttackOnCollideMoveSpeed)
            return new Chromosome(gene, new Allele(false, Array(1.0D), classOf[Array[Double]]))
        if(gene == GeneAIAttackOnCollideTarget)
            return new Chromosome(gene, new Allele(false, Array(classOf[EntityPlayer]), classOf[Array[Class[_]]]))

        if(gene == GeneAIAvoidEntity)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIAvoidEntityIndex)
            return new Chromosome(gene, new Allele(false, Array(3), classOf[Array[Int]]))
        if(gene == GeneAIAvoidEntityFarSpeed)
            return new Chromosome(gene, new Allele(false, Array(1.0D), classOf[Array[Double]]))
        if(gene == GeneAIAvoidEntityNearSpeed)
            return new Chromosome(gene, new Allele(false, Array(1.2D), classOf[Array[Double]]))
        if(gene == GeneAIAvoidEntityRange)
            return new Chromosome(gene, new Allele(false, Array(6.0F), classOf[Array[Float]]))
        if(gene == GeneAIAvoidEntityTarget)
            return new Chromosome(gene, new Allele(false, Array(classOf[EntityCreeper]), classOf[Array[Class[_]]]))

        if(gene == GeneAIBeg)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIBegIndex)
            return new Chromosome(gene, new Allele(true, 8, classOf[Int]))
        if(gene == GeneAIBegRange)
            return new Chromosome(gene, new Allele(false, 0.0F, classOf[Float]))

        if(gene == GeneAIBreakDoor)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIBreakDoorIndex)
            return new Chromosome(gene, new Allele(true, 1, classOf[Int]))

        if(gene == GeneAIControlledByPlayer)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIControlledByPlayerIndex)
            return new Chromosome(gene, new Allele(true, 2, classOf[Int]))
        if(gene == GeneAIControlledByPlayerMaxSpeed)
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))

        if(gene == GeneAICreeperSwell)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAICreeperSwellIndex)
            return new Chromosome(gene, new Allele(true, 2, classOf[Int]))

        if(gene == GeneAIDefendVillage)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIDefendVillageIndex)
            return new Chromosome(gene, new Allele(true, 1, classOf[Int]))

        if(gene == GeneAIEatGrass)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIEatGrassIndex)
            return new Chromosome(gene, new Allele(true, 5, classOf[Int]))

        if(gene == GeneAIFleeSun)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIFleeSunIndex)
            return new Chromosome(gene, new Allele(true, 3, classOf[Int]))
        if(gene == GeneAIFleeSunMoveSpeed)
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))

        if(gene == GeneAIFollowGolem)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIFollowGolemIndex)
            return new Chromosome(gene, new Allele(true, 7, classOf[Int]))

        if(gene == GeneAIFollowOwner)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIFollowOwnerIndex)
            return new Chromosome(gene, new Allele(true, 5, classOf[Int]))
        if(gene == GeneAIFollowOwnerMoveSpeed)
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))
        if(gene == GeneAIFollowOwnerMinDistance)
            return new Chromosome(gene, new Allele(false, 5.0F, classOf[Float]))
        if(gene == GeneAIFollowOwnerMaxDistance)
            return new Chromosome(gene, new Allele(true, 10.0F, classOf[Float]))

        if(gene == GeneAIFollowParent)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIFollowParentIndex)
            return new Chromosome(gene, new Allele(true, 4, classOf[Int]))
        if(gene == GeneAIFollowParentMoveSpeed)
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))

        if(gene == GeneAIHurtByTarget)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAIHurtByTargetIndex)
            return new Chromosome(gene, new Allele(true, 2, classOf[Int]))
        if(gene == GeneAIHurtByTargetCallHelp)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))

        if(gene == GeneAILeapAtTarget)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAILeapAtTargetIndex)
            return new Chromosome(gene, new Allele(true, 3, classOf[Int]))
        if(gene == GeneAILeapAtTargetMotionY)
            return new Chromosome(gene, new Allele(false, 0.4F, classOf[Float]))

        if(gene == GeneAILookAtTradePlayer)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAILookAtTradePlayerIndex)
            return new Chromosome(gene, new Allele(true, 1, classOf[Int]))

        if(gene == GeneAILookAtVillager)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAILookAtVillagerIndex)
            return new Chromosome(gene, new Allele(true, 5, classOf[Int]))

        if(gene == GeneAILookIdle)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAILookIdleIndex)
            return new Chromosome(gene, new Allele(true, 8, classOf[Int]))

        if(gene == GeneAIMate)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIMateIndex)
            return new Chromosome(gene, new Allele(true, 2, classOf[Int]))
        if(gene == GeneAIMateMoveSpeed)
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))

        if(gene == GeneAIMoveIndoors)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIMoveIndoorsIndex)
            return new Chromosome(gene, new Allele(true, 2, classOf[Int]))

        if(gene == GeneAIMoveThroughVillage)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIMoveThroughVillageIndex)
            return new Chromosome(gene, new Allele(true, 3, classOf[Int]))
        if(gene == GeneAIMoveThroughVillageIsNocturnal)
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))
        if(gene == GeneAIMoveThroughVillageMoveSpeed)
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))

        if(gene == GeneAIMoveTowardsRestriction)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIMoveTowardsRestrictionIndex)
            return new Chromosome(gene, new Allele(true, 5, classOf[Int]))
        if(gene == GeneAIMoveTowardsRestrictionMoveSpeed)
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))

        if(gene == GeneAIMoveTowardsTarget)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIMoveTowardsTargetIndex)
            return new Chromosome(gene, new Allele(true, 2, classOf[Int]))
        if(gene == GeneAIMoveTowardsTargetMaxDistance)
            return new Chromosome(gene, new Allele(true, 32.0F, classOf[Float]))
        if(gene == GeneAIMoveTowardsTargetMoveSpeed)
            return new Chromosome(gene, new Allele(true, 0.9D, classOf[Double]))

        if(gene == GeneAINearestAttackableTarget)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAINearestAttackableTargetIndex)
            return new Chromosome(gene, new Allele(true, Array(2), classOf[Array[Int]]))
        if(gene == GeneAINearestAttackableTargetEntitySelector)
            return new Chromosome(gene, new Allele(true, Array(""), classOf[Array[String]]))
        if(gene == GeneAINearestAttackableTargetNearbyOnly)
            return new Chromosome(gene, new Allele(true, Array(false), classOf[Array[Boolean]]))
        if(gene == GeneAINearestAttackableTargetTarget)
            return new Chromosome(gene, new Allele(false, Array(classOf[EntityPlayer]), classOf[Array[Class[_]]]))
        if(gene == GeneAINearestAttackableTargetTargetChance)
            return new Chromosome(gene, new Allele(true, Array(0), classOf[Array[Int]]))
        if(gene == GeneAINearestAttackableTargetVisible)
            return new Chromosome(gene, new Allele(true, Array(false), classOf[Array[Boolean]]))

        if(gene == GeneAIOcelotAttack)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIOcelotAttackIndex)
            return new Chromosome(gene, new Allele(true, 8, classOf[Int]))

        if(gene == GeneAIOcelotSit)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIOcelotSitIndex)
            return new Chromosome(gene, new Allele(true, 6, classOf[Int]))
        if(gene == GeneAIOcelotSitMoveSpeed)
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))

        if(gene == GeneAIOpenDoor)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIOpenDoorIndex)
            return new Chromosome(gene, new Allele(true, 4, classOf[Int]))
        if(gene == GeneAIOpenDoorCloseDoor)
            return new Chromosome(gene, new Allele(false, true, classOf[Boolean]))

        if(gene == GeneAIOwnerHurtByTarget)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIOwnerHurtByTargetIndex)
            return new Chromosome(gene, new Allele(true, 1, classOf[Int]))
        if(gene == GeneAIOwnerHurtTarget)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIOwnerHurtTargetIndex)
            return new Chromosome(gene, new Allele(true, 2, classOf[Int]))

        if(gene == GeneAIPanic)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIPanicIndex)
            return new Chromosome(gene, new Allele(true, 1, classOf[Int]))
        if(gene == GeneAIPanicMoveSpeed)
            return new Chromosome(gene, new Allele(true, 1.25D, classOf[Double]))

        if(gene == GeneAIPlay)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIPlayIndex)
            return new Chromosome(gene, new Allele(true, 8, classOf[Int]))
        if(gene == GeneAIPlayMoveSpeed)
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))

        if(gene == GeneAIRestrictOpenDoor)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIRestrictOpenDoorIndex)
            return new Chromosome(gene, new Allele(true, 3, classOf[Int]))
        if(gene == GeneAIRestrictSun)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIRestrictSunIndex)
            return new Chromosome(gene, new Allele(true, 2, classOf[Int]))

        if(gene == GeneAIRunAroundLikeCrazy)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIRunAroundLikeCrazyIndex)
            return new Chromosome(gene, new Allele(true, 1, classOf[Int]))
        if(gene == GeneAIRunAroundLikeCrazyMoveSpeed)
            return new Chromosome(gene, new Allele(false, 1.0D, classOf[Double]))

        if(gene == GeneAISit)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAISitIndex)
            return new Chromosome(gene, new Allele(true, 2, classOf[Int]))

        if(gene == GeneAISwimming)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAISwimmingIndex)
            return new Chromosome(gene, new Allele(true, 0, classOf[Int]))

        if(gene == GeneAITargetNonTamed)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAITargetNonTamedIndex)
            return new Chromosome(gene, new Allele(true, Array(1), classOf[Array[Int]]))
        if(gene == GeneAITargetNonTamedTarget)
            return new Chromosome(gene, new Allele(true, Array(classOf[EntityChicken]), classOf[Array[Class[_]]]))
        if(gene == GeneAITargetNonTamedTargetChance)
            return new Chromosome(gene, new Allele(true, Array(750), classOf[Array[Int]]))
        if(gene == GeneAITargetNonTamedVisible)
            return new Chromosome(gene, new Allele(true, Array(false), classOf[Array[Boolean]]))

        if(gene == GeneAITempt)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAITemptIndex)
            return new Chromosome(gene, new Allele(true, Array(2), classOf[Array[Int]]))
        if(gene == GeneAITemptItem)
            return new Chromosome(gene, new Allele(true, Array(new ItemStack(Items.wheat)), classOf[Array[ItemStack]]))
        if(gene == GeneAITemptMoveSpeed)
            return new Chromosome(gene, new Allele(true, Array(1.2D), classOf[Array[Double]]))
        if(gene == GeneAITemptScaredByPlayer)
            return new Chromosome(gene, new Allele(true, Array(false), classOf[Array[Boolean]]))

        if(gene == GeneAITradePlayer)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAITradePlayerIndex)
            return new Chromosome(gene, new Allele(true, 1, classOf[Int]))

        if(gene == GeneAIVillagerMate)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIVillagerMateIndex)
            return new Chromosome(gene, new Allele(true, 6, classOf[Int]))

        if(gene == GeneAIWander)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAIWanderIndex)
            return new Chromosome(gene, new Allele(true, 5, classOf[Int]))
        if(gene == GeneAIWanderMoveSpeed)
            return new Chromosome(gene, new Allele(true, 1.0D, classOf[Double]))

        if(gene == GeneAIWatchClosest)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneAIWatchClosestIndex)
            return new Chromosome(gene, new Allele(true, Array(6), classOf[Array[Int]]))
        if(gene == GeneAIWatchClosestChance)
            return new Chromosome(gene, new Allele(true, Array(0.02F), classOf[Array[Float]]))
        if(gene == GeneAIWatchClosestRange)
            return new Chromosome(gene, new Allele(true, Array(8.0F), classOf[Array[Float]]))
        if(gene == GeneAIWatchClosestTarget)
            return new Chromosome(gene, new Allele(true, Array(classOf[EntityPlayer]), classOf[Array[Class[_]]]))

        if(gene == GeneAIWatchClosest2)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneAIWatchClosest2Index)
            return new Chromosome(gene, new Allele(true, Array(9), classOf[Array[Int]]))
        if(gene == GeneAIWatchClosest2Chance)
            return new Chromosome(gene, new Allele(true, Array(1.0F), classOf[Array[Float]]))
        if(gene == GeneAIWatchClosest2Range)
            return new Chromosome(gene, new Allele(true, Array(3.0F), classOf[Array[Float]]))
        if(gene == GeneAIWatchClosest2Target)
            return new Chromosome(gene, new Allele(true, Array(classOf[EntityPlayer]), classOf[Array[Class[_]]]))

        //Rendering related Genes.
        if(gene == GeneModel)
            return new Chromosome(gene, new Allele(true, null, classOf[Model]))
        if(gene == GeneTexture)
            return new Chromosome(gene, new Allele(true, textureStringToNBT("textures/entity/skeleton/skeleton.png"), classOf[NBTTagCompound]), new Allele(false, textureStringToNBT("textures/entity/skeleton/skeleton.png"), classOf[NBTTagCompound]))


        //Explosion related genes
        if(gene == GeneFuseTime)
            return new Chromosome(gene, new Allele(true, 30, classOf[Int]))
        if(gene == GeneExplosionRadius)
            return new Chromosome(gene, new Allele(true, 3, classOf[Int]))
        if(gene == GeneFlintAndSteelIgnite)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))
        if(gene == GeneCanBeCharged)
            return new Chromosome(gene, new Allele(true, true, classOf[Boolean]))

        if(gene == GeneDropsItemWhenKilledBySpecificEntity)
            return new Chromosome(gene, new Allele(true, false, classOf[Boolean]))
        if(gene == GeneKilledBySpecificEntityDrops)
            return new Chromosome(gene, new Allele(false, null, classOf[Array[ItemStack]]))
        if(gene == GeneKilledBySpecificEntityEntity)
            return new Chromosome(gene, new Allele(false, null, classOf[Class[_]]))

        null
    }

    def textureStringToNBT(textureName: String): NBTTagCompound = {
        val in = Minecraft.getMinecraft.getResourceManager.getResource(new ResourceLocation(textureName)).getInputStream
        val image = ImageIO.read(in)
        val out = new ByteArrayOutputStream()
        val nbt = new NBTTagCompound

        ImageIO.write(image, "png", out)
        nbt.setByteArray("textureBytes", out.toByteArray)

        nbt
    }

    def modelBiped(biped: ModelBiped, setRotation: Boolean = true): Model = {
        val model = new Model

        if(setRotation) {
            biped.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, null.asInstanceOf[Entity])
        }

        biped.bipedHeadwear.rotateAngleY = 0.0F
        biped.bipedHead.rotateAngleY = 0.0F

        model.addPart(ModelPart.rendererToPart(biped.bipedBody, General.Body, AttachmentPoints.Biped.Body))
        model.addPart(ModelPart.rendererToPart(biped.bipedHead, General.Head, AttachmentPoints.Biped.Head))
        model.addPart(ModelPart.rendererToPart(biped.bipedHeadwear, General.Headwear, AttachmentPoints.Biped.Headwear))
        model.addPart(ModelPart.rendererToPart(biped.bipedLeftArm, Biped.ArmLeft, AttachmentPoints.Biped.ArmLeft))
        model.addPart(ModelPart.rendererToPart(biped.bipedRightArm, Biped.ArmRight, AttachmentPoints.Biped.ArmRight))
        model.addPart(ModelPart.rendererToPart(biped.bipedLeftLeg, Biped.LegLeft, AttachmentPoints.Biped.LegLeft))
        model.addPart(ModelPart.rendererToPart(biped.bipedRightLeg, Biped.LegRight, AttachmentPoints.Biped.LegRight))

        model
    }
}