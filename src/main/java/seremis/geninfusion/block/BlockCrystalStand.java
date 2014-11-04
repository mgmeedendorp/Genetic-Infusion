package seremis.geninfusion.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.lib.RenderIds;
import seremis.geninfusion.tileentity.TileCrystalStand;
import seremis.geninfusion.util.UtilBlock;

public class BlockCrystalStand extends GIBlockContainer {

    public BlockCrystalStand(Material material) {
        super(material);
        setBlockName("crystalStand");
        setNeedsIcon(false);
        setHardness(0.5F);
        setBlockBounds(0.25F, 0.0F, 0.25F, 0.8125F, 0.75F, 0.8125F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
        if(CommonProxy.instance.isRenderWorld(world)) {
            return true;
        }
        TileCrystalStand tile = (TileCrystalStand) world.getTileEntity(x, y, z);
        ItemStack currPlayerItem = player.getCurrentEquippedItem();
        boolean hasCrystal = tile.hasCrystal();

        if(!hasCrystal && currPlayerItem != null && currPlayerItem.isItemEqual(new ItemStack(ModBlocks.crystal))) {
            tile.setHasCrystal(true);
            currPlayerItem.stackSize--;
            return true;
        }
        if(hasCrystal) {
            tile.setHasCrystal(false);
            UtilBlock.dropItemInWorld(x, y, z, world, new ItemStack(ModBlocks.crystal, 1));
            return true;
        }
        return true;
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
        return RenderIds.CrystalStandRenderID;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        TileCrystalStand tile = (TileCrystalStand) world.getTileEntity(x, y, z);
        if(tile.hasCrystal()) {
            UtilBlock.dropItemInWorld(x, y, z, world, new ItemStack(ModBlocks.crystal, 1));
        }
        super.breakBlock(world, x, y, z, block, metadata);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileCrystalStand();
    }


    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if(CommonProxy.instance.isServerWorld(world)) {
            Block id = world.getBlock(x, y - 1, z);

            if(id.isAir(world, x, y - 1, z) || !id.isBlockSolid(world, x, y - 1, z, ForgeDirection.UP.ordinal())) {
                dropBlockAsItem(world, x, y, z, new ItemStack(block, 1, 0));
                world.setBlock(x, y, z, null);
            }
        }
    }
}
