package seremis.soulcraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import seremis.soulcraft.core.lib.Tiles;
import seremis.soulcraft.entity.EntityTransporter;
import seremis.soulcraft.item.ModItems;
import seremis.soulcraft.util.UtilBlock;
import seremis.soulcraft.util.UtilTileEntity;


public class TileItemIO extends SCTile implements IInventory, ISidedInventory {
    
    public boolean isStructureMagnetStation = false;
   
    public ItemStack[] inv = new ItemStack[12];
    
    public void importTransporter(EntityTransporter transporter) {
        if(isInvEmpty()) {
            for(int i = 0; i<transporter.getInventory().length; i++) {
                inv[i] = transporter.getInventory()[i];
            }
            inv[9] = new ItemStack(ModItems.transporterModules, 1, 2);
            if(transporter.hasEngine())
                inv[10] = new ItemStack(ModItems.transporterModules, 1, 1);
            if(transporter.hasInventory()) 
                inv[11] = new ItemStack(ModItems.transporterModules, 1, 0);
        } else {
            UtilBlock.dropItemsFromTile(worldObj, xCoord, yCoord, zCoord);
            
            for(int i = 0; i<transporter.getInventory().length; i++) {
                inv[i] = transporter.getInventory()[i];
            }
            inv[9] = new ItemStack(ModItems.transporterModules, 1, 2);
            if(transporter.hasEngine())
                inv[10] = new ItemStack(ModItems.transporterModules, 1, 1);
            if(transporter.hasInventory()) 
                inv[11] = new ItemStack(ModItems.transporterModules, 1, 0);
        }
    }
    
    public boolean isInvEmpty() {
        for(ItemStack stack : inv) {
            if(stack != null) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void updateEntity() {
        if(isStructureMagnetStation && !isInvEmpty()) {
            TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);
            if(tile != null && tile instanceof IInventory) {
                IInventory inventory = (IInventory)tile;
                int index = 0;
                for(ItemStack stack : inv) {
                    if(stack != null && canInsertStack(inventory, stack)) {
                        insertStackFromSlotInInventory(inventory, index);
                    }
                    index++;
                }
            }
        }
    }
    
    public void insertStackFromSlotInInventory(IInventory target, int slot) {        
        int iteration = target.getSizeInventory();
        while(iteration > 0 && inv[slot] != null && inv[slot].stackSize > 0) {
            iteration--;
            
            ItemStack itemstack1 = target.getStackInSlot(iteration);
            ItemStack stack = inv[slot].copy();
            if(itemstack1 == null) {
                if(target.isItemValidForSlot(iteration, stack)) {
                    target.setInventorySlotContents(iteration, stack);
                    target.onInventoryChanged();
                    setInventorySlotContents(slot, null);
                    onInventoryChanged();
                }
            } else if(itemstack1.itemID == stack.itemID && itemstack1.getItemDamage() == stack.getItemDamage()) {
                if(itemstack1.getMaxStackSize() - itemstack1.stackSize > stack.stackSize) {
                    stack.stackSize += itemstack1.stackSize;
                    target.setInventorySlotContents(iteration, stack);
                    target.onInventoryChanged();
                    setInventorySlotContents(slot, null);
                    onInventoryChanged();
                } else {
                    stack.stackSize -= (itemstack1.getMaxStackSize() - itemstack1.stackSize);
                    itemstack1.stackSize = itemstack1.getMaxStackSize();
                    target.setInventorySlotContents(iteration, itemstack1);
                    target.onInventoryChanged();
                    setInventorySlotContents(slot, stack);
                    onInventoryChanged();
                }
            }
        }
    } 
    
    public boolean canInsertStack(IInventory target, ItemStack itemstack) {
        ItemStack stack = itemstack.copy();
        for(int slot = 0; slot < target.getSizeInventory(); slot++) {
            ItemStack itemstack1 = target.getStackInSlot(slot);
            
            if(itemstack1 == null) {
                if(target.isItemValidForSlot(slot, stack)) {
                    return true;
                }
            } else {
                if(itemstack1.itemID == stack.itemID && itemstack1.getItemDamage() == stack.getItemDamage()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inv = UtilTileEntity.readInventoryFromNBT(this, compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        UtilTileEntity.writeInventoryToNBT(this, compound);
    }
    
    //IInventory//
    
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
        if (slot < inv.length && inv[slot] != null) {
            if (inv[slot].stackSize > amount) {
                    ItemStack result = inv[slot].splitStack(amount);
                    onInventoryChanged();
                    return result;
            }
            ItemStack stack = inv[slot];
            setInventorySlotContents(slot, null);
            return stack;
        }
        return null;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }
    
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot >= inv.length) {
            return;
        }
        inv[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
                stack.stackSize = this.getInventoryStackLimit();
        }
        onInventoryChanged();
    }
    
    @Override
    public String getInvName() {
        return Tiles.INV_ITEM_IO_UNLOCALIZED_NAME;
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
        return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int amount) {
        return true;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int amount) {
        return true;
    }
    
    

}
