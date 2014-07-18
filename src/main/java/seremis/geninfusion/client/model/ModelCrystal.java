package seremis.geninfusion.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCrystal extends ModelBase {

    ModelRenderer crystal;

    public ModelCrystal() {

        int textureHeight = 256;
        int textureWidth = 256;

        crystal = new ModelRenderer(this);
        crystal.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16);
        crystal.setRotationPoint(0.0F, 32.0F, 0.0F);
        crystal.setTextureSize(textureWidth, textureHeight);
        setRotation(crystal, 0.7F, 0.0F, 0.4F);
    }

    public void render() {
        crystal.render(0.0625F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
