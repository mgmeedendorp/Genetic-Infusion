package voidrunner101.SoulCraft.common.tileentity;

import voidrunner101.SoulCraft.common.proxy.CommonProxy;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileCompressor extends SCTileEntity implements IInventory {

	private ItemStack[] inv;
	
	public TileCompressor() {
		inv = new ItemStack[1];
	}
	
	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if(slot > getSizeInventory()) {
			return null;
		}
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
				if(stack.stackSize == 0)
				{
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if(stack != null)
		{
			setInventorySlotContents(slot, null);
		}
		return stack;
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
		return 1024;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory");

		for(int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("Slot");
			if(slot >= 0 && slot < inv.length) {
				inv[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		NBTTagList itemList = new NBTTagList();

		for(int i = 0; i < inv.length; i++) {
			ItemStack stack = inv[i];
			if(stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
	}
	
	public boolean insertItemInSlot(ItemStack stack, int slot) {
		if(slot < inv.length) {
			ItemStack currStack = this.getStackInSlot(slot);
			if(stack == null) {return false;}
			if(currStack == null) {
				setInventorySlotContents(slot, stack);
				return true;
			}
			if(currStack.stackSize == 0){currStack = null; insertItemInSlot(stack, slot);}
			if(currStack.getItem().itemID == stack.getItem().itemID) {
				currStack.stackSize += stack.stackSize;
				return true;
			}
		}
		return false;
	}
	
	public boolean getContent(EntityPlayer player, int slot) {
		if(CommonProxy.proxy.isServerWorld(player.worldObj)){return true;}
		if(slot < inv.length && inv[slot] != null) {
			ItemStack currStack = inv[slot];
			player.addChatMessage("This Compressor contains " + currStack.stackSize + " " + currStack.getItem().getItemName());
		} else {
			player.addChatMessage("This Compressor does not contain anything.");
		}
		return true;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}	
}
