package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.IGeneRegistry;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.UtilSoulEntity;

import java.util.ArrayList;
import java.util.Collections;

public class TraitAI extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        boolean useNewAI = SoulHelper.geneRegistry.getValueBoolean(entity, Genes.GENE_USE_NEW_AI);

        if(useNewAI) {
            EntityLiving living = (EntityLiving) entity;

            IGeneRegistry gReg = SoulHelper.geneRegistry;
            EntityAITasks tasks = living.tasks;
            EntityAITasks targetTasks = living.targetTasks;

            float aiModifierAttack = gReg.getValueFloat(entity, Genes.GENE_AI_MODIFIER_ATTACK);
            float aiModifierRun = gReg.getValueFloat(entity, Genes.GENE_AI_MODIFIER_RUN);
            float aiModifierSurvive = gReg.getValueFloat(entity, Genes.GENE_AI_MODIFIER_SURVIVE);
            float aiModifierTrade = gReg.getValueFloat(entity, Genes.GENE_AI_MODIFIER_TRADE);
            float aiModifierHelpOwner = gReg.getValueFloat(entity, Genes.GENE_AI_MODIFIER_HELP_OWNER);
            float aiModifierMate = gReg.getValueFloat(entity, Genes.GENE_AI_MODIFIER_MATE);
            float aiModifierDoUselessThings = gReg.getValueFloat(entity, Genes.GENE_AI_MODIFIER_DO_USELESS_THINGS);
            float aiModifierWander = gReg.getValueFloat(entity, Genes.GENE_AI_MODIFIER_WANDER);

            ArrayList<Float> array = new ArrayList<Float>();
            array.add(aiModifierAttack);
            array.add(aiModifierRun);
            array.add(aiModifierSurvive);
            array.add(aiModifierTrade);
            array.add(aiModifierHelpOwner);
            array.add(aiModifierMate);
            array.add(aiModifierDoUselessThings);

            Collections.sort(array);

            int attackIndex = array.indexOf(aiModifierAttack);
            int runIndex = array.indexOf(aiModifierRun);
            int surviveIndex = array.indexOf(aiModifierSurvive);
            int tradeIndex = array.indexOf(aiModifierTrade);
            int helpOwnerIndex = array.indexOf(aiModifierHelpOwner);
            int mateIndex = array.indexOf(aiModifierMate);
            int doUselessThingsIndex = array.indexOf(aiModifierDoUselessThings);
            int wanderIndex = array.indexOf(aiModifierWander);

            tasks.addTask(1, new EntityAIWander((EntityCreature) entity, 1.0D));

            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_ARROW_ATTACK)) {
            //            int maxRangedAttackTime = gReg.getValueInteger(entity, Genes.GENE_AI_ARROW_ATTACK_MAX_RANGED_ATTACK_TIME);
            //            int minRangedAttackTime = gReg.getValueInteger(entity, Genes.GENE_AI_ARROW_ATTACK_MIN_RANGED_ATTACK_TIME);
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_ARROW_ATTACK_MOVE_SPEED);
            //            float rangedAttackTimeModifier = gReg.getValueFloat(entity, Genes.GENE_AI_ARROW_ATTACK_RANGED_ATTACK_TIME_MODIFIER);
            //
            //            tasks.addTask(attackIndex, new EntityAIArrowAttack(entity, moveSpeed, minRangedAttackTime, maxRangedAttackTime, rangedAttackTimeModifier));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_ATTACK_ON_COLLIDE)) {
            //            try {
            //                boolean longMemory = gReg.getValueBoolean(entity, Genes.GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY);
            //                double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED);
            //                Class target = Class.forName(gReg.getValueString(entity, Genes.GENE_AI_ATTACK_ON_COLLIDE_TARGET));
            //
            //                tasks.addTask(attackIndex, new EntityAIAttackOnCollide((EntityCreature) entity, target, moveSpeed, longMemory));
            //            } catch(Exception e) {
            //                e.printStackTrace();
            //            }
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_AVOID_ENTITY)) {
            //            try {
            //                double farSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_AVOID_ENTITY_FAR_SPEED);
            //                double nearSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_AVOID_ENTITY_NEAR_SPEED);
            //                float range = gReg.getValueFloat(entity, Genes.GENE_AI_AVOID_ENTITY_RANGE);
            //                Class target = Class.forName(gReg.getValueString(entity, Genes.GENE_AI_AVOID_ENTITY_TARGET));
            //
            //                tasks.addTask(runIndex, new EntityAIAvoidEntity((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), target, range, farSpeed, nearSpeed));
            //            } catch(Exception e) {
            //                e.printStackTrace();
            //            }
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_BEG)) {
            //            float range = gReg.getValueFloat(entity, Genes.GENE_AI_BEG_RANGE);
            //
            //            tasks.addTask(doUselessThingsIndex, new EntityAIBeg((EntityWolf) entity.getEntityAsInstanceOf(EntityWolf.class), range));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_BREAK_DOOR)) {
            //            tasks.addTask(attackIndex, new EntityAIBreakDoor((EntityLiving) entity));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_CONTROLLED_BY_PLAYER)) {
            //            float maxSpeed = gReg.getValueFloat(entity, Genes.GENE_AI_CONTROLLED_BY_PLAYER_MAX_SPEED);
            //
            //            tasks.addTask(helpOwnerIndex, new EntityAIControlledByPlayer((EntityLiving) entity, maxSpeed));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_CREEPER_SWELL)) {
            //            tasks.addTask(attackIndex, new EntityAICreeperSwell((EntityCreeper) entity.getEntityAsInstanceOf(EntityCreeper.class)));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_DEFEND_VILLAGE)) {
            //            tasks.addTask(surviveIndex, new EntityAIDefendVillage((EntityIronGolem) entity.getEntityAsInstanceOf(EntityGolem.class)));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_EAT_GRASS)) {
            //            tasks.addTask(wanderIndex, new EntityAIEatGrass((EntityLiving) entity));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_FLEE_SUN)) {
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_FLEE_SUN_MOVE_SPEED);
            //
            //            tasks.addTask(surviveIndex, new EntityAIFleeSun((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_FOLLOW_GOLEM)) {
            //            tasks.addTask(helpOwnerIndex, new EntityAIFollowGolem((EntityVillager) entity.getEntityAsInstanceOf(EntityVillager.class)));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_FOLLOW_OWNER) || gReg.getValueBoolean(entity, Genes.GENE_IS_TAMEABLE)) {
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_FOLLOW_OWNER_MOVE_SPEED);
            //            float maxDistance = gReg.getValueFloat(entity, Genes.GENE_AI_FOLLOW_OWNER_MAX_DISTANCE);
            //            float minDistance = gReg.getValueFloat(entity, Genes.GENE_AI_FOLLOW_OWNER_MIN_DISTANCE);
            //
            //            tasks.addTask(helpOwnerIndex, new EntityAIFollowOwner((EntityTameable) entity.getEntityAsInstanceOf(EntityTameable.class), moveSpeed, minDistance, maxDistance));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_FOLLOW_PARENT)) {
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_FOLLOW_PARENT_MOVE_SPEED);
            //
            //            tasks.addTask(mateIndex, new EntityAIFollowParent((EntityAnimal) entity.getEntityAsInstanceOf(EntityAnimal.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_HURT_BY_TARGET)) {
            //            boolean callHelp = gReg.getValueBoolean(entity, Genes.GENE_AI_HURT_BY_TARGET_CALL_HELP);
            //
            //            targetTasks.addTask(surviveIndex, new EntityAIHurtByTarget((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), callHelp));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_LEAP_AT_TARGET)) {
            //            float motionY = gReg.getValueFloat(entity, Genes.GENE_AI_LEAP_AT_TARGET_MOTION_Y);
            //
            //            tasks.addTask(attackIndex, new EntityAILeapAtTarget((EntityLiving) entity, motionY));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_LOOK_AT_TRADE_PLAYER)) {
            //            tasks.addTask(doUselessThingsIndex, new EntityAILookAtTradePlayer((EntityVillager) entity.getEntityAsInstanceOf(EntityVillager.class)));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_LOOK_AT_VILLAGER)) {
            //            tasks.addTask(doUselessThingsIndex, new EntityAILookAtVillager((EntityIronGolem) entity.getEntityAsInstanceOf(EntityIronGolem.class)));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_LOOK_IDLE)) {
            //            tasks.addTask(doUselessThingsIndex, new EntityAILookIdle((EntityLiving) entity));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_MATE)) {
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_MATE_MOVE_SPEED);
            //
            //            tasks.addTask(mateIndex, new EntityAIMate((EntityAnimal) entity.getEntityAsInstanceOf(EntityAnimal.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_MOVE_INDOORS)) {
            //            tasks.addTask(surviveIndex, new EntityAIMoveIndoors((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class)));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_MOVE_THROUGH_VILLAGE)) {
            //            boolean isNocturnal = gReg.getValueBoolean(entity, Genes.GENE_AI_MOVE_THROUGH_VILLAGE_IS_NOCTURNAL);
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_MOVE_THROUGH_VILLAGE_MOVE_SPEED);
            //
            //            tasks.addTask(wanderIndex, new EntityAIMoveThroughVillage((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), moveSpeed, isNocturnal));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION)) {
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_MOVE_SPEED);
            //
            //            tasks.addTask(wanderIndex, new EntityAIMoveTowardsRestriction((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_MOVE_TOWARDS_TARGET)) {
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_MOVE_TOWARDS_TARGET);
            //            float maxDistance = gReg.getValueFloat(entity, Genes.GENE_AI_MOVE_TOWARDS_TARGET_MAX_DISTANCE);
            //
            //            tasks.addTask(attackIndex, new EntityAIMoveTowardsTarget((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), moveSpeed, maxDistance));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET)) {
            //            try {
            //                boolean nearbyOnly = gReg.getValueBoolean(entity, Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY);
            //                Class target = Class.forName(gReg.getValueString(entity, Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET));
            //                int targetChance = gReg.getValueInteger(entity, Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE);
            //                boolean visible = gReg.getValueBoolean(entity, Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE);
            //                String entitySelector = gReg.getValueString(entity, Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR);
            //                IEntitySelector selector = null;
            //
            //                if(entitySelector.equals("mobSelector")) {
            //                    selector = IMob.mobSelector;
            //                }
            //
            //                targetTasks.addTask(attackIndex, new EntityAINearestAttackableTarget((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), target, targetChance, nearbyOnly, visible, selector));
            //            } catch(Exception e) {
            //                e.printStackTrace();
            //            }
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_OCELOT_ATTACK)) {
            //            tasks.addTask(attackIndex, new EntityAIOcelotAttack((EntityLiving) entity));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_OCELOT_SIT)) {
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_OCELOT_SIT_MOVE_SPEED);
            //
            //            tasks.addTask(helpOwnerIndex, new EntityAIOcelotSit((EntityOcelot) entity.getEntityAsInstanceOf(EntityOcelot.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_OPEN_DOOR)) {
            //            boolean closeDoor = gReg.getValueBoolean(entity, Genes.GENE_AI_OPEN_DOOR_CLOSE_DOOR);
            //
            //            tasks.addTask(wanderIndex, new EntityAIOpenDoor((EntityLiving) entity, closeDoor));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_OWNER_HURT_BY_TARGET)) {
            //            targetTasks.addTask(attackIndex, new EntityAIOwnerHurtByTarget((EntityTameable) entity.getEntityAsInstanceOf(EntityTameable.class)));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_OWNER_HURT_TARGET)) {
            //            targetTasks.addTask(helpOwnerIndex, new EntityAIOwnerHurtTarget((EntityTameable) entity.getEntityAsInstanceOf(EntityTameable.class)));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_PANIC)) {
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_PANIC_MOVE_SPEED);
            //
            //            tasks.addTask(surviveIndex, new EntityAIPanic((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_PLAY)) {
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_PLAY_MOVE_SPEED);
            //
            //            tasks.addTask(doUselessThingsIndex, new EntityAIPlay((EntityVillager) entity.getEntityAsInstanceOf(EntityVillager.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_RESTRICT_OPEN_DOOR)) {
            //            tasks.addTask(surviveIndex, new EntityAIRestrictOpenDoor((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class)));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_RESTRICT_SUN)) {
            //            tasks.addTask(surviveIndex, new EntityAIRestrictSun((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class)));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_RUN_AROUND_LIKE_CRAZY)) {
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_RUN_AROUND_LIKE_CRAZY_MOVE_SPEED);
            //
            //            //This should be EntityAITameHorse
            //            tasks.addTask(doUselessThingsIndex, new EntityAIRunAroundLikeCrazy((EntityHorse) entity.getEntityAsInstanceOf(EntityHorse.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_SIT)) {
            //            tasks.addTask(helpOwnerIndex, new EntityAISit((EntityTameable) entity.getEntityAsInstanceOf(EntityTameable.class)));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_SWIMMING)) {
            //            tasks.addTask(surviveIndex, new EntityAISwimming((EntityLiving) entity));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_TARGET_NON_TAMED)) {
            //            try {
            //                Class target = Class.forName(gReg.getValueString(entity, Genes.GENE_AI_TARGET_NON_TAMED_TARGET));
            //                int targetChance = gReg.getValueInteger(entity, Genes.GENE_AI_TARGET_NON_TAMED_TARGET_CHANCE);
            //                boolean visible = gReg.getValueBoolean(entity, Genes.GENE_AI_TARGET_NON_TAMED_VISIBLE);
            //
            //                targetTasks.addTask(attackIndex, new EntityAITargetNonTamed((EntityTameable) entity.getEntityAsInstanceOf(EntityTameable.class), target, targetChance, visible));
            //            } catch(Exception e) {
            //                e.printStackTrace();
            //            }
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_TEMPT)) {
            //            Item item = gReg.getValueItemStack(entity, Genes.GENE_AI_TEMPT_ITEM).getItem();
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_TEMPT_MOVE_SPEED);
            //            boolean scaredByPlayer = gReg.getValueBoolean(entity, Genes.GENE_AI_TEMPT_SCARED_BY_PLAYER);
            //
            //            tasks.addTask(surviveIndex, new EntityAITempt((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), moveSpeed, item, scaredByPlayer));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_TRADE_PLAYER)) {
            //            tasks.addTask(tradeIndex, new EntityAITradePlayer((EntityVillager) entity.getEntityAsInstanceOf(EntityVillager.class)));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_VILLAGER_MATE)) {
            //            tasks.addTask(mateIndex, new EntityAIVillagerMate((EntityVillager) entity.getEntityAsInstanceOf(EntityVillager.class)));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_WANDER)) {
            //            double moveSpeed = gReg.getValueDouble(entity, Genes.GENE_AI_WANDER_MOVE_SPEED);
            //
            //            tasks.addTask(wanderIndex, new EntityAIWander((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_WATCH_CLOSEST)) {
            //            try {
            //                Class target = Class.forName(gReg.getValueString(entity, Genes.GENE_AI_WATCH_CLOSEST_TARGET));
            //                float range = gReg.getValueFloat(entity, Genes.GENE_AI_WATCH_CLOSEST_RANGE);
            //                float chance = gReg.getValueFloat(entity, Genes.GENE_AI_WATCH_CLOSEST_CHANCE);
            //
            //                tasks.addTask(doUselessThingsIndex, new EntityAIWatchClosest((EntityLiving) entity, target, range, chance));
            //            } catch(Exception e) {
            //                e.printStackTrace();
            //            }
            //        }
            //
            //        if(gReg.getValueBoolean(entity, Genes.GENE_AI_WATCH_CLOSEST_2)) {
            //            try {
            //                Class target = Class.forName(gReg.getValueString(entity, Genes.GENE_AI_WATCH_CLOSEST_2_TARGET));
            //                float range = gReg.getValueFloat(entity, Genes.GENE_AI_WATCH_CLOSEST_2_RANGE);
            //                float chance = gReg.getValueFloat(entity, Genes.GENE_AI_WATCH_CLOSEST_2_CHANCE);
            //
            //                tasks.addTask(doUselessThingsIndex, new EntityAIWatchClosest2((EntityLiving) entity, target, range, chance));
            //            } catch(Exception e) {
            //                e.printStackTrace();
            //            }
            //        }
        }
    }

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        boolean useNewAI = SoulHelper.geneRegistry.getValueBoolean(entity, Genes.GENE_USE_NEW_AI);
        boolean useOldAI = SoulHelper.geneRegistry.getValueBoolean(entity, Genes.GENE_USE_OLD_AI);
        entity.getWorld().theProfiler.startSection("ai");

        if(UtilSoulEntity.isMovementBlocked(entity)) {
            entity.setBoolean("isJumping", false);
            entity.setFloat("moveStrafing", 0.0F);
            entity.setFloat("moveForward", 0.0F);
            entity.setFloat("randomYawVelocity", 0.0F);
        } else if(!entity.getWorld().isRemote) {
            if(useNewAI) {
                entity.getWorld().theProfiler.startSection("newAi");
                updateAITasks(entity);
                entity.getWorld().theProfiler.endSection();
            } else if(useOldAI) {
                entity.getWorld().theProfiler.startSection("oldAi");
                entity.updateEntityActionState();
                entity.getWorld().theProfiler.endSection();
                entity.setFloat("rotationYawHead", entity.getFloat("rotationYaw"));
            }
        }

        entity.getWorld().theProfiler.endSection();
    }

    @Override
    public void updateWanderPath(IEntitySoulCustom entity) {
        double posX = entity.getDouble("posX");
        double posY = entity.getDouble("posY");
        double posZ = entity.getDouble("posZ");

        entity.getWorld().theProfiler.startSection("stroll");
        boolean flag = false;
        int i = -1;
        int j = -1;
        int k = -1;
        float f = -99999.0F;

        for(int l = 0; l < 10; ++l) {
            int i1 = MathHelper.floor_double(posX + (double) entity.getRandom().nextInt(13) - 6.0D);
            int j1 = MathHelper.floor_double(posY + (double) entity.getRandom().nextInt(7) - 3.0D);
            int k1 = MathHelper.floor_double(posZ + (double) entity.getRandom().nextInt(13) - 6.0D);
            float f1 = entity.getBlockPathWeight(i1, j1, k1);

            if(f1 > f) {
                f = f1;
                i = i1;
                j = j1;
                k = k1;
                flag = true;
            }
        }

        if(flag) {
            entity.setObject("pathToEntity", entity.getWorld().getEntityPathToXYZ((Entity) entity, i, j, k, 10.0F, true, false, false, true));
        }

        entity.getWorld().theProfiler.endSection();
    }

    @Override
    public float getBlockPathWeight(IEntitySoulCustom entity, int x, int y, int z) {
        //TODO this properly
        return 0.5F - entity.getWorld().getLightBrightness(x, y, z);
    }

    @Override
    public Entity findPlayerToAttack(IEntitySoulCustom entity) {
        return entity.getWorld().getClosestPlayerToEntity((Entity) entity, 50);
    }

    @Override
    public void updateEntityActionState(IEntitySoulCustom entity) {
        System.out.println("TEST");

        entity.setInteger("entityAge", entity.getInteger("entityAge") + 1);
        entity.setFloat("moveForward", 0.0F);
        entity.setFloat("moveStrafing", 0.0F);
        UtilSoulEntity.despawnEntity(entity);
        float f = 8.0F;

        if(entity.getRandom().nextFloat() < 0.02F) {
            EntityPlayer entityplayer = entity.getWorld().getClosestPlayerToEntity((Entity) entity, (double) f);

            if(entityplayer != null) {
                entity.setObject("currentTarget", entityplayer);
                entity.setInteger("numTicksToChaseTarget", 10 + entity.getRandom().nextInt(20));
            } else {
                entity.setFloat("randomYawVelocity", (entity.getRandom().nextFloat() - 0.5F) * 20.0F);
            }
        }

        Entity currentTarget = (Entity) entity.getObject("currentTarget");
        int numTicksToChaseTarget = entity.getInteger("numTicksToChaseTarget");

        if(currentTarget != null) {
            UtilSoulEntity.faceEntity(entity, currentTarget, 10.0F, (float) 40);

            if(numTicksToChaseTarget-- <= 0 || currentTarget.isDead || currentTarget.getDistanceSqToEntity((Entity) entity) > (double) (f * f)) {
                entity.setObject("currentTarget", null);
            }
        } else {
            if(entity.getRandom().nextFloat() < 0.05F) {
                entity.setFloat("randomYawVelocity", (entity.getRandom().nextFloat() - 0.5F) * 20.0F);
            }

            entity.setFloat("rotationYaw", entity.getFloat("rotationYaw") + entity.getFloat("randomYawVelocity"));
            entity.setFloat("rotationPitch", entity.getFloat("defaultPitch"));
        }

        entity.setObject("currentTarget", currentTarget);
        entity.setInteger("numTicksToChaseTarget", numTicksToChaseTarget);

        boolean flag1 = entity.getBoolean("inWater");
        boolean flag = UtilSoulEntity.handleLavaMovement(entity);

        if(flag1 || flag) {
            entity.setBoolean("isJumping", entity.getRandom().nextFloat() < 0.8F);
        }
    }

    public void updateAITasks(IEntitySoulCustom entity) {
        entity.setInteger("entityAge", entity.getInteger("entityAge") + 1);
        entity.getWorld().theProfiler.startSection("checkDespawn");
        UtilSoulEntity.despawnEntity(entity);
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("sensing");
        ((EntityLiving) entity).getEntitySenses().clearSensingCache();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("targetSelector");
        ((EntityAITasks) entity.getObject("targetTasks")).onUpdateTasks();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("goalSelector");
        ((EntityAITasks) entity.getObject("tasks")).onUpdateTasks();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("navigation");
        ((EntityLiving) entity).getNavigator().onUpdateNavigation();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("mob tick");
        entity.updateAITick();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.startSection("controls");
        entity.getWorld().theProfiler.startSection("move");
        ((EntityLiving) entity).getMoveHelper().onUpdateMoveHelper();
        entity.getWorld().theProfiler.endStartSection("look");
        ((EntityLiving) entity).getLookHelper().onUpdateLook();
        entity.getWorld().theProfiler.endStartSection("jump");
        ((EntityLiving) entity).getJumpHelper().doJump();
        entity.getWorld().theProfiler.endSection();
        entity.getWorld().theProfiler.endSection();
    }
}
