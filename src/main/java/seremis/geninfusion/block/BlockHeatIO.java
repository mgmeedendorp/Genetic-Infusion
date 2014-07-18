package seremis.geninfusion.block;

import seremis.geninfusion.lib.Blocks;
import seremis.geninfusion.tileentity.TileHeatIO;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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