package Seremis.SoulCraft.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
	            if(((TileCrystalStand)tile).inv == null || ((TileCrystalStand)tile).getStackInSlot(0).stackSize == 0 && playerItem.itemID == ModBlocks.Crystal.blockID) {
	                ((TileCrystalStand)tile).setInventorySlotContents(0, new ItemStack(ModBlocks.Crystal));
	                player.inventory.consumeInventoryItem(ModBlocks.Crystal.blockID);
	            } else {
	                ((TileCrystalStand)tile).setInventorySlotContents(0, null);
	                if(world.isRemote) {
	                    world.spawnEntityInWorld(new EntityItem(world, maxX, maxY, maxZ, new ItemStack(ModBlocks.Crystal, 1)));
	                }
	            }
	        }
	        world.markBlockForRenderUpdate(x, y, z);
	        return true;
	    }
	    world.markBlockForRenderUpdate(x, y, z);
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
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
	    dropItems(world, x, y, z);
	    super.breakBlock(world, x, y, z, par5, par6);
	}

    private void dropItems(World world, int x, int y, int z){
        Random rand = new Random();

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (!(tileEntity instanceof IInventory)) {
            return;
        }
        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack item = inventory.getStackInSlot(i);

            if (item != null && item.stackSize > 0) {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

                if (item.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                item.stackSize = 0;
                }
            }
    }
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileCrystalStand();
    }

}
