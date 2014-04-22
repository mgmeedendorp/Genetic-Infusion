package seremis.soulcraft.block;

import seremis.soulcraft.core.lib.Blocks;
import seremis.soulcraft.core.lib.RenderIds;
import seremis.soulcraft.helper.ConnectedTextureHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockConnectedGlass extends SCBlock {

    private ConnectedTextureHelper textureHelper = new ConnectedTextureHelper(this, Blocks.CONNECTED_GLASS_UNLOCALIZED_NAME);

    public BlockConnectedGlass(Material material) {
        super(material);
        setBlockName(Blocks.CONNECTED_GLASS_UNLOCALIZED_NAME);
        setHardness(2F);
        setResistance(3F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister) {
        super.registerBlockIcons(iconregister);
        textureHelper.registerBlockIcons(iconregister);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return textureHelper.getIcon(blockAccess, x, y, z, side);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockaccess, int x, int y, int z, int side) {
        return blockaccess.getBlock(x, y, z) == this ? false : super.shouldSideBeRendered(blockaccess, x, y, z, side);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
}
