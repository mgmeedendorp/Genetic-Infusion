package Seremis.SoulCraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.api.util.Coordinate3D;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileCrystalStand extends SCTileMagnetConnector {

    private int hasCrystal = 0;

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
        return hasCrystal==1;
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
        if(hasCrystal==0) {
            this.heat = 0;
        }
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        hasCrystal = compound.getInteger("hasCrystal");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("hasCrystal", hasCrystal);
    }
    
    public void setHasCrystal(boolean hasCrystal) {
        this.hasCrystal = hasCrystal? 1:0;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    public boolean hasCrystal() {
        return hasCrystal==1;
    } 
}
