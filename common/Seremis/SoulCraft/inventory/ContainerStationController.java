package Seremis.SoulCraft.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.inventory.slot.FilteredSlot;
import Seremis.SoulCraft.inventory.slot.ToggleableMoveSlot;
import Seremis.SoulCraft.inventory.slot.ToggleableMoveUpgradeSlot;
import Seremis.SoulCraft.tileentity.TileStationController;

public class ContainerStationController extends SCContainer {

    private TileStationController tile;
    private boolean transporterSlotsEnabled = false;
    
    public ContainerStationController(EntityPlayer player, IInventory tile) {
        this.tile = (TileStationController)tile;
        this.tile.container = this;
        
        addSlotToContainer(new FilteredSlot(tile, 0, 130, 19, new ItemStack(ModBlocks.transporter), 1));
        
        addTransporterInventory();
        addPlayerInventory(player, 56);
    }
    
    private void addTransporterInventory() {
        //negen gewone slots
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                ToggleableMoveSlot slot = new ToggleableMoveSlot(tile, i*3+j+1, 112 + (18 * i), 77 + (18 * j));
                slot.disable();
                addSlotToContainer(slot);
            }
        }
        // drie upgrade slots
        for(int i = 0; i < 3; i++) {
            ToggleableMoveUpgradeSlot slot = new ToggleableMoveUpgradeSlot(tile, i+10, 112 + (18 * i), 53);
            slot.disable();
            addSlotToContainer(slot);
        }
    }

    public void enableTransporterInventory() {
        for(int i = 0; i < this.slotCount-1; i++) {
            ((ToggleableMoveSlot)inventorySlots.get(i+1)).enable();
        }
    }
    
    public void disableTransporterInventory() {
        for(int i = 0; i < this.slotCount-1; i++) {
            ((ToggleableMoveSlot)inventorySlots.get(i+1)).disable();
        }
    }
    
    @Override
    public void addCraftingToCrafters(ICrafting iCrafting) {
        super.addCraftingToCrafters(iCrafting);
        iCrafting.sendProgressBarUpdate(this, 0, tile.hasTransporter() ? 1 : 0);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for(int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);
            if(this.transporterSlotsEnabled != tile.hasTransporter()) {
                icrafting.sendProgressBarUpdate(this, 0, tile.hasTransporter() ? 1 : 0);
            }
        }
        this.transporterSlotsEnabled = tile.hasTransporter();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        if(id == 0) {
            if(value == 1) {
                this.enableTransporterInventory();
            } else {
                this.disableTransporterInventory();
            }
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNr) {
        ItemStack result = null;
        Slot slot = (Slot) this.inventorySlots.get(slotNr);

        if(slot != null && slot.getHasStack()) {
            ItemStack  itemstack = slot.getStack();
            result = itemstack.copy();

            if(slotNr < slotCount) {
                if(!this.mergeItemStack(itemstack, slotCount, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else {
                if(!this.mergeItemStack(itemstack, 0, slotCount, false)) {
                    return null;
                }
            }
            if(itemstack.stackSize <= 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
           slot.onPickupFromSlot(player, itemstack);
           tile.onInventoryChanged();
        }

        return result;
    }
}
