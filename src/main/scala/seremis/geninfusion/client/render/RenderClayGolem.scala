package seremis.geninfusion.client.render

import cpw.mods.fml.client.FMLClientHandler
import net.minecraft.client.renderer.entity.RenderEntity
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import seremis.geninfusion.client.model.ModelClayGolem
import seremis.geninfusion.entity.EntityClayGolem
import seremis.geninfusion.lib.Localizations

class RenderClayGolem extends RenderEntity {

    val model = new ModelClayGolem()

    override def doRender(entity : Entity, x : Double, y : Double, z : Double, f1 : Float, f2 : Float) {
        GL11.glPushMatrix()

        FMLClientHandler.instance().getClient.renderEngine.bindTexture(getEntityTexture(entity))

        GL11.glDisable(GL11.GL_CULL_FACE)

        GL11.glTranslatef(x.toFloat, y.toFloat + (entity.boundingBox.maxY - entity.boundingBox.minY).toFloat/2.0F, z.toFloat)
        GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F)

        model.render(entity.asInstanceOf[EntityClayGolem])

        GL11.glEnable(GL11.GL_CULL_FACE)
        GL11.glPopMatrix()
    }

    override def getEntityTexture(entity : Entity): ResourceLocation = {
        val golem = entity.asInstanceOf[EntityClayGolem]
        if(golem.isTransformating)
            new ResourceLocation(Localizations.LocModelTextures + Localizations.ClayGolemTransformation)
        else if(golem.isWaitingAfterTransformation)
            golem.getTransformationGoal.get.getEntityTexture_I
        else
            new ResourceLocation(Localizations.LocModelTextures + Localizations.ClayGolem)
    }
}
