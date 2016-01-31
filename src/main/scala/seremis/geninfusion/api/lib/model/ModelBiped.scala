package seremis.geninfusion.api.lib.model

import seremis.geninfusion.api.lib.{AttachmentPoints, CuboidTypes}
import seremis.geninfusion.api.render.cuboid.Cuboid

object ModelBiped {
    val head = new Cuboid(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0, 0, false, CuboidTypes.General.Head, AttachmentPoints.Biped.Head)
    val body = new Cuboid(-4.0F, 0.0F, -2.0F, 8, 12, 4, 16, 16, false, CuboidTypes.General.Body, AttachmentPoints.Biped.Body)
    val rightArm = new Cuboid(-3.0F, -2.0F, -2.0F, 4, 12, 4, 40, 16, false, CuboidTypes.Biped.ArmRight, AttachmentPoints.Biped.ArmRight)
    val leftArm = new Cuboid(-1.0F, -2.0F, -2.0F, 4, 12, 4, 40, 16, true, CuboidTypes.Biped.ArmLeft, AttachmentPoints.Biped.ArmLeft)
    val rightLeg = new Cuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0, 16, false, CuboidTypes.Biped.LegRight, AttachmentPoints.Biped.LegRight)
    val leftLeg = new Cuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0, 16, true, CuboidTypes.Biped.LegLeft, AttachmentPoints.Biped.LegLeft)

    head.setRotationPoint(0.0F, 0.0F, 0.0F)
    body.setRotationPoint(0.0F, 0.0F, 0.0F)
    rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F)
    leftArm.setRotationPoint(5.0F, 2.0F, 0.0F)
    rightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F)
    leftLeg.setRotationPoint(1.9F, 12.0F, 0.0F)

    val cuboids = Array(head, body, rightArm, leftArm, rightLeg, leftLeg)

    object Child {
        val childHead = head.getScaledCuboid(0.75F)
        val childBody = body.getScaledCuboid(0.5F)
        val childRightArm = rightArm.getScaledCuboid(0.5F)
        val childLeftArm = leftArm.getScaledCuboid(0.5F)
        val childRightLeg = rightLeg.getScaledCuboid(0.5F)
        val childLeftLeg = leftLeg.getScaledCuboid(0.5F)

        childHead.rotationPointY += 16.0F
        childBody.rotationPointY += 24.0F
        childRightArm.rotationPointY += 24.0F
        childLeftArm.rotationPointY += 24.0F
        childRightLeg.rotationPointY += 24.0F
        childLeftLeg.rotationPointY += 24.0F

        val cuboids = Array(childHead, childBody, childRightArm, childLeftArm, childRightLeg, childLeftLeg)
    }
}
