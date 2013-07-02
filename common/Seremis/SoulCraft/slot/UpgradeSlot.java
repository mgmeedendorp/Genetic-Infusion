package Seremis.SoulCraft.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.item.SCItem;

public class UpgradeSlot extends SCSlot {

    public UpgradeSlot(IInventory inventory, int id, int x, int y) {
        super(inventory, id, x, y);
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        for(SCItem item : ModItems.transporterUpgrades) {
            if(stack.itemID == item.itemID) {
                return true;
            }
        }
        return false;
    }
}
