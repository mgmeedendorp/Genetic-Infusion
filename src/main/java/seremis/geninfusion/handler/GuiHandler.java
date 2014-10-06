package seremis.geninfusion.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.geninfusion.gui.GuiStationControllerSend;
import seremis.geninfusion.gui.GuiStationControllerTransporter;
import seremis.geninfusion.inventory.ContainerStationControllerSend;
import seremis.geninfusion.inventory.ContainerStationControllerTransporter;
import seremis.geninfusion.lib.GuiIds;
import seremis.geninfusion.tileentity.TileStationController;

@SideOnly(Side.CLIENT)
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if(ID == GuiIds.GUI_STATION_TRANSPORTER_SCREEN_ID) {
            return new ContainerStationControllerTransporter(player, (IInventory) tile);
        } else if(ID == GuiIds.GUI_STATION_SEND_SCREEN_ID) {
            return new ContainerStationControllerSend(player, (IInventory) tile);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if(ID == GuiIds.GUI_STATION_TRANSPORTER_SCREEN_ID) {
            return new GuiStationControllerTransporter(player, (TileStationController) tile);
        } else if(ID == GuiIds.GUI_STATION_SEND_SCREEN_ID) {
            return new GuiStationControllerSend(player, (IInventory) tile);
        }
        return null;
    }

}
