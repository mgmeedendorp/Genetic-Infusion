package seremis.soulcraft.soul.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import seremis.soulcraft.core.lib.Localizations;

public class RenderEntitySoulCustom extends RenderLiving {

    public RenderEntitySoulCustom() {
        super(new ModelZombie(), 5.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation(Localizations.LOC_MODEL_TEXTURES + Localizations.BLACK);
    }

}
