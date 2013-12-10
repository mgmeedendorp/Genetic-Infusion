package Seremis.SoulCraft.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.gui.util.GuiTab;
import Seremis.SoulCraft.gui.util.GuiTabEngine;
import Seremis.SoulCraft.gui.util.GuiTabInventory;
import Seremis.SoulCraft.gui.util.GuiTabName;
import Seremis.SoulCraft.helper.SCRenderHelper;
import Seremis.SoulCraft.inventory.ContainerStationControllerTransporter;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.tileentity.TileStationController;

public class GuiStationControllerTransporter extends SCGui {

    public TileStationController tile;

    private GuiTab activeTab;
    private List<GuiTab> tabs = new ArrayList<GuiTab>();

    public GuiStationControllerTransporter(EntityPlayer player, TileStationController tile) {
        super(new ContainerStationControllerTransporter(player, tile));
        super.xSize = 176;
        super.ySize = 222;
        this.tile = tile;

        tabs.add(new GuiTabName(0, 28, 74, 14, 12, "Station Settings", this));
        tabs.add(new GuiTabInventory(1, 28, 86, 14, 12, "Transporter Inventory").setVisible(false));
        tabs.add(new GuiTabEngine(2, 28, 98, 14, 12, "Transporter Engines", this).setVisible(false));

        activeTab = tabs.get(0);
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();

        buttonList.add(new GuiButton(0, guiLeft + 20, guiTop + 15, 40, 20, "Send"));

        for(GuiTab tab : tabs) {
            tab.initGui(this);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        SCRenderHelper.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_MAGNET_STATION_TRANSPORTER_SCREEN);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);

        for(GuiTab tab : tabs) {

            int srcY = 18;
            
            if(tab == tabs.get(0))
                srcY = 42;
            if(tab == tabs.get(1))
                srcY = 18;

            if(tab == activeTab) {
                srcY += 12;
                tab.drawBackground(this, x, y);
            }

            tab.draw(this, 176, srcY);
        }

        tabs.get(1).setVisible(false);
        tabs.get(2).setVisible(false);
        for(int i = 0; i < 3; i++) {
            ItemStack stack = tile.getStackInSlot(i + 1);
            if(stack != null && stack.itemID == ModItems.transporterModules.itemID && stack.getItemDamage() == 0) {
                tabs.get(1).setVisible(true);
            } else {
                tile.activeTab = 0;
            }
            if(stack != null && stack.itemID == ModItems.transporterModules.itemID && stack.getItemDamage() == 1) {
                tabs.get(2).setVisible(true);
            } else {
                tile.activeTab = 0;
            }
        }
        if(tile.activeTab > 0 && tile.activeTab < tabs.size()) {
            activeTab = tabs.get(tile.activeTab);
        }
        
        int barHeight = (int) (tile.barHeat/1000F*18F);
        if(barHeight != 0)
            drawTexturedModalRect(guiLeft + 102, guiTop + 14 + 18 - barHeight, 176, 18- barHeight, 4, barHeight);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Magnet Station Controller", 8, 5, 0x404040);
        fontRenderer.drawString("Inventory", 8, this.ySize - 92, 0x404040);

        SCRenderHelper.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_MAGNET_STATION_TRANSPORTER_SCREEN);

        if(activeTab != null) {
            activeTab.drawForeground(this, x, y);
        }
        for(GuiTab tab : tabs) {
            tab.drawString(this, x, y, tab.getName());
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

    @Override
    protected void actionPerformed(GuiButton button) {
        tile.sendTileDataToServer(1, new byte[] {(byte) button.id});
    }
}
