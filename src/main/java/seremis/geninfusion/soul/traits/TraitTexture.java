package seremis.geninfusion.soul.traits;

import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.helper.GITextureHelper;
import seremis.geninfusion.lib.Localizations;

public class TraitTexture extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        if(entity.getWorld().isRemote && !GITextureHelper.getFile(toResource(getEntityTexture(entity))).exists()) {
            GITextureHelper.mergeTextures(toResource("textures/entity/zombie/zombie.png"), toResource("textures/entity/skeleton/skeleton.png"), entity.getEntityId() + ".png");
        }
    }

    @Override
    public String getEntityTexture(IEntitySoulCustom entity) {
        return Localizations.LOC_ENTITY_CUSTOM_TEXTURES() + entity.getEntityId() + ".png";
    }

    public ResourceLocation toResource(String string) {
        return new ResourceLocation(string);
    }

    @Override
    public void onDeath(IEntitySoulCustom entity, DamageSource source) {
        GITextureHelper.deleteTexture(toResource(Localizations.LOC_ENTITY_CUSTOM_TEXTURES() + entity.getEntityId() + ".png"));
    }
}
