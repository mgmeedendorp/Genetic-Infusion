package Seremis.SoulCraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import Seremis.SoulCraft.helper.ConnectedTexturesHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockConnectedGlass extends SCBlock {

    private ConnectedTexturesHelper textureHelper = new ConnectedTexturesHelper(this, "connectedGlass");
    
    public BlockConnectedGlass(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName("connectedGlass");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconregister) {
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
