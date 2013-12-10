package Seremis.SoulCraft.tileentity;

import java.nio.ByteBuffer;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import Seremis.SoulCraft.mod_SoulCraft;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.structure.IStructureChangeReceiver;
import Seremis.SoulCraft.api.util.structure.Structure;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.lib.GuiIds;
import Seremis.SoulCraft.core.lib.Tiles;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.entity.EntityTransporter;
import Seremis.SoulCraft.entity.logic.EntityTransporterLogic;
import Seremis.SoulCraft.event.TransporterSendEvent;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.util.UtilTileEntity;
import Seremis.SoulCraft.util.structure.ModStructures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileStationController extends SCTileMagnetConsumer implements IInventory, ISidedInventory, IStructureChangeReceiver {

    public Structure structure = new Structure(ModStructures.magnetStation);

    public boolean isMultiblock = false;

    public String name;

    private long currTime = 0;
    private long lastUpdateTick = 0;
    private long ticksBeforeUpdate = 60;
    
    private boolean heatNeeded = false;
    private TileStationController tempDestination;
    private final int neededHeat = 1000;
    
    private TileItemIO itemIO;
    
    @SideOnly(Side.CLIENT)
    public int barHeat = 0;
    
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
            List<Coordinate3D> itemIOCoordinates = structure.getBlockCoordinates(ModBlocks.itemIO, 0);
            
            if(structure.doesBlockExistInStructure(ModBlocks.crystalStand, 0, 1)) {
                Coordinate3D crystalStandCoord = crystalStandCoordinates.get(0);
                TileEntity tile = worldObj.getBlockTileEntity((int) crystalStandCoord.x, (int) crystalStandCoord.y, (int) crystalStandCoord.z);
                if(tile != null && tile instanceof TileCrystalStand) {
                    ((TileCrystalStand) tile).isStructureMagnetStation = false;
                    ((TileCrystalStand) tile).structure = null;
                }
                sendTileDataToClient(4, new byte[] {0});
                
                Coordinate3D itemIOCoord = itemIOCoordinates.get(0);
                
                TileEntity itemIO = worldObj.getBlockTileEntity((int) itemIOCoord.x, (int) itemIOCoord.y, (int) itemIOCoord.z);
                
                if(itemIO != null && itemIO instanceof TileItemIO) {
                    TileItemIO tileIO = (TileItemIO)itemIO;
                    
                    tileIO.isStructureMagnetStation = false;
                    this.itemIO = null;
                }
            }
        }
        isMultiblock = false;
    }

    public void initiateMultiblock() {
        if(!isMultiblock && CommonProxy.proxy.isServerWorld(worldObj)) {
            
            List<Coordinate3D> crystalStandCoordinates = structure.getBlockCoordinates(ModBlocks.crystalStand, 0);
            List<Coordinate3D> itemIOCoordinates = structure.getBlockCoordinates(ModBlocks.itemIO, 0);
            
            if(structure.doesBlockExistInStructure(ModBlocks.crystalStand, 0, 1) && structure.doesBlockExistInStructure(ModBlocks.itemIO, 0, 1)) {
                Coordinate3D crystalStandCoord = crystalStandCoordinates.get(0);

                TileEntity tile = worldObj.getBlockTileEntity((int) crystalStandCoord.x, (int) crystalStandCoord.y, (int) crystalStandCoord.z);

                if(tile != null && tile instanceof TileCrystalStand) {

                    ((TileCrystalStand) tile).isStructureMagnetStation = true;
                    ((TileCrystalStand) tile).structure = structure;

                    MagnetLinkHelper.instance.addLink(new MagnetLink(this, (TileCrystalStand) tile));
                    sendTileDataToClient(4, new byte[] {1});
                } else {
                    isMultiblock = false;
                    return;
                }
                
                Coordinate3D itemIOCoord = itemIOCoordinates.get(0);
                
                TileEntity itemIO = worldObj.getBlockTileEntity((int) itemIOCoord.x, (int) itemIOCoord.y, (int) itemIOCoord.z);
                
                if(itemIO != null && itemIO instanceof TileItemIO) {
                    TileItemIO tileIO = (TileItemIO)itemIO;
                    
                    tileIO.isStructureMagnetStation = true;
                    this.itemIO = tileIO;
                } else {
                    isMultiblock = false;
                    return;
                }
                isMultiblock = true;
            }
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(CommonProxy.proxy.isRenderWorld(worldObj)) {
            return;
        }

        if(currTime == 0) {
            structure.initiate(worldObj, new Coordinate3D(xCoord, yCoord, zCoord), ModBlocks.stationController, 0);
            structure.notifyChangesTo(this);
        }

        currTime++;
        if(lastUpdateTick + ticksBeforeUpdate <= currTime) {
            lastUpdateTick = currTime;

            if(isValid()) {
                if(!isMultiblock) {
                    initiateMultiblock();
                }
            } else {
                if(isMultiblock) {
                    invalidateMultiblock();
                }
            }
        }
        
        if(heat == neededHeat) {
            sendCurrentTransporterTo(tempDestination);
            heatNeeded = false;
            heat = 0;
            tempDestination = null;
        }
    }

    @Override
    public void onStructureChange() {
        List<Coordinate3D> crystalStandCoordinates = structure.getBlockCoordinates(ModBlocks.crystalStand, 0);

        if(structure.doesBlockExistInStructure(ModBlocks.crystalStand, 0, 1)) {
            Coordinate3D crystalStandCoord = crystalStandCoordinates.get(0);

            TileEntity tile = worldObj.getBlockTileEntity((int) crystalStandCoord.x, (int) crystalStandCoord.y, (int) crystalStandCoord.z);
            if(tile != null && tile instanceof TileCrystalStand) {
                MagnetLinkHelper.instance.addLink(new MagnetLink(this, (TileCrystalStand) tile));
                ((TileCrystalStand) tile).isStructureMagnetStation = true;
                ((TileCrystalStand) tile).structure = structure;
            }
        }
        onInventoryChanged();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        UtilTileEntity.writeInventoryToNBT(this, compound);
        if(name != null && name != "") {
            compound.setString("name", name);
        }
        compound.setBoolean("heatNeeded", heatNeeded);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inv = UtilTileEntity.readInventoryFromNBT(this, compound);
        if(compound.hasKey("name")) {
            name = compound.getString("name");
        } else {
            name = "";
        }
        heatNeeded = compound.getBoolean("heatNeeded");
    }

    // IInventory//
    private ItemStack[] inv = new ItemStack[13];

    public int activeTab = 0;

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

        if(itemstack != null) {
            if(itemstack.stackSize <= amount) {
                setInventorySlotContents(slot, null);
            } else {
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
        return Tiles.INV_STATION_CONTROLLER_UNLOCALIZED_NAME;
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
        // TODO change this
        return true;
    }

    public ItemStack[] getInventory() {
        return inv;
    }

    public float getTransporterSpeed() {
        return hasTransporterEngines() ? 5.0F : 1.0F;
    }

    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
        if(CommonProxy.proxy.isRenderWorld(worldObj)) {
            return;
        }

        if(!hasTransporter() && hasTransporterInventory() || hasTransporterEngines() && !hasTransporter()) {
            emptySlots(1, 13);
        }
    }

    public void emptySlots(int start, int end) {
        if(start < 0) {
            start = 0;
        }
        if(start > getSizeInventory()) {
            start = getSizeInventory();
        }
        if(end < 0) {
            end = 0;
        }
        if(end > getSizeInventory()) {
            end = getSizeInventory();
        }

        for(int i = start; i < end; i++) {
            inv[i] = null;
        }
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public boolean showTransporterInventory() {
        return this.activeTab == 1;
    }

    public boolean hasTransporter() {
        return getStackInSlot(0) != null;
    }

    public boolean hasTransporterInventory() {
        for(int i = 1; i < 4; i++) {
            if(getStackInSlot(i) != null && getStackInSlot(i).getItemDamage() == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasTransporterEngines() {
        for(int i = 1; i < 4; i++) {
            if(getStackInSlot(i) != null && getStackInSlot(i).getItemDamage() == 1) {
                return true;
            }
        }
        return false;
    }

    public void sendCurrentTransporterTo(TileStationController tile) {
        if(CommonProxy.proxy.isServerWorld(worldObj) && tile != this) {
            if(heat < neededHeat) {
                heatNeeded = true;
                tempDestination = tile;
                return;
            }
            
            EntityTransporterLogic logic = EntityTransporterLogic.getLogic(this, tile);

            EntityTransporter transporter = new EntityTransporter(worldObj, xCoord, yCoord, zCoord, logic);

            ItemStack[] inventory = new ItemStack[9];

            for(int i = 0; i < 9; i++) {
                inventory[i] = inv[i + 4];
            }

            if(hasTransporterInventory()) {
                transporter.setHasInventory(true);
            }
            if(hasTransporterEngines()) {
                transporter.setHasEngine(true);
            }

            transporter.setYaw(structure.getRotation() * 90F);

            transporter.setInventory(inventory);
            
            transporter.setHeat(heat);
            
            worldObj.spawnEntityInWorld(transporter);

            MinecraftForge.EVENT_BUS.post(new TransporterSendEvent(transporter, this, tile));

            emptySlots(0, 13);
        }
    }

    public void handleIncoming(EntityTransporter transporter) {
        if(itemIO != null)
            itemIO.importTransporter(transporter);
    }

    @Override
    public void setTileDataFromClient(int id, byte[] data) {
        if(id == 0) {
            activeTab = data[0];
        }
        if(id == 1) {
            if(data[0] == 0) {
                if(player != null) {
                    player.openGui(mod_SoulCraft.instance, GuiIds.GUI_STATION_SEND_SCREEN_ID, worldObj, xCoord, yCoord, zCoord);
                }
            }
        }
        if(id == 2) {
            name = "";
            if(data.length > 0) {
                name = new String(data);
            }
        }
        if(id == 3) {
            byte[] data1 = new byte[4];
            byte[] data2 = new byte[4];
            byte[] data3 = new byte[4];

            for(int j = 0; j < 4; j++) {
                data1[j] = data[j];
                data2[j] = data[j + 4];
                data3[j] = data[j + 8];
            }
            ByteBuffer wrapped1 = ByteBuffer.wrap(data1);
            ByteBuffer wrapped2 = ByteBuffer.wrap(data2);
            ByteBuffer wrapped3 = ByteBuffer.wrap(data3);

            Coordinate3D destination = new Coordinate3D();

            destination.x = wrapped1.getInt();
            destination.y = wrapped2.getInt();
            destination.z = wrapped3.getInt();

            TileEntity tile = worldObj.getBlockTileEntity((int) destination.x, (int) destination.y, (int) destination.z);

            if(tile != null && tile instanceof TileStationController) {
                sendCurrentTransporterTo((TileStationController) tile);
            }
        }
        if(id != 3 && id != 1) {
            sendTileDataToClient(id, data);
        }
    }

    @Override
    public void setTileDataFromServer(int id, byte[] data) {
        if(id == 0) {
            activeTab = data[0];
        }
        if(id == 2) {
            name = "";
            if(data.length > 0) {
                name = new String(data);
            }
        }
        if(id == 4) {
            if(data[0] == 1) {
                isMultiblock = true;
            } else {
                isMultiblock = false;
            }
        }
    }

    // ISidedInventory//

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return slot == 0 ? ModItems.transporterModules.isTransporter(stack) : (slot == 1 || slot == 2 || slot == 3) ? ModItems.transporterModules.isUpgrade(stack) && stack.stackSize == 1 && inv[slot] == null : hasTransporterInventory();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return true;
    }

    // TileMagnetConnector//

    @Override
    public double getRange() {
        return 3;
    }

    @Override
    public boolean canConnect(MagnetLink link) {
        ForgeDirection dir = null;
        ForgeDirection direction = link.line.getSide(link.getOther(this).getTile());
        if(isMultiblock) {
            int rotation = structure.getRotation();
            switch(rotation) {
                case 0:
                    dir = ForgeDirection.SOUTH;
                    break;
                case 1:
                    dir = ForgeDirection.WEST;
                    break;
                case 2:
                    dir = ForgeDirection.NORTH;
                    break;
                case 3:
                    dir = ForgeDirection.EAST;
                    break;
            }
            return direction == dir;
        }
        return false;
    }

    @Override
    public int getHeatLossPerTick() {
        return 0;
    }

    @Override
    public int getMaxHeat() {
        return heatNeeded ? neededHeat : 0;
    }
    
    @Override
    public int getHeatTransmissionSpeed() {
        return 5;
    }

    @Override
    public void linkUpdate() {}
}
