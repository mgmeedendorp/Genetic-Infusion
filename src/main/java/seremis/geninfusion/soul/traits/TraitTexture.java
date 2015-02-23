package seremis.geninfusion.soul.traits;

import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.helper.GITextureHelper;
import seremis.geninfusion.lib.Localizations;

public class TraitTexture extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        if(!entity.loadedFromNBT()) {
            GITextureHelper.writeBufferedImage(GITextureHelper.getBufferedImage(Localizations.LOC_MODEL_TEXTURES() + Localizations.CRYSTAL()), Localizations.LOC_MODEL_TEXTURES() + Localizations.CRYSTAL());
        }
    }

    @Override
    public String getEntityTexture(IEntitySoulCustom entity) {
        return Localizations.LOC_MODEL_TEXTURES() + Localizations.BLANK();
    }
}
