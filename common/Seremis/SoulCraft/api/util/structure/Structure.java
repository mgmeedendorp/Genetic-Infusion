package Seremis.SoulCraft.api.util.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.world.World;
import Seremis.SoulCraft.api.util.Coordinate3D;

public class Structure {
    
    protected List<IStructureBlock> blocks = new ArrayList<IStructureBlock>();
    
    protected int length;
    protected int width;
    protected int height;
    
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
    
    public void setSize(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }
    
    private static boolean doesStructureExistAtCoords(Structure structure, World world, int x, int y, int z) {
        for(IStructureBlock block : structure.getBlocks()) {
            if(world.getBlockId(x+(int)block.getPosition().x, y+(int)block.getPosition().y, z+(int)block.getPosition().z) == block.getBlock().blockID) {
                if(world.getBlockMetadata(x+(int)block.getPosition().x, y+(int)block.getPosition().y, z+(int)block.getPosition().z) == block.getMetadata()) {
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return structure.airCheck(world, x, y, z);
    }
    
    /**
     * Checks if this structure exists at the given world coords and with any possible x-z rotation
     * @param x
     * @param y
     * @param z
     * @return
     */
    public boolean doesRotatedStructureExistAtCoords(World world, int x, int y, int z) {
        Structure structure = new Structure();
        for(int i = 1; i<5; i++) {
            structure = rotateOnYAxis(this, i);
        }
        return doesStructureExistAtCoords(structure, world, x, y, z);
    }
    
    private boolean airCheck(World world, int x, int y, int z) {
        List<Coordinate3D> filled = new ArrayList<Coordinate3D>();
        List<Coordinate3D> empty = new ArrayList<Coordinate3D>();
        for(IStructureBlock block : blocks) {
            filled.add(block.getPosition());
        }
        for(int i=0; i<length; i++) {
            for(int j=0; j<height; j++) {
                for(int k=0; k<width; k++) {
                    if(world.getBlockId(x+i, y+j, z+k) == 0) {
                        empty.add(new Coordinate3D(i, j, k));
                    }
                }
            }
        }
        for(Coordinate3D full : filled) {
            for(Coordinate3D emp : empty) {
                if(full.equals(emp)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int getBlockCount() {
        return blocks.size();
    }
    
    public List<IStructureBlock> getBlocks() {
        return blocks;
    }
    
    public int getLength() {
        return length;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public static Structure rotateOnYAxis(Structure structure, int rotation) {
        Structure newStructure = new Structure();

        for(IStructureBlock block : structure.getBlocks()) {
            for(int i=0; i<rotation; i++) {
            block.getPosition().swapXZCoords();
            }
            newStructure.addBlock(block);
        }
        newStructure.setSize(rotation %2 == 0 ? structure.getWidth(): structure.getLength(), rotation %2 == 0 ? structure.getLength(): structure.getWidth(), structure.getHeight());
        return newStructure;
    }
}
