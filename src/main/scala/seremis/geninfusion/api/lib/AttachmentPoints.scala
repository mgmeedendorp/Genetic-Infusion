package seremis.geninfusion.api.lib

import net.minecraft.util.Vec3
import seremis.geninfusion.api.lib.ModelPartTypes.General
import seremis.geninfusion.api.util.render.model.{ModelPartAttachmentPoint, ModelPartType}

object AttachmentPoints {

    def MPAP(vec3: Vec3, partTypes: Array[ModelPartType]) = new ModelPartAttachmentPoint(vec3, partTypes)
    
    object Biped {
        final val Bpd = ModelPartTypes.Biped

        final val Head = Array(
            MPAP(Vec3.createVectorHelper(4.0, 8.0, 4.0), Array(General.Neck, General.Body)),
            MPAP(Vec3.createVectorHelper(4.0, 0.0, 4.0), Array(General.Headwear))
        )

        final val Headwear = Array(
            MPAP(Vec3.createVectorHelper(4.0, 0.0, 4.0), Array(General.Head))
        )

        final val Body = Array(
            MPAP(Vec3.createVectorHelper(4.0, 0.0, 2.0), Array(General.Neck, General.Head)),
            MPAP(Vec3.createVectorHelper(0.0, 2.0, 2.0), Array(Bpd.ArmRight)),
            MPAP(Vec3.createVectorHelper(8.0, 2.0, 2.0), Array(Bpd.ArmLeft)),
            MPAP(Vec3.createVectorHelper(2.0, 12.0, 2.0), Array(Bpd.LegRight)),
            MPAP(Vec3.createVectorHelper(6.0, 12.0, 2.0), Array(Bpd.LegLeft))
        )

        final val ArmRight = Array(
            MPAP(Vec3.createVectorHelper(4.0, 2.0, 2.0), Array(General.Body))
        )

        final val ArmLeft = Array(
            MPAP(Vec3.createVectorHelper(0.0, 2.0, 2.0), Array(General.Body))
        )

        final val LegRight = Array(
            MPAP(Vec3.createVectorHelper(2.0, 0.0, 2.0), Array(General.Body))
        )

        final val LegLeft = Array(
            MPAP(Vec3.createVectorHelper(2.0, 0.0, 2.0), Array(General.Body))
        )
    }

    object Skeleton {
        final val ArmRight = Array(
            MPAP(Vec3.createVectorHelper(1.0, 2.0, 1.0), Array(General.Body))
        )

        final val ArmLeft = Array(
            MPAP(Vec3.createVectorHelper(1.0, 2.0, 1.0), Array(General.Body))
        )

        final val LegRight = Array(
            MPAP(Vec3.createVectorHelper(1.0, 0.0, 1.0), Array(General.Body))
        )

        final val LegLeft = Array(
            MPAP(Vec3.createVectorHelper(1.0, 0.0, 1.0), Array(General.Body))
        )
    }

    object Creeper {
        final val Crpr = ModelPartTypes.Creeper

        final val Head = Array(
            MPAP(Vec3.createVectorHelper(4.0, 8.0, 4.0), Array(General.Neck, General.Body)),
            MPAP(Vec3.createVectorHelper(4.0, 0.0, 4.0), Array(General.Headwear))
        )

        final val Headwear = Array(
            MPAP(Vec3.createVectorHelper(4.0, 0.0, 4.0), Array(General.Head))
        )

        final val Body = Array(
            MPAP(Vec3.createVectorHelper(4.0, 0.0, 2.0), Array(General.Neck, General.Head)),
            MPAP(Vec3.createVectorHelper(2.0, 12.0, 4.0), Array(Crpr.LegFrontRight)),
            MPAP(Vec3.createVectorHelper(2.0, 12.0, 0.0), Array(Crpr.LegHindRight)),
            MPAP(Vec3.createVectorHelper(6.0, 12.0, 4.0), Array(Crpr.LegFrontLeft)),
            MPAP(Vec3.createVectorHelper(6.0, 12.0, 0.0), Array(Crpr.LegHindLeft))
        )

        final val LegRightFront = Array(
            MPAP(Vec3.createVectorHelper(2.0, 0.0, 4.0), Array(General.Body))
        )

        final val LegRightBack = Array(
            MPAP(Vec3.createVectorHelper(2.0, 0.0, 0.0), Array(General.Body))
        )

        final val LegLeftFront = Array(
            MPAP(Vec3.createVectorHelper(2.0, 0.0, 4.0), Array(General.Body))
        )

        final val LegLeftBack = Array(
            MPAP(Vec3.createVectorHelper(2.0, 0.0, 0.0), Array(General.Body))
        )
    }

    object Spider {
        final val Spdr = ModelPartTypes.Spider

        final val Head = Array(
            MPAP(Vec3.createVectorHelper(4.0, 4.0, 8.0), Array(General.Neck, General.Body))
        )

        final val Neck = Array(
            MPAP(Vec3.createVectorHelper(3.0, 3.0, 0.0), Array(General.Head)),
            MPAP(Vec3.createVectorHelper(3.0, 3.0, 6.0), Array(General.Body)),
            MPAP(Vec3.createVectorHelper(0.0, 3.0, 1.0), Array(Spdr.LegFrontRight)),
            MPAP(Vec3.createVectorHelper(0.0, 3.0, 2.0), Array(Spdr.LegMiddleFrontRight)),
            MPAP(Vec3.createVectorHelper(0.0, 3.0, 2.0), Array(Spdr.LegMiddleHindRight)),
            MPAP(Vec3.createVectorHelper(0.0, 3.0, 3.0), Array(Spdr.LegHindRight)),
            MPAP(Vec3.createVectorHelper(6.0, 3.0, 1.0), Array(Spdr.LegFrontLeft)),
            MPAP(Vec3.createVectorHelper(6.0, 3.0, 2.0), Array(Spdr.LegMiddleFrontLeft)),
            MPAP(Vec3.createVectorHelper(6.0, 3.0, 2.0), Array(Spdr.LegMiddleHindLeft)),
            MPAP(Vec3.createVectorHelper(6.0, 3.0, 3.0), Array(Spdr.LegHindLeft))
        )

        final val Body = Array(
            MPAP(Vec3.createVectorHelper(5.0, 4.0, 0.0), Array(General.Neck, General.Body, General.Head))
        )

        final val LegRight = Array(
            MPAP(Vec3.createVectorHelper(15.0, 0.0, 1.0), Array(General.Body, General.Neck))
        )

        final val LegLeft = Array(
            MPAP(Vec3.createVectorHelper(0.0, 0.0, 1.0), Array(General.Body, General.Neck))
        )
    }
}