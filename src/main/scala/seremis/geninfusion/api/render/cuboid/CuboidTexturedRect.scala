package seremis.geninfusion.api.render.cuboid

import net.minecraft.client.renderer.Tessellator
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.util.{ResourceLocation, Vec3}
import net.minecraftforge.common.util.Constants
import seremis.geninfusion.api.util.ModelTextureHelper
import seremis.geninfusion.util.INBTTagable

class CuboidTexturedRect(var textureLocation: String, var corners: Array[Vec3], var x: Int, var y: Int, var sizeX: Int, var sizeY: Int, var mirror: Boolean) extends INBTTagable {

    var destX: Int = x
    var destY: Int = y
    var textureSizeX: Int = 0
    var textureSizeY: Int = 0

    def this() {
        this("", null, 0, 0, 16, 16, false)
    }

    def this(compound: NBTTagCompound) {
        this()
        readFromNBT(compound)
    }

    def setDestination(x: Int, y: Int) {
        destX = x
        destY = y
    }

    def setTextureSize(width: Int, height: Int): Unit = {
        textureSizeX = width
        textureSizeY = height
    }

    def draw(tessellator: Tessellator) {
        val vec1 = corners(1).subtract(corners(0))
        val vec2 = corners(1).subtract(corners(2))

        val normalVec = vec2.crossProduct(vec1).normalize()

        tessellator.startDrawingQuads()

        tessellator.setNormal(normalVec.xCoord.toFloat, normalVec.yCoord.toFloat, normalVec.zCoord.toFloat)

        val u = x.toFloat / textureSizeX.toFloat
        val v = y.toFloat / textureSizeY.toFloat
        val du = sizeX.toFloat / textureSizeX.toFloat
        val dv = sizeY.toFloat / textureSizeY.toFloat


        tessellator.addVertexWithUV(corners(0).xCoord, corners(0).yCoord, corners(0).zCoord, u     , v     )
        tessellator.addVertexWithUV(corners(1).xCoord, corners(1).yCoord, corners(1).zCoord, u     , v + dv)
        tessellator.addVertexWithUV(corners(2).xCoord, corners(2).yCoord, corners(2).zCoord, u + du, v + dv)
        tessellator.addVertexWithUV(corners(3).xCoord, corners(3).yCoord, corners(3).zCoord, u + du, v     )


        tessellator.draw()
    }

    def getResourceLocation = new ResourceLocation(textureLocation)

    def getTexture = ModelTextureHelper.getBufferedImage(getResourceLocation)

    def setTextureLocation(location: String) {
        textureLocation = location
    }

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setString("textureLocation", textureLocation)
        compound.setInteger("x", x)
        compound.setInteger("y", y)
        compound.setInteger("sizeX", sizeX)
        compound.setInteger("sizeY", sizeY)
        compound.setInteger("destX", destX)
        compound.setInteger("destY", destY)

        val cornerList = new NBTTagList

        for(corner <- corners) {
            val nbt = new NBTTagCompound

            nbt.setFloat("x", corner.xCoord.toFloat)
            nbt.setFloat("y", corner.yCoord.toFloat)
            nbt.setFloat("z", corner.zCoord.toFloat)

            cornerList.appendTag(nbt)
        }
        compound.setTag("corners", cornerList)
        
        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        textureLocation = compound.getString("textureLocation")
        x = compound.getInteger("x")
        y = compound.getInteger("y")
        sizeX = compound.getInteger("sizeX")
        sizeY = compound.getInteger("sizeY")
        destX = compound.getInteger("destX")
        destY = compound.getInteger("destY")

        val cornersList = compound.getTagList("corners", Constants.NBT.TAG_COMPOUND)
        corners = new Array[Vec3](cornersList.tagCount)

        for(i <- 0 until cornersList.tagCount) {
            val nbt = cornersList.getCompoundTagAt(i)

            corners(i) = Vec3.createVectorHelper(nbt.getFloat("x"), nbt.getFloat("y"), nbt.getFloat("z"))
        }
        compound
    }
}
