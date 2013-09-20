package Seremis.SoulCraft.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.gui.util.GuiTab;
import Seremis.SoulCraft.gui.util.GuiTabEngine;
import Seremis.SoulCraft.gui.util.GuiTabInventory;
import Seremis.SoulCraft.helper.SCRenderHelper;
import Seremis.SoulCraft.inventory.ContainerStationControllerTransporter;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.network.PacketTypeHandler;
import Seremis.SoulCraft.network.packet.PacketTileData;
import Seremis.SoulCraft.tileentity.TileStationController;

public class GuiStationControllerTransporter extends SCGui {

    public TileStationController tile;

    private GuiTab activeTab;
    private List<GuiTab> tabs = new ArrayList<GuiTab>();

    public GuiStationControllerTransporter(EntityPlayer player, TileStationController tile) {
        super(new ContainerStationControllerTransporter(player, (IInventory) tile));
        super.xSize = 176;
        super.ySize = 222;
        this.tile = tile;

        tabs.add(new GuiTabInventory(0, 28, 74, 14, 12, "Transporter Inventory").setVisible(false));
        tabs.add(new GuiTabEngine(1, 28, 86, 14, 12, "Transporter Engines", this).setVisible(false));

        activeTab = tabs.get(0);
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();

        buttonList.add(new GuiButton(0, guiLeft + 20, guiTop + 15, 40, 20, "Send"));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        SCRenderHelper.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_MAGNET_STATION_TRANSPORTER_SCREEN);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        for(GuiTab tab : tabs) {

            int srcY = 18;

            if(tab == activeTab) {
                srcY = 30;
                tab.drawBackground(this, x, y);
            }

            tab.draw(this, 176, srcY);
        }
        tabs.get(0).setVisible(false);
        tabs.get(1).setVisible(false);
        for(int i = 0; i < 3; i++) {
            ItemStack stack = tile.getStackInSlot(i + 1);
            if(stack != null && stack.itemID == ModItems.transporterModules.itemID && stack.getItemDamage() == 0) {
                tabs.get(0).setVisible(true);
            } else {
                tile.activeTab = -1;
            }
            if(stack != null && stack.itemID == ModItems.transporterModules.itemID && stack.getItemDamage() == 1) {
                tabs.get(1).setVisible(true);
            } else {
                tile.activeTab = -1;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Magnet Station Controller", 8, 5, 0x404040);
        fontRenderer.drawString("Inventory", 8, this.ySize - 92, 0x404040);

        SCRenderHelper.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_MAGNET_STATION_TRANSPORTER_SCREEN);

        if(activeTab != null) {
            activeTab.drawForeground(this, x, y);
        }
        if(activeTab == null || !activeTab.visible()) {
            fontRenderer.drawSplitString("No modules inserted", 50, 80, 100, 0x404040);
        }
        for(GuiTab tab : tabs) {
            tab.drawString(this, x, y, tab.getName());
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);

        if(activeTab != null)
            activeTab.mouseClick(this, x, y, button);

        for(GuiTab tab : tabs) {
            if(activeTab == null || activeTab != tab) {
                if(tab.inRect(this, x, y)) {
                    activeTab = tab;
                    tile.activeTab = tab.getId();
                    tile.sendTileData(0, tab.getId());
                    tile.worldObj.addBlockEvent(tile.xCoord, tile.yCoord, tile.zCoord, ModBlocks.stationController.blockID, 1, activeTab.getId());
                    break;
                }
            }
        }
    }

    @Override
    protected void mouseClickMove(int x, int y, int button, long timeSinceClicked) {
        super.mouseClickMove(x, y, button, timeSinceClicked);

        if(activeTab != null)
            activeTab.mouseMoveClick(this, x, y, button, timeSinceClicked);
    }

    @Override
    protected void mouseMovedOrUp(int x, int y, int button) {
        super.mouseMovedOrUp(x, y, button);

        if(activeTab != null)
            activeTab.mouseReleased(this, x, y, button);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        tile.sendTileData(1, button.id);
    }
}
