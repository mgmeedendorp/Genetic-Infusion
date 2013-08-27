package Seremis.SoulCraft.tileentity;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.structure.IStructureChangeReceiver;
import Seremis.SoulCraft.api.util.structure.Structure;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.lib.Strings;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.util.structure.ModStructures;

public class TileStationController extends SCTileMagnetConnector implements IInventory, ISidedInventory, IStructureChangeReceiver {
    
    public Structure structure = new Structure(ModStructures.magnetStation);
    
    public boolean isMultiblock = false;
    
    private long currTime = 0;
    private long lastUpdateTick = 0;
    private long ticksBeforeUpdate = 60;
        
    public TileStationController() {

    }

    public boolean isValid() {
        return structure.doesStructureExist();
    }
    
    @Override
    public void invalidate() {
        invalidateMultiblock();
        super.invalidate();
    }

    public void invalidateMultiblock() {
        if(isMultiblock) {
            List<Coordinate3D> crystalStandCoordinates = structure.getBlockCoordinates(ModBlocks.crystalStand, 0);
            
            if(structure.doesBlockExistInStructure(ModBlocks.crystalStand, 0, 1)) {
                Coordinate3D crystalStandCoord = crystalStandCoordinates.get(0);
                TileEntity tile = worldObj.getBlockTileEntity((int) crystalStandCoord.x, (int) crystalStandCoord.y, (int) crystalStandCoord.z);
                if(tile != null && tile instanceof TileCrystalStand){
                    ((TileCrystalStand)tile).isStructureMagnetStation = false;
                    ((TileCrystalStand)tile).structure = null;
                }
            }
        }
        isMultiblock = false;
    }

    public void initiateMultiblock() {
        if(!isMultiblock) {
            List<Coordinate3D> crystalStandCoordinates = structure.getBlockCoordinates(ModBlocks.crystalStand, 0);
            if(structure.doesBlockExistInStructure(ModBlocks.crystalStand, 0, 1)) {
                Coordinate3D crystalStandCoord = crystalStandCoordinates.get(0);
                
                TileEntity tile = worldObj.getBlockTileEntity((int) crystalStandCoord.x, (int) crystalStandCoord.y, (int) crystalStandCoord.z);
                if(tile != null && tile instanceof TileCrystalStand){
                    MagnetLinkHelper.instance.addLink(new MagnetLink(this, (TileCrystalStand)tile));
                    
                    ((TileCrystalStand)tile).isStructureMagnetStation = true;
                    ((TileCrystalStand)tile).structure = structure;

                    isMultiblock = true;
                }
            } else {
                isMultiblock = false;
            }
        }
    }

    @Override
    public void updateEntity() {
        if(CommonProxy.proxy.isRenderWorld(worldObj))
            return;
        
        if(currTime == 0) {
            structure.initiate(worldObj, new Coordinate3D(xCoord, yCoord, zCoord), ModBlocks.stationController, 0);
            structure.notifyChangesTo(this);
        }

        currTime++;
        if(lastUpdateTick + ticksBeforeUpdate <= currTime) {
            lastUpdateTick = currTime;
            if(this.isValid()) {
                if(!isMultiblock)
                    initiateMultiblock();
            } else {
                if(isMultiblock)
                    invalidateMultiblock();
            }
        }
    }
    
    @Override
    public void onStructureChange() {
        List<Coordinate3D> crystalStandCoordinates = structure.getBlockCoordinates(ModBlocks.crystalStand, 0);
        
        if(structure.doesBlockExistInStructure(ModBlocks.crystalStand, 0, 1)) {
            Coordinate3D crystalStandCoord = crystalStandCoordinates.get(0);
            
            TileEntity tile = worldObj.getBlockTileEntity((int) crystalStandCoord.x, (int) crystalStandCoord.y, (int) crystalStandCoord.z);
            if(tile != null && tile instanceof TileCrystalStand){
                MagnetLinkHelper.instance.addLink(new MagnetLink(this, (TileCrystalStand)tile));
                ((TileCrystalStand)tile).isStructureMagnetStation = true;
                ((TileCrystalStand)tile).structure = structure;
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
        return 3;
    }
    
    @Override
    public boolean canConnect(MagnetLink link) {
        return isMultiblock & MagnetLinkHelper.instance.getLinksConnectedTo(this).size() <= 1;
    }

    @Override
    public int getHeatLossPerTick() {
        return 0;
    }

    @Override
    public int getMaxHeat() {
        return 0;
    }
    
    @Override
    public void linkUpdate() {}
}
