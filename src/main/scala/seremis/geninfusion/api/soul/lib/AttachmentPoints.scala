package seremis.geninfusion.api.soul.lib

import net.minecraft.util.Vec3
import seremis.geninfusion.api.util.render.model.ModelPartAttachmentPoint

object AttachmentPoints {

    object Biped {
        final val Head = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(4.0, 8.0, 4.0), Array(ModelPartTypes.Neck, ModelPartTypes.Body))
        )

        final val Body = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(4.0, 0.0, 2.0), Array(ModelPartTypes.Neck, ModelPartTypes.Head)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(0.0, 2.0, 2.0), Array(ModelPartTypes.ArmsRight)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(8.0, 2.0, 2.0), Array(ModelPartTypes.ArmsLeft)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(2.0, 12.0, 2.0), Array(ModelPartTypes.LegsRight)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(6.0, 12.0, 2.0), Array(ModelPartTypes.LegsLeft))
        )

        final val ArmRight = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(3.0, 2.0, 2.0), Array(ModelPartTypes.Body))
        )

        final val ArmLeft = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(1.0, 2.0, 2.0), Array(ModelPartTypes.Body))
        )

        final val LegRight = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(2.0, 0.0, 2.0), Array(ModelPartTypes.Body))
        )

        final val LegLeft = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(2.0, 0.0, 2.0), Array(ModelPartTypes.Body))
        )
    }

    object Skeleton {
        final val ArmRight = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(1.0, 2.0, 1.0), Array(ModelPartTypes.Body))
        )

        final val ArmLeft = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(1.0, 2.0, 1.0), Array(ModelPartTypes.Body))
        )

        final val LegRight = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(1.0, 0.0, 1.0), Array(ModelPartTypes.Body))
        )

        final val LegLeft = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(1.0, 0.0, 1.0), Array(ModelPartTypes.Body))
        )
    }

    object Creeper {
        final val Head = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(4.0, 8.0, 4.0), Array(ModelPartTypes.Neck, ModelPartTypes.Body))
        )

        final val Body = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(4.0, 0.0, 2.0), Array(ModelPartTypes.Neck, ModelPartTypes.Head)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(0.0, 12.0, 0.0), Array(ModelPartTypes.LegsRight)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(0.0, 12.0, 0.0), Array(ModelPartTypes.LegsRight)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(8.0, 12.0, 0.0), Array(ModelPartTypes.LegsLeft)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(8.0, 12.0, 0.0), Array(ModelPartTypes.LegsLeft))
        )

        final val LegRightFront = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(0.0, 0.0, 4.0), Array(ModelPartTypes.Body))
        )

        final val LegRightBack = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(0.0, 0.0, 0.0), Array(ModelPartTypes.Body))
        )

        final val LegLeftFront = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(4.0, 0.0, 4.0), Array(ModelPartTypes.Body))
        )

        final val LegLeftBack = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(4.0, 0.0, 0.0), Array(ModelPartTypes.Body))
        )
    }

    object Spider {
        final val Head = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(4.0, 4.0, 8.0), Array(ModelPartTypes.Neck, ModelPartTypes.Body))
        )

        final val Neck = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(3.0, 3.0, 0.0), Array(ModelPartTypes.Head)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(3.0, 3.0, 6.0), Array(ModelPartTypes.Body)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(0.0, 3.0, 1.0), Array(ModelPartTypes.LegsRight)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(0.0, 3.0, 2.0), Array(ModelPartTypes.LegsRight)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(0.0, 3.0, 2.0), Array(ModelPartTypes.LegsRight)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(0.0, 3.0, 3.0), Array(ModelPartTypes.LegsRight)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(6.0, 3.0, 1.0), Array(ModelPartTypes.LegsLeft)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(6.0, 3.0, 2.0), Array(ModelPartTypes.LegsLeft)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(6.0, 3.0, 2.0), Array(ModelPartTypes.LegsLeft)),
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(6.0, 3.0, 3.0), Array(ModelPartTypes.LegsLeft))
        )

        final val Body = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(5.0, 4.0, 0.0), Array(ModelPartTypes.Neck, ModelPartTypes.Body, ModelPartTypes.Head))
        )

        final val LegRight = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(15.0, 1.0, 1.0), Array(ModelPartTypes.Body, ModelPartTypes.Neck))
        )

        final val LegLeft = Array(
            new ModelPartAttachmentPoint(Vec3.createVectorHelper(1.0, 1.0, 1.0), Array(ModelPartTypes.Body, ModelPartTypes.Neck))
        )
    }
}