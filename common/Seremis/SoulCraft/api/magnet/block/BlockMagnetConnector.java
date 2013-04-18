package Seremis.SoulCraft.api.magnet.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.api.magnet.item.IMagneticTool;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;

public class BlockMagnetConnector extends BlockContainer {

    public Random rand = new Random();
    
    public BlockMagnetConnector(int blockId, Material material) {
        super(blockId, material);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        Item playerItem = player.inventory.getCurrentItem().getItem();
        if(playerItem instanceof IMagneticTool) {
            return true;
        }
        return false;
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int blockId, int metadata) {
        dropInventory(world, x, y, z);
        disConnectLinks(world, x, y, z);
        super.breakBlock(world, x, y, z, blockId, metadata);
    }
    
    private void dropInventory(World world, int x, int y, int z) {

        TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (!(tile instanceof IInventory))
            return;

        IInventory inventory = (IInventory) tile;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {

            ItemStack itemStack = inventory.getStackInSlot(i);

            if (itemStack != null && itemStack.stackSize > 0) {
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.itemID, itemStack.stackSize, itemStack.getItemDamage()));

                if (itemStack.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }
    
    private void disConnectLinks(World world, int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        
        if(!(tile instanceof IMagnetConnector)) {
            IMagnetConnector conductor = (IMagnetConnector)tile;
            conductor.disconnect();
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return null;
    }

}
