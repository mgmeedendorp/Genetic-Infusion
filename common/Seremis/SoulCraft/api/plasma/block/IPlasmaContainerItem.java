package Seremis.SoulCraft.api.plasma.block;

import net.minecraft.item.ItemStack;
import Seremis.SoulCraft.api.plasma.PlasmaPacket;

public interface IPlasmaContainerItem {
    /**
     * 
     * @param stack The Itemstack
     * @param pack The PlasmaPacket to fill this item with.
     * @return The remaining Plasma, null if it fits in completely.
     */
    public PlasmaPacket fill(ItemStack stack, PlasmaPacket pack);
    public PlasmaPacket drain(ItemStack stack, int amount);
    public int getSize();
    
}
