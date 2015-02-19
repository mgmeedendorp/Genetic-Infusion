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

        AnimationPart[] animationWalkHead = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_LINEAR_HEAD);
        AnimationPart[] animationWalkBody = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_LINEAR_BODY);
        AnimationPart[] animationWalkArmLeft = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WAVE_ARM_LEFT);
        AnimationPart[] animationWalkArmRight = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WAVE_ARM_RIGHT);
        AnimationPart[] animationWalkLegLeft = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WAVE_LEG_LEFT);
        AnimationPart[] animationWalkLegRight = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WAVE_LEG_RIGHT);
        AnimationPart[] animationWalkEars = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_LINEAR_EARS);
        AnimationPart[] animationWalkCloak = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_LINEAR_CLOAK);

        entity.setFloat("legLeftWavePeriod",  timeModifier * 0.6662F);
        entity.setFloat("legLeftWaveAmplitude", walkSpeed * 1.4F);
        entity.setFloat("legLeftWaveOffsetHor", (float) Math.PI);

        entity.setFloat("legRightWavePeriod",  timeModifier * 0.6662F);
        entity.setFloat("legRightWaveAmplitude", walkSpeed * 1.4F);
        entity.setFloat("legRightWaveOffsetHor", 0.0F);

        entity.setFloat("armLeftWavePeriod", timeModifier * 0.6662F);
        entity.setFloat("armLeftWaveAmplitude", walkSpeed);
        entity.setFloat("armLeftWaveOffsetHor", 0.0F);

        entity.setFloat("armRightWavePeriod", timeModifier * 0.6662F);
        entity.setFloat("armRightWaveAmplitude", walkSpeed);
        entity.setFloat("armRightWaveOffsetHor", (float) Math.PI);

        entity.setFloat("headLinearRotateAngleX", rotationPitch / (180F / (float) Math.PI));
        entity.setFloat("headLinearRotateAngleY", rotationYawHead / (180F / (float) Math.PI));

        float bodyAngleY = 0.0F;
        float armRightPointX = 0.0F;
        float armRightPointZ = 0.0F;
        float armLeftPointX = 0.0F;
        float armLeftPointZ = 0.0F;

        float armRightAngleX = 0.0F;
        float armRightAngleY = 0.0F;
        float armRightAngleZ = 0.0F;
        float armLeftAngleX = 0.0F;
        float armLeftAngleY = 0.0F;
        float armLeftAngleZ = 0.0F;

        float swingProgress = ((EntityLiving)entity).getSwingProgress(entity.getFloat("partialTickTime"));

        if(swingProgress > -9990.0F) {
            float f6 = swingProgress;
            bodyAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F;
            armRightPointZ = MathHelper.sin(bodyAngleY) * 5.0F;
            armRightPointX = -MathHelper.cos(bodyAngleY) * 5.0F;
            armLeftPointZ = -MathHelper.sin(bodyAngleY) * 5.0F;
            armLeftPointX = MathHelper.cos(bodyAngleY) * 5.0F;

            armRightAngleY = bodyAngleY;
            armLeftAngleY = bodyAngleY;
            armLeftAngleX = bodyAngleY;

            f6 = 1.0F - swingProgress;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            float f7 = MathHelper.sin(f6 * (float)Math.PI);
            float f8 = MathHelper.sin(swingProgress * (float)Math.PI) * -(entity.getFloat("headLinearRotateAngleX") - 0.7F) * 0.75F;

            armRightAngleX = -(float) ((double)f7 * 1.2D * (double)f8);
            armRightAngleY = bodyAngleY * 2.0F;
            armRightAngleZ = MathHelper.sin(swingProgress * (float)Math.PI) * -0.4F);
        }

        //TODO continue this
        if (((EntityLiving)entity).isSneaking()) {
            entity.setFloat("bodyRotateAngleX", 0.5F);
            entity.setFloat("armRightWalkRotateAngleX", entity.getFloat("armRightWalkRotateAngleX") + 0.5F);
            entity.setFloat("armLeftWalkRotateAngleX", entity.getFloat("armRightWalkRotateAngleX") + 0.5F);
            entity.setFloat("legRightWalkRotationPointZ", 4.0F);
            entity.setFloat("legLeftWalkRotationPointZ", 4.0F);
            entity.setFloat("legRightWalkRotationPointY", 9.0F);
            entity.setFloat("legLeftWalkRotationPointY", 9.0F);
            entity.setFloat("headRotationPointY", 1.0F);
        } else {
            entity.setFloat("bodyRotateAngleX", 0.0F);
            entity.setFloat("legRightWalkRotationPointZ", 0.1F);
            entity.setFloat("legLeftWalkRotationPointZ", 0.1F);
            entity.setFloat("legRightWalkRotationPointY", 12.0F);
            entity.setFloat("legLeftWalkRotationPointY", 12.0F);
            entity.setFloat("headRotationPointY", 0.0F);
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
