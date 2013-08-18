// Date: 12-8-2013 20:10:12
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package Seremis.SoulCraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBush1 extends ModelBase {
  //fields
    ModelRenderer Stem1;
    ModelRenderer Stem2;
    ModelRenderer Stem3;
    ModelRenderer Stem4;
  
  public ModelBush1() {
      textureWidth = 64;
      textureHeight = 32;
    
      Stem1 = new ModelRenderer(this, 0, 0);
      Stem1.addBox(0F, 0F, 0F, 2, 1, 2);
      Stem1.setRotationPoint(-1F, 23F, -1F);
      Stem1.setTextureSize(64, 32);
      Stem1.mirror = true;
      setRotation(Stem1, 0F, 0F, 0F);
      Stem2 = new ModelRenderer(this, 0, 0);
      Stem2.addBox(0F, 0F, 0F, 1, 1, 1);
      Stem2.setRotationPoint(0F, 22F, -1F);
      Stem2.setTextureSize(64, 32);
      Stem2.mirror = true;
      setRotation(Stem2, 0F, 0F, 0F);
      Stem3 = new ModelRenderer(this, 0, 0);
      Stem3.addBox(0F, 0F, 0F, 1, 1, 1);
      Stem3.setRotationPoint(-1F, 21F, 0F);
      Stem3.setTextureSize(64, 32);
      Stem3.mirror = true;
      setRotation(Stem3, 0F, 0F, 0F);
      Stem4 = new ModelRenderer(this, 0, 0);
      Stem4.addBox(0F, 0F, 0F, 1, 1, 1);
      Stem4.setRotationPoint(0F, 20F, 0F);
      Stem4.setTextureSize(64, 32);
      Stem4.mirror = true;
      setRotation(Stem4, 0F, 0F, 0F);
  }
  
  public void render() {
      float scale = 0.0625F;
      Stem1.render(scale);
      Stem2.render(scale);
      Stem3.render(scale);
      Stem4.render(scale);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
  }
}
