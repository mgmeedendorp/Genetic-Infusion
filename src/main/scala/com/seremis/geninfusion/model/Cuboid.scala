package com.seremis.geninfusion.model

import com.seremis.geninfusion.api.model.{ICuboid, ITexturedRect}
import net.minecraft.client.renderer.VertexBuffer
import net.minecraft.util.math.Vec3d

class Cuboid(x: Float, y: Float, z: Float, sizeX: Float, sizeY: Float, sizeZ: Float, texturedRects: Array[ITexturedRect]) extends ICuboid {

    def this(x: Float, y: Float, z: Float, sizeX: Float, sizeY: Float, sizeZ: Float, textureOffsetX: Int, textureOffsetY: Int, mirror: Boolean, textureLocation: String, textureWidth: Int, textureHeight: Int) {
        this(x, y, z, sizeX, sizeY, sizeZ, Cuboid.textureLocationToRects(x, y, z, sizeX, sizeY, sizeZ, textureOffsetX, textureOffsetY, mirror, textureLocation, textureWidth, textureHeight).asInstanceOf[Array[ITexturedRect]])
    }

    override def getX: Float = x
    override def getY: Float = y
    override def getZ: Float = z

    override def getSizeX: Float = sizeX
    override def getSizeY: Float = sizeY
    override def getSizeZ: Float = sizeZ

    override def getTexturedRects: Array[ITexturedRect] = texturedRects

    override def draw(buffer: VertexBuffer, scale: Float) {
        for(rect <- texturedRects) {
            rect.draw(buffer, scale)
        }
    }

    override def mutate(): ICuboid = ??? //TODO implement mutate
}

object Cuboid {
    def textureLocationToRects(x: Float, y: Float, z: Float, sizeX: Float, sizeY: Float, sizeZ: Float, textureOffsetX: Int, textureOffsetY: Int, mirror: Boolean, textureLocation: String, textureWidth: Int, textureHeight: Int): Array[TexturedRect] = {
        var x1 = x
        val y1 = y
        val z1 = z
        var x2 = x + sizeX
        val y2 = y + sizeY
        val z2 = z + sizeZ

        val dx = sizeX.toInt
        val dy = sizeY.toInt
        val dz = sizeZ.toInt

        if(mirror) {
            x1 = x2
            x2 = x
        }

        def vec3(x: Double, y: Double, z: Double) = new Vec3d(x, y, z)

        val quadCorners = Array(
            Array(vec3(x2, y1, z1), vec3(x2, y2, z1), vec3(x2, y2, z2), vec3(x2, y1, z2)),
            Array(vec3(x1, y1, z2), vec3(x1, y2, z2), vec3(x1, y2, z1), vec3(x1, y1, z1)),
            Array(vec3(x1, y1, z2), vec3(x1, y1, z1), vec3(x2, y1, z1), vec3(x2, y1, z2)),
            Array(vec3(x1, y2, z1), vec3(x1, y2, z2), vec3(x2, y2, z2), vec3(x2, y2, z1)),
            Array(vec3(x1, y1, z1), vec3(x1, y2, z1), vec3(x2, y2, z1), vec3(x2, y1, z1)),
            Array(vec3(x2, y1, z2), vec3(x2, y2, z2), vec3(x1, y2, z2), vec3(x1, y1, z2))
        )

        Array(
            new TexturedRect(textureLocation, quadCorners(0), textureOffsetX + dz + dx, textureOffsetY + dz, dz, dy, textureWidth, textureHeight),
            new TexturedRect(textureLocation, quadCorners(1), textureOffsetX, textureOffsetY + dz, dz, dy, textureWidth, textureHeight),
            new TexturedRect(textureLocation, quadCorners(2), textureOffsetX + dz, textureOffsetY, dx, dz, textureWidth, textureHeight),
            new TexturedRect(textureLocation, quadCorners(3), textureOffsetX + dz + dx, textureOffsetY, dx, dz, textureWidth, textureHeight),
            new TexturedRect(textureLocation, quadCorners(4), textureOffsetX + dz, textureOffsetY + dz, dx, dy, textureWidth, textureHeight),
            new TexturedRect(textureLocation, quadCorners(5), textureOffsetX + dz + dx + dz, textureOffsetY + dz, dx, dy, textureWidth, textureHeight)
        )
    }
}