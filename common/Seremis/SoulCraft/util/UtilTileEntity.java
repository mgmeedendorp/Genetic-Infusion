package Seremis.SoulCraft.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

}
