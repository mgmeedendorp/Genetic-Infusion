package seremis.geninfusion.util.structure;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.geninfusion.api.util.Coordinate3D;
import seremis.geninfusion.api.util.structure.IStructureBlock;
import seremis.geninfusion.api.util.structure.StructureBlock;
import seremis.geninfusion.api.util.structure.StructureMap;
import seremis.geninfusion.block.ModBlocks;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.tileentity.TileCrystalStand;

public class StructureBlockCrystalStand extends StructureBlock {

    public StructureBlockCrystalStand(Coordinate3D position) {
        super(ModBlocks.crystalStand, 0, position);
    }

    @Override
    public boolean canFormStructure(StructureMap structureMap, World world, int x, int y, int z) {
        if(CommonProxy.instance.isServerWorld(world)) {
            TileEntity tile = world.getTileEntity(x, y, z);

            if(tile != null && tile instanceof TileCrystalStand) {
                TileCrystalStand tile2 = (TileCrystalStand) tile;
                if(tile2.hasCrystal()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public IStructureBlock copy() {
        return new StructureBlockCrystalStand(getPosition().clone());
    }
}
