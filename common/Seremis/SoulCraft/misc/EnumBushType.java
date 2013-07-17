package Seremis.SoulCraft.misc;

import net.minecraft.tileentity.TileEntity;
import Seremis.SoulCraft.core.lib.Localizations;

public enum EnumBushType {

    NORMAL(Localizations.BUSH_STAGE_5, Localizations.BUSH_STAGE_5, Localizations.BUSH_STAGE_5, Localizations.BUSH_STAGE_5, Localizations.BUSH_STAGE_5);

    private String texture1;
    private String texture2;
    private String texture3;
    private String texture4;
    private String texture5;

    EnumBushType(String textureStage1, String textureStage2, String textureStage3, String textureStage4, String textureStage5) {
        texture1 = Localizations.LOC_MODEL_TEXTURES + textureStage1;
        texture2 = Localizations.LOC_MODEL_TEXTURES + textureStage2;
        texture3 = Localizations.LOC_MODEL_TEXTURES + textureStage3;
        texture4 = Localizations.LOC_MODEL_TEXTURES + textureStage4;
        texture5 = Localizations.LOC_MODEL_TEXTURES + textureStage5;
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

    public static EnumBushType getTypeFromTile(TileEntity tile) {
        switch(tile.worldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)) {
            case 0:
                return NORMAL;
        }
        return null;
    }
}
