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
    public void firstTick(IEntitySoulCustom entity) {
        if(entity.getWorld().isRemote) {
//            ModelSkeleton modelSkeleton = new ModelSkeleton();
//
//            ModelPart head = ModelPart.modelRendererToModelPart(modelSkeleton.bipedHead);
//            ModelPart headBox = ModelPart.modelRendererToModelPart(modelSkeleton.bipedHeadwear);
//            ModelPart body = ModelPart.modelRendererToModelPart(modelSkeleton.bipedBody);
//            ModelPart armLeft = ModelPart.modelRendererToModelPart(modelSkeleton.bipedLeftArm);
//            ModelPart armRight = ModelPart.modelRendererToModelPart(modelSkeleton.bipedRightArm);
//
//            BufferedImage image = GITextureHelper.getBufferedImage(toResource(SoulHelper.geneRegistry.getValueString(entity, Genes.GENE_TEXTURE)));
//
//            BufferedImage imageHead = GITextureHelper.getModelPartTexture(head, image);
//            BufferedImage imageHeadWear = GITextureHelper.getModelPartTexture(headBox, image);
//            BufferedImage imageBody = GITextureHelper.getModelPartTexture(body, image);
//            BufferedImage imageArmLeft = GITextureHelper.getModelPartTexture(armLeft, image);
//            BufferedImage imageArmRight = GITextureHelper.getModelPartTexture(armRight, image);
//
//            List<Tuple2<ModelPart[], BufferedImage>> list = new ArrayList<Tuple2<ModelPart[], BufferedImage>>();
//
//            list.add(new Tuple2(new ModelPart[] {head}, imageHead));
//            list.add(new Tuple2(new ModelPart[] {headBox}, imageHeadWear));
//            list.add(new Tuple2(new ModelPart[] {body}, imageBody));
//            list.add(new Tuple2(new ModelPart[] {armLeft}, imageArmLeft));
//            list.add(new Tuple2(new ModelPart[] {armRight}, imageArmRight));
//
//            Tuple3<BufferedImage, List<Rectangle2D>, List<ModelPart>> parent = GeneModel.createParentTexture(list);
//
//
//            GITextureHelper.writeBufferedImage(parent._1(), toResource(Localizations.LOC_ENTITY_CUSTOM_TEXTURES() + "test.png"));
//
//            entity.setObject("parent", parent);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void render(IEntitySoulCustom entity, float timeModifier, float limbSwing, float specialRotation, float rotationYawHead, float rotationPitch, float scale) {
//        Tuple3<BufferedImage, List<Rectangle2D>, List<ModelPart>> parent = (Tuple3<BufferedImage, List<Rectangle2D>, List<ModelPart>>) entity.getObject("parent");
//
//        GIRenderHelper.bindTexture(Localizations.LOC_ENTITY_CUSTOM_TEXTURES() + "test.png");
//
//        for(ModelPart part : parent._3()) {
//            part.render(scale);
//        }
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
