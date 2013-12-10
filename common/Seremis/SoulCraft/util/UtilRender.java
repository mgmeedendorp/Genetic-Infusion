package Seremis.SoulCraft.util;

import org.lwjgl.util.vector.Vector2f;

public class UtilRender {

    public static Vector2f[] calculateUVMapping(int texture, int atlasWidth, int atlasHeight) {
        int textureIndex = texture;

        int u = textureIndex % atlasWidth;
        int v = textureIndex / atlasHeight;

        float xOffset = 1f / atlasWidth;
        float yOffset = 1f / atlasHeight;

        float uOffset = u * xOffset;
        float vOffset = v * yOffset;

        Vector2f[] UVList = new Vector2f[4];

        UVList[0] = new Vector2f(uOffset, vOffset); // 0,0
        UVList[1] = new Vector2f(uOffset, vOffset + yOffset); // 0,1
        UVList[2] = new Vector2f(uOffset + xOffset, vOffset); // 1,0
        UVList[3] = new Vector2f(uOffset + xOffset, vOffset + yOffset); // 1,1

        return UVList;
    }

    public static Vector2f[] calculateUVMapping(int texture) {
        return calculateUVMapping(texture, 16, 16);
    }
}
