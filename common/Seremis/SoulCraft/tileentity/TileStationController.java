package Seremis.SoulCraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import Seremis.SoulCraft.api.util.structure.Structure;
import Seremis.SoulCraft.core.lib.Strings;
import Seremis.SoulCraft.util.structure.ModStructures;

public class TileStationController extends SCTileEntity implements IInventory, ISidedInventory {
    
    private Structure structure = ModStructures.magnetStation;
    
    public boolean isMultiBlock = false;
        
    public TileStationController() {
        
    }

    
    public boolean getIsValid() {
        return structure.doesRotatedStructureExistAtCoords(worldObj, xCoord-2, yCoord, zCoord);
    }

    @Override
    public void invalidate() {
        isMultiBlock = false;
        super.invalidate();
    }

    public void initiateMultiblock() {
        isMultiBlock = true;
        System.out.println("Multiblock!");
    }

    //IInventory//
    
    private ItemStack[] inv = new ItemStack[9];
    
    @Override
    public int getSizeInventory() {
        return inv.length;
    }


    @Override
    public ItemStack getStackInSlot(int slot) {
        return inv[slot];
    }


    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        inv[slot].stackSize =- amount; 
        return inv[slot];
    }


    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }


    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inv[slot] = stack;
    }


    @Override
    public String getInvName() {
        return Strings.INV_STATION_CONTROLLER_NAME;
    }


    @Override
    public boolean isInvNameLocalized() {
        return false;
    }


    @Override
    public int getInventoryStackLimit() {
        return 64;
    }


    @Override
    public void openChest() {}


    @Override
    public void closeChest() {}


    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return true;
    }

    //ISidedInventory//
    
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    }


    @Override
    public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
        return true;
    }


    @Override
    public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
        return true;
    }
}
