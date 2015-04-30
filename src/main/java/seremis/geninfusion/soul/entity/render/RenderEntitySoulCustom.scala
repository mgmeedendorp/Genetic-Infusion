package seremis.geninfusion.soul.entity.render

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.entity.RenderLiving
import net.minecraft.entity.{Entity, EntityLivingBase}
import net.minecraft.util.ResourceLocation
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.soul.TraitHandler

@SideOnly(Side.CLIENT)
class RenderEntitySoulCustom extends RenderLiving(SoulHelper.entityModel, 0.3F) {

    override def renderLivingAt(entity: EntityLivingBase, x: Double, y: Double, z: Double) {
        entity.asInstanceOf[IEntitySoulCustom].setEntityRender(this)
        super.renderLivingAt(entity, x, y, z)
    }

    override def preRenderCallback(entity: EntityLivingBase, partialTickTime: Float) {
        entity.asInstanceOf[IEntitySoulCustom].setFloat("partialTickTime", partialTickTime)
        TraitHandler.preRenderCallback(entity.asInstanceOf[IEntitySoulCustom], partialTickTime)
    }

    protected override def getEntityTexture(entity: Entity): ResourceLocation = {
        new ResourceLocation(TraitHandler.getEntityTexture(entity.asInstanceOf[IEntitySoulCustom]))
    }

    protected override def renderEquippedItems(base: EntityLivingBase, partialTickTime: Float) {
        TraitHandler.renderEquippedItems(base.asInstanceOf[IEntitySoulCustom], partialTickTime)
    }

    protected override def getColorMultiplier(living: EntityLivingBase, brightness: Float, partialTickTime: Float): Int = {
        TraitHandler.getColorMultiplier(living.asInstanceOf[IEntitySoulCustom], brightness, partialTickTime)
    }

    protected override def shouldRenderPass(living: EntityLivingBase, renderPass: Int, partialTickTime: Float): Int = {
        TraitHandler.shouldRenderPass(living.asInstanceOf[IEntitySoulCustom], renderPass, partialTickTime)
    }

    protected override def inheritRenderPass(living: EntityLivingBase, renderPass: Int, partialTickTime: Float): Int = {
        TraitHandler.inheritRenderPass(living.asInstanceOf[IEntitySoulCustom], renderPass, partialTickTime)
    }
}