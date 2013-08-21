package Seremis.SoulCraft.api.util.structure;

import net.minecraft.block.Block;
import Seremis.SoulCraft.api.util.Coordinate3D;

public class StructureBlock implements IStructureBlock {

    private Block block;
    private int metadata;
    private Coordinate3D position;
    
    public StructureBlock(Block block, int metadata, Coordinate3D position) {
        this.block = block;
        this.metadata = metadata;
        this.position = position;
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
}
