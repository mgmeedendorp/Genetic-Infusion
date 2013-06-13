package Seremis.SoulCraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityTransporter extends Entity implements IInventory {

    public boolean isOpen = true;
    public ItemStack[] inv = new ItemStack[4];
    
    public EntityTransporter(World par1World) {
        super(par1World);
    }
    
    public EntityTransporter(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y + 0.5, z);
        System.out.println("transporter spawned at: " + x + " " + y + " "+ z);
    }

    @Override
    protected void entityInit() {
        
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, int i) {
        this.kill();
        return false;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public String getTexture() {
        return Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER;
    }
    
    @Override
    public boolean interact(EntityPlayer player) {
        if(CommonProxy.proxy.isServerWorld(worldObj)) {
            if(isOpen) {
                for(int i = 0; i < inv.length; i++) {
                    if(inv[i] == null || inv[i].stackSize == 0) {
                        isOpen = false;
                        return true;
                    }
                }
            } else {
                isOpen = true;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void onUpdate() {
        extinguish();
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        isOpen = compound.getBoolean("isOpen");
        
        NBTTagList var2 = compound.getTagList("Items");
        this.inv = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.inv.length) {
                this.inv[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setBoolean("isOpen", isOpen);
        
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.inv.length; ++var3) {
            if (this.inv[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.inv[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        compound.setTag("Items", var2);
    }
    
    //IInventory//

    @Override
    public int getSizeInventory() {
        return 4;
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
    public ItemStack getStackInSlot(int slot) {
        return inv[slot];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inv[slot];
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
        return "EntityTransporter";
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
    public void onInventoryChanged() {
        
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public void openChest() {}

    @Override
    public void closeChest() {}

    @Override
    public boolean isStackValidForSlot(int slot, ItemStack stack) {
        return true;
    }
}
