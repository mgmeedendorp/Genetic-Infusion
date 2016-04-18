package com.seremis.geninfusion.model

import java.awt.image.BufferedImage

import com.seremis.geninfusion.api.model.ITexturedRect
import com.seremis.geninfusion.util.TextureHelper
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.{Tessellator, VertexBuffer}
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.Vec3d
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

class TexturedRect(textureLocation: String, corners: Array[Vec3d], srcX: Int, srcY: Int, width: Int, height: Int) extends ITexturedRect {

    var destX = srcX
    var destY = srcY
    var destTextureWidth = 0
    var destTextureHeight = 0

    override def getSrcX: Int = srcX
    override def getSrcY: Int = srcY

    override def getDestX = destX
    override def getDestY = destY

    override def setDestX(x: Int) = destX = x
    override def setDestY(y: Int) = destY = y

    override def getDestTextureWidth = destTextureWidth
    override def getDestTextureHeight = destTextureHeight

    override def setDestTextureWidth(width: Int) = destTextureWidth = width
    override def setDestTextureHeight(height: Int) = destTextureHeight = height

    override def getWidth: Int = width
    override def getHeight: Int = height


    @SideOnly(Side.CLIENT)
    override def getTexture: BufferedImage = TextureHelper.getBufferedImage(new ResourceLocation(textureLocation))

    override def getTextureLocation: String = textureLocation

    override def draw(buffer: VertexBuffer) = {
        val vec1 = corners(1).subtract(corners(0))
        val vec2 = corners(1).subtract(corners(2))

        val normalVec = vec2.crossProduct(vec1).normalize()

        buffer.begin(7, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL)

        buffer.normal(normalVec.xCoord.toFloat, normalVec.yCoord.toFloat, normalVec.zCoord.toFloat)

        val u1 = destX / destTextureWidth
        val v1 = destY / destTextureHeight
        val u2 = (destX + width) / destTextureWidth
        val v2 = (destY + height) / destTextureHeight

        buffer.pos(corners(0).xCoord, corners(0).yCoord, corners(0).zCoord).tex(u1, v1)
        buffer.pos(corners(1).xCoord, corners(1).yCoord, corners(1).zCoord).tex(u1, v2)
        buffer.pos(corners(2).xCoord, corners(2).yCoord, corners(2).zCoord).tex(u2, v2)
        buffer.pos(corners(3).xCoord, corners(3).yCoord, corners(3).zCoord).tex(u2, v1)

        Tessellator.getInstance().draw()
    }
}
