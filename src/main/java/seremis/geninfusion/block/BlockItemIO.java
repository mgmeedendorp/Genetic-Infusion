package seremis.geninfusion.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.geninfusion.lib.Blocks;
import seremis.geninfusion.tileentity.TileItemIO;

public class BlockItemIO extends GIBlockContainer {

    public BlockItemIO(Material material) {
        super(material);
        setBlockName(Blocks.ITEM_IO_UNLOCALIZED_NAME);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileItemIO();
    }
}
