package seremis.geninfusion.soul.gene.newAI;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.EntityWither;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleString;

import java.util.ArrayList;

public abstract class GeneTarget extends Gene {

    @Override
    public IChromosome mutate(IChromosome chromosome) {
        AlleleString allele1 = (AlleleString) chromosome.getPrimary();
        AlleleString allele2 = (AlleleString) chromosome.getSecondary();

        if(rand.nextBoolean()) {
            allele1.value = possibleTargets()[rand.nextInt(possibleTargets().length)];
        } else {
            allele2.value = possibleTargets()[rand.nextInt(possibleTargets().length)];
        }
        return chromosome;
    }

    public String[] possibleTargets() {
        return sensibleTargets();
    }

    /**
     * Returns a String[] with the names of all sensible target entities. Sensible being every EntityLiving except
     * bosses.
     *
     * @return A String[] with the names of all sensible target entities.
     */
    public String[] sensibleTargets() {
        ArrayList<String> list = new ArrayList<String>();

        for(Object obj : EntityList.classToStringMapping.keySet()) {
            Class<Entity> entity = (Class<Entity>) obj;

            if(EntityLiving.class.isAssignableFrom(entity) && !(EntityDragon.class.isAssignableFrom(entity) || EntityDragonPart.class.isAssignableFrom(entity) || EntityWither.class.isAssignableFrom(entity))) {
                list.add(entity.getName());
            }
        }

        return list.toArray(new String[list.size()]);
    }
}
