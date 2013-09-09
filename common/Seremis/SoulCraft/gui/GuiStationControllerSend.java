package Seremis.SoulCraft.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.gui.util.GuiLine;
import Seremis.SoulCraft.gui.util.GuiRectangle;
import Seremis.SoulCraft.helper.SCRenderHelper;
import Seremis.SoulCraft.inventory.ContainerStationControllerSend;
import Seremis.SoulCraft.tileentity.TileStationController;

public class GuiStationControllerSend extends SCGui {

    public TileStationController tile;
    
    public List<GuiLine> lines = new ArrayList<GuiLine>();
    public List<GuiRectangle> stations = new ArrayList<GuiRectangle>();
    
    public GuiStationControllerSend(EntityPlayer player, IInventory tile) {
        super(new ContainerStationControllerSend(player, tile));
        
        super.xSize = 176;
        super.ySize = 222;
        tile = (TileStationController)tile;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   
        SCRenderHelper.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_MAGNET_STATION_LOCATION_SCREEN);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);          
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Magnet Station Controller", 8, 5, 0x404040);
        fontRenderer.drawString("Inventory", 8, this.ySize - 92, 0x404040);
        initLines();

        for(GuiRectangle rect : stations) {
            rect.draw(this, 0, 0);
        }
        for(GuiLine line : lines) {
            line.render(0, 255, 4, 0.5F);
        }
    }
    
    private void initLines() {
        if(lines.isEmpty()) {
            calculateLines();
        }
    }
    
    private void calculateLines() {
        List<MagnetLink> firstLinks = MagnetLinkHelper.instance.getLinksConnectedTo((IMagnetConnector)tile);
        
        stations.add(new GuiRectangle(this.width/2-xSize, this.height/2-ySize, 4, 3));
        
        for(MagnetLink link : firstLinks) {
            lines.add(new GuiLine().setLine(link.line));
            
        }
    }
    

}
