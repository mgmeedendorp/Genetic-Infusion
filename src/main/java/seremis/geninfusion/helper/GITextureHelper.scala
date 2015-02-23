package seremis.geninfusion.helper

import java.awt.image.BufferedImage
import java.io._
import javax.imageio.ImageIO

import net.minecraft.util.ResourceLocation

import scala.util.Random

object GITextureHelper {

    val rand = new Random

    def getBufferedImage(location: String): BufferedImage = {
        try {
            return ImageIO.read(getFile(location))
        } catch {
            case e: IOException => e.printStackTrace()
        }
        null
    }

    def mergeTextures(texture1: String, texture2: String): BufferedImage = {
        val image1 = getBufferedImage(texture1)
        val image2 = getBufferedImage(texture2)

        null
    }

    def writeBufferedImage(image: BufferedImage, location: String) {
        ImageIO.write(image, "PNG", getFile(location))
    }

    def getFile(location: String): File = {
        val resource = new ResourceLocation(location)
        new File(getClass.getResource("/assets/" + resource.getResourceDomain + "/" + resource.getResourcePath).getPath)
    }
}
