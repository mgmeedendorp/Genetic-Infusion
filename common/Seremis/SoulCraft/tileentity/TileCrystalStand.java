package Seremis.SoulCraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.structure.Structure;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileCrystalStand extends SCTileMagnetConnector {

    private int hasCrystal = 0;
    public boolean isStructureMagnetStation = false;
    public Structure structure = null;

    @Override
    public boolean canConnect(MagnetLink link) {
        ForgeDirection dir = null;
        ForgeDirection direction = link.line.getSide(link.getOther(this).getTile());
        if(isStructureMagnetStation && !(link.getOther(this) instanceof TileStationController) && structure != null) {
            int rotation = structure.getRotation();
            switch(rotation) {
                case 0: dir = ForgeDirection.SOUTH;
                break;
                case 1: dir = ForgeDirection.WEST;
                break;
                case 2: dir = ForgeDirection.NORTH;
                break;
                case 3: dir = ForgeDirection.EAST;
                break;
            }
            return direction != ForgeDirection.DOWN && hasCrystal == 1 && direction == dir;
        }
        return direction != ForgeDirection.DOWN && hasCrystal == 1;
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
