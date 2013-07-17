package Seremis.SoulCraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.common.ForgeDirection;

public class ModelTransporterEngine extends ModelBase {

    ModelRenderer engine1;
    ModelRenderer engine2;

    public ModelTransporterEngine() {
        int textureHeight = 256;
        int textureWidth = 256;

        engine1 = new ModelRenderer(this);
        engine1.addBox(-12.0F, 2.0F, -12.0F, 12, 8, 6);
        engine1.setTextureSize(textureHeight, textureWidth);
        engine1.setRotationPoint(16.0F, 16.0F, 16.0F);
        engine2 = new ModelRenderer(this);
        engine2.addBox(-12.0F, 2.0F, 6.0F, 12, 8, 6);
        engine2.setTextureSize(textureHeight, textureWidth);
        engine2.setRotationPoint(16.0F, 16.0F, 16.0F);
    }

    public void render(ForgeDirection direction) {
        float scale = 0.03125F;
        float rotationY = 0.0F;
        switch(direction.ordinal()) {
            case 2:
                rotationY = (float) (Math.PI * 0.5);
                break;
            case 3:
                rotationY = (float) (Math.PI * 1.5);
                break;
            case 4:
                rotationY = (float) (Math.PI);
                break;
            case 5:
                rotationY = (float) (0);
                break;
        }
        setRotation(engine1, 0.0F, rotationY, 0.0F);
        engine1.render(scale);
        setRotation(engine2, 0.0F, rotationY, 0.0F);
        engine2.render(scale);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
