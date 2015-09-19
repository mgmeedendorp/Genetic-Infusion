package seremis.geninfusion.soul.entity.render

import net.minecraft.client.model.{ModelSpider, ModelBase}
import net.minecraft.entity.Entity
import org.lwjgl.opengl.GL11
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.api.soul.lib.ModelPartTypes
import seremis.geninfusion.api.util.render.model.{ModelPart, Model}
import seremis.geninfusion.helper.{GIRenderHelper, GITextureHelper}
import seremis.geninfusion.lib.Localizations
import seremis.geninfusion.soul.TraitHandler

class ModelEntitySoulCustom extends ModelBase {

    override def render(entity: Entity, timeModifier: Float, limbSwing: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float) {
        GL11.glTranslated(0.0F, -3.0F, 0.0F)
        TraitHandler.render(entity.asInstanceOf[IEntitySoulCustom], timeModifier, limbSwing, specialRotation, rotationYawHead, rotationPitch, scale)
        GL11.glTranslated(0.0F, 3.0F, 0.0F)

        GIRenderHelper.bindTexture(Localizations.LocModelTextures + "white.png")

        model.render()
    }

    lazy val model: Model = {
        val model = new Model
        val modelSpider = new ModelSpider

        modelSpider.setRotationAngles(0.0F, 0.0F, 1.3997523F, 0.0F, 0.0F, 0.0F, null)

        model.addPart(ModelPart.rendererToPart(modelSpider.spiderHead, ModelPartTypes.Head))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderBody, ModelPartTypes.Body))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderNeck, ModelPartTypes.Body))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg1, ModelPartTypes.LegsRight))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg2, ModelPartTypes.LegsLeft))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg3, ModelPartTypes.LegsRight))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg4, ModelPartTypes.LegsLeft))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg5, ModelPartTypes.LegsRight))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg6, ModelPartTypes.LegsLeft))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg7, ModelPartTypes.LegsRight))
        model.addPart(ModelPart.rendererToPart(modelSpider.spiderLeg8, ModelPartTypes.LegsLeft))

        model
    }
}