package Seremis.SoulCraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.entity.EntityTransporter;

public class ItemTransporter extends SCItem {

    public ItemTransporter(int ID) {
        super(ID);
        setUnlocalizedName("transporter");
    }
    
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if(CommonProxy.proxy.isServerWorld(world)) {
            EntityTransporter transporter = new EntityTransporter(world, x + 0.5, y + 0.5, z + 0.5);
            world.spawnEntityInWorld(transporter);
        }
        stack.stackSize--;
        return false;
    }
}
