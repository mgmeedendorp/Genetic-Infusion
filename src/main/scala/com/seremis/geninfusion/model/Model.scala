package com.seremis.geninfusion.model

import java.awt.image.BufferedImage

import com.seremis.geninfusion.api.model.animation.IAnimator
import com.seremis.geninfusion.api.model.{IModel, IModelPart, ITexturedRect}
import com.seremis.geninfusion.util.TextureHelper
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

import scala.collection.mutable.ArrayBuffer

class Model(parts: Array[IModelPart], textureLocation: Option[String] = None) extends IModel {

    lazy val texture = generateTexture(textureLocation)

    override def render(): Unit = render(1F/16F)

    override def render(scale: Float): Unit = {
        getAnimator.animate()

        GL11.glPushMatrix()

        GL11.glScalef(scale, scale, scale)

        for(part <- parts) {
            part.render()
        }
        GL11.glPopMatrix()
    }

    def getTexturedRects: Array[ITexturedRect] = {
        val list: ArrayBuffer[ITexturedRect] = ArrayBuffer()

        for(part <- parts) {
            for(cuboid <- part.getCuboids) {
                for(rect <- cuboid.getTexturedRects) {
                    list += rect
                }
            }
        }

        list.toArray
    }

    def setTextureSize(width: Int, height: Int) {
        for(part <- parts) {
            for(cuboid <- part.getCuboids) {
                for(rect <- cuboid.getTexturedRects) {
                    rect.setDestTextureWidth(width)
                    rect.setDestTextureHeight(height)
                }
            }
        }
    }

    def generateTexture(textureLocation: Option[String]): BufferedImage = {
        if(textureLocation.isEmpty) {
            val rects = getTexturedRects
            val textureSize = TextureHelper.populateTexturedRectsDestination(rects)

            setTextureSize(textureSize._1, textureSize._2)

            TextureHelper.stitchTexturedRects(rects, textureSize)
        } else {
            val texture = TextureHelper.getBufferedImage(new ResourceLocation(textureLocation.get))

            setTextureSize(texture.getWidth, texture.getHeight)

            texture
        }
    }

    override def getTexture: BufferedImage = texture

    override def copy() = new Model(parts)

    override def getModelParts = parts

    override def mutate() = ???

    override def getAnimator: IAnimator = ???

    override def resetInitialValues() {
        for(part <- parts) {
            part.resetInitialValues()
        }
    }

}
