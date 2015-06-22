package seremis.geninfusion.soul.traits

import java.util
import java.util.Random

import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.{Entity, EntityLiving}
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item._
import net.minecraft.network.play.server.{S04PacketEntityEquipment, S0DPacketCollectItem}
import net.minecraft.stats.AchievementList
import net.minecraft.world.WorldServer
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

import scala.collection.JavaConversions._

class TraitItemPickup extends Trait {

    override def onUpdate(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        entity.getWorld.theProfiler.startSection("looting")

        val canPickUpItems = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_PICKS_UP_ITEMS)

        if(GeneticInfusion.commonProxy.isServerWorld(entity.getWorld) && canPickUpItems && !living.isDead && entity.getWorld.getGameRules.getGameRuleBooleanValue("mobGriefing")) {
            val list = entity.getWorld.getEntitiesWithinAABB(classOf[EntityItem], entity.getBoundingBox.expand(1.0D, 0.0D, 1.0D)).asInstanceOf[util.List[Entity]]

            for(value <- list) {
                val item = value.asInstanceOf[EntityItem]

                if(!item.isDead && item.getEntityItem != null) {
                    val newStack = item.getEntityItem
                    val armorSlot = getArmorSlot(newStack)

                    if(armorSlot > -1) {
                        var dropEquipment = true
                        val currentStack = getEquipmentInSlot(entity, armorSlot)

                        if(currentStack != null) {
                            if(armorSlot == 0) {
                                val canShootBow = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_AI_ARROW_ATTACK)
                                val canFightWithSword = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_AI_ATTACK_ON_COLLIDE)

                                if(((newStack.getItem.isInstanceOf[ItemSword] && canFightWithSword) || (newStack.getItemUseAction == EnumAction.bow && canShootBow)) && (!(currentStack.getItem.isInstanceOf[ItemSword] && canFightWithSword) || !(currentStack.getItemUseAction == EnumAction.bow && canShootBow))) {
                                    dropEquipment = true
                                } else if(newStack.getItem.isInstanceOf[ItemSword] && currentStack.getItem.isInstanceOf[ItemSword]) {
                                    val newSword = newStack.getItem.asInstanceOf[ItemSword]
                                    val oldSword = currentStack.getItem.asInstanceOf[ItemSword]
                                    dropEquipment = if(newSword.func_150931_i() == oldSword.func_150931_i()) newStack.getMetadata > currentStack.getMetadata || newStack.hasTagCompound && !currentStack.hasTagCompound else newSword.func_150931_i() > oldSword.func_150931_i()
                                } else {
                                    dropEquipment = false
                                }
                            } else if(newStack.getItem.isInstanceOf[ItemArmor] && !currentStack.getItem.isInstanceOf[ItemArmor]) {
                                dropEquipment = true
                            } else if(newStack.getItem.isInstanceOf[ItemArmor]) {
                                val newArmor = newStack.getItem.asInstanceOf[ItemArmor]
                                val oldArmor = currentStack.getItem.asInstanceOf[ItemArmor]
                                dropEquipment = if(newArmor.damageReduceAmount == oldArmor.damageReduceAmount) newStack.getMetadata > currentStack.getMetadata || newStack.hasTagCompound && !currentStack.hasTagCompound else newArmor.damageReduceAmount > oldArmor.damageReduceAmount
                            } else {
                                dropEquipment = false
                            }
                        }
                        if(dropEquipment) {
                            val equipmentDropChances = entity.getFloatArray(EntityEquipmentDropChances)
                            val dropChance = equipmentDropChances(armorSlot)

                            if(currentStack != null && new Random().nextFloat() - 0.1F < dropChance) {
                                living.entityDropItem(currentStack, 0.0F)
                            }

                            if(newStack.getItem == Items.diamond && item.getThrower != null) {
                                val player = entity.getWorld.getPlayerEntityByName(item.getThrower)
                                if(player != null) {
                                    player.triggerAchievement(AchievementList.field_150966_x)
                                }
                            }
                            setEquipmentInSlot(entity, armorSlot, newStack)
                            equipmentDropChances(armorSlot) = 2.0F
                            entity.setFloatArray(EntityEquipmentDropChances, equipmentDropChances)
                            entity.setBoolean(EntityPersistenceRequired, true)
                            onItemPickup(entity, item, 1)
                            item.setDead()
                        }
                    }
                }
            }
        }

        if(GeneticInfusion.commonProxy.isServerWorld(entity.getWorld)) {
            val living = entity.asInstanceOf[EntityLiving]
            val arrowCount = living.getArrowCountInEntity

            if(arrowCount > 0) {
                if(living.arrowHitTimer <= 0) {
                    living.arrowHitTimer = 20 * (30 - arrowCount)
                }
                living.arrowHitTimer
                if(living.arrowHitTimer <= 0) {
                    living.setArrowCountInEntity(arrowCount - 1)
                }
            }

            for(j <- 0 until 5) {
                val previousEquipment = entity.getItemStackArray(EntityPreviousEquipment)
                val prevStack = previousEquipment(j)
                val currStack = living.getEquipmentInSlot(j)

                if(!ItemStack.areItemStacksEqual(currStack, prevStack)) {
                    entity.getWorld.asInstanceOf[WorldServer].getEntityTracker.sendToAllTrackingEntity(living, new S04PacketEntityEquipment(entity.getEntityId, j, currStack))

                    if(prevStack != null) {
                        living.getAttributeMap.removeAttributeModifiers(prevStack.getAttributeModifiers)
                    }

                    if(currStack != null) {
                        living.getAttributeMap.applyAttributeModifiers(currStack.getAttributeModifiers)
                    }

                    previousEquipment(j) = if(currStack == null) null else currStack.copy()

                    entity.setItemStackArray(EntityPreviousEquipment, previousEquipment)
                }
            }
        }
        entity.getWorld.theProfiler.endSection()
    }

    def onItemPickup(entity: IEntitySoulCustom, ent: Entity, stackSize: Int) {
        if(!ent.isDead && GeneticInfusion.commonProxy.isServerWorld(entity.getWorld)) {
            val entitytracker = entity.getWorld.asInstanceOf[WorldServer].getEntityTracker
            entitytracker.sendToAllTrackingEntity(ent, new S0DPacketCollectItem(ent.getEntityId, entity.getEntityId))
        }
    }

    def getEquipmentInSlot(entity: IEntitySoulCustom, slot: Int): ItemStack = {
        if(entity.getItemStackArray(EntityEquipment) != null) entity.getItemStackArray(EntityEquipment)(slot) else null
    }

    def getArmorSlot(stack: ItemStack): Int = {
        if(stack.getItem != Item.getItemFromBlock(Blocks.pumpkin) && stack.getItem != Items.skull) {
            if(stack.getItem.isInstanceOf[ItemArmor]) stack.getItem.asInstanceOf[ItemArmor].armorType match {
                case 0 => return 4
                case 1 => return 3
                case 2 => return 2
                case 3 => return 1
            }
            0
        } else {
            4
        }
    }

    def setEquipmentInSlot(entity: IEntitySoulCustom, slot: Int, stack: ItemStack) {
        val equipment = entity.getItemStackArray(EntityEquipment)
        equipment(slot) = stack
        entity.setItemStackArray(EntityEquipment, equipment)
    }
}