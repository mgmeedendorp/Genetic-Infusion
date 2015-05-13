package seremis.geninfusion.client.model

import net.minecraft.client.model.{ModelBase, ModelRenderer}

class ModelCrystal extends ModelBase {

    textureHeight = 256
    textureWidth = 256

    val crystal: ModelRenderer = new ModelRenderer(this)

    crystal.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16)
    crystal.setRotationPoint(0.0F, 32.0F, 0.0F)
    crystal.setTextureSize(textureWidth, textureHeight)

    setRotation(crystal, 0.7F, 0.0F, 0.4F)

    def render() {
        crystal.render(0.0625F)
    }

    private def setRotation(model: ModelRenderer, x: Float, y: Float, z: Float) {
        model.rotateAngleX = x
        model.rotateAngleY = y
        model.rotateAngleZ = z
    }
}