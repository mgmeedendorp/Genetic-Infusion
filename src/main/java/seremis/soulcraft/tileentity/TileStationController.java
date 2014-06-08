package seremis.soulcraft.tileentity;

import java.nio.ByteBuffer;
import java.util.List;

import seremis.soulcraft.SoulCraft;
import seremis.soulcraft.api.magnet.MagnetLink;
import seremis.soulcraft.api.magnet.MagnetLinkHelper;
import seremis.soulcraft.api.util.Coordinate3D;
import seremis.soulcraft.api.util.structure.IStructureChangeReceiver;
import seremis.soulcraft.api.util.structure.Structure;
import seremis.soulcraft.block.ModBlocks;
import seremis.soulcraft.core.lib.GuiIds;
import seremis.soulcraft.core.lib.Tiles;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.entity.EntityTransporter;
import seremis.soulcraft.entity.logic.EntityTransporterLogic;
import seremis.soulcraft.event.TransporterSendEvent;
import seremis.soulcraft.item.ModItems;
import seremis.soulcraft.util.UtilBlock;
import seremis.soulcraft.util.inventory.Inventory;
import seremis.soulcraft.util.structure.ModStructures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileStationController extends SCTileMagnetConsumer implements IInventory, ISidedInventory, IStructureChangeReceiver {

    public Structure structure = new Structure(ModStructures.magnetStation);

    public boolean isMultiblock = false;

    public String name;

    private long currTime = 0;
    private long lastUpdateTick = 0;
    private long ticksBeforeUpdate = 100;
    
    private boolean heatNeeded = false;
    private Coordinate3D tempDestination;
    private final int neededHeat = 1000;
    
    public Coordinate3D selectedDestination;
    
    private TileItemIO itemIO1;
    private TileItemIO itemIO2;
    
    @SideOnly(Side.CLIENT)
    public int barHeat;

    public boolean isValid() {
        if(structure.doesStructureExist()) return true;
        return false;
    }

    @Override
    public void invalidate() {
        invalidateMultiblock();
        super.invalidate();
    }

    public void invalidateMultiblock() {
        if(isMultiblock && CommonProxy.instance.isServerWorld(worldObj)) {
            List<Coordinate3D> crystalStandCoordinates = structure.getBlockCoordinates(ModBlocks.crystalStand, 0);
            
            if(structure.doesBlockExistInStructure(ModBlocks.crystalStand, 0, 1)) {
                Coordinate3D crystalStandCoord = crystalStandCoordinates.get(0);
                TileEntity tile = worldObj.getTileEntity((int) crystalStandCoord.x, (int) crystalStandCoord.y, (int) crystalStandCoord.z);
                if(tile != null && tile instanceof TileCrystalStand) {
                    ((TileCrystalStand) tile).isStructureMagnetStation = false;
                    ((TileCrystalStand) tile).structure = null;
                }
                
                if(itemIO1 != null) {
                    itemIO1.isStructureMagnetStation = false;
                    itemIO1.stationController = null;
                    itemIO1 = null;
                }
                if(itemIO2 != null) {
                    itemIO2.isStructureMagnetStation = true;
                    itemIO2.stationController = null;
                    itemIO2 = null;
                }
            }
        }
        isMultiblock = false;
    }

    public void initiateMultiblock() {
        if(!isMultiblock && CommonProxy.instance.isServerWorld(worldObj)) {
            
            List<Coordinate3D> crystalStandCoordinates = structure.getBlockCoordinates(ModBlocks.crystalStand, 0);
            List<Coordinate3D> itemIOCoordinates = structure.getBlockCoordinates(ModBlocks.itemIO, 0);
            
            if(structure.doesBlockExistInStructure(ModBlocks.crystalStand, 0, 1)) {
                Coordinate3D crystalStandCoord = crystalStandCoordinates.get(0);
                
                TileEntity tile = worldObj.getTileEntity((int) crystalStandCoord.x, (int) crystalStandCoord.y, (int) crystalStandCoord.z);
                
                if(tile != null && tile instanceof TileCrystalStand) {

                    isMultiblock = true;
                    
                    ((TileCrystalStand) tile).isStructureMagnetStation = true;
                    ((TileCrystalStand) tile).structure = structure;
                    
                    MagnetLinkHelper.instance.addLink(new MagnetLink(this, (TileCrystalStand) tile));
                } else {
                    isMultiblock = false;
                    return;
                }
                
                if(itemIOCoordinates != null) {
                    for(Coordinate3D itemIOCoord : itemIOCoordinates) {
                        TileEntity itemIO = worldObj.getTileEntity((int) itemIOCoord.x, (int) itemIOCoord.y, (int) itemIOCoord.z);
                        
                        if(itemIO != null && itemIO instanceof TileItemIO) {                        
                            TileItemIO tileIO = (TileItemIO)itemIO;
                            

                            tileIO.stationController = this;
                            
                            if(itemIO1 == null) {
                                itemIO1 = tileIO;
                            } else {
                                itemIO2 = tileIO;
                            }
                        }
                    }
                }
                isMultiblock = true;
            }
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(CommonProxy.instance.isRenderWorld(worldObj)) {
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
        
        if(heatNeeded && heat == neededHeat) {
            sendCurrentTransporterTo((TileStationController) worldObj.getTileEntity((int) tempDestination.x, (int) tempDestination.y, (int) tempDestination.z));
            heatNeeded = false;
            heat = 0;
            tempDestination = null;
        }
    }

    @Override
    public void onStructureChange() {
        
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        inventory.writeToNBT(compound);
        if(name != null && name != "") {
            compound.setString("name", name);
        }
        compound.setBoolean("heatNeeded", heatNeeded);
        if(tempDestination != null)
            compound.setIntArray("tempDestination", tempDestination.toArray());
        if(selectedDestination != null)
            compound.setIntArray("selectedDestination", selectedDestination.toArray());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory.readFromNBT(compound);
        
        if(compound.hasKey("name")) {
            name = compound.getString("name");
        } else {
            name = "";
        }
        
        heatNeeded = compound.getBoolean("heatNeeded");

        if(compound.hasKey("tempDestination")) {
            tempDestination = new Coordinate3D().fromArray(compound.getIntArray("tempDestination"));
        }
        
        if(compound.hasKey("selectedDestination")) {
            selectedDestination = new Coordinate3D().fromArray(compound.getIntArray("selectedDestination"));
        }
    }
    
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1.5, zCoord + 1);
    }
    
    // IInventory//    
    public Inventory inventory = new Inventory(13, Tiles.INV_STATION_CONTROLLER_UNLOCALIZED_NAME, 64, this);

    public int activeTab = 0;

    public float transporterSpeed = 1.0F;

    public EntityPlayer player;

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return inventory.decrStackSize(slot, amount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inventory.getStackInSlotOnClosing(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory.setInventorySlotContents(slot, stack);
    }

    @Override
    public String getInventoryName() {
        return inventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return inventory.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return slot == 0 ? ModItems.transporterModules.isTransporter(stack) : (slot == 1 || slot == 2 || slot == 3) ? ModItems.transporterModules.isUpgrade(stack) && getStackInSlot(slot) == null : hasTransporterInventory();
    }

    public ItemStack[] getInventory() {
        return inventory.getItemStacks();
    }

    public float getTransporterSpeed() {
        return hasTransporterEngines() ? 5.0F : 1.0F;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if(CommonProxy.instance.isRenderWorld(worldObj)) {
            return;
        }

        if(!hasTransporter()) {
            for(int i = 1; i < 4; i++) {
                UtilBlock.dropItemsFromTile(worldObj, xCoord, yCoord, zCoord, i);
            }
            emptySlots(1, 4);
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
            setInventorySlotContents(i, null);
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
        if(CommonProxy.instance.isServerWorld(worldObj) && tile != null && tile != this && this.hasTransporter()) {
            if(heat < neededHeat) {
                heatNeeded = true;
                tempDestination = new Coordinate3D(tile);
                return;
            }
            
            EntityTransporterLogic logic = EntityTransporterLogic.getLogic(this, tile);

            EntityTransporter transporter = new EntityTransporter(worldObj, xCoord, yCoord, zCoord, logic);

            ItemStack[] inventory = new ItemStack[9];

            for(int i = 0; i < 9; i++) {
                inventory[i] = getStackInSlot(i + 4);
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
        if(isInvEmpty()) {
            for(int i = 3; i<transporter.getInventory().length; i++) {
                setInventorySlotContents(i, transporter.getInventory()[i]);
            }
            setInventorySlotContents(0, new ItemStack(ModItems.transporterModules, 1, 2));
            if(transporter.hasEngine())
                setInventorySlotContents(1, new ItemStack(ModItems.transporterModules, 1, 1));
            if(transporter.hasInventory()) 
                setInventorySlotContents(2, new ItemStack(ModItems.transporterModules, 1, 0));
        } else {
            UtilBlock.dropItemsFromTile(worldObj, xCoord, yCoord, zCoord);
            
            for(int i = 3; i<transporter.getInventory().length; i++) {
                setInventorySlotContents(i, transporter.getInventory()[i]);
            }
            setInventorySlotContents(0, new ItemStack(ModItems.transporterModules, 1, 2));
            if(transporter.hasEngine())
                setInventorySlotContents(1, new ItemStack(ModItems.transporterModules, 1, 1));
            if(transporter.hasInventory()) 
                setInventorySlotContents(2, new ItemStack(ModItems.transporterModules, 1, 0));
        }
    }
    
    public boolean isInvEmpty() {
        for(ItemStack stack : inventory.getItemStacks()) {
            if(stack != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setTileDataFromClient(int id, byte[] data) {
        if(id == 0) {
            activeTab = data[0];
        }
        if(id == 1) {
            if(data[0] == 0) {
                if(player != null) {
                    player.openGui(SoulCraft.instance, GuiIds.GUI_STATION_SEND_SCREEN_ID, worldObj, xCoord, yCoord, zCoord);
                }
            }
            if(data[0] == 1) {
                if(player != null) {
                    player.openGui(SoulCraft.instance, GuiIds.GUI_STATION_TRANSPORTER_SCREEN_ID, worldObj, xCoord, yCoord, zCoord);
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
            byte[] data4 = new byte[4];

            for(int j = 0; j < 4; j++) {
                data1[j] = data[j];
                data2[j] = data[j + 4];
                data3[j] = data[j + 8];
                data4[j] = data[j + 12];
            }
            ByteBuffer wrapped1 = ByteBuffer.wrap(data1);
            ByteBuffer wrapped2 = ByteBuffer.wrap(data2);
            ByteBuffer wrapped3 = ByteBuffer.wrap(data3);
            ByteBuffer wrapped4 = ByteBuffer.wrap(data4);

            Coordinate3D destination = new Coordinate3D();

            destination.x = wrapped1.getInt();
            destination.y = wrapped2.getInt();
            destination.z = wrapped3.getInt();
            int mouseButton = wrapped4.getInt();

            TileEntity tile = worldObj.getTileEntity((int) destination.x, (int) destination.y, (int) destination.z);

            if(tile != null && tile != this && tile instanceof TileStationController) {
                if(mouseButton == 0) {
                    sendCurrentTransporterTo((TileStationController) tile);
                } else if(mouseButton == 1) {
                    selectedDestination = new Coordinate3D(tile);
                }
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
    }
    
    public void onRedstoneSignal() {
        System.out.println(this);
        if(selectedDestination != null)
            sendCurrentTransporterTo((TileStationController) worldObj.getTileEntity((int) selectedDestination.x, (int) selectedDestination.y, (int) selectedDestination.z));
    }

    // ISidedInventory//

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return slot == 0 ? ModItems.transporterModules.isTransporter(stack) && stack.stackSize == 1 && getStackInSlot(0) == null : (slot == 1 || slot == 2 || slot == 3) ? ModItems.transporterModules.isUpgrade(stack) && stack.stackSize == 1 && getStackInSlot(slot) == null : hasTransporterInventory();
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