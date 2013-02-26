package voidrunner101.SoulCraft.common.tileentity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import voidrunner101.SoulCraft.common.core.DefaultProps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileCompressor extends SCTileEntity implements IInventory {

	private ItemStack[] inv;
    private int requiredPlayerRange = DefaultProps.CompressorRenderDistance;
	
	public TileCompressor() {
		inv = new ItemStack[1];
	}
	
	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
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
		return 4096;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.inv = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.inv.length)
            {
                this.inv[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.inv.length; ++var3)
        {
            if (this.inv[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.inv[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("Items", var2);
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
			return false;
		}
		if(currStack.stackSize < 0) {
			inv[0] = null;
		}
		if(currStack.getItem() == stack.getItem() && currStack.getItemDamage() == stack.getItemDamage()) {
			inv[0].stackSize += stack.stackSize;
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
}
