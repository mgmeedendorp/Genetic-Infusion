package seremis.soulcraft.util.structure;

import seremis.soulcraft.api.util.Coordinate3D;
import seremis.soulcraft.api.util.structure.IStructureBlock;
import seremis.soulcraft.api.util.structure.StructureBlock;
import seremis.soulcraft.api.util.structure.StructureMap;
import seremis.soulcraft.block.ModBlocks;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.tileentity.TileCrystalStand;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
