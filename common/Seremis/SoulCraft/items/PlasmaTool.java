package Seremis.SoulCraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.item.IMagneticTool;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.core.proxy.CommonProxy;

public class PlasmaTool extends SCItem implements IMagneticTool {

    public IMagnetConnector connector1;
    public IMagnetConnector connector2;
    
    public PlasmaTool(int ID) {
        super(ID);
        setUnlocalizedName("plasmaTool");
        setNumbersofMetadata(-1);
    }
    
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int sideHit, float par8, float par9, float par10) {
        if(CommonProxy.proxy.isServerWorld(world)) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if(tile instanceof IMagnetConnector) {
                setConnector((IMagnetConnector)tile, player);
            }
        }
        return false;
    }
    
    public void setConnector(IMagnetConnector connector, EntityPlayer player) {
        if(connector == null)
            return;
        if(connector1 == null) {
            this.connector1 = connector;
            player.addChatMessage("Link Started");
        } else if(connector2 == null) {
            this.connector2 = connector;
        }
        if(connector1 != null && connector2 != null && connector1 != connector2) {
            new MagnetLink(connector1, connector2);
            player.addChatMessage("Linked");
            resetConnectors();
        } else if(connector1 == connector2) {
            player.addChatMessage("It's impossible to link something to itself!");
            resetConnectors();
        }
        
    }
    
    public void resetConnectors() {
        connector1 = null;
        connector2 = null;
    }
}
