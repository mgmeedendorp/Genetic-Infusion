package seremis.geninfusion.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.geninfusion.api.util.Coordinate3D;
import seremis.geninfusion.core.proxy.CommonProxy;

import java.util.Random;

public class UtilBlock {

    public static Coordinate3D getBlockCoordsAtSide(int x, int y, int z, int side) {
        Coordinate3D coord = new Coordinate3D(x, y, z);
        int[][] shiftMap = {{0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}, {-1, 0, 0}, {1, 0, 0}};

        int shiftx = shiftMap[side][0];
        int shifty = shiftMap[side][1];
        int shiftz = shiftMap[side][2];

        coord.move(shiftx, shifty, shiftz);
        return coord;
    }

    public static EntityItem dropItemInWorld(int x, int y, int z, World world, ItemStack stack) {
        Random rand = new Random();

        float rx = rand.nextFloat() * 0.8F + 0.5F;
        float ry = rand.nextFloat() * 0.8F + 0.5F;
        float rz = rand.nextFloat() * 0.8F + 0.5F;

        EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage()));

        if(stack.hasTagCompound()) {
            entityItem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
        }

        float factor = 0.005F;
        entityItem.motionX = rand.nextGaussian() * factor;
        entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
        entityItem.motionZ = rand.nextGaussian() * factor;
        world.spawnEntityInWorld(entityItem);
        stack.stackSize = 0;

        return entityItem;
    }

    public static EntityItem dropItemInWorld(int x, int y, int z, World world, Item item, int amount) {
        return dropItemInWorld(x, y, z, world, new ItemStack(item, amount));
    }

    public static EntityItem dropItemInWorld(int x, int y, int z, World world, Item item, int amount, int metadata) {
        return dropItemInWorld(x, y, z, world, new ItemStack(item, amount, metadata));
    }

    public static void dropItemsFromTile(World world, int x, int y, int z) {

        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if(!(tileEntity instanceof IInventory) || CommonProxy.instance.isRenderWorld(tileEntity.getWorldObj())) {
            return;
        }
        IInventory inventory = (IInventory) tileEntity;
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            dropItemsFromTile(world, x, y, z, i);
        }
    }

    /**
     * Drop all items in the specified slot from the TileEntity.
     */
    public static void dropItemsFromTile(World world, int x, int y, int z, int slot) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if(!(tileEntity instanceof IInventory) || CommonProxy.instance.isRenderWorld(tileEntity.getWorldObj())) {
            return;
        }
        IInventory inventory = (IInventory) tileEntity;

        ItemStack item = inventory.getStackInSlot(slot);
        Random rand = new Random();

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
