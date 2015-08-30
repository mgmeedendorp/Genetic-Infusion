package seremis.geninfusion.soul.`trait`

import com.mojang.authlib.GameProfile
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer
import net.minecraft.entity.EntityLiving
import net.minecraft.init.Items
import net.minecraft.item.{Item, ItemBlock}
import net.minecraft.nbt.NBTUtil
import net.minecraft.util.{MathHelper, StringUtils}
import net.minecraftforge.client.{IItemRenderer, MinecraftForgeClient}
import org.lwjgl.opengl.GL11
import seremis.geninfusion.api.soul.lib.{ModelPartTypes, Genes}
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.api.util.DataWatcherHelper
import seremis.geninfusion.api.util.render.model.{Model, ModelPart}
import seremis.geninfusion.helper.GIRenderHelper

class TraitRender extends Trait {

    @SideOnly(Side.CLIENT)
    override def render(entity: IEntitySoulCustom, timeModifier: Float, limbSwing: Float, specialRotation: Float, rotationYawHead: Float, rotationPitch: Float, scale: Float) {
        val model = SoulHelper.geneRegistry.getValueFromAllele[Model](entity, Genes.GeneModel)

        if(entity.asInstanceOf[EntityLiving].isChild) {

            if(model.getParts(ModelPartTypes.Head).nonEmpty) {
                GL11.glPushMatrix()
                GL11.glScalef(0.75F, 0.75F, 0.75F)
                GL11.glTranslatef(0.0F, 16.0F * scale, 0.0F)
                new Model(model.getParts(ModelPartTypes.Head).get).render(scale)
                GL11.glPopMatrix()
            }
            GL11.glPushMatrix()
            GL11.glScalef(0.5F, 0.5F, 0.5F)
            GL11.glTranslatef(0.0F, 24.0F * scale, 0.0F)
            if(model.getParts(ModelPartTypes.Head).nonEmpty) {
                model.getWholeModelExcept(model.getParts(ModelPartTypes.Head).get).render(scale)
            } else {
                model.getWholeModelExcept(model.getParts(ModelPartTypes.Head).get).render(scale)
            }
            GL11.glPopMatrix()
        } else {
            for(part <- model.getAllParts) {
                part.render(scale)
            }
        }
    }

    @SideOnly(Side.CLIENT)
    override def renderEquippedItems(entity: IEntitySoulCustom, partialTickTime: Float) {
        val living = entity.asInstanceOf[EntityLiving]

        val model: Model = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneModel)

        GL11.glColor3f(1.0F, 1.0F, 1.0F)
        val itemstack = living.getHeldItem
        val itemstack1 = living.func_130225_q(3)
        var item: Item = null
        var f1: Float = 0.0f
        if (itemstack1 != null) {
            GL11.glPushMatrix()
            val head = model.getParts(ModelPartTypes.Head)
            head.foreach(head => head.foreach(head => head.postRender(0.0625F)))
            item = itemstack1.getItem
            val customRenderer = MinecraftForgeClient.getItemRenderer(itemstack1, IItemRenderer.ItemRenderType.EQUIPPED)
            val is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, itemstack1, IItemRenderer.ItemRendererHelper.BLOCK_3D)
            if (item.isInstanceOf[ItemBlock]) {
                if (is3D ||
                    RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType)) {
                    f1 = 0.625F
                    GL11.glTranslatef(0.0F, -0.25F, 0.0F)
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F)
                    GL11.glScalef(f1, -f1, -f1)
                }
                RenderManager.instance.itemRenderer.renderItem(living, itemstack1, 0)
            } else if (item == Items.skull) {
                f1 = 1.0625F
                GL11.glScalef(f1, -f1, -f1)
                var gameprofile: GameProfile = null
                if (itemstack1.hasTagCompound) {
                    val nbttagcompound = itemstack1.getTagCompound
                    if (nbttagcompound.hasKey("SkullOwner", 10)) {
                        gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"))
                    } else if (nbttagcompound.hasKey("SkullOwner", 8) &&
                        !StringUtils.isNullOrEmpty(nbttagcompound.getString("SkullOwner"))) {
                        gameprofile = new GameProfile(null, nbttagcompound.getString("SkullOwner"))
                    }
                }
                TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, itemstack1.getMetadata, gameprofile)
            }
            GL11.glPopMatrix()
        }
        if (itemstack != null && itemstack.getItem != null) {
            item = itemstack.getItem
            GL11.glPushMatrix()
            if(SoulHelper.entityModel.isChild) {
                f1 = 0.5F
                GL11.glTranslatef(0.0F, 0.625F, 0.0F)
                GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F)
                GL11.glScalef(f1, f1, f1)
            }
            if(!living.isDead && model.getParts(ModelPartTypes.ArmsLeft, ModelPartTypes.ArmsRight).nonEmpty) {
                val rightArm = model.getParts(ModelPartTypes.ArmsRight).get(0)
                rightArm.postRender(0.0625F)
                GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F)
                val customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.EQUIPPED)
                val is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, IItemRenderer.ItemRendererHelper.BLOCK_3D)
                if(item.isInstanceOf[ItemBlock] &&
                    (is3D ||
                        RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType))) {
                    f1 = 0.5F
                    GL11.glTranslatef(0.0F, 0.1875F, -0.3125F)
                    f1 *= 0.75F
                    GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F)
                    GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F)
                    GL11.glScalef(-f1, -f1, f1)
                } else if(item == Items.bow) {
                    f1 = 0.625F
                    GL11.glTranslatef(0.0F, 0.125F, 0.3125F)
                    GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F)
                    GL11.glScalef(f1, -f1, f1)
                    GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F)
                    GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F)
                } else if(item.isFull3D) {
                    f1 = 0.625F
                    if(item.shouldRotateAroundWhenRendering()) {
                        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F)
                        GL11.glTranslatef(0.0F, -0.125F, 0.0F)
                    }
                    GL11.glTranslatef(0.0F, 0.1875F, 0.0F)
                    GL11.glScalef(f1, -f1, f1)
                    GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F)
                    GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F)
                } else {
                    f1 = 0.375F
                    GL11.glTranslatef(0.25F, 0.1875F, -0.1875F)
                    GL11.glScalef(f1, f1, f1)
                    GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F)
                    GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F)
                    GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F)
                }
                var f2: Float = 0.0f
                var i: Int = 0
                var f5: Float = 0.0f
                if(itemstack.getItem.requiresMultipleRenderPasses()) {
                    i = 0
                    while(i < itemstack.getItem.getRenderPasses(itemstack.getMetadata)) {
                        val j = itemstack.getItem.getColorFromItemStack(itemstack, i)
                        f5 = (j >> 16 & 255).toFloat / 255.0F
                        f2 = (j >> 8 & 255).toFloat / 255.0F
                        val f3 = (j & 255).toFloat / 255.0F
                        GL11.glColor4f(f5, f2, f3, 1.0F)
                        RenderManager.instance.itemRenderer.renderItem(living, itemstack, i)
                        i += 1
                    }
                } else {
                    i = itemstack.getItem.getColorFromItemStack(itemstack, 0)
                    val f4 = (i >> 16 & 255).toFloat / 255.0F
                    f5 = (i >> 8 & 255).toFloat / 255.0F
                    f2 = (i & 255).toFloat / 255.0F
                    GL11.glColor4f(f4, f5, f2, 1.0F)
                    RenderManager.instance.itemRenderer.renderItem(living, itemstack, 0)
                }
            }
            GL11.glPopMatrix()
        }
    }

    @SideOnly(Side.CLIENT)
    override def preRenderCallback(entity: IEntitySoulCustom, partialTickTime: Float) {
        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneAICreeperSwell)

        if(explodes) {
            var flashIntensity: Float = entity.getInteger(EntityTimeSinceIgnited).toFloat + (entity.getInteger(EntityTimeSinceIgnited) - entity.getInteger(EntityTimeSinceIgnited).toFloat * partialTickTime) / (entity.getInteger(EntityFuseTime) - 2).toFloat
            val f2: Float = 1.0F + MathHelper.sin(flashIntensity * 100.0F) * flashIntensity * 0.01F

            if(flashIntensity < 0.0F) {
                flashIntensity = 0.0F
            }

            if(flashIntensity > 1.0F) {
                flashIntensity = 1.0F
            }

            flashIntensity *= flashIntensity
            flashIntensity *= flashIntensity
            val scaleSideways: Float = (1.0F + flashIntensity * 0.4F) * f2
            val scaleUp: Float = (1.0F + flashIntensity * 0.1F) / f2
            GL11.glScalef(scaleSideways, scaleUp, scaleSideways)
        }
    }

    @SideOnly(Side.CLIENT)
    override def inheritRenderPass(entity: IEntitySoulCustom, renderPass: Int, partialTickTime: Float): Int = {
        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneAICreeperSwell)

        if(explodes)
            return -1

        shouldRenderPass(entity, renderPass, partialTickTime)
    }

    @SideOnly(Side.CLIENT)
    override def shouldRenderPass(entity: IEntitySoulCustom, renderPass: Int, partialTickTime: Float): Int = {
        val living = entity.asInstanceOf[EntityLiving]

        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneAICreeperSwell)
        val canBeCharged = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneCanBeCharged)

        if(explodes && canBeCharged) {
            val isCharged = DataWatcherHelper.getObjectFromDataWatcher(living.getDataWatcher, EntityCharged).asInstanceOf[Byte] == 1

            if(isCharged){
                GL11.glDepthMask(!living.isInvisible)

                if (renderPass == 1)
                {
                    val partialTicksExisted = living.ticksExisted + partialTickTime
                    GIRenderHelper.bindTexture("textures/entity/creeper/creeper_armor.png")
                    GL11.glMatrixMode(GL11.GL_TEXTURE)
                    GL11.glLoadIdentity()
                    val f2 = partialTicksExisted * 0.01F
                    val f3 = partialTicksExisted * 0.01F
                    GL11.glTranslatef(f2, f3, 0.0F)
                    entity.getEntityRender_I.setRenderPassModel(SoulHelper.entityModel)
                    GL11.glMatrixMode(GL11.GL_MODELVIEW)
                    GL11.glEnable(GL11.GL_BLEND)
                    val f4 = 0.5F
                    GL11.glColor4f(f4, f4, f4, 1.0F)
                    GL11.glDisable(GL11.GL_LIGHTING)
                    GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE)
                    return 1
                }

                if (renderPass == 2)
                {
                    GL11.glMatrixMode(GL11.GL_TEXTURE)
                    GL11.glLoadIdentity()
                    GL11.glMatrixMode(GL11.GL_MODELVIEW)
                    GL11.glEnable(GL11.GL_LIGHTING)
                    GL11.glDisable(GL11.GL_BLEND)
                }
            }
        }
        -1
    }

    @SideOnly(Side.CLIENT)
    override def getColorMultiplier(entity: IEntitySoulCustom, brightness: Float, partialTickTime: Float): Int = {
        val explodes = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneAICreeperSwell)

        if(explodes) {
            val flashIntensity: Float = entity.getInteger(EntityTimeSinceIgnited).toFloat + (entity.getInteger(EntityTimeSinceIgnited) - entity.getInteger(EntityTimeSinceIgnited).toFloat * partialTickTime) / (entity.getInteger(EntityFuseTime) - 2).toFloat

            if((flashIntensity * 10.0F).toInt % 2 == 0) {
                return 0
            }
            else {
                var i: Int = (flashIntensity * 0.2F * 255.0F).toInt
                if(i < 0) {
                    i = 0
                }
                if(i > 255) {
                    i = 255
                }
                val short1: Short = 255
                val short2: Short = 255
                val short3: Short = 255
                return i << 24 | short1 << 16 | short2 << 8 | short3
            }
        }
        0
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        val dX = living.posX - living.prevPosX
        val dZ = living.posZ - living.prevPosZ
        val distSq = (dX * dX + dZ * dZ).toFloat
        var yawOffset = living.renderYawOffset
        var f2 = 0.0F
        entity.setFloat(EntityPrevOnGroundSpeedFactor, entity.getFloat(EntityOnGroundSpeedFactor))
        var f3 = 0.0F

        if(distSq > 0.0025000002F) {
            f3 = 1.0F
            f2 = Math.sqrt(distSq.toDouble).toFloat * 3.0F
            yawOffset = Math.atan2(dZ, dX).toFloat * 180.0F / Math.PI.toFloat - 90.0F
        }

        if(living.swingProgress > 0.0F) {
            yawOffset = living.rotationYaw
        }

        if(!living.onGround) {
            f3 = 0.0F
        }

        entity.setFloat(EntityOnGroundSpeedFactor, entity.getFloat(EntityOnGroundSpeedFactor) + (f3 - entity.getFloat(EntityOnGroundSpeedFactor)) * 0.3F)

        living.worldObj.theProfiler.startSection("headTurn")

        f2 = entity.func_110146_f_I(yawOffset, f2)

        living.worldObj.theProfiler.endSection()

        living.worldObj.theProfiler.startSection("rangeChecks")

        while(living.rotationYaw - living.prevRotationYaw < -180.0F) {
            living.prevRotationYaw -= 360.0F
        }

        while(living.rotationYaw - living.prevRotationYaw >= 180.0F) {
            living.prevRotationYaw += 360.0F
        }

        while(living.renderYawOffset - living.prevRenderYawOffset < -180.0F) {
            living.prevRenderYawOffset -= 360.0F
        }

        while(living.renderYawOffset - living.prevRenderYawOffset >= 180.0F) {
            living.prevRenderYawOffset += 360.0F
        }

        while(living.rotationPitch - living.prevRotationPitch < -180.0F) {
            living.prevRotationPitch -= 360.0F
        }

        while(living.rotationPitch - living.prevRotationPitch >= 180.0F) {
            living.prevRotationPitch += 360.0F
        }

        while(living.rotationYawHead - living.prevRotationYawHead < -180.0F) {
            living.prevRotationYawHead -= 360.0F
        }

        while(living.rotationYawHead - living.prevRotationYawHead >= 180.0F) {
            living.prevRotationYawHead += 360.0F
        }

        living.worldObj.theProfiler.endSection()

        entity.setFloat(EntityMovedDistance, entity.getFloat(EntityMovedDistance) + f2)

        entity.updateArmSwingProgress_I()

        val burnsInDaylight = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneBurnsInDaylight)

        if(living.getBrightness(1.0F) > 0.5F && burnsInDaylight) {
            entity.setInteger(EntityEntityAge, entity.getInteger(EntityEntityAge) + 2)
        }
    }
}
