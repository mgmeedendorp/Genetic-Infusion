package seremis.geninfusion.api.lib

import seremis.geninfusion.api.render.cuboid.CuboidType

object CuboidTypes {

    private final val ID = Genes.ID

    object Tags {
        final val Head = ID + ".cuboidTypeType.head"
        final val Neck = ID + ".cuboidTypeType.neck"
        final val Body = ID + ".cuboidTypeType.body"
        final val Leg = ID + ".cuboidTypeType.leg"
        final val Arm = ID + ".cuboidTypeType.arm"
        final val Wing = ID + ".cuboidTypeType.wing"
        //Any attachment to body that is not a limb and doesn't have any other AttachmentPoints
        final val BodyAttachment = ID + ".cuboidTypeTag.bodyAttachment"

        final val Left = ID + ".cuboidTypeTag.left"
        final val Right = ID + ".cuboidTypeTag.right"
        final val Front = ID + ".cuboidTypeTag.front"
        final val Hind = ID + ".cuboidTypeTag.back"
        final val Middle = ID + ".cuboidTypeTag.middle"
    }

    object General {
        final val Head = new CuboidType(Tags.Head)
        final val Neck = new CuboidType(Tags.Neck)
        final val Body = new CuboidType(Tags.Body)
    }

    object Biped {
        final val ArmRight = new CuboidType(Array(Tags.Arm, Tags.Right))
        final val ArmLeft = new CuboidType(Array(Tags.Arm, Tags.Left))
        final val LegRight = new CuboidType(Array(Tags.Leg, Tags.Right))
        final val LegLeft = new CuboidType(Array(Tags.Leg, Tags.Left))
    }

    object Creeper {
        final val LegRightFront = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Right, CuboidTypes.Tags.Front))
        final val LegRightBack = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Right, CuboidTypes.Tags.Hind))
        final val LegLeftFront = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Left, CuboidTypes.Tags.Front))
        final val LegLeftBack = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Left, CuboidTypes.Tags.Hind))
    }

    object Spider {
        final val BodyAttachmentBack = new CuboidType(Array(CuboidTypes.Tags.BodyAttachment, CuboidTypes.Tags.Hind))

        final val LegFrontRight = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Right, CuboidTypes.Tags.Front))
        final val LegMiddleFrontRight = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Right, CuboidTypes.Tags.Front, CuboidTypes.Tags.Middle))
        final val LegMiddleBackRight = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Right, CuboidTypes.Tags.Hind, CuboidTypes.Tags.Middle))
        final val LegBackRight = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Right, CuboidTypes.Tags.Hind))
        final val LegFrontLeft = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Left, CuboidTypes.Tags.Front))
        final val LegMiddleFrontLeft = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Left, CuboidTypes.Tags.Front, CuboidTypes.Tags.Middle))
        final val LegMiddleBackLeft = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Left, CuboidTypes.Tags.Hind, CuboidTypes.Tags.Middle))
        final val LegBackLeft = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Left, CuboidTypes.Tags.Hind))
    }
}
