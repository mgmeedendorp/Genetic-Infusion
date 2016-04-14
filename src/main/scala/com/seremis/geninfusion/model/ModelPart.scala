package com.seremis.geninfusion.model

import com.seremis.geninfusion.api.model.{IAttachmentPoint, ICuboid, IModelPart, IModelPartType}
import net.minecraft.client.renderer.{GlStateManager, Tessellator}
import org.lwjgl.opengl.GL11

class ModelPart(cuboids: Array[ICuboid], partType: IModelPartType, attachmentPoints: Array[IAttachmentPoint], childParts: Array[ModelPart]) extends IModelPart {

    protected var rotationPointX = 0.0F
    protected var rotationPointY = 0.0F
    protected var rotationPointZ = 0.0F

    protected var rotateAngleX = 0.0F
    protected var rotateAngleY = 0.0F
    protected var rotateAngleZ = 0.0F

    protected var initialRotationPointX = 0.0F
    protected var initialRotationPointY = 0.0F
    protected var initialRotationPointZ = 0.0F

    protected var initialRotateAngleX = 0.0F
    protected var initialRotateAngleY = 0.0F
    protected var initialRotateAngleZ = 0.0F

    protected var displayList = 0
    protected var displayListCompiled = false

    var firstRender = true

    override def render() {
        if(firstRender) {
            initialRotationPointX = rotationPointX
            initialRotationPointY = rotationPointY
            initialRotationPointZ = rotationPointZ

            initialRotateAngleX = rotateAngleX
            initialRotateAngleY = rotateAngleY
            initialRotateAngleZ = rotateAngleZ
        }

        if(!displayListCompiled) {
            compileDisplayList()
        }

        GL11.glPushMatrix()

        GL11.glTranslatef(rotationPointX, rotationPointY, rotationPointZ)

        if(rotateAngleX != 0.0F) {
            GL11.glRotatef(rotateAngleX * (180F / Math.PI.toFloat), 1.0F, 0.0F, 0.0F)
        }

        if(rotateAngleY != 0.0F) {
            GL11.glRotatef(rotateAngleY * (180F / Math.PI.toFloat), 0.0F, 1.0F, 0.0F)
        }

        if(rotateAngleZ != 0.0F) {
            GL11.glRotatef(rotateAngleZ * (180F / Math.PI.toFloat), 0.0F, 0.0F, 1.0F)
        }

        GL11.glCallList(displayList)

        GL11.glPopMatrix()
    }

    override def setRotationPoints(x: Float, y: Float, z: Float) {
        val dX = x - rotationPointX
        val dY = y - rotationPointY
        val dZ = z - rotationPointZ

        moveRotationPoints(dX, dY, dZ)
    }

    override def moveRotationPoints(dX: Float, dY: Float, dZ: Float) {
        rotationPointX += dX
        rotationPointY += dY
        rotationPointZ += dZ

        for(child <- childParts) {
            child.moveRotationPoints(dX, dY, dZ)
        }
    }

    override def setRotateAngles(angleX: Float, angleY: Float, angleZ: Float) {
        val dX = angleX - rotateAngleX
        val dY = angleY - rotateAngleY
        val dZ = angleZ - rotateAngleZ

        addRotateAngles(dX, dY, dZ)
    }

    override def addRotateAngles(dX: Float, dY: Float, dZ: Float) {
        rotateAngleX += dX
        rotateAngleY += dY
        rotateAngleZ += dZ

        for(child <- childParts) {
            child.addRotateAngles(dX, dY, dZ)
        }
    }

    override def getRotationPointX: Float = rotationPointX
    override def getRotationPointY: Float = rotationPointY
    override def getRotationPointZ: Float = rotationPointZ

    override def getRotateAngleX: Float = rotateAngleX
    override def getRotateAngleY: Float = rotateAngleY
    override def getRotateAngleZ: Float = rotateAngleZ

    override def getCuboids: Array[ICuboid] = cuboids

    override def resetInitialValues() {
        rotationPointX = initialRotationPointX
        rotationPointY = initialRotationPointY
        rotationPointZ = initialRotationPointZ

        rotateAngleX = initialRotateAngleX
        rotateAngleY = initialRotateAngleY
        rotateAngleZ = initialRotateAngleZ
    }

    def compileDisplayList() {
        GlStateManager.glNewList(this.displayList, 4864)
        val buffer = Tessellator.getInstance.getBuffer

        for(cuboid <- cuboids) {
            cuboid.draw(buffer)
        }

        GlStateManager.glEndList()
        displayListCompiled = true
    }

    override def mutate(): IModelPart = ???

    override def getAttachmentPoints: Array[IAttachmentPoint] = attachmentPoints

    override def getPartType: IModelPartType = partType
}
