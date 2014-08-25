package seremis.geninfusion.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.block.ModBlocks;


public class TileItemIO extends GITile implements IInventory, ISidedInventory {
    
    public boolean isStructureMagnetStation = false;

    public TileStationController stationController;
    
    @Override
    public void updateEntity() {
        if(stationController != null) {
            isStructureMagnetStation = true;
            worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, ModBlocks.itemIO);
        } else {
            isStructureMagnetStation = false;
        }
    }
    
    //IInventory//
    
    @Override
    public int getSizeInventory() {
        if(isStructureMagnetStation)
            return stationController.inventory.getSizeInventory();
        return 0;
    }
    
    @Override
    public ItemStack getStackInSlot(int slot) {
        if(isStructureMagnetStation)
            return stationController.inventory.getStackInSlot(slot);
        return null;
    }
    
    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if(isStructureMagnetStation) {
            return stationController.inventory.decrStackSize(slot, amount);
        }
        return null;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if(isStructureMagnetStation)
            return stationController.inventory.getStackInSlotOnClosing(slot);
        return null;
    }
    
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if(isStructureMagnetStation)
            stationController.inventory.setInventorySlotContents(slot, stack);
    }
    
    @Override
    public String getInventoryName() {
        if(isStructureMagnetStation)
            return stationController.inventory.getInventoryName();
        return "";
    }
    
    @Override
    public boolean hasCustomInventoryName() {
        return isStructureMagnetStation && stationController.inventory.hasCustomInventoryName();
    }
    
    @Override
    public int getInventoryStackLimit() {
        if(isStructureMagnetStation)
           return stationController.inventory.getInventoryStackLimit();
        return 0;
    }
    
    @Override
    public void openInventory() {}
    
    @Override
    public void closeInventory() {}
    
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return isStructureMagnetStation && stationController.isItemValidForSlot(slot, stack);
    }

    //ISidedInventory//
    
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        if(isStructureMagnetStation)
            return stationController.getAccessibleSlotsFromSide(side);
        return new int[] {};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return isStructureMagnetStation && stationController.canInsertItem(slot, stack, side);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return isStructureMagnetStation && stationController.canExtractItem(slot, stack, side);
    }
}
