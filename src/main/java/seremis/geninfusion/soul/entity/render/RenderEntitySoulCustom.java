package seremis.geninfusion.soul.entity.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.helper.GIRenderHelper;
import seremis.geninfusion.lib.Localizations;
import seremis.geninfusion.soul.TraitHandler;
import seremis.geninfusion.soul.entity.EntitySoulCustom;

@SideOnly(Side.CLIENT)
public class RenderEntitySoulCustom extends RenderEntity {

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f1, float f2) {
        GL11.glPushMatrix();

        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glTranslated(x, y, z);
        GL11.glRotated(entity.rotationYaw + 90, 0, -1, 0);
        GL11.glRotated(entity.rotationPitch, 0, 0, -1);
        GIRenderHelper.avoidFlickering();

        bindEntityTexture();

        TraitHandler.render((IEntitySoulCustom) entity);

        GL11.glPopMatrix();
    }

    public void bindEntityTexture() {
        GIRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.BLACK);
    }
}
