package seremis.soulcraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.soulcraft.api.magnet.MagnetLinkHelper;
import seremis.soulcraft.api.magnet.tile.IMagnetConnector;
import seremis.soulcraft.core.lib.Items;
import seremis.soulcraft.core.proxy.CommonProxy;

public class ItemThermometer extends SCItem {

    private String[] subNames = {Items.THERMOMETER_META_0_UNLOCALIZED_NAME, Items.THERMOMETER_META_1_UNLOCALIZED_NAME};

    public ItemThermometer(int ID) {
        super(ID);
        setHasSubtypes(true);
        setMaxDamage(0);
        setNumbersofMetadata(2);
        setUnlocalizedName(Items.THERMOMETER_UNLOCALIZED_NAME);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
    }

    public IMagnetConnector conn1;

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if(CommonProxy.proxy.isServerWorld(world)) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if(tile != null && tile instanceof IMagnetConnector) {
                player.addChatMessage("Reading...");
                player.addChatMessage("Heat: " + ((IMagnetConnector) tile).getHeat());
                if(stack.getItemDamage() == 1) {
                    player.addChatMessage("Server " + MagnetLinkHelper.instance.getLinksConnectedTo((IMagnetConnector)tile).get(0).toString());
                }
            }
        } else if(stack.getItemDamage() == 1) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if(tile != null && tile instanceof IMagnetConnector) {
                player.addChatMessage("Client " + MagnetLinkHelper.instance.getLinksConnectedTo((IMagnetConnector)tile).get(0).toString());
            }
        }
        return true;
    }

}
