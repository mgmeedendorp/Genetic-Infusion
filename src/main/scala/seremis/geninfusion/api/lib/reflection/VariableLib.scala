package seremis.geninfusion.api.lib.reflection

import seremis.geninfusion.helper.MCPNames._

/**
  * This is a library class containing all variable names used in reflection.
  * The variables in this class will be automatically converted to mcp names in
  * a dev environment by the MCPNames object.
  * The names that don't call the field() function are not used inside minecraft.
  * These fields are named according to the rule: VarClassNameFieldName
  */
object VariableLib {

    final val VarEntityNextEntityID = field("field_70152_a")
    final val VarEntityEntityId = field("field_145783_c")
    final val VarEntityRenderDistanceWeight = field("field_70155_l")
    final val VarEntityPreventEntitySpawning = field("field_70156_m")
    final val VarEntityRiddenByEntity = field("field_70153_n")
    final val VarEntityRidingEntity = field("field_70154_o")
    final val VarEntityForceSpawn = field("field_98038_p")
    final val VarEntityWorldObj = field("field_70170_p")
    final val VarEntityPrevPosX = field("field_70169_q")
    final val VarEntityPrevPosY = field("field_70167_r")
    final val VarEntityPrevPosZ = field("field_70166_s")
    final val VarEntityPosX = field("field_70165_t")
    final val VarEntityPosY = field("field_70163_u")
    final val VarEntityPosZ = field("field_70161_v")
    final val VarEntityMotionX = field("field_70159_w")
    final val VarEntityMotionY = field("field_70181_x")
    final val VarEntityMotionZ = field("field_70179_y")
    final val VarEntityRotationYaw = field("field_70177_z")
    final val VarEntityRotationPitch = field("field_70125_A")
    final val VarEntityPrevRotationYaw = field("field_70126_B")
    final val VarEntityPrevRotationPitch = field("field_70127_C")
    final val VarEntityBoundingBox = field("field_70121_D")
    final val VarEntityOnGround = field("field_70122_E")
    final val VarEntityIsCollidedHorizontally = field("field_70123_F")
    final val VarEntityIsCollidedVertically = field("field_70124_G")
    final val VarEntityIsCollided = field("field_70132_H")
    final val VarEntityVelocityChanged = field("field_70133_I")
    final val VarEntityIsInWeb = field("field_70134_J")
    final val VarEntityField_70135_K = "field_70135_K"
    final val VarEntityIsDead = field("field_70128_L")
    final val VarEntityYOffset = field("field_70129_M")
    final val VarEntityWidth = field("field_70130_N")
    final val VarEntityHeight = field("field_70131_O")
    final val VarEntityPrevDistanceWalkedModified = field("field_70141_P")
    final val VarEntityDistanceWalkedModified = field("field_70140_Q")
    final val VarEntityDistanceWalkedOnStepModified = field("field_82151_R")
    final val VarEntityFallDistance = field("field_70143_R")
    final val VarEntityNextStepDistance = field("field_70150_b")
    final val VarEntityLastTickPosX = field("field_70142_S")
    final val VarEntityLastTickPosY = field("field_70137_T")
    final val VarEntityLastTickPosZ = field("field_70136_U")
    final val VarEntityYOffset2 = "yOffset2"
    final val VarEntityStepHeight = field("field_70138_W")
    final val VarEntityNoClip = field("field_70145_X")
    final val VarEntityEntityCollisionReduction = field("field_70144_Y")
    final val VarEntityRand = field("field_70146_Z")
    final val VarEntityTicksExisted = field("field_70173_aa")
    final val VarEntityFireResistance = field("field_70174_ab")
    final val VarEntityFire = field("field_70151_c")
    final val VarEntityInWater = field("field_70171_ac")
    final val VarEntityHurtResistantTime = field("field_70172_ad")
    final val VarEntityFirstUpdate = field("field_70148_d")
    final val VarEntityIsImmuneToFire = field("field_70178_ae")
    final val VarEntityDataWatcher = field("field_70180_af")
    final val VarEntityEntityRiderPitchDelta = field("field_70149_e")
    final val VarEntityEntityRiderYawDelta = field("field_70147_f")
    final val VarEntityAddedToChunk = field("field_70175_ag")
    final val VarEntityChunkCoordX = field("field_70176_ah")
    final val VarEntityChunkCoordY = field("field_70162_ai")
    final val VarEntityChunkCoordZ = field("field_70164_aj")
    final val VarEntityServerPosX = field("field_70118_ct")
    final val VarEntityServerPosY = field("field_70117_cu")
    final val VarEntityServerPosZ = field("field_70116_cv")
    final val VarEntityIgnoreFrustumCheck = field("field_70158_ak")
    final val VarEntityIsAirBorne = field("field_70160_al")
    final val VarEntityTimeUntilPortal = field("field_71088_bW")
    final val VarEntityInPortal = field("field_71087_bX")
    final val VarEntityPortalCounter = field("field_82153_h")
    final val VarEntityDimension = field("field_71093_bK")
    final val VarEntityTeleportDirection = field("field_82152_aq")
    final val VarEntityInvulnerable = field("field_83001_bt")
    final val VarEntityEntityUniqueID = field("field_96093_i")
    final val VarEntityMyEntitySize = field("field_70168_am")
    final val VarEntityCustomEntityData = "customEntityData"
    final val VarEntityCaptureDrops = "captureDrops"
    final val VarEntityCapturedDrops = "capturedDrops"
    final val VarEntityPersistentID = "persistentID"
    final val VarEntityPossibleAnimations = "possibleAnimations"
    final val VarEntityActiveAnimations = "activeAnimations"
    final val VarEntityPendingAnimations = "pendingAnimations"
    final val VarEntityFuseState = "fuseState"
    final val VarEntityCharged = "charged"
    final val VarEntityIgnited = "ignited"
    final val VarEntityExplosionRadius = "explosionRadius"
    final val VarEntityFuseTime = "fuseTime"
    final val VarEntityTimeSinceIgnited = "timeSinceIgnited"
    final val VarEntityHealth = "health"
    final val VarEntityCreatureAttribute = "creatureAttribute"
    final val VarEntityAIEnabled = "aiEnabled"
    final val VarEntityTalkInterval = "talkInterval"
    final val VarEntityPartialTickTime = "partialTickTime"
    final val VarEntityPrevIsChild = "prevIsChild"
    final val VarEntityChildSpeedModifier = "childSpeedModifier"
    final val VarDataWatcherWatchedEntity = field("field_151511_a")
    final val VarDataWatcherWatchedObjects = field("field_75695_b")
    final val VarDataWatcherNameTag = "nameTag"
    final val VarDataWatcherGrowingAge = "growingAge"
    final val VarDataWatcherClimbableWall = "climbableWall"
    final val VarDataWatcherSprinting = "sprinting"
    final val VarDataWatcherSneaking = "sneaking"
    final val VarModelRendererBoxName = field("field_78802_n")
    final val VarModelRendererTextureOffsetX = field("field_78803_o")
    final val VarModelRendererTextureOffsetY = field("field_78813_p")
    final val VarModelRendererBaseModel = field("field_78810_s")
    final val VarModelRendererDisplayList = field("field_78811_r")
    final val VarModelRendererCompiled = field("field_78812_q")
    final val VarModelBoxQuadList = field("field_78254_i")
    final val VarModelBoxVertexPositions = field("field_78253_h")
    final val VarModelBoxPosX1 = field("field_78252_a")
    final val VarModelBoxPosY1 = field("field_78250_b")
    final val VarModelBoxPosZ1 = field("field_78251_c")
    final val VarModelBoxPosX2 = field("field_78248_d")
    final val VarModelBoxPosY2 = field("field_78249_e")
    final val VarModelBoxPosZ2 = field("field_78246_f")
}
