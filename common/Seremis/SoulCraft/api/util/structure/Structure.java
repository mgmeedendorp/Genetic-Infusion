package Seremis.SoulCraft.api.util.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import Seremis.SoulCraft.api.util.Coordinate3D;

public class Structure {
    
    protected List<IStructureBlock> blocks = new ArrayList<IStructureBlock>();
    
    protected int length;
    protected int width;
    protected int height;
    private Coordinate3D baseSize = new Coordinate3D();
    
    public Structure(IStructureBlock... block) {
        blocks.addAll(Arrays.asList(block));
    }
    
    public void addBlock(IStructureBlock block) {
        blocks.add(block);
        calculateSize();
    }
    
    private void calculateSize() {
        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;
        for(IStructureBlock block : blocks) {
            if(block.getPosition().x > maxX) {
                maxX = (int) block.getPosition().x;
            }
            if(block.getPosition().y > maxY) {
                maxY = (int) block.getPosition().y;
            }
            if(block.getPosition().z > maxZ) {
                maxZ = (int) block.getPosition().z;
            }
        }
        baseSize.setCoords(maxX+1, maxY+1, maxZ+1);
    }
    
    private void setRotatedSize(int rotation) {
        switch(rotation) {           
               case 0: {
                   setSize((int)baseSize.x, (int)baseSize.y, (int)baseSize.z);
                   break;
               }
               case 1: {
                   setSize((int)-baseSize.z, (int)baseSize.y, (int)baseSize.x);
                   break;
               }
               case 2: {
                   setSize((int)-baseSize.x, (int)baseSize.y, (int)-baseSize.z);
                   break;
               }
               case 3: {
                   setSize((int)baseSize.z, (int)baseSize.y, (int)-baseSize.x);
                   break;
               }
           }
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
     * Gets the block at the given world coordinates
     * @param x
     * @param y
     * @param z
     * @return the block at the coordinate
     */
    public IStructureBlock getBlockAtWorldCoordinates(World world, int x, int y, int z) {
        for(IStructureBlock sBlock : getBlocks()) {
            if(sBlock.getBlock().blockID == world.getBlockId(x, y, z)) {
                if(sBlock.getMetadata() == world.getBlockMetadata(x+(int)sBlock.getPosition().x, y+(int)sBlock.getPosition().y, z+(int)sBlock.getPosition().z)) {
                    return sBlock;
                }
            }
        }
        return null;
    }
    
    /**
     * Returns the world coordinates of the given blockToFind
     * @param blockToFind
     * @param metadataToFind
     * @param world
     * @param x coordinate of the block to measure from
     * @param y coordinate of the block to measure from
     * @param z coordinate of the block to measure from
     * @return
     */
    public Coordinate3D getBlockWorldCoordinates(Block blockToFind, int metadataToFind, World world, int x, int y, int z) {
        IStructureBlock blockToMeasureFrom = getBlockAtWorldCoordinates(world, x, y, z);
        int rotation = getStructureRotation(world, blockToMeasureFrom.getBlock(), x, y, z);
        Coordinate3D structureNullCoord = new Coordinate3D();
        Coordinate3D blockCoord = new Coordinate3D();
        
        for(IStructureBlock sBlock : getBlocks()) {
            if(sBlock.getBlock() == blockToMeasureFrom) {
                if(sBlock.getMetadata() == world.getBlockMetadata(x+(int)sBlock.getPosition().x, y+(int)sBlock.getPosition().y, z+(int)sBlock.getPosition().z)) {
                    structureNullCoord.x = x-sBlock.getPosition().x;
                    structureNullCoord.y = y-sBlock.getPosition().y;
                    structureNullCoord.z = z-sBlock.getPosition().z;
                }
            }
        }
        
        Structure structure = this.rotateOnYAxis(rotation);
        
        for(IStructureBlock sBlock : structure.getBlocks()) {
            if(sBlock.getBlock() == blockToFind) {
                if(sBlock.getMetadata() == metadataToFind) {
                    blockCoord = sBlock.getPosition();
                    break;
                }
            }
        }
        blockCoord.move(x, y, z);
        return blockCoord;
    }
    
    /**
     * Get the rotation of the structure with the world coordinates at the 0, 0, 0 coordinate of the structure 
     * @param world
     * @param x 
     * @param y
     * @param z
     * @return
     */
    public int getStructureRotation(World world, int x, int y, int z) {
        for(int rotation = 0; rotation<4; rotation++) {
            Structure structure = new Structure();
            structure = rotateOnYAxis(rotation);
            setRotatedSize(rotation);
            if(structure.doesStructureExistAtCoords(world, x, y, z)) {
                return rotation;
            }
        }
        return -1;
    }
    
    /**
     * Get the rotation of the structure with the world coordinates at the coordinates of the given block in the structure 
     * @param world
     * @param x 
     * @param y
     * @param z
     * @return
     */
    public int getStructureRotation(World world, Block block, int x, int y, int z) {
        Coordinate3D coord = new Coordinate3D();
        
        for(int rotation = 0; rotation<4; rotation++) {
            Structure structure = new Structure();
            structure = rotateOnYAxis(rotation);
            for(IStructureBlock sBlock : structure.getBlocks()) {
                if(sBlock.getBlock() == block) {
                    if(sBlock.getMetadata() == world.getBlockMetadata(x+(int)sBlock.getPosition().x, y+(int)sBlock.getPosition().y, z+(int)sBlock.getPosition().z)) {
                        coord = sBlock.getPosition().clone();
                    }
                }
            }
            setRotatedSize(rotation);
            if(structure.doesStructureExistAtCoords(world, x-(int)coord.x, y-(int)coord.y, z-(int)coord.z)) {
                return rotation;
            }
        }
        return -1;
    }
    
    private void setSize(int length, int height, int width) {
        this.length = length;
        this.width = width;
        this.height = height;
    }
    
    private boolean doesStructureExistAtCoords(World world, int x, int y, int z) {
        for(IStructureBlock block : getBlocks()) {
            if(world.getBlockId(x+(int)block.getPosition().x, y+(int)block.getPosition().y, z+(int)block.getPosition().z) == block.getBlock().blockID) {
                if(world.getBlockMetadata(x+(int)block.getPosition().x, y+(int)block.getPosition().y, z+(int)block.getPosition().z) == block.getMetadata()) {
                    if(block.canFormStructure(this, world, x+(int)block.getPosition().x, y+(int)block.getPosition().y, z+(int)block.getPosition().z)) {
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return airCheck(world, x, y, z);
    }
    
    /**
     * Checks if this structure exists from the given block, at the given world coords and with any possible x-z rotation
     * @param world The world instance
     * @param block The block the structure is measured from, this should be a block that is contained in a structureblock in this structure.
     * @param x
     * @param y
     * @param z
     * @return
     */
    public boolean doesRotatedStructureExistAtCoords(World world, Block block, int x, int y, int z) {
        Coordinate3D coord = new Coordinate3D();
        
        for(int rotation = 0; rotation<4; rotation++) {
            Structure structure = new Structure();
            structure = rotateOnYAxis(rotation);
            for(IStructureBlock sBlock : structure.getBlocks()) {
                if(sBlock.getBlock() == block) {
                    if(sBlock.getMetadata() == world.getBlockMetadata(x+(int)sBlock.getPosition().x, y+(int)sBlock.getPosition().y, z+(int)sBlock.getPosition().z)) {
                        coord = sBlock.getPosition().clone();
                    }
                }
            }
            if(structure.doesStructureExistAtCoords(world, x-(int)coord.x, y-(int)coord.y, z-(int)coord.z)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean airCheck(World world, int x, int y, int z) {
        List<Coordinate3D> shouldBeFull = new ArrayList<Coordinate3D>();
        List<Coordinate3D> actuallyFull = new ArrayList<Coordinate3D>();
        for(IStructureBlock block : blocks) {
            shouldBeFull.add(block.getPosition());
        }

        if(length >= 0 && height >= 0 && width >= 0) {
            for(int i = 0; i<length; i++) {
                for(int j = 0; j<height; j++) {
                    for(int k = 0; k<width; k++) {
                        if(world.getBlockId(x+i, y+j, z+k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(length >= 0 && height >= 0 && width < 0) {
            for(int i = 0; i<length; i++) {
                for(int j = 0; j<height; j++) {
                    for(int k = 0; k>width; k--) {
                        if(world.getBlockId(x+i, y+j, z+k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(length >= 0 && height < 0 && width >= 0) {
            for(int i = 0; i<length; i++) {
                for(int j = 0; j>height; j--) {
                    for(int k = 0; k<width; k++) {
                        if(world.getBlockId(x+i, y+j, z+k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(length >= 0 && height < 0 && width < 0) {
            for(int i = 0; i<length; i++) {
                for(int j = 0; j>height; j--) {
                    for(int k = 0; k>width; k--) {
                        if(world.getBlockId(x+i, y+j, z+k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(length < 0 && height >= 0 && width >= 0) {
            for(int i = 0; i>length; i--) {
                for(int j = 0; j<height; j++) {
                    for(int k = 0; k<width; k++) {
                        if(world.getBlockId(x+i, y+j, z+k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(length < 0 && height >= 0 && width < 0) {
            for(int i = 0; i>length; i--) {
                for(int j = 0; j<height; j++) {
                    for(int k = 0; k>width; k--) {
                        if(world.getBlockId(x+i, y+j, z+k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(length < 0 && height < 0 && width >= 0) {
            for(int i = 0; i>length; i--) {
                for(int j = 0; j>height; j--) {
                    for(int k = 0; k<width; k++) {
                        if(world.getBlockId(x+i, y+j, z+k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(length < 0 && height < 0 && width < 0) {
            for(int i = 0; i>length; i--) {
                for(int j = 0; j>height; j--) {
                    for(int k = 0; k>width; k--) {
                        if(world.getBlockId(x+i, y+j, z+k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(shouldBeFull.size() == actuallyFull.size()) {
            return true;
        }
        return false;
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
    
    private Structure rotateOnYAxis(int rotation) {
        Structure newStructure = new Structure();
        
        for(IStructureBlock block : getBlocks()) {    
            IStructureBlock newBlock = block.copy();
            int x = (int) block.getPosition().x;
            int z = (int) block.getPosition().z;      
            
            switch(rotation) {           
               case 0: {
                   newBlock.getPosition().x = x; 
                   newBlock.getPosition().z = z;
                   break;
               }
               case 1: { 
                   newBlock.getPosition().x = -z;   
                   newBlock.getPosition().z = x;
                   break;
               }
               case 2: {
                   newBlock.getPosition().x = -x;   
                   newBlock.getPosition().z = -z;
                   break;
               }
               case 3: {
                   newBlock.getPosition().x = z;   
                   newBlock.getPosition().z = -x;
                   break;
               }
           }
           newStructure.addBlock(newBlock);
        }
        setRotatedSize(rotation);
        return newStructure;
    }
}
