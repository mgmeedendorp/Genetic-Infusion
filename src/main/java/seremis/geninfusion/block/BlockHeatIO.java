package seremis.geninfusion.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.geninfusion.lib.Blocks;
import seremis.geninfusion.tileentity.TileHeatIO;

public class BlockHeatIO extends GIBlockContainer {

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