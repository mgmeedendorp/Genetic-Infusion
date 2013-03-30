package Seremis.SoulCraft.items;

import net.minecraft.world.World;
import Seremis.SoulCraft.api.plasma.IPlasmaNetwork;
import Seremis.SoulCraft.api.plasma.PlasmaNetwork;
import Seremis.SoulCraft.api.plasma.PlasmaPacket;
import Seremis.SoulCraft.api.plasma.PlasmaRegistry;
import Seremis.SoulCraft.api.plasma.block.IPlasmaConnector;
import Seremis.SoulCraft.core.proxy.CommonProxy;

public class PlasmaConnectorTool extends SCItem {

    private IPlasmaConnector connector1 = null;
    private IPlasmaConnector connector2 = null;
    
    public PlasmaConnectorTool(int ID) {
        super(ID);
    }
    
    public void setConnector(IPlasmaConnector connector, World world) {
        if(CommonProxy.proxy.isRenderWorld(world)){return;}
        if(connector1 == null) {
            connector1 = connector;
        } else if(connector2 == null) {
            connector2 = connector;
        }
        if(connector1 != null && connector2 != null) {
            IPlasmaNetwork net1 = connector1.getNetwork();
            IPlasmaNetwork net2 = connector2.getNetwork();
            if(net1 != null) {
                net1.addConnectorToNetwork(connector2, new PlasmaPacket());
                net1.dividePlasma();
                System.out.println(net1.toString());
            }
            if(net2 != null) {
                net2.addConnectorToNetwork(connector1, new PlasmaPacket());
                net2.dividePlasma();
                System.out.println(net2.toString());
            }
            if(net1 != null && net2 != null) {
                net1.merge(net2);
                net1.dividePlasma();
            } 
            if(net1 == null && net2 == null) {
                IPlasmaNetwork network = new PlasmaNetwork(connector1, connector2);
                network.dividePlasma();
                PlasmaRegistry.instance.registerPlasmaNetwork(network, network.getNetworkId());
                System.out.println(network.toString());
            }
            resetConnectors();
        }
    }
    
    public void resetConnectors() {
        connector1 = null;
        connector2 = null;
    }

}
