package seremis.geninfusion.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.inventory.slot.FilteredSlot;
import seremis.geninfusion.inventory.slot.ToggleableMoveSlot;
import seremis.geninfusion.inventory.slot.ToggleableMoveUpgradeSlot;
import seremis.geninfusion.item.ModItems;
import seremis.geninfusion.tileentity.TileStationController;

public class ContainerStationControllerTransporter extends GIContainer {

    private TileStationController tile;

    private boolean transporterUpgradesEnabled = false;
    private boolean transporterSlotsEnabled = false;
    private float transporterSpeed = 1.0F;
    private int heat = 0;

    public ContainerStationControllerTransporter(EntityPlayer player, IInventory tile) {
        this.tile = (TileStationController) tile;
        this.tile.player = player;

        addSlotToContainer(new FilteredSlot(tile, 0, 80, 19, new ItemStack(ModItems.transporterModules, 1, 2), 1));

        addTransporterInventory();
        addPlayerInventory(player, 56);
    }

    private void addTransporterInventory() {
        for(int i = 0; i < 3; i++) {
            ToggleableMoveUpgradeSlot slot = new ToggleableMoveUpgradeSlot(tile, i + 1, 62 + 18 * i, 45);
            slot.disable();
            addSlotToContainer(slot);
        }
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                ToggleableMoveSlot slot = new ToggleableMoveSlot(tile, i * 3 + j + 4, 62 + 18 * i, 73 + 18 * j);
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
        iCrafting.sendProgressBarUpdate(this, 3, tile.getHeat());
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
            
            if(this.heat != tile.getHeat()) {
                icrafting.sendProgressBarUpdate(this, 3, tile.getHeat());
            }
        }
        this.transporterUpgradesEnabled = tile.hasTransporter();
        this.transporterSlotsEnabled = tile.hasTransporterInventory() && tile.showTransporterInventory();
        this.transporterSpeed = tile.getTransporterSpeed();
        this.heat = tile.getHeat();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        if(id == 0) {
            if(value == 1) {
                enableUpgradeSlots();
                this.transporterUpgradesEnabled = true;
                tile.markDirty();
            } else {
                disableUpgradeSlots();
                disableToggleSlots();
                this.transporterUpgradesEnabled = false;
                tile.markDirty();
            }
        } else if(id == 1) {
            if(value == 1) {
                enableToggleSlots();
                this.transporterSlotsEnabled = true;
                tile.markDirty();
            } else {
                disableToggleSlots();
                this.transporterSlotsEnabled = false;
                tile.markDirty();
            }
        } else if(id == 2) {
            this.transporterSpeed = value / 100;
            tile.transporterSpeed = value / 100;
        } else if(id == 3) {
            tile.barHeat = value;
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
