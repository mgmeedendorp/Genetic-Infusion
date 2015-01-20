package seremis.geninfusion.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * SoulCage - Seremis
 * Created using Tabula 4.1.1
 */
public class ModelSoulCage extends ModelBase {
    public ModelRenderer top2;
    public ModelRenderer bottom3;
    public ModelRenderer top4;
    public ModelRenderer top3;
    public ModelRenderer bottom4;
    public ModelRenderer bottom2;
    public ModelRenderer top1;
    public ModelRenderer bottom1;
    public ModelRenderer side3;
    public ModelRenderer side2;
    public ModelRenderer side1;
    public ModelRenderer side4;

    public ModelSoulCage() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.bottom4 = new ModelRenderer(this, 0, 0);
        this.bottom4.setRotationPoint(6.0F, 22.0F, -6.0F);
        this.bottom4.addBox(0.0F, 0.0F, 0.0F, 2, 2, 12, 0.0F);
        this.bottom2 = new ModelRenderer(this, 0, 0);
        this.bottom2.setRotationPoint(-8.0F, 22.0F, -8.0F);
        this.bottom2.addBox(0.0F, 0.0F, 0.0F, 16, 2, 2, 0.0F);
        this.side4 = new ModelRenderer(this, 0, 0);
        this.side4.setRotationPoint(-8.0F, 10.0F, 6.0F);
        this.side4.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.top1 = new ModelRenderer(this, 0, 0);
        this.top1.setRotationPoint(-8.0F, 8.0F, 6.0F);
        this.top1.addBox(0.0F, 0.0F, 0.0F, 16, 2, 2, 0.0F);
        this.top2 = new ModelRenderer(this, 0, 0);
        this.top2.setRotationPoint(-8.0F, 8.0F, -8.0F);
        this.top2.addBox(0.0F, 0.0F, 0.0F, 16, 2, 2, 0.0F);
        this.top4 = new ModelRenderer(this, 0, 0);
        this.top4.setRotationPoint(6.0F, 8.0F, -6.0F);
        this.top4.addBox(0.0F, 0.0F, 0.0F, 2, 2, 12, 0.0F);
        this.bottom3 = new ModelRenderer(this, 0, 0);
        this.bottom3.setRotationPoint(-8.0F, 22.0F, -6.0F);
        this.bottom3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 12, 0.0F);
        this.side1 = new ModelRenderer(this, 0, 0);
        this.side1.setRotationPoint(6.0F, 10.0F, -8.0F);
        this.side1.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.side3 = new ModelRenderer(this, 0, 0);
        this.side3.setRotationPoint(6.0F, 10.0F, 6.0F);
        this.side3.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
        this.bottom1 = new ModelRenderer(this, 0, 0);
        this.bottom1.setRotationPoint(-8.0F, 22.0F, 6.0F);
        this.bottom1.addBox(0.0F, 0.0F, 0.0F, 16, 2, 2, 0.0F);
        this.top3 = new ModelRenderer(this, 0, 0);
        this.top3.setRotationPoint(-8.0F, 8.0F, -6.0F);
        this.top3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 12, 0.0F);
        this.side2 = new ModelRenderer(this, 0, 0);
        this.side2.setRotationPoint(-8.0F, 10.0F, -8.0F);
        this.side2.addBox(0.0F, 0.0F, 0.0F, 2, 12, 2, 0.0F);
    }

    public void render(float scale) {
        this.bottom4.render(scale);
        this.bottom2.render(scale);
        this.side4.render(scale);
        this.top1.render(scale);
        this.top2.render(scale);
        this.top4.render(scale);
        this.bottom3.render(scale);
        this.side1.render(scale);
        this.side3.render(scale);
        this.bottom1.render(scale);
        this.top3.render(scale);
        this.side2.render(scale);
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
