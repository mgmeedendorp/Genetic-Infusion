package seremis.geninfusion.soul.gene;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IGene;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.AlleleString;

/**
 * @author Seremis
 */
public class GeneSplashSound implements IGene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleString.class;
    }

    @Override
    public IChromosome getStandardForEntity(EntityLiving entity) {
        if(entity instanceof EntityMob) {
            return new Chromosome(new AlleleString(true, "game.hostile.swim.splash"), new AlleleString(false, "game.hostile.swim.splash"));
        }
        return new Chromosome(new AlleleString(true, "game.neutral.swim.splash"), new AlleleString(false, "game.neutral.swim.splash"));
    }
}
