package seremis.geninfusion.api.util.render.model.cuboid

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.Tessellator
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ResourceLocation
import seremis.geninfusion.util.INBTTagable

class CuboidTexturedRect(var textureLocation: String, var x: Int, var y: Int, var sizeX: Int, var sizeY: Int) extends INBTTagable {

    var destX: Int = x
    var destY: Int = y
    var textureSizeX: Int = 0
    var textureSizeY: Int = 0

    def this() {
        this("", 0, 0, 0, 0)
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

    def draw(tessellator: Tessellator, x1: Double, y1: Double, z1: Double, x2: Double, y2: Double, z2: Double, mirror: Boolean) {
        tessellator.startDrawingQuads()

        val u = x.toFloat / textureSizeX.toFloat
        val v = y.toFloat / textureSizeY.toFloat
        val du = sizeX.toFloat / textureSizeX.toFloat
        val dv = sizeY.toFloat / textureSizeY.toFloat

        if(mirror) {
            tessellator.addVertexWithUV(x2, y2, z2, u     , v     )
            tessellator.addVertexWithUV(x2, y1, z2, u     , v + dv)
            tessellator.addVertexWithUV(x1, y1, z1, u + du, v + dv)
            tessellator.addVertexWithUV(x1, y2, z1, u + du, v     )
        } else {
            tessellator.addVertexWithUV(x1, y2, z1, u     , v     )
            tessellator.addVertexWithUV(x1, y1, z1, u     , v + dv)
            tessellator.addVertexWithUV(x2, y1, z2, u + du, v + dv)
            tessellator.addVertexWithUV(x2, y2, z2, u + du, v     )
        }

        tessellator.draw()
    }

    def getResourceLocation = new ResourceLocation(textureLocation)

    @SideOnly(Side.CLIENT)
    def getTexture = ModelTextureHelper.getBufferedImage(getResourceLocation)

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setString("textureLocation", textureLocation)
        compound.setInteger("x", x)
        compound.setInteger("y", y)
        compound.setInteger("sizeX", sizeX)
        compound.setInteger("sizeY", sizeY)
        compound.setInteger("destX", destX)
        compound.setInteger("destY", destY)

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

        compound
    }
}
