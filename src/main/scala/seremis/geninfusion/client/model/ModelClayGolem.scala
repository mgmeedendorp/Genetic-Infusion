package seremis.geninfusion.client.model

import net.minecraft.client.model.ModelBase
import net.minecraft.entity.EntityLiving
import org.lwjgl.opengl.GL11
import seremis.geninfusion.api.lib.CuboidTypes
import seremis.geninfusion.api.render.Model
import seremis.geninfusion.api.render.cuboid.Cuboid
import seremis.geninfusion.api.util.UtilModel
import seremis.geninfusion.entity.EntityClayGolem

class ModelClayGolem extends ModelBase {

    val textureSize = 128
    textureWidth = textureSize
    textureHeight = textureSize

    val head = new Cuboid(-4.0F, -12.0F, -5.5F, 8, 10, 8, CuboidTypes.General.Head, Array())
    head.setRotationPoint(0.0F, -7.0F, -2.0F)

    val nose = new Cuboid(-1.0F, -5.0F, -7.5F, 2, 4, 2, 23, 0, CuboidTypes.General.Head, Array())
    nose.setRotationPoint(0.0F, -7.0F, -2.0F)

    val body1 = new Cuboid(-9.0F, -2.0F, -6.0F, 18, 12, 11, 0, 40, CuboidTypes.General.Body, Array())
    val body2 = new Cuboid(-4.5F, 10.0F, -3.0F, 9, 5, 6, 0, 70, CuboidTypes.General.Body, Array())
    body1.setRotationPoint(0.0F, -7.0F, 0.0F)
    body2.setRotationPoint(0.0F, -7.0F, 0.0F)

    val rightArm = new Cuboid(-13.0F, -2.5F, -3.0F, 4, 30, 6, 60, 21, CuboidTypes.Biped.ArmRight, Array())
    rightArm.setRotationPoint(0.0F, -7.0F, 0.0F)

    val leftArm = new Cuboid(9.0F, -2.5F, -3.0F, 4, 30, 6, 60, 58, CuboidTypes.Biped.ArmLeft, Array())
    leftArm.setRotationPoint(0.0F, -7.0F, 0.0F)

    val rightLeg = new Cuboid(-3.5F, -3.0F, -3.0F, 6, 16, 5, 60, 0, CuboidTypes.Biped.ArmRight, Array())
    rightLeg.setRotationPoint(5.0F, 11.0F, 0.0F)
    rightLeg.mirror = true

    val leftLeg = new Cuboid(-3.5F, -3.0F, -3.0F, 6, 16, 5, 37, 0, CuboidTypes.Biped.ArmLeft, Array())
    leftLeg.setRotationPoint(-4.0F, 11.0F, 0.0F)

    val modelGolem = new Model(Array(head, nose, body1, body2, rightArm, leftArm, leftLeg, rightLeg))

    def render(golem: EntityClayGolem) {
        if(golem.firstRenderTick) {
            golem.currentRenderModel = Some(modelGolem)
            golem.firstRenderTick = false
        }
        if(golem.isWaitingAfterTransformation) {
            golem.currentRenderModel = golem.getTransformationGoalModel
        }

        if((golem.isTransforming || golem.isWaitingAfterTransformation) && golem.getTransformationGoal.get.asInstanceOf[EntityLiving].isChild) {
            GL11.glPushMatrix()

            if(golem.currentRenderModel.get.getCuboidsWithTag(CuboidTypes.Tags.Head).nonEmpty) {
                GL11.glPushMatrix()

                val headScale = 1.0F + ((0.75F - 1.0F) / golem.getMaxTransformationTimer) * golem.getTransformationTimer
                GL11.glScalef(headScale, headScale, headScale)

                val headTranslateModifier = 16.0F / golem.getMaxTransformationTimer * golem.getTransformationTimer
                GL11.glTranslatef(0.0F, 0.0625F * headTranslateModifier, 0.0F)

                new Model(golem.currentRenderModel.get.getCuboidsWithTag(CuboidTypes.Tags.Head).get).render()
                GL11.glPopMatrix()
            }

            val scale = 1.0F + ((0.5F - 1.0F) / golem.getMaxTransformationTimer) * golem.getTransformationTimer
            GL11.glScalef(scale, scale, scale)

            val translateModifier = 24.0F / golem.getMaxTransformationTimer * golem.getTransformationTimer
            GL11.glTranslatef(0.0F, 0.0625F * translateModifier, 0.0F)

            for(cuboid <- golem.currentRenderModel.get.getCuboids) {
                if(!cuboid.cuboidType.tags.contains(CuboidTypes.Tags.Head)) {
                    cuboid.render()
                }
            }

            GL11.glPopMatrix()
        } else {
            golem.currentRenderModel.get.render()
        }

        if(golem.isTransforming) {
            animateTransformation(golem)
        }
    }

    def animateTransformation(golem: EntityClayGolem) {
        val modelTo = golem.getTransformationGoalModel.get

        golem.currentRenderModel = Some(UtilModel.morphModel(modelGolem, modelTo, golem.getMaxTransformationTimer, golem.getTransformationTimer))
    }
}
