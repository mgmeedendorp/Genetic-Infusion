package seremis.soulcraft.util.structure;

import java.util.ArrayList;
import java.util.List;

import seremis.soulcraft.api.util.Coordinate3D;
import seremis.soulcraft.api.util.structure.IStructureBlock;
import seremis.soulcraft.api.util.structure.StructureBlock;
import seremis.soulcraft.api.util.structure.StructureBlockChangeable;
import seremis.soulcraft.api.util.structure.StructureMap;
import seremis.soulcraft.block.ModBlocks;
import net.minecraft.block.Block;

public class ModStructures {

    public static StructureMap magnetStation = new StructureMap();
    public static int[] listX = new int[] {0, 1, 2, 0, 2, 0, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 2, 0, 1, 2, 0, 2, 0, 1, 2, 0, 2, 0, 1, 2};
    public static int[] listY = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 1, 2, 2, 2};
    public static int[] listZ = new int[] {0, 0, 0, 1, 1, 2, 2, 3, 3, 3, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3};
    public static Block[] blockList = new Block[] {ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.stationController, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.connectedGlass, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.titaniumBrick, ModBlocks.connectedGlass, ModBlocks.titaniumBrick};

    public static void init() {

        for(int i = 0; i < blockList.length; i++) {
            magnetStation.addBlock(new StructureBlock(blockList[i], 0, new Coordinate3D(listX[i], listY[i], listZ[i])));
        }
        magnetStation.addBlock(new StructureBlockCrystalStand(new Coordinate3D(1, 1, 3)));
        
        List<IStructureBlock> itemIOChangeable = new ArrayList<IStructureBlock>();
        itemIOChangeable.add(new StructureBlock(ModBlocks.itemIO));
        itemIOChangeable.add(new StructureBlock(ModBlocks.titaniumBrick));
        
        magnetStation.addBlock(new StructureBlockChangeable(itemIOChangeable, new Coordinate3D(1, 0, 1)));
        magnetStation.addBlock(new StructureBlockChangeable(itemIOChangeable, new Coordinate3D(1, 0, 2)));
    }
}