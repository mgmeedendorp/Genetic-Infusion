package Seremis.SoulCraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.api.plasma.IPlasmaNetwork;
import Seremis.SoulCraft.api.plasma.PlasmaNetwork;
import Seremis.SoulCraft.api.plasma.PlasmaPacket;
import Seremis.SoulCraft.api.plasma.block.IPlasmaConnector;
import Seremis.SoulCraft.api.plasma.block.IPlasmaContainerItem;
import Seremis.SoulCraft.core.proxy.CommonProxy;

public class PlasmaConnectorTool extends SCItem implements IPlasmaContainerItem {

    private PlasmaPacket inv;
    
    private IPlasmaConnector connector1 = null;
    private IPlasmaConnector connector2 = null;
    
    public PlasmaConnectorTool(int ID) {
        super(ID);
        setUnlocalizedName("toolPlasmaConnector");
        setMaxStackSize(1);
        inv = new PlasmaPacket(1000);
    }
    
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List currentTipList, boolean advancedToolTips) {
        PlasmaPacket pack = inv.readFromNBT(stack.stackTagCompound);
        if(pack == null) {return;}
        currentTipList.add("Plasma: " + pack.getAmount() + "/" + getSize());
    }
    
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int sideHit, float par8, float par9, float par10) {
        if(CommonProxy.proxy.isServerWorld(world)) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if(tile instanceof IPlasmaConnector && ((IPlasmaConnector)tile).connect(ForgeDirection.getOrientation(sideHit))) {
                setConnector((IPlasmaConnector)tile, world, stack);
            }
        }
        return false;
    }
    
    public void setConnector(IPlasmaConnector connector, World world, ItemStack stack) {
        if(CommonProxy.proxy.isRenderWorld(world)){return;}
        if(connector1 == null) {
            connector1 = connector;
        } else if(connector2 == null) {
            connector2 = connector;
        }
        if(connector1 != null && connector2 != null) {
            IPlasmaNetwork net1 = connector1.getNetwork();
            IPlasmaNetwork net2 = connector2.getNetwork();
            IPlasmaNetwork finalNet = new PlasmaNetwork();
            if(net1 != null) {
                finalNet.merge(net1);
                finalNet.addConnectorToNetwork(connector1, null);
            }
            if(net2 != null) {
                finalNet.merge(net2);
                finalNet.addConnectorToNetwork(connector2, null);
            }
            if(net1 != null && net2 != null && net1 != net2) {
                finalNet.merge(net1);
                finalNet.merge(net2);
            } 
            if(net1 == null && net2 == null) {
                finalNet.addConnectorToNetwork(connector1, null);
                finalNet.addConnectorToNetwork(connector2, null);
            }
            finalNet.dividePlasma();
            System.out.println(finalNet.toString());
            addPlasmaToNetwork(finalNet, connector, world, stack);
            resetConnectors();
            System.out.println(finalNet.getTotalPlasmaInNetwork().getAmount());
        }
    }
    
    public void resetConnectors() {
        connector1 = null;
        connector2 = null;
    }
    
    public void addPlasmaToNetwork(IPlasmaNetwork network, IPlasmaConnector connector, World world, ItemStack stack) {
        if(CommonProxy.proxy.isRenderWorld(world)) {return;}
        network.addPlasmaToConnector(connector, drain(stack, 100));
    }

    //PlasmaContainer Start//
    
    @Override
    public PlasmaPacket fill(ItemStack stack, PlasmaPacket pack) {
        inv.addAmount(pack.getAmount());
        if(inv.getAmount()>getSize()) {
            return new PlasmaPacket(inv.getAmount()-getSize());
        }
        inv.writeToNBT(stack.stackTagCompound);
        return null;
    }

    @Override
    public PlasmaPacket drain(ItemStack stack, int amount) {
        PlasmaPacket drained = inv.decreasePacket(amount);
        inv.amount = drained.amount;
        inv.writeToNBT(stack.stackTagCompound);
        return drained;
    }

    @Override
    public int getSize() {
        return 1000;
    }

}
