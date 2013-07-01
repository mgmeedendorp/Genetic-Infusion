package Seremis.SoulCraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.common.ForgeDirection;

public class ModelTransporter extends ModelBase {
    
    ModelRenderer base;
    ModelRenderer top;
    ModelRenderer side1;
    ModelRenderer side2;
    
    public ModelTransporter() {
        int textureHeight = 256;
        int textureWidth = 256;
        
        base = new ModelRenderer(this);
        base.addBox(-6.0F, 0.0F, -3.0F, 12, 1, 6);
        base.setTextureSize(textureWidth, textureHeight);
        base.setRotationPoint(8.0F, 8.0F, 8.0F);
        side1 = new ModelRenderer(this);
        side1.addBox(-6.0F, 1.0F, -3.0F, 12, 4, 1);
        side1.setTextureSize(textureWidth, textureHeight);
        side1.setRotationPoint(8.0F, 8.0F, 8.0F);
        top = new ModelRenderer(this);
        top.addBox(-6.0F, 5.0F, -3.0F, 12, 1, 6);
        top.setTextureSize(textureWidth, textureHeight);
        top.setRotationPoint(8.0F, 8.0F, 8.0F);
        side2 = new ModelRenderer(this);
        side2.addBox(-6.0F, 1.0F, 2.0F, 12, 4, 1);
        side2.setTextureSize(textureWidth, textureHeight);
        side2.setRotationPoint(8.0F, 8.0F, 8.0F);
    }
    
    public void render(ForgeDirection direction) {
        float scale = 0.0625F;
        float rotationY = (float)(Math.PI*1.5);
        switch(direction.ordinal()) {
            case 2: rotationY = (float)(Math.PI*0.5);
                    break;
            case 3: rotationY = (float)(Math.PI*1.5);
                    break;
            case 4: rotationY = (float)(Math.PI);
                    break;
            case 5: rotationY = (float)(0);
                    break;
        }
        setRotation(base, 0.0F, rotationY, 0.0F);
        base.render(scale);
        setRotation(top, 0.0F, rotationY, 0.0F);
        top.render(scale);
        setRotation(side1, 0.0F, rotationY, 0.0F);
        side1.render(scale);
        setRotation(side2, 0.0F, rotationY, 0.0F);
        side2.render(scale);
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
