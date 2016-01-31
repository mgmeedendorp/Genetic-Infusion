package seremis.geninfusion.api.render

import java.awt.image.BufferedImage

import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.util.Constants
import org.lwjgl.opengl.GL11
import seremis.geninfusion.api.render.cuboid.{Cuboid, CuboidTexturedRect}
import seremis.geninfusion.api.util.ModelTextureHelper
import seremis.geninfusion.util.INBTTagable

import scala.collection.mutable.ListBuffer

class Model(var cuboids: Array[Cuboid]) extends INBTTagable {

    var texture: Option[BufferedImage] = None
    var textureSize: (Int, Int) = (0, 0)

    var texturedRectsCache: Option[Array[CuboidTexturedRect]] = None

    def this(compound: NBTTagCompound) {
        this(null.asInstanceOf[Array[Cuboid]])
        readFromNBT(compound)
    }

    def getCuboids = cuboids

    def getCuboidsWithTag(tags: String*): Option[Array[Cuboid]] = {
        val cuboidList: ListBuffer[Cuboid] = ListBuffer()

        for(tag <- tags) {
            for(cuboid <- cuboids) {
                if(cuboid.cuboidType.tags.contains(tag)) {
                    cuboidList += cuboid
                }
            }
        }

        if(cuboidList.isEmpty)
            return None

        Some(cuboidList.to[Array])
    }

    def setTextureLocation(location: String): Model = {
        val texture = ModelTextureHelper.getBufferedImage(new ResourceLocation(location))
        for(cuboid <- cuboids) {
            cuboid.setTextureLocation(location)
        }
        textureSize = (texture.getWidth, texture.getHeight)
        this
    }

    def render() {
        val scale = 1F/16F

        GL11.glScalef(scale, scale, scale)
        for(cuboid <- cuboids) {
            cuboid.render()
        }

    }

    def generateTexture() {
        if(texture.isEmpty) {
            texture = Some(ModelTextureHelper.stitchTexturedRects(getTexturedRects, textureSize))
        }
    }

    def getTexture: BufferedImage = {
        if(texture.isEmpty) {
            generateTexture()
        }
        texture.get
    }

    def populateTexturedRectsDestination() {
        textureSize = ModelTextureHelper.populateTexturedRectsDestination(getTexturedRects)
    }

    def getTexturedRects: Array[CuboidTexturedRect] = {
        if(texturedRectsCache.isEmpty) {
            val rects: ListBuffer[CuboidTexturedRect] = ListBuffer()

            for(cuboid <- cuboids) {
                for(rect <- cuboid.getTexturedRects) {
                    rects += rect
                }
            }

            texturedRectsCache = Some(rects.to[Array])
        }
        texturedRectsCache.get
    }

    def getMostResemblingCuboid(cuboid: Cuboid): Option[Cuboid] = {
        var result: Option[Cuboid] = None
        var maxTagSimilarity = 0.0F

        for(c <- cuboids) {
            val tagSimilarity = cuboid.cuboidType.similarity(c.cuboidType)

            if(tagSimilarity > maxTagSimilarity) {
                result = Some(c)
                maxTagSimilarity = tagSimilarity
            }
        }

        if(maxTagSimilarity > 0.0F) {
            result
        } else {
            None
        }
    }

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setInteger("textureSizeX", textureSize._1)
        compound.setInteger("textureSizeY", textureSize._2)

        val cuboidList = new NBTTagList

        for(cuboid <- cuboids) {
            cuboidList.appendTag(cuboid.writeToNBT(new NBTTagCompound))
        }

        compound.setTag("cuboids", cuboidList)

        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        textureSize = (compound.getInteger("textureSizeX"), compound.getInteger("textureSizeY"))

        val cuboidList = compound.getTagList("cuboids", Constants.NBT.TAG_COMPOUND)
        cuboids = new Array[Cuboid](cuboidList.tagCount)

        for(i <- 0 until cuboidList.tagCount) {
            cuboids(i) = new Cuboid(cuboidList.getCompoundTagAt(i))
        }

        compound
    }

    def copy(): Model = new Model(cuboids)
}
