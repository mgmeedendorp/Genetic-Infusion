package Seremis.SoulCraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import Seremis.SoulCraft.core.lib.Tiles;
import Seremis.SoulCraft.entity.EntityTransporter;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.util.UtilBlock;


public class TileItemIO extends SCTile implements IInventory {
    
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
        if(inv[slot] != null) {
            inv[slot].stackSize =- amount;
            if(inv[slot].stackSize < 0) {
                inv[slot] = null;
            }
        }
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
    
    

}
