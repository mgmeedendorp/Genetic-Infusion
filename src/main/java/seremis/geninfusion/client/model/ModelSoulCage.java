package seremis.geninfusion.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * SoulCage - Seremis
 * Created using Tabula 4.1.1
 */
public class ModelSoulCage extends ModelBase {
    public ModelRenderer Top2;
    public ModelRenderer Bottom3;
    public ModelRenderer Top4;
    public ModelRenderer Top3;
    public ModelRenderer Bottom4;
    public ModelRenderer Bottom2;
    public ModelRenderer Top1;
    public ModelRenderer Bottom1;
    public ModelRenderer Side3;
    public ModelRenderer Side2;
    public ModelRenderer Side1;
    public ModelRenderer Side4;

    public ModelSoulCage() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Bottom4 = new ModelRenderer(this, 0, 0);
        this.Bottom4.setRotationPoint(6.0F, 22.0F, -6.0F);
        this.Bottom4.addBox(0.0F, 0.0F, 0.0F, 2, 2, 12, 0.0F);
        this.Bottom2 = new ModelRenderer(this, 0, 0);
        this.Bottom2.setRotationPoint(-8.0F, 22.0F, -8.0F);
        this.Bottom2.addBox(0.0F, 0.0F, 0.0F, 16, 2, 2, 0.0F);
        this.Side4 = new ModelRenderer(this, 0, 0);
        this.Side4.setRotationPoint(-8.0F, 10.0F, 6.0F);
        this.Side4.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.Top1 = new ModelRenderer(this, 0, 0);
        this.Top1.setRotationPoint(-8.0F, 8.0F, 6.0F);
        this.Top1.addBox(0.0F, 0.0F, 0.0F, 16, 2, 2, 0.0F);
        this.Top2 = new ModelRenderer(this, 0, 0);
        this.Top2.setRotationPoint(-8.0F, 8.0F, -8.0F);
        this.Top2.addBox(0.0F, 0.0F, 0.0F, 16, 2, 2, 0.0F);
        this.Top4 = new ModelRenderer(this, 0, 0);
        this.Top4.setRotationPoint(6.0F, 8.0F, -6.0F);
        this.Top4.addBox(0.0F, 0.0F, 0.0F, 2, 2, 12, 0.0F);
        this.Bottom3 = new ModelRenderer(this, 0, 0);
        this.Bottom3.setRotationPoint(-8.0F, 22.0F, -6.0F);
        this.Bottom3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 12, 0.0F);
        this.Side1 = new ModelRenderer(this, 0, 0);
        this.Side1.setRotationPoint(6.0F, 10.0F, -8.0F);
        this.Side1.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.Side3 = new ModelRenderer(this, 0, 0);
        this.Side3.setRotationPoint(6.0F, 10.0F, 6.0F);
        this.Side3.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.Bottom1 = new ModelRenderer(this, 0, 0);
        this.Bottom1.setRotationPoint(-8.0F, 22.0F, 6.0F);
        this.Bottom1.addBox(0.0F, 0.0F, 0.0F, 16, 2, 2, 0.0F);
        this.Top3 = new ModelRenderer(this, 0, 0);
        this.Top3.setRotationPoint(-8.0F, 8.0F, -6.0F);
        this.Top3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 12, 0.0F);
        this.Side2 = new ModelRenderer(this, 0, 0);
        this.Side2.setRotationPoint(-8.0F, 10.0F, -8.0F);
        this.Side2.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
    }

    public void render(float scale) {
        this.Bottom4.render(scale);
        this.Bottom2.render(scale);
        this.Side4.render(scale);
        this.Top1.render(scale);
        this.Top2.render(scale);
        this.Top4.render(scale);
        this.Bottom3.render(scale);
        this.Side1.render(scale);
        this.Side3.render(scale);
        this.Bottom1.render(scale);
        this.Top3.render(scale);
        this.Side2.render(scale);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
