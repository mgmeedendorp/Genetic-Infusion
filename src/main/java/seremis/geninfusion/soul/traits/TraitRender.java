package seremis.geninfusion.soul.traits;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLiving;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.ModelPart;

public class TraitRender extends Trait {

    @Override
    @SideOnly(Side.CLIENT)
    public void render(IEntitySoulCustom entity, float timeModifier, float limbSwing, float specialRotation, float rotationYawHead, float rotationPitch, float scale) {
        ModelPart[] model = SoulHelper.geneRegistry.getValueModelPartArray(entity, Genes.GENE_MODEL);

        for(int i = 0; i < model.length; i++) {
            model[i].render(scale);
        }
    }

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        EntityLiving living = (EntityLiving) entity;

        double d0 = living.posX - living.prevPosX;
        double d1 = living.posZ - living.prevPosZ;
        float f = (float) (d0 * d0 + d1 * d1);
        float f1 = living.renderYawOffset;
        float f2 = 0.0F;
        entity.setFloat("field_70768_au", entity.getFloat("field_110154_aX"));
        float f3 = 0.0F;

        if(f > 0.0025000002F) {
            f3 = 1.0F;
            f2 = (float) Math.sqrt((double) f) * 3.0F;
            f1 = (float) Math.atan2(d1, d0) * 180.0F / (float) Math.PI - 90.0F;
        }

        if(living.swingProgress > 0.0F) {
            f1 = living.rotationYaw;
        }

        if(!living.onGround) {
            f3 = 0.0F;
        }

        entity.setFloat("field_110154_aX", entity.getFloat("field_110154_aX") + (f3 - entity.getFloat("field_110154_aX")) * 0.3F);
        living.worldObj.theProfiler.startSection("headTurn");
        f2 = entity.func_110146_f(f1, f2);
        living.worldObj.theProfiler.endSection();
        living.worldObj.theProfiler.startSection("rangeChecks");

        while(living.rotationYaw - living.prevRotationYaw < -180.0F) {
            living.prevRotationYaw -= 360.0F;
        }

        while(living.rotationYaw - living.prevRotationYaw >= 180.0F) {
            living.prevRotationYaw += 360.0F;
        }

        while(living.renderYawOffset - living.prevRenderYawOffset < -180.0F) {
            living.prevRenderYawOffset -= 360.0F;
        }

        while(living.renderYawOffset - living.prevRenderYawOffset >= 180.0F) {
            living.prevRenderYawOffset += 360.0F;
        }

        while(living.rotationPitch - living.prevRotationPitch < -180.0F) {
            living.prevRotationPitch -= 360.0F;
        }

        while(living.rotationPitch - living.prevRotationPitch >= 180.0F) {
            living.prevRotationPitch += 360.0F;
        }

        while(living.rotationYawHead - living.prevRotationYawHead < -180.0F) {
            living.prevRotationYawHead -= 360.0F;
        }

        while(living.rotationYawHead - living.prevRotationYawHead >= 180.0F) {
            living.prevRotationYawHead += 360.0F;
        }

        living.worldObj.theProfiler.endSection();
        entity.setFloat("field_70764_aw", entity.getFloat("field_70764_aw") + f2);

        entity.updateArmSwingProgress();

        boolean burnsInDaylight = SoulHelper.geneRegistry.getValueBoolean(entity, Genes.GENE_BURNS_IN_DAYLIGHT);

        if(living.getBrightness(1.0F) > 0.5F && burnsInDaylight) {
            entity.setInteger("entityAge", entity.getInteger("entityAge") + 2);
        }
    }
}
