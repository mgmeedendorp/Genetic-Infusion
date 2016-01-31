package seremis.geninfusion.api.util

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

import net.minecraft.util.ResourceLocation
import seremis.geninfusion.api.render.cuboid.CuboidTexturedRect

object ModelTextureHelper {

    def stitchTexturedRects(rects: Array[CuboidTexturedRect], textureSize: (Int, Int)): BufferedImage = {
        val result = new BufferedImage(textureSize._1, textureSize._2, BufferedImage.TYPE_INT_ARGB)
        val graphics = result.getGraphics

        for(rect <- rects) {
            graphics.drawImage(rect.getTexture, rect.destX, rect.destY, rect.destX + rect.sizeX, rect.destY + rect.sizeY, rect.x, rect.y, rect.x + rect.sizeX, rect.y + rect.sizeY, null)
        }
        graphics.dispose()

        result
    }

    def populateTexturedRectsDestination(rects: Array[CuboidTexturedRect]): (Int, Int) = {
        var currentPos = (0, 0)
        var currentRowMaxY = 0

        for(rect <- rects) {
            rect.setDestination(currentPos._1, currentPos._2)

            currentPos = (currentPos._1 + rect.sizeX, currentPos._2)

            if(currentRowMaxY < rect.sizeY + rect.y) {
                currentRowMaxY = rect.sizeY + rect.y
            }

            if(currentPos._1 > 64) {
                rect.setDestination(0, currentRowMaxY)
                currentPos = (rect.sizeX, currentRowMaxY)
            }

            if(currentPos._1 == 64) {
                currentPos = (0, currentRowMaxY)
            }

            currentPos
        }

        val textureSize = (wrapPow2(currentPos._1), wrapPow2(currentPos._2))

        textureSize
    }

    /**
      * Wraps
      *
      * @param i
      * @return
      */
    def wrapPow2(i: Int): Int = {
        var n = i

        n -= 1;
        n |= n >> 1
        n |= n >> 2
        n |= n >> 4
        n |= n >> 8
        n |= n >> 16
        n += 1

        n
    }

    /**
      * Get BufferedImage from disk, location specified by ResourceLocation.
      *
      * @param location The location the image is stored at.
      * @return The image found at the specified location.
      */
    def getBufferedImage(location: ResourceLocation): BufferedImage = {
        try {
            return ImageIO.read(getClass.getResourceAsStream("/assets/" + location.getResourceDomain + "/" + location.getResourcePath))
        } catch {
            case e: Exception => e.printStackTrace()
        }
        null
    }
}
