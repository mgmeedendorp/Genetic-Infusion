package Seremis.SoulCraft.tileentity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import Seremis.SoulCraft.items.ModItems;

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
		if(stack != null)
		{
			if(stack.stackSize <= amount)
			{
				setInventorySlotContents(slot, null);
			} 
			else
			{
				stack = stack.splitStack(amount);
				if(stack.stackSize <= 0)
				{
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
		return "Compressor";
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
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        NBTTagList nbtTagList = compound.getTagList("Items");
        this.inv = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbtTagList.tagCount(); ++i)
        {
            NBTTagCompound compound2 = (NBTTagCompound)nbtTagList.tagAt(i);
            int var5 = compound2.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.inv.length)
            {
                this.inv[var5] = ItemStack.loadItemStackFromNBT(compound2);
            }
        }
    }

	@Override
	public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        NBTTagList nbtTagList = new NBTTagList();

        for (int i = 0; i < this.inv.length; ++i)
        {
            if (this.inv[i] != null)
            {
                NBTTagCompound compound2 = new NBTTagCompound();
                compound2.setByte("Slot", (byte)i);
                this.inv[i].writeToNBT(compound2);
                nbtTagList.appendTag(compound2);
            }
        }
        compound.setTag("Items", nbtTagList);
    }
	
	public boolean setInventorySlot(int slot, ItemStack stack) {
		ItemStack currStack = getStackInSlot(slot);
		if(currStack == null) {
			inv[0] = stack;
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			return true;
		}
		if(currStack.stackSize > getInventoryStackLimit()) {
			ItemStack tooMuch = new ItemStack(getStackInSlot(0).itemID, currStack.stackSize-getInventoryStackLimit(), currStack.getItemDamage());
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, tooMuch));
			currStack.stackSize = getInventoryStackLimit();
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			return false;
		}
		if(currStack.stackSize < 0) {
			inv[0] = null;
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		if(currStack.getItem() == stack.getItem() && currStack.getItemDamage() == stack.getItemDamage()) {
			inv[0].stackSize += stack.stackSize;
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
 			return true;
		}
		return false;
	}
	
	/**
     * Returns true if there is a player in range (using World.getClosestPlayer)
     */
    public boolean anyPlayerInRange()
    {
        return this.worldObj.getClosestPlayer((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D, (double)this.requiredPlayerRange) != null;
    }
    
    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
    	readFromNBT(packet.customParam1);
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
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
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		if(itemstack.itemID == ModItems.shardIsolatzium.itemID) {
			return true;
		}
		return false;
	}
}
