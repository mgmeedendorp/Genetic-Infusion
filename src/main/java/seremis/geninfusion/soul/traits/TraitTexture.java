package seremis.geninfusion.soul.traits;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.helper.GIReflectionHelper;
import seremis.geninfusion.helper.GIRenderHelper;
import seremis.geninfusion.helper.GITextureHelper;
import seremis.geninfusion.lib.Localizations;
import seremis.geninfusion.soul.allele.AlleleModelPartArray;
import seremis.geninfusion.soul.allele.AlleleString;
import seremis.geninfusion.soul.entity.animation.AnimationCache;

import java.awt.image.BufferedImage;

public class TraitTexture extends Trait {

    @Override
    public void firstTick(IEntitySoulCustom entity) {
        if(entity.getWorld().isRemote) {
            ModelSkeleton modelSkeleton = new ModelSkeleton();

            ModelPart part = ModelPart.modelRendererToModelPart(modelSkeleton.bipedBody);

            BufferedImage image = GITextureHelper.getModelPartTexture(part, GITextureHelper.getBufferedImage(toResource(SoulHelper.geneRegistry.getValueString(entity, Genes.GENE_TEXTURE))));

            GITextureHelper.writeBufferedImage(image, toResource(Localizations.LOC_ENTITY_CUSTOM_TEXTURES() + "test.png"));

            entity.setObject("head", part);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void render(IEntitySoulCustom entity, float timeModifier, float limbSwing, float specialRotation, float rotationYawHead, float rotationPitch, float scale) {
        ModelPart part = (ModelPart) entity.getObject("head");

        GIRenderHelper.bindTexture(Localizations.LOC_ENTITY_CUSTOM_TEXTURES() + "test.png");

        //part.render(scale);

    }

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
