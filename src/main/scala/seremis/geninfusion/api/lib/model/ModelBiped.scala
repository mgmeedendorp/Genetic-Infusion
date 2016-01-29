package seremis.geninfusion.api.lib.model

import seremis.geninfusion.api.lib.{AttachmentPoints, CuboidTypes}
import seremis.geninfusion.api.render.Model
import seremis.geninfusion.api.render.cuboid.Cuboid

object ModelBiped {
    val head = new Cuboid(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0, 0, CuboidTypes.General.Head, AttachmentPoints.Biped.Head)

    val cuboids = Array(head)
}

class ModelBiped extends Model(ModelBiped.cuboids) {
    setTextureLocation("minecraft:textures/entity/zombie/zombie.png")
}
