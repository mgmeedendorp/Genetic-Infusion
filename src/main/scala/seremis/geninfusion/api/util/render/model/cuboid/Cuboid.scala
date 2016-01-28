package seremis.geninfusion.api.util.render.model.cuboid

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.{GLAllocation, Tessellator}
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraftforge.common.util.Constants
import org.lwjgl.opengl.GL11
import seremis.geninfusion.util.INBTTagable

import scala.collection.mutable.ListBuffer

class Cuboid(var x: Float, var y: Float, var z: Float, var xSize: Float, var ySize: Float, var zSize: Float, var cuboidType: CuboidType, var attachmentPoints: Array[CuboidAttachmentPoint], var texturedRects: Array[CuboidTexturedRect]) extends INBTTagable {

    //TODO correctly implement this in Model
    var renderAsChildCuboids: ListBuffer[Cuboid] = ListBuffer()

    var displayList: Int = 0
    var displayListCompiled: Boolean = false

    var rotationPointX = x
    var rotationPointY = y
    var rotationPointZ = z
    var rotateAngleX = 0.0F
    var rotateAngleY = 0.0F
    var rotateAngleZ = 0.0F
    var mirror = false

    def this(compound: NBTTagCompound) {
        this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, null)
        readFromNBT(compound)
    }

    def setPosition(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }

    def setSize(xSize: Float, ySize: Float, zSize: Float) {
        this.xSize = xSize
        this.ySize = ySize
        this.zSize = zSize
    }

    def addChild(child: Cuboid) {
        renderAsChildCuboids += child
    }

    def getAttachmentPoints = attachmentPoints

    def getTexturedRects: Array[CuboidTexturedRect] = texturedRects

    @SideOnly(Side.CLIENT)
    def render() {
        if(!displayListCompiled) {
            compileDisplayList()
        }
        GL11.glPushMatrix()

        GL11.glTranslatef(rotationPointX, rotationPointY, rotationPointZ)

        if(rotateAngleZ != 0.0F) {
            GL11.glRotatef(rotateAngleZ * (180F / Math.PI.toFloat), 0.0F, 0.0F, 1.0F)
        }

        if(rotateAngleY != 0.0F) {
            GL11.glRotatef(this.rotateAngleY * (180F / Math.PI.toFloat), 0.0F, 1.0F, 0.0F)
        }

        if(rotateAngleX != 0.0F) {
            GL11.glRotatef(this.rotateAngleX * (180F / Math.PI.toFloat), 1.0F, 0.0F, 0.0F)
        }

        GL11.glCallList(displayList)

        for(child <- renderAsChildCuboids) {
            child.render()
        }

        GL11.glPopMatrix()
    }

    @SideOnly(Side.CLIENT)
    def compileDisplayList() {
        displayList = GLAllocation.generateDisplayLists(1)
        GL11.glNewList(displayList, GL11.GL_COMPILE)
        val tessellator = Tessellator.instance

        drawBox(tessellator)

        GL11.glEndList()
        displayListCompiled = true
    }

    def drawBox(tessellator: Tessellator) {
        var x1 = x
        val y1 = y
        val z1 = z
        var x2 = x + xSize
        val y2 = y + ySize
        val z2 = z + zSize

        if(mirror) {
            x1 = x2
            x2 = x
        }

        texturedRects(0).draw(tessellator, x2, y1, z1, x2, y2, z2, mirror)
        texturedRects(1).draw(tessellator, x1, y1, z2, x1, y2, z1, mirror)
        texturedRects(2).draw(tessellator, x1, y1, z2, x1, y1, z1, mirror)
        texturedRects(3).draw(tessellator, x1, y2, z1, x2, y2, z2, mirror)
        texturedRects(4).draw(tessellator, x1, y1, z1, x2, y2, z1, mirror)
        texturedRects(5).draw(tessellator, x2, y1, z2, x1, y2, z2, mirror)
    }

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setFloat("x", x)
        compound.setFloat("y", y)
        compound.setFloat("z", z)
        compound.setFloat("xSize", xSize)
        compound.setFloat("ySize", ySize)
        compound.setFloat("zSize", zSize)

        compound.setFloat("rotationPointX", rotationPointX)
        compound.setFloat("rotationPointY", rotationPointY)
        compound.setFloat("rotationPointZ", rotationPointZ)
        compound.setFloat("rotateAngleX", rotateAngleX)
        compound.setFloat("rotateAngleY", rotateAngleY)
        compound.setFloat("rotateAngleZ", rotateAngleZ)

        compound.setTag("cuboidType", cuboidType.writeToNBT(new NBTTagCompound))

        val attachmentPointList = new NBTTagList

        for(attachmentPoint <- attachmentPoints) {
            attachmentPointList.appendTag(attachmentPoint.writeToNBT(new NBTTagCompound))
        }

        compound.setTag("attachmentPoints", attachmentPointList)

        val texturedRectsList = new NBTTagList

        for(texturedRect <- texturedRects) {
            texturedRectsList.appendTag(texturedRect.writeToNBT(new NBTTagCompound))
        }

        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        x = compound.getFloat("x")
        y = compound.getFloat("y")
        z = compound.getFloat("z")
        xSize = compound.getFloat("xSize")
        ySize = compound.getFloat("ySize")
        zSize = compound.getFloat("zSize")

        rotationPointX = compound.getFloat("rotationPointX")
        rotationPointY = compound.getFloat("rotationPointY")
        rotationPointZ = compound.getFloat("rotationPointZ")
        rotateAngleX = compound.getFloat("rotateAngleX")
        rotateAngleY = compound.getFloat("rotateAngleY")
        rotateAngleZ = compound.getFloat("rotateAngleZ")

        cuboidType = new CuboidType(compound.getCompoundTag("cuboidType"))

        val attachmentPointList = compound.getTagList("attachmentPoints", Constants.NBT.TAG_COMPOUND)

        attachmentPoints = new Array[CuboidAttachmentPoint](attachmentPointList.tagCount)

        for(i <- 0 until attachmentPointList.tagCount) {
            attachmentPoints(i) = new CuboidAttachmentPoint(attachmentPointList.getCompoundTagAt(i))
        }

        val texturedRectList = compound.getTagList("attachmentPoints", Constants.NBT.TAG_COMPOUND)

        texturedRects = new Array[CuboidTexturedRect](texturedRectList.tagCount)

        for(i <- 0 until texturedRectList.tagCount) {
            texturedRects(i) = new CuboidTexturedRect(texturedRectList.getCompoundTagAt(i))
        }

        compound
    }
}
