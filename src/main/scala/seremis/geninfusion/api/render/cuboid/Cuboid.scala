package seremis.geninfusion.api.render.cuboid

import net.minecraft.client.renderer.{GLAllocation, Tessellator}
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.util.{ResourceLocation, Vec3}
import net.minecraftforge.common.util.Constants
import org.lwjgl.opengl.GL11
import seremis.geninfusion.api.util.ModelTextureHelper
import seremis.geninfusion.util.INBTTagable

import scala.collection.mutable.ListBuffer

class Cuboid(var x: Float, var y: Float, var z: Float, var xSize: Float, var ySize: Float, var zSize: Float, var textureOffsetX: Int, var textureOffsetY: Int, var mirror: Boolean, var cuboidType: CuboidType, var attachmentPoints: Array[CuboidAttachmentPoint], var texturedRects: Array[CuboidTexturedRect]) extends INBTTagable {

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

    var initialRotationPointX = 0.0F
    var initialRotationPointY = 0.0F
    var initialRotationPointZ = 0.0F
    var initialRotateAngleX = 0.0F
    var initialRotateAngleY = 0.0F
    var initialRotateAngleZ = 0.0F

    var firstTick = true

    def this(x: Float, y: Float, z: Float, xSize: Float, ySize: Float, zSize: Float, textureOffsetX: Int, textureOffsetY: Int, mirror: Boolean, cuboidType: CuboidType, attachmentPoints: Array[CuboidAttachmentPoint], textureLocation: String) {
        this(x, y, z, xSize, ySize, zSize, textureOffsetX, textureOffsetY, mirror, cuboidType, attachmentPoints, null.asInstanceOf[Array[CuboidTexturedRect]])

        var x1 = x
        val y1 = y
        val z1 = z
        var x2 = x + xSize
        val y2 = y + ySize
        val z2 = z + zSize
        
        val dx = xSize.toInt
        val dy = ySize.toInt
        val dz = zSize.toInt

        if(mirror) {
            x1 = x2
            x2 = x
        }

        def vec3(x: Double, y: Double, z: Double) = Vec3.createVectorHelper(x, y, z)

        val quadCorners = Array(
            Array(vec3(x2, y1, z1), vec3(x2, y2, z1), vec3(x2, y2, z2), vec3(x2, y1, z2)),
            Array(vec3(x1, y1, z2), vec3(x1, y2, z2), vec3(x1, y2, z1), vec3(x1, y1, z1)),
            Array(vec3(x1, y1, z2), vec3(x1, y1, z1), vec3(x2, y1, z1), vec3(x2, y1, z2)),
            Array(vec3(x1, y2, z1), vec3(x1, y2, z2), vec3(x2, y2, z2), vec3(x2, y2, z1)),
            Array(vec3(x1, y1, z1), vec3(x1, y2, z1), vec3(x2, y2, z1), vec3(x2, y1, z1)),
            Array(vec3(x2, y1, z2), vec3(x2, y2, z2), vec3(x1, y2, z2), vec3(x1, y1, z2))
        )

        texturedRects = Array(
            new CuboidTexturedRect(textureLocation, quadCorners(0), textureOffsetX + dz + dx, textureOffsetY + dz, dz, dy, mirror),
            new CuboidTexturedRect(textureLocation, quadCorners(1), textureOffsetX, textureOffsetY + dz, dz, dy, mirror),
            new CuboidTexturedRect(textureLocation, quadCorners(2), textureOffsetX + dz, textureOffsetY, dx, dz, mirror),
            new CuboidTexturedRect(textureLocation, quadCorners(3), textureOffsetX + dz + dx, textureOffsetY, dx, dz, mirror),
            new CuboidTexturedRect(textureLocation, quadCorners(4), textureOffsetX + dz, textureOffsetY + dz, dx, dy, mirror),
            new CuboidTexturedRect(textureLocation, quadCorners(5), textureOffsetX + dz + dx + dz, textureOffsetY + dz, dx, dy, mirror)
        )
    }

    def this(x: Float, y: Float, z: Float, xSize: Float, ySize: Float, zSize: Float, textureOffsetX: Int, textureOffsetY: Int, mirror: Boolean, cuboidType: CuboidType, attachmentPoints: Array[CuboidAttachmentPoint]) {
        this(x, y, z, xSize, ySize, zSize, textureOffsetX, textureOffsetY, mirror, cuboidType, attachmentPoints, "")
    }

    def this(x: Float, y: Float, z: Float, xSize: Float, ySize: Float, zSize: Float, mirror: Boolean, cuboidType: CuboidType, attachmentPoints: Array[CuboidAttachmentPoint]) {
        this(x, y, z, xSize, ySize, zSize, 0, 0, mirror, cuboidType, attachmentPoints)
    }

    def this(compound: NBTTagCompound) {
        this(0.0F, 0.0F, 0.0F, 0, 0, 0, false, null, null)
        readFromNBT(compound)
    }

    def setRotationPoint(x: Float, y: Float, z: Float) {
        this.rotationPointX = x
        this.rotationPointY = y
        this.rotationPointZ = z
    }

    def setRotateAngles(x: Float, y: Float, z: Float) {
        this.rotateAngleX = x
        this.rotateAngleY = y
        this.rotateAngleZ = z
    }

    def setSize(xSize: Float, ySize: Float, zSize: Float) {
        this.xSize = xSize
        this.ySize = ySize
        this.zSize = zSize
    }

    def addChild(child: Cuboid) {
        renderAsChildCuboids += child
    }

    def getScaledCuboid(scale: Float): Cuboid = {
        val cuboidTexturedRects = new Array[CuboidTexturedRect](6)

        for(i <- 0 until 6) {
            val rect = texturedRects(i)

            val newCorners = new Array[Vec3](4)

            for(j <- 0 until 4 ) {
                val corner = rect.corners(j)

                newCorners(j) = Vec3.createVectorHelper(corner.xCoord * scale, corner.yCoord * scale, corner.zCoord * scale)
            }

            cuboidTexturedRects(i) = new CuboidTexturedRect(rect.textureLocation, newCorners, rect.x, rect.y, rect.sizeX, rect.sizeY, rect.mirror)
            cuboidTexturedRects(i).setDestination(rect.destX, rect.destY)
            cuboidTexturedRects(i).setTextureSize(rect.textureSizeX, rect.textureSizeY)
        }

        val cuboid = new Cuboid(x * scale, y * scale, z * scale, xSize.toFloat * scale, ySize.toFloat * scale, zSize.toFloat * scale, textureOffsetX, textureOffsetY, mirror, cuboidType, attachmentPoints, cuboidTexturedRects)

        cuboid.setRotationPoint(rotationPointX, rotationPointY, rotationPointZ)
        cuboid.setRotateAngles(rotateAngleX, rotateAngleY, rotateAngleZ)
        cuboid.renderAsChildCuboids = renderAsChildCuboids

        cuboid
    }

    def resetInitialRotateAngles() {
        rotateAngleX = initialRotateAngleX
        rotateAngleY = initialRotateAngleY
        rotateAngleZ = initialRotateAngleZ
    }

    def getAttachmentPoints = attachmentPoints

    def getTexturedRects: Array[CuboidTexturedRect] = texturedRects

    def render() {
        if(firstTick) {
            initialRotateAngleX = rotateAngleX
            initialRotateAngleY = rotateAngleY
            initialRotateAngleZ = rotateAngleZ
            initialRotationPointX = rotationPointX
            initialRotationPointY = rotationPointY
            initialRotationPointZ = rotationPointZ

            firstTick = false
        }

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

    def setTextureLocation(location: String) {
        val texture = ModelTextureHelper.getBufferedImage(new ResourceLocation(location))
        for(rect <- texturedRects) {
            rect.setTextureLocation(location)
            rect.setTextureSize(texture.getWidth, texture.getHeight)
        }
    }

    def applyTranslationAndRotations() {
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
    }

    def compileDisplayList() {
        displayList = GLAllocation.generateDisplayLists(1)
        GL11.glNewList(displayList, GL11.GL_COMPILE)
        val tessellator = Tessellator.instance

        for(rect <- texturedRects) {
            rect.draw(tessellator)
        }

        GL11.glEndList()
        displayListCompiled = true
    }

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setFloat("x", x)
        compound.setFloat("y", y)
        compound.setFloat("z", z)
        compound.setFloat("xSize", xSize)
        compound.setFloat("ySize", ySize)
        compound.setFloat("zSize", zSize)

        compound.setInteger("textureOffsetX", textureOffsetX)
        compound.setInteger("textureOffsetY", textureOffsetY)

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

        textureOffsetX = compound.getInteger("textureOffsetX")
        textureOffsetY = compound.getInteger("textureOffsetY")

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
