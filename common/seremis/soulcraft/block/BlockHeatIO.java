package seremis.soulcraft.block;

import seremis.soulcraft.core.lib.Blocks;
import seremis.soulcraft.tileentity.TileHeatIO;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHeatIO extends SCBlockContainer {

    public BlockHeatIO(Material material) {
        super(material);
        setBlockName(Blocks.HEAT_IO_UNLOCALIZED_NAME);
        setNeedsIcon(false);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileHeatIO();
    }
}