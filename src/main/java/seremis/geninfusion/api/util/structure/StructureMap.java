package seremis.geninfusion.api.util.structure;

import seremis.geninfusion.api.util.Coordinate3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StructureMap {

    protected List<IStructureBlock> blocks = new ArrayList<IStructureBlock>();

    protected int length;
    protected int width;
    protected int height;

    public StructureMap(IStructureBlock... block) {
        blocks.addAll(Arrays.asList(block));
        calculateSize();
    }

    public void addBlock(IStructureBlock block) {
        blocks.add(block);
        calculateSize();
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
            maxX -= 1;
        } else {
            maxX += 1;
        }
        if(maxY < 0) {
            maxY -= -1;
        } else {
            maxY += 1;
        }
        if(maxZ < 0) {
            maxZ -= 1;
        } else {
            maxZ += 1;
        }
        length = maxX;
        height = maxY;
        width = maxZ;
    }

    public IStructureBlock getBlockAtCoordinate(int x, int y, int z) {
        for(IStructureBlock block : blocks) {
            if(block.getPosition().x == x && block.getPosition().y == y && block.getPosition().z == z) {
                return block;
            }
        }
        return null;
    }

    public List<Coordinate3D> getBlockCoordinates(IStructureBlock block) {
        List<Coordinate3D> blockCoords = new ArrayList<Coordinate3D>();
        Coordinate3D blockCoord = new Coordinate3D();

        for(IStructureBlock sBlock : getBlocks()) {
            blockCoord = new Coordinate3D();
            if(sBlock.getBlock() == block.getBlock()) {
                if(sBlock.getMetadata() == block.getMetadata()) {
                    blockCoord.move(sBlock.getPosition());
                    blockCoords.add(blockCoord);
                }
            }
        }

        return blockCoords;
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

    public StructureMap getRotation(int rotation) {
        return rotateOnYAxis(rotation);
    }

    public StructureMap rotateOnYAxis(int rotation) {
        StructureMap newStructure = new StructureMap();

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
        return newStructure;
    }
}
