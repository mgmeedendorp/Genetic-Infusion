package Seremis.SoulCraft.api.util;

import org.lwjgl.util.Color;




public class HeatColorHelper {

    public static HeatColorHelper instance = new HeatColorHelper();
    
    public Color convertHeatToColor(int heat) {
        Color color = new Color();
        
        color.setRed(0);
        color.setGreen(255);
        color.setBlue(255);
        
        return color;
    }
}
