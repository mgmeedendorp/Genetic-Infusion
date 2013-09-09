package Seremis.SoulCraft.tileentity;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.mod_SoulCraft;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.structure.IStructureChangeReceiver;
import Seremis.SoulCraft.api.util.structure.Structure;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.lib.GuiIds;
import Seremis.SoulCraft.core.lib.Strings;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.util.UtilTileEntity;
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
        onInventoryChanged();
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        UtilTileEntity.writeInventoryToNBT(this, compound);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inv = UtilTileEntity.readInventoryFromNBT(this, compound);
    }
    
    //IInventory//
    private Coordinate3D transporterCoord;    
    
    private ItemStack[] inv = new ItemStack[13];
    
    public int activeTab = -1;
    
    public float transporterSpeed = 1.0F;
    
    public EntityPlayer player;
    
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
        ItemStack itemstack = getStackInSlot(slot);
        
        if (itemstack != null) {
            if (itemstack.stackSize <= amount) {
                setInventorySlotContents(slot, null);
            }else{
                itemstack = itemstack.splitStack(amount);
                onInventoryChanged();
            }
        }

        return itemstack;
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
        
        onInventoryChanged();
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
        return slot % 2 == 0 ? getStackInSlot(slot) != null ? !(getStackInSlot(slot).stackSize >= 1) ? stack != null ? stack.stackSize == 1 ? stack.itemID == ModBlocks.transporter.blockID : false : false : false : true : true;
    }
    
    public ItemStack[] getInventory() {
        return inv;
    }
    
    public float getTransporterSpeed() {
        return transporterSpeed;
    }
    
    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
        if(CommonProxy.proxy.isRenderWorld(worldObj))
            return;
        
        if(!hasTransporter()) {
            if(getStackInSlot(0) != null && getStackInSlot(0).itemID == ModBlocks.transporter.blockID) {                
                worldObj.setBlock((int)getTransporterCoordinate().x, (int)getTransporterCoordinate().y, (int)getTransporterCoordinate().z, ModBlocks.transporter.blockID, 0, 3);
                
                TileEntity tile = worldObj.getBlockTileEntity((int)getTransporterCoordinate().x, (int)getTransporterCoordinate().y, (int)getTransporterCoordinate().z);
                
                if(tile != null && tile instanceof TileTransporter) {
                    ((TileTransporter)tile).setDirection(convertStructureRotationToForgeDirection(structure.getRotation()));
                    initiateTransporterInventory();
                }
            }
        } else if(getStackInSlot(0) == null) {
            int id = worldObj.getBlockId((int)getTransporterCoordinate().x, (int)getTransporterCoordinate().y, (int)getTransporterCoordinate().z);
            if(id == ModBlocks.transporter.blockID) {
                worldObj.setBlock((int)getTransporterCoordinate().x, (int)getTransporterCoordinate().y, (int)getTransporterCoordinate().z, 0, 0, 3);
                removeTransporterInventory();
            }
        } else if(getStackInSlot(0) != null && getStackInSlot(0).itemID == ModBlocks.transporter.blockID) {
            updateModules();
        }
    }
    
    public TileTransporter getTransporter() {
        if(CommonProxy.proxy.isServerWorld(worldObj)) {
            Coordinate3D pos = getTransporterCoordinate();
            TileEntity tile = worldObj.getBlockTileEntity((int)pos.x, (int)pos.y, (int)pos.z);
            if(tile != null && tile instanceof TileTransporter) {
                return (TileTransporter)tile;
            }
        }
        return null;
    }
    
    public Coordinate3D getTransporterCoordinate() {
        if(transporterCoord == null) {
            calculateTransporterCoordinate();
        }
        return transporterCoord;
    }
    
    public boolean showTransporterInventory() {
        return this.activeTab == 0;
    }
    
    public boolean hasTransporter() {
        return structure.doesBlockExistInStructure(ModBlocks.transporter, 0, 1);
    }
    
    private void calculateTransporterCoordinate() {
        transporterCoord = new Coordinate3D().setCoords(this);
        
        Coordinate3D coord = structure.getBlockCoordinates(ModBlocks.transporter, 0).get(0);
        Coordinate3D coord2 = structure.getBlockCoordinates(ModBlocks.stationController, 0).get(0);
        
        transporterCoord.moveBack(coord2);
        transporterCoord.move(coord);
    }
    
    private ForgeDirection convertStructureRotationToForgeDirection(int rotation) {
        ForgeDirection direction = ForgeDirection.NORTH;
        switch(rotation) {
            case 0 : {
                direction = ForgeDirection.SOUTH;
                break;
            }
            case 1 : {
                direction = ForgeDirection.WEST;
                break;
            }
            case 2 : {
                direction = ForgeDirection.NORTH;
                break;
            }
            case 3 : {
                direction = ForgeDirection.EAST;
                break;
            }
        }
        return direction;
    }
    
    private void updateModules() { 
        TileTransporter tile = getTransporter();
        
        tile.setHasEngine(false);
        tile.setHasInventory(false);
        
        for(int i = 0; i < 3; i++) {
            if(tile != null) {
                if(getStackInSlot(i+1) != null && getStackInSlot(i+1).itemID == ModItems.transporterModules.itemID) {
                    if(getStackInSlot(i+1).getItemDamage() == 0) {
                        tile.setHasInventory(true);
                    } else {
                        tile.setHasEngine(true);
                    }
                }
            }
        }
        this.transporterSpeed = tile.getSpeed();
    }
    
    private void initiateTransporterInventory() {
        TileTransporter tile = getTransporter();
        
        if(tile != null) {
            for(int i = 0; i < 9; i++) {
                setInventorySlotContents(i+4, tile.inv[i]);
            }
            if(tile.hasEngine()) {
                this.setInventorySlotContents(1, new ItemStack(ModItems.transporterModules, 1, 1));
            }
            if(tile.hasInventory()) {
                this.setInventorySlotContents(2, new ItemStack(ModItems.transporterModules, 1, 0));
            }
        }
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    private void removeTransporterInventory() {
        for(int i = 0; i < 12; i++) {
            setInventorySlotContents(i+1, null);
        }
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    public void setTileData(int id, int data){
        if(id == 0) {
            activeTab = data;
        }
        if(id == 1) {
            if(data == 0) {
                if(player != null) {
                    player.openGui(mod_SoulCraft.instance, GuiIds.GUI_STATION_SEND_SCREEN_ID, worldObj, xCoord, yCoord, zCoord);
                }
            }
        }
    }
    
    //ISidedInventory//
    
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] {0};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return slot == 0 ? stack.itemID == ModBlocks.transporter.blockID : false;
    }


    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot == 0 ? stack.itemID == ModBlocks.transporter.blockID : false;
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
