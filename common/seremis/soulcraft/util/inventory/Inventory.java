package seremis.soulcraft.util.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import seremis.soulcraft.util.UtilTileEntity;

public class Inventory implements IInventory {
    
    private ItemStack[] inventory;
    private String name;
    private int inventoryStackLimit;
    private TileEntity tile;
    
    public Inventory(int size, String name, int inventoryStackLimit, TileEntity tile) {
        inventory = new ItemStack[size];
        this.name = name;
        this.inventoryStackLimit = inventoryStackLimit;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = getStackInSlot(slot);
        if(stack != null) {
            if(stack.stackSize <= amount) {
                setInventorySlotContents(slot, null);
            } else {
                stack = stack.splitStack(amount);
                if(stack.stackSize <= 0) {
                    setInventorySlotContents(slot, null);
                }
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot >= getSizeInventory()) {
            return;
        }
        inventory[slot] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
                stack.stackSize = getInventoryStackLimit();
        }
        onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return name;
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return inventoryStackLimit;
    }

    @Override
    public void onInventoryChanged() {
        if(tile != null)
            tile.onInventoryChanged();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openChest() {}

    @Override
    public void closeChest() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return true;
    }

    public ItemStack[] getItemStacks() {
        return inventory;
    }
    
    public void writeToNBT(NBTTagCompound compound) {
        UtilTileEntity.writeInventoryToNBT(this, compound);
    }
    
    public void readFromNBT(NBTTagCompound compound) {
        inventory = UtilTileEntity.readInventoryFromNBT(this, compound);
    }
}
