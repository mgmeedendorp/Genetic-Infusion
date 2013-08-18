package Seremis.SoulCraft.handler;

import java.util.EnumSet;

import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.core.lib.Strings;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class RenderTickHandler implements ITickHandler {

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {

    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        MagnetLinkHelper.instance.renderLinks();
        
        //for testing, will be used later on
//        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft().gameSettings,
//                Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
//                FontRenderer fontRender = Minecraft.getMinecraft().fontRenderer;
//                int width = res.getScaledWidth();
//                int height = res.getScaledHeight();
//                Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
//
//                // draw
//                if(FMLClientHandler.instance().getClient().inGameHasFocus){
//                String text = "Hello World";
//                int x = 100;
//                int y = 200;
//                int color = 0xFFFFFF;
//                fontRender.drawStringWithShadow(text, x, y, color);
//                }
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.RENDER);
    }

    @Override
    public String getLabel() {
        return Strings.nameRenderTickHandler;
    }

}
