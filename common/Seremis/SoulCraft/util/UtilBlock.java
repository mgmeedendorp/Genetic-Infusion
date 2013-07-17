package Seremis.SoulCraft.util;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class UtilBlock {

    public static void dropItemsFromTile(World world, int x, int y, int z) {

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if(!(tileEntity instanceof IInventory)) {
            return;
        }
        IInventory inventory = (IInventory) tileEntity;
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            dropItemsFromTile(world, x, y, z, i);
        }
    }

    /**
     * Drop all items in the specified slot from the TileEntity.
     * 
     * @param world
     * @param x
     * @param y
     * @param z
     * @param slot
     */
    public static void dropItemsFromTile(World world, int x, int y, int z, int slot) {

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if(!(tileEntity instanceof IInventory)) {
            return;
        }
        IInventory inventory = (IInventory) tileEntity;

        ItemStack item = inventory.getStackInSlot(slot);
        Random rand = new Random();

        if(item != null && item.stackSize > 0) {
            float rx = rand.nextFloat() * 0.8F + 0.1F;
            float ry = rand.nextFloat() * 0.8F + 0.1F;
            float rz = rand.nextFloat() * 0.8F + 0.1F;

            EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

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
