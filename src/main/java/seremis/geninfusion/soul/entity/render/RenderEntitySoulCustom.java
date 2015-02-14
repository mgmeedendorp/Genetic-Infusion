package seremis.geninfusion.soul.entity.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.lib.Localizations;

@SideOnly(Side.CLIENT)
public class RenderEntitySoulCustom extends RenderLiving {

    public RenderEntitySoulCustom() {
        super(SoulHelper.entityModel, 1.0F);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f1, float f2) {
        super.doRender(entity, x, y, z, f1, f2);

//        GL11.glPushMatrix();
//
//        GL11.glTranslated(x, y, z);
////        GL11.glTranslatef(0.0F, -24.0F * 0.0625F - 0.0078125F, 0.0F);
////        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
////        GL11.glRotated(entity.rotationYaw + 90, 0, -1, 0);
////        GL11.glRotated(entity.rotationPitch, 0, 0, -1);
////        GIRenderHelper.avoidFlickering();
//
//        TraitHandler.render((IEntitySoulCustom) entity);
//
//        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation("textures/entity/zombie/zombie.png");
//        return new ResourceLocation(((IEntitySoulCustom) entity).getString("textureLocation"));
    }
}
