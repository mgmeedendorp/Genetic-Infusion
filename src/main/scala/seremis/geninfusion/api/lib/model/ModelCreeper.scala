package seremis.geninfusion.api.lib.model

import seremis.geninfusion.api.lib.{AttachmentPoints, CuboidTypes}
import seremis.geninfusion.api.render.cuboid.Cuboid

object ModelCreeper {

    val head = new Cuboid(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0, 0, false, CuboidTypes.General.Head, AttachmentPoints.Creeper.Head)
    val body = new Cuboid(-4.0F, 0.0F, -2.0F, 8, 12, 4, 16, 16, false, CuboidTypes.General.Body, AttachmentPoints.Creeper.Body)
    val legRightBack = new Cuboid(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0, 16, false, CuboidTypes.Creeper.LegRightBack, AttachmentPoints.Creeper.LegRightBack)
    val legLeftBack = new Cuboid(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0, 16, false, CuboidTypes.Creeper.LegLeftBack, AttachmentPoints.Creeper.LegRightBack)
    val legRightFront = new Cuboid(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0, 16, false, CuboidTypes.Creeper.LegRightFront, AttachmentPoints.Creeper.LegRightFront)
    val legLeftFront = new Cuboid(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0, 16, false, CuboidTypes.Creeper.LegLeftFront, AttachmentPoints.Creeper.LegLeftFront)

    val cuboids = Array(head, body, legRightBack, legLeftBack, legRightFront, legLeftFront)
    val texture = "textures/entity/creeper/creeper.png"
}
