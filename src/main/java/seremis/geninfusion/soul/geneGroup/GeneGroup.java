package seremis.geninfusion.soul.geneGroup;

import seremis.geninfusion.api.soul.IGeneGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seremis
 */
public class GeneGroup implements IGeneGroup {

    protected String decisiveGene;

    protected List<String> genes = new ArrayList<String>();

    public GeneGroup(String decisiveGene) {
        this.decisiveGene = decisiveGene;
    }

    @Override
    public void addGeneToGroup(String gene) {
        genes.add(gene);
    }

    @Override
    public String getDecisiveGeneString() {
        return decisiveGene;
    }

    @Override
    public List<String> getGenes() {
        return genes;
    }
}
