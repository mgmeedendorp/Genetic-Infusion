package seremis.soulcraft.api.util.structure;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import seremis.soulcraft.api.util.Coordinate3D;

public interface IStructureBlock {

    public Block getBlock();

    public boolean canFormStructure(StructureMap structureMap, World world, int x, int y, int z);

    /**
     * The metadata value of the block used in the structure
     * 
     * @return
     */
    public int getMetadata();

    /**
     * The position relative to the structure
     * 
     * @return
     */
    Coordinate3D getPosition();

    void setPosition(Coordinate3D position);

    IStructureBlock copy();

    boolean needsToExistForStructureToForm();
}
