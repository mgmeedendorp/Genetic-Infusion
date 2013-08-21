package Seremis.SoulCraft.util.structure;

import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.structure.Structure;
import Seremis.SoulCraft.api.util.structure.StructureBlock;
import Seremis.SoulCraft.block.ModBlocks;

public class ModStructures {

    public static Structure magnetStation = new Structure();
    
    public static void init() {
        for(int i=0; i<5; i++) {
            for(int j = 0; j<3; j++) {
                if(i != 2 && j != 0) 
                magnetStation.addBlock(new StructureBlock(ModBlocks.titaniumBrick, 0, new Coordinate3D(i, 0, j)));
            }
        }
        magnetStation.addBlock(new StructureBlock(ModBlocks.stationController, 0, new Coordinate3D(2, 0, 0)));
        
        magnetStation.addBlock(new StructureBlock(ModBlocks.titaniumBrick, 0, new Coordinate3D(0, 1, 0)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.titaniumBrick, 0, new Coordinate3D(0, 2, 0)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.titaniumBrick, 0, new Coordinate3D(4, 1, 0)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.titaniumBrick, 0, new Coordinate3D(4, 2, 0)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.titaniumBrick, 0, new Coordinate3D(0, 1, 2)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.titaniumBrick, 0, new Coordinate3D(0, 2, 2)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.titaniumBrick, 0, new Coordinate3D(4, 1, 2)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.titaniumBrick, 0, new Coordinate3D(4, 2, 2)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.titaniumBrick, 0, new Coordinate3D(0, 3, 1)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.titaniumBrick, 0, new Coordinate3D(4, 3, 1)));
        
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(1, 1, 0)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(1, 2, 0)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(2, 1, 0)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(2, 2, 0)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(3, 1, 0)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(3, 2, 0)));
        
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(1, 1, 2)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(1, 2, 2)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(2, 1, 2)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(2, 2, 2)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(3, 1, 2)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(3, 2, 2)));
        
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(1, 3, 1)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(2, 3, 1)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(3, 3, 1)));

        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(0, 2, 1)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.connectedGlass, 0, new Coordinate3D(4, 2, 1)));
        
        magnetStation.setSize(5, 3, 4);
    }
}
