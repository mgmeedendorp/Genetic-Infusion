// Date: 12-8-2013 20:17:16
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package Seremis.SoulCraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBush3 extends ModelBase {
  //fields
    ModelRenderer Stem;
    ModelRenderer BushBase;
  
  public ModelBush3() {
    textureWidth = 64;
    textureHeight = 32;
    
      Stem = new ModelRenderer(this, 16, 0);
      Stem.addBox(0F, 0F, 0F, 2, 6, 2);
      Stem.setRotationPoint(-1F, 18F, -1F);
      Stem.setTextureSize(64, 32);
      Stem.mirror = true;
      setRotation(Stem, 0F, 0F, 0F);
      BushBase = new ModelRenderer(this, 0, 0);
      BushBase.addBox(0F, 0F, 0F, 4, 5, 4);
      BushBase.setRotationPoint(-2F, 13F, -2F);
      BushBase.setTextureSize(64, 32);
      BushBase.mirror = true;
      setRotation(BushBase, 0F, 0F, 0F);
  }
  
  public void render() {
      float scale = 0.0625F;
      Stem.render(scale);
      BushBase.render(scale);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
  }
}
