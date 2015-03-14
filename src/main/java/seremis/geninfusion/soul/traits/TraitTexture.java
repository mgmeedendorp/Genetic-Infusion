package seremis.geninfusion.soul.traits;

import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.helper.GITextureHelper;
import seremis.geninfusion.lib.Localizations;
import seremis.geninfusion.soul.allele.AlleleModelPartArray;
import seremis.geninfusion.soul.allele.AlleleString;

public class TraitTexture extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        if(entity.getWorld().isRemote && !GITextureHelper.getFile(toResource(getEntityTexture(entity))).exists()) {
            ResourceLocation parent1 = toResource(((AlleleString) SoulHelper.geneRegistry.getChromosomeFor(entity, Genes.GENE_TEXTURE).getPrimary()).value);
            ResourceLocation parent2 = toResource(((AlleleString) SoulHelper.geneRegistry.getChromosomeFor(entity, Genes.GENE_TEXTURE).getSecondary()).value);

            ModelPart[] model1 = ((AlleleModelPartArray)SoulHelper.geneRegistry.getChromosomeFor(entity, Genes.GENE_MODEL).getActive()).value;
            ModelPart[] model2 = ((AlleleModelPartArray)SoulHelper.geneRegistry.getChromosomeFor(entity, Genes.GENE_MODEL).getRecessive()).value;

            GITextureHelper.mergeModelTextures(model1, GITextureHelper.getBufferedImage(parent1), model2, GITextureHelper.getBufferedImage(parent2));
        }
    }

    @Override
    public String getEntityTexture(IEntitySoulCustom entity) {
        return Localizations.LOC_ENTITY_CUSTOM_TEXTURES() + entity.getEntityId() + ".png";
    }

    public ResourceLocation toResource(String string) {
        return new ResourceLocation(string);
    }

    @Override
    public void onDeath(IEntitySoulCustom entity, DamageSource source) {
        GITextureHelper.deleteTexture(toResource(Localizations.LOC_ENTITY_CUSTOM_TEXTURES() + entity.getEntityId() + ".png"));
    }
}
