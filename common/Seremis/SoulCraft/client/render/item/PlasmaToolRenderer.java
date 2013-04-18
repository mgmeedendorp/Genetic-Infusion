package Seremis.SoulCraft.client.render.item;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.IItemRenderer;
import Seremis.SoulCraft.client.render.tile.ModelCrystal;

public class PlasmaToolRenderer implements IItemRenderer {

    private ModelCrystal model = new ModelCrystal();
    
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type == ItemRenderType.ENTITY;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch(type) {
            case ENTITY:
                RenderBlocks renderEntity = (RenderBlocks) data[0];
                EntityItem entityEntity = (EntityItem) data[1];
                model.render();
                break;
            case INVENTORY:
                RenderBlocks renderInventory = (RenderBlocks) data[0];
                break;
            case EQUIPPED:
                RenderBlocks renderEquipped = (RenderBlocks) data[0];
                EntityLiving entityEquipped = (EntityLiving) data[1];
                model.render();
                break;
            case FIRST_PERSON_MAP:
                EntityPlayer playerFirstPerson = (EntityPlayer) data[0];
                RenderEngine engineFirstPerson = (RenderEngine) data[1];
                MapData mapDataFirstPerson = (MapData) data[2];
                model.render();
                break;
            default:
                break;
        }
    }
}
