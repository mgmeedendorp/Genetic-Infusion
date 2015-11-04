package seremis.geninfusion.api.lib

import seremis.geninfusion.helper.MCPNames._

/**
 * This is a library class containing all variable names used in reflection.
 * The variables in this class will be automatically converted to srg names
 * by the build script.
 */
object VariableLib {

    var obfuscated = false

    final val EntityIsJumping = field("field_70703_bu")
    final val EntityMoveStrafing = field("field_70702_br")
    final val EntityMoveForward = field("field_70701_bs")
    final val EntityRandomYawVelocity = field("field_70704_bt")
    final val EntityRotationYawHead = field("field_70759_as")
    final val EntityRotationYaw = field("field_70177_z")
    final val EntityRotationPitch = field("field_70125_A")
    final val EntityFleeingTick = field("field_70788_c")
    final val EntityHasAttacked = field("field_70787_b")
    final val EntityEntityToAttack = field("field_70789_a")
    final val EntityPathToEntity = field("field_70786_d")
    final val EntityEntityAge = field("field_70708_bq")
    final val EntityInWater = field("field_70171_ac")
    final val EntityWidth = field("field_70130_N")
    final val EntityHeight = field("field_70131_O")
    final val EntityPosX = field("field_70165_t")
    final val EntityPosY = field("field_70163_u")
    final val EntityPosZ = field("field_70161_v")
    final val EntityPrevPosX = field("field_70169_q")
    final val EntityPrevPosY = field("field_70167_r")
    final val EntityPrevPosZ = field("field_70166_s")
    final val EntityMotionX = field("field_70159_w")
    final val EntityMotionY = field("field_70181_x")
    final val EntityMotionZ = field("field_70179_y")
    final val EntityIsCollidedHorizontally = field("field_70123_F")
    final val EntityIsCollidedVertically = field("field_70124_G")
    final val EntityCurrentTarget = field("field_70776_bF")
    final val EntityNumTicksToChaseTarget = field("field_70700_bx")
    final val EntityDefaultPitch = field("field_70698_bv")
    final val EntityPossibleAnimations = "possibleAnimations"
    final val EntityActiveAnimations = "activeAnimations"
    final val EntityPendingAnimations = "pendingAnimations"
    final val EntityAttackTime = field("field_70724_aR")
    final val EntityHurtTime = field("field_70737_aN")
    final val EntityMaxHurtTime = field("field_70738_aO")
    final val EntityHurtResistantTime = field("field_70172_ad")
    final val EntityMaxHurtResistantTime = field("field_70771_an")
    final val EntityRecentlyHit = field("field_70718_bc")
    final val EntityTicksExisted = field("field_70173_aa")
    final val EntityLastAttacker = field("field_110150_bn")
    final val EntityEntityLivingToAttack = field("field_70755_b")
    final val EntityAttackingPlayer = field("field_70717_bb")
    final val EntityInvulnerable = field("field_83001_bt")
    final val EntityRevengeTimer = field("field_70756_c")
    final val EntityLimbSwingAmount = field("field_70721_aZ")
    final val EntityLastDamage = field("field_110153_bc")
    final val EntityPrevHealth = field("field_70735_aL")
    final val EntityAttackedAtYaw = field("field_70739_aP")
    final val EntityScoreValue = field("field_70744_aE")
    final val EntityDead = field("field_70729_aU")
    final val EntityFuseState = "fuseState"
    final val EntityCharged = "charged"
    final val EntityIgnited = "ignited"
    final val EntityExplosionRadius = "explosionRadius"
    final val EntityFuseTime = "fuseTime"
    final val EntityTimeSinceIgnited = "timeSinceIgnited"
    final val EntityIsImmuneToFire = field("field_70178_ae")
    final val EntityFire = field("field_150480_ab")
    final val EntityFallDistance = field("field_70143_R")
    final val EntityIsDead = field("field_70128_L")
    final val EntityMaximumHomeDistance = field("field_70772_bD")
    final val EntityHomePosition = field("field_70775_bC")
    final val EntityHealth = "health"
    final val EntityCreatureAttribute = "creatureAttribute"
    final val EntityExperienceValue = field("field_70728_aV")
    final val EntityEquipmentDropChances = field("field_82174_bp")
    final val EntityAIEnabled = "aiEnabled"
    final val EntityCaptureDrops = "captureDrops"
    final val EntityCapturedDrops = "capturedDrops"
    final val EntityEquipment = field("field_82182_bS")
    final val EntityPersistenceRequired = field("field_82179_bU")
    final val EntityPreviousEquipment = field("field_82180_bT")
    final val EntityPortalCounter = field("field_82153_h")
    final val EntityInPortal = field("field_71087_bX")
    final val EntityPrevMovedDistance = field("field_70763_ax")
    final val EntityMovedDistance = field("field_70764_aw")
    final val EntityJumpTicks = field("field_70773_bE")
    final val EntityNewPosRotationIncrements = field("field_70716_bi")
    final val EntityNewPosX = field("field_70709_bj")
    final val EntityNewPosY = field("field_70710_bk")
    final val EntityNewPosZ = field("field_110152_bk")
    final val EntityNewRotationYaw = field("field_70712_bm")
    final val EntityNewRotationPitch = field("field_70125_A")
    final val EntityPrevOnGroundSpeedFactor = field("field_70768_au")
    final val EntityOnGroundSpeedFactor = field("field_110154_aX")
    final val EntityLivingSoundTime = field("field_70757_a")
    final val EntityTalkInterval = "talkInterval"
    final val EntityPartialTickTime = "partialTickTime"
    final val EntityDeathTime = field("field_70725_aQ")
    final val EntityFirstUpdate = field("field_70148_d")
    final val EntityMyEntitySize = field("field_70168_am")
    final val EntityAdultHeight = field("field_98056_d")
    final val EntityAdultWidth = field("field_98057_e")
    final val EntityPrevIsChild = "prevIsChild"
    final val EntityChildSpeedModifier = "childSpeedModifier"
    final val EntityInWeb = field("field_70134_J")
    final val DataWatcherWatchedEntity = field("field_151511_a")
    final val DataWatcherWatchedObjects = field("field_75695_b")
    final val DataWatcherNameTag = "nameTag"
    final val DataWatcherGrowingAge = "growingAge"
    final val DataWatcherClimbableWall = "climbableWall"
    final val ModelRendererBoxName = field("field_78802_n")
    final val ModelRendererTextureOffsetX = field("field_78803_o")
    final val ModelRendererTextureOffsetY = field("field_78813_p")
    final val ModelRendererBaseModel = field("field_78810_s")
    final val ModelRendererDisplayList = field("field_78811_r")
    final val ModelRendererCompiled = field("field_78812_q")
    final val ModelBoxQuadList = field("field_78254_i")
    final val ModelBoxVertexPositions = field("field_78253_h")
    final val ModelBoxPosX1 = field("field_78252_a")
    final val ModelBoxPosY1 = field("field_78250_b")
    final val ModelBoxPosZ1 = field("field_78251_c")
    final val ModelBoxPosX2 = field("field_78248_d")
    final val ModelBoxPosY2 = field("field_78249_e")
    final val ModelBoxPosZ2 = field("field_78246_f")
    final val ModelBipedEars = field("field_78121_j")
    final val ModelBipedCloak = field("field_78122_k")
    final val ModelBipedHeadwear = field("field_78114_d")
}
