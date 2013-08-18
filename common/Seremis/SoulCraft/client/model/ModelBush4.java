// Date: 12-8-2013 20:47:07
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package Seremis.SoulCraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBush4 extends ModelBase {
  //fields
    ModelRenderer Stem;
    ModelRenderer BushBase;
    ModelRenderer BushPart1;
    ModelRenderer BushPart2;
    ModelRenderer BushPart3;
    ModelRenderer BushPart4;
    ModelRenderer BushPart5;
    ModelRenderer BushPart6;
    ModelRenderer BushPart7;
    ModelRenderer BushPart8;
    ModelRenderer BushPart9;
    ModelRenderer BushPart10;
    ModelRenderer BushPart11;
    ModelRenderer Berry1;
    ModelRenderer Berry2;
    ModelRenderer Berry3;
    ModelRenderer Berry4;
  
  public ModelBush4() {
    textureWidth = 64;
    textureHeight = 32;
    
      Stem = new ModelRenderer(this, 0, 0);
      Stem.addBox(0F, 0F, 0F, 2, 7, 2);
      Stem.setRotationPoint(-1F, 17F, -1F);
      Stem.setTextureSize(64, 32);
      Stem.mirror = true;
      setRotation(Stem, 0F, 0F, 0F);
      BushBase = new ModelRenderer(this, 0, 9);
      BushBase.addBox(0F, 0F, 0F, 4, 5, 4);
      BushBase.setRotationPoint(-2F, 12F, -2F);
      BushBase.setTextureSize(64, 32);
      BushBase.mirror = true;
      setRotation(BushBase, 0F, 0F, 0F);
      BushPart1 = new ModelRenderer(this, 0, 9);
      BushPart1.addBox(0F, 0F, 0F, 2, 3, 1);
      BushPart1.setRotationPoint(-1F, 13F, 2F);
      BushPart1.setTextureSize(64, 32);
      BushPart1.mirror = true;
      setRotation(BushPart1, 0F, 0F, 0F);
      BushPart2 = new ModelRenderer(this, 0, 9);
      BushPart2.addBox(0F, 0F, 0F, 1, 1, 1);
      BushPart2.setRotationPoint(0F, 14F, 3F);
      BushPart2.setTextureSize(64, 32);
      BushPart2.mirror = true;
      setRotation(BushPart2, 0F, 0F, 0F);
      BushPart3 = new ModelRenderer(this, 0, 9);
      BushPart3.addBox(0F, 0F, 0F, 1, 2, 2);
      BushPart3.setRotationPoint(-3F, 13F, -1F);
      BushPart3.setTextureSize(64, 32);
      BushPart3.mirror = true;
      setRotation(BushPart3, 0F, 0F, 0F);
      BushPart4 = new ModelRenderer(this, 0, 10);
      BushPart4.addBox(0F, 0F, 0F, 1, 1, 1);
      BushPart4.setRotationPoint(-3F, 15F, 0F);
      BushPart4.setTextureSize(64, 32);
      BushPart4.mirror = true;
      setRotation(BushPart4, 0F, 0F, 0F);
      BushPart5 = new ModelRenderer(this, 0, 11);
      BushPart5.addBox(0F, 0F, 0F, 1, 1, 1);
      BushPart5.setRotationPoint(-4F, 14F, -1F);
      BushPart5.setTextureSize(64, 32);
      BushPart5.mirror = true;
      setRotation(BushPart5, 0F, 0F, 0F);
      BushPart6 = new ModelRenderer(this, 0, 12);
      BushPart6.addBox(0F, 0F, 0F, 2, 3, 1);
      BushPart6.setRotationPoint(-1F, 13F, -3F);
      BushPart6.setTextureSize(64, 32);
      BushPart6.mirror = true;
      setRotation(BushPart6, 0F, 0F, 0F);
      BushPart7 = new ModelRenderer(this, 0, 11);
      BushPart7.addBox(0F, 0F, 0F, 1, 1, 1);
      BushPart7.setRotationPoint(0F, 14F, -4F);
      BushPart7.setTextureSize(64, 32);
      BushPart7.mirror = true;
      setRotation(BushPart7, 0F, 0F, 0F);
      BushPart8 = new ModelRenderer(this, 0, 10);
      BushPart8.addBox(0F, 0F, 0F, 1, 3, 3);
      BushPart8.setRotationPoint(2F, 13F, -1F);
      BushPart8.setTextureSize(64, 32);
      BushPart8.mirror = true;
      setRotation(BushPart8, 0F, 0F, 0F);
      BushPart9 = new ModelRenderer(this, 0, 13);
      BushPart9.addBox(0F, 0F, 0F, 1, 1, 1);
      BushPart9.setRotationPoint(3F, 14F, 0F);
      BushPart9.setTextureSize(64, 32);
      BushPart9.mirror = true;
      setRotation(BushPart9, 0F, 0F, 0F);
      BushPart10 = new ModelRenderer(this, 0, 9);
      BushPart10.addBox(0F, 0F, 0F, 1, 1, 1);
      BushPart10.setRotationPoint(2F, 14F, -2F);
      BushPart10.setTextureSize(64, 32);
      BushPart10.mirror = true;
      setRotation(BushPart10, 0F, 0F, 0F);
      BushPart11 = new ModelRenderer(this, 0, 12);
      BushPart11.addBox(0F, 0F, 0F, 2, 1, 2);
      BushPart11.setRotationPoint(-1F, 11F, -1F);
      BushPart11.setTextureSize(64, 32);
      BushPart11.mirror = true;
      setRotation(BushPart11, 0F, 0F, 0F);
      Berry1 = new ModelRenderer(this, 0, 18);
      Berry1.addBox(0F, 0F, 0F, 1, 1, 1);
      Berry1.setRotationPoint(2F, 16F, 1F);
      Berry1.setTextureSize(64, 32);
      Berry1.mirror = true;
      setRotation(Berry1, 0F, 0F, 0F);
      Berry2 = new ModelRenderer(this, 0, 18);
      Berry2.addBox(0F, 0F, 0F, 1, 1, 1);
      Berry2.setRotationPoint(0F, 15F, 3F);
      Berry2.setTextureSize(64, 32);
      Berry2.mirror = true;
      setRotation(Berry2, 0F, 0F, 0F);
      Berry3 = new ModelRenderer(this, 0, 18);
      Berry3.addBox(0F, 0F, 0F, 1, 1, 1);
      Berry3.setRotationPoint(-3F, 16F, -1F);
      Berry3.setTextureSize(64, 32);
      Berry3.mirror = true;
      setRotation(Berry3, 0F, 0F, 0F);
      Berry4 = new ModelRenderer(this, 0, 18);
      Berry4.addBox(0F, 0F, 0F, 1, 1, 1);
      Berry4.setRotationPoint(0F, 16F, -3F);
      Berry4.setTextureSize(64, 32);
      Berry4.mirror = true;
      setRotation(Berry4, 0F, 0F, 0F);
  }
  
  public void render() {
      float scale = 0.0625F;
      Stem.render(scale);
      BushBase.render(scale);
      BushPart1.render(scale);
      BushPart2.render(scale);
      BushPart3.render(scale);
      BushPart4.render(scale);
      BushPart5.render(scale);
      BushPart6.render(scale);
      BushPart7.render(scale);
      BushPart8.render(scale);
      BushPart9.render(scale);
      BushPart10.render(scale);
      BushPart11.render(scale);
      Berry1.render(scale);
      Berry2.render(scale);
      Berry3.render(scale);
      Berry4.render(scale);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
  }
}
