package seremis.soulcraft.api.util.structure;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import seremis.soulcraft.api.util.Coordinate3D;

public class Structure {

    private World world;
    private Coordinate3D coordinate;
    private IStructureBlock baseBlock;
    private Coordinate3D baseBlockCoords;
    private StructureMap structureMap;

    private int currentRotation = -1;

    private boolean initiated = false;

    private int length = initiated ? structureMap.getLength() : 0;
    private int width = initiated ? structureMap.getWidth() : 0;
    private int height = initiated ? structureMap.getHeight() : 0;

    private List<IStructureChangeReceiver> changeReceiver = new ArrayList<IStructureChangeReceiver>();

    public Structure(StructureMap structureMap) {
        this.structureMap = structureMap;
    }

    public void initiate(World world, Coordinate3D blockPosition, Block baseBlock, int metadata) {
        this.world = world;
        this.baseBlockCoords = blockPosition;
        this.baseBlock = new StructureBlock(baseBlock, metadata);
        initiated = true;
    }

    public void notifyChangesTo(IStructureChangeReceiver scr) {
        changeReceiver.add(scr);
    }

    private void calculateBaseCoordinates(int rotation) {
        if(initiated) {
            Coordinate3D blockCoordinate = baseBlockCoords.clone();
            Coordinate3D blockCoordinate2 = structureMap.getBlockCoordinates(baseBlock).get(0);
            rotateOnYAxis(blockCoordinate2, rotation);
            Coordinate3D baseCoordinate = blockCoordinate.moveBack(blockCoordinate2);
            coordinate = baseCoordinate;
        }
    }

    private Coordinate3D rotateOnYAxis(Coordinate3D coord, int rotation) {
        int x = (int) coord.getXCoord();
        int z = (int) coord.getZCoord();
        switch(rotation) {
            case 0: {
                coord.x = x;
                coord.z = z;
                break;
            }
            case 1: {
                coord.x = -z;
                coord.z = x;
                break;
            }
            case 2: {
                coord.x = -x;
                coord.z = -z;
                break;
            }
            case 3: {
                coord.x = z;
                coord.z = -x;
                break;
            }
        }
        return coord;
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
            calculateBaseCoordinates(getRotation());
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
     * 
     * @param block
     *            doesn't need to have a position value
     * @param number
     *            the number of blocks to exist for the method to return true
     * @return true if the number of given blocks >= number. if number == -1 it
     *         checks for all the blocks
     */
    public boolean doesBlockExistInStructure(IStructureBlock block, int number) {
        if(initiated) {
            List<Coordinate3D> list = getBlockCoordinates(block);
            List<Coordinate3D> exists = new ArrayList<Coordinate3D>();
            for(Coordinate3D coord : list) {
                if(block.getReplaceableBlocks() != null) {
                    for(IStructureBlock blck : block.getReplaceableBlocks()) {
                        if(world.getBlockId((int) coord.x, (int) coord.y, (int) coord.z) == blck.getBlock().blockID) {
                            if(world.getBlockMetadata((int) coord.x, (int) coord.y, (int) coord.z) == block.getMetadata()) {
                                exists.add(coord);
                            }
                        }
                    }
                } else {
                    if(world.getBlockId((int) coord.x, (int) coord.y, (int) coord.z) == block.getBlock().blockID) {
                        if(world.getBlockMetadata((int) coord.x, (int) coord.y, (int) coord.z) == block.getMetadata()) {
                            exists.add(coord);
                        }
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
        return structureMap.getRotation(getRotation()).getBlocks();
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
        if(initiated && currentRotation == -1) {
            doesStructureExist();
        }
        return currentRotation;
    }

    public float getRotationInDegrees() {
        if(initiated) {
            return getRotation() * 90;
        }
        return -1F;
    }

    public boolean doesStructureExist() {
        if(initiated) {
            for(int i = 0; i < 4; i++) {
                List<IStructureBlock> exists = new ArrayList<IStructureBlock>();
                StructureMap structureMap = this.structureMap.getRotation(i);
                calculateBaseCoordinates(i);
                for(IStructureBlock block : structureMap.getBlocks()) {
                    if(block.getReplaceableBlocks() != null) {
                        for(IStructureBlock blck : block.getReplaceableBlocks()) {
                            if(world.getBlockId((int) coordinate.x + (int) block.getPosition().x, (int) coordinate.y + (int) block.getPosition().y, (int) coordinate.z + (int) block.getPosition().z) == blck.getBlock().blockID) {
                                if(world.getBlockMetadata((int) coordinate.x + (int) block.getPosition().x, (int) coordinate.y + (int) block.getPosition().y, (int) coordinate.z + (int) block.getPosition().z) == blck.getMetadata()) {
                                    if(block.canFormStructure(structureMap, world, (int) coordinate.x + (int) block.getPosition().x, (int) coordinate.y + (int) block.getPosition().y, (int) coordinate.z + (int) block.getPosition().z)) {
                                        exists.add(block);
                                    }
                                }
                            }
                        }
                    } else {
                        if(world.getBlockId((int) coordinate.x + (int) block.getPosition().x, (int) coordinate.y + (int) block.getPosition().y, (int) coordinate.z + (int) block.getPosition().z) == block.getBlock().blockID) {
                            if(world.getBlockMetadata((int) coordinate.x + (int) block.getPosition().x, (int) coordinate.y + (int) block.getPosition().y, (int) coordinate.z + (int) block.getPosition().z) == block.getMetadata()) {
                                if(block.canFormStructure(structureMap, world, (int) coordinate.x + (int) block.getPosition().x, (int) coordinate.y + (int) block.getPosition().y, (int) coordinate.z + (int) block.getPosition().z)) {
                                    exists.add(block);
                                }
                            }
                        }
                    }
                    if(!block.needsToExistForStructureToForm()) {
                        if(world.getBlockId((int) coordinate.x + (int) block.getPosition().x, (int) coordinate.y + (int) block.getPosition().y, (int) coordinate.z + (int) block.getPosition().z) == 0) {
                            if(!exists.contains(block)) {
                                exists.add(block);
                            }
                        }
                    }
                }
                if(exists.size() == structureMap.getBlockCount()) {
                    if(airCheck(i)) {
                        int oldRotation = currentRotation;
                        this.currentRotation = i;
                        if(oldRotation != currentRotation) {
                            updateChangeReceivers();
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void updateChangeReceivers() {
        if(!changeReceiver.isEmpty()) {
            for(IStructureChangeReceiver iscr : changeReceiver) {
                iscr.onStructureChange();
            }
        }
    }

    private boolean airCheck(int rotation) {
        List<Coordinate3D> shouldBeFull = new ArrayList<Coordinate3D>();
        List<Coordinate3D> actuallyFull = new ArrayList<Coordinate3D>();
        StructureMap map = structureMap.getRotation(rotation);
        for(IStructureBlock block : map.getBlocks()) {
            shouldBeFull.add(block.getPosition());
        }
        if(map.length >= 0 && map.height >= 0 && map.width >= 0) {
            for(int i = 0; i < map.length; i++) {
                for(int j = 0; j < map.height; j++) {
                    for(int k = 0; k < map.width; k++) {
                        if(world.getBlockId((int) coordinate.x + i, (int) coordinate.y + j, (int) coordinate.z + k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(map.length >= 0 && map.height >= 0 && map.width < 0) {
            for(int i = 0; i < map.length; i++) {
                for(int j = 0; j < map.height; j++) {
                    for(int k = 0; k > map.width; k--) {
                        if(world.getBlockId((int) coordinate.x + i, (int) coordinate.y + j, (int) coordinate.z + k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(map.length >= 0 && map.height < 0 && map.width >= 0) {
            for(int i = 0; i < map.length; i++) {
                for(int j = 0; j > map.height; j--) {
                    for(int k = 0; k < map.width; k++) {
                        if(world.getBlockId((int) coordinate.x + i, (int) coordinate.y + j, (int) coordinate.z + k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(map.length >= 0 && map.height < 0 && map.width < 0) {
            for(int i = 0; i < map.length; i++) {
                for(int j = 0; j > map.height; j--) {
                    for(int k = 0; k > map.width; k--) {
                        if(world.getBlockId((int) coordinate.x + i, (int) coordinate.y + j, (int) coordinate.z + k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(map.length < 0 && map.height >= 0 && map.width >= 0) {
            for(int i = 0; i > map.length; i--) {
                for(int j = 0; j < map.height; j++) {
                    for(int k = 0; k < map.width; k++) {
                        if(world.getBlockId((int) coordinate.x + i, (int) coordinate.y + j, (int) coordinate.z + k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(map.length < 0 && map.height >= 0 && map.width < 0) {
            for(int i = 0; i > map.length; i--) {
                for(int j = 0; j < map.height; j++) {
                    for(int k = 0; k > map.width; k--) {
                        if(world.getBlockId((int) coordinate.x + i, (int) coordinate.y + j, (int) coordinate.z + k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(map.length < 0 && map.height < 0 && map.width >= 0) {
            for(int i = 0; i > map.length; i--) {
                for(int j = 0; j > map.height; j--) {
                    for(int k = 0; k < map.width; k++) {
                        if(world.getBlockId((int) coordinate.x + i, (int) coordinate.y + j, (int) coordinate.z + k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(map.length < 0 && map.height < 0 && map.width < 0) {
            for(int i = 0; i > map.length; i--) {
                for(int j = 0; j > map.height; j--) {
                    for(int k = 0; k > map.width; k--) {
                        if(world.getBlockId((int) coordinate.x + i, (int) coordinate.y + j, (int) coordinate.z + k) != 0) {
                            actuallyFull.add(new Coordinate3D(i, j, k));
                        }
                    }
                }
            }
        }
        if(shouldBeFull.size() == actuallyFull.size()) {
            return true;
        } else {
            int index = 0;
            for(IStructureBlock block : map.getBlocks()) {
                for(Coordinate3D coord : actuallyFull) {
                    if(!block.needsToExistForStructureToForm()) {
                        if(!block.getPosition().equals(coord)) {
                            index++;
                        }
                        if(index == actuallyFull.size()) {
                            shouldBeFull.remove(block.getPosition());
                        }
                    }
                }
                index = 0;
            }
        }
        if(shouldBeFull.size() == actuallyFull.size()) {
            return true;
        }
        return false;
    }
}
