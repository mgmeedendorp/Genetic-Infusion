package Seremis.SoulCraft.tileentity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;

public class TileTransporter extends SCTileEntity implements IInventory, ISidedInventory {

    private ItemStack[] inv = new ItemStack[9];
    
    private boolean hasEngine;
    private boolean hasInventory;
    private float speed = 1.0F;
    public ForgeDirection direction = ForgeDirection.NORTH;
    
    public TileTransporter() {
        this(false, false);
    }
    
    public TileTransporter(boolean engine, boolean inventory) {
        hasEngine = engine;
        hasInventory = inventory;
        if(hasEngine) {
            speed = 5.0F;
        }
    }
    
    public void setHasInventory(boolean inventory) {
        this.hasInventory = inventory;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    public void setHasEngine(boolean engine) {
        this.hasEngine = engine;
        if(hasEngine) {
            speed = 5.0F;
        }
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public boolean hasEngine() {
        return hasEngine;
    }
    
    public boolean hasInventory() {
        return hasInventory;
    }
    
    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
        readFromNBT(packet.customParam1);
    }
    
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound var1 = new NBTTagCompound();
        writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
    }
    
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return hasInventory ? new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9} : null;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return hasInventory;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return hasInventory;
    }

    @Override
    public int getSizeInventory() {
        return hasInventory ? inv.length : 0;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return hasInventory ? inv[slot] : null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if(hasInventory) {
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
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if(hasInventory) {
            inv[slot] = stack;
            if(stack != null && stack.stackSize > getInventoryStackLimit()) {
                stack.stackSize = getInventoryStackLimit();
            }
        }
    }

    @Override
    public String getInvName() {
        return hasInventory ? "plasmaticTransporter" : null;
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
    public boolean isStackValidForSlot(int slot, ItemStack stack) {
        return hasInventory ? inv[slot].itemID == stack.itemID : null;
    }
    
    public boolean setInventorySlot(int slot, ItemStack stack) {
        if(hasInventory) {
            ItemStack currStack = getStackInSlot(slot);
            if(currStack == null) {
                inv[slot] = stack;
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                return true;
            }
            if(currStack.stackSize > getInventoryStackLimit()) {
                ItemStack tooMuch = new ItemStack(getStackInSlot(slot).itemID, currStack.stackSize-getInventoryStackLimit(), currStack.getItemDamage());
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
        return false;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        hasInventory = compound.getBoolean("hasInventory");
        hasEngine = compound.getBoolean("hasEngine");
        direction = ForgeDirection.values()[compound.getInteger("direction")];
        if(hasInventory) {
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
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setBoolean("hasInventory", hasInventory);
        compound.setBoolean("hasEngine", hasEngine);
        compound.setInteger("direction", direction.ordinal());
        if(hasInventory) {
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
    }
}
