package Seremis.SoulCraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.tileentity.TileCrystalStand;
import Seremis.SoulCraft.util.UtilBlock;

public class BlockCrystalStand extends SCBlockContainer {

    public BlockCrystalStand(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName("crystalStand");
        setNeedsIcon(false);
        setHardness(0.5F);
        setBlockBounds(0.25F, 0.0F, 0.25F, 0.8125F, 0.75F, 0.8125F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
        if(CommonProxy.proxy.isRenderWorld(world)) {
            return true;
        }
        TileCrystalStand tile = (TileCrystalStand) world.getBlockTileEntity(x, y, z);
        ItemStack currPlayerItem = player.getCurrentEquippedItem();
        boolean hasCrystal = tile.hasCrystal();

        if(!hasCrystal && currPlayerItem != null && currPlayerItem.itemID == ModBlocks.crystal.blockID) {
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
    public void breakBlock(World world, int x, int y, int z, int blockId, int metadata) {
        TileCrystalStand tile = (TileCrystalStand) world.getBlockTileEntity(x, y, z);
        if(tile.hasCrystal()) {
            UtilBlock.dropItemInWorld(x, y, z, world, new ItemStack(ModBlocks.crystal, 1));
        }
        super.breakBlock(world, x, y, z, blockId, metadata);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileCrystalStand();
    }

    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        if(CommonProxy.proxy.isServerWorld(world)) {
            int id = world.getBlockId(x, y-1, z);
            
            if(id == 0 || !Block.blocksList[id].isBlockSolidOnSide(world, x, y-1, z, ForgeDirection.UP)) {
                dropBlockAsItem_do(world, x, y, z, new ItemStack(blockID, 1, 0));
                world.setBlock(x, y, z, 0);
            }
        }
    }
}
