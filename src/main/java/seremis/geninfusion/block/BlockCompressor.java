package seremis.geninfusion.block;

import java.util.List;
import java.util.Random;

import seremis.geninfusion.GeneticInfusion;
import seremis.geninfusion.lib.Blocks;
import seremis.geninfusion.lib.RenderIds;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.item.ModItems;
import seremis.geninfusion.tileentity.TileCompressor;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCompressor extends GIBlockContainer {

    private Random random;

    private String[] sidedTextureNames = {"top", "side"};

    public BlockCompressor(Material material) {
        super(material);
        setBlockName(Blocks.COMPRESSOR_UNLOCALIZED_NAME);
        setNeedsSidedTexture(true, sidedTextureNames);
        random = new Random();
        setHardness(10.0F);
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
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public int getRenderType() {
        return RenderIds.CompressorRenderID;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB AABB, List list, Entity entity) {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
        float f = 0.125F;
        setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
        setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
        setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, AABB, list, entity);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        switch(side) {
            case 0:
                return getSidedIcons()[0];
            case 1:
                return getSidedIcons()[0];
            default:
                return getSidedIcons()[1];
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if(CommonProxy.instance.isRenderWorld(world)) {
            return;
        }
        TileCompressor tile = (TileCompressor) world.getTileEntity(x, y, z);
        if(tile != null && entity instanceof EntityItem) {
            if(((EntityItem) entity).getEntityItem().isItemEqual(new ItemStack(ModItems.crystalShard))) {
                if(tile.setInventorySlot(0, ((EntityItem) entity).getEntityItem())) {
                    CommonProxy.instance.removeEntity(entity);
                }
            }
        } else if(entity instanceof EntityLiving) {
            entity.attackEntityFrom(GeneticInfusion.damageCompressor, 3);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        TileCompressor tile = (TileCompressor) world.getTileEntity(x, y, z);
        if(tile == null || CommonProxy.instance.isRenderWorld(world)) {
            return true;
        }
        ItemStack cont = tile.getStackInSlot(0);
        if(player.isSneaking()) {
            dispense(world, x, y, z, random, 1);
            world.markBlockForUpdate(x, y, z);
        }
        if(cont != null) {// TODO change this text in language supported
        	player.addChatMessage(new ChatComponentText("This Compressor contains " + cont.stackSize + " " + cont.getDisplayName()));
        } else {
        	player.addChatMessage(new ChatComponentText("This Compressor is empty."));
        }

        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileCompressor();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6) {
        dropItems(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    private void dropItems(World world, int x, int y, int z) {
        Random rand = new Random();

        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if(!(tileEntity instanceof IInventory)) {
            return;
        }
        IInventory inventory = (IInventory) tileEntity;

        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack item = inventory.getStackInSlot(i);

            if(item != null && item.stackSize > 0) {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));

                if(item.hasTagCompound()) {
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
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbourBlock) {
        if(neighbourBlock != null && neighbourBlock.canProvidePower()) {
            boolean var6 = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z);

            if(var6) {
                world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
            }
        }
    }

    @Override
    public int tickRate(World world) {
        return 3;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if(CommonProxy.instance.isServerWorld(world) && (world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z))) {
            dispense(world, x, y, z, random, 64);
        }
    }

    public void dispense(World world, int x, int y, int z, Random random, int stackSize) {
        TileCompressor tile = (TileCompressor) world.getTileEntity(x, y, z);
        ItemStack currStack = tile.getStackInSlot(0);
        ItemStack dropStack;
        int dropStackSize;

        if(currStack == null || currStack.stackSize <= 0) {
            return;
        }
        if(currStack.stackSize >= 64) {
            dropStackSize = 64;
        } else if(currStack.stackSize <= stackSize) {
            dropStackSize = currStack.stackSize;
        } else {
            dropStackSize = stackSize;
        }
        dropStack = new ItemStack(currStack.getItem(), dropStackSize, currStack.getItemDamage());
        currStack = tile.decrStackSize(0, stackSize);
        world.spawnEntityInWorld(new EntityItem(world, x, y + 1, z, dropStack));
        world.markBlockForUpdate(x, y, z);
    }
}
