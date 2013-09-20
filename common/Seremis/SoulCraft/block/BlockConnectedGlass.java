package Seremis.SoulCraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import Seremis.SoulCraft.core.lib.Blocks;
import Seremis.SoulCraft.helper.ConnectedTextureHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockConnectedGlass extends SCBlock {

    private ConnectedTextureHelper textureHelper = new ConnectedTextureHelper(this, Blocks.CONNECTED_GLASS_UNLOCALIZED_NAME);

    public BlockConnectedGlass(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName(Blocks.CONNECTED_GLASS_UNLOCALIZED_NAME);
        setHardness(2F);
        setResistance(3F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconregister) {
        super.registerIcons(iconregister);
        textureHelper.registerIcons(iconregister);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return textureHelper.getBlockTexture(blockAccess, x, y, z, side);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockaccess, int x, int y, int z, int side) {
        int blockID = blockaccess.getBlockId(x, y, z);
        return blockID == this.blockID ? false : super.shouldSideBeRendered(blockaccess, x, y, z, side);
    }

    @Override
    public int getRenderBlockPass() {
        return 0;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
}
