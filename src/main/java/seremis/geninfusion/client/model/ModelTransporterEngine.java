package seremis.geninfusion.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
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

    public void render() {
        float scale = 0.03125F;

        engine1.render(scale);
        engine2.render(scale);
    }
}
