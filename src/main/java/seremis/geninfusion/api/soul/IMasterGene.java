package seremis.geninfusion.api.soul;

import java.util.List;

public interface IMasterGene extends IGene {

    void addControlledGene(IGene gene);

    List<IGene> getControlledGenes();
}
