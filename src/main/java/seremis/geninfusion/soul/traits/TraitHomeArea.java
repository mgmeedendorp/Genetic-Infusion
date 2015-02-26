package seremis.geninfusion.soul.traits;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import seremis.geninfusion.api.soul.IEntitySoulCustom;

public class TraitHomeArea extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        entity.makePersistent("maximumHomeDistance");

        entity.setFloat("maximumHomeDistance", -1.0F);
    }

    @Override
    public boolean isWithinHomeDistanceCurrentPosition(IEntitySoulCustom entity) {
        EntityLiving living = (EntityLiving) entity;
        return entity.isWithinHomeDistance((int) Math.floor(living.posX), (int) Math.floor(living.posY), (int) Math.floor(living.posZ));
    }

    @Override
    public boolean isWithinHomeDistance(IEntitySoulCustom entity, int x, int y, int z) {
        float maximumHomeDistance = entity.getFloat("maximumHomeDistance");
        ChunkCoordinates homePosition = (ChunkCoordinates) entity.getObject("homePosition");
        return maximumHomeDistance == -1.0F || homePosition != null || homePosition.getDistanceSquared(x, y, z) < maximumHomeDistance * maximumHomeDistance;
    }

    @Override
    public ChunkCoordinates getHomePosition(IEntitySoulCustom entity) {
        return (ChunkCoordinates) entity.getObject("homePosition");
    }

    @Override
    public void setHomeArea(IEntitySoulCustom entity, int x, int y, int z, int maxDistance) {
        ChunkCoordinates coords = new ChunkCoordinates(0, 0, 0);
        coords.set(x, y, z);
        entity.setObject("homePosition", coords);
        entity.setFloat("maximumHomeDistance", (float) maxDistance);
    }

    @Override
    public float getMaxHomeDistance(IEntitySoulCustom entity) {
        return entity.getFloat("maximumHomeDistance");
    }

    @Override
    public void detachHome(IEntitySoulCustom entity) {
        entity.setFloat("maximumHomeDistance", -1.0F);
    }

    @Override
    public boolean hasHome(IEntitySoulCustom entity) {
        return entity.getFloat("maximumHomeDistance") != -1.0F;
    }

    @Override
    public void writeToNBT(IEntitySoulCustom entity, NBTTagCompound compound) {
        ChunkCoordinates coords = (ChunkCoordinates) entity.getObject("homePosition");

        NBTTagCompound coordCompound = new NBTTagCompound();
        coordCompound.setInteger("coordX", coords.posX);
        coordCompound.setInteger("coordY", coords.posY);
        coordCompound.setInteger("coordZ", coords.posZ);
        compound.setTag("homePosition", coordCompound);
    }

    @Override
    public void readFromNBT(IEntitySoulCustom entity, NBTTagCompound compound) {
        if(compound.hasKey("homePosition")) {
            ChunkCoordinates coords = new ChunkCoordinates(0, 0, 0);

            NBTTagCompound coordCompound = compound.getCompoundTag("homePosition");
            coords.posX = coordCompound.getInteger("coordX");
            coords.posY = coordCompound.getInteger("coordY");
            coords.posZ = coordCompound.getInteger("coordZ");

            entity.setObject("homePosition", coords);
        } else {
            entity.setObject("homePosition", new ChunkCoordinates(0, 0, 0));
        }
    }
}
