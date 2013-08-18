package Seremis.SoulCraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.api.magnet.tile.TileMagnetConnector;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.util.UtilTileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileCrystalStand extends TileMagnetConnector implements IInventory {

    public ItemStack[] inv = new ItemStack[1];

    @Override
    public boolean connectToSide(ForgeDirection direction) {
        return direction != ForgeDirection.DOWN;
    }
    
    @Override
    public boolean connectToConnector(IMagnetConnector connector) {
        return true;
    }

    @Override
    public boolean canConnect() {
        return inv[0] != null && inv[0].itemID == ModBlocks.crystal.blockID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Coordinate3D applyBeamRenderOffset(Coordinate3D position, ForgeDirection side) {
        Coordinate3D finalPosition = super.applyBeamRenderOffset(position, side);
        finalPosition.y += 0.2D;
        return finalPosition;
    }
    
    @Override
    public double getRange() {
        return 5;
    }

    @Override
    public int getHeatLossPerTick() {
        return 2;
    }

    @Override
    public int getMaxHeat() {
        return 400;
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        if(getStackInSlot(0) == null || getStackInSlot(0).stackSize == 0) {
            this.heat = 0;
        }
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
        return "Crystal Stand";
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public void openChest() {}

    @Override
    public void closeChest() {}

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inv = UtilTileEntity.readInventoryFromNBT(this, compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        UtilTileEntity.writeInventoryToNBT(this, compound);
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
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return false;
    }
}
