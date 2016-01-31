package seremis.geninfusion.api.lib.model

object ModelZombie {

    val cuboids = ModelBiped.cuboids
    val texture = "textures/entity/zombie/zombie.png"

    object Child {
        val cuboids = ModelBiped.Child.cuboids
        val texture = ModelZombie.texture
    }
}
