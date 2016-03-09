package seremis.geninfusion.soul.entity.ai

import java.util.{Collections, Comparator}

import net.minecraft.entity.ai.EntityAINearestAttackableTarget
import net.minecraft.entity.{Entity, EntityLivingBase}
import seremis.geninfusion.api.soul.IEntitySoulCustom

class EntityAINearestAttackableTargetCustom(entity: IEntitySoulCustom, targetClass: Class[_], targetChance: Int, targetVisible: Boolean, nearbyOnly: Boolean, entitySelector: IEntitySelector) extends EntityAITargetCustom(entity, targetVisible, nearbyOnly) {
    setMutexBits(1)

    private val sorter = new EntityAINearestAttackableTarget.Sorter(living)

    private val targetEntitySelector = new IEntitySelector() {
        def isEntityApplicable(entity: Entity): Boolean = entity.isInstanceOf[EntityLivingBase] && (!(entitySelector != null && !entitySelector.isEntityApplicable(entity)) && EntityAINearestAttackableTargetCustom.this.isSuitableTarget(entity.asInstanceOf[EntityLivingBase], false))
    }

    private var targetEntity: EntityLivingBase = null

    def this(entity: IEntitySoulCustom, targetClass: Class[_], targetChance: Int, targetVisible: Boolean) {
        this(entity, targetClass, targetChance, targetVisible, false, null)
    }


    def this(entity: IEntitySoulCustom, targetClass: Class[_], targetChance: Int, targetVisible: Boolean, nearbyOnly: Boolean) {
        this(entity, targetClass, targetChance, targetVisible, nearbyOnly, null)
    }

    override def shouldExecute(): Boolean = {
        if (targetChance <= 0 || living.getRNG.nextInt(targetChance) == 0) {
            val followRange = getFollowRange
            val list = living.worldObj.selectEntitiesWithinAABB(this.targetClass, this.living.boundingBox.expand(followRange, 4.0D, followRange), targetEntitySelector).asInstanceOf[java.util.List[Any]]

            Collections.sort(list, sorter.asInstanceOf[Comparator[Any]])

            if (list.nonEmpty) {
                targetEntity = list.get(0).asInstanceOf[EntityLivingBase]
                return true
            }
        }
        false
    }

    override def startExecuting() {
        living.setAttackTarget(targetEntity)
        super.startExecuting()
    }
}
