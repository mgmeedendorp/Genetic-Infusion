package seremis.soulcraft.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class UtilTileEntity {

    public static EntityItem dropItemInWorld(TileEntity tile, World world, ItemStack stack) {
        return new EntityItem(world, tile.xCoord, tile.yCoord, tile.zCoord, stack);
    }

    public static EntityItem dropItemInWorld(TileEntity tile, World world, Item item, int amount) {
        return dropItemInWorld(tile, world, new ItemStack(item, amount));
    }

    public static EntityItem dropItemInWorld(TileEntity tile, World world, Item item, int amount, int metadata) {
        return dropItemInWorld(tile, world, new ItemStack(item, amount, metadata));
    }

    public static void writeInventoryToNBT(IInventory tile, NBTTagCompound compound) {
        NBTTagList nbtTagList = new NBTTagList();

        for(int i = 0; i < tile.getSizeInventory(); ++i) {
            if(tile.getStackInSlot(i) != null) {
                NBTTagCompound compound2 = new NBTTagCompound();
                compound2.setByte("Slot", (byte) i);
                tile.getStackInSlot(i).writeToNBT(compound2);
                nbtTagList.appendTag(compound2);
            }
        }
        compound.setTag("Items", nbtTagList);
    }

    public static ItemStack[] readInventoryFromNBT(IInventory tile, NBTTagCompound compound) {
        ItemStack[] inv;

        NBTTagList nbtTagList = compound.getTagList("Items");
        inv = new ItemStack[tile.getSizeInventory()];

        for(int i = 0; i < nbtTagList.tagCount(); ++i) {
            NBTTagCompound compound2 = (NBTTagCompound) nbtTagList.tagAt(i);
            int var5 = compound2.getByte("Slot") & 255;

            if(var5 >= 0 && var5 < tile.getSizeInventory()) {
                inv[var5] = ItemStack.loadItemStackFromNBT(compound2);
            }
        }

        return inv;
    }

}
