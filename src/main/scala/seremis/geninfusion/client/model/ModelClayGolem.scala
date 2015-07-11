package seremis.geninfusion.client.model

import net.minecraft.client.model.ModelBase
import seremis.geninfusion.api.util.render.model.{Model, ModelPart}
import seremis.geninfusion.entity.EntityClayGolem
import seremis.geninfusion.util.UtilModel

class ModelClayGolem extends ModelBase {

    val textureSize = 128
    textureWidth = textureSize
    textureHeight = textureSize

    val head = new ModelPart(this)
    head.setTextureSize(textureWidth, textureHeight)
    head.setRotationPoint(0.0F, -7.0F, -2.0F)
    head.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, 0.0F)
    head.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, 0.0F)

    val body = new ModelPart(this)
    body.setTextureSize(textureWidth, textureHeight)
    body.setRotationPoint(0.0F, -7.0F, 0.0F)
    body.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, 0.0F)
    body.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, 0.5F)

    val rightArm = new ModelPart(this)
    rightArm.setTextureSize(textureWidth, textureHeight)
    rightArm.setRotationPoint(0.0F, -7.0F, 0.0F)
    rightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, 0.0F)

    val leftArm = new ModelPart(this)
    leftArm.setTextureSize(textureWidth, textureHeight)
    leftArm.setRotationPoint(0.0F, -7.0F, 0.0F)
    leftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, 0.0F)

    val leftLeg = new ModelPart(this)
    leftLeg.setTextureSize(textureWidth, textureHeight)
    leftLeg.setRotationPoint(-4.0F, 11.0F, 0.0F)
    leftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, 0.0F)

    val rightLeg = new ModelPart(this)
    rightLeg.setTextureSize(textureWidth, textureHeight)
    rightLeg.mirror = true
    rightLeg.setRotationPoint(5.0F, 11.0F, 0.0F)
    rightLeg.setTextureOffset(60, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, 0.0F)

    val modelGolem = new Model(Array(head, body, rightArm, leftArm, leftLeg, rightLeg))

    def render(golem: EntityClayGolem) {
        if(golem.firstRenderTick) {
            golem.currentRenderModel = Some(modelGolem)
            golem.firstRenderTick = false
        }
        if(golem.isWaitingAfterTransformation) {
            golem.currentRenderModel = golem.getTransformationGoalModel
        }

        golem.currentRenderModel.get.render

        if(golem.isTransformating) {
            animateTransformation(golem)
        }
    }

    def animateTransformation(golem: EntityClayGolem) {
        var modelTo = golem.getTransformationGoalModel.get
//        modelTo = new Model(ModelPart.getModelPartsFromModel(new ModelZombie(), null))

        golem.currentRenderModel = Some(UtilModel.morphModel(modelGolem, modelTo, golem.getMaxTransformationTimer, golem.getTransformationTimer))
    }
}
