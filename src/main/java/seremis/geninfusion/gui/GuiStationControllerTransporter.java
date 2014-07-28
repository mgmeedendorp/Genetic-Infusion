package seremis.geninfusion.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import seremis.geninfusion.gui.util.*;
import seremis.geninfusion.helper.GIRenderHelper;
import seremis.geninfusion.inventory.ContainerStationControllerTransporter;
import seremis.geninfusion.item.ModItems;
import seremis.geninfusion.lib.Localizations;
import seremis.geninfusion.tileentity.TileStationController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiStationControllerTransporter extends GIGui {

    public TileStationController tile;

    private GuiTab activeTab;
    private List<GuiTab> tabs = new ArrayList<GuiTab>();
    
    private List<GuiTab> tabsTop = new ArrayList<GuiTab>();

    public GuiStationControllerTransporter(EntityPlayer player, TileStationController tile) {
        super(new ContainerStationControllerTransporter(player, tile));
        super.xSize = 176;
        super.ySize = 222;
        this.tile = tile;

        tabs.add(new GuiTabName(0, 28, 74, 14, 12, 176, 42, "Station Settings", this));
        tabs.add(new GuiTabInventory(1, 28, 86, 14, 12, 176, 18, "Transporter Inventory").setVisible(false));
        tabs.add(new GuiTabEngine(2, 28, 98, 14, 12, 176, 66, "Transporter Engines", this).setVisible(false));

        tabsTop.add(new GuiTabTop(0, 2, -16, 16, 19, 190, 0, "Transporter"));
        tabsTop.add(new GuiTabTop(1, 18, -16, 16, 16, 206, 0, "Destination"));
        
        activeTab = tabs.get(0);
    }

    @Override
    public void initGui() {
        super.initGui();
        
        for(GuiTab tab : tabs) {
            tab.initGui(this);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        GIRenderHelper.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_MAGNET_STATION_TRANSPORTER_SCREEN);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);

        for(GuiTab tab : tabs) {
            int v = tab.v;
            if(tab == activeTab) {
                tab.v += 12;
                tab.drawBackground(this, x, y);
            }
            
            tab.draw(this);
            tab.v = v;
        }

        for(GuiTab tab : tabsTop) {
            tab.draw(this);
        }
        
        tabs.get(1).setVisible(false);
        tabs.get(2).setVisible(false);
        
        for(int i = 0; i < 3; i++) {
            ItemStack stack = tile.getStackInSlot(i + 1);
            if(stack != null && stack.isItemEqual(new ItemStack(ModItems.transporterModules)) && stack.getItemDamage() == 0) {
                tabs.get(1).setVisible(true);
            } else {
                tile.activeTab = 0;
            }
            if(stack != null && stack.isItemEqual(new ItemStack(ModItems.transporterModules)) && stack.getItemDamage() == 1) {
                tabs.get(2).setVisible(true);
            } else {
                tile.activeTab = 0;
            }
        }
        if(tile.activeTab > 0 && tile.activeTab < tabs.size()) {
            activeTab = tabs.get(tile.activeTab);
        }
        
        int barHeight = (int) (tile.barHeat/1000F*48F);
        if(barHeight != 0)
            drawTexturedModalRect(guiLeft + 127, guiTop + 15 + 48 - barHeight, 176, 138 - barHeight, 8, barHeight);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRendererObj.drawString("Magnet Station Controller", 8, 5, 0x404040);
        fontRendererObj.drawString("Inventory", 8, this.ySize - 92, 0x404040);

        GIRenderHelper.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_MAGNET_STATION_TRANSPORTER_SCREEN);

        if(activeTab != null) {
            activeTab.drawForeground(this, x, y);
        }
        for(GuiTab tab : tabs) {
            tab.drawString(this, x, y, tab.getName());
        }
        
        for(GuiTab tab : tabsTop) {
            tab.drawString(this, x, y, tab.getName());
        }
        
        int mouseX = x - getLeft();
        int mouseY = y - getTop();
        
        if(mouseX > 126 && mouseX < 135 && mouseY > 14 && mouseY < 63) {
            String str = "Heat: " + tile.barHeat;
            drawHoveringString(Arrays.asList(str.split("\n")), x - getLeft(), y - getTop());
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);

        if(activeTab != null) {
            activeTab.mouseClick(this, x, y, button);
        }

        for(GuiTab tab : tabs) {
            if(activeTab == null || activeTab != tab) {
                if(tab.inRect(this, x, y)) {
                    activeTab = tab;
                    tile.activeTab = tab.getId();
                    tile.sendTileDataToServer(0, new byte[] {(byte) tab.getId()});
                    break;
                }
            }
        }
        
        GuiTab tab = tabsTop.get(1);
        if(tab.inRect(this, x, y))
            tab.mouseClick(this, x, y, button);
    }

    @Override
    protected void mouseClickMove(int x, int y, int button, long timeSinceClicked) {
        super.mouseClickMove(x, y, button, timeSinceClicked);

        if(activeTab != null) {
            activeTab.mouseMoveClick(this, x, y, button, timeSinceClicked);
        }
    }

    @Override
    protected void mouseMovedOrUp(int x, int y, int button) {
        super.mouseMovedOrUp(x, y, button);

        if(activeTab != null) {
            activeTab.mouseReleased(this, x, y, button);
        }
    }

    @Override
    protected void keyTyped(char keyTyped, int keyCode) {
        if(keyCode == 1) {
            this.mc.thePlayer.closeScreen();
        }
        if(activeTab != null) {
            activeTab.keyTyped(this, keyTyped, keyCode);
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        tile.sendTileDataToServer(2, tile.name.getBytes());
        tile.activeTab = 0;
        tile.sendTileDataToServer(0, new byte[] {0});
    }
}
