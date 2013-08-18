package Seremis.SoulCraft.misc.bush;

import net.minecraft.item.ItemStack;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.item.ModItems;

public class BushTypeNormal extends BushType {

    public BushTypeNormal() {
        super(5, 4);
        addTextureToStage(1, Localizations.LOC_MODEL_TEXTURES+Localizations.BUSH_STAGE_1);
        addTextureToStage(2, Localizations.LOC_MODEL_TEXTURES+Localizations.BUSH_STAGE_2);
        addTextureToStage(3, Localizations.LOC_MODEL_TEXTURES+Localizations.BUSH_STAGE_3);
        addTextureToStage(4, Localizations.LOC_MODEL_TEXTURES+Localizations.BUSH_STAGE_4);
        addTextureToStage(5, Localizations.LOC_MODEL_TEXTURES+Localizations.BUSH_STAGE_5);
        addFortuneDrop(new ItemStack(ModItems.berry, 2), 1F);
        addFortuneDrop(new ItemStack(ModItems.berry, 1), 0.3F);
    }

}
