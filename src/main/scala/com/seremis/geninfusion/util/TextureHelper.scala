package com.seremis.geninfusion.util

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

import com.seremis.geninfusion.api.model.ITexturedRect
import net.minecraft.util.ResourceLocation

import scala.collection.mutable.HashMap

object TextureHelper {

    val imageCache: HashMap[ResourceLocation, BufferedImage] = HashMap()

    /**
      * Get BufferedImage from disk, location specified by ResourceLocation.
      *
      * @param location The location the image is stored at.
      * @return The image found at the specified location.
      */
    def getBufferedImage(location: ResourceLocation): BufferedImage = {
        if(!imageCache.contains(location)) {
            try {
                imageCache += (location -> ImageIO.read(getClass.getResourceAsStream("/assets/" + location.getResourceDomain + "/" + location.getResourcePath)))
            } catch {
                case e: Exception => e.printStackTrace()
            }
        }

        imageCache.get(location).get
    }

    /**
      * Creates a new BufferedImage with the texture of every ITexturedRect in rects. The destination coordinates
      * of the textured rects needs to be populated.
      * @param rects The rectangles that need to be stitched to a BufferedImage.
      * @param textureSize A tuple with the (width, height) of the returned BufferedImage.
      * @return A new BufferedImage with the textures of every ITexturedRect in rects.
      */
    def stitchTexturedRects(rects: Array[ITexturedRect], textureSize: (Int, Int)): BufferedImage = {
        val result = new BufferedImage(textureSize._1, textureSize._2, BufferedImage.TYPE_INT_ARGB)
        val graphics = result.getGraphics

        for(rect <- rects) {
            val srcX1 = rect.getSrcX
            val srcY1 = rect.getSrcY
            val srcX2 = rect.getSrcX + rect.getWidth
            val srcY2 = rect.getSrcY + rect.getHeight

            val destX1 = rect.getDestX
            val destY1 = rect.getDestY
            val destX2 = rect.getDestX + rect.getWidth
            val destY2 = rect.getDestY + rect.getHeight

            graphics.drawImage(rect.getTexture, destX1, destY1, destX2, destY2, srcX1, srcY1, srcX2, srcY2, null)
        }
        graphics.dispose()

        result
    }

    /**
      * Rounds i up to the nearest power of two.
      * @param i A number.
      * @return The nearest power of two that is bigger than i.
      */
    def wrapPow2(i: Int): Int = {
        var n = i

        n -= 1
        n |= n >> 1
        n |= n >> 2
        n |= n >> 4
        n |= n >> 8
        n |= n >> 16
        n += 1

        n
    }

    /**
      * Populates the destination coordinates of the ITexturedRectangles in rects.
      * @param rects An array of ITexturedRect. Their destination coordinates will be set.
      * @param maxWidth The maximum width of the texture. Defaults to 64 px.
      * @return The required texture size (rounded up to the nearest power of 2) to fit all rectangles.
      */
    def populateTexturedRectsDestination(rects: Array[ITexturedRect], maxWidth: Int = 64): (Int, Int) = {
        var x = 0
        var y = 0
        var maxY = 0

        for(rect <- rects) {
            if(x + rect.getWidth > maxWidth) {
                x = 0
                y = maxY
            }

            if(y + rect.getHeight > maxY) {
                maxY = y + rect.getHeight
            }

            rect.setDestX(x)
            rect.setDestY(y)

            x = x + rect.getWidth
            y = y + rect.getHeight
        }

        (maxWidth, wrapPow2(y))
    }
}
