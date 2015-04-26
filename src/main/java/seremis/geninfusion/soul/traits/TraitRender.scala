package seremis.geninfusion.soul.traits

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.EntityLiving
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitRender extends Trait {

    @SideOnly(Side.CLIENT)
    override def render(entity: IEntitySoulCustom, timeModifier: Float, limbSwing: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float) {
        val model = SoulHelper.geneRegistry.getValueFromAllele[Array[ModelPart]](entity, Genes.GENE_MODEL)

        for(part <- model) {
            part.render(scale)
        }
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        val dX = living.posX - living.prevPosX
        val dZ = living.posZ - living.prevPosZ
        val distSq = (dX * dX + dZ * dZ).toFloat
        var yawOffset = living.renderYawOffset
        var f2 = 0.0F
        entity.setFloat("field_70768_au", entity.getFloat("field_110154_aX"))
        var f3 = 0.0F

        if(distSq > 0.0025000002F) {
            f3 = 1.0F
            f2 = Math.sqrt(distSq.toDouble).toFloat * 3.0F
            yawOffset = Math.atan2(dZ, dX).toFloat * 180.0F / Math.PI.toFloat - 90.0F
        }

        if(living.swingProgress > 0.0F) {
            yawOffset = living.rotationYaw
        }

        if(!living.onGround) {
            f3 = 0.0F
        }

        entity.setFloat("field_110154_aX", entity.getFloat("field_110154_aX") + (f3 - entity.getFloat("field_110154_aX")) * 0.3F)

        living.worldObj.theProfiler.startSection("headTurn")

        f2 = entity.func_110146_f(yawOffset, f2)

        living.worldObj.theProfiler.endSection()

        living.worldObj.theProfiler.startSection("rangeChecks")

        while(living.rotationYaw - living.prevRotationYaw < -180.0F) {
            living.prevRotationYaw -= 360.0F
        }

        while(living.rotationYaw - living.prevRotationYaw >= 180.0F) {
            living.prevRotationYaw += 360.0F
        }

        while(living.renderYawOffset - living.prevRenderYawOffset < -180.0F) {
            living.prevRenderYawOffset -= 360.0F
        }

        while(living.renderYawOffset - living.prevRenderYawOffset >= 180.0F) {
            living.prevRenderYawOffset += 360.0F
        }

        while(living.rotationPitch - living.prevRotationPitch < -180.0F) {
            living.prevRotationPitch -= 360.0F
        }

        while(living.rotationPitch - living.prevRotationPitch >= 180.0F) {
            living.prevRotationPitch += 360.0F
        }

        while(living.rotationYawHead - living.prevRotationYawHead < -180.0F) {
            living.prevRotationYawHead -= 360.0F
        }

        while(living.rotationYawHead - living.prevRotationYawHead >= 180.0F) {
            living.prevRotationYawHead += 360.0F
        }

        living.worldObj.theProfiler.endSection()

        entity.setFloat("field_70764_aw", entity.getFloat("field_70764_aw") + f2)

        entity.updateArmSwingProgress

        val burnsInDaylight = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_BURNS_IN_DAYLIGHT)

        if(living.getBrightness(1.0F) > 0.5F && burnsInDaylight) {
            entity.setInteger("entityAge", entity.getInteger("entityAge") + 2)
        }
    }
}
