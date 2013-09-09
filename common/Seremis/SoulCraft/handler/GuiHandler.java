package Seremis.SoulCraft.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.GuiIds;
import Seremis.SoulCraft.gui.GuiStationControllerSend;
import Seremis.SoulCraft.gui.GuiStationControllerTransporter;
import Seremis.SoulCraft.inventory.ContainerStationControllerSend;
import Seremis.SoulCraft.inventory.ContainerStationControllerTransporter;
import Seremis.SoulCraft.tileentity.TileStationController;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(ID == GuiIds.GUI_STATION_TRANSPORTER_SCREEN_ID) {
            return new ContainerStationControllerTransporter(player, (IInventory) tile);
        } else if(ID == GuiIds.GUI_STATION_SEND_SCREEN_ID) {
            return new ContainerStationControllerSend(player, (IInventory) tile);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(ID == GuiIds.GUI_STATION_TRANSPORTER_SCREEN_ID) {
            return new GuiStationControllerTransporter(player, (TileStationController)tile);
        } else if(ID == GuiIds.GUI_STATION_SEND_SCREEN_ID) {
            return new GuiStationControllerSend(player, (IInventory) tile);
        }
        return null;
    }

}
