package seremis.geninfusion.api.lib.model

import seremis.geninfusion.api.lib.{AttachmentPoints, CuboidTypes}
import seremis.geninfusion.api.render.cuboid.Cuboid

object ModelSkeleton {
    val rightArm = new Cuboid(-1.0F, -2.0F, -1.0F, 2, 12, 2, 40, 16, false, CuboidTypes.Biped.ArmRight, AttachmentPoints.Biped.ArmRight)
    val leftArm = new Cuboid(-1.0F, -2.0F, -1.0F, 2, 12, 2, 40, 16, true, CuboidTypes.Biped.ArmLeft, AttachmentPoints.Biped.ArmLeft)
    val rightLeg = new Cuboid(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0, 16, false, CuboidTypes.Biped.LegRight, AttachmentPoints.Biped.LegRight)
    val leftLeg = new Cuboid(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0, 16, true, CuboidTypes.Biped.LegLeft, AttachmentPoints.Biped.LegLeft)

    rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F)
    leftArm.setRotationPoint(5.0F, 2.0F, 0.0F)
    rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F)
    leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F)

    val cuboids = Array(ModelBiped.head, ModelBiped.body, rightArm, leftArm, rightLeg, leftLeg)
    val texture = "textures/entity/skeleton/skeleton.png"
}
