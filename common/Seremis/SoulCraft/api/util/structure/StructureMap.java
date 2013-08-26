package Seremis.SoulCraft.api.util.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Seremis.SoulCraft.api.util.Coordinate3D;

public class StructureMap {
    
    protected List<IStructureBlock> blocks = new ArrayList<IStructureBlock>();
    
    protected int length;
    protected int width;
    protected int height;
    
    public StructureMap(IStructureBlock... block) {
        blocks.addAll(Arrays.asList(block));
    }
    
    public void addBlock(IStructureBlock block) {
        blocks.add(block);
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
        return this.rotateOnYAxis(rotation);
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
