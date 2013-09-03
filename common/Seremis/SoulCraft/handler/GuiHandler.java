package Seremis.SoulCraft.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.GuiIds;
import Seremis.SoulCraft.gui.GuiContainerStationController;
import Seremis.SoulCraft.gui.GuiContainerTransporter;
import Seremis.SoulCraft.inventory.ContainerStationController;
import Seremis.SoulCraft.inventory.ContainerTransporter;
import Seremis.SoulCraft.tileentity.TileStationController;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(ID == GuiIds.GUI_TRANSPORTER_ID) {
            return new ContainerTransporter(player, (IInventory) tile);
        } else if(ID == GuiIds.GUI_STATION_ID) {
            return new ContainerStationController(player, (IInventory) tile);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(ID == GuiIds.GUI_TRANSPORTER_ID) {
            return new GuiContainerTransporter(player.inventory, (IInventory) tile);
        } else if(ID == GuiIds.GUI_STATION_ID) {
            return new GuiContainerStationController(player, (TileStationController)tile);
        }
        return null;
    }

}
