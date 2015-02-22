package seremis.geninfusion.soul.traits;

import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.lib.Localizations;

public class TraitTexture extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        if(!entity.loadedFromNBT()) {

        }
    }

    @Override
    public String getEntityTexture(IEntitySoulCustom entity) {
        return Localizations.LOC_MODEL_TEXTURES() + Localizations.BLANK();
    }
}
