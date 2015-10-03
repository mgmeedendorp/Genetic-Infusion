package seremis.geninfusion.api.soul.lib

import net.minecraft.util.Vec3

object AttachmentPoints {

    object Biped {
        final val Head = Array(
            (Vec3.createVectorHelper(4.0, 8.0, 4.0), Array(ModelPartTypes.Neck, ModelPartTypes.Body))
        )

        final val Body = Array(
            (Vec3.createVectorHelper(4.0, 0.0, 2.0), Array(ModelPartTypes.Neck, ModelPartTypes.Head)),
            (Vec3.createVectorHelper(0.0, 2.0, 2.0), Array(ModelPartTypes.ArmsRight)),
            (Vec3.createVectorHelper(8.0, 2.0, 2.0), Array(ModelPartTypes.ArmsLeft)),
            (Vec3.createVectorHelper(2.0, 12.0, 2.0), Array(ModelPartTypes.LegsRight)),
            (Vec3.createVectorHelper(6.0, 12.0, 2.0), Array(ModelPartTypes.LegsLeft))
        )

        final val ArmRight = Array(
            (Vec3.createVectorHelper(3.0, 2.0, 2.0), Array(ModelPartTypes.Body))
        )

        final val ArmLeft = Array(
            (Vec3.createVectorHelper(1.0, 2.0, 2.0), Array(ModelPartTypes.Body))
        )

        final val LegRight = Array(
            (Vec3.createVectorHelper(2.0, 0.0, 2.0), Array(ModelPartTypes.Body))
        )

        final val LegLeft = Array(
            (Vec3.createVectorHelper(2.0, 0.0, 2.0), Array(ModelPartTypes.Body))
        )
    }

    object Creeper {
        final val Head = Array(
            (Vec3.createVectorHelper(4.0, 8.0, 4.0), Array(ModelPartTypes.Neck, ModelPartTypes.Body))
        )

        final val Body = Array(
            (Vec3.createVectorHelper(4.0, 0.0, 2.0), Array(ModelPartTypes.Neck, ModelPartTypes.Head)),
            (Vec3.createVectorHelper(0.0, 12.0, 0.0), Array(ModelPartTypes.LegsRight)),
            (Vec3.createVectorHelper(0.0, 12.0, 2.0), Array(ModelPartTypes.LegsRight)),
            (Vec3.createVectorHelper(8.0, 12.0, 0.0), Array(ModelPartTypes.LegsLeft)),
            (Vec3.createVectorHelper(8.0, 12.0, 0.0), Array(ModelPartTypes.LegsLeft))
        )

        final val LegRightFront = Array(
            (Vec3.createVectorHelper(0.0, 0.0, 4.0), Array(ModelPartTypes.Body))
        )

        final val LegRightBack = Array(
            (Vec3.createVectorHelper(0.0, 0.0, 0.0), Array(ModelPartTypes.Body))
        )

        final val LegLeftFront = Array(
            (Vec3.createVectorHelper(4.0, 0.0, 4.0), Array(ModelPartTypes.Body))
        )

        final val LegLeftBack = Array(
            (Vec3.createVectorHelper(4.0, 0.0, 0.0), Array(ModelPartTypes.Body))
        )
    }

    object Spider {
        final val Head = Array(
            (Vec3.createVectorHelper(4.0, 4.0, 8.0), Array(ModelPartTypes.Neck, ModelPartTypes.Body))
        )

        final val Neck = Array(
            (Vec3.createVectorHelper(3.0, 3.0, 0.0), Array(ModelPartTypes.Head)),
            (Vec3.createVectorHelper(3.0, 3.0, 6.0), Array(ModelPartTypes.Body)),
            (Vec3.createVectorHelper(0.0, 3.0, 1.0), Array(ModelPartTypes.LegsRight)),
            (Vec3.createVectorHelper(0.0, 3.0, 2.0), Array(ModelPartTypes.LegsRight)),
            (Vec3.createVectorHelper(0.0, 3.0, 2.0), Array(ModelPartTypes.LegsRight)),
            (Vec3.createVectorHelper(0.0, 3.0, 3.0), Array(ModelPartTypes.LegsRight)),
            (Vec3.createVectorHelper(6.0, 3.0, 1.0), Array(ModelPartTypes.LegsLeft)),
            (Vec3.createVectorHelper(6.0, 3.0, 2.0), Array(ModelPartTypes.LegsLeft)),
            (Vec3.createVectorHelper(6.0, 3.0, 2.0), Array(ModelPartTypes.LegsLeft)),
            (Vec3.createVectorHelper(6.0, 3.0, 3.0), Array(ModelPartTypes.LegsLeft))
        )

        final val Body = Array(
            (Vec3.createVectorHelper(5.0, 4.0, 0.0), Array(ModelPartTypes.Neck, ModelPartTypes.Head))
        )

        final val LegRight = Array(
            (Vec3.createVectorHelper(15.0, 1.0, 1.0), Array(ModelPartTypes.Body, ModelPartTypes.Neck))
        )

        final val LegLeft = Array(
            (Vec3.createVectorHelper(1.0, 1.0, 1.0), Array(ModelPartTypes.Body, ModelPartTypes.Neck))
        )
    }
}