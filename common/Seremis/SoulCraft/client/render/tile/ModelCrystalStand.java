package Seremis.SoulCraft.client.render.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCrystalStand extends ModelBase {
	
	ModelRenderer standBase;
	ModelRenderer standPart1;
	ModelRenderer standPart2;
	ModelRenderer standPart3;
	ModelRenderer standPart4;
	ModelRenderer standPart5;
	ModelRenderer standPart6;
	ModelCrystal crystal;
	
	public ModelCrystalStand() {
		
		int textureHeight = 256;
		int textureWidth = 256;
		
		standBase = new ModelRenderer(this, 0, 0);
		standBase.addBox(4.0F, 0.0F, 5.0F, 8, 2, 6);
		standBase.setTextureSize(textureHeight, textureWidth);
		standBase.setRotationPoint(0F, 0F, 0F);
		standBase.mirror = true;
		
		standPart1 = new ModelRenderer(this, 0, 0);
		standPart1.addBox(7.0F, 2.0F, 7.0F, 2, 7, 2);
		standPart1.setTextureSize(textureHeight, textureWidth);
		standPart1.setRotationPoint(0F, 0F, 0F);
		standPart1.mirror = true;
		
//		standPart2 = new ModelRenderer(this, 0, 0);
//		standPart2.addBox(4.0F, 9.0F, 7.0F, 8, 2, 2);
//		standPart2.setTextureSize(textureHeight, textureWidth);
//		standPart2.setRotationPoint(0.0F, 0.0F, 0.0F);
//		standPart2.mirror = true;
	}
	
	public void render() {
		float scale = 0.0625F;
		standBase.render(scale);
		standPart1.render(scale);
//		standPart2.render(scale);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) 
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
