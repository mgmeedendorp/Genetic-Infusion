package Seremis.SoulCraft.api.util.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.world.World;

public class Structure {
    
    protected List<IStructureBlock> blocks = new ArrayList<IStructureBlock>();
    
    public Structure(IStructureBlock... block) {
        blocks.addAll(Arrays.asList(block));
    }
    
    public void addBlock(IStructureBlock block) {
        blocks.add(block);
    }
    
    /**
     * Gets the block in the coordinate according to the multiblock, starting at 0, 0, 0.
     * @param x
     * @param y
     * @param z
     * @return the block at the coordinate
     */
    public IStructureBlock getBlockAtCoordinate(int x, int y, int z) {
        for(IStructureBlock block : blocks) {
            if(block.getPosition().x == x && block.getPosition().y == y && block.getPosition().z == z) {
                return block;
            }
        }
        return null;
    }
    
    /**
     * Checks if this structure exists at the given world coords.
     * @param x
     * @param y
     * @param z
     * @return
     */
    public boolean doesStructureExistAtCoords(World world, int x, int y, int z) {
        for(IStructureBlock block : blocks) {
            if(world.getBlockId(x+(int)block.getPosition().x, y+(int)block.getPosition().y, z+(int)block.getPosition().z) == block.getBlock().blockID) {
                if(world.getBlockMetadata(x+(int)block.getPosition().x, y+(int)block.getPosition().y, z+(int)block.getPosition().z) == block.getMetadata()) {
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
    
    public int getBlockCount() {
        return blocks.size();
    }
}
