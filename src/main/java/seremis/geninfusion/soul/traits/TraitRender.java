package seremis.geninfusion.soul.traits;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
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

        AnimationPart[] animationLinearHead = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_LINEAR_HEAD);
        AnimationPart[] animationLinearBody = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_LINEAR_BODY);
        AnimationPart[] animationLinearArmLeft = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_LINEAR_ARM_LEFT);
        AnimationPart[] animationLinearArmRight = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_LINEAR_ARM_RIGHT);
        AnimationPart[] animationLinearLegLeft = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_LINEAR_LEG_LEFT);
        AnimationPart[] animationLinearLegRight = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_LINEAR_LEG_RIGHT);
        AnimationPart[] animationLinearEars = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_LINEAR_EARS);
        AnimationPart[] animationLinearCloak = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_LINEAR_CLOAK);

        AnimationPart[] animationWaveArmLeft = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WAVE_ARM_LEFT);
        AnimationPart[] animationWaveArmRight = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WAVE_ARM_RIGHT);
        AnimationPart[] animationWaveLegLeft = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WAVE_LEG_LEFT);
        AnimationPart[] animationWaveLegRight = SoulHelper.geneRegistry.getValueAnimationPartArray(entity, Genes.GENE_ANIM_WAVE_LEG_RIGHT);

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

        entity.setFloat("armRightLinearRotationPointY", 2F);
        entity.setFloat("armLeftLinearRotationPointY", 2F);

        entity.setFloat("legRightLinearRotationPointX", -1.9F);
        entity.setFloat("legLeftLinearRotationPointX", 1.9F);

        float bodyAngleX;
        float bodyAngleY = 0.0F;
        float bodyAngleZ = 0.0F;
        float armRightPointX = entity.getFloat("armRightWaveRotationPointX");
        float armRightPointZ = entity.getFloat("armRightWaveRotationPointZ");
        float armLeftPointX = entity.getFloat("armLeftWaveRotationPointX");
        float armLeftPointZ = entity.getFloat("armLeftWaveRotationPointZ");

        float armRightAngleX = 0.0F;
        float armRightAngleY = 0.0F;
        float armRightAngleZ = 0.0F;
        float armLeftAngleX = 0.0F;
        float armLeftAngleY = 0.0F;
        float armLeftAngleZ = 0.0F;

        float legLeftAngleX = 0.0F;
        float legRightAngleX = 0.0F;
        float legLeftAngleY = 0.0F;
        float legRightAngleY = 0.0F;
        
        float legRightPointY;
        float legRightPointZ;
        float legLeftPointY;
        float legLeftPointZ;

        float headPointY;

        if (((EntityLiving)entity).isRiding()) {
            armRightAngleX += -((float)Math.PI / 5F);
            armLeftAngleX += -((float)Math.PI / 5F);
            legRightAngleX = -((float)Math.PI * 2F / 5F);
            legLeftAngleX = -((float)Math.PI * 2F / 5F);
            legRightAngleY = ((float)Math.PI / 10F);
            legLeftAngleY = -((float)Math.PI / 10F);
        }

        if (((EntityLiving)entity).getHeldItem() != null) {
            armRightAngleX = armRightAngleX * 0.5F - ((float)Math.PI / 10F);
        }

        float swingProgress = ((EntityLiving)entity).getSwingProgress(entity.getFloat("partialTickTime"));

        if(swingProgress > -9990.0F) {
            float f6 = swingProgress;
            bodyAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F;
            armRightPointZ = MathHelper.sin(bodyAngleY) * 5.0F;
            armRightPointX = -MathHelper.cos(bodyAngleY) * 5.0F;
            armLeftPointZ = -MathHelper.sin(bodyAngleY) * 5.0F;
            armLeftPointX = MathHelper.cos(bodyAngleY) * 5.0F;

            armRightAngleY += bodyAngleY;
            armLeftAngleY += bodyAngleY;
            armLeftAngleX += bodyAngleY;

            f6 = 1.0F - swingProgress;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            float f7 = MathHelper.sin(f6 * (float)Math.PI);
            float f8 = MathHelper.sin(swingProgress * (float)Math.PI) * -(entity.getFloat("headLinearRotateAngleX") - 0.7F) * 0.75F;

            armRightAngleX -= (float) ((double)f7 * 1.2D * (double)f8);
            armRightAngleY += bodyAngleY * 2.0F;
            armRightAngleZ = MathHelper.sin(swingProgress * (float)Math.PI) * -0.4F;
        }

        if (((EntityLiving)entity).isSneaking()) {
            bodyAngleX = 0.5F;
            armRightAngleX += 0.5F;
            armLeftAngleX += 0.5F;
            legRightPointZ = 4.0F;
            legLeftPointZ = 4.0F;
            legRightPointY = 9.0F;
            legLeftPointY = 9.0F;
            headPointY = 1.0F;
        } else {
            bodyAngleX = 0.0F;
            legRightPointZ = 0.1F;
            legLeftPointZ = 0.1F;
            legRightPointY = 12.0F;
            legLeftPointY = 12.0F;
            headPointY = 0.0F;
        }

        armRightAngleZ += MathHelper.cos(specialRotation * 0.09F) * 0.05F + 0.05F;
        armLeftAngleZ -= MathHelper.cos(specialRotation * 0.09F) * 0.05F + 0.05F;
        armRightAngleX += MathHelper.cos(specialRotation * 0.067F) * 0.05F;
        armLeftAngleX -= MathHelper.cos(specialRotation * 0.067F) * 0.05F;

        if ((((EntityLiving)entity).getHeldItem() != null && ((EntityLiving)entity).getHeldItem().getItemUseAction() == EnumAction.bow)) {
            armRightAngleZ = 0.0F;
            armLeftAngleZ = 0.0F;
            armRightAngleY = -0.1F + entity.getFloat("headLinearRotateAngleY");
            armLeftAngleY = 0.1F + entity.getFloat("headLinearRotateAngleY") + 0.4F;
            armRightAngleX = -((float)Math.PI / 2F) + entity.getFloat("headLinearRotateAngleX");
            armLeftAngleX = -((float)Math.PI / 2F) + entity.getFloat("headLinearRotateAngleX");
            armRightAngleZ += MathHelper.cos(specialRotation * 0.09F) * 0.05F + 0.05F;
            armLeftAngleZ -= MathHelper.cos(specialRotation * 0.09F) * 0.05F + 0.05F;
            armRightAngleX += MathHelper.sin(specialRotation * 0.067F) * 0.05F;
            armLeftAngleX -= MathHelper.sin(specialRotation * 0.067F) * 0.05F;
        }

        entity.setFloat("bodyLinearRotateAngleX", bodyAngleX);
        entity.setFloat("bodyLinearRotateAngleY", bodyAngleY);
        entity.setFloat("bodyLinearRotateAngleZ", bodyAngleZ);
        entity.setFloat("armRightLinearRotationPointX", armRightPointX);
        entity.setFloat("armRightLinearRotationPointZ", armRightPointZ);
        entity.setFloat("armLeftLinearRotationPointX", armLeftPointX);
        entity.setFloat("armLeftLinearRotationPointZ", armLeftPointZ);
        entity.setFloat("armRightLinearRotateAngleX", armRightAngleX);
        entity.setFloat("armRightLinearRotateAngleY", armRightAngleY);
        entity.setFloat("armRightLinearRotateAngleZ", armRightAngleZ);
        entity.setFloat("armLeftLinearRotateAngleX", armLeftAngleX);
        entity.setFloat("armLeftLinearRotateAngleY", armLeftAngleY);
        entity.setFloat("armLeftLinearRotateAngleZ", armLeftAngleZ);
        entity.setFloat("legLeftLinearRotationPointY", legLeftPointY);
        entity.setFloat("legLeftLinearRotationPointZ", legLeftPointZ);
        entity.setFloat("legRightLinearRotationPointY", legRightPointY);
        entity.setFloat("legRightLinearRotationPointZ", legRightPointZ);
        entity.setFloat("legLeftLinearRotateAngleX", legLeftAngleX);
        entity.setFloat("legRightLinearRotateAngleX", legRightAngleX);
        entity.setFloat("legLeftLinearRotateAngleY", legLeftAngleY);
        entity.setFloat("legRightLinearRotateAngleY", legRightAngleY);
        entity.setFloat("headLinearRotationPointY", headPointY);

        for(int i = 0; i < head.length; i++) {
            if(i < animationLinearHead.length) {
                head[i].resetRotations();
                animationLinearHead[i].setAnimationModel(head[i]).animate(entity);
            }
            head[i].render(0.0625F);
        }

        for(int i = 0; i < body.length; i++) {
            if(i < animationLinearBody.length) {
                body[i].resetRotations();
                animationLinearBody[i].setAnimationModel(body[i]).animate(entity);
            }
            body[i].render(0.0625F);
        }

        for(int i = 0; i < armLeft.length; i++) {
            if(i < animationWaveArmLeft.length) {
                armLeft[i].resetRotations();
                animationWaveArmLeft[i].setAnimationModel(armLeft[i]).animate(entity);
            }
            if(i < animationLinearArmLeft.length) {
                animationLinearArmLeft[i].setAnimationModel(armLeft[i]).animate(entity);
            }
            armLeft[i].render(0.0625F);
        }

        for(int i = 0; i < armRight.length; i++) {
            if(i < animationWaveArmRight.length) {
                armRight[i].resetRotations();
                animationWaveArmRight[i].setAnimationModel(armRight[i]).animate(entity);
            }
            if(i < animationLinearArmRight.length) {
                animationLinearArmRight[i].setAnimationModel(armRight[i]).animate(entity);
            }
            armRight[i].render(0.0625F);
        }

        for(int i = 0; i < legLeft.length; i++) {
            if(i < animationWaveLegLeft.length) {
                legLeft[i].resetRotations();
                animationWaveLegLeft[i].setAnimationModel(legLeft[i]).animate(entity);
            }
            if(i < animationLinearLegLeft.length) {
                animationLinearLegLeft[i].setAnimationModel(legLeft[i]).animate(entity);
            }
            legLeft[i].render(0.0625F);
        }

        for(int i = 0; i < legRight.length; i++) {
            if(i < animationWaveLegRight.length) {
                legRight[i].resetRotations();
                animationWaveLegRight[i].setAnimationModel(legRight[i]).animate(entity);
            }
            if(i < animationLinearLegRight.length) {
                animationLinearLegRight[i].setAnimationModel(legRight[i]).animate(entity);
            }
            legRight[i].render(0.0625F);
        }

//        for(int i = 0; i < cloak.length; i++) {
//            if(i < animationLinearCloak.length) {
//                animationLinearCloak[i].setAnimationModel(cloak[i]).animate(entity);
//            }
//            cloak[i].render(0.0625F);
//        }

        for(int i = 0; i < ears.length; i++) {
            if(i < animationLinearEars.length) {
                ears[i].resetRotations();
                animationLinearEars[i].setAnimationModel(ears[i]).animate(entity);
            }
            ears[i].render(0.0625F);
        }
    }

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        EntityLiving living = (EntityLiving) entity;

        double d0 = living.posX - living.prevPosX;
        double d1 = living.posZ - living.prevPosZ;
        float f = (float)(d0 * d0 + d1 * d1);
        float f1 = living.renderYawOffset;
        float f2 = 0.0F;
        entity.setFloat("field_70768_au", entity.getFloat("field_110154_aX"));
        float f3 = 0.0F;

        if (f > 0.0025000002F) {
            f3 = 1.0F;
            f2 = (float)Math.sqrt((double)f) * 3.0F;
            f1 = (float)Math.atan2(d1, d0) * 180.0F / (float)Math.PI - 90.0F;
        }

        if (living.swingProgress > 0.0F) {
            f1 = living.rotationYaw;
        }

        if (!living.onGround) {
            f3 = 0.0F;
        }

        entity.setFloat("field_110154_aX", entity.getFloat("field_110154_aX") + (f3 - entity.getFloat("field_110154_aX")) * 0.3F);
        living.worldObj.theProfiler.startSection("headTurn");
        f2 = entity.func_110146_f(f1, f2);
        living.worldObj.theProfiler.endSection();
        living.worldObj.theProfiler.startSection("rangeChecks");

        while (living.rotationYaw - living.prevRotationYaw < -180.0F) {
            living.prevRotationYaw -= 360.0F;
        }

        while (living.rotationYaw - living.prevRotationYaw >= 180.0F) {
            living.prevRotationYaw += 360.0F;
        }

        while (living.renderYawOffset - living.prevRenderYawOffset < -180.0F) {
            living.prevRenderYawOffset -= 360.0F;
        }

        while (living.renderYawOffset - living.prevRenderYawOffset >= 180.0F) {
            living.prevRenderYawOffset += 360.0F;
        }

        while (living.rotationPitch - living.prevRotationPitch < -180.0F) {
            living.prevRotationPitch -= 360.0F;
        }

        while (living.rotationPitch - living.prevRotationPitch >= 180.0F) {
            living.prevRotationPitch += 360.0F;
        }

        while (living.rotationYawHead - living.prevRotationYawHead < -180.0F) {
            living.prevRotationYawHead -= 360.0F;
        }

        while (living.rotationYawHead - living.prevRotationYawHead >= 180.0F) {
            living.prevRotationYawHead += 360.0F;
        }

        living.worldObj.theProfiler.endSection();
        entity.setFloat("field_70764_aw", entity.getFloat("field_70764_aw") + f2);
    }
}
