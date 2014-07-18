package seremis.geninfusion.tileentity;

import java.util.Random;

import seremis.geninfusion.api.magnet.MagnetLink;
import seremis.geninfusion.api.util.Coordinate3D;
import seremis.geninfusion.api.util.structure.Structure;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileCrystalStand extends GITileMagnetConnector {

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
            return direction != ForgeDirection.DOWN && hasCrystal == 1 && direction == dir;
        }
        return direction != ForgeDirection.DOWN && hasCrystal == 1;
    }

    @Override
    public Coordinate3D applyBeamRenderOffset(Coordinate3D position, ForgeDirection side) {
        position.x += 0.5D;
        position.y += 0.8D;
        position.z += 0.5D;
        return position;
    }

    @Override
    public double getRange() {
        return 5;
    }

    @Override
    public int getHeatLossPerTick() {
        return new Random().nextBoolean() ? 1 : 0;
    }

    @Override
    public int getMaxHeat() {
        return 400;
    }
    
    @Override
    public int getHeatTransmissionSpeed() {
        return 20;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(hasCrystal == 0) {
            this.heat = 0;
        }
    }

    @Override
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
        this.hasCrystal = hasCrystal ? 1 : 0;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public boolean hasCrystal() {
        return hasCrystal == 1;
    }
}
