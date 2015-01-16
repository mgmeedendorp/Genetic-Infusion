package seremis.geninfusion.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.geninfusion.lib.Blocks;
import seremis.geninfusion.lib.RenderIds;
import seremis.geninfusion.tileentity.TileSoulCage;

public class BlockSoulCage extends GIBlockContainer {

    public BlockSoulCage(Material material) {
        super(material);
        setBlockName(Blocks.SOUL_CAGE_UNLOCALIZED_NAME);
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
        return RenderIds.soulCageRenderID;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileSoulCage();
    }
}
