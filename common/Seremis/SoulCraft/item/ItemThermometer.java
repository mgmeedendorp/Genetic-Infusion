package Seremis.SoulCraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.core.proxy.CommonProxy;

public class ItemThermometer extends SCItem {

    public ItemThermometer(int ID) {
        super(ID);
        setUnlocalizedName("thermometer");
    }
    
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if(CommonProxy.proxy.isServerWorld(world)) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if(tile != null && tile instanceof IMagnetConnector) {
                player.addChatMessage("Reading...");
                player.addChatMessage("Heat: " + ((IMagnetConnector)tile).getHeat());
            }
        }
        return true;
    }

}
