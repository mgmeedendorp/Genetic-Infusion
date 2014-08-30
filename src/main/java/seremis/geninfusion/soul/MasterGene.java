package seremis.geninfusion.soul;

import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.api.soul.IMasterGene;

import java.util.ArrayList;
import java.util.List;

public abstract class MasterGene implements IMasterGene {

    public List<IGene> controlledGenes = new ArrayList<IGene>();

    @Override
    public void addControlledGene(IGene gene) {
        controlledGenes.add(gene);
    }

    @Override
    public List<IGene> getControlledGenes() {
        return controlledGenes;
    }
}
