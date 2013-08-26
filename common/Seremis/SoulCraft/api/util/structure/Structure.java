package Seremis.SoulCraft.api.util.structure;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import Seremis.SoulCraft.api.util.Coordinate3D;

public class Structure {
    
    private World world;
    private Coordinate3D coordinate;
    private IStructureBlock baseBlock;
    private Coordinate3D baseBlockCoords;
    private StructureMap structureMap;

    private int currentRotation = -1;
    
    private boolean initiated = false;
    
    private int length;
    private int width;
    private int height;
    
    private IStructureChangeReceiver changeReceiver;
    
    public Structure(StructureMap structureMap) {
        this.structureMap = structureMap;
    }
    
    public void initiate(World world, Coordinate3D blockPosition, Block baseBlock, int metadata) {
        this.world = world;
        this.baseBlockCoords = blockPosition;
        this.baseBlock = new StructureBlock(baseBlock, metadata);
        initiated = true;
        calculateSize();
    }
    
    public void notifyChangesTo(IStructureChangeReceiver scr) {
        changeReceiver = scr;
    }
    
    private void calculateBaseCoordinates(int rotation) {
        if(initiated) {
            Coordinate3D blockCoordinate = baseBlockCoords.clone();
            Coordinate3D blockCoordinate2 = structureMap.getBlockCoordinates(baseBlock).get(0);
            rotateOnYAxis(blockCoordinate2, rotation);
            System.out.println(blockCoordinate2 + " " + rotation);
            Coordinate3D baseCoordinate = blockCoordinate.moveBack(blockCoordinate2);
            coordinate = baseCoordinate;
        }
    }
    
    private Coordinate3D rotateOnYAxis(Coordinate3D coord, int rotation) {
        Coordinate3D coord2 = new Coordinate3D();
        int x = (int) coord.getXCoord();
        int z = (int) coord.getZCoord();
        switch(rotation) {
            case 0: {
                coord2.x = x;
                coord2.z = z;
                break;
            }
            case 1: {
                coord2.x = -z;
                coord2.z = x;
                break;
            }
            case 2: {
                coord2.x = -x;
                coord2.z = -z;
                break;
            }
            case 3: {
                coord2.x = z;
                coord2.z = -x;
                break;
            }
        }
        return coord2;
    }
    
    private void calculateSize() {
        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;
        for(IStructureBlock block : getBlocks()) {
            if(Math.abs(block.getPosition().x) > Math.abs(maxX)) {
                maxX = (int) block.getPosition().x;
            }
            if(Math.abs(block.getPosition().y) > Math.abs(maxY)) {
                maxY = (int) block.getPosition().y;
            }
            if(Math.abs(block.getPosition().z) > Math.abs(maxZ)) {
                maxZ = (int) block.getPosition().z;
            }
        }
        if(maxX < 0) {
            maxX -=1;
        } else {
            maxX +=1;
        }
        if(maxY < 0) {
            maxY -=-1;
        } else {
            maxY +=1;
        }
        if(maxZ < 0) {
            maxZ -=1;
        } else {
            maxZ +=1;
        }
        length = maxX;
        height = maxY;
        width = maxZ;
    }
    
    public IStructureBlock getBlockAtCoordinate(Coordinate3D coordinate) {
        if(initiated) {
            Coordinate3D structureCoords = convertToStructureCoords(coordinate);
            IStructureBlock block = structureMap.getBlockAtCoordinate((int) structureCoords.x, (int) structureCoords.y, (int) structureCoords.z);
            
            return block;
        }
        return null;
    }
    
    public List<Coordinate3D> getBlockCoordinates(IStructureBlock block) {
        if(initiated) {
            List<Coordinate3D> blockCoords = new ArrayList<Coordinate3D>();
            Coordinate3D blockCoord = getStructureCoordinates().clone();
            
            for(IStructureBlock sBlock : getBlocks()) {
                blockCoord = getStructureCoordinates().clone();
                if(sBlock.getBlock() == block.getBlock()) {
                    if(sBlock.getMetadata() == block.getMetadata()) {
                        blockCoord.move(sBlock.getPosition());
                        blockCoords.add(blockCoord);
                    }
                }
            }
            return blockCoords;
        }
        return null;
    }
    
    /**
     * Checks if the given block exists in the in-world structure.
     * @param block doesn't need to have a position value
     * @param number the number of blocks to exist for the method to return true
     * @return true if the number of given blocks >= number. if number == -1 it checks for all the blocks
     */
    public boolean doesBlockExistInStructure(IStructureBlock block, int number) {
        if(initiated) {
            List<Coordinate3D> list = getBlockCoordinates(block);
            List<Coordinate3D> exists = new ArrayList<Coordinate3D>();
            for(Coordinate3D coord : list) {
                if(world.getBlockId((int)coord.x, (int)coord.y, (int)coord.z) == block.getBlock().blockID) {
                    if(world.getBlockMetadata((int) coord.x, (int)coord.y, (int)coord.z) == block.getMetadata()) {
                        exists.add(coord);
                    }
                }
            }
            if(exists.size() >= number) {
                return true;
            }
            if(number == -1) {
                if(exists.size() == list.size()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean doesBlockExistInStructure(Block block, int metadata, int number) {
        return doesBlockExistInStructure(new StructureBlock(block, metadata), number);
    }
    
    public List<Coordinate3D> getBlockCoordinates(Block block, int metadata) {
        return getBlockCoordinates(new StructureBlock(block, metadata));
    }
    
    private Coordinate3D convertToStructureCoords(Coordinate3D coordinate) {
        if(initiated) {
            coordinate.move(-this.coordinate.x, -this.coordinate.y, -this.coordinate.z);
            return coordinate;
        }
        return null;
    }
    
    public Coordinate3D getStructureCoordinates() {
        if(initiated) {
            return coordinate;
        }
        return null;
    }
    
    public List<IStructureBlock> getBlocks() {
        return structureMap.getBlocks();
    }
    
    public int getLength() {
        return length;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getRotation() {
        if(initiated)
            doesStructureExist();
        return currentRotation;
    }
 
    public boolean doesStructureExist() {
        if(initiated) {
            for(int i = 0; i<4; i++) {
                List<IStructureBlock> exists = new ArrayList<IStructureBlock>();
                StructureMap structureMap = this.structureMap.getRotation(i);
                calculateBaseCoordinates(i);
                for(IStructureBlock block : structureMap.getBlocks()) {
                    if(world.getBlockId((int) coordinate.x + (int) block.getPosition().x, (int) coordinate.y + (int) block.getPosition().y, (int) coordinate.z + (int) block.getPosition().z) == block.getBlock().blockID) {
                        if(world.getBlockMetadata((int) coordinate.x + (int) block.getPosition().x, (int) coordinate.y + (int) block.getPosition().y, (int) coordinate.z + (int) block.getPosition().z) == block.getMetadata()) {
                            if(block.canFormStructure(structureMap, world, (int) coordinate.x + (int) block.getPosition().x, (int) coordinate.y + (int) block.getPosition().y, (int) coordinate.z + (int) block.getPosition().z)) {
                                exists.add(block);
                            }
                        }
                    }
                }
                if(exists.size() == structureMap.getBlockCount()) {
                    if(airCheck()) {
                        int oldRotation = currentRotation;
                        this.currentRotation = i;
                        if(oldRotation != currentRotation)
                            changeReceiver.onStructureChange();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean airCheck() {
        List<Coordinate3D> shouldBeFull = new ArrayList<Coordinate3D>();
        List<Coordinate3D> actuallyFull = new ArrayList<Coordinate3D>();
        for(IStructureBlock block : getBlocks()) {
            shouldBeFull.add(block.getPosition());
        }

        if(length >= 0 && height >= 0 && width >= 0) {
            for(int i = 0; i<length; i++) {
                for(int j = 0; j<height; j++) {
                    for(int k = 0; k<width; k++) {
                        if(world.getBlockId((int)coordinate.x+i, (int)coordinate.y+j, (int)coordinate.z+k) != 0) {
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
                        if(world.getBlockId((int)coordinate.x+i, (int)coordinate.y+j, (int)coordinate.z+k) != 0) {
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
                        if(world.getBlockId((int)coordinate.x+i, (int)coordinate.y+j, (int)coordinate.z+k) != 0) {
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
                        if(world.getBlockId((int)coordinate.x+i, (int)coordinate.y+j, (int)coordinate.z+k) != 0) {
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
                        if(world.getBlockId((int)coordinate.x+i, (int)coordinate.y+j, (int)coordinate.z+k) != 0) {
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
                        if(world.getBlockId((int)coordinate.x+i, (int)coordinate.y+j, (int)coordinate.z+k) != 0) {
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
                        if(world.getBlockId((int)coordinate.x+i, (int)coordinate.y+j, (int)coordinate.z+k) != 0) {
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
                        if(world.getBlockId((int)coordinate.x+i, (int)coordinate.y+j, (int)coordinate.z+k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(shouldBeFull.size() == actuallyFull.size()) {
            return true;
        }
        return true;
    }
}
