package Seremis.SoulCraft.util.structure;

import net.minecraft.block.Block;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.structure.StructureMap;
import Seremis.SoulCraft.api.util.structure.StructureBlock;
import Seremis.SoulCraft.block.ModBlocks;

public class ModStructures {
    
    public static StructureMap magnetStation = new StructureMap();
    public static int[] listX = new int[]{0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 2, 0, 1, 2, 0, 2, 0, 1, 2, 0, 2, 0, 1, 2};
    public static int[] listY = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 1, 2, 2, 2};
    public static int[] listZ = new int[]{0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3};
    public static Block[] blockList = new Block[]{ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick ,ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick ,ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.stationController, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.connectedGlass, ModBlocks.titaniumBrick};     

    public static void init() {
        
        for(int i = 0; i<blockList.length; i++) {
            magnetStation.addBlock(new StructureBlock(blockList[i], 0, new Coordinate3D(listX[i], listY[i], listZ[i])));
        }
        magnetStation.addBlock(new StructureBlockCrystalStand(new Coordinate3D(1, 1, 3)));
        magnetStation.addBlock(new StructureBlock(ModBlocks.transporter, 0, new Coordinate3D(1, 1, 1), false));
    }
}