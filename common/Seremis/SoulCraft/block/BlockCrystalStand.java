package Seremis.SoulCraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.tileentity.TileCrystalStand;
import Seremis.SoulCraft.util.UtilBlock;

public class BlockCrystalStand extends SCBlock {

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
        TileCrystalStand tile = (TileCrystalStand) (world.getBlockTileEntity(x, y, z));
        ItemStack currPlayerItem = player.getCurrentEquippedItem();
        ItemStack currStack = tile.getStackInSlot(0);
        if(currPlayerItem != null && currPlayerItem.itemID == ModItems.thermometer.itemID) {
            return false;
        }
        if(currPlayerItem != null && tile != null && currStack == null && currPlayerItem.itemID == ModBlocks.crystal.blockID) {
            tile.setInventorySlotContents(0, new ItemStack(ModBlocks.crystal, 1));
            player.getCurrentEquippedItem().stackSize--;
            world.markBlockForRenderUpdate(x, y, z);
        }
        if(tile != null && currStack != null && currPlayerItem == null) {
            tile.setInventorySlotContents(0, null);
            if(CommonProxy.proxy.isServerWorld(world)) {
                if(currStack != null && currStack.stackSize > 0) {
                    UtilBlock.dropItemsFromTile(world, x, y, z, 0);
                }
            }
        }
        world.markBlockForRenderUpdate(x, y, z);
        return true;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderIds.CrystalStandRenderID;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
        UtilBlock.dropItemsFromTile(world, x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileCrystalStand();
    }

}
