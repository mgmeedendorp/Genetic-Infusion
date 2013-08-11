package Seremis.SoulCraft.misc;

import net.minecraft.item.ItemStack;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.helper.SCRenderHelper;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.tileentity.TileBush;

public enum EnumBushType {

    NORMAL(Localizations.BUSH_STAGE_1, Localizations.BUSH_STAGE_2, Localizations.BUSH_STAGE_3, Localizations.BUSH_STAGE_4, Localizations.BUSH_STAGE_5, new ItemStack(ModItems.berry, 2, 0));

    private String texture1;
    private String texture2;
    private String texture3;
    private String texture4;
    private String texture5;
    
    private ItemStack drops;

    EnumBushType(String textureStage1, String textureStage2, String textureStage3, String textureStage4, String textureStage5, ItemStack drops) {
        texture1 = Localizations.LOC_MODEL_TEXTURES + textureStage1;
        texture2 = Localizations.LOC_MODEL_TEXTURES + textureStage2;
        texture3 = Localizations.LOC_MODEL_TEXTURES + textureStage3;
        texture4 = Localizations.LOC_MODEL_TEXTURES + textureStage4;
        texture5 = Localizations.LOC_MODEL_TEXTURES + textureStage5;
        this.drops = drops;
    }

    public String getTexture(int stage) {
        switch(stage) {
            case 1:
                return texture1;
            case 2:
                return texture2;
            case 3:
                return texture3;
            case 4:
                return texture4;
            case 5:
                return texture5;
            default:
                return texture1;
        }
    }

    public static EnumBushType getTypeFromMetadata(int metadata) {
        switch(metadata) {
            case 0:
                return NORMAL;
        }
        return null;
    }
    
    public ItemStack getDrops() {
        return this.drops;
    }
}
