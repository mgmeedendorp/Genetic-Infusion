package Seremis.SoulCraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.mod_SoulCraft;
import Seremis.SoulCraft.api.magnet.block.BlockMagnetConnector;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.tileentity.TileCrystalStand;

public class BlockCrystalStand extends BlockMagnetConnector {
    
	public BlockCrystalStand(int ID, Material material) {
		super(ID, material);
		setUnlocalizedName("crystalStand");
		setHardness(0.5F);
		setCreativeTab(mod_SoulCraft.CreativeTab);
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister) {}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
	    super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
	    ItemStack playerItem = player.inventory.getCurrentItem();
	    if(playerItem != null) {
	        TileEntity tile = world.getBlockTileEntity(x, y, z);
	        if(tile instanceof TileCrystalStand) {
	            if(((TileCrystalStand)tile).inv == null && playerItem.itemID == ModBlocks.Crystal.blockID) {
	                ((TileCrystalStand)tile).setInventorySlotContents(0, new ItemStack(ModBlocks.Crystal));
	                player.inventory.consumeInventoryItem(ModBlocks.Crystal.blockID);
	            } else {
	                ((TileCrystalStand)tile).setInventorySlotContents(0, null);
	                if(world.isRemote) {
	                    world.spawnEntityInWorld(new EntityItem(world, maxX, maxY, maxZ, new ItemStack(ModBlocks.Crystal, 1)));
	                }
	            }
	        }
	        return true;
	    }
	    return false;
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
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileCrystalStand();
    }

}
