package Seremis.SoulCraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.structure.Structure;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.lib.Strings;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.util.structure.ModStructures;

public class TileStationController extends SCTileMagnetConnector implements IInventory, ISidedInventory {
    
    private Structure structure = ModStructures.magnetStation;
    
    public boolean isMultiBlock = false;
    
    private long currTime = 0;
    private long lastUpdateTick = 0;
    private long ticksBeforeUpdate = 10;
        
    public TileStationController() {
        
    }

    public boolean isValid() {
        return structure.doesRotatedStructureExistAtCoords(worldObj, ModBlocks.stationController, xCoord, yCoord, zCoord);
    }
    
    @Override
    public void invalidate() {
        invalidateMultiblock();
        super.invalidate();
    }

    public void invalidateMultiblock() {
        isMultiBlock = false;
    }

    public void initiateMultiblock() {
        isMultiBlock = true;
        Coordinate3D crystalStandCoord = structure.getBlockWorldCoordinates(ModBlocks.crystalStand, 0, worldObj, xCoord, yCoord, zCoord);
        
        TileEntity tile = worldObj.getBlockTileEntity((int) crystalStandCoord.x, (int) crystalStandCoord.y, (int) crystalStandCoord.z);
        System.out.println(crystalStandCoord);
        if(tile != null && tile instanceof IMagnetConnector)
        MagnetLinkHelper.instance.addLink(new MagnetLink(this, (IMagnetConnector)tile));
        System.out.println("Multiblock!");
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(CommonProxy.proxy.isRenderWorld(worldObj))
            return;
        currTime++;
        if(lastUpdateTick + ticksBeforeUpdate <= currTime) {
            lastUpdateTick = currTime;
            if(this.isValid()) {
                initiateMultiblock();
            } else {
                invalidateMultiblock();
                System.out.println();
            }
        }
    }
    
    //IInventory//
    
    private ItemStack[] inv = new ItemStack[9];
    
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
        inv[slot].stackSize =- amount; 
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
        return Strings.INV_STATION_CONTROLLER_NAME;
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

    //ISidedInventory//
    
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    }


    @Override
    public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
        return true;
    }


    @Override
    public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
        return true;
    }

    //TileMagnetConnector//
    
    @Override
    public double getRange() {
        return 0;
    }

    @Override
    public boolean connectToSide(ForgeDirection direction) {
        return true;
    }

    @Override
    public boolean connectToConnector(IMagnetConnector connector) {
        return isMultiBlock && MagnetLinkHelper.instance.getLinksConnectedTo(this).size() == 0;
    }

    @Override
    public int getHeatLossPerTick() {
        return 0;
    }

    @Override
    public int getMaxHeat() {
        return 0;
    }
}
