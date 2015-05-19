package seremis.geninfusion.client.render

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.entity.RenderEntity
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import seremis.geninfusion.lib.Localizations

@SideOnly(Side.CLIENT)
class RenderEntitySoul extends RenderEntity {

    override def doRender (entity : Entity, x : Double, y : Double, z : Double, f1 : Float, f2 : Float) {
        super.doRender(entity, x, y, z, f1, f2)
    }

    override def getEntityTexture(entity: Entity): ResourceLocation = {
        new ResourceLocation(Localizations.LOC_MODEL_TEXTURES + Localizations.BLANK)
    }
}
