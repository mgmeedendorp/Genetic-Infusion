package seremis.soulcraft.item;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import seremis.soulcraft.api.magnet.tile.IMagnetConnector;
import seremis.soulcraft.api.soul.GeneRegistry;
import seremis.soulcraft.api.soul.util.UtilSoulEntity;
import seremis.soulcraft.core.lib.Items;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.soul.entity.EntitySoulCustom;
import seremis.soulcraft.util.UtilBlock;

public class ItemThermometer extends SCItem {

    private String[] subNames = {Items.THERMOMETER_META_0_UNLOCALIZED_NAME, Items.THERMOMETER_META_1_UNLOCALIZED_NAME};

    public ItemThermometer() {
        super();
        setHasSubtypes(true);
        setMaxDamage(0);
        setNumbersofMetadata(2);
        setUnlocalizedName(Items.THERMOMETER_UNLOCALIZED_NAME);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if(CommonProxy.proxy.isServerWorld(world)) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile != null && tile instanceof IMagnetConnector) {
                player.addChatComponentMessage(new ChatComponentText("Reading..."));
                player.addChatComponentMessage(new ChatComponentText("Heat: " + ((IMagnetConnector) tile).getHeat()));
            }
            if(stack.getItemDamage() == 1) {
                EntitySoulCustom entity = new EntitySoulCustom(world,GeneRegistry.getSoulFor(new EntityZombie(world)), x+0.5F, y+1F, z+0.5F);
                world.spawnEntityInWorld(entity);
            }
        }
        return true;
    }

}
