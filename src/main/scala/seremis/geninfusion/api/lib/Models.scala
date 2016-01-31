package seremis.geninfusion.api.lib

import seremis.geninfusion.api.lib.model.{ModelCreeper, ModelSkeleton, ModelSpider, ModelZombie}
import seremis.geninfusion.api.render.Model

object Models {

    val creeper = new Model(ModelCreeper.cuboids).setTextureLocation(ModelCreeper.texture)
    val skeleton = new Model(ModelSkeleton.cuboids).setTextureLocation(ModelSkeleton.texture)
    val spider = new Model(ModelSpider.cuboids).setTextureLocation(ModelSpider.texture)
    val zombie = new Model(ModelZombie.cuboids).setTextureLocation(ModelZombie.texture)
    val zombieChild = new Model(ModelZombie.Child.cuboids).setTextureLocation(ModelZombie.Child.texture)
}
