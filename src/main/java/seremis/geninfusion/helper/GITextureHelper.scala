package seremis.geninfusion.helper

import java.awt.image.BufferedImage
import java.awt.{Graphics, Polygon}
import java.io._
import javafx.geometry.Rectangle2D
import javax.imageio.ImageIO

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.ResourceLocation
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.soul.entity.animation.AnimationCache

import scala.collection.mutable.ListBuffer
import scala.util.Random

import scala.collection.JavaConversions._

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

    //model1 HAS TO BE THE DOMINANT ALLELE!
    def mergeModelTextures(model1: Array[ModelPart], texture1: BufferedImage, model2: Array[ModelPart], texture2: BufferedImage): BufferedImage = {
        val time = System.currentTimeMillis()

        var modelPartImages: ListBuffer[BufferedImage] = ListBuffer()
        var textureRectsSource: ListBuffer[Rectangle2D] = ListBuffer()

        var model1PartArrayList: ListBuffer[Array[ModelPart]] = ListBuffer()
        var model2PartArrayList: ListBuffer[Array[ModelPart]] = ListBuffer()

        val model1Head = AnimationCache.getModelHead(model1)
        val model2Head = AnimationCache.getModelHead(model2)
        val model1Body = AnimationCache.getModelBody(model1)
        val model2Body = AnimationCache.getModelBody(model2)
        val model1Arms = AnimationCache.getModelArms(model1)
        val model2Arms = AnimationCache.getModelArms(model2)
        val model1Legs = AnimationCache.getModelLegs(model1)
        val model2Legs = AnimationCache.getModelLegs(model2)
        val model1Wings = AnimationCache.getModelWings(model1)
        val model2Wings = AnimationCache.getModelWings(model2)

        model1PartArrayList += model1Head
        model1PartArrayList += Array(model1Body)
        model1PartArrayList += model1Arms
        model1PartArrayList += model1Legs
        model1PartArrayList += model1Wings

        model2PartArrayList += model2Head
        model2PartArrayList += Array(model2Body)
        model2PartArrayList += model2Arms
        model2PartArrayList += model2Legs
        model2PartArrayList += model2Wings

        for(i <- 0 until Math.min(model1PartArrayList.length, model2PartArrayList.length)) {
            for(j <- 0 until Math.min(model1PartArrayList.get(i).length, model2PartArrayList.get(i).length)) {
                val model1PartImage = getModelPartTexture(model1PartArrayList.get(i)(j), texture1)
                val model2PartImage = getModelPartTexture(model2PartArrayList.get(i)(j), texture2)

                val image = mergeImages(model1PartImage, model2PartImage)

                val imageSize = (image.getWidth, image.getHeight)

                textureRectsSource += new Rectangle2D(0, 0, imageSize._1, imageSize._2)
                modelPartImages += image
            }
        }

        val textureRectsDest = distributeSquaresOnTexture(textureRectsSource)

        println("final texturesize: " + textureRectsDest._1)

        val finalImage = new BufferedImage(textureRectsDest._1._1, textureRectsDest._1._2, BufferedImage.TYPE_INT_ARGB)
        val graphics = finalImage.getGraphics

        for(i <- 0 until textureRectsDest._2.size) {
            val sourceRect = textureRectsSource.get(i)
            val sourceImage = modelPartImages.get(i)
            val destRect = textureRectsDest._2.get(i)

            graphics.drawImage(sourceImage, destRect.getMinX.toInt, destRect.getMinY.toInt, destRect.getMaxX.toInt, destRect.getMaxY.toInt, sourceRect.getMinX.toInt, sourceRect.getMinY.toInt, sourceRect.getMaxX.toInt, sourceRect.getMaxY.toInt, null)
        }

        graphics.dispose()

        for(i <- 0 until Math.min(model1PartArrayList.length, model2PartArrayList.length)) {
            for(j <- 0 until Math.min(model1PartArrayList.get(i).length, model2PartArrayList.get(i).length)) {
                val part = model1PartArrayList.get(i)(j)
                val destRect = textureRectsDest._2.get(i)

                moveModelPartTextureOffset(part, (destRect.getMinX.toInt, destRect.getMinY.toInt))
            }
        }

        println("mergeModelTextures Time: " + (System.currentTimeMillis() - time))

        finalImage
    }

    def moveModelPartTextureOffset(part: ModelPart, textureOffset: (Int, Int)): ModelPart = {
        for(box <- part.getBoxList) {
            val texturedQuads = part.getBoxQuads(box)

            for(quad <- texturedQuads) {
                var quadPositionsMin = (quad.vertexPositions(1).texturePositionX, quad.vertexPositions(1).texturePositionY)
                var quadPositionsMax = (quad.vertexPositions(3).texturePositionX, quad.vertexPositions(3).texturePositionY)

                var quadCoordsMin = (part.textureWidth * quadPositionsMin._1, part.textureHeight * quadPositionsMin._2)
                var quadCoordsMax = (part.textureWidth * quadPositionsMax._1, part.textureHeight * quadPositionsMax._2)

                val quadSize = (Math.abs(quadCoordsMax._1 - quadCoordsMin._1).toInt, Math.abs(quadCoordsMax._2 - quadCoordsMin._2).toInt)

                quadCoordsMin = (quadCoordsMin._1 + textureOffset._1, quadCoordsMin._2 + textureOffset._2)
                quadCoordsMax = (quadCoordsMax._1 + textureOffset._1, quadCoordsMax._2 + textureOffset._2)

                quadPositionsMin = (quadCoordsMin._1/part.textureWidth, quadCoordsMin._2/part.textureHeight)
                quadPositionsMax = ((quadCoordsMax._1 + quadSize._1)/part.textureWidth, (quadCoordsMax._2 + quadSize._2)/part.textureHeight)

                quad.vertexPositions(0) = quad.vertexPositions(0).setTexturePosition(quadPositionsMax._1, quadPositionsMin._2)
                quad.vertexPositions(1) = quad.vertexPositions(1).setTexturePosition(quadPositionsMin._1, quadPositionsMin._2)
                quad.vertexPositions(2) = quad.vertexPositions(2).setTexturePosition(quadPositionsMin._1, quadPositionsMax._2)
                quad.vertexPositions(3) = quad.vertexPositions(3).setTexturePosition(quadPositionsMax._1, quadPositionsMax._2)
            }
        }
        part
    }

    /**
     * Creates a new BufferedImage with only the texture of the ModelPart part from the provided BufferedImage image with the texture of the whole model.
     * @param part The ModelPart from which the texture should be returned.
     * @param image The image file of the model that the ModelPart is part of.
     * @return A section of the passed BufferedImage with the passed ModelPart's texture.
     */
    def getModelPartTexture(part: ModelPart, image: BufferedImage): BufferedImage = {
        val time = System.currentTimeMillis()

        var textureRectsSource: ListBuffer[Rectangle2D] = ListBuffer()

        for(box <- part.getBoxList) {
            val texturedQuads = part.getBoxQuads(box)

            for(quad <- texturedQuads) {
                val quadPositionsMin = (quad.vertexPositions(1).texturePositionX, quad.vertexPositions(1).texturePositionY)
                val quadPositionsMax = (quad.vertexPositions(3).texturePositionX, quad.vertexPositions(3).texturePositionY)

                val quadCoordsMin = (part.textureWidth * quadPositionsMin._1, part.textureHeight * quadPositionsMin._2)
                val quadCoordsMax = (part.textureWidth * quadPositionsMax._1, part.textureHeight * quadPositionsMax._2)

                val quadCoords = (Math.min(quadCoordsMax._1, quadCoordsMin._1).toInt, Math.min(quadCoordsMax._2, quadCoordsMin._2).toInt)
                val quadSize = (Math.abs(quadCoordsMax._1 - quadCoordsMin._1).toInt, Math.abs(quadCoordsMax._2 - quadCoordsMin._2).toInt)
                
                textureRectsSource += new Rectangle2D(quadCoords._1, quadCoords._2, quadSize._1, quadSize._2)
            }
        }

        val textureRectsDest = distributeSquaresOnTexture(textureRectsSource)

        val finalImage = new BufferedImage(textureRectsDest._1._1, textureRectsDest._1._2, BufferedImage.TYPE_INT_ARGB)
        val graphics = finalImage.getGraphics

        for(i <- 0 until textureRectsDest._2.size) {
            val sourceRect = textureRectsSource.get(i)
            val destRect = textureRectsDest._2.get(i)

            graphics.drawImage(image, destRect.getMinX.toInt, destRect.getMinY.toInt, destRect.getMaxX.toInt, destRect.getMaxY.toInt, sourceRect.getMinX.toInt, sourceRect.getMinY.toInt, sourceRect.getMaxX.toInt, sourceRect.getMaxY.toInt, null)
        }

        graphics.dispose()

        println("getModelPartTexture Time: " + (System.currentTimeMillis() - time))

        finalImage
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

            if(position._1 >= textureSize._1) {
                position = (0, rowMaxY)
            }

            if(position._2 >= textureSize._2) {
                textureSize = (textureSize._1, textureSize._2 * 2)
            }

            rowMaxY = Math.max(rowMaxY, newRect.getMaxY).toInt

            resultList += newRect
        }

        if(rowMaxY >= textureSize._2) {
            textureSize = (textureSize._1, textureSize._2 * 2)
        }

        println("textureSize: " + textureSize)

        (textureSize, resultList)
    }

    /**
     * Merge two images. This overlays one image with random parts of the other.
     * @param image1 One of the images to be merged.
     * @param image2 One of the images to be merged.
     * @return The merged BufferedImage.
     */
    def mergeImages(image1: BufferedImage, image2: BufferedImage): BufferedImage = {
        val time = System.currentTimeMillis()

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

        println("mergeImages Time: " + (System.currentTimeMillis() - time))

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
        new File(new File(new File(new File(GeneticInfusion.getClass.getProtectionDomain.getCodeSource.getLocation.toURI.getPath).getParent).getParent).getParent, "/assets/" + location.getResourceDomain + "/" + location.getResourcePath)
    }

    /**
     * Deletes a texture from disk.
     * @param location The location the file to delete is stored at.
     */
    def deleteTexture(location: ResourceLocation): Unit = {
        try {
            val file = getFile(location)

            file.delete()
        } catch {
            case e: Exception => e.printStackTrace()
        }
    }
}
