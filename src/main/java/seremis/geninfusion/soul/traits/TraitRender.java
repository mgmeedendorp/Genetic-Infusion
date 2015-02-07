package seremis.geninfusion.soul.traits;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.soul.allele.AlleleModelPartArray;

public class TraitRender extends Trait {

    @Override
    public void render(IEntitySoulCustom entity) {
        ModelPart[] head = ((AlleleModelPartArray) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MODEL_HEAD)).value;
        ModelPart[] body = ((AlleleModelPartArray) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_MODEL_BODY)).value;

//        System.out.println(head.length);
//        System.out.println(body.length);

//        for(ModelPart model : head) {
//            model.render(0.0625F);
//            NBTTagCompound compound = new NBTTagCompound();
//            model.writeToNBT(compound);
//            System.out.println(compound);
//        }

        head[0].render(0.0625F);

        head[0].rotateAngleY = ((EntityLiving)entity).rotationYawHead / (180F / (float)Math.PI);
        head[0].rotateAngleX = ((EntityLiving)entity).rotationPitch / (180F / (float)Math.PI);

        body[0].render(0.0625F);

//        for(ModelPart model : body) {
//            model.render(0.0625F);
//        }
    }
}
