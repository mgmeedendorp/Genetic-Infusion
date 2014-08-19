package seremis.geninfusion.api.soul;

import java.util.List;

/**
 * @author Seremis
 */
public interface IGeneGroupRegistry {

    void registerGeneGroup(String groupName, IGeneGroup geneGroup);

    List<IGeneGroup> getGeneGroups();
}
