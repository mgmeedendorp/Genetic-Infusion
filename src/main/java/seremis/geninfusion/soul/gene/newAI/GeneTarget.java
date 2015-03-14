package seremis.geninfusion.soul.gene.newAI;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.EntityWither;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleClass;
import seremis.geninfusion.soul.allele.AlleleClassArray;

import java.util.ArrayList;

public abstract class GeneTarget extends Gene {

    @Override
    public IChromosome mutate(IChromosome chromosome) {
        if(possibleAlleles().equals(AlleleClass.class)) {
            AlleleClass allele1 = (AlleleClass) chromosome.getPrimary();
            AlleleClass allele2 = (AlleleClass) chromosome.getSecondary();

            if(rand.nextBoolean()) {
                allele1.value = possibleTargets()[rand.nextInt(possibleTargets().length)];
            } else {
                allele2.value = possibleTargets()[rand.nextInt(possibleTargets().length)];
            }
        } else if(possibleAlleles().equals(AlleleClassArray.class)) {
            AlleleClassArray allele1 = (AlleleClassArray) chromosome.getPrimary();
            AlleleClassArray allele2 = (AlleleClassArray) chromosome.getSecondary();

            if(rand.nextBoolean()) {
                allele1.value[rand.nextInt(allele1.value.length)] = possibleTargets()[rand.nextInt(possibleTargets().length)];
            } else {
                allele2.value[rand.nextInt(allele2.value.length)] = possibleTargets()[rand.nextInt(possibleTargets().length)];
            }
        }
        return chromosome;
    }

    public Class[] possibleTargets() {
        return sensibleTargets();
    }

    /**
     * Returns a String[] with the names of all sensible target entities. Sensible being every EntityLiving except
     * bosses.
     *
     * @return A String[] with the names of all sensible target entities.
     */
    public Class[] sensibleTargets() {
        ArrayList<Class> list = new ArrayList<Class>();

        for(Object obj : EntityList.classToStringMapping.keySet()) {
            Class<Entity> entity = (Class<Entity>) obj;

            if(EntityLiving.class.isAssignableFrom(entity) && !(EntityDragon.class.isAssignableFrom(entity) || EntityDragonPart.class.isAssignableFrom(entity) || EntityWither.class.isAssignableFrom(entity))) {
                list.add(entity.getClass());
            }
        }

        return list.toArray(new Class[list.size()]);
    }
}
