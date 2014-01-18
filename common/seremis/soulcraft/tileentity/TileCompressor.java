package seremis.soulcraft.tileentity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import seremis.soulcraft.core.lib.Tiles;
import seremis.soulcraft.item.ModItems;
import seremis.soulcraft.util.inventory.Inventory;

public class TileCompressor extends SCTile implements IInventory {
    
    private int requiredPlayerRange = 16;

    private Inventory inventory = new Inventory(1, Tiles.INV_COMPRESSOR_UNLOCALIZED_NAME, 4096, this);

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return inventory.decrStackSize(slot, amount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inventory.getStackInSlotOnClosing(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory.setInventorySlotContents(slot, stack);
    }

    @Override
    public String getInvName() {
        return inventory.getInvName();
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        inventory.writeToNBT(compound);
    }

    public boolean setInventorySlot(int slot, ItemStack stack) {
        ItemStack currStack = getStackInSlot(slot);
        if(currStack == null) {
            setInventorySlotContents(slot, stack);
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
            setInventorySlotContents(slot, null);
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
        if(currStack.getItem() == stack.getItem() && currStack.getItemDamage() == stack.getItemDamage()) {
            ItemStack last = getStackInSlot(slot).copy();
            last.stackSize += stack.stackSize;
            setInventorySlotContents(slot, last);
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
        return inventory.isInvNameLocalized();
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        if(itemstack.itemID == ModItems.crystalShard.itemID) {
            return true;
        }
        return false;
    }
}
