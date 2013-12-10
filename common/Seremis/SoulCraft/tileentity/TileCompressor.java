package Seremis.SoulCraft.tileentity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import Seremis.SoulCraft.core.lib.Tiles;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.util.UtilTileEntity;

public class TileCompressor extends TileEntity implements IInventory {

    private ItemStack[] inv;
    private int requiredPlayerRange = 16;

    public TileCompressor() {
        inv = new ItemStack[1];
    }

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
        inv[slot] = stack;
        if(stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return Tiles.INV_COMPRESSOR_UNLOCALIZED_NAME;
    }

    @Override
    public int getInventoryStackLimit() {
        return 4096;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
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

    public boolean setInventorySlot(int slot, ItemStack stack) {
        ItemStack currStack = getStackInSlot(slot);
        if(currStack == null) {
            inv[slot] = stack;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return true;
        }
        if(currStack.stackSize > getInventoryStackLimit()) {
            ItemStack tooMuch = new ItemStack(getStackInSlot(slot).itemID, currStack.stackSize - getInventoryStackLimit(), currStack.getItemDamage());
            this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, tooMuch));
            currStack.stackSize = getInventoryStackLimit();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return false;
        }
        if(currStack.stackSize < 0) {
            inv[slot] = null;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
        if(currStack.getItem() == stack.getItem() && currStack.getItemDamage() == stack.getItemDamage()) {
            inv[slot].stackSize += stack.stackSize;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return true;
        }
        return false;
    }

    /**
     * Returns true if there is a player in range (using World.getClosestPlayer)
     */
    public boolean anyPlayerInRange() {
        return this.worldObj.getClosestPlayer(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, this.requiredPlayerRange) != null;
    }

    @Override
    public void openChest() {}

    @Override
    public void closeChest() {}

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        if(itemstack.itemID == ModItems.crystalShard.itemID) {
            return true;
        }
        return false;
    }
}
