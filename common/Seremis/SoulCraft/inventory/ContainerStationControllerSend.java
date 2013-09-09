package Seremis.SoulCraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import Seremis.SoulCraft.tileentity.TileStationController;

public class ContainerStationControllerSend extends SCContainer {

    private TileStationController tile;
    
    public ContainerStationControllerSend(EntityPlayer player, IInventory tile) {
        this.tile = (TileStationController)tile;
        
        addPlayerInventory(player, 56);
    }
}
