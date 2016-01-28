package seremis.geninfusion.api.util.render.model.cuboid

import java.awt.image.BufferedImage

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraftforge.common.util.Constants
import seremis.geninfusion.util.INBTTagable

import scala.collection.mutable.ListBuffer

class Model(var cuboids: Array[Cuboid]) extends INBTTagable {

    @SideOnly(Side.CLIENT)
    var texture: Option[BufferedImage] = None
    var textureSize: (Int, Int) = (0, 0)

    var texturedRectsCache: Option[Array[CuboidTexturedRect]] = None

    def getCuboids = cuboids

    @SideOnly(Side.CLIENT)
    def render() {
        for(cuboid <- cuboids) {
            cuboid.render()
        }
    }

    @SideOnly(Side.CLIENT)
    def generateTexture() {
        if(texture.isEmpty) {
            texture = Some(ModelTextureHelper.stitchTexturedRects(getTexturedRects, textureSize))
        }
    }

    @SideOnly(Side.CLIENT)
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
}
