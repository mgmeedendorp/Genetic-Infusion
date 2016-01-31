package seremis.geninfusion.api.lib

import net.minecraft.util.Vec3
import seremis.geninfusion.api.lib.CuboidTypes.General
import seremis.geninfusion.api.render.cuboid.{CuboidAttachmentPoint, CuboidType}

object AttachmentPoints {

    private def CAP(vec3: Vec3, partTypes: Array[CuboidType]) = new CuboidAttachmentPoint(vec3, partTypes)

    object Biped {
        private final val Bpd = CuboidTypes.Biped

        final val Head = Array(
            CAP(Vec3.createVectorHelper(4.0, 8.0, 4.0), Array(General.Neck, General.Body))
        )

        final val Headwear = Array(
            CAP(Vec3.createVectorHelper(4.0, 0.0, 4.0), Array(General.Head))
        )

        final val Body = Array(
            CAP(Vec3.createVectorHelper(4.0, 0.0, 2.0), Array(General.Neck, General.Head)),
            CAP(Vec3.createVectorHelper(0.0, 2.0, 2.0), Array(Bpd.ArmRight)),
            CAP(Vec3.createVectorHelper(8.0, 2.0, 2.0), Array(Bpd.ArmLeft)),
            CAP(Vec3.createVectorHelper(2.0, 12.0, 2.0), Array(Bpd.LegRight)),
            CAP(Vec3.createVectorHelper(6.0, 12.0, 2.0), Array(Bpd.LegLeft))
        )

        final val ArmRight = Array(
            CAP(Vec3.createVectorHelper(4.0, 2.0, 2.0), Array(General.Body))
        )

        final val ArmLeft = Array(
            CAP(Vec3.createVectorHelper(0.0, 2.0, 2.0), Array(General.Body))
        )

        final val LegRight = Array(
            CAP(Vec3.createVectorHelper(2.0, 0.0, 2.0), Array(General.Body))
        )

        final val LegLeft = Array(
            CAP(Vec3.createVectorHelper(2.0, 0.0, 2.0), Array(General.Body))
        )
    }

    object Skeleton {
        final val ArmRight = Array(
            CAP(Vec3.createVectorHelper(1.0, 2.0, 1.0), Array(General.Body))
        )

        final val ArmLeft = Array(
            CAP(Vec3.createVectorHelper(1.0, 2.0, 1.0), Array(General.Body))
        )

        final val LegRight = Array(
            CAP(Vec3.createVectorHelper(1.0, 0.0, 1.0), Array(General.Body))
        )

        final val LegLeft = Array(
            CAP(Vec3.createVectorHelper(1.0, 0.0, 1.0), Array(General.Body))
        )
    }

    object Creeper {
        private final val Crpr = CuboidTypes.Creeper

        final val Head = Array(
            CAP(Vec3.createVectorHelper(4.0, 8.0, 4.0), Array(General.Neck, General.Body))
        )

        final val  Headwear = Array(
            CAP(Vec3.createVectorHelper(4.0, 0.0, 4.0), Array(General.Head))
        )

        final val Body = Array(
            CAP(Vec3.createVectorHelper(4.0, 0.0, 2.0), Array(General.Neck, General.Head)),
            CAP(Vec3.createVectorHelper(2.0, 12.0, 0.0), Array(Crpr.LegRightFront)),
            CAP(Vec3.createVectorHelper(2.0, 12.0, 4.0), Array(Crpr.LegRightBack)),
            CAP(Vec3.createVectorHelper(6.0, 12.0, 0.0), Array(Crpr.LegLeftFront)),
            CAP(Vec3.createVectorHelper(6.0, 12.0, 4.0), Array(Crpr.LegLeftBack))
        )

        final val LegRightFront = Array(
            CAP(Vec3.createVectorHelper(2.0, 0.0, 4.0), Array(General.Body))
        )

        final val LegRightBack = Array(
            CAP(Vec3.createVectorHelper(2.0, 0.0, 0.0), Array(General.Body))
        )

        final val LegLeftFront = Array(
            CAP(Vec3.createVectorHelper(2.0, 0.0, 4.0), Array(General.Body))
        )

        final val LegLeftBack = Array(
            CAP(Vec3.createVectorHelper(2.0, 0.0, 0.0), Array(General.Body))
        )
    }

    object Spider {
        private final val Spdr = CuboidTypes.Spider

        final val Head = Array(
            CAP(Vec3.createVectorHelper(4.0, 4.0, 8.0), Array(General.Neck, General.Body))
        )

        final val Body = Array(
            CAP(Vec3.createVectorHelper(3.0, 3.0, 0.0), Array(General.Head)),
            CAP(Vec3.createVectorHelper(3.0, 3.0, 6.0), Array(Spdr.BodyAttachmentBack)),
            CAP(Vec3.createVectorHelper(0.0, 3.0, 1.0), Array(Spdr.LegFrontRight)),
            CAP(Vec3.createVectorHelper(0.0, 3.0, 2.0), Array(Spdr.LegMiddleFrontRight)),
            CAP(Vec3.createVectorHelper(0.0, 3.0, 2.0), Array(Spdr.LegMiddleBackRight)),
            CAP(Vec3.createVectorHelper(0.0, 3.0, 3.0), Array(Spdr.LegBackRight)),
            CAP(Vec3.createVectorHelper(6.0, 3.0, 1.0), Array(Spdr.LegFrontLeft)),
            CAP(Vec3.createVectorHelper(6.0, 3.0, 2.0), Array(Spdr.LegMiddleFrontLeft)),
            CAP(Vec3.createVectorHelper(6.0, 3.0, 2.0), Array(Spdr.LegMiddleBackLeft)),
            CAP(Vec3.createVectorHelper(6.0, 3.0, 3.0), Array(Spdr.LegBackLeft))
        )

        final val BodyAttachmentBack = Array(
            CAP(Vec3.createVectorHelper(5.0, 4.0, 0.0), Array(General.Neck))
        )

        final val LegRight = Array(
            CAP(Vec3.createVectorHelper(15.0, 0.0, 1.0), Array(General.Body))
        )

        final val LegLeft = Array(
            CAP(Vec3.createVectorHelper(0.0, 0.0, 1.0), Array(General.Body))
        )
    }
}