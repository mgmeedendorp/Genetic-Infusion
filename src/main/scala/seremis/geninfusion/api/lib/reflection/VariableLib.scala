package seremis.geninfusion.api.lib.reflection

import seremis.geninfusion.helper.MCPNames._

/**
  * This is a library class containing all variable names used in reflection.
  * The variables in this class will be automatically converted to mcp names in
  * a dev environment by the MCPNames object.
  * The names that don't call the field() function are not used inside minecraft.
  */
object VariableLib {

    final val EntityNextEntityID = field("field_70152_a")
    final val EntityEntityId = field("field_145783_c")
    final val EntityRenderDistanceWeight = field("field_70155_l")
    final val EntityPreventEntitySpawning = field("field_70156_m")
    final val EntityRiddenByEntity = field("field_70153_n")
    final val EntityRidingEntity = field("field_70154_o")
    final val EntityForceSpawn = field("field_98038_p")
    final val EntityWorldObj = field("field_70170_p")
    final val EntityPrevPosX = field("field_70169_q")
    final val EntityPrevPosY = field("field_70167_r")
    final val EntityPrevPosZ = field("field_70166_s")
    final val EntityPosX = field("field_70165_t")
    final val EntityPosY = field("field_70163_u")
    final val EntityPosZ = field("field_70161_v")
    final val EntityMotionX = field("field_70159_w")
    final val EntityMotionY = field("field_70181_x")
    final val EntityMotionZ = field("field_70179_y")
    final val EntityRotationYaw = field("field_70177_z")
    final val EntityRotationPitch = field("field_70125_A")
    final val EntityPrevRotationYaw = field("field_70126_B")
    final val EntityPrevRotationPitch = field("field_70127_C")
    final val EntityBoundingBox = field("field_70121_D")
    final val EntityOnGround = field("field_70122_E")
    final val EntityIsCollidedHorizontally = field("field_70123_F")
    final val EntityIsCollidedVertically = field("field_70124_G")
    final val EntityIsCollided = field("field_70132_H")
    final val EntityVelocityChanged = field("field_70133_I")
    final val EntityIsInWeb = field("field_70134_J")
    final val EntityField_70135_K = "field_70135_K"
    final val EntityIsDead = field("field_70128_L")
    final val EntityYOffset = field("field_70129_M")
    final val EntityWidth = field("field_70130_N")
    final val EntityHeight = field("field_70131_O")
    final val EntityPrevDistanceWalkedModified = field("field_70141_P")
    final val EntityDistanceWalkedModified = field("field_70140_Q")
    final val EntityDistanceWalkedOnStepModified = field("field_82151_R")
    final val EntityFallDistance = field("field_70143_R")
    final val EntityNextStepDistance = field("field_70150_b")
    final val EntityLastTickPosX = field("field_70142_S")
    final val EntityLastTickPosY = field("field_70137_T")
    final val EntityLastTickPosZ = field("field_70136_U")
    final val EntityYOffset2 = "yOffset2"
    final val EntityStepHeight = field("field_70138_W")
    final val EntityNoClip = field("field_70145_X")
    final val EntityEntityCollisionReduction = field("field_70144_Y")
    final val EntityRand = field("field_70146_Z")
    final val EntityTicksExisted = field("field_70173_aa")
    final val EntityFireResistance = field("field_70174_ab")
    final val EntityFire = field("field_70151_c")
    final val EntityInWater = field("field_70171_ac")
    final val EntityHurtResistantTime = field("field_70172_ad")
    final val EntityFirstUpdate = field("field_70148_d")
    final val EntityIsImmuneToFire = field("field_70178_ae")
    final val EntityDataWatcher = field("field_70180_af")
    final val EntityEntityRiderPitchDelta = field("field_70149_e")
    final val EntityEntityRiderYawDelta = field("field_70147_f")
    final val EntityAddedToChunk = field("field_70175_ag")
    final val EntityChunkCoordX = field("field_70176_ah")
    final val EntityChunkCoordY = field("field_70162_ai")
    final val EntityChunkCoordZ = field("field_70164_aj")
    final val EntityServerPosX = field("field_70118_ct")
    final val EntityServerPosY = field("field_70117_cu")
    final val EntityServerPosZ = field("field_70116_cv")
    final val EntityIgnoreFrustumCheck = field("field_70158_ak")
    final val EntityIsAirBorne = field("field_70160_al")
    final val EntityTimeUntilPortal = field("field_71088_bW")
    final val EntityInPortal = field("field_71087_bX")
    final val EntityPortalCounter = field("field_82153_h")
    final val EntityDimension = field("field_71093_bK")
    final val EntityTeleportDirection = field("field_82152_aq")
    final val EntityInvulnerable = field("field_83001_bt")
    final val EntityEntityUniqueID = field("field_96093_i")
    final val EntityMyEntitySize = field("field_70168_am")
    final val EntityCustomEntityData = "customEntityData"
    final val EntityCaptureDrops = "captureDrops"
    final val EntityCapturedDrops = "capturedDrops"
    final val EntityPersistentID = "persistentID"




    final val EntityPossibleAnimations = "possibleAnimations"
    final val EntityActiveAnimations = "activeAnimations"
    final val EntityPendingAnimations = "pendingAnimations"
    final val EntityFuseState = "fuseState"
    final val EntityCharged = "charged"
    final val EntityIgnited = "ignited"
    final val EntityExplosionRadius = "explosionRadius"
    final val EntityFuseTime = "fuseTime"
    final val EntityTimeSinceIgnited = "timeSinceIgnited"
    final val EntityHealth = "health"
    final val EntityCreatureAttribute = "creatureAttribute"
    final val EntityAIEnabled = "aiEnabled"
    final val EntityTalkInterval = "talkInterval"
    final val EntityPartialTickTime = "partialTickTime"
    final val EntityPrevIsChild = "prevIsChild"
    final val EntityChildSpeedModifier = "childSpeedModifier"
    final val DataWatcherWatchedEntity = field("field_151511_a")
    final val DataWatcherWatchedObjects = field("field_75695_b")
    final val DataWatcherNameTag = "nameTag"
    final val DataWatcherGrowingAge = "growingAge"
    final val DataWatcherClimbableWall = "climbableWall"
    final val DataWatcherSprinting = "sprinting"
    final val DataWatcherSneaking = "sneaking"
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
