package seremis.soulcraft.api.magnet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import seremis.soulcraft.api.magnet.tile.IMagnetConnector;

public class MagnetNetwork {

    private List<MagnetLink> links = new ArrayList<MagnetLink>();
    private List<IMagnetConnector> connectors = new ArrayList<IMagnetConnector>();

    public MagnetNetwork(List<MagnetLink> links) {
        this.links = links;
        addConnectors();
    }

    public MagnetNetwork() {

    }

    private void addConnectors() {
        Iterator<MagnetLink> it = links.iterator();
        while(it.hasNext()) {
            MagnetLink link = it.next();
            if(!connectors.contains(link.connector1)) {
                connectors.add(link.connector1);
            }
            if(!connectors.contains(link.connector2)) {
                connectors.add(link.connector2);
            }
        }
    }

    public void addLink(MagnetLink link) {
        if(!links.contains(link)) {
            links.add(link);
            addConnectors();
        }
    }

    public List<MagnetLink> getLinks() {
        return links;
    }

    public List<IMagnetConnector> getConnectors() {
        return connectors;
    }
}
