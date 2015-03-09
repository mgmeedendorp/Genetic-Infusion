package seremis.geninfusion.soul.entity.render;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.soul.TraitHandler;
import seremis.geninfusion.soul.entity.animation.AnimationCache;

@SideOnly(Side.CLIENT)
public class RenderEntitySoulCustom extends RenderLiving {

    public RenderEntitySoulCustom() {
        super(SoulHelper.entityModel, 1.0F);
    }

    @Override
    public void preRenderCallback(EntityLivingBase entity, float partialTickTime) {
        ((IEntitySoulCustom) entity).setFloat("partialTickTime", partialTickTime);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation(TraitHandler.getEntityTexture((IEntitySoulCustom) entity));
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase base, float p_77029_2_) {
        EntityLiving living = (EntityLiving) base;
        IEntitySoulCustom custom = (IEntitySoulCustom) living;

        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        ItemStack itemstack = living.getHeldItem();
        ItemStack itemstack1 = living.func_130225_q(3);
        Item item;
        float f1;

        if(itemstack1 != null) {
            GL11.glPushMatrix();
            ModelPart head = AnimationCache.getModelHead(custom)[0];
            head.postRender(0.0625F);
            item = itemstack1.getItem();

            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack1, IItemRenderer.ItemRenderType.EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, itemstack1, IItemRenderer.ItemRendererHelper.BLOCK_3D));

            if(item instanceof ItemBlock) {
                if(is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType())) {
                    f1 = 0.625F;
                    GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(f1, -f1, -f1);
                }

                this.renderManager.itemRenderer.renderItem(living, itemstack1, 0);
            } else if(item == Items.skull) {
                f1 = 1.0625F;
                GL11.glScalef(f1, -f1, -f1);
                GameProfile gameprofile = null;

                if(itemstack1.hasTagCompound()) {
                    NBTTagCompound nbttagcompound = itemstack1.getTagCompound();

                    if(nbttagcompound.hasKey("SkullOwner", 10)) {
                        gameprofile = NBTUtil.func_152459_a(nbttagcompound.getCompoundTag("SkullOwner"));
                    } else if(nbttagcompound.hasKey("SkullOwner", 8) && !StringUtils.isNullOrEmpty(nbttagcompound.getString("SkullOwner"))) {
                        gameprofile = new GameProfile(null, nbttagcompound.getString("SkullOwner"));
                    }
                }

                TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, itemstack1.getItemDamage(), gameprofile);
            }

            GL11.glPopMatrix();
        }

        if(itemstack != null && itemstack.getItem() != null) {
            item = itemstack.getItem();
            GL11.glPushMatrix();

            if(this.mainModel.isChild) {
                f1 = 0.5F;
                GL11.glTranslatef(0.0F, 0.625F, 0.0F);
                GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
                GL11.glScalef(f1, f1, f1);
            }

            if(!living.isDead && AnimationCache.getModelArms(custom) != null && AnimationCache.getModelArms(custom).length > 0) {
                ModelPart rightArm = AnimationCache.getModelArms(custom)[0];
                rightArm.postRender(0.0625F);

                GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

                IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.EQUIPPED);
                boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, IItemRenderer.ItemRendererHelper.BLOCK_3D));

                if(item instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType()))) {
                    f1 = 0.5F;
                    GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                    f1 *= 0.75F;
                    GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(-f1, -f1, f1);
                } else if(item == Items.bow) {
                    f1 = 0.625F;
                    GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                    GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(f1, -f1, f1);
                    GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                } else if(item.isFull3D()) {
                    f1 = 0.625F;

                    if(item.shouldRotateAroundWhenRendering()) {
                        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                    }

                    GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                    GL11.glScalef(f1, -f1, f1);
                    GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                } else {
                    f1 = 0.375F;
                    GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                    GL11.glScalef(f1, f1, f1);
                    GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
                }

                float f2;
                int i;
                float f5;

                if(itemstack.getItem().requiresMultipleRenderPasses()) {
                    for(i = 0; i < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++i) {
                        int j = itemstack.getItem().getColorFromItemStack(itemstack, i);
                        f5 = (float) (j >> 16 & 255) / 255.0F;
                        f2 = (float) (j >> 8 & 255) / 255.0F;
                        float f3 = (float) (j & 255) / 255.0F;
                        GL11.glColor4f(f5, f2, f3, 1.0F);
                        this.renderManager.itemRenderer.renderItem(living, itemstack, i);
                    }
                } else {
                    i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
                    float f4 = (float) (i >> 16 & 255) / 255.0F;
                    f5 = (float) (i >> 8 & 255) / 255.0F;
                    f2 = (float) (i & 255) / 255.0F;
                    GL11.glColor4f(f4, f5, f2, 1.0F);
                    this.renderManager.itemRenderer.renderItem(living, itemstack, 0);
                }
            }

            GL11.glPopMatrix();
        }
    }
}
