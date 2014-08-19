package seremis.geninfusion.soul;

import seremis.geninfusion.api.soul.IGeneGroup;
import seremis.geninfusion.api.soul.IGeneGroupRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Seremis
 */
public class GeneGroupRegistry implements IGeneGroupRegistry {

    public HashMap<String, IGeneGroup> geneGroups = new HashMap<String, IGeneGroup>();

    @Override
    public void registerGeneGroup(String groupName, IGeneGroup geneGroup) {
        geneGroups.put(groupName, geneGroup);
    }

    @Override
    public List<IGeneGroup> getGeneGroups() {
        return new ArrayList<IGeneGroup>(geneGroups.values());
    }
}
