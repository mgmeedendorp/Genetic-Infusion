package seremis.geninfusion.soul.traits

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.util.{DamageSource, ResourceLocation}
import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.api.render.Model
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.lib.{DefaultProps, Localizations}

class TraitTexture extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        if(entity.getWorld_I.isRemote) {
            var model: Model = null

            if(entity.getBoolean("isChild")) {
                model = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneModelChild)
            } else {
                model = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneModelAdult)
            }

            val image = model.getTexture

            entity.setObject("texture", image)

            val dynamicTexture = new DynamicTexture(image)

            entity.setObject("dynamicTexture", dynamicTexture)
        }
    }

    @SideOnly(Side.CLIENT)
    override def getEntityTexture(entity: IEntitySoulCustom): ResourceLocation = {
        val dynamicTexture = entity.getObject("dynamicTexture").asInstanceOf[DynamicTexture]

        if(dynamicTexture != null) {
            Minecraft.getMinecraft.renderEngine.getDynamicTextureLocation(DefaultProps.ID + ":customEntityTexture", dynamicTexture)
        } else {
            new ResourceLocation(Localizations.LocModelTextures + Localizations.ClayGolemTransformation)
        }
    }

    override def onDeath(entity: IEntitySoulCustom, source: DamageSource) {
        if(entity.getWorld_I.isRemote) {
            val dynamicTexture = entity.getObject("dynamicTexture").asInstanceOf[DynamicTexture]

            dynamicTexture.deleteGlTexture()
        }
    }

    def toResource(string: String): ResourceLocation = new ResourceLocation(string)
}