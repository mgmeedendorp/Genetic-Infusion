package seremis.geninfusion.soul.traits;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import javafx.geometry.Rectangle2D;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import scala.Tuple2;
import scala.Tuple3;
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
import seremis.geninfusion.soul.gene.model.GeneModel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
