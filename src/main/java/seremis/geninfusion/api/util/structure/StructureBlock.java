package seremis.geninfusion.api.util.structure;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import seremis.geninfusion.api.util.Coordinate3D;

import java.util.List;

public class StructureBlock implements IStructureBlock {

    protected Block block;
    protected int metadata;
    protected Coordinate3D position;
    protected boolean needsExistance;

    public StructureBlock(Block block, int metadata, Coordinate3D position, boolean mustExist) {
        this.block = block;
        this.metadata = metadata;
        this.position = position;
        this.needsExistance = mustExist;
    }

    public StructureBlock(Block block, int metadata, Coordinate3D position) {
        this(block, metadata, position, true);
    }

    public StructureBlock(Block block, int metadata) {
        this(block, metadata, null);
    }

    public StructureBlock(Block block) {
        this(block, 0);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public int getMetadata() {
        return metadata;
    }

    @Override
    public Coordinate3D getPosition() {
        return position;
    }

    @Override
    public void setPosition(Coordinate3D position) {
        this.position = position;
    }

    @Override
    public IStructureBlock copy() {
        return new StructureBlock(block, metadata, position.clone(), needsExistance);
    }

    @Override
    public boolean canFormStructure(StructureMap structureMap, World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean needsToExistForStructureToForm() {
        return needsExistance;
    }

    @Override
    public List<IStructureBlock> getReplaceableBlocks() {
        return null;
    }
}
