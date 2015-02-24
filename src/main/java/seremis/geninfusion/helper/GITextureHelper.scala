package seremis.geninfusion.helper

import java.awt.image.BufferedImage
import java.awt.{Graphics, Polygon}
import java.io._
import javax.imageio.ImageIO

import net.minecraft.util.ResourceLocation
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.lib.Localizations

import scala.util.Random

object GITextureHelper {

    val rand = new Random

    def getBufferedImage(location: ResourceLocation): BufferedImage = {
        try {
            return ImageIO.read(getClass.getResourceAsStream("/assets/" + location.getResourceDomain + "/" + location.getResourcePath))
        } catch {
            case e: IOException => e.printStackTrace()
        }
        null
    }

    def mergeTextures(location1: ResourceLocation, location2: ResourceLocation, fileName: String): BufferedImage = {
        val image1 = getBufferedImage(location1)
        val image2 = getBufferedImage(location2)
        val result = new BufferedImage(image1.getWidth, image1.getHeight, BufferedImage.TYPE_INT_ARGB)

        val graphics = result.createGraphics()
        graphics.drawImage(image1, 0, 0, null)

        for(i <- 0 until rand.nextInt(50) + 5) {
            val nrCorners = rand.nextInt(60)
            val startX = result.getWidth * rand.nextFloat()
            val startY = result.getHeight * rand.nextFloat()
            val sizeX = rand.nextInt(((result.getWidth - startX)*rand.nextFloat + 1).asInstanceOf[Int])
            val sizeY = rand.nextInt(((result.getHeight - startY)*rand.nextFloat + 1).asInstanceOf[Int])

            addPolygon(graphics, image2, nrCorners, startX.asInstanceOf[Int], startY.asInstanceOf[Int], sizeX, sizeY)
        }

        graphics.dispose

        writeBufferedImage(result, new ResourceLocation(Localizations.LOC_ENTITY_CUSTOM_TEXTURES + fileName))

        result
    }

    def writeBufferedImage(image: BufferedImage, location: ResourceLocation) {
        val file = getFile(location)

        if(!file.exists())
            file.mkdirs

        ImageIO.write(image, "PNG", getFile(location))
    }

    def getFile(location: ResourceLocation): File = {
        new File(new File(new File(new File(GeneticInfusion.getClass.getProtectionDomain.getCodeSource.getLocation.toURI.getPath).getParent).getParent).getParent, "/assets/" + location.getResourceDomain + "/" + location.getResourcePath)
    }

    def addPolygon(graphics: Graphics, overlay: BufferedImage, numberOfCorners: Int, startX: Int, startY: Int, sizeX: Int, sizeY: Int): Unit = {
        val polygon = new Polygon()

        for(i <- 0 until numberOfCorners) {
            polygon.addPoint((startX + sizeX * Math.cos(i * 2 * Math.PI / numberOfCorners)).asInstanceOf[Int], (startY + sizeY * Math.sin(i * 2 * Math.PI / numberOfCorners)).asInstanceOf[Int])
        }

        graphics.setClip(polygon)
        graphics.drawImage(overlay, 0, 0, null)
        graphics.setClip(null)
    }

    def deleteTexture(location: ResourceLocation): Unit = {
        val file = getFile(location)

        file.delete()
    }
}
