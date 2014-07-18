package seremis.geninfusion.block;

import seremis.geninfusion.lib.Blocks;
import seremis.geninfusion.tileentity.TileItemIO;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
