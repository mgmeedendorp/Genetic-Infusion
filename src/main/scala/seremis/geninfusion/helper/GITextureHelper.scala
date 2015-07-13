package seremis.geninfusion.helper

import java.awt.image.BufferedImage
import java.awt.{Graphics, Polygon}
import java.io._
import javafx.geometry.Rectangle2D
import javax.imageio.ImageIO

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.model.TexturedQuad
import net.minecraft.util.ResourceLocation
import seremis.geninfusion.api.util.render.model.ModelPart

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import scala.util.Random

@SideOnly(Side.CLIENT)
object GITextureHelper {

    val rand = new Random

    /**
     * Get BufferedImage from disk, location specified by ResourceLocation.
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

    def stitchImages(rects: ListBuffer[Rectangle2D], images: ListBuffer[BufferedImage]): (BufferedImage, ListBuffer[Rectangle2D]) = {
        val textureRectsDest = distributeSquaresOnTexture(rects)

        val finalImage = new BufferedImage(textureRectsDest._1._1, textureRectsDest._1._2, BufferedImage.TYPE_INT_ARGB)
        val graphics = finalImage.getGraphics

        for(i <- 0 until textureRectsDest._2.size) {
            val sourceRect = rects.get(i)
            val sourceImage = images.get(i)
            val destRect = textureRectsDest._2.get(i)

            graphics.drawImage(sourceImage, destRect.getMinX.toInt, destRect.getMinY.toInt, destRect.getMaxX.toInt, destRect.getMaxY.toInt, sourceRect.getMinX.toInt, sourceRect.getMinY.toInt, sourceRect.getMaxX.toInt, sourceRect.getMaxY.toInt, null)
        }

        graphics.dispose()

        (finalImage, textureRectsDest._2)
    }

    def moveModelPartTextureOffset(part: ModelPart, textureOffset: (Int, Int)): ModelPart = {
        if(!textureOffset.equals((0, 0))) {
            for(box <- part.getBoxList) {
                for(quad <- part.getBoxQuads(box)) {
                    moveModelBoxQuadTextureOffset(part, quad, textureOffset)
                }
            }
        }
        part
    }

    def moveModelBoxQuadTextureOffset(part: ModelPart, quad: TexturedQuad, textureOffset: (Int, Int)): TexturedQuad = {
        var quadPositionsMin = (Math.min(Math.min(quad.vertexPositions(0).texturePositionX, quad.vertexPositions(1).texturePositionX), Math.min(quad.vertexPositions(2).texturePositionX, quad.vertexPositions(3).texturePositionX)), Math.min(Math.min(quad.vertexPositions(1).texturePositionY, quad.vertexPositions(2).texturePositionY), Math.min(quad.vertexPositions(2).texturePositionY, quad.vertexPositions(3).texturePositionY)))
        var quadPositionsMax = (Math.max(Math.max(quad.vertexPositions(0).texturePositionX, quad.vertexPositions(1).texturePositionX), Math.max(quad.vertexPositions(2).texturePositionX, quad.vertexPositions(3).texturePositionX)), Math.max(Math.max(quad.vertexPositions(1).texturePositionY, quad.vertexPositions(2).texturePositionY), Math.max(quad.vertexPositions(2).texturePositionY, quad.vertexPositions(3).texturePositionY)))

        var quadCoordsMin = (part.textureWidth * quadPositionsMin._1, part.textureHeight * quadPositionsMin._2)
        var quadCoordsMax = (part.textureWidth * quadPositionsMax._1, part.textureHeight * quadPositionsMax._2)

        quadCoordsMin = (quadCoordsMin._1 + textureOffset._1, quadCoordsMin._2 + textureOffset._2)
        quadCoordsMax = (quadCoordsMax._1 + textureOffset._1, quadCoordsMax._2 + textureOffset._2)

        quadPositionsMin = (quadCoordsMin._1 / part.textureWidth, quadCoordsMin._2 / part.textureHeight)
        quadPositionsMax = (quadCoordsMax._1 / part.textureWidth, quadCoordsMax._2 / part.textureHeight)

        quad.vertexPositions(0) = quad.vertexPositions(0).setTexturePosition(quadPositionsMax._1, quadPositionsMin._2)
        quad.vertexPositions(1) = quad.vertexPositions(1).setTexturePosition(quadPositionsMin._1, quadPositionsMin._2)
        quad.vertexPositions(2) = quad.vertexPositions(2).setTexturePosition(quadPositionsMin._1, quadPositionsMax._2)
        quad.vertexPositions(3) = quad.vertexPositions(3).setTexturePosition(quadPositionsMax._1, quadPositionsMax._2)

        quad
    }

    def setModelBoxQuadTextureOffset(part: ModelPart, quad: TexturedQuad, textureOffset: (Int, Int)): TexturedQuad = {
        var quadPositionsMin = (Math.min(Math.min(quad.vertexPositions(0).texturePositionX, quad.vertexPositions(1).texturePositionX), Math.min(quad.vertexPositions(2).texturePositionX, quad.vertexPositions(3).texturePositionX)), Math.min(Math.min(quad.vertexPositions(1).texturePositionY, quad.vertexPositions(2).texturePositionY), Math.min(quad.vertexPositions(2).texturePositionY, quad.vertexPositions(3).texturePositionY)))
        var quadPositionsMax = (Math.max(Math.max(quad.vertexPositions(0).texturePositionX, quad.vertexPositions(1).texturePositionX), Math.max(quad.vertexPositions(2).texturePositionX, quad.vertexPositions(3).texturePositionX)), Math.max(Math.max(quad.vertexPositions(1).texturePositionY, quad.vertexPositions(2).texturePositionY), Math.max(quad.vertexPositions(2).texturePositionY, quad.vertexPositions(3).texturePositionY)))

        var quadCoordsMin = (part.textureWidth * quadPositionsMin._1, part.textureHeight * quadPositionsMin._2)
        var quadCoordsMax = (part.textureWidth * quadPositionsMax._1, part.textureHeight * quadPositionsMax._2)

        val quadSize = (Math.abs(quadCoordsMax._1 - quadCoordsMin._1).toInt, Math.abs(quadCoordsMax._2 - quadCoordsMin._2).toInt)

        quadCoordsMin = (textureOffset._1, textureOffset._2)
        quadCoordsMax = (textureOffset._1 + quadSize._1, textureOffset._2 + quadSize._2)

        quadPositionsMin = (quadCoordsMin._1 / part.textureWidth, quadCoordsMin._2 / part.textureHeight)
        quadPositionsMax = (quadCoordsMax._1 / part.textureWidth, quadCoordsMax._2 / part.textureHeight)

        quad.vertexPositions(0) = quad.vertexPositions(0).setTexturePosition(quadPositionsMax._1, quadPositionsMin._2)
        quad.vertexPositions(1) = quad.vertexPositions(1).setTexturePosition(quadPositionsMin._1, quadPositionsMin._2)
        quad.vertexPositions(2) = quad.vertexPositions(2).setTexturePosition(quadPositionsMin._1, quadPositionsMax._2)
        quad.vertexPositions(3) = quad.vertexPositions(3).setTexturePosition(quadPositionsMax._1, quadPositionsMax._2)

        quad
    }

    /**
     * Creates a new BufferedImage with only the texture of the ModelPart part from the provided BufferedImage image with the texture of the whole model.
     * @param part The ModelPart from which the texture should be returned.
     * @param image The image file of the model that the ModelPart is part of.
     * @return A section of the passed BufferedImage with the passed ModelPart's texture.
     */
    def getModelPartTexture(part: ModelPart, image: BufferedImage): BufferedImage = {
        var textureRectsSource: ListBuffer[Rectangle2D] = ListBuffer()
        var texturedQuadsList: ListBuffer[TexturedQuad] = ListBuffer()


        for(box <- part.getBoxList) {
            val texturedQuads = part.getBoxQuads(box)

            for(quad <- texturedQuads) {
                val quadPositionsMin = (Math.min(Math.min(quad.vertexPositions(0).texturePositionX, quad.vertexPositions(1).texturePositionX), Math.min(quad.vertexPositions(2).texturePositionX, quad.vertexPositions(3).texturePositionX)), Math.min(Math.min(quad.vertexPositions(1).texturePositionY, quad.vertexPositions(2).texturePositionY), Math.min(quad.vertexPositions(2).texturePositionY, quad.vertexPositions(3).texturePositionY)))
                val quadPositionsMax = (Math.max(Math.max(quad.vertexPositions(0).texturePositionX, quad.vertexPositions(1).texturePositionX), Math.max(quad.vertexPositions(2).texturePositionX, quad.vertexPositions(3).texturePositionX)), Math.max(Math.max(quad.vertexPositions(1).texturePositionY, quad.vertexPositions(2).texturePositionY), Math.max(quad.vertexPositions(2).texturePositionY, quad.vertexPositions(3).texturePositionY)))

                val quadCoordsMin = (part.textureWidth * quadPositionsMin._1, part.textureHeight * quadPositionsMin._2)
                val quadCoordsMax = (part.textureWidth * quadPositionsMax._1, part.textureHeight * quadPositionsMax._2)

                val quadCoords = (Math.min(quadCoordsMax._1, quadCoordsMin._1).toInt, Math.min(quadCoordsMax._2, quadCoordsMin._2).toInt)
                val quadSize = (Math.abs(quadCoordsMax._1 - quadCoordsMin._1).toInt, Math.abs(quadCoordsMax._2 - quadCoordsMin._2).toInt)

                textureRectsSource += new Rectangle2D(quadCoords._1, quadCoords._2, quadSize._1, quadSize._2)
                texturedQuadsList += quad
            }
        }

        val result = stitchImages(textureRectsSource, ListBuffer.fill(textureRectsSource.length)(image))

        changeModelPartTextureSize(part, (result._1.getWidth, result._1.getHeight))

        for(i <- 0 until result._2.size) {
            val rect = result._2.get(i)
            val quad = texturedQuadsList.get(i)

            setModelBoxQuadTextureOffset(part, quad, (rect.getMinX.toInt, rect.getMinY.toInt))
        }

        result._1
    }

    def changeModelPartTextureSize(part: ModelPart, newSize: (Int, Int)): ModelPart = {
        val oldSize = (part.textureWidth, part.textureHeight)
        part.setTextureSize(newSize._1, newSize._2)

        if(!(oldSize._1 == newSize._1 && oldSize._2 == newSize._2)) {
            for(box <- part.getBoxList) {
                val texturedQuads = part.getBoxQuads(box)

                for(quad <- texturedQuads) {
                    var quadPositionsMin = (Math.min(Math.min(quad.vertexPositions(0).texturePositionX, quad.vertexPositions(1).texturePositionX), Math.min(quad.vertexPositions(2).texturePositionX, quad.vertexPositions(3).texturePositionX)), Math.min(Math.min(quad.vertexPositions(1).texturePositionY, quad.vertexPositions(2).texturePositionY), Math.min(quad.vertexPositions(2).texturePositionY, quad.vertexPositions(3).texturePositionY)))
                    var quadPositionsMax = (Math.max(Math.max(quad.vertexPositions(0).texturePositionX, quad.vertexPositions(1).texturePositionX), Math.max(quad.vertexPositions(2).texturePositionX, quad.vertexPositions(3).texturePositionX)), Math.max(Math.max(quad.vertexPositions(1).texturePositionY, quad.vertexPositions(2).texturePositionY), Math.max(quad.vertexPositions(2).texturePositionY, quad.vertexPositions(3).texturePositionY)))

                    var quadCoordsMin = (oldSize._1 * quadPositionsMin._1, oldSize._2 * quadPositionsMin._2)
                    var quadCoordsMax = (oldSize._1 * quadPositionsMax._1, oldSize._2 * quadPositionsMax._2)

                    quadCoordsMin = (quadCoordsMin._1, quadCoordsMin._2)
                    quadCoordsMax = (quadCoordsMax._1, quadCoordsMax._2)

                    quadPositionsMin = (quadCoordsMin._1 / part.textureWidth, quadCoordsMin._2 / part.textureHeight)
                    quadPositionsMax = (quadCoordsMax._1 / part.textureWidth, quadCoordsMax._2 / part.textureHeight)

                    if(!part.mirror) {
                        quad.vertexPositions(0) = quad.vertexPositions(0).setTexturePosition(quadPositionsMax._1, quadPositionsMin._2)
                        quad.vertexPositions(1) = quad.vertexPositions(1).setTexturePosition(quadPositionsMin._1, quadPositionsMin._2)
                        quad.vertexPositions(2) = quad.vertexPositions(2).setTexturePosition(quadPositionsMin._1, quadPositionsMax._2)
                        quad.vertexPositions(3) = quad.vertexPositions(3).setTexturePosition(quadPositionsMax._1, quadPositionsMax._2)
                    } else {
                        quad.vertexPositions(0) = quad.vertexPositions(0).setTexturePosition(quadPositionsMax._1, quadPositionsMin._2)
                        quad.vertexPositions(1) = quad.vertexPositions(1).setTexturePosition(quadPositionsMax._1, quadPositionsMax._2)
                        quad.vertexPositions(2) = quad.vertexPositions(2).setTexturePosition(quadPositionsMin._1, quadPositionsMax._2)
                        quad.vertexPositions(3) = quad.vertexPositions(3).setTexturePosition(quadPositionsMin._1, quadPositionsMin._2)
                    }
                }
            }
        }
        part
    }

    def distributeSquaresOnTexture(usedRects: ListBuffer[Rectangle2D]): ((Int, Int), ListBuffer[Rectangle2D]) = {
        var textureSize = (16, 16)
        var position: (Int, Int) = (0, 0)
        var rowMaxY = 0

        var resultList: ListBuffer[Rectangle2D] = ListBuffer()

        for(rect <- usedRects) {
            val newRect = new Rectangle2D(position._1, position._2, rect.getWidth, rect.getHeight)

            if(newRect.getWidth.toInt > textureSize._1) {
                textureSize = (textureSize._1 * 2, textureSize._2)
            }

            position = (position._1 + newRect.getWidth.toInt, position._2)

            rowMaxY = Math.max(rowMaxY, newRect.getMaxY).toInt

            if(position._1 >= textureSize._1) {
                position = (0, rowMaxY)
            }

            if(position._2 >= textureSize._2) {
                textureSize = (textureSize._1, textureSize._2 * 2)
            }

            resultList += newRect
        }

        if(rowMaxY >= textureSize._2) {
            textureSize = (textureSize._1, textureSize._2 * 2)
        }

        (textureSize, resultList)
    }

    /**
     * Merge two images. This overlays one image with random parts of the other.
     * @param image1 One of the images to be merged.
     * @param image2 One of the images to be merged.
     * @return The merged BufferedImage.
     */
    def mergeImages(image1: BufferedImage, image2: BufferedImage): BufferedImage = {
        val result = new BufferedImage(Math.max(image1.getWidth, image2.getWidth), Math.max(image1.getHeight, image2.getHeight), BufferedImage.TYPE_INT_ARGB)

        val graphics = result.createGraphics()
        graphics.drawImage(image2, 0, 0, null)
        graphics.drawImage(image1, 0, 0, null)

        for(i <- 0 until rand.nextInt(10) + 5) {
            val nrCorners = rand.nextInt(10)
            val startX = result.getWidth * rand.nextFloat()
            val startY = result.getHeight * rand.nextFloat()
            val sizeX = rand.nextInt(((result.getWidth - startX) * rand.nextFloat + 1).asInstanceOf[Int])
            val sizeY = rand.nextInt(((result.getHeight - startY) * rand.nextFloat + 1).asInstanceOf[Int])

            addPolygon(graphics, image2, nrCorners, startX.asInstanceOf[Int], startY.asInstanceOf[Int], sizeX, sizeY)
        }

        graphics.dispose()

        result
    }

    /**
     * Adds a polygon filled with the overlay BufferedImage to the grapics provided.
     * @param graphics The graphics to be overlayed.
     * @param overlay The image to draw to the graphics.
     * @param numberOfCorners The numbers of corners this specific polygon should have
     * @param startX The x coordinate on the graphics the overlay should start at.
     * @param startY The y coordinate on the graphics the overlay should start at.
     * @param sizeX The maximum size in the x direction this polygon can have.
     * @param sizeY The maximum size in the y direction this polygon can have.
     */
    def addPolygon(graphics: Graphics, overlay: BufferedImage, numberOfCorners: Int, startX: Int, startY: Int, sizeX: Int, sizeY: Int): Unit = {
        val polygon = new Polygon()

        for(i <- 0 until numberOfCorners) {
            polygon.addPoint((startX + sizeX * Math.cos(i * 2 * Math.PI / numberOfCorners)).asInstanceOf[Int], (startY + sizeY * Math.sin(i * 2 * Math.PI / numberOfCorners)).asInstanceOf[Int])
        }

        graphics.setClip(polygon)
        graphics.drawImage(overlay, 0, 0, null)
        graphics.setClip(null)
    }

    /**
     * Writes a BufferedImage to disk, the location is provided through a ResourceLocation.
     * @param image The BufferedImage to write to disk.
     * @param location The location the image should be stored at.
     */
    def writeBufferedImage(image: BufferedImage, location: ResourceLocation) {
        try {
            val file = getFile(location)

            if(!file.exists())
                file.mkdirs

            ImageIO.write(image, "PNG", getFile(location))
        } catch {
            case e: Exception => e.printStackTrace()
        }
    }

    /**
     * Get a file from the assets, using a ResourceLocation.
     * @param location The ResourceLocation of the asset to get.
     * @return A File object with the file at the specified location.
     */
    def getFile(location: ResourceLocation): File = {
        new File(getClass.getClassLoader.getResource("assets/" + location.getResourceDomain + "/" + location.getResourcePath).getPath)
    }

    /**
     * Deletes a texture from disk.
     * @param location The location the file to delete is stored at.
     */
    def deleteTexture(location: ResourceLocation): Unit = {
        try {
            val file = getFile(location)

            if(file.exists())
                file.delete()
        } catch {
            case e: Exception => e.printStackTrace()
        }
    }
}
