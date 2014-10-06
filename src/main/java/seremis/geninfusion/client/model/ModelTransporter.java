package seremis.geninfusion.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
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

    public void render() {
        float scale = 0.0625F;

        base.render(scale);
        top.render(scale);
        side1.render(scale);
        side2.render(scale);
    }
}
