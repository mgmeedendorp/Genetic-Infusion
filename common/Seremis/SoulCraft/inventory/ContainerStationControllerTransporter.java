package Seremis.SoulCraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import Seremis.SoulCraft.inventory.slot.FilteredSlot;
import Seremis.SoulCraft.inventory.slot.ToggleableMoveSlot;
import Seremis.SoulCraft.inventory.slot.ToggleableMoveUpgradeSlot;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.tileentity.TileStationController;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerStationControllerTransporter extends SCContainer {

    private TileStationController tile;

    private boolean transporterUpgradesEnabled = false;
    private boolean transporterSlotsEnabled = false;
    private float transporterSpeed = 1.0F;

    public ContainerStationControllerTransporter(EntityPlayer player, IInventory tile) {
        this.tile = (TileStationController) tile;
        this.tile.player = player;

        addSlotToContainer(new FilteredSlot(tile, 0, 80, 15, new ItemStack(ModItems.transporterModules, 1, 2), 1));

        addTransporterInventory();
        addPlayerInventory(player, 56);
    }

    private void addTransporterInventory() {
        for(int i = 0; i < 3; i++) {
            ToggleableMoveUpgradeSlot slot = new ToggleableMoveUpgradeSlot(tile, i + 1, 62 + (18 * i), 45);
            slot.disable();
            addSlotToContainer(slot);
        }
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                ToggleableMoveSlot slot = new ToggleableMoveSlot(tile, i * 3 + j + 4, 63 + (18 * i), 73 + (18 * j));
                slot.disable();
                addSlotToContainer(slot);
            }
        }
    }

    private void enableToggleSlots() {
        for(int i = 0; i < 9; i++) {
            ((ToggleableMoveSlot) inventorySlots.get(i + 4)).enable();
        }
    }

    private void enableUpgradeSlots() {
        for(int i = 0; i < 3; i++) {
            ((ToggleableMoveSlot) inventorySlots.get(i + 1)).enable();
        }
    }

    private void disableUpgradeSlots() {
        for(int i = 0; i < 3; i++) {
            ((ToggleableMoveSlot) inventorySlots.get(i + 1)).disable();
        }
    }

    private void disableToggleSlots() {
        for(int i = 0; i < 9; i++) {
            ((ToggleableMoveSlot) inventorySlots.get(i + 4)).disable();
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting iCrafting) {
        super.addCraftingToCrafters(iCrafting);
        iCrafting.sendProgressBarUpdate(this, 0, tile.hasTransporter() ? 1 : 0);
        iCrafting.sendProgressBarUpdate(this, 1, tile.hasTransporterInventory() && tile.showTransporterInventory() ? 1 : 0);
        iCrafting.sendProgressBarUpdate(this, 2, (int) (tile.getTransporterSpeed() * 100));
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for(int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);
            if(this.transporterUpgradesEnabled != tile.hasTransporter()) {
                icrafting.sendProgressBarUpdate(this, 0, tile.hasTransporter() ? 1 : 0);
                if(tile.hasTransporter()) {
                    enableUpgradeSlots();
                } else {
                    disableUpgradeSlots();
                    disableToggleSlots();
                }
            }

            boolean tileCondition = tile.hasTransporterInventory() && tile.showTransporterInventory();

            if(this.transporterSlotsEnabled != tileCondition) {
                icrafting.sendProgressBarUpdate(this, 1, tileCondition ? 1 : 0);
                if(tileCondition) {
                    enableToggleSlots();
                } else {
                    disableToggleSlots();
                }
            }

            if(this.transporterSpeed != tile.getTransporterSpeed()) {
                icrafting.sendProgressBarUpdate(this, 2, (int) (tile.getTransporterSpeed() * 100));
            }
        }
        this.transporterUpgradesEnabled = tile.hasTransporter();
        this.transporterSlotsEnabled = tile.hasTransporterInventory() && tile.showTransporterInventory();
        this.transporterSpeed = tile.getTransporterSpeed();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        if(id == 0) {
            if(value == 1) {
                this.enableUpgradeSlots();
                this.transporterUpgradesEnabled = true;
                tile.onInventoryChanged();
            } else {
                this.disableUpgradeSlots();
                this.disableToggleSlots();
                this.transporterUpgradesEnabled = false;
                tile.onInventoryChanged();
            }
        } else if(id == 1) {
            if(value == 1) {
                this.enableToggleSlots();
                this.transporterSlotsEnabled = true;
                tile.onInventoryChanged();
            } else {
                this.disableToggleSlots();
                this.transporterSlotsEnabled = false;
                tile.onInventoryChanged();
            }
        } else if(id == 2) {
            this.transporterSpeed = value / 100;
            tile.transporterSpeed = value / 100;
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNr) {
        ItemStack result = null;
        Slot slot = (Slot) this.inventorySlots.get(slotNr);

        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack = slot.getStack();
            result = itemstack.copy();

            if(slotNr < slotCount) {
                if(!mergeItemStack(itemstack, slotCount, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else {
                if(!mergeItemStack(itemstack, 0, slotCount, false)) {
                    return null;
                }
            }
            if(itemstack.stackSize <= 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return result;
    }
}
