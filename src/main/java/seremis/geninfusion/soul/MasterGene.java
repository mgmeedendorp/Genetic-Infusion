package seremis.geninfusion.soul;

import seremis.geninfusion.api.soul.IMasterGene;

import java.util.ArrayList;
import java.util.List;

public abstract class MasterGene extends Gene implements IMasterGene {

    public List<String> controlledGenes = new ArrayList<String>();

    @Override
    public void addControlledGene(String name) {
        controlledGenes.add(name);
    }

    @Override
    public List<String> getControlledGenes() {
        return controlledGenes;
    }
}
