package seremis.geninfusion.api.lib

import seremis.geninfusion.api.render.cuboid.CuboidType

object CuboidTypes {

    private final val ID = Genes.ID

    object Tags {
        final val Head = ID + ".cuboidTypeName.head"
        final val Neck = ID + ".cuboidTypeName.neck"
        final val Headwear = ID + ".cuboidTypeName.headwear"
        final val Body = ID + ".cuboidTypeName.body"
        final val Cloak = ID + ".cuboidTypeName.cloak"
        final val Ears = ID + ".cuboidTypeName.ears"
        final val Leg = ID + ".cuboidTypeName.leg"
        final val Arm = ID + ".cuboidTypeName.arm"
        final val Wing = ID + ".cuboidTypeName.wing"

        final val Left = ID + ".cuboidTypeTag.left"
        final val Right = ID + ".cuboidTypeTag.right"
        final val Front = ID + ".cuboidTypeTag.front"
        final val Hind = ID + ".cuboidTypeTag.back"
        final val Middle = ID + ".cuboidTypeTag.middle"
    }

    object General {
        final val Headwear = new CuboidType(Tags.Headwear)
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
        final val LegFrontRight = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Right, CuboidTypes.Tags.Front))
        final val LegHindRight = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Right, CuboidTypes.Tags.Hind))
        final val LegFrontLeft = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Left, CuboidTypes.Tags.Front))
        final val LegHindLeft = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Left, CuboidTypes.Tags.Hind))
    }

    object Spider {
        final val LegFrontRight = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Right, CuboidTypes.Tags.Front))
        final val LegMiddleFrontRight = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Right, CuboidTypes.Tags.Front, CuboidTypes.Tags.Middle))
        final val LegMiddleHindRight = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Right, CuboidTypes.Tags.Hind, CuboidTypes.Tags.Middle))
        final val LegHindRight = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Right, CuboidTypes.Tags.Hind))
        final val LegFrontLeft = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Left, CuboidTypes.Tags.Front))
        final val LegMiddleFrontLeft = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Left, CuboidTypes.Tags.Front, CuboidTypes.Tags.Middle))
        final val LegMiddleHindLeft = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Left, CuboidTypes.Tags.Hind, CuboidTypes.Tags.Middle))
        final val LegHindLeft = new CuboidType(Array(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Left, CuboidTypes.Tags.Hind))
    }
}
