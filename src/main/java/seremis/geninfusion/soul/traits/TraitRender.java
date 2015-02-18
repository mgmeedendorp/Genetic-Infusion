package seremis.geninfusion.soul.traits;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.api.soul.util.animation.AnimationPart;

public class TraitRender extends Trait {

    @Override
    @SideOnly(Side.CLIENT)
    public void render(IEntitySoulCustom entity, float timeModifier, float walkSpeed, float specialRotation, float rotationYawHead, float rotationPitch, float scale) {
        ModelPart[] head = SoulHelper.geneRegistry.getValueModelPartArray(entity, Genes.GENE_MODEL_HEAD);
        ModelPart[] body = SoulHelper.geneRegistry.getValueModelPartArray(entity, Genes.GENE_MODEL_BODY);
        ModelPart[] armLeft = SoulHelper.geneRegistry.getValueModelPartArray(entity, Genes.GENE_MODEL_ARM_LEFT);
        ModelPart[] armRight = SoulHelper.geneRegistry.getValueModelPartArray(entity, Genes.GENE_MODEL_ARM_RIGHT);
        ModelPart[] legLeft = SoulHelper.geneRegistry.getValueModelPartArray(entity, Genes.GENE_MODEL_LEG_LEFT);
        ModelPart[] legRight = SoulHelper.geneRegistry.getValueModelPartArray(entity, Genes.GENE_MODEL_LEG_RIGHT);
        ModelPart[] ears = SoulHelper.geneRegistry.getValueModelPartArray(entity, Genes.GENE_MODEL_EARS);
        ModelPart[] cloak = SoulHelper.geneRegistry.getValueModelPartArray(entity, Genes.GENE_MODEL_CLOAK);

        AnimationPart[] animationWalkHead = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WALK_HEAD);
        AnimationPart[] animationWalkBody = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WALK_BODY);
        AnimationPart[] animationWalkArmLeft = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WALK_ARM_LEFT);
        AnimationPart[] animationWalkArmRight = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WALK_ARM_RIGHT);
        AnimationPart[] animationWalkLegLeft = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WALK_LEG_LEFT);
        AnimationPart[] animationWalkLegRight = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WALK_LEG_RIGHT);
        AnimationPart[] animationWalkEars = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WALK_EARS);
        AnimationPart[] animationWalkCloak = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WALK_CLOAK);

        entity.setFloat("legLeftWalkPeriod",  timeModifier * 0.6662F);
        entity.setFloat("legLeftWalkAmplitude", walkSpeed * 1.4F);
        entity.setFloat("legLeftWalkOffsetHor", (float) Math.PI);

        entity.setFloat("legRightWalkPeriod",  timeModifier * 0.6662F);
        entity.setFloat("legRightWalkAmplitude", walkSpeed * 1.4F);
        entity.setFloat("legRightWalkOffsetHor", 0.0F);

        entity.setFloat("armLeftWalkPeriod", timeModifier * 0.6662F);
        entity.setFloat("armLeftWalkAmplitude", walkSpeed);
        entity.setFloat("armLeftWalkOffsetHor", 0.0F);

        entity.setFloat("armRightWalkPeriod", timeModifier * 0.6662F);
        entity.setFloat("armRightWalkAmplitude", walkSpeed);
        entity.setFloat("armRightWalkOffsetHor", (float) Math.PI);

        entity.setFloat("headWalkRotateAngleX", rotationPitch / (180F / (float) Math.PI));
        entity.setFloat("headWalkRotateAngleY", rotationYawHead / (180F / (float) Math.PI));

        float swingProgress = ((EntityLiving)entity).getSwingProgress(entity.getFloat("partialTickTime"));

        if(swingProgress > -9990.0F) {
            float f6 = swingProgress;
            entity.setFloat("bodyWalkRotateAngleY", MathHelper.sin(MathHelper.sqrt_float(f6) * (float) Math.PI * 2.0F) * 0.2F);
            entity.setFloat("armRightWalkRotationPointZ", MathHelper.sin(entity.getFloat("bodyWalkRotateAngleY")) * 5.0F);
            entity.setFloat("armRightWalkRotationPointX", -MathHelper.cos(entity.getFloat("bodyWalkRotateAngleY")) * 5.0F);
            entity.setFloat("armLeftWalkRotationPointZ", -MathHelper.sin(entity.getFloat("bodyWalkRotateAngleY")) * 5.0F);
            entity.setFloat("armLeftWalkRotationPointX", MathHelper.sin(entity.getFloat("bodyWalkRotateAngleY")) * 5.0F);

            entity.setFloat("armRightWalkRotateAngleX", entity.getFloat("bodyWalkRotateAngleY"));
            entity.setFloat("armLeftWalkRotateAngleX", entity.getFloat("bodyWalkRotateAngleY"));

            entity.setFloat("armLeftWalkOffsetVert", entity.getFloat("bodyWalkRotateAngleY"));

            f6 = 1.0F - swingProgress;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            float f7 = MathHelper.sin(f6 * (float)Math.PI);
            float f8 = MathHelper.sin(swingProgress * (float)Math.PI) * -(entity.getFloat("headWalkRotateAngleX") - 0.7F) * 0.75F;

            entity.setFloat("armRightWalkRotateAngleX", -(float) ((double)f7 * 1.2D * (double)f8));
            entity.setFloat("armRightWalkRotateAngleY", entity.getFloat("bodyWalkRotateAngleY") * 2.0F);
            entity.setFloat("armRightWalkRotateAngleZ", MathHelper.sin(swingProgress * (float)Math.PI) * -0.4F);
        }

        entity.setFloat("armRightWalkRotateAngleZ", entity.getFloat("armRightWalkRotateAngleZ") + MathHelper.cos(specialRotation * 0.09F) * 0.05F + 0.05F);
        entity.setFloat("armLeftWalkRotateAngleZ", entity.getFloat("armLeftWalkRotateAngleZ") - MathHelper.cos(specialRotation * 0.09F) * 0.05F + 0.05F);
        entity.setFloat("armRightWalkRotateAngleX", entity.getFloat("armRightWalkRotateAngleX") + MathHelper.sin(specialRotation * 0.067F) * 0.05F);
        entity.setFloat("armLeftWalkRotateAngleX", entity.getFloat("armLeftWalkRotateAngleX") - MathHelper.cos(specialRotation * 0.067F) * 0.05F);


        for(int i = 0; i < head.length; i++) {
            if(i < animationWalkHead.length) {
                head[i].resetRotations();
                animationWalkHead[i].setAnimationModel(head[i]).animate(entity);
            }
            head[i].render(0.0625F);
        }

        for(int i = 0; i < body.length; i++) {
            if(i < animationWalkBody.length) {
                body[i].resetRotations();
                animationWalkBody[i].setAnimationModel(body[i]).animate(entity);
            }
            body[i].render(0.0625F);
        }

        for(int i = 0; i < armLeft.length; i++) {
            if(i < animationWalkArmLeft.length) {
                armLeft[i].resetRotations();
                animationWalkArmLeft[i].setAnimationModel(armLeft[i]).animate(entity);
            }
            armLeft[i].render(0.0625F);
        }

        for(int i = 0; i < armRight.length; i++) {
            if(i < animationWalkArmRight.length) {
                armRight[i].resetRotations();
                animationWalkArmRight[i].setAnimationModel(armRight[i]).animate(entity);
            }
            armRight[i].render(0.0625F);
        }

        for(int i = 0; i < legLeft.length; i++) {
            if(i < animationWalkLegLeft.length) {
                legLeft[i].resetRotations();
                animationWalkLegLeft[i].setAnimationModel(legLeft[i]).animate(entity);
            }
            legLeft[i].render(0.0625F);
        }

        for(int i = 0; i < legRight.length; i++) {
            if(i < animationWalkLegRight.length) {
                legRight[i].resetRotations();
                animationWalkLegRight[i].setAnimationModel(legRight[i]).animate(entity);
            }
            legRight[i].render(0.0625F);
        }

//        for(int i = 0; i < cloak.length; i++) {
//            if(i < animationWalkCloak.length) {
//                animationWalkCloak[i].setAnimationModel(cloak[i]).animate(entity);
//            }
//            cloak[i].render(0.0625F);
//        }

        for(int i = 0; i < ears.length; i++) {
            if(i < animationWalkEars.length) {
                ears[i].resetRotations();
                animationWalkEars[i].setAnimationModel(ears[i]).animate(entity);
            }
            ears[i].render(0.0625F);
        }
    }
}
