package seremis.geninfusion.api.soul;

import java.util.List;

public interface IMasterGene extends IGene {

    void addControlledGene(String name);

    List<String> getControlledGenes();
}
