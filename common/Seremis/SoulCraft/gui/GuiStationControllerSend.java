package Seremis.SoulCraft.gui;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.api.magnet.MagnetNetwork;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.api.util.Line2D;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.gui.util.GuiLine;
import Seremis.SoulCraft.gui.util.GuiLineStation;
import Seremis.SoulCraft.helper.SCRenderHelper;
import Seremis.SoulCraft.inventory.ContainerStationControllerSend;
import Seremis.SoulCraft.tileentity.TileStationController;

public class GuiStationControllerSend extends SCGui {

    public TileStationController tile;

    private int structureRotation = -1;

    public GuiLine station;
    public List<GuiLine> lines = new ArrayList<GuiLine>();
    public List<GuiLineStation> stations = new ArrayList<GuiLineStation>();

    public GuiStationControllerSend(EntityPlayer player, IInventory tile) {
        super(new ContainerStationControllerSend(player, tile));

        super.xSize = 176;
        super.ySize = 222;

        this.tile = (TileStationController) tile;
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

        SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.BLANK);

        station.render(this);

        GL11.glPushMatrix();

        switch(getStructureRotation(tile)) {
            case 1: {
                GL11.glTranslatef(xSize / 2 + 1, ySize / 2 + 1, 0.0F);
                GL11.glRotatef(-getStructureRotation(tile) * 90, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(-xSize / 2 + 1 - 2, -ySize / 2 + 1 - 4, 0.0F);
                break;
            }
            case 2: {
                GL11.glTranslatef(xSize / 2 + 1, ySize / 2 + 1, 0.0F);
                GL11.glRotatef(-getStructureRotation(tile) * 90, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(-xSize / 2 + 1, -ySize / 2 + 1 - 4, 0.0F);
                break;
            }
            case 3: {
                GL11.glTranslatef(xSize / 2 + 1, ySize / 2 + 1, 0.0F);
                GL11.glRotatef(-getStructureRotation(tile) * 90, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(-xSize / 2 + 1, -ySize / 2 + 1 - 2, 0.0F);
                break;
            }
        }
        for(GuiLine line : stations) {
            line.render(this);
        }
        for(GuiLine line : lines) {
            line.render(this);
        }
        GL11.glPopMatrix();

        for(GuiLineStation station : stations) {
            if(station.inRect(this, x, y)) {
                List<String> list = new ArrayList<String>();

                list.add("Magnet Station");

                if(station.tile.name != null) {
                    list.add(station.tile.name);
                }

                drawHoveringString(list, x - guiLeft, y - guiTop);
            }
        }
    }

    private void initLines() {
        if(lines.isEmpty()) {
            calculate();
        }
    }

    private void calculate() {
        lines.clear();

        station = new GuiLineStation(xSize / 2 - 3, ySize / 2 - 12, 6, 8);

        station.red = 50;

        calculateLines();
    }

    private void calculateLines() {
        MagnetNetwork network = MagnetLinkHelper.instance.getNetworkFrom((IMagnetConnector) tile);

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

            line.setY((int) (line.getY() - line.getHeight()));

            line.red = (float) ((link.connector1.getHeat() + link.connector2.getHeat()) / 2 * 0.1);
            line.green = 50;

            lines.add(line);

            // Check for stations
            if(tile1 != tile && tile2 != tile) {
                int id = tile.blockType.blockID;

                GuiLineStation station = new GuiLineStation();

                if(tile1.blockType != null && tile1.blockType.blockID == id || tile2.blockType != null && tile2.blockType.blockID == id) {

                    station.setX(line.getX() + 4);
                    station.setY(line.getY() + 6);

                    station.setWidth(6);
                    station.setHeight(8);

                    station.rotation = (float) line2d.getYaw();

                    station.red = 20;

                    int xx = station.getX();
                    int yy = station.getY();
                    int w = station.getWidth();
                    int h = station.getHeight();

                    station.x = xx;
                    station.y = yy - h;
                    station.h = h;
                    station.w = -w;

                    if(tile1.blockType != null && tile1.blockType.blockID == id) {
                        station.tile = (TileStationController) tile1;
                    } else if(tile2.blockType != null && tile2.blockType.blockID == id) {
                        station.tile = (TileStationController) tile2;
                    }

                    stations.add(station);

                }
            }
        }
    }

    private int calculateStructureRotation(TileStationController tile) {
        int checkId = ModBlocks.crystalStand.blockID;

        int id1 = tile.worldObj.getBlockId(tile.xCoord + 3, tile.yCoord, tile.zCoord);
        int id2 = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord, tile.zCoord + 3);
        int id3 = tile.worldObj.getBlockId(tile.xCoord - 3, tile.yCoord, tile.zCoord);
        int id4 = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord, tile.zCoord - 3);

        if(id1 == checkId)
            return 0;
        if(id2 == checkId)
            return 1;
        if(id3 == checkId)
            return 2;
        if(id4 == checkId)
            return 3;

        return -1;
    }

    private int getStructureRotation(TileStationController tile) {
        if(structureRotation == -1 && tile == this.tile)
            structureRotation = calculateStructureRotation(this.tile);

        if(structureRotation != -1 && tile == this.tile)
            return structureRotation;

        return calculateStructureRotation(tile);
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        for(GuiLineStation station : stations) {
            if(station.inRect(this, x, y)) {
                int[] coordinates = new int[] {station.tile.xCoord, station.tile.yCoord, station.tile.zCoord};
                
                ByteBuffer byteBuffer = ByteBuffer.allocate(coordinates.length * 4);        
                IntBuffer intBuffer = byteBuffer.asIntBuffer();
                intBuffer.put(coordinates);

                byte[] array = byteBuffer.array();
                tile.sendTileData(3, array);
            }
        }
    }
}