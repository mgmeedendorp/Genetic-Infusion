package SoulCraft.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import SoulCraft.mod_SoulCraft;
import SoulCraft.core.DefaultProps;
import SoulCraft.items.ModItems;
import SoulCraft.proxy.CommonProxy;
import SoulCraft.tileentity.TileCompressor;

public class Compressor extends SCBlock {

	private Random random;
	
	public Compressor(int ID, Material material) {
		super(ID, 0, material);
		setBlockName("compressor");
		random = new Random();
		this.setHardness(10.0F);
	}
	
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return DefaultProps.CompressorRenderID;
	}
	
	@Override
    public void addCollidingBlockToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
    	float var8 = 0.0625F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var8, 1.0F);
        super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, var8, 1.0F, 1.0F);
        super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var8);
        super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(1.0F - var8, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - var8, 1.0F, 1.0F, 1.0F);
        super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		switch(side) {
		case 0: return 6;
		case 1: return 6;
		default: return 5;
		}
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(CommonProxy.proxy.isRenderWorld(world)){return;}
		TileCompressor tile = (TileCompressor)(world.getBlockTileEntity(x, y, z));
		if(tile != null && entity instanceof EntityItem){
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1F, 1.0F);
			if(((EntityItem) entity).func_92014_d().itemID == ModItems.ShardIsolatzium.itemID) {
				if(tile.setInventorySlot(0, ((EntityItem) entity).func_92014_d())) {
					CommonProxy.proxy.removeEntity(entity);
				}
			}
		} else if(entity instanceof EntityLiving) {
			entity.attackEntityFrom(mod_SoulCraft.damageCompressor, 3);
		}
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileCompressor tile = (TileCompressor)(world.getBlockTileEntity(x, y, z));
		if(tile == null || CommonProxy.proxy.isRenderWorld(world)) {return true;}
		ItemStack cont = tile.getStackInSlot(0);
		if(player.isSneaking()) {
			dispense(world, x, y, z, random, 1);
			world.markBlockForUpdate(x, y, z);
		}
		if(cont != null) {
			player.addChatMessage("This Compressor contains " + cont.stackSize + " " + cont.getItem().getItemDisplayName(cont));
		} else {
			player.addChatMessage("This Compressor does not contain anything.");
		}
		
		return true;
    }
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileCompressor();
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

                            EntityItem entityItem = new EntityItem(world,
                                            x + rx, y + ry, z + rz,
                                            new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

                            if (item.hasTagCompound()) {
                                    entityItem.func_92014_d().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
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
    
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 > 0 && Block.blocksList[par5].canProvidePower())
        {
            boolean var6 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);

            if (var6)
            {
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
            }
        }
    }
    
    @Override
    public int tickRate()
    {
        return 3;
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (CommonProxy.proxy.isServerWorld(world) && (world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z)))
        {
            this.dispense(world, x, y, z, random, 64);
        }
    }
    
    public void dispense(World world, int x, int y, int z, Random random, int stackSize) {
    	TileCompressor tile = (TileCompressor)(world.getBlockTileEntity(x, y, z));
    	ItemStack currStack = tile.getStackInSlot(0);
    	ItemStack dropStack;
    	int dropStackSize;
    	
    	if(currStack == null || currStack.stackSize <= 0) {return;}
    	if(currStack.stackSize >= 64) {
    		dropStackSize = 64;
    	} else if(currStack.stackSize <= stackSize) {
    		dropStackSize = currStack.stackSize;
    	} else {
    		dropStackSize = stackSize;
    	}
    	dropStack = new ItemStack(currStack.itemID, dropStackSize, currStack.getItemDamage());
    	currStack = tile.decrStackSize(0, stackSize);
    	world.spawnEntityInWorld(new EntityItem(world, x, y+1, z, dropStack));
    	world.markBlockForUpdate(x, y, z);
    }
}
