package seremis.geninfusion.soul.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import seremis.geninfusion.lib.Localizations;

public class RenderEntitySoulCustom extends RenderLiving {

    public RenderEntitySoulCustom() {
        super(new ModelZombie(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation(Localizations.LOC_MODEL_TEXTURES + Localizations.BLACK);
    }

}
