package Seremis.SoulCraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelTransporter extends ModelBase {
    
    ModelRenderer base;
    ModelRenderer side1;
    ModelRenderer side2;
    ModelRenderer side3;
    ModelRenderer side4;
    ModelRenderer top;
    
    public ModelTransporter() {
        int textureHeight = 256;
        int textureWidth = 256;
        
        base = new ModelRenderer(this);
        base.addBox(2.0F, 2.0F, 2.0F, 12, 1, 12);
        base.setTextureSize(textureWidth, textureHeight);
        base.setRotationPoint(0.0F, 0.0F, 0.0F);
        base.mirror = true;
        side1 = new ModelRenderer(this);
        side2 = new ModelRenderer(this);
        side3 = new ModelRenderer(this);
        side4 = new ModelRenderer(this);
        top = new ModelRenderer(this);
    }
    
    public void render(int openPhase) {
        float scale = 0.0625F;
        base.render(scale);
//        side1.render(scale);
//        side2.render(scale);
//        side3.render(scale);
//        side4.render(scale);
//        top.render(scale);
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z) 
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
