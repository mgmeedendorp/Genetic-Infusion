package seremis.geninfusion.api.lib

import seremis.geninfusion.api.util.render.model.ModelPartType

object ModelPartTypes {

    private final val ID = Genes.ID

    object Names {
        final val Head = ID + ".modelPartTypeName.head"
        final val Neck = ID + ".modelPartTypeName.neck"
        final val Headwear = ID + ".modelPartTypeName.headwear"
        final val Body = ID + ".modelPartTypeName.body"
        final val Cloak = ID + ".modelPartTypeName.cloak"
        final val Ears = ID + ".modelPartTypeName.ears"
        final val Leg = ID + ".modelPartTypeName.leg"
        final val Arm = ID + ".modelPartTypeName.arm"
        final val Wing = ID + ".modelPartTypeName.wing"
    }

    object Tags {
        final val Left = ID + ".modelPartTypeTag.left"
        final val Right = ID + ".modelPartTypeTag.right"
        final val Front = ID + ".modelPartTypeTag.front"
        final val Hind = ID + ".modelPartTypeTag.back"
        final val Middle = ID + ".modelPartTypeTag.middle"
    }

    object General {
        final val Headwear = new ModelPartType(Names.Headwear)
        final val Head = new ModelPartType(Names.Head)
        final val Neck = new ModelPartType(Names.Neck)
        final val Body = new ModelPartType(Names.Body)
    }

    object Biped {
        final val ArmRight = new ModelPartType(Names.Arm, Some(Array(Tags.Right)))
        final val ArmLeft = new ModelPartType(Names.Arm, Some(Array(Tags.Left)))
        final val LegRight = new ModelPartType(Names.Leg, Some(Array(Tags.Right)))
        final val LegLeft = new ModelPartType(ModelPartTypes.Names.Leg, Some(Array(ModelPartTypes.Tags.Left)))
    }

    object Creeper {
        final val LegFrontRight = new ModelPartType(ModelPartTypes.Names.Leg, Some(Array(ModelPartTypes.Tags.Right, ModelPartTypes.Tags.Front)))
        final val LegHindRight = new ModelPartType(ModelPartTypes.Names.Leg, Some(Array(ModelPartTypes.Tags.Right, ModelPartTypes.Tags.Hind)))
        final val LegFrontLeft = new ModelPartType(ModelPartTypes.Names.Leg, Some(Array(ModelPartTypes.Tags.Left, ModelPartTypes.Tags.Front)))
        final val LegHindLeft = new ModelPartType(ModelPartTypes.Names.Leg, Some(Array(ModelPartTypes.Tags.Left, ModelPartTypes.Tags.Hind)))
    }

    object Spider {
        final val LegFrontRight = new ModelPartType(ModelPartTypes.Names.Leg, Some(Array(ModelPartTypes.Tags.Right, ModelPartTypes.Tags.Front)))
        final val LegMiddleFrontRight = new ModelPartType(ModelPartTypes.Names.Leg, Some(Array(ModelPartTypes.Tags.Right, ModelPartTypes.Tags.Front, ModelPartTypes.Tags.Middle)))
        final val LegMiddleHindRight = new ModelPartType(ModelPartTypes.Names.Leg, Some(Array(ModelPartTypes.Tags.Right, ModelPartTypes.Tags.Hind, ModelPartTypes.Tags.Middle)))
        final val LegHindRight = new ModelPartType(ModelPartTypes.Names.Leg, Some(Array(ModelPartTypes.Tags.Right, ModelPartTypes.Tags.Hind)))
        final val LegFrontLeft = new ModelPartType(ModelPartTypes.Names.Leg, Some(Array(ModelPartTypes.Tags.Left, ModelPartTypes.Tags.Front)))
        final val LegMiddleFrontLeft = new ModelPartType(ModelPartTypes.Names.Leg, Some(Array(ModelPartTypes.Tags.Left, ModelPartTypes.Tags.Front, ModelPartTypes.Tags.Middle)))
        final val LegMiddleHindLeft = new ModelPartType(ModelPartTypes.Names.Leg, Some(Array(ModelPartTypes.Tags.Left, ModelPartTypes.Tags.Hind, ModelPartTypes.Tags.Middle)))
        final val LegHindLeft = new ModelPartType(ModelPartTypes.Names.Leg, Some(Array(ModelPartTypes.Tags.Left, ModelPartTypes.Tags.Hind)))
    }
}
