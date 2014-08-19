package seremis.geninfusion.api.soul;

import java.util.List;

/**
 * @author Seremis
 */
public interface IGeneGroup {

    void addGeneToGroup(String gene);

    String getDecisiveGeneString();

    List<String> getGenes();
}
