package seremis.geninfusion.soul.`trait`

import cpw.mods.fml.relauncher.{Side, SideOnly}
import seremis.geninfusion.api.soul._
import seremis.geninfusion.api.soul.lib.VariableLib._

import scala.collection.mutable.ListBuffer


class TraitAnimation extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        if(entity.getWorld_I.isRemote) {
            val registry = SoulHelper.animationRegistry

            val animations = registry.getAnimations
            val possibleAnimations: ListBuffer[String] = ListBuffer()

            for(animation <- animations if animation.canAnimateEntity(entity)) {
                possibleAnimations += registry.getName(animation)
            }

            entity.setStringArray(EntityPossibleAnimations, possibleAnimations.to[Array])
        }
    }

    @SideOnly(Side.CLIENT)
    override def render(entity: IEntitySoulCustom, timeModifier: Float, limbSwing: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float) {
        val registry = SoulHelper.animationRegistry

        val possibleAnimationsArray = entity.getStringArray(EntityPossibleAnimations)

        if(possibleAnimationsArray != null && possibleAnimationsArray.length != 0) {
            val possibleAnimations = possibleAnimationsArray.to[ListBuffer]

            val activeAnimationArray = entity.getStringArray(EntityActiveAnimations)
            val activeAnimations = if(activeAnimationArray != null && activeAnimationArray.length != 0) activeAnimationArray.to[ListBuffer] else new ListBuffer[String]()

            val pendingAnimationArray = entity.getStringArray(EntityPendingAnimations)
            val pendingAnimations = if(pendingAnimationArray != null && pendingAnimationArray.length != 0) pendingAnimationArray.to[ListBuffer] else new ListBuffer[String]()

            for(name <- possibleAnimations) {
                val animation = registry.getAnimation(name)

                if(activeAnimations.contains(name)) {
                    if(animation.continueAnimation(entity)) {
                        animation.animate(entity, timeModifier, limbSwing, specialRotation, rotationYawHead, rotationPitch, scale)
                    } else {
                        animation.stopAnimation(entity)
                        activeAnimations -= name
                    }
                } else {
                    if(pendingAnimations.contains(name)) {
                        val sameTypeName = getSameTypeAnimationActive(activeAnimations, name)

                        if(sameTypeName == null) {
                            animation.startAnimation(entity)
                            activeAnimations += name
                        }
                    } else if(animation.shouldStartAnimation(entity)) {
                        val sameTypeName = getSameTypeAnimationActive(activeAnimations, name)
                        val sameTypeAnimation = if(sameTypeName != null) registry.getAnimation(sameTypeName) else null

                        if(sameTypeName != null && sameTypeAnimation.canBeInterrupted(entity)) {
                            sameTypeAnimation.stopAnimation(entity)
                            activeAnimations -= sameTypeName
                        } else if(sameTypeName != null) {
                            pendingAnimations += name
                        }

                        if(!activeAnimations.contains(sameTypeName)) {
                            animation.startAnimation(entity)
                            activeAnimations += name
                        }
                    }
                }
            }
            entity.setStringArray(EntityActiveAnimations, activeAnimations.to[Array])
            entity.setStringArray(EntityPendingAnimations, pendingAnimations.to[Array])
        }
    }

    def getSameTypeAnimationActive(activeAnimations: ListBuffer[String], animation: String): String = {
        val registry = SoulHelper.animationRegistry

        if(registry.getAnimation(animation).getAnimationType != EnumAnimationType.Undefined) {
            for(name <- activeAnimations if registry.getAnimation(name).getAnimationType == registry.getAnimation(animation).getAnimationType) {
                return name
            }
        }
        null
    }
}