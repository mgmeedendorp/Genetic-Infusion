package Seremis.SoulCraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.tileentity.TileIsolatziumCrystal;

public class BlockCrystal extends SCBlock {

    public BlockCrystal(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName("crystal");
        setBlockBounds(0.3F, 0.0F, 0.3F, 0.6F, 0.83F, 0.6F);
        setNeedsIcon(false);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderIds.IsolatziumCrystalRenderID;

    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileIsolatziumCrystal();
    }
}
