package seremis.geninfusion.gui;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import seremis.geninfusion.api.magnet.MagnetLink;
import seremis.geninfusion.api.magnet.MagnetLinkHelper;
import seremis.geninfusion.api.magnet.MagnetNetwork;
import seremis.geninfusion.api.util.Coordinate3D;
import seremis.geninfusion.api.util.HeatColorHelper;
import seremis.geninfusion.api.util.Line2D;
import seremis.geninfusion.lib.Localizations;
import seremis.geninfusion.gui.util.GuiLine;
import seremis.geninfusion.gui.util.GuiLineStation;
import seremis.geninfusion.gui.util.GuiTab;
import seremis.geninfusion.gui.util.GuiTabTop;
import seremis.geninfusion.helper.GIRenderHelper;
import seremis.geninfusion.inventory.ContainerStationControllerSend;
import seremis.geninfusion.tileentity.TileStationController;

public class GuiStationControllerSend extends GIGui {

    public TileStationController tile;

    public List<GuiLine> lines = new ArrayList<GuiLine>();
    public List<GuiLineStation> stations = new ArrayList<GuiLineStation>();

    public final int mapSendX = guiLeft + 6;
    public final int mapSendY = guiTop + 16;
    public final int mapSendWidth = 164;
    public final int mapSendHeight = 110;

    public int dragOffsetX = 0;
    public int dragOffsetY = 0;
    public boolean isDragging;
    public boolean firstDragTick = true;
    public int mouseStartX;
    public int mouseStartY;
    
    public List<GuiTab> tabsTop = new ArrayList<GuiTab>();

    public GuiStationControllerSend(EntityPlayer player, IInventory tile) {
        super(new ContainerStationControllerSend(player, tile));

        super.xSize = 176;
        super.ySize = 222;

        this.tile = (TileStationController) tile;
        
        tabsTop.add(new GuiTabTop(0, 2, -16, 16, 16, 175, 0, "Transporter"));
        tabsTop.add(new GuiTabTop(1, 18, -16, 16, 19, 192, 0, "Destination"));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GIRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.BLACK);
        drawTexturedModalRect(guiLeft + 6, guiTop + 16, 6, 16, this.mapSendWidth, mapSendHeight);

        GIRenderHelper.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_MAGNET_STATION_LOCATION_SCREEN);
        drawTexturedModalRect(guiLeft, guiTop + 135, 0, 135, xSize, ySize - 135);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        initLines();
        GL11.glColor4f(1, 1, 1, 1);

        GL11.glPushMatrix();

        GL11.glTranslatef(dragOffsetX, dragOffsetY, 0);

        for(GuiLine line : stations) {
            if(isInBetween(mapSendX, mapSendX + mapSendWidth-9, line.x + dragOffsetX) && isInBetween(mapSendY, mapSendY + mapSendHeight-9, line.y + dragOffsetY)) {
                line.render(this);
            }
        }
        for(GuiLine line : lines) {
            if(isInBetween(mapSendX, mapSendX + mapSendWidth-9, line.x + dragOffsetX) && isInBetween(mapSendY, mapSendY + mapSendHeight-9, line.y + dragOffsetY)) {
                line.render(this);
            }
        }

        GL11.glTranslatef(-dragOffsetX, -dragOffsetY, 0);

        GL11.glPopMatrix();

        GL11.glColor3f(1, 1, 1);
        GIRenderHelper.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_MAGNET_STATION_LOCATION_SCREEN);
        drawTexturedModalRect(0, 0, 0, 0, xSize, 135);

        GIRenderHelper.bindTexture(Localizations.LOC_GUI_TEXTURES + Localizations.GUI_MAGNET_STATION_LOCATION_SCREEN);
        for(GuiTab tab : tabsTop) {
            tab.drawForeground(this, x, y);
        }
        
        fontRendererObj.drawString("Magnet Station Controller", 8, 5, 0x404040);
        fontRendererObj.drawString("Inventory", 8, this.ySize - 92, 0x404040);
        
        if(!isDragging) {
            for(GuiLineStation station : stations) {
                if(tile.selectedDestination != null && tile.selectedDestination.equals(new Coordinate3D(station.tile))) {
                    station.green = 80;
                } else {
                    station.green = 0;
                }
                if(station.inRect(this, x-dragOffsetX, y-dragOffsetY)) {
                    List<String> list = new ArrayList<String>();
    
                    list.add("Magnet Station");
                    
                    if(new Coordinate3D(station.tile).equals(new Coordinate3D(tile))) {
                        list.add(EnumChatFormatting.BLUE + "Current");
                    }
    
                    if(station.tile.name != null) {
                        list.add(EnumChatFormatting.ITALIC + station.tile.name);
                    }
    
                    drawHoveringString(list, x - guiLeft, y - guiTop);
                }
            }
        }
        
        for(GuiTab tab : tabsTop) {
            tab.drawString(this, x, y, tab.getName());
        }
    }

    private boolean isInBetween(int x, int y, int i) {
        return i > Math.min(x, y) && i < Math.max(x, y);
    }

    private void initLines() {
        if(lines.isEmpty()) {
            calculate();
        }
    }

    private void calculate() {
        lines.clear();
        calculateLines();
    }

    private void calculateLines() {
        MagnetNetwork network = MagnetLinkHelper.instance.getNetworkFrom(tile);
        
        Iterator<MagnetLink> it = network.getLinks().iterator();

        int x = xSize / 2 - 1;
        int y = ySize / 2 - 1;

        int stationX = this.tile.xCoord;
        int stationZ = this.tile.zCoord;

        while(it.hasNext()) {
            GuiLine line = new GuiLine();

            MagnetLink link = it.next();
            
            TileEntity tile1 = link.connector1.getTile();
            TileEntity tile2 = link.connector2.getTile();

            int x1 = tile1.xCoord - stationX;
            int z1 = stationZ - tile1.zCoord;
            int diff1 = Math.abs(x1) + Math.abs(z1);

            int x2 = tile2.xCoord - stationX;
            int z2 = stationZ - tile2.zCoord;
            int diff2 = Math.abs(x2) + Math.abs(z2);

            Line2D line2d = new Line2D();

            if(diff1 < diff2) {
                line.setX(x - z1 * 2);
                line.setY(y - x1 * 2 + 3);
                line2d = new Line2D(z1, x1, z2, x2);
            } else {
                line.setX(x - z2 * 2);
                line.setY(y - x2 * 2 + 3);
                line2d = new Line2D(z2, x2, z1, x1);
            }

            line.setWidth(2);

            line.setHeight((int) (line2d.getLength() * 2));

            line.rotation = (float) line2d.getYaw();

            line.setY(line.getY() - line.getHeight());

            Color color = HeatColorHelper.instance.convertHeatToColor(link.connector1.getHeat());
            
            line.red = color.getRed();
            line.green = color.getGreen();
            line.blue = color.getBlue();
            
            lines.add(line);

            // Check for stations
            if(tile1.blockType != null && tile1.blockType == tile.blockType || tile2.blockType != null && tile2.blockType == tile.blockType) {
                GuiLineStation station = new GuiLineStation();

                TileStationController controller = null;

                if(tile1.blockType != null && tile1.blockType == tile.blockType) {
                    controller = (TileStationController) tile1;
                } else {
                    controller = (TileStationController) tile2;
                }

                station.setX(4);
                station.setY(6);

                station.setWidth(6);
                station.setHeight(8);

                station.rotation = (float) line2d.getYaw();

                station.x += line.getX();
                station.y += line.getY();

                station.red = 20;

                station.y -= station.h;
                station.w = -station.w;

                station.tile = controller;

                stations.add(station);

            }
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
        for(GuiLineStation station : stations) {
            if(station.inRect(this, x - dragOffsetX, y - dragOffsetY) && station.tile != tile) {
                int[] coordinates = new int[] {station.tile.xCoord, station.tile.yCoord, station.tile.zCoord, button};

                ByteBuffer byteBuffer = ByteBuffer.allocate(coordinates.length * 4);
                IntBuffer intBuffer = byteBuffer.asIntBuffer();
                intBuffer.put(coordinates);

                byte[] array = byteBuffer.array();
                tile.sendTileDataToServer(3, array);
                if(button == 1 && !new Coordinate3D(station.tile).equals(new Coordinate3D(tile))) {
                    tile.selectedDestination = new Coordinate3D(station.tile);
                }
            }
        }

        if(isInBetween(this.mapSendX, this.mapSendX + this.mapSendWidth, x-guiLeft) && isInBetween(this.mapSendY, this.mapSendY + this.mapSendHeight, y-guiTop)) {
            this.isDragging = true;
        }
        
        GuiTab tab = tabsTop.get(0);
        if(tab.inRect(this, x, y))
            tab.mouseClick(this, x, y, button);
    }
    
    @Override
    public void mouseClickMove(int x, int y, int button, long timeSinceClicked) {
        super.mouseClickMove(x, y, button, timeSinceClicked);
        if(isDragging && firstDragTick) {       
            mouseStartX = x - dragOffsetX;
            mouseStartY = y - dragOffsetY;
            firstDragTick = false;
        } else if(isDragging) {
            dragOffsetX = -(mouseStartX - x);
            dragOffsetY = -(mouseStartY - y);
        }
    }
    
    @Override
    public void mouseMovedOrUp(int x, int y, int movedOrUp) {
        super.mouseMovedOrUp(x, y, movedOrUp);
        if(isDragging) {
            isDragging = false;
            firstDragTick = true;
        }
    }
}