package Seremis.SoulCraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class SCContainer extends Container {

    private boolean addingPlayerInv = false;
    protected int slotCount = 0;
    
    public void addPlayerInventory(EntityPlayer player, int zDown) {
        addingPlayerInv = true;
        InventoryPlayer playerInv = player.inventory;
        for(int columns = 0; columns < 3; ++columns) {
            for(int rows = 0; rows < 9; ++rows) {
                this.addSlotToContainer(new Slot(playerInv, rows + columns * 9 + 9, 8 + rows * 18, 84 + zDown + columns * 18));
            }
        }

        for(int j = 0; j < 9; ++j) {
            this.addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 142+zDown));
        }
        addingPlayerInv = false;
    }
    
    public void addPlayerInventory(EntityPlayer player) {
        addPlayerInventory(player, 0);
    }
    
    @Override
    public Slot addSlotToContainer(Slot slot) {
        if(!addingPlayerInv) {
            slotCount++;
        }
        return super.addSlotToContainer(slot);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
