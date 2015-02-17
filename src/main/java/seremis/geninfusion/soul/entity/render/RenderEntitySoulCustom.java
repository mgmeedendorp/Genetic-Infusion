package seremis.geninfusion.soul.entity.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.lib.Localizations;

@SideOnly(Side.CLIENT)
public class RenderEntitySoulCustom extends RenderLiving {

    public RenderEntitySoulCustom() {
        super(SoulHelper.entityModel, 1.0F);
    }

    @Override
    public void preRenderCallback(EntityLivingBase entity, float partialTickTime) {
        ((IEntitySoulCustom)entity).setFloat("partialTickTime", partialTickTime);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation("textures/entity/zombie/zombie.png");
//        return new ResourceLocation(((IEntitySoulCustom) entity).getString("textureLocation"));
    }
}
