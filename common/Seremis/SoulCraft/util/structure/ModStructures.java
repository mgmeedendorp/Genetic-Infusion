package Seremis.SoulCraft.util.structure;

import net.minecraft.block.Block;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.structure.Structure;
import Seremis.SoulCraft.api.util.structure.StructureBlock;

public class ModStructures {

    public static Structure magnetStation = new Structure();
    
    public static void init() {
        magnetStation.addBlock(new StructureBlock(Block.brick, 0, new Coordinate3D(0, 0, 0)));
    }
}
