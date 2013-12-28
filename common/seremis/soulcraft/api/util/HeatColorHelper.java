package seremis.soulcraft.api.util;

import org.lwjgl.util.Color;


public class HeatColorHelper {

    public static HeatColorHelper instance = new HeatColorHelper();
    
    public Color convertHeatToColor(int heat) {
        instance = new HeatColorHelper();
        Color color = new Color();

        if(heat == 0) {
            color.setGreen(1);
            color.setBlue(1);
        }else if(heat < 300) {
            color.setGreen(255 - heat > 255 ? 255 : heat);
            color.setBlue(heat > 255 ? 255 : heat);
        } else if(heat > 255 && heat < 600) {
            color.setBlue(255);
        } else if(heat > 600 && heat < 1000) {
            color.setBlue(255);
            color.setRed(heat-600);
        } else if(heat > 1000) {
            color.setRed(255);
        }
        
        return color;
    }
}
