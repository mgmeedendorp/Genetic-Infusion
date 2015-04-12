package seremis.geninfusion.api.soul;

import com.sun.istack.internal.NotNull;
import net.minecraft.client.model.ModelBase;

import java.util.Random;

/**
 * The access point for several registries needed for souls.
 *
 * @author Seremis
 */
public class SoulHelper {

    public static IGeneRegistry geneRegistry;

    public static IStandardSoulRegistry standardSoulRegistry;

    public static ITraitRegistry traitRegistry;

    public static IInstanceHelper instanceHelper;

    public static IAnimationRegistry animationRegistry;

    /**
     * The model used for all EntitySoulCustoms, this only contains a bridge method that redirects all render() calls to
     * Traits, but can be useful for declaring new ModelRenderers or ModelParts and can be used to set the textureOffset
     * on individual cubes within a ModelRenderer/ModelPart.
     */
    public static ModelBase entityModel;

    /**
     * Creates offspring from two parent souls
     *
     * @param parent1 A parent soul
     * @param parent2 A parent soul
     * @return The offspring
     */
    public static ISoul produceOffspring(@NotNull ISoul parent1, @NotNull ISoul parent2) {
        IChromosome[] chromosomes1 = parent1.getChromosomes();
        IChromosome[] chromosomes2 = parent2.getChromosomes();

        IChromosome[] offspring = new IChromosome[chromosomes1.length];

        for(int i = 0; i < chromosomes1.length; i++) {
            IGene gene = SoulHelper.geneRegistry.getGene(i);

            if(geneRegistry.useNormalInheritance(gene)) {
                offspring[i] = gene.inherit(chromosomes1[i], chromosomes2[i]);

                if(rand.nextInt(100) < 5) {
                    offspring[i] = gene.mutate(offspring[i]);
                }
            }
        }

        for(int i = 0; i < geneRegistry.getCustomInheritanceGenes().size(); i++) {
            IGene gene = geneRegistry.getCustomInheritanceGenes().get(i);

            offspring[geneRegistry.getGeneId(gene)] = gene.advancedInherit(chromosomes1, chromosomes2, offspring);

            if(rand.nextInt(100) < 5) {
                offspring[geneRegistry.getGeneId(gene)] = gene.mutate(offspring[geneRegistry.getGeneId(gene)]);
            }
        }

        return instanceHelper.getISoulInstance(offspring);
    }

    private static Random rand = new Random();

    public static int textureID = 0;

    /**
     * Get the next available texture ID, this is used as the name of the texture. This number persists over saves.
     *
     * @return The next textureID
     */
    public static int getNextAvailableTextureID() {
        return textureID++;
    }
}
