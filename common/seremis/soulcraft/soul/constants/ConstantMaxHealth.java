package seremis.soulcraft.soul.constants;

import seremis.soulcraft.soul.IChromosome;
import seremis.soulcraft.soul.IEntitySoulCustom;
import seremis.soulcraft.soul.allele.AlleleFloat;

public class ConstantMaxHealth extends ChromosomeConstant {

    @Override
    public void init(IChromosome chromosome, IEntitySoulCustom entity) {
        entity.setMaxHealth(((AlleleFloat)chromosome.getActive()).value);
    }
}
