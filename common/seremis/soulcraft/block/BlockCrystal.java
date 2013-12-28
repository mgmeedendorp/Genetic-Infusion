package seremis.soulcraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.soulcraft.core.lib.Blocks;
import seremis.soulcraft.core.lib.RenderIds;
import seremis.soulcraft.tileentity.TileCrystal;

public class BlockCrystal extends SCBlockContainer {

    public BlockCrystal(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName(Blocks.CRYSTAL_UNLOCALIZED_NAME);
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
    public TileEntity createNewTileEntity(World world) {
        return new TileCrystal();
    }
}
