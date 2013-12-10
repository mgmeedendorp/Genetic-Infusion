package Seremis.SoulCraft.client.render;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.client.model.ModelTransporter;
import Seremis.SoulCraft.client.model.ModelTransporterEngine;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.helper.SCRenderHelper;

public class ItemTransporterModulesRenderer implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    ModelTransporter model = new ModelTransporter();
    ModelTransporterEngine engine = new ModelTransporterEngine();

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

        switch(type) {
            case ENTITY:
                GL11.glTranslated(-0.7, -0.5, -0.7);
                GL11.glScaled(1.0, 1.0, 1.0);
                break;
            case EQUIPPED:

                break;
            case EQUIPPED_FIRST_PERSON:

                break;
            case FIRST_PERSON_MAP:
                break;
            case INVENTORY:
                GL11.glTranslated(0.1, -0.4, 0);
                break;
        }

        switch(item.getItemDamage()) {
            case 0: {

                break;
            }
            case 1: {
                GL11.glPushMatrix();
                GL11.glScalef(1.5F, 1.5F, 1.5F);
                SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER_ENGINE);
                engine.render();
                GL11.glPopMatrix();
                break;
            }
            case 2: {
                GL11.glPushMatrix();
                GL11.glScalef(1.5F, 1.5F, 1.5F);
                SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER);
                model.render();
                GL11.glPopMatrix();
                break;
            }
        }
    }

}
