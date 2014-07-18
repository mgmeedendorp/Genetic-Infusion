package seremis.geninfusion.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerStationControllerSend extends GIContainer {

    public ContainerStationControllerSend(EntityPlayer player, IInventory tile) {
        addPlayerInventory(player, 56);
    }
}
