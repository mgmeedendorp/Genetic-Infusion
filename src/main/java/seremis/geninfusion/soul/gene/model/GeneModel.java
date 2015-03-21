package seremis.geninfusion.soul.gene.model;

import javafx.geometry.Rectangle2D;
import net.minecraft.util.ResourceLocation;
import scala.Tuple2;
import scala.collection.mutable.ListBuffer;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.api.soul.IAllele;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.helper.GITextureHelper;
import seremis.geninfusion.lib.Localizations;
import seremis.geninfusion.soul.Gene;
import seremis.geninfusion.soul.allele.AlleleModelPartArray;
import seremis.geninfusion.soul.allele.AlleleString;
import seremis.geninfusion.soul.entity.animation.AnimationCache;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static scala.collection.JavaConversions.*;

public class GeneModel extends Gene {

    @Override
    public Class<? extends IAllele> possibleAlleles() {
        return AlleleModelPartArray.class;
    }

    @Override
    public IChromosome mutate(IChromosome chromosome) {
        AlleleModelPartArray allele1 = (AlleleModelPartArray) chromosome.getPrimary();
        AlleleModelPartArray allele2 = (AlleleModelPartArray) chromosome.getSecondary();

        if(rand.nextBoolean()) {
            ModelPart[] parts = allele1.value;
            for(ModelPart part : parts) {
                if(rand.nextInt(100) < 20) {
                    part.mutate();
                }
            }
        } else {
            ModelPart[] parts = allele2.value;
            for(ModelPart part : parts) {
                if(rand.nextInt(100) < 20) {
                    part.mutate();
                }
            }
        }

        return chromosome;
    }

    @Override
    public IChromosome advancedInherit(IChromosome[] parent1, IChromosome[] parent2, IChromosome[] offspring) {
        int geneId = SoulHelper.geneRegistry.getGeneId(this);
        int geneIdTexture = SoulHelper.geneRegistry.getGeneId(Genes.GENE_TEXTURE);

        IChromosome textureChromosome1 = parent1[geneIdTexture];
        IChromosome textureChromosome2 = parent2[geneIdTexture];

        BufferedImage texture1 = GITextureHelper.getBufferedImage(toResource(((AlleleString) textureChromosome1.getPrimary()).value));
        BufferedImage texture2 = GITextureHelper.getBufferedImage(toResource(((AlleleString) textureChromosome2.getPrimary()).value));
        BufferedImage texture3 = GITextureHelper.getBufferedImage(toResource(((AlleleString) textureChromosome1.getSecondary()).value));
        BufferedImage texture4 = GITextureHelper.getBufferedImage(toResource(((AlleleString) textureChromosome2.getSecondary()).value));

        IChromosome chromosome1 = parent1[geneId];
        IChromosome chromosome2 = parent2[geneId];

        AlleleModelPartArray allele1 = (AlleleModelPartArray) chromosome1.getPrimary();
        AlleleModelPartArray allele2 = (AlleleModelPartArray) chromosome2.getPrimary();
        AlleleModelPartArray allele3 = (AlleleModelPartArray) chromosome1.getSecondary();
        AlleleModelPartArray allele4 = (AlleleModelPartArray) chromosome2.getSecondary();

        ModelPart[] head1 = AnimationCache.getModelHead(allele1.value);
        ModelPart[] head2 = AnimationCache.getModelHead(allele2.value);
        ModelPart[] head3 = AnimationCache.getModelHead(allele3.value);
        ModelPart[] head4 = AnimationCache.getModelHead(allele4.value);
        ModelPart[] arms1 = AnimationCache.getModelArms(allele1.value);
        ModelPart[] arms2 = AnimationCache.getModelArms(allele2.value);
        ModelPart[] arms3 = AnimationCache.getModelArms(allele3.value);
        ModelPart[] arms4 = AnimationCache.getModelArms(allele4.value);
        ModelPart[] legs1 = AnimationCache.getModelLegs(allele1.value);
        ModelPart[] legs2 = AnimationCache.getModelLegs(allele2.value);
        ModelPart[] legs3 = AnimationCache.getModelLegs(allele3.value);
        ModelPart[] legs4 = AnimationCache.getModelLegs(allele4.value);
        ModelPart[] wings1 = AnimationCache.getModelWings(allele1.value);
        ModelPart[] wings2 = AnimationCache.getModelWings(allele2.value);
        ModelPart[] wings3 = AnimationCache.getModelWings(allele3.value);
        ModelPart[] wings4 = AnimationCache.getModelWings(allele4.value);
        ModelPart[] body1 = new ModelPart[] {AnimationCache.getModelBody(allele1.value)};
        ModelPart[] body2 = new ModelPart[] {AnimationCache.getModelBody(allele2.value)};
        ModelPart[] body3 = new ModelPart[] {AnimationCache.getModelBody(allele3.value)};
        ModelPart[] body4 = new ModelPart[] {AnimationCache.getModelBody(allele4.value)};

        ArrayList<Tuple2<ModelPart[], BufferedImage>> inherited1 = new ArrayList<Tuple2<ModelPart[], BufferedImage>>();
        ArrayList<Tuple2<ModelPart[], BufferedImage>> uninherited1 = new ArrayList<Tuple2<ModelPart[], BufferedImage>>();

        ArrayList<Tuple2<ModelPart[], BufferedImage>> inherited2 = new ArrayList<Tuple2<ModelPart[], BufferedImage>>();
        ArrayList<Tuple2<ModelPart[], BufferedImage>> uninherited2 = new ArrayList<Tuple2<ModelPart[], BufferedImage>>();

        randomlyInherit(inherited1, uninherited1, head1, texture1, head3, texture3);
        randomlyInherit(inherited1, uninherited1, arms1, texture1, arms3, texture3);
        randomlyInherit(inherited1, uninherited1, legs1, texture1, legs3, texture3);
        randomlyInherit(inherited1, uninherited1, wings1, texture1, wings3, texture3);
        randomlyInherit(inherited1, uninherited1, body1, texture1, body3, texture3);

        randomlyInherit(inherited2, uninherited2, head2, texture2, head4, texture4);
        randomlyInherit(inherited2, uninherited2, arms2, texture2, arms4, texture4);
        randomlyInherit(inherited2, uninherited2, legs2, texture2, legs4, texture4);
        randomlyInherit(inherited2, uninherited2, wings2, texture2,  wings4, texture4);
        randomlyInherit(inherited2, uninherited2, body2, texture2, body4, texture4);

        Tuple2<BufferedImage, List<Rectangle2D>> parent1Tuple = createParentTexture(inherited1, uninherited1);
        Tuple2<BufferedImage, List<Rectangle2D>> parent2Tuple = createParentTexture(inherited2, uninherited2);

        BufferedImage parent1Texture = parent1Tuple._1();
        BufferedImage parent2Texture = parent2Tuple._1();

        int textureId = SoulHelper.getNextAvailableTextureID();

        String parent1TextureLocationString = Localizations.LOC_ENTITY_CUSTOM_TEXTURES() + textureId + "_texture1.png";
        String parent2TextureLocationString = Localizations.LOC_ENTITY_CUSTOM_TEXTURES() + textureId + "_texture2.png";

        ResourceLocation parent1TextureLocation = toResource(parent1TextureLocationString);
        ResourceLocation parent2TextureLocation = toResource(parent2TextureLocationString);

        GITextureHelper.writeBufferedImage(parent1Texture, parent1TextureLocation);
        GITextureHelper.writeBufferedImage(parent2Texture, parent2TextureLocation);

        IAllele textureAllele1 = SoulHelper.instanceHelper.getIAlleleInstance(EnumAlleleType.STRING, true, parent1TextureLocationString);
        IAllele textureAllele2 = SoulHelper.instanceHelper.getIAlleleInstance(EnumAlleleType.STRING, false, parent2TextureLocationString);

        offspring[geneIdTexture] = SoulHelper.instanceHelper.getIChromosomeInstance(textureAllele1, textureAllele2);

        ArrayList<ModelPart> result1 = new ArrayList<ModelPart>();
        ArrayList<ModelPart> result2 = new ArrayList<ModelPart>();

        for(Tuple2<ModelPart[], BufferedImage> tuple : inherited1) {
            Collections.addAll(result1, tuple._1());
        }

        for(Tuple2<ModelPart[], BufferedImage> tuple : inherited2) {
            Collections.addAll(result2, tuple._1());
        }

        IAllele resultAllele1 = SoulHelper.instanceHelper.getIAlleleInstance(EnumAlleleType.MODEL_PART_ARRAY, true, result1.toArray(new ModelPart[result1.size()]));
        IAllele resultAllele2 = SoulHelper.instanceHelper.getIAlleleInstance(EnumAlleleType.MODEL_PART_ARRAY, false, result2.toArray(new ModelPart[result2.size()]));

        return SoulHelper.instanceHelper.getIChromosomeInstance(resultAllele1, resultAllele2);
    }

    private void randomlyInherit(List<Tuple2<ModelPart[], BufferedImage>> inherited, List<Tuple2<ModelPart[], BufferedImage>> uninherited, ModelPart[] parent1, BufferedImage texture1, ModelPart[] parent2, BufferedImage texture2) {
        if(rand.nextBoolean()) {
            inherited.add(new Tuple2<ModelPart[], BufferedImage>(parent1, texture1));
            uninherited.add(new Tuple2<ModelPart[], BufferedImage>(parent2, texture2));
        } else {
            inherited.add(new Tuple2<ModelPart[], BufferedImage>(parent2, texture2));
            uninherited.add(new Tuple2<ModelPart[], BufferedImage>(parent1, texture1));
        }
    }

    private ResourceLocation toResource(String location) {
        return new ResourceLocation(location);
    }

    private Tuple2<BufferedImage, List<Rectangle2D>> createParentTexture(List<Tuple2<ModelPart[], BufferedImage>> inherited, List<Tuple2<ModelPart[], BufferedImage>> uninherited) {
        List<BufferedImage> modelPartImages = new ArrayList<BufferedImage>();
        List<Rectangle2D> textureRects = new ArrayList<Rectangle2D>();

        for(int i = 0; i < inherited.size(); i++) {
            ModelPart[] parent1Inherited = inherited.get(i)._1();
            ModelPart[] parent1Uninherited = uninherited.get(i)._1();

            BufferedImage wholeTexture1 = inherited.get(i)._2();
            BufferedImage wholeTexture2 = uninherited.get(i)._2();

            for(int j = 0; j < Math.min(parent1Inherited.length, parent1Uninherited.length); j++) {
                BufferedImage tex1 = GITextureHelper.getModelPartTexture(parent1Inherited[j], wholeTexture1);
                BufferedImage tex2 = GITextureHelper.getModelPartTexture(parent1Uninherited[j], wholeTexture2);

                BufferedImage image = GITextureHelper.mergeImages(tex1, tex2);

                textureRects.add(new Rectangle2D(0, 0, image.getWidth(), image.getHeight()));
                modelPartImages.add(image);
            }
        }
        ListBuffer<Rectangle2D> rectBuffer = new ListBuffer<Rectangle2D>();
        ListBuffer<BufferedImage> imageBuffer = new ListBuffer<BufferedImage>();

        for(Rectangle2D rect : textureRects) {
            rectBuffer.$plus$eq(rect);
        }

        for(BufferedImage image : modelPartImages) {
            imageBuffer.$plus$eq(image);
        }

        Tuple2<BufferedImage, ListBuffer<Rectangle2D>> result = GITextureHelper.stitchImages(rectBuffer, imageBuffer);

        return new Tuple2<BufferedImage, List<Rectangle2D>>(result._1(), bufferAsJavaList(result._2()));
    }
}
