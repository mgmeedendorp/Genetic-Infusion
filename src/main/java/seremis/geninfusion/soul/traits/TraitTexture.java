package seremis.geninfusion.soul.traits;

import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.helper.GITextureHelper;
import seremis.geninfusion.lib.Localizations;
import seremis.geninfusion.soul.allele.AlleleString;

public class TraitTexture extends Trait {

    @Override
    public String getEntityTexture(IEntitySoulCustom entity) {
        try {
            return SoulHelper.geneRegistry.getValueString(entity, Genes.GENE_TEXTURE);
        } catch(NullPointerException e) {
            return "textures/entity/zombie/zombie.png";
        }
    }

    @Override
    public void onDeath(IEntitySoulCustom entity, DamageSource source) {
        //TODO if it doesn't spawn a Soul, remove the texture
        GITextureHelper.deleteTexture(toResource(getEntityTexture(entity)));
        GITextureHelper.deleteTexture(toResource(((AlleleString) SoulHelper.geneRegistry.getChromosomeFor(entity, Genes.GENE_TEXTURE).getRecessive()).value));
    }

    public ResourceLocation toResource(String string) {
        return new ResourceLocation(string);
    }
}
