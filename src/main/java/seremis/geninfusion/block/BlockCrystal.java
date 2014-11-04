package seremis.geninfusion.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.geninfusion.lib.Blocks;
import seremis.geninfusion.lib.RenderIds;
import seremis.geninfusion.tileentity.TileCrystal;

public class BlockCrystal extends GIBlockContainer {

    public BlockCrystal(Material material) {
        super(material);
        setBlockName(Blocks.CRYSTAL_UNLOCALIZED_NAME);
        setBlockBounds(0.3F, 0.0F, 0.3F, 0.6F, 0.83F, 0.6F);
        setNeedsIcon(false);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderIds.CrystalRenderID;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileCrystal();
    }
}
