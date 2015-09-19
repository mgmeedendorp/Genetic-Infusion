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
        TraitHandler.render(entity.asInstanceOf[IEntitySoulCustom], timeModifier, limbSwing, specialRotation, rotationYawHead, rotationPitch, scale)
    }
}