package seremis.geninfusion.soul;

import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.api.soul.IMasterGene;
import seremis.geninfusion.api.soul.SoulHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class MasterGene extends Gene implements IMasterGene {

    public List<IGene> controlledGenes = new ArrayList<IGene>();

    @Override
    public void addControlledGene(IGene gene) {
        controlledGenes.add(gene);
    }

    @Override
    public void addControlledGene(String name) {
        controlledGenes.add(SoulHelper.geneRegistry.getGene(name));
    }

    @Override
    public List<IGene> getControlledGenes() {
        return controlledGenes;
    }
}
