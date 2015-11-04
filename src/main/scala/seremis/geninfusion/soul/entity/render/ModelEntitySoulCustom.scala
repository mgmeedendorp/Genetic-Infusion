package seremis.geninfusion.soul.entity.render

import net.minecraft.client.model.ModelBase
import net.minecraft.entity.Entity
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.soul.TraitHandler

class ModelEntitySoulCustom extends ModelBase {

    override def render(entity: Entity, timeModifier: Float, limbSwing: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float) {
        TraitHandler.render(entity.asInstanceOf[IEntitySoulCustom], timeModifier, limbSwing, specialRotation, rotationYawHead, rotationPitch, scale)
    }
}