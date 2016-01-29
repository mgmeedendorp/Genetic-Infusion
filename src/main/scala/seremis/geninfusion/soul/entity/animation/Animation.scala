package seremis.geninfusion.soul.entity.animation

import seremis.geninfusion.api.lib.CuboidTypes
import seremis.geninfusion.api.render.Model
import seremis.geninfusion.api.render.cuboid.Cuboid
import seremis.geninfusion.api.soul.{IAnimation, IEntitySoulCustom}
import seremis.geninfusion.api.util.UtilModel

abstract class Animation extends IAnimation {

    final val PI = Math.PI.asInstanceOf[Float]

    def getModel(entity: IEntitySoulCustom): Model = UtilModel.getModel(entity)

    def getModelLeftLegs(entity: IEntitySoulCustom): Option[Array[Cuboid]] = getModel(entity).getCuboidsWithTag(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Left)
    def getModelRightLegs(entity: IEntitySoulCustom): Option[Array[Cuboid]] = getModel(entity).getCuboidsWithTag(CuboidTypes.Tags.Leg, CuboidTypes.Tags.Right)

    def getModelLegs(entity: IEntitySoulCustom): Option[Array[Cuboid]] = getModel(entity).getCuboidsWithTag(CuboidTypes.Tags.Leg)

    def getModelLeftArms(entity: IEntitySoulCustom): Option[Array[Cuboid]] = getModel(entity).getCuboidsWithTag(CuboidTypes.Tags.Arm, CuboidTypes.Tags.Left)
    def getModelRightArms(entity: IEntitySoulCustom): Option[Array[Cuboid]] = getModel(entity).getCuboidsWithTag(CuboidTypes.Tags.Arm, CuboidTypes.Tags.Right)

    def getModelArms(entity: IEntitySoulCustom): Option[Array[Cuboid]] = getModel(entity).getCuboidsWithTag(CuboidTypes.Tags.Arm)

    def armsHorizontal(entity: IEntitySoulCustom): Boolean = UtilModel.armsHorizontal(getModel(entity))

    def getModelBody(entity: IEntitySoulCustom): Option[Array[Cuboid]] = getModel(entity).getCuboidsWithTag(CuboidTypes.Tags.Body)

    def getModelHead(entity: IEntitySoulCustom): Option[Array[Cuboid]] = getModel(entity).getCuboidsWithTag(CuboidTypes.Tags.Head)

    def getModelLeftWings(entity: IEntitySoulCustom): Option[Array[Cuboid]] = getModel(entity).getCuboidsWithTag(CuboidTypes.Tags.Wing, CuboidTypes.Tags.Left)
    def getModelRightWings(entity: IEntitySoulCustom): Option[Array[Cuboid]] = getModel(entity).getCuboidsWithTag(CuboidTypes.Tags.Wing, CuboidTypes.Tags.Right)

    def getModelWings(entity: IEntitySoulCustom): Option[Array[Cuboid]] = getModel(entity).getCuboidsWithTag(CuboidTypes.Tags.Wing)
}


