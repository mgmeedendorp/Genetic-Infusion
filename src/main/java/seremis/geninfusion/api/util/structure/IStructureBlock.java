package seremis.geninfusion.api.util.structure;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import seremis.geninfusion.api.util.Coordinate3D;

import java.util.List;

public interface IStructureBlock {

    public Block getBlock();

    public boolean canFormStructure(StructureMap structureMap, World world, int x, int y, int z);

    /**
     * The metadata value of the block used in the structure
     */
    public int getMetadata();

    /**
     * The position relative to the structure
     */
    Coordinate3D getPosition();

    void setPosition(Coordinate3D position);

    IStructureBlock copy();

    boolean needsToExistForStructureToForm();

    /**
     * The blocks this block is replaceable with
     *
     * @return a list of replaceable blocks
     */
    List<IStructureBlock> getReplaceableBlocks();
}
