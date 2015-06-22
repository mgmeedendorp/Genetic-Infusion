package seremis.geninfusion.soul.traits

import net.minecraft.command.IEntitySelector
import net.minecraft.entity.ai._
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.entity.{Entity, EntityCreature, EntityLiving, SharedMonsterAttributes}
import net.minecraft.pathfinding.PathEntity
import net.minecraft.util.MathHelper
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.soul.entity.ai._

class TraitAI extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        val useNewAI = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_USE_NEW_AI)
        if(useNewAI) {
            val living = entity.asInstanceOf[EntityLiving]

            val gReg = SoulHelper.geneRegistry
            val tasks = living.tasks
            val targetTasks = living.targetTasks

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_SWIMMING)) {
                val index: Int = gReg.getValueFromAllele(entity, Genes.GENE_AI_SWIMMING_INDEX)

                tasks.addTask(index, new EntityAISwimming(living))
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_ATTACK_ON_COLLIDE)) {
                val index: Array[Int] = gReg.getValueFromAllele(entity, Genes.GENE_AI_ATTACK_ON_COLLIDE_INDEX)

                val longMemory: Array[Boolean] = gReg.getValueFromAllele(entity, Genes.GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY)
                val moveSpeed: Array[Double] = gReg.getValueFromAllele(entity, Genes.GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED)
                val target: Array[Class[_]] = gReg.getValueFromAllele(entity, Genes.GENE_AI_ATTACK_ON_COLLIDE_TARGET)

                if(index != null && index.length != 0) {
                    for(i <- 0 until index.length) {
                        if(target != null && target.length != 0) {
                            tasks.addTask(index(i), new EntityAIAttackOnCollideCustom(entity, target(i), moveSpeed(i), longMemory(i)))
                        } else {
                            tasks.addTask(index(i), new EntityAIAttackOnCollideCustom(entity, moveSpeed(i), longMemory(i)))
                        }
                    }
                }
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION)) {
                val index: Int = gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_INDEX)
                val moveSpeed: Double = gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_MOVE_SPEED)

                tasks.addTask(index, new EntityAIMoveTowardsRestrictionCustom(entity, moveSpeed))
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_THROUGH_VILLAGE)) {
                val index: Int = gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_THROUGH_VILLAGE_INDEX)
                val isNocturnal: Boolean = gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_THROUGH_VILLAGE_IS_NOCTURNAL)
                val moveSpeed: Double = gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_THROUGH_VILLAGE_MOVE_SPEED)

                tasks.addTask(index, new EntityAIMoveThroughVillageCustom(entity, moveSpeed, isNocturnal))
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_WANDER)) {
                val index: Int = gReg.getValueFromAllele(entity, Genes.GENE_AI_WANDER_INDEX)
                val moveSpeed: Double = gReg.getValueFromAllele(entity, Genes.GENE_AI_WANDER_MOVE_SPEED)

                tasks.addTask(index, new EntityAIWanderCustom(entity, moveSpeed))
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_WATCH_CLOSEST)) {
                val index: Array[Int] = gReg.getValueFromAllele(entity, Genes.GENE_AI_WATCH_CLOSEST_INDEX)
                val target: Array[Class[_]] = gReg.getValueFromAllele(entity, Genes.GENE_AI_WATCH_CLOSEST_TARGET)
                val range: Array[Float] = gReg.getValueFromAllele(entity, Genes.GENE_AI_WATCH_CLOSEST_RANGE)
                val chance: Array[Float] = gReg.getValueFromAllele(entity, Genes.GENE_AI_WATCH_CLOSEST_CHANCE)

                if(index != null && index.length != 0)
                    for(i <- 0 until target.length)
                        tasks.addTask(index(i), new EntityAIWatchClosest(living, target(i), range(i), chance(i)))
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_LOOK_IDLE)) {
                val index: Int = gReg.getValueFromAllele(entity, Genes.GENE_AI_LOOK_IDLE_INDEX)

                tasks.addTask(index, new EntityAILookIdle(living))
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_HURT_BY_TARGET)) {
                val index: Int = gReg.getValueFromAllele(entity, Genes.GENE_AI_HURT_BY_TARGET_INDEX)
                val callHelp: Boolean = gReg.getValueFromAllele(entity, Genes.GENE_AI_HURT_BY_TARGET_CALL_HELP)

                targetTasks.addTask(index, new EntityAIHurtByTargetCustom(entity, callHelp))
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET)) {
                val index: Array[Int] = gReg.getValueFromAllele(entity, Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_INDEX)
                val nearbyOnly: Array[Boolean] = gReg.getValueFromAllele(entity, Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY)
                val target: Array[Class[_]] = gReg.getValueFromAllele(entity, Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET)
                val targetChance: Array[Int] = gReg.getValueFromAllele(entity, Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE)
                val visible: Array[Boolean] = gReg.getValueFromAllele(entity, Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE)
                val entitySelector: Array[String] = gReg.getValueFromAllele(entity, Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR)
                var selector: IEntitySelector = null

                if(index != null && index.length != 0) {
                    for(i <- 0 until target.length) {
                        if(entitySelector(i) == "mobSelector") {
                            selector = IMob.mobSelector
                        }
                        targetTasks.addTask(index(i), new EntityAINearestAttackableTargetCustom(entity, target(i), targetChance(i), visible(i), nearbyOnly(i), selector))
                    }
                }
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_RESTRICT_SUN)) {
                val index: Int = gReg.getValueFromAllele(entity, Genes.GENE_AI_RESTRICT_SUN_INDEX)

                tasks.addTask(index, new EntityAIRestrictSunCustom(entity))
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_FLEE_SUN)) {
                val index: Int = gReg.getValueFromAllele(entity, Genes.GENE_AI_FLEE_SUN_INDEX)
                val moveSpeed: Double = gReg.getValueFromAllele(entity, Genes.GENE_AI_FLEE_SUN_MOVE_SPEED)

                tasks.addTask(index, new EntityAIFleeSunCustom(entity, moveSpeed))
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_BREAK_DOOR)) {
                val index: Int = gReg.getValueFromAllele(entity, Genes.GENE_AI_BREAK_DOOR_INDEX)

                tasks.addTask(index, new EntityAIBreakDoor(living))
                living.getNavigator.setBreakDoors(true)
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_ARROW_ATTACK)) {
                val index: Int = gReg.getValueFromAllele(entity, Genes.GENE_AI_ARROW_ATTACK_INDEX)
                val maxRangedAttackTime: Int = gReg.getValueFromAllele(entity, Genes.GENE_AI_ARROW_ATTACK_MAX_RANGED_ATTACK_TIME)
                val minRangedAttackTime: Int = gReg.getValueFromAllele(entity, Genes.GENE_AI_ARROW_ATTACK_MIN_RANGED_ATTACK_TIME)
                val moveSpeed: Double = gReg.getValueFromAllele(entity, Genes.GENE_AI_ARROW_ATTACK_MOVE_SPEED)
                val rangedAttackTimeModifier: Float = gReg.getValueFromAllele(entity, Genes.GENE_AI_ARROW_ATTACK_RANGED_ATTACK_TIME_MODIFIER)

                tasks.addTask(index, new EntityAIArrowAttack(entity, moveSpeed, minRangedAttackTime, maxRangedAttackTime, rangedAttackTimeModifier))
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_AVOID_ENTITY)) {
                val index: Array[Int] = gReg.getValueFromAllele(entity, Genes.GENE_AI_AVOID_ENTITY_INDEX)
                val farSpeed: Array[Double] = gReg.getValueFromAllele(entity, Genes.GENE_AI_AVOID_ENTITY_FAR_SPEED)
                val nearSpeed: Array[Double] = gReg.getValueFromAllele(entity, Genes.GENE_AI_AVOID_ENTITY_NEAR_SPEED)
                val range: Array[Float] = gReg.getValueFromAllele(entity, Genes.GENE_AI_AVOID_ENTITY_RANGE)
                val target: Array[Class[_]] = gReg.getValueFromAllele(entity, Genes.GENE_AI_AVOID_ENTITY_TARGET)

                if(index != null && index.length != 0)
                    for(i <- 0 until index.length)
                        tasks.addTask(index(i), new EntityAIAvoidEntityCustom(entity, target(i), range(i), farSpeed(i), nearSpeed(i)))
            }

            if(gReg.getValueFromAllele(entity, Genes.GENE_AI_CREEPER_SWELL)) {
                val index: Int = gReg.getValueFromAllele(entity, Genes.GENE_AI_CREEPER_SWELL_INDEX)

                tasks.addTask(index, new EntityAICreeperSwellCustom(entity))
            }


            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_BEG)) {
            //            float range = gReg.getValueFromAllele(entity, Genes.GENE_AI_BEG_RANGE);
            //
            //            tasks.addTask(doUselessThingsIndex, new EntityAIBeg((EntityWolf) entity.getEntityAsInstanceOf(EntityWolf.class), range));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_BREAK_DOOR)) {
            //            tasks.addTask(attackIndex, new EntityAIBreakDoor((EntityLiving) entity));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_CONTROLLED_BY_PLAYER)) {
            //            float maxSpeed = gReg.getValueFromAllele(entity, Genes.GENE_AI_CONTROLLED_BY_PLAYER_MAX_SPEED);
            //
            //            tasks.addTask(helpOwnerIndex, new EntityAIControlledByPlayer((EntityLiving) entity, maxSpeed));
            //        }
            //
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_DEFEND_VILLAGE)) {
            //            tasks.addTask(surviveIndex, new EntityAIDefendVillage((EntityIronGolem) entity.getEntityAsInstanceOf(EntityGolem.class)));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_EAT_GRASS)) {
            //            tasks.addTask(wanderIndex, new EntityAIEatGrass((EntityLiving) entity));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_FOLLOW_GOLEM)) {
            //            tasks.addTask(helpOwnerIndex, new EntityAIFollowGolem((EntityVillager) entity.getEntityAsInstanceOf(EntityVillager.class)));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_IS_TAMEABLE)) {
            //            double moveSpeed = gReg.getValueFromAllele(entity, Genes.GENE_AI_FOLLOW_OWNER_MOVE_SPEED);
            //            float maxDistance = gReg.getValueFromAllele(entity, Genes.GENE_AI_FOLLOW_OWNER_MAX_DISTANCE);
            //            float minDistance = gReg.getValueFromAllele(entity, Genes.GENE_AI_FOLLOW_OWNER_MIN_DISTANCE);
            //
            //            tasks.addTask(helpOwnerIndex, new EntityAIFollowOwner((EntityTameable) entity.getEntityAsInstanceOf(EntityTameable.class), moveSpeed, minDistance, maxDistance));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_FOLLOW_PARENT)) {
            //            double moveSpeed = gReg.getValueFromAllele(entity, Genes.GENE_AI_FOLLOW_PARENT_MOVE_SPEED);
            //
            //            tasks.addTask(mateIndex, new EntityAIFollowParent((EntityAnimal) entity.getEntityAsInstanceOf(EntityAnimal.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_LEAP_AT_TARGET)) {
            //            float motionY = gReg.getValueFromAllele(entity, Genes.GENE_AI_LEAP_AT_TARGET_MOTION_Y);
            //
            //            tasks.addTask(attackIndex, new EntityAILeapAtTarget((EntityLiving) entity, motionY));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_LOOK_AT_TRADE_PLAYER)) {
            //            tasks.addTask(doUselessThingsIndex, new EntityAILookAtTradePlayer((EntityVillager) entity.getEntityAsInstanceOf(EntityVillager.class)));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_LOOK_AT_VILLAGER)) {
            //            tasks.addTask(doUselessThingsIndex, new EntityAILookAtVillager((EntityIronGolem) entity.getEntityAsInstanceOf(EntityIronGolem.class)));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_LOOK_IDLE)) {
            //            tasks.addTask(doUselessThingsIndex, new EntityAILookIdle((EntityLiving) entity));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_MATE)) {
            //            double moveSpeed = gReg.getValueFromAllele(entity, Genes.GENE_AI_MATE_MOVE_SPEED);
            //
            //            tasks.addTask(mateIndex, new EntityAIMate((EntityAnimal) entity.getEntityAsInstanceOf(EntityAnimal.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_INDOORS)) {
            //            tasks.addTask(surviveIndex, new EntityAIMoveIndoors((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class)));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_THROUGH_VILLAGE)) {
            //            boolean isNocturnal = gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_THROUGH_VILLAGE_IS_NOCTURNAL);
            //            double moveSpeed = gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_THROUGH_VILLAGE_MOVE_SPEED);
            //
            //            tasks.addTask(wanderIndex, new EntityAIMoveThroughVillage((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), moveSpeed, isNocturnal));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION)) {
            //            double moveSpeed = gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_MOVE_SPEED);
            //
            //            tasks.addTask(wanderIndex, new EntityAIMoveTowardsRestriction((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_TOWARDS_TARGET)) {
            //            double moveSpeed = gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_TOWARDS_TARGET);
            //            float maxDistance = gReg.getValueFromAllele(entity, Genes.GENE_AI_MOVE_TOWARDS_TARGET_MAX_DISTANCE);
            //
            //            tasks.addTask(attackIndex, new EntityAIMoveTowardsTarget((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), moveSpeed, maxDistance));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_OCELOT_ATTACK)) {
            //            tasks.addTask(attackIndex, new EntityAIOcelotAttack((EntityLiving) entity));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_OCELOT_SIT)) {
            //            double moveSpeed = gReg.getValueFromAllele(entity, Genes.GENE_AI_OCELOT_SIT_MOVE_SPEED);
            //
            //            tasks.addTask(helpOwnerIndex, new EntityAIOcelotSit((EntityOcelot) entity.getEntityAsInstanceOf(EntityOcelot.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_OPEN_DOOR)) {
            //            boolean closeDoor = gReg.getValueFromAllele(entity, Genes.GENE_AI_OPEN_DOOR_CLOSE_DOOR);
            //
            //            tasks.addTask(wanderIndex, new EntityAIOpenDoor((EntityLiving) entity, closeDoor));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_OWNER_HURT_BY_TARGET)) {
            //            targetTasks.addTask(attackIndex, new EntityAIOwnerHurtByTarget((EntityTameable) entity.getEntityAsInstanceOf(EntityTameable.class)));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_OWNER_HURT_TARGET)) {
            //            targetTasks.addTask(helpOwnerIndex, new EntityAIOwnerHurtTarget((EntityTameable) entity.getEntityAsInstanceOf(EntityTameable.class)));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_PANIC)) {
            //            double moveSpeed = gReg.getValueFromAllele(entity, Genes.GENE_AI_PANIC_MOVE_SPEED);
            //
            //            tasks.addTask(surviveIndex, new EntityAIPanic((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_PLAY)) {
            //            double moveSpeed = gReg.getValueFromAllele(entity, Genes.GENE_AI_PLAY_MOVE_SPEED);
            //
            //            tasks.addTask(doUselessThingsIndex, new EntityAIPlay((EntityVillager) entity.getEntityAsInstanceOf(EntityVillager.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_RESTRICT_OPEN_DOOR)) {
            //            tasks.addTask(surviveIndex, new EntityAIRestrictOpenDoor((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class)));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_RUN_AROUND_LIKE_CRAZY)) {
            //            double moveSpeed = gReg.getValueFromAllele(entity, Genes.GENE_AI_RUN_AROUND_LIKE_CRAZY_MOVE_SPEED);
            //
            //            //This should be EntityAITameHorse
            //            tasks.addTask(doUselessThingsIndex, new EntityAIRunAroundLikeCrazy((EntityHorse) entity.getEntityAsInstanceOf(EntityHorse.class), moveSpeed));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_SIT)) {
            //            tasks.addTask(helpOwnerIndex, new EntityAISit((EntityTameable) entity.getEntityAsInstanceOf(EntityTameable.class)));
            //        }
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_TARGET_NON_TAMED)) {
            //            try {
            //                Class target = Class.forName(gReg.getValueFromAllele(entity, Genes.GENE_AI_TARGET_NON_TAMED_TARGET));
            //                int targetChance = gReg.getValueFromAllele(entity, Genes.GENE_AI_TARGET_NON_TAMED_TARGET_CHANCE);
            //                boolean visible = gReg.getValueFromAllele(entity, Genes.GENE_AI_TARGET_NON_TAMED_VISIBLE);
            //
            //                targetTasks.addTask(attackIndex, new EntityAITargetNonTamed((EntityTameable) entity.getEntityAsInstanceOf(EntityTameable.class), target, targetChance, visible));
            //            } catch(Exception e) {
            //                e.printStackTrace();
            //            }
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_TEMPT)) {
            //            Item item = gReg.getValueFromAllele();
            //            double moveSpeed = gReg.getValueFromAllele(entity, Genes.GENE_AI_TEMPT_MOVE_SPEED);
            //            boolean scaredByPlayer = gReg.getValueFromAllele(entity, Genes.GENE_AI_TEMPT_SCARED_BY_PLAYER);
            //
            //            tasks.addTask(surviveIndex, new EntityAITempt((EntityCreature) entity.getEntityAsInstanceOf(EntityCreature.class), moveSpeed, item, scaredByPlayer));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_TRADE_PLAYER)) {
            //            tasks.addTask(tradeIndex, new EntityAITradePlayer((EntityVillager) entity.getEntityAsInstanceOf(EntityVillager.class)));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_VILLAGER_MATE)) {
            //            tasks.addTask(mateIndex, new EntityAIVillagerMate((EntityVillager) entity.getEntityAsInstanceOf(EntityVillager.class)));
            //        }
            //
            //        if(gReg.getValueFromAllele(entity, Genes.GENE_AI_WATCH_CLOSEST_2)) {
            //            try {
            //                Class target = Class.forName(gReg.getValueFromAllele(entity, Genes.GENE_AI_WATCH_CLOSEST_2_TARGET));
            //                float range = gReg.getValueFromAllele(entity, Genes.GENE_AI_WATCH_CLOSEST_2_RANGE);
            //                float chance = gReg.getValueFromAllele(entity, Genes.GENE_AI_WATCH_CLOSEST_2_CHANCE);
            //
            //                tasks.addTask(doUselessThingsIndex, new EntityAIWatchClosest2((EntityLiving) entity, target, range, chance));
            //            } catch(Exception e) {
            //                e.printStackTrace();
            //            }
            //        }
        }
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        val useNewAI = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_USE_NEW_AI)
        val useOldAI = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_USE_OLD_AI)

        entity.getWorld.theProfiler.startSection("ai")

        if(entity.asInstanceOf[EntityLiving].getHealth <= 0.0F) {
            entity.setBoolean(EntityIsJumping, false)
            entity.setFloat(EntityMoveStrafing, 0.0F)
            entity.setFloat(EntityMoveForward, 0.0F)
            entity.setFloat(EntityRandomYawVelocity, 0.0F)
        } else if(!entity.getWorld.isRemote) {
            if(useNewAI) {
                entity.getWorld.theProfiler.startSection("newAi")
                updateAITasks(entity)
                entity.getWorld.theProfiler.endSection()
            } else if(useOldAI) {
                entity.getWorld.theProfiler.startSection("oldAi")
                entity.updateEntityActionState
                entity.getWorld.theProfiler.endSection()
                entity.setFloat(EntityRotationYawHead, entity.getFloat(EntityRotationYaw))
            }
        }
        entity.getWorld.theProfiler.endSection()
    }

    override def updateEntityActionState(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        if(SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GENE_IS_CREATURE)) {
            entity.getWorld.theProfiler.startSection("ai")

            var fleeingTick = entity.getInteger(EntityFleeingTick)

            if(fleeingTick >= 0) {
                val iattributeinstance = living.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
                iattributeinstance.removeModifier(EntityCreature.field_110181_i)
                fleeingTick -= 1
            }

            entity.setInteger(EntityFleeingTick, fleeingTick)
            entity.setBoolean(EntityHasAttacked, entity.isMovementCeased)

            val f4 = 16.0F
            var entityToAttack = entity.getObject(EntityEntityToAttack).asInstanceOf[Entity]

            if(entityToAttack == null) {
                entityToAttack = entity.findPlayerToAttack

                if(entityToAttack != null) {
                    entity.setObject(EntityPathToEntity, entity.getWorld.getPathEntityToEntity(entity.asInstanceOf[Entity], entityToAttack, f4, true, false, false, true))
                }
            } else if(entityToAttack.isEntityAlive) {
                val distance = entityToAttack.getDistanceToEntity(entity.asInstanceOf[Entity])

                if(entity.asInstanceOf[EntityLiving].canEntityBeSeen(entityToAttack)) {
                    entity.attackEntity(entityToAttack, distance)
                }
            } else {
                entityToAttack = null
            }

            if(entityToAttack.isInstanceOf[EntityPlayerMP] && entityToAttack.asInstanceOf[EntityPlayerMP].theItemInWorldManager.isCreative) {
                entityToAttack = null
            }

            entity.setObject(EntityEntityToAttack, entityToAttack)

            entity.getWorld.theProfiler.endSection()

            if(!entity.getBoolean(EntityHasAttacked) && entityToAttack != null && (entity.getObject(EntityPathToEntity) == null || entity.getRandom.nextInt(20) == 0)) {
                entity.setObject(EntityPathToEntity, entity.getWorld.getPathEntityToEntity(entity.asInstanceOf[Entity], entityToAttack, f4, true, false, false, true))
            } else if(!entity.getBoolean(EntityHasAttacked) && (entity.getObject(EntityPathToEntity) == null && entity.getRandom.nextInt(180) == 0 || entity.getRandom.nextInt(120) == 0 || entity.getInteger(EntityFleeingTick) > 0) && entity.getInteger(EntityEntityAge) < 100) {
                entity.updateWanderPath
            }

            val i = MathHelper.floor_double(entity.getBoundingBox.minY + 0.5D)
            val flag = entity.getBoolean(EntityInWater)
            val flag1 = entity.asInstanceOf[EntityLiving].handleLavaMovement()
            entity.setFloat(EntityRotationPitch, 0.0F)

            var pathToEntity = entity.getObject(EntityPathToEntity).asInstanceOf[PathEntity]

            if(pathToEntity != null && entity.getRandom.nextInt(100) != 0) {
                entity.getWorld.theProfiler.startSection("followpath")
                var vec3 = pathToEntity.getPosition(living)
                val d0 = (entity.getFloat(EntityWidth) * 2.0F).toDouble

                val posX = entity.getDouble(EntityPosX)
                val posZ = entity.getDouble(EntityPosZ)
                var rotationYaw = entity.getFloat(EntityRotationYaw)
                var moveForward = entity.getFloat(EntityMoveForward)
                var moveStrafing = entity.getFloat(EntityMoveStrafing)

                while(vec3 != null && vec3.squareDistanceTo(posX, vec3.yCoord, posZ) < d0 * d0) {
                    pathToEntity.incrementPathIndex()

                    if(pathToEntity.isFinished) {
                        vec3 = null
                        pathToEntity = null
                    } else {
                        vec3 = pathToEntity.getPosition(living)
                    }
                }

                entity.setBoolean(EntityIsJumping, false)

                if(vec3 != null) {
                    val d1 = vec3.xCoord - posX
                    val d2 = vec3.zCoord - posZ
                    val d3 = vec3.yCoord - i.toDouble
                    val f1 = (Math.atan2(d2, d1) * 180.0D / Math.PI).toFloat - 90.0F
                    var f2 = MathHelper.wrapAngleTo180_float(f1 - rotationYaw)
                    moveForward = living.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue.toFloat

                    if(f2 > 30.0F) {
                        f2 = 30.0F
                    }

                    if(f2 < -30.0F) {
                        f2 = -30.0F
                    }

                    rotationYaw += f2

                    if(entity.getBoolean(EntityHasAttacked) && entityToAttack != null) {
                        val d4 = entityToAttack.posX - posX
                        val d5 = entityToAttack.posZ - posZ
                        val f3 = rotationYaw
                        rotationYaw = (Math.atan2(d5, d4) * 180.0D / Math.PI).toFloat - 90.0F
                        f2 = (f3 - rotationYaw + 90.0F) * Math.PI.toFloat / 180.0F
                        moveStrafing = -MathHelper.sin(f2) * moveForward * 1.0F
                        moveForward = MathHelper.cos(f2) * moveForward * 1.0F
                    }

                    if(d3 > 0.0D) {
                        entity.setBoolean(EntityIsJumping, true)
                    }
                }

                if(entityToAttack != null) {
                    entity.asInstanceOf[EntityLiving].faceEntity(entityToAttack, 30.0F, 30.0F)
                }

                if(entity.getBoolean(EntityIsCollidedHorizontally) && pathToEntity == null) {
                    entity.setBoolean(EntityIsJumping, true)
                }

                if(entity.getRandom.nextFloat() < 0.8F && (flag || flag1)) {
                    entity.setBoolean(EntityIsJumping, true)
                }

                entity.setFloat(EntityRotationYaw, rotationYaw)
                entity.setFloat(EntityMoveForward, moveForward)
                entity.setFloat(EntityMoveStrafing, moveStrafing)

                entity.getWorld.theProfiler.endSection()
            } else {
                updateEntityActionStateLiving(entity)
                pathToEntity = null
            }
            entity.setObject(EntityPathToEntity, pathToEntity)
        } else {
            updateEntityActionStateLiving(entity)
        }
    }

    def updateEntityActionStateLiving(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        entity.setInteger(EntityEntityAge, entity.getInteger(EntityEntityAge) + 1)
        entity.setFloat(EntityMoveForward, 0.0F)
        entity.setFloat(EntityMoveStrafing, 0.0F)
        entity.despawnEntity
        val f = 8.0F

        if(entity.getRandom.nextFloat() < 0.02F) {
            val entityplayer = entity.getWorld.getClosestPlayerToEntity(entity.asInstanceOf[Entity], f.toDouble)

            if(entityplayer != null) {
                entity.setObject(EntityCurrentTarget, entityplayer)
                entity.setInteger(EntityNumTicksToChaseTarget, 10 + entity.getRandom.nextInt(20))
            } else {
                entity.setFloat(EntityRandomYawVelocity, (entity.getRandom.nextFloat() - 0.5F) * 20.0F)
            }
        }

        val currentTarget = entity.getObject(EntityCurrentTarget).asInstanceOf[Entity]
        var numTicksToChaseTarget = entity.getInteger(EntityNumTicksToChaseTarget)

        if(currentTarget != null) {
            living.faceEntity(currentTarget, 10.0F, 40F)

            numTicksToChaseTarget -= 1
            if(numTicksToChaseTarget <= 0 || currentTarget.isDead || currentTarget.getDistanceSqToEntity(living) > (f * f).toDouble) {
                entity.setObject(EntityCurrentTarget, null)
            }
        } else {
            if(entity.getRandom.nextFloat() < 0.05F) {
                entity.setFloat(EntityRandomYawVelocity, (entity.getRandom.nextFloat() - 0.5F) * 20.0F)
            }

            entity.setFloat(EntityRotationYaw, entity.getFloat(EntityRotationYaw) + entity.getFloat(EntityRandomYawVelocity))
            entity.setFloat(EntityRotationPitch, entity.getFloat(EntityDefaultPitch))
        }

        entity.setObject(EntityCurrentTarget, currentTarget)
        entity.setInteger(EntityNumTicksToChaseTarget, numTicksToChaseTarget)

        val flag1 = entity.getBoolean(EntityInWater)
        val flag = living.handleLavaMovement()

        if(flag1 || flag) {
            entity.setBoolean(EntityIsJumping, entity.getRandom.nextFloat() < 0.8F)
        }
    }

    def updateAITasks(entity: IEntitySoulCustom) {
        entity.setInteger(EntityEntityAge, entity.getInteger(EntityEntityAge) + 1)
        entity.getWorld.theProfiler.startSection("checkDespawn")
        entity.despawnEntity
        entity.getWorld.theProfiler.endSection()
        entity.getWorld.theProfiler.startSection("sensing")
        entity.asInstanceOf[EntityLiving].getEntitySenses.clearSensingCache()
        entity.getWorld.theProfiler.endSection()
        entity.getWorld.theProfiler.startSection("targetSelector")
        entity.asInstanceOf[EntityLiving].targetTasks.onUpdateTasks()
        entity.getWorld.theProfiler.endSection()
        entity.getWorld.theProfiler.startSection("goalSelector")
        entity.asInstanceOf[EntityLiving].tasks.onUpdateTasks()
        entity.getWorld.theProfiler.endSection()
        entity.getWorld.theProfiler.startSection("navigation")
        entity.asInstanceOf[EntityLiving].getNavigator.onUpdateNavigation()
        entity.getWorld.theProfiler.endSection()
        entity.getWorld.theProfiler.startSection("mob tick")
        entity.updateAITick
        entity.getWorld.theProfiler.endSection()
        entity.getWorld.theProfiler.startSection("controls")
        entity.getWorld.theProfiler.startSection("move")
        entity.asInstanceOf[EntityLiving].getMoveHelper.onUpdateMoveHelper()
        entity.getWorld.theProfiler.endStartSection("look")
        entity.asInstanceOf[EntityLiving].getLookHelper.onUpdateLook()
        entity.getWorld.theProfiler.endStartSection("jump")
        entity.asInstanceOf[EntityLiving].getJumpHelper.doJump()
        entity.getWorld.theProfiler.endSection()
        entity.getWorld.theProfiler.endSection()
    }
}
