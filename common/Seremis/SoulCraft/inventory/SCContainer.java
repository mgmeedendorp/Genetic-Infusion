package Seremis.SoulCraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SCContainer extends Container {

    private IInventory tile;
    
    public SCContainer(IInventory tile) {
        this.tile = tile;
    }
    
    public void addPlayerInventory(EntityPlayer player) {
        InventoryPlayer playerInv = player.inventory;
        for (int i = 0; i < 3; ++i) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(playerInv, var4 + i * 9 + 9, 8 + var4 * 18, 84 + i * 18));
            }
        }

        for (int j = 0; j < 9; ++j) {
            this.addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 142));
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
