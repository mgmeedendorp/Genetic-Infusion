package seremis.geninfusion.api.lib.model

import seremis.geninfusion.api.lib.{AttachmentPoints, CuboidTypes}
import seremis.geninfusion.api.render.cuboid.Cuboid

object ModelSpider {

    val head = new Cuboid(-4.0F, -4.0F, -8.0F, 8, 8, 8, 32, 4, false, CuboidTypes.General.Head, AttachmentPoints.Spider.Head)
    val body = new Cuboid(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0, 0, false, CuboidTypes.General.Body, AttachmentPoints.Spider.Body)
    val bodyAttachmentBack = new Cuboid(-5.0F, -4.0F, -6.0F, 10, 8, 12, 0, 12, false, CuboidTypes.Spider.BodyAttachmentBack, AttachmentPoints.Spider.BodyAttachmentBack)
    val legBackRight = new Cuboid(-15.0F, -1.0F, -1.0F, 16, 2, 2, 18, 0, false, CuboidTypes.Spider.LegBackRight, AttachmentPoints.Spider.LegRight)
    val legBackLeft = new Cuboid(-1.0F, -1.0F, -1.0F, 16, 2, 2, 18, 0, false, CuboidTypes.Spider.LegBackLeft, AttachmentPoints.Spider.LegLeft)
    val legMiddleBackRight = new Cuboid(-15.0F, -1.0F, -1.0F, 16, 2, 2, 18, 0, false, CuboidTypes.Spider.LegMiddleBackRight, AttachmentPoints.Spider.LegRight)
    val legMiddleBackLeft = new Cuboid(-1.0F, -1.0F, -1.0F, 16, 2, 2, 18, 0, false, CuboidTypes.Spider.LegMiddleBackLeft, AttachmentPoints.Spider.LegLeft)
    val legMiddleFrontRight = new Cuboid(-15.0F, -1.0F, -1.0F, 16, 2, 2, 18, 0, false, CuboidTypes.Spider.LegMiddleFrontRight, AttachmentPoints.Spider.LegRight)
    val legMiddleFrontLeft = new Cuboid(-1.0F, -1.0F, -1.0F, 16, 2, 2, 18, 0, false, CuboidTypes.Spider.LegMiddleFrontLeft, AttachmentPoints.Spider.LegLeft)
    val legFrontRight = new Cuboid(-15.0F, -1.0F, -1.0F, 16, 2, 2, 18, 0, false, CuboidTypes.Spider.LegFrontRight, AttachmentPoints.Spider.LegRight)
    val legFrontLeft = new Cuboid(-1.0F, -1.0F, -1.0F, 16, 2, 2, 18, 0, false, CuboidTypes.Spider.LegFrontLeft, AttachmentPoints.Spider.LegLeft)

    head.setRotationPoint(0.0F, 15.0F, -3.0F)
    body.setRotationPoint(0.0F, 15.0F, 0.0F)
    bodyAttachmentBack.setRotationPoint(0.0F, 15.0F, 9.0F)
    legBackRight.setRotationPoint(-4.0F, 15.0F, 2.0F)
    legBackLeft.setRotationPoint(4.0F, 15.0F, 2.0F)
    legMiddleBackRight.setRotationPoint(-4.0F, 15.0F, 1.0F)
    legMiddleBackLeft.setRotationPoint(4.0F, 15.0F, 1.0F)
    legMiddleFrontRight.setRotationPoint(-4.0F, 15.0F, 0.0F)
    legMiddleFrontLeft.setRotationPoint(4.0F, 15.0F, 0.0F)
    legFrontRight.setRotationPoint(-4.0F, 15.0F, -1.0F)
    legFrontLeft.setRotationPoint(4.0F, 15.0F, -1.0F)

    val cuboids = Array(
        head,
        body,
        bodyAttachmentBack,
        legBackRight,
        legBackLeft,
        legMiddleBackRight,
        legMiddleBackLeft,
        legMiddleFrontRight,
        legMiddleFrontLeft,
        legFrontRight,
        legFrontLeft
    )

    val texture = "textures/entity/spider/spider.png"
}
