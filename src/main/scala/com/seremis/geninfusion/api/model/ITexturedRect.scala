package com.seremis.geninfusion.api.model

import java.awt.image.BufferedImage

import com.seremis.geninfusion.api.util.INBTTagable
import net.minecraft.client.renderer.VertexBuffer
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

trait ITexturedRect extends INBTTagable {

    def getSrcX: Int
    def getSrcY: Int

    def getDestX: Int
    def getDestY: Int

    def setDestX(x: Int)
    def setDestY(y: Int)

    def getDestTextureWidth: Int
    def getDestTextureHeight: Int

    def setDestTextureWidth(width: Int)
    def setDestTextureHeight(height: Int)
    
    def getWidth: Int
    def getHeight: Int

    def getTextureLocation: String
    @SideOnly(Side.CLIENT)
    def getTexture: BufferedImage

    def draw(buffer: VertexBuffer)
}
