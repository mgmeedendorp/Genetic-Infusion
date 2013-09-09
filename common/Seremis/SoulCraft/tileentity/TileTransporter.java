package Seremis.SoulCraft.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.proxy.CommonProxy;

public class TileTransporter extends SCTile {

    private boolean hasEngine = false;
    private boolean hasInventory = false;
    private float speed = 1.0F;
    public ForgeDirection direction = ForgeDirection.WEST;
    
    public ItemStack[] inv = new ItemStack[9];

    public TileTransporter() {
        this(false, false);
    }

    public TileTransporter(boolean engine, boolean inventory) {
        hasInventory = inventory;
        hasEngine = engine;
        if(hasEngine) {
            speed = 5.0F;
        }
    }

    public void setHasInventory(boolean inventory) {
        this.hasInventory = inventory;
        if(CommonProxy.proxy.isServerWorld(worldObj))
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.transporter.blockID, 4, inventory ? 1 : 0);
    }

    public void setHasEngine(boolean engine) {
        if(engine) {
            speed = 5.0F;
        } else {
            speed = 1.0F;
        }
        this.hasEngine = engine;
        if(CommonProxy.proxy.isServerWorld(worldObj))
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.transporter.blockID, 3, engine ? 1 : 0);
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
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        hasInventory = compound.getBoolean("hasInventory");
        hasEngine = compound.getBoolean("hasEngine");
        direction = ForgeDirection.values()[compound.getInteger("direction")];
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("hasInventory", hasInventory);
        compound.setBoolean("hasEngine", hasEngine);
        compound.setInteger("direction", direction.ordinal());
    }
    
    @Override
    public boolean receiveClientEvent(int eventId, int variable) {
        if(eventId == 2) {
            this.direction = ForgeDirection.values()[variable];
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            return true;
        } else if (eventId == 3) {
            this.hasEngine = variable == 1;
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            return true;
        } else if(eventId == 4) {
            this.hasInventory = variable == 1;
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            return true;
        } else {
            return super.receiveClientEvent(eventId, variable);
        }
    }
}
