package Seremis.SoulCraft.tileentity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.item.ItemTransporterModules;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.util.UtilTileEntity;

public class TileTransporter extends SCTile implements IInventory, ISidedInventory {

    private ItemStack[] inv;

    private boolean hasEngine = false;
    private boolean hasInventory = false;
    private float speed = 1.0F;
    public ForgeDirection direction = ForgeDirection.WEST;

    public TileTransporter() {
        this(false, false);
    }

    public TileTransporter(boolean engine, boolean inventory) {
        inv = new ItemStack[12];
        hasInventory = inventory;
        hasEngine = engine;
        if(hasEngine) {
            speed = 5.0F;
        }
        validateModules();
    }

    public void setHasInventory(boolean inventory) {
        for(int i = 0; i < 3; i++) {
            if(getStackInSlot(i + 9) == null) {
                setInventorySlotContents(i + 9, new ItemStack(ModItems.transporterModules, 1, 0));
                break;
            }
        }
        this.hasInventory = inventory;
    }

    public void setHasEngine(boolean engine) {
        for(int i = 0; i < 3; i++) {
            if(getStackInSlot(i + 9) == null) {
                setInventorySlotContents(i + 9, new ItemStack(ModItems.transporterModules, 1, 1));
                break;
            }
        }
        if(engine) {
            speed = 5.0F;
        }
        this.hasEngine = engine;
        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    public void setDirection(ForgeDirection direction) {
        this.direction = direction;
        if(CommonProxy.proxy.isServerWorld(worldObj) && direction != null)
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.transporter.blockID, 2, direction.ordinal());
    }
    
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean hasEngine() {
        return hasEngine;
    }

    public boolean hasInventory() {
        return hasInventory;
    }

    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
        validateModules();
    }

    public void validateModules() {
        boolean engine = false;
        boolean inventory = false;
        for(int i = 0; i < 3; i++) {
            if(getStackInSlot(i + 9) != null && getStackInSlot(i + 9).itemID == ItemTransporterModules.engine().itemID && getStackInSlot(i + 9).getItemDamage() == ItemTransporterModules.engine().getItemDamage()) {
                engine = true;
                hasEngine = true;
            }
            if(getStackInSlot(i + 9) != null && getStackInSlot(i + 9).itemID == ItemTransporterModules.storage().itemID && getStackInSlot(i + 9).getItemDamage() == ItemTransporterModules.storage().getItemDamage()) {
                inventory = true;
                hasInventory = true;
            }
        }
        if(!engine) {
            hasEngine = false;
        }
        if(!inventory) {
            hasInventory = false;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return hasInventory ? new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 } : null;
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
        return inv.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return hasInventory ? inv[slot] : null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if(hasInventory) {
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
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return hasInventory ? null : inv[slot];
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
        return "plasmaticTransporter";
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
    
    public ItemStack addToInventorySlot(int slot, ItemStack stack) {
        ItemStack istack = getStackInSlot(slot);
        
        if(stack != null && istack == null) {
            setInventorySlotContents(slot, stack);
            
        } else if(stack != null  && stack.itemID == istack.itemID) {
            if(istack.stackSize+stack.stackSize > stack.getMaxStackSize()) {
                stack.stackSize += istack.stackSize;
                istack = stack.splitStack(istack.getMaxStackSize());
                setInventorySlotContents(slot, istack);
            } else {
                istack.stackSize += stack.stackSize;
                setInventorySlotContents(slot, istack);
            }
        }
        if(stack.stackSize <= 0) {
            stack = null;
        }
        
        return stack;
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

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        hasInventory = compound.getBoolean("hasInventory");
        hasEngine = compound.getBoolean("hasEngine");
        direction = ForgeDirection.values()[compound.getInteger("direction")];
        if(hasInventory) {
            this.inv = UtilTileEntity.readInventoryFromNBT(this, compound);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("hasInventory", hasInventory);
        compound.setBoolean("hasEngine", hasEngine);
        compound.setInteger("direction", direction.ordinal());
        if(hasInventory) {
            UtilTileEntity.writeInventoryToNBT(this, compound);
        }
    }

    public void emptyInventory() {
        inv = new ItemStack[12];
        if(hasEngine) {
            inv[10] = new ItemStack(ModItems.transporterModules, 1, 1);
        }
    }
    
    @Override
    public boolean receiveClientEvent(int eventId, int variable) {
        if(eventId == 2) {
            this.direction = ForgeDirection.values()[variable];
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            return true;
        } else {
            return super.receiveClientEvent(eventId, variable);
        }
    }
}
