package seremis.soulcraft.soul.entity;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seremis.soulcraft.entity.SCEntityLiving;
import seremis.soulcraft.soul.Soul;
import seremis.soulcraft.soul.traits.TraitHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class EntitySoulCustom extends SCEntityLiving implements IEntitySoulCustom, IEntityAdditionalSpawnData {
    
    private Soul soul;
    
    public EntitySoulCustom(World world) {
        super(world);
    }
    
    public EntitySoulCustom(World world, Soul soul, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
        setSize(0.8F, 1.7F);
        this.soul = soul;
        syncVariables();
        TraitHandler.entityInit(this);
    }

    @Override
    public void writeSpawnData(ByteBuf data) {
        NBTTagCompound compound = new NBTTagCompound();
        writeToNBT(compound);
        
        byte[] abyte = null;
        try {
            abyte = CompressedStreamTools.compress(compound);
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        data.writeShort((short)abyte.length);
        data.writeBytes(abyte);
    }

    @Override
    public void readSpawnData(ByteBuf data) {
        short short1 = data.readShort();
        byte[] abyte = new byte[short1];
        data.readBytes(abyte);
        NBTTagCompound compound;
        try {
            compound = CompressedStreamTools.decompress(abyte);
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        readFromNBT(compound);
    }
    
    @Override
	public Soul getSoul() {
        return soul;
    }
    
    @Override
    public World getWorld() {
        return worldObj;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox() {
        return boundingBox;
    }
    
    @Override
    public int getEntityId() {
    	return super.getEntityId();
    }
    
    @Override
    public void onDeathUpdate() {
        super.onDeathUpdate();
    }
    
    @Override
    public void setFlag(int id, boolean value) {
        super.setFlag(id, value);
    }
    
    //Syncing variables//
    
    private int syncTicksExisted;
    
    private void syncVariables() {
    	syncCoordinates();
    	syncMovement();
    	syncRotation();
    	syncSize();
    	syncEnvironment();
    	syncChunk();
    	syncDimension();
    	syncCollisions();
    	syncWalking();
    	syncRender();
    	syncRiding();
    	
    	if(syncTicksExisted != ticksExisted) {
    		persistentInteger.put("ticksExisted", ticksExisted);
    		syncTicksExisted = ticksExisted;
    	} else if(ticksExisted != getPersistentInteger("ticksExisted")) {
    		ticksExisted = getPersistentInteger("ticksExisted");
    		syncTicksExisted = ticksExisted;
    	}
    }
    
    private double syncPosX, syncPosY, syncPosZ, syncPrevPosX, syncPrevPosY, syncPrevPosZ, syncLastTickPosX, syncLastTickPosY, syncLastTickPosZ;
    private int syncServerPosX, syncServerPosY, syncServerPosZ;
    
    private void syncCoordinates() {
    	if(syncPosX != posX) {
    		persistentDouble.put("posX", posX);
    		syncPosX = posX;
    	} else if(syncPosX != getPersistentDouble("posX")) {
    		posX = getPersistentDouble("posX");
    		syncPosX = posX;
    	}
    	if(syncPosY != posY) {
    		persistentDouble.put("posY", posY);
    		syncPosY = posY;
    	} else if(syncPosY != getPersistentDouble("posY")) {
    		posY = getPersistentDouble("posY");
    		syncPosY = posY;
    	}
    	if(syncPosZ != posZ) {
    		persistentDouble.put("posZ", posZ);
    		syncPosZ = posZ;
    	} else if(syncPosZ != getPersistentDouble("posZ")) {
    		posZ = getPersistentDouble("posZ");
    		syncPosZ = posZ;
    	}
    	if(syncPrevPosX != prevPosX) {
    		variableDouble.put("prevPosX", prevPosX);
    		syncPrevPosX = prevPosX;
    	} else if(syncPrevPosX != getDouble("prevPosX")) {
    		prevPosX = getDouble("prevPosX");
    		syncPrevPosX = prevPosX;
    	}
    	if(syncPrevPosY != prevPosY) {
    		variableDouble.put("prevPosY", prevPosY);
    		syncPrevPosY = prevPosY;
    	} else if(syncPrevPosY != getDouble("prevPosY")) {
    		prevPosY = getDouble("prevPosY");
    		syncPrevPosY = prevPosY;
    	}
    	if(syncPrevPosZ != prevPosZ) {
    		variableDouble.put("prevPosZ", prevPosZ);
    		syncPrevPosZ = prevPosZ;
    	} else if(syncPrevPosZ != getDouble("prevPosZ")) {
    		prevPosZ = getDouble("prevPosZ");
    		syncPrevPosZ = prevPosZ;
    	}
    	if(syncServerPosX != serverPosX) {
    		variableInteger.put("serverPosX", serverPosX);
    		syncServerPosX = serverPosX;
    	} else if(syncServerPosX != getInteger("serverPosX")) {
    		serverPosX = getInteger("serverPosX");
    		syncServerPosX = serverPosX;
    	}
    	if(syncServerPosY != serverPosY) {
    		variableInteger.put("serverPosY", serverPosY);
    		syncServerPosY = serverPosY;
    	} else if(syncServerPosY != getInteger("serverPosY")) {
    		serverPosY = getInteger("serverPosY");
    		syncServerPosY = serverPosY;
    	}
    	if(syncServerPosZ != serverPosZ) {
    		variableInteger.put("serverPosZ", serverPosZ);
    		syncServerPosZ = serverPosZ;
    	} else if(syncServerPosZ != getInteger("serverPosZ")) {
    		serverPosZ = getInteger("serverPosZ");
    		syncServerPosZ = serverPosZ;
    	}
    	if(syncLastTickPosX != lastTickPosX) {
    		variableDouble.put("lastTickPosX", lastTickPosX);
    		syncLastTickPosX = lastTickPosX;
    	} else if(syncLastTickPosX != getDouble("lastTickPosX")) {
    		lastTickPosX = getDouble("lastTickPosX");
    		syncLastTickPosX = lastTickPosX;
    	}
    	if(syncLastTickPosY != lastTickPosY) {
    		variableDouble.put("lastTickPosY", lastTickPosY);
    		syncLastTickPosY = lastTickPosY;
    	} else if(syncLastTickPosY != getDouble("lastTickPosY")) {
    		lastTickPosY = getDouble("lastTickPosY");
    		syncLastTickPosY = lastTickPosY;
    	}
    	if(syncLastTickPosZ != lastTickPosZ) {
    		variableDouble.put("lastTickPosZ", lastTickPosZ);
    		syncLastTickPosZ = lastTickPosZ;
    	} else if(syncLastTickPosZ != getDouble("lastTickPosZ")) {
    		lastTickPosZ = getDouble("lastTickPosZ");
    		syncLastTickPosZ = lastTickPosZ;
    	}
    }
    
    private double syncMotionX, syncMotionY, syncMotionZ;
    
    private void syncMovement() {
    	if(syncMotionX != motionX) {
    		persistentDouble.put("motionX", motionX);
    		syncMotionX = motionX;
    	} else if(syncMotionX != getPersistentDouble("motionX")) {
    		motionX = getPersistentDouble("motionX");
    		syncMotionX = motionX;
    	}
    	if(syncMotionY != motionY) {
    		persistentDouble.put("motionY", motionY);
    		syncMotionY = motionY;
    	} else if(syncMotionY != getPersistentDouble("motionY")) {
    		motionY = getPersistentDouble("motionY");
    		syncMotionY = motionY;
    	}
    	if(syncMotionZ != motionZ) {
    		persistentDouble.put("motionZ", motionZ);
    		syncMotionZ = motionZ;
    	} else if(syncMotionZ != getPersistentDouble("motionZ")) {
    		motionZ = getPersistentDouble("motionZ");
    		syncMotionZ = motionZ;
    	}
    }
    
    private float syncWidth, syncHeight, syncYSize, syncYOffset, syncStepHeight;
    private EnumEntitySize syncMyEntitySize;
    
    private void syncSize() {
    	if(syncWidth != width) {
    		variableFloat.put("width", width);
    		syncWidth = width;
    	} else if(syncWidth != getFloat("width")) {
    		width = getFloat("width");
    		syncWidth = width;
    	}
    	if(syncHeight != height) {
    		variableFloat.put("height", height);
    		syncHeight = height;
    	} else if(syncHeight != getFloat("height")) {
    		height = getFloat("height");
    		syncHeight = height;
    	}
    	if(syncYSize != ySize) {
    		variableFloat.put("ySize", ySize);
    		syncYSize = ySize;
    	} else if(syncYSize != getFloat("ySize")) {
    		ySize = getFloat("ySize");
    		syncYSize = ySize;
    	}
    	if(syncMyEntitySize != myEntitySize) {
    		variableInteger.put("myEntitySize", myEntitySize.ordinal());
    		syncMyEntitySize = myEntitySize;
    	} else if(syncMyEntitySize != EnumEntitySize.values()[getInteger("myEntitySize")]) {
    		myEntitySize = EnumEntitySize.values()[getInteger("myEntitySize")];
    		syncMyEntitySize = myEntitySize;
    	}
    	if(syncYOffset != yOffset) {
    		variableFloat.put("yOffset", yOffset);
    		syncYOffset = yOffset;
    	} else if(syncYOffset != getFloat("yOffset")) {
    		yOffset = getFloat("yOffset");
    		syncYOffset = yOffset;
    	}
    	if(syncStepHeight != stepHeight) {
    		variableFloat.put("stepHeight", stepHeight);
    		syncStepHeight = stepHeight;
    	} else if(syncStepHeight != getFloat("stepHeight")) {
    		stepHeight = getFloat("stepHeight");
    		syncStepHeight = stepHeight;
    	}
    }
    
    private float syncYaw, syncPitch, syncPrevYaw, syncPrevPitch;
    
    private void syncRotation() {
    	if(syncYaw != rotationYaw) {
    		persistentFloat.put("rotationYaw", rotationYaw);
    		syncYaw = rotationYaw;
    	} else if(syncYaw != getPersistentFloat("rotationYaw")) {
    		rotationYaw = getPersistentFloat("rotationYaw");
    		syncYaw = rotationYaw;
    	}
    	if(syncPitch != rotationPitch) {
    		persistentFloat.put("rotationPitch", rotationPitch);
    		syncPitch = rotationPitch;
    	} else if(syncPitch != getPersistentFloat("rotationPitch")) {
    		rotationPitch = getPersistentFloat("rotationPitch");
    		syncPitch = rotationPitch;
    	}
    	if(syncPrevYaw != prevRotationYaw) {
    		variableFloat.put("prevRotationYaw", prevRotationYaw);
    		syncPrevYaw = prevRotationYaw;
    	} else if(syncPrevYaw != getFloat("prevRotationYaw")) {
    		prevRotationYaw = getFloat("prevRotationYaw");
    		syncPrevYaw = prevRotationYaw;
    	}
    	if(syncPrevPitch != prevRotationPitch) {
    		variableFloat.put("prevRotationPitch", prevRotationPitch);
    		syncPrevPitch = prevRotationPitch;
    	} else if(syncPrevPitch != getFloat("prevRotationPitch")) {
    		prevRotationPitch = getFloat("prevRotationPitch");
    		syncPrevPitch = prevRotationPitch;
    	}
    }
    
    private int syncFire, syncFireResistance;
    private boolean syncInWater, syncOnGround, syncIsInWeb, syncIsDead, syncIsAirBorne;
    
    private void syncEnvironment() {
    	if(syncFire != getFire()) {
    		persistentInteger.put("fire", getFire());
    		syncFire = getFire();
    	} else if(syncFire != getPersistentInteger("fire")) {
    		setFireTicks(getPersistentInteger("fire"));
    		syncFire = getFire();
    	}
    	if(syncFireResistance != fireResistance) {
    		variableInteger.put("fireResistance", fireResistance);
    		syncFireResistance = fireResistance;
    	} else if(syncFireResistance != getInteger("fireResistance")) {
    		fireResistance = getInteger("fireResistance");
    		syncFireResistance = fireResistance;
    	}
    	if(syncInWater != inWater) {
    		variableBoolean.put("inWater", inWater);
    		syncInWater = inWater;
    	} else if(syncInWater != getBoolean("inWater")) {
    		inWater = getBoolean("inWater");
    		syncInWater = inWater;
    	}
    	if(syncOnGround != onGround) {
    		persistentBoolean.put("onGround", onGround);
    		syncOnGround = onGround;
    	} else if(syncOnGround != getPersistentBoolean("onGround")) {
    		onGround = getPersistentBoolean("onGround");
    		syncOnGround = onGround;
    	}
       	if(syncIsInWeb != isInWeb) {
    		variableBoolean.put("isInWeb", isInWeb);
    		syncIsInWeb = isInWeb;
    	} else if(syncIsInWeb != getBoolean("isInWeb")) {
    		isInWeb = getBoolean("isInWeb");
    		syncIsInWeb = isInWeb;
    	}
       	if(syncIsDead != isDead) {
    		persistentBoolean.put("isDead", isDead);
    		syncIsDead = isDead;
    	} else if(syncIsDead != getPersistentBoolean("isDead")) {
    		isDead = getPersistentBoolean("isDead");
    		syncIsDead = isDead;
    	}
       	if(syncIsAirBorne != isAirBorne) {
    		variableBoolean.put("isAirBorne", isAirBorne);
    		syncIsAirBorne = isAirBorne;
    	} else if(syncIsAirBorne != getBoolean("isAirBorne")) {
    		isAirBorne = getBoolean("isAirBorne");
    		syncIsAirBorne = isAirBorne;
    	}
    }
    
    private boolean syncAddedToChunk;
    private int syncChunkCoordX, syncChunkCoordY, syncChunkCoordZ;
    
    private void syncChunk() {
    	if(syncAddedToChunk != addedToChunk) {
    		variableBoolean.put("addedToChunk", addedToChunk);
    		syncAddedToChunk = addedToChunk;
    	} else if(syncAddedToChunk != getBoolean("addedToChunk")) {
    		addedToChunk = getBoolean("addedToChunk");
    		syncAddedToChunk = addedToChunk;
    	}
    	if(syncChunkCoordX != chunkCoordX) {
    		variableInteger.put("chunkCoordX", chunkCoordX);
    		syncChunkCoordX = chunkCoordX;
    	} else if(syncChunkCoordX != getInteger("chunkCoordX")) {
    		chunkCoordX = getInteger("chunkCoordX");
    		syncChunkCoordX = chunkCoordX;
    	}
    	if(syncChunkCoordY != chunkCoordY) {
    		variableInteger.put("chunkCoordY", chunkCoordY);
    		syncChunkCoordY = chunkCoordY;
    	} else if(syncChunkCoordY != getInteger("chunkCoordY")) {
    		chunkCoordY = getInteger("chunkCoordY");
    		syncChunkCoordY = chunkCoordY;
    	}
    	if(syncChunkCoordZ != chunkCoordZ) {
    		variableInteger.put("chunkCoordZ", chunkCoordZ);
    		syncChunkCoordZ = chunkCoordZ;
    	} else if(syncChunkCoordZ != getInteger("chunkCoordZ")) {
    		chunkCoordZ = getInteger("chunkCoordZ");
    		syncChunkCoordZ = chunkCoordZ;
    	}
    }
    
    private int syncDimension, syncPortalCounter, syncTimeUntilPortal, syncTeleportDirection;
    private boolean inPortal;
    
    private void syncDimension() {
    	if(syncDimension != dimension) {
    		persistentInteger.put("dimension", dimension);
    		syncDimension = dimension;
    	} else if(syncDimension != getPersistentInteger("dimension")) {
    		dimension = getPersistentInteger("dimension");
    		syncDimension = dimension;
    	}
    	if(syncPortalCounter != portalCounter) {
    		variableInteger.put("portalCounter", portalCounter);
    		syncPortalCounter = portalCounter;
    	} else if(syncPortalCounter != getInteger("portalCounter")) {
    		portalCounter = getInteger("portalCounter");
    		syncPortalCounter = portalCounter;
    	}
    	if(syncTimeUntilPortal != timeUntilPortal) {
    		persistentInteger.put("timeUntilPortal", timeUntilPortal);
    		syncTimeUntilPortal = timeUntilPortal;
    	} else if(syncTimeUntilPortal != getPersistentInteger("timeUntilPortal")) {
    		timeUntilPortal = getPersistentInteger("timeUntilPortal");
    		syncTimeUntilPortal = timeUntilPortal;
    	}
    	if(syncTeleportDirection != teleportDirection) {
    		variableInteger.put("teleportDirection", teleportDirection);
    		syncTeleportDirection = teleportDirection;
    	} else if(syncTeleportDirection != getInteger("teleportDirection")) {
    		teleportDirection = getInteger("teleportDirection");
    		syncTeleportDirection = teleportDirection;
    	}
    }
    
    private boolean syncIsCollidedHorizontally, syncIsCollidedVertically, syncIsCollided, syncVelocityChanged;
    private float syncEntityCollisionReduction;
    
    private void syncCollisions() {
       	if(syncIsCollidedHorizontally != isCollidedHorizontally) {
    		variableBoolean.put("isCollidedHorizontally", isCollidedHorizontally);
    		syncIsCollidedHorizontally = isCollidedHorizontally;
    	} else if(syncIsCollidedHorizontally != getBoolean("isCollidedHorizontally")) {
    		isCollidedHorizontally = getBoolean("isCollidedHorizontally");
    		syncIsCollidedHorizontally = isCollidedHorizontally;
    	}
       	if(syncIsCollidedVertically != isCollidedVertically) {
    		variableBoolean.put("isCollidedVertically", isCollidedVertically);
    		syncIsCollidedVertically = isCollidedVertically;
    	} else if(syncIsCollidedVertically != getBoolean("isCollidedVertically")) {
    		isCollidedVertically = getBoolean("isCollidedVertically");
    		syncIsCollidedVertically = isCollidedVertically;
    	}
       	if(syncIsCollided != isCollided) {
    		variableBoolean.put("isCollided", isCollided);
    		syncIsCollided = isCollided;
    	} else if(syncIsCollided != getBoolean("isCollided")) {
    		isCollided = getBoolean("isCollided");
    		syncIsCollided = isCollided;
    	}
       	if(syncVelocityChanged != velocityChanged) {
    		variableBoolean.put("velocityChanged", velocityChanged);
    		syncVelocityChanged = velocityChanged;
    	} else if(syncVelocityChanged != getBoolean("velocityChanged")) {
    		velocityChanged = getBoolean("velocityChanged");
    		syncVelocityChanged = velocityChanged;
    	}
    	if(syncEntityCollisionReduction != entityCollisionReduction) {
    		variableFloat.put("entityCollisionReduction", entityCollisionReduction);
    		syncEntityCollisionReduction = entityCollisionReduction;
    	} else if(syncEntityCollisionReduction != getFloat("entityCollisionReduction")) {
    		entityCollisionReduction = getFloat("entityCollisionReduction");
    		syncEntityCollisionReduction = entityCollisionReduction;
    	}
    }
    
    private float syncDistanceWalkedModified, syncPrevDistanceWalkedModified, syncDistanceWalkedOnStepModified, syncFallDistance;
    private int syncNextStepDistance;
    
    private void syncWalking() {
    	if(syncDistanceWalkedModified != distanceWalkedModified) {
    		variableFloat.put("distanceWalkedModified", distanceWalkedModified);
    		syncDistanceWalkedModified = distanceWalkedModified;
    	} else if(syncDistanceWalkedModified != getFloat("distanceWalkedModified")) {
    		distanceWalkedModified = getFloat("distanceWalkedModified");
    		syncDistanceWalkedModified = distanceWalkedModified;
    	}
    	if(syncPrevDistanceWalkedModified != prevDistanceWalkedModified) {
    		variableFloat.put("prevDistanceWalkedModified", prevDistanceWalkedModified);
    		syncPrevDistanceWalkedModified = prevDistanceWalkedModified;
    	} else if(syncPrevDistanceWalkedModified != getFloat("prevDistanceWalkedModified")) {
    		prevDistanceWalkedModified = getFloat("prevDistanceWalkedModified");
    		syncPrevDistanceWalkedModified = prevDistanceWalkedModified;
    	}
    	if(syncDistanceWalkedOnStepModified != distanceWalkedOnStepModified) {
    		variableFloat.put("distanceWalkedOnStepModified", distanceWalkedOnStepModified);
    		syncDistanceWalkedOnStepModified = distanceWalkedOnStepModified;
    	} else if(syncDistanceWalkedOnStepModified != getFloat("distanceWalkedOnStepModified")) {
    		distanceWalkedOnStepModified = getFloat("distanceWalkedOnStepModified");
    		syncDistanceWalkedOnStepModified = distanceWalkedOnStepModified;
    	}
    	if(syncFallDistance != fallDistance) {
    		persistentFloat.put("fallDistance", fallDistance);
    		syncFallDistance = fallDistance;
    	} else if(syncFallDistance != getPersistentFloat("fallDistance")) {
    		distanceWalkedOnStepModified = getPersistentFloat("fallDistance");
    		syncFallDistance = fallDistance;
    	}
    }
    
    private double syncRenderDistanceWeight;
    private boolean syncIgnoreFrustumCheck;
    
    private void syncRender() {
    	if(syncRenderDistanceWeight != renderDistanceWeight) {
    		variableDouble.put("renderDistanceWeight", renderDistanceWeight);
    		syncRenderDistanceWeight = renderDistanceWeight;
    	} else if(syncRenderDistanceWeight != getDouble("renderDistanceWeight")) {
    		renderDistanceWeight = getDouble("renderDistanceWeight");
    		syncRenderDistanceWeight = renderDistanceWeight;
    	}
       	if(syncIgnoreFrustumCheck != ignoreFrustumCheck) {
       		variableBoolean.put("ignoreFrustumCheck", ignoreFrustumCheck);
    		syncIgnoreFrustumCheck = ignoreFrustumCheck;
    	} else if(syncIgnoreFrustumCheck != getBoolean("ignoreFrustumCheck")) {
    		ignoreFrustumCheck = getBoolean("ignoreFrustumCheck");
    		syncIgnoreFrustumCheck = ignoreFrustumCheck;
    	}
    }
    
    private Entity syncRiddenByEntity, syncRidingEntity;
    private double syncEntityRiderPitchDelta, syncEntityRiderYawDelta;
    
    private void syncRiding() {
    	if(syncRiddenByEntity != riddenByEntity) {
    		variableInteger.put("riddenByEntityID", riddenByEntity.getEntityId());
    		syncRiddenByEntity = riddenByEntity;
    	} else if(syncRiddenByEntity != worldObj.getEntityByID(getInteger("riddenByEntityID"))) {
    		riddenByEntity = worldObj.getEntityByID(getInteger("riddenByEntityID"));
    		syncRiddenByEntity = riddenByEntity;
    	}
    	if(syncRidingEntity != ridingEntity) {
    		persistentInteger.put("ridingEntityID", ridingEntity.getEntityId());
    		syncRidingEntity = ridingEntity;
    	} else if(syncRidingEntity != worldObj.getEntityByID(getPersistentInteger("ridingEntityID"))) {
    		ridingEntity = worldObj.getEntityByID(getPersistentInteger("ridingEntityID"));
    		syncRidingEntity = ridingEntity;
    	}
    	if(syncEntityRiderPitchDelta != getEntityRiderPitchDelta()) {
    		variableDouble.put("entityRiderPitchDelta", getEntityRiderPitchDelta());
    		syncEntityRiderPitchDelta = getEntityRiderPitchDelta();
    	} else if(syncEntityRiderPitchDelta != getDouble("entityRiderPitchDelta")) {
    		setEntityRiderPitchDelta(getDouble("entityRiderPitchDelta"));
    		syncEntityRiderPitchDelta = getEntityRiderPitchDelta();
    	}
    	if(syncEntityRiderYawDelta != getEntityRiderYawDelta()) {
    		variableDouble.put("entityRiderYawDelta", getEntityRiderYawDelta());
    		syncEntityRiderYawDelta = getEntityRiderYawDelta();
    	} else if(syncEntityRiderYawDelta != getDouble("entityRiderYawDelta")) {
    		setEntityRiderYawDelta(getDouble("entityRiderYawDelta"));
    		syncEntityRiderYawDelta = getEntityRiderYawDelta();
    	}
    }
    
    private boolean syncCaptureDrops;
    private List<EntityItem> syncCapturedDrops;
    
    private void syncInventory() {
       	if(syncCaptureDrops != captureDrops) {
       		variableBoolean.put("captureDrops", captureDrops);
    		syncCaptureDrops = captureDrops;
    	} else if(syncCaptureDrops != getBoolean("captureDrops")) {
    		captureDrops = getBoolean("captureDrops");
    		syncCaptureDrops = captureDrops;
    	}
       	for(int i = 0; i < capturedDrops.size(); i++) {
       		if(syncCapturedDrops.get(i) != capturedDrops.get(i)) {
       			persistentItemStack.put("capturedDrops." + i, capturedDrops.get(i).getEntityItem());
       			syncCapturedDrops.add(i, capturedDrops.get(i));
       		} else if(syncCapturedDrops.get(i).getEntityItem() != getPersistentItemStack("capturedDrops." + i)) {
       			capturedDrops.add(i, new EntityItem(worldObj, posX, posY, posZ, getPersistentItemStack("capturedDrops." + i)));
       			syncCapturedDrops.add(i, capturedDrops.get(i));
       		}
       	}
    }
    
    
//    @Override
//    public void setPosition(double x, double y, double z) {
//        setPersistentVariable("posX", x);
//        setPersistentVariable("posY", y);
//        setPersistentVariable("posZ", z);
//
//        float width = getPersistentFloat("width");
//        float height = getPersistentFloat("height");
//        
//        float yOffset = getPersistentFloat("yOffset");
//        float ySize = getPersistentFloat("ySize");
//        
//        float f = width / 2.0F;
//        float f1 = height;
//        boundingBox.setBounds(x - f, y - yOffset + ySize, z - f, x + f, y - yOffset + ySize + f1, z + f);
//    }
//    
//    @Override
//    protected void setRotation(float yaw, float pitch) {
//        setPersistentVariable("rotationYaw", yaw % 360.0F);
//        setPersistentVariable("rotationPitch", pitch % 360.0F);
//    }
//    
//    @Override
//    public void setFire(int fireSec) {
//        int fireTicks = fireSec * 20;
//        fireTicks = EnchantmentProtection.getFireTimeForEntity(this, fireTicks);
//         
//        int fire = getPersistentInteger("fireTicks");
//        
//        if (fire < fireTicks) {
//             setPersistentVariable("fireTicks", fireTicks);
//        }
//    }

    //Modularity stuff//    
//    @Override
//    public double getPosX() {
//        return posX;
//    }
//    
//    @Override
//    public double getPosY() {
//        return posY;
//    }
//    
//    @Override
//    public double getPosZ() {
//        return posZ;
//    }
//    
//
//    @Override
//    public double getMotionX() {
//        return motionX;
//    }
//
//    @Override
//    public double getMotionY() {
//        return motionY;
//    }
//
//    @Override
//    public double getMotionZ() {
//        return motionZ;
//    }
//    
//    @Override
//    public void setMotion(double x, double y, double z) {
//        motionX = x;
//        motionY = y;
//        motionZ = z;
//    }
//    
//    @Override
//    public float getRotationYaw() {
//        return rotationYaw;
//    }
//    
//    @Override
//    public void setRotationYaw(float yaw) {
//        rotationYaw = yaw;
//    }
//    
//    @Override
//    public float getRotationPitch() {
//        return rotationPitch;
//    }
//    
//    @Override
//    public void setRotationPitch(float pitch) {
//        rotationPitch = pitch;
//    }
//    

//    
//    @Override
//    public float getBrightness() {
//        return getBrightness(1.0F);
//    }
//    
//    @Override
//    public ItemStack getCurrentItemOrArmor(int slot) {
//        return super.getCurrentItemOrArmor(slot);
//    }
//    
//    @Override
//    public void setArmor(int slot, ItemStack stack) {
//        this.setCurrentItemOrArmor(slot+1, stack);
//    }
//    
//    @Override
//    public ItemStack getHeldItem() {
//        return getCurrentItemOrArmor(0);
//    }
//    
//    @Override
//    public void setHeldItem(ItemStack stack) {
//        setCurrentItemOrArmor(0, stack);
//    }
//    
//    @Override
//    public boolean getCanPickUpItems() {
//        return canPickUpLoot();
//    }
//    
//    @Override
//    public void setCanPickUpItems(boolean canPickUp) {
//        setCanPickUpLoot(canPickUp);
//    }
//    
//    @Override
//    public void dropItems(ItemStack stack) {
//        this.entityDropItem(stack, getEyeHeight());
//    }
//
//    @Override
//    public boolean getIsChild() {
//        return isChild();
//    }
//    
//    @Override
//    public String getLivingSound() {
//        return "sound.living";
//    }
//    
//    @Override
//    public String getHurtSound() {
//        return "sound.hurt";
//    }
//    
//    @Override
//    public String getDeathSound() {
//        return "sound.death";
//    }
//    
//    @Override
//    public EntityAITasks getTasks() {
//        return tasks;
//    }
//
//    @Override
//    public EntityAITasks getTargetTasks() {
//        return targetTasks;
//    }
//    
 
    public int getFire() {
        int fire = 0;
        try {
            Field onFire = ReflectionHelper.findField(Entity.class, new String[] {"fire", "field_70151_c"});
            onFire.setAccessible(true);
            fire = onFire.getInt(this);
            onFire.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return fire;
    }
 
    public void setFireTicks(int fire) {
        try {
            Field onFire = ReflectionHelper.findField(Entity.class, new String[] {"fire", "field_70151_c"});
            onFire.setAccessible(true);
            onFire.setInt(this, fire);
            onFire.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }
    
    private double getEntityRiderPitchDelta() {
        double var = 0;
        try {
            Field field = ReflectionHelper.findField(Entity.class, new String[] {"entityRiderPitchDelta", "field_70149_e"});
            field.setAccessible(true);
            var = field.getDouble(this);
            field.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return var;
    }
    
    private double getEntityRiderYawDelta() {
        double var = 0;
        try {
            Field field = ReflectionHelper.findField(Entity.class, new String[] {"entityRiderYawDelta", "field_70147_f"});
            field.setAccessible(true);
            var = field.getDouble(this);
            field.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return var;
    }
    
    private void setEntityRiderPitchDelta(double value) {
        try {
            Field field = ReflectionHelper.findField(Entity.class, new String[] {"entityRiderPitchDelta", "field_70149_e"});
            field.setAccessible(true);
            field.setDouble(this, value);
            field.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }
    
    private void setEntityRiderYawDelta(double value) {
        try {
            Field field = ReflectionHelper.findField(Entity.class, new String[] {"entityRiderYawDelta", "field_70147_f"});
            field.setAccessible(true);
            field.setDouble(this, value);
            field.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }
//    
//    @Override
//    public void setAge(int age) {
//        entityAge = age;
//    }
//    
//    @Override
//    public float getLimbSwingAmount() {
//        return limbSwingAmount;
//    }
//    
//    @Override
//    public void setLimbSwingAmount(float amount) {
//        limbSwingAmount = amount;
//    }
//    
//    @Override
//    public float getPrevHealth() {
//        return prevHealth;
//    }
//
//    @Override
//    public void setPrevHealth(float lastHealth) {
//        prevHealth = lastHealth;
//    }
//
//    @Override
//    public int getHurtTime() {
//        return hurtTime;
//    }
//
//    @Override
//    public void setHurtTime(int time) {
//        hurtTime = time;
//    }
//
//    @Override
//    public int getHurtResistantTime() {
//        return hurtResistantTime;
//    }
//
//    @Override
//    public void setHurtResistantTime(int time) {
//        hurtResistantTime = time;
//    }
//
//    @Override
//    public float getLastDamage() {
//        return lastDamage;
//    }
//
//    @Override
//    public void setLastDamage(float damage) {
//        lastDamage = damage;
//    }
//    
//    @Override
//    public EntityPlayer getRevengePlayer() {
//        return attackingPlayer;
//    }
//
//    @Override
//    public void setRevengePlayer(EntityPlayer player) {
//        attackingPlayer = player;
//    }
//
//    @Override
//    public int getRecentlyHit() {
//        return recentlyHit;
//    }
//    
//    @Override
//    public void setRecentlyHit(int time) {
//        recentlyHit = time;
//    }
//
//    @Override
//    public void setAttackedAtYaw(float yaw) {
//        attackedAtYaw = yaw;
//    }
//
//    @Override
//    public float getAttackedAtYaw() {
//        return attackedAtYaw;
//    }
//
//    @Override
//    public void setBeenAttacked() {
//        super.setBeenAttacked();
//    }
//    
//    @Override
//    public CombatTracker getCombatTracker() {
//        return super.func_110142_aN();
//    }
//    
//    @Override
//    public float applyArmorCalculations(DamageSource source, float damage) {
//        return super.applyArmorCalculations(source, damage);
//    }
//    
//    @Override
//    public float applyPotionDamageCalculations(DamageSource source, float damage) {
//        return super.applyPotionDamageCalculations(source, damage);
//    }
//    

//    
//    @Override
//    public float getSoundPitch() {
//        return super.getSoundPitch();
//    }
//    
//    @Override
//    public void setAttackTime(int time) {
//        attackTime = time;
//    }
//    
//    @Override
//    public int getAttackTime() {
//        return attackTime;
//    }
//    
//    @Override
//    private EnumCreatureAttribute creatureAttribute;
//    
//    @Override
//    public EnumCreatureAttribute getCreatureAttribute() {
//        return creatureAttribute;
//    }
//    
//    @Override
//    public void setCreatureAttribute(EnumCreatureAttribute attribute) {
//        creatureAttribute = attribute;
//    }
//    
//    @Override
//    public void collideWithNearbyEntities() {
//        super.collideWithNearbyEntities();
//    }
//

//    
//    @Override
//    public void setIsJumping(boolean jump) {
//        this.isJumping = true;
//    }
//
//    @Override
//    public boolean getIsJumping() {
//        return isJumping;
//    }
//    
//    @Override
//    public void setMoveStrafing(float strafe) {
//        this.moveStrafing = strafe;        
//    }
//
//    @Override
//    public float getMoveStrafing() {
//        return moveStrafing;
//    }
//
//    @Override
//    public float getMoveForward() {
//        return moveForward;
//    }
//    
//
//    @Override
//    public double getPrevPosX() {
//        return prevPosX;
//    }
//
//    @Override
//    public double getPrevPosY() {
//        return prevPosY;
//    }
//
//    @Override
//    public double getPrevPosZ() {
//        return prevPosZ;
//    }
//
//    @Override
//    public void setPrevPosX(double x) {
//        prevPosX = x;
//    }
//
//    @Override
//    public void setPrevPosY(double y) {
//        prevPosY = y;
//    }
//
//    @Override
//    public void setPrevPosZ(double z) {
//        prevPosZ = z;
//    }
//
//    @Override
//    public float getPrevRotationYaw() {
//        return prevRotationYaw;
//    }
//
//    @Override
//    public float getPrevRotationPitch() {
//        return prevRotationPitch;
//    }
//
//    @Override
//    public void setPrevRotationYaw(float yaw) {
//        prevRotationYaw = yaw;
//    }
//
//    @Override
//    public void setPrevRotationPitch(float pitch) {
//        prevRotationPitch = pitch;
//    }
//
//    @Override
//    public boolean getIsInPortal() {
//        return inPortal;
//    }
//
//    @Override
//    public void setIsInPortal(boolean inPortal) {
//        this.inPortal = inPortal;
//    }
//
//    @Override
//    public int getPortalTimer() {
//        return portalCounter;
//    }
//
//    @Override
//    public void setPortalTimer(int time) {
//        portalCounter = time;
//    }
//
//    @Override
//    public void setPortalCooldown(int cooldown) {
//        timeUntilPortal = cooldown;
//    }
//
//    @Override
//    public int getPortalCooldown() {
//        return timeUntilPortal;
//    }
//
//    @Override
//    public void setRidingEntity(Entity ridingEntity) {
//        this.ridingEntity = ridingEntity;
//    }
//
//    @Override
//    public Entity getRidingEntity() {
//        return ridingEntity;
//    }
//    
//
//    @Override
//    public void setPrevDistanceWalkedModified(float prevDistanceWalkedModified) {
//        this.prevDistanceWalkedModified = prevDistanceWalkedModified;
//    }
//
//    @Override
//    public void setDistanceWalkedModified(float distanceWalkedModified) {
//        this.distanceWalkedModified = distanceWalkedModified;        
//    }
//
//    @Override
//    public float getPrevDistanceWalkedModified() {
//        return prevDistanceWalkedModified;
//    }
//
//    @Override
//    public float getDistanceWalkedModified() {
//        return distanceWalkedModified;
//    }
//    
//    @Override
//    public float getWidth() {
//        return width;
//    }
//
//    @Override
//    public void setWidth(float width) {
//        this.width = width;
//    }
//
//    @Override
//    public float getHeight() {
//        return height;
//    }
//
//    @Override
//    public void setHeight(float height) {
//        this.height = height;
//    }
//
//    @Override
//    public float getCameraPitch() {
//        return this.cameraPitch;
//    }
//
//    @Override
//    public void setCameraPitch(float cameraPitch) {
//        this.cameraPitch = cameraPitch;
//    }
//
//    @Override
//    public float getPrevCameraPitch() {
//        return this.prevCameraPitch;
//    }
//
//    @Override
//    public void setPrevCameraPitch(float prevCameraPitch) {
//        this.prevCameraPitch = prevCameraPitch;
//    }
//
//    @Override
//    public void setIsSprinting(boolean sprint) {
//        this.setSprinting(sprint);
//    }
//
//    @Override
//    public void setFallDistance(float fallDistance) {
//        this.fallDistance = fallDistance;
//    }
//
//    @Override
//    public float getFallDistance() {
//        return fallDistance;
//    }
//
//    @Override
//    public void setYOffset(float yOffset) {
//        this.yOffset = yOffset;
//    }
//
//    @Override
//    public void setRenderYawOffset(float yawOffset) {
//        this.renderYawOffset = yawOffset;
//    }
//
//    @Override
//    public float getRenderYawOffset() {
//        return renderYawOffset;
//    }
//
//    @Override
//    public void setPrevRenderYawOffset(float prevYawOffset) {
//        this.prevRenderYawOffset = prevYawOffset;
//    }
//
//    @Override
//    public float getPrevRenderYawOffset() {
//        return prevRenderYawOffset;
//    }
//
//    @Override
//    public void setPrevRotationYawHead(float yaw) {
//        this.prevRotationYawHead = yaw;
//    }
//
//    @Override
//    public float getPrevRotationYawHead() {
//        return prevRotationYawHead;
//    }
//    
//    @Override
//    public void updatePotionEffects() {
//        super.updatePotionEffects();
//    }
    
    //Entity stuff//    
    @Override
    public boolean interact(EntityPlayer player) {
        TraitHandler.entityRightClicked(this, player);
        return true;
    }
    
    @Override
    public void onLivingUpdate(){}
    
    @Override
    public void onEntityUpdate(){}
    
    @Override
    public void onUpdate() {
    	syncVariables();
        if(ForgeHooks.onLivingUpdate(this))return;
        
        TraitHandler.entityUpdate(this);
        
        //Mojang, why u make things so hard?
        
        try {
            Field up = ReflectionHelper.findField(Entity.class, new String[] {"firstUpdate", "field_70151_c"});
            up.setAccessible(true);
            up.setBoolean(this, false);
            up.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onDeath(DamageSource source) {
        if(ForgeHooks.onLivingDeath(this, source)) return;
        
        if(source.getEntity() != null) {
            source.getEntity().onKillEntity(this);
        }
        
        TraitHandler.entityDeath(this, source);
        worldObj.setEntityState(this, (byte)3);
    }
    
    @Override
    public void onKillEntity(EntityLivingBase entity) {
        TraitHandler.onKillEntity(this, entity);
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if(ForgeHooks.onLivingAttack(this, source, damage)) return false;
        return TraitHandler.attackEntityFrom(this, source, damage);
    }
    
    @Override
    public void damageEntity(DamageSource source, float damage) {
        TraitHandler.damageEntity(this, source, damage);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        return TraitHandler.spawnEntityFromEgg(this, data);
    }
    
    @Override
    public void playSound(String name, float volume, float pitch) {
        TraitHandler.playSoundAtEntity(this, name, volume, pitch);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        soul = new Soul(compound);
        
        if(compound.hasKey("traitVariables")) {
            NBTTagList tagList = compound.getTagList("traitVariables", Constants.NBT.TAG_COMPOUND);
            
            NBTTagCompound compoundBoolean = null;
            NBTTagCompound compoundByte = null;
            NBTTagCompound compoundInteger = null;
            NBTTagCompound compoundFloat = null;
            NBTTagCompound compoundDouble = null;
            NBTTagCompound compoundString = null;
            NBTTagCompound compoundItemStack = null;
            
            for(int i = 0; i < tagList.tagCount(); i++) {
                if(tagList.getCompoundTagAt(i).getString("type") == "boolean") {
                    compoundBoolean = tagList.getCompoundTagAt(i);
                } else if(tagList.getCompoundTagAt(i).getString("type") == "byte") {
                    compoundByte = tagList.getCompoundTagAt(i);
                } else if(tagList.getCompoundTagAt(i).getString("type") == "integer") {
                    compoundInteger = tagList.getCompoundTagAt(i);
                } else if(tagList.getCompoundTagAt(i).getString("type") == "float") {
                    compoundFloat = tagList.getCompoundTagAt(i);
                } else if(tagList.getCompoundTagAt(i).getString("type") == "double") {
                    compoundDouble = tagList.getCompoundTagAt(i);
                } else if(tagList.getCompoundTagAt(i).getString("type") == "string") {
                    compoundString = tagList.getCompoundTagAt(i);
                } else if(tagList.getCompoundTagAt(i).getString("type") == "itemStack") {
                    compoundItemStack = tagList.getCompoundTagAt(i);
                }
            }
            
            if(compoundBoolean != null) {
                int size = compoundBoolean.getInteger("size");
                for(int i = 0; i < size; i++) {
                    String name = compoundBoolean.getString("boolean" + i + "Name");
                    boolean value = compoundBoolean.getBoolean("boolean" + i + "Value");
                    persistentBoolean.put(name, value);
                }
            }
            
            if(compoundByte != null) {
                int size = compoundByte.getInteger("size");
                for(int i = 0; i < size; i++) {
                    String name = compoundByte.getString("byte" + i + "Name");
                    byte value = compoundByte.getByte("byte" + i + "Value");
                    persistentByte.put(name, value);
                }
            }
            
            if(compoundInteger != null) {
                int size = compoundInteger.getInteger("size");
                for(int i = 0; i < size; i++) {
                    String name = compoundInteger.getString("integer" + i + "Name");
                    int value = compoundInteger.getInteger("integer" + i + "Value");
                    persistentInteger.put(name, value);
                }
            }
            
            if(compoundFloat != null) {
                int size = compoundFloat.getInteger("size");
                for(int i = 0; i < size; i++) {
                    String name = compoundFloat.getString("float" + i + "Name");
                    float value = compoundFloat.getFloat("float" + i + "Value");
                    persistentFloat.put(name, value);
                }
            }
            
            if(compoundDouble != null) {
                int size = compoundDouble.getInteger("size");
                for(int i = 0; i < size; i++) {
                    String name = compoundDouble.getString("double" + i + "Name");
                    double value = compoundDouble.getDouble("double" + i + "Value");
                    persistentDouble.put(name, value);
                }
            }
            
            if(compoundString != null) {
                int size = compoundString.getInteger("size");
                for(int i = 0; i < size; i++) {
                    String name = compoundString.getString("string" + i + "Name");
                    String value = compoundString.getString("string" + i + "Value");
                    persistentString.put(name, value);
                }
            }
            
            if(compoundItemStack != null) {
                int size = compoundItemStack.getInteger("size");
                for(int i = 0; i < size; i++) {
                    String name = compoundItemStack.getString("itemStack" + i + "Name");
                    NBTTagCompound stackCompound = compoundItemStack.getCompoundTag("itemStack" + i + "Value");
                    ItemStack stack = ItemStack.loadItemStackFromNBT(stackCompound);
                    persistentItemStack.put(name, stack);
                }
            }
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        soul.writeToNBT(compound);
        
        //if(persistentBoolean != null) {
	        NBTTagList tagList = new NBTTagList();
	        
	        List<String> stringList = new ArrayList<String>();
	        
	        if(!persistentBoolean.isEmpty()) {
		        stringList.addAll(persistentBoolean.keySet());
		        
		        NBTTagCompound booleanCompound = new NBTTagCompound();
		        for(int i = 0; i < persistentBoolean.size(); i++) {
		            booleanCompound.setString("boolean" + i + "Name", stringList.get(i));
		            booleanCompound.setBoolean("boolean" + i + "Value", persistentBoolean.get(stringList.get(i)));
		        }
		        booleanCompound.setString("type", "boolean");
		        booleanCompound.setInteger("size", persistentBoolean.size());
		        tagList.appendTag(booleanCompound);
		        
		        stringList.clear();
	        }
	        
	        if(!persistentByte.isEmpty()) {
		        stringList.addAll(persistentByte.keySet());
		        
		        NBTTagCompound byteCompound = new NBTTagCompound();
		        for(int i = 0; i < persistentByte.size(); i++) {
		            byteCompound.setString("byte" + i + "Name", stringList.get(i));
		            byteCompound.setByte("byte" + i + "Value", persistentByte.get(stringList.get(i)));
		        }
		        byteCompound.setString("type", "byte");
		        byteCompound.setInteger("size", persistentByte.size());
		        tagList.appendTag(byteCompound);
		        
		        stringList.clear();
	        }
	        
	        if(!persistentInteger.isEmpty()) {
		        stringList.addAll(persistentInteger.keySet());
		        
		        NBTTagCompound integerCompound = new NBTTagCompound();
		        for(int i = 0; i < persistentInteger.size(); i++) {
		            integerCompound.setString("integer" + i + "Name", stringList.get(i));
		            integerCompound.setInteger("integer" + i + "Value", persistentInteger.get(stringList.get(i)));
		        }
		        integerCompound.setString("type", "integer");
		        integerCompound.setInteger("size", persistentInteger.size());
		        tagList.appendTag(integerCompound);
		        
		        stringList.clear();
	        }
	        
	        if(!persistentFloat.isEmpty()) {
		        stringList.addAll(persistentFloat.keySet());
		        
		        NBTTagCompound floatCompound = new NBTTagCompound();
		        for(int i = 0; i < persistentFloat.size(); i++) {
		            floatCompound.setString("float" + i + "Name", stringList.get(i));
		            floatCompound.setFloat("float" + i + "Value", persistentFloat.get(stringList.get(i)));
		        }
		        floatCompound.setString("type", "float");
		        floatCompound.setInteger("size", persistentFloat.size());
		        
		        stringList.clear();
	        }
	        
	        if(!persistentDouble.isEmpty()) {
		        stringList.addAll(persistentDouble.keySet());
		        
		        NBTTagCompound doubleCompound = new NBTTagCompound();
		        for(int i = 0; i < persistentDouble.size(); i++) {
		            doubleCompound.setString("double" + i + "Name", stringList.get(i));
		            doubleCompound.setDouble("double" + i + "Value", persistentDouble.get(stringList.get(i)));
		        }
		        doubleCompound.setString("type", "double");
		        doubleCompound.setInteger("size", persistentDouble.size());
		        
		        stringList.clear();
	        }
	        
	        if(!persistentString.isEmpty()) {
		        stringList.addAll(persistentString.keySet());
		        
		        NBTTagCompound stringCompound = new NBTTagCompound();
		        for(int i = 0; i < persistentString.size(); i++) {
		            stringCompound.setString("string" + i + "Name", stringList.get(i));
		            stringCompound.setString("string" + i + "Value", persistentString.get(stringList.get(i)));
		        }
		        stringCompound.setString("type", "string");
		        stringCompound.setInteger("size", persistentString.size());
		        
		        stringList.clear();
	        }
	        
	        if(!persistentItemStack.isEmpty()) {
		        stringList.addAll(persistentItemStack.keySet());
		        
		        NBTTagCompound itemStackCompound = new NBTTagCompound();
		        for(int i = 0; i < persistentItemStack.size(); i++) {
		            itemStackCompound.setString("itemStack" + i + "Name", stringList.get(i));
		            NBTTagCompound compoundStack = new NBTTagCompound();
		            itemStackCompound.setTag("itemStack" + i + "Value", persistentItemStack.get(i).writeToNBT(compoundStack));
		        }
		        itemStackCompound.setString("type", "itemStack");
		        itemStackCompound.setInteger("size", persistentString.size());
		        
		        stringList.clear();
	        }
	        
	        compound.setTag("traitVariables", tagList);
        //}
    }
    
    private HashMap<String, Boolean> persistentBoolean = new HashMap<String, Boolean>(), variableBoolean = new HashMap<String, Boolean>(); 
    private HashMap<String, Byte> persistentByte = new HashMap<String, Byte>(), variableByte = new HashMap<String, Byte>(); 
    private HashMap<String, Integer> persistentInteger = new HashMap<String, Integer>(), variableInteger = new HashMap<String, Integer>(); 
    private HashMap<String, Float> persistentFloat = new HashMap<String, Float>(), variableFloat = new HashMap<String, Float>(); 
    private HashMap<String, Double> persistentDouble = new HashMap<String, Double>(), variableDouble = new HashMap<String, Double>(); 
    private HashMap<String, String> persistentString = new HashMap<String, String>(), variableString = new HashMap<String, String>(); 
    private HashMap<String, ItemStack> persistentItemStack = new HashMap<String, ItemStack>(), variableItemStack = new HashMap<String, ItemStack>();
    
    @Override
    public void setPersistentVariable(String name, boolean variable) {
    	if(!persistentBoolean.isEmpty()) {
	        for(String string : persistentBoolean.keySet()) {
	            if(string.equals(name)) {
	                persistentBoolean.remove(name);
	            }
	        }
    	}
        persistentBoolean.put(name, variable);
    }

    @Override
    public void setPersistentVariable(String name, byte variable) {
    	if(!persistentByte.isEmpty()) {
	        for(String string : persistentByte.keySet()) {
	            if(string.equals(name)) {
	                persistentByte.remove(name);
	            }
	        }
    	}
        persistentByte.put(name, variable);
    }

    @Override
    public void setPersistentVariable(String name, int variable) {
    	if(!persistentInteger.isEmpty()) {
	        for(String string : persistentInteger.keySet()) {
	            if(string.equals(name)) {
	                persistentInteger.remove(name);
	            }
	        }
    	}
        persistentInteger.put(name, variable);
    }

    @Override
    public void setPersistentVariable(String name, float variable) {
    	if(!persistentFloat.isEmpty()) {
	        for(String string : persistentFloat.keySet()) {
	            if(string.equals(name)) {
	                persistentFloat.remove(name);
	            }
	        }
    	}
        persistentFloat.put(name, variable);        
    }

    @Override
    public void setPersistentVariable(String name, double variable) {
    	System.out.println(persistentDouble);
    	if(!persistentDouble.isEmpty()) {
	        for(String string : persistentDouble.keySet()) {
	            if(string.equals(name)) {
	                persistentDouble.remove(name);
	            }
	        }
    	}
        persistentDouble.put(name, variable);
    }

    @Override
    public void setPersistentVariable(String name, String variable) {
    	if(!persistentString.isEmpty()) {
	        for(String string : persistentString.keySet()) {
	            if(string.equals(name)) {
	                persistentString.remove(name);
	            }
	        }
    	}
        persistentString.put(name, variable);
    }
    
    @Override
    public void setPersistentVariable(String name, ItemStack variable) {
    	if(!persistentItemStack.isEmpty()) {
	        for(String string : persistentItemStack.keySet()) {
	            if(string.equals(name)) {
	                persistentItemStack.remove(name);
	            }
	        }
    	}
        persistentItemStack.put(name, variable);
    }
    
    @Override
    public boolean getPersistentBoolean(String name) {
        return !persistentBoolean.containsKey(name) ? false : persistentBoolean.get(name);
    }

    @Override
    public byte getPersistentByte(String name) {
        return !persistentByte.containsKey(name) ? 0 : persistentByte.get(name);
    }

    @Override
    public int getPersistentInteger(String name) {
        return !persistentInteger.containsKey(name) ? 0 :  persistentInteger.get(name);
    }

    @Override
    public float getPersistentFloat(String name) {
        return !persistentFloat.containsKey(name) ? 0 : persistentFloat.get(name);
    }

    @Override
    public double getPersistentDouble(String name) {
        return !persistentDouble.containsKey(name) ? 0 : persistentDouble.get(name);
    }

    @Override
    public String getPersistentString(String name) {
        return !persistentString.containsKey(name) ? null : persistentString.get(name);
    }
    
    @Override
    public ItemStack getPersistentItemStack(String name) {
        return !persistentItemStack.containsKey(name) ? null : persistentItemStack.get(name);
    }

    @Override
    public void setVariable(String name, boolean variable) {
    	if(!variableBoolean.isEmpty()) {
	        for(String string : variableBoolean.keySet()) {
	            if(string.equals(name)) {
	                variableBoolean.remove(name);
	            }
	        }	
    	}
        variableBoolean.put(name, variable);
    }

    @Override
    public void setVariable(String name, byte variable) {
    	if(!variableByte.isEmpty()) {
	        for(String string : variableByte.keySet()) {
	            if(string.equals(name)) {
	                persistentByte.remove(name);
	            }
	        }
    	}
        persistentByte.put(name, variable);
    }

    @Override
    public void setVariable(String name, int variable) {
    	if(!variableInteger.isEmpty()) {
	        for(String string : variableInteger.keySet()) {
	            if(string.equals(name)) {
	                variableInteger.remove(name);
	            }
	        }
    	}
        variableInteger.put(name, variable);
    }

    @Override
    public void setVariable(String name, float variable) {
    	if(variableFloat.isEmpty()) {
	        for(String string : variableFloat.keySet()) {
	            if(string.equals(name)) {
	                variableFloat.remove(name);
	            }
	        }
    	}
        variableFloat.put(name, variable);        
    }

    @Override
    public void setVariable(String name, double variable) {
    	if(!variableDouble.isEmpty()) {
	        for(String string : variableDouble.keySet()) {
	            if(string.equals(name)) {
	                variableDouble.remove(name);
	            }
	        }
    	}
        variableDouble.put(name, variable);
    }

    @Override
    public void setVariable(String name, String variable) {
    	if(!variableString.isEmpty()) {
	        for(String string : variableString.keySet()) {
	            if(string.equals(name)) {
	                variableString.remove(name);
	            }
	        }
    	}
        variableString.put(name, variable);
    }
    
    @Override
    public void setVariable(String name, ItemStack variable) {
    	if(variableItemStack.isEmpty()) {
	        for(String string : variableItemStack.keySet()) {
	            if(string.equals(name)) {
	                variableItemStack.remove(name);
	            }
	        }
    	}
        variableItemStack.put(name, variable);
    }

    @Override
    public boolean getBoolean(String name) {
        return !variableBoolean.containsKey(name) ? false : variableBoolean.get(name);
    }

    @Override
    public byte getByte(String name) {
        return !variableByte.containsKey(name) ? 0 : variableByte.get(name);
    }

    @Override
    public int getInteger(String name) {
        return !variableInteger.containsKey(name) ? 0 : variableInteger.get(name);
    }

    @Override
    public float getFloat(String name) {
        return !variableFloat.containsKey(name) ? 0 : variableFloat.get(name);
    }

    @Override
    public double getDouble(String name) {
        return !variableDouble.containsKey(name) ? 0 : variableDouble.get(name);
    }

    @Override
    public String getString(String name) {
        return !variableString.containsKey(name) ? null : variableString.get(name);
    }

    @Override
    public ItemStack getItemStack(String name) {
        return !variableItemStack.containsKey(name) ? null : variableItemStack.get(name);
    }
}