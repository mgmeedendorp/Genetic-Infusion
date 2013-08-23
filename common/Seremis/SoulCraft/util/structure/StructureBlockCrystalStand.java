package Seremis.SoulCraft.util.structure;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.structure.IStructureBlock;
import Seremis.SoulCraft.api.util.structure.Structure;
import Seremis.SoulCraft.api.util.structure.StructureBlock;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.tileentity.TileCrystalStand;

public class StructureBlockCrystalStand extends StructureBlock {

    public StructureBlockCrystalStand(Coordinate3D position) {
        super(ModBlocks.crystalStand, 0, position);
    }

    @Override
    public boolean canFormStructure(Structure structure, World world, int x, int y, int z) {
        if(CommonProxy.proxy.isServerWorld(world)) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            
            if(tile != null && tile instanceof TileCrystalStand) {
                TileCrystalStand tile2 = (TileCrystalStand)tile;
                if(tile2.hasCrystal()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public IStructureBlock copy() {
        return new StructureBlockCrystalStand(getPosition());
    }
}
