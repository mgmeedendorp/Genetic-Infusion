package seremis.geninfusion.util

import net.minecraft.client.model.{ModelBox, ModelRenderer, PositionTextureVertex, TexturedQuad}
import seremis.geninfusion.api.lib.VariableLib
import seremis.geninfusion.helper.GIReflectionHelper

class GIModelBox(modelRenderer: ModelRenderer) extends ModelBox(modelRenderer, 0, 0, 0, 0, 0, 0, 0, 0, 0) {

    var vertices: Array[PositionTextureVertex] = _
    var texturedQuads: Array[TexturedQuad] = _
    
    var x1, x2, y1, y2, z1, z2: Float = _
    
    def this(modelRenderer: ModelRenderer, textureOffsetX: Int, textureOffsetY: Int, x: Float, y: Float, z: Float, sizeX: Float, sizeY: Float, sizeZ: Float, sizeModifier: Float) {
        this(modelRenderer)

        x1 = x
        y1 = y
        z1 = z
        x2 = x + sizeX
        y2 = y + sizeY
        z2 = z + sizeZ
        
        vertices = new Array[PositionTextureVertex](8)
        texturedQuads = new Array[TexturedQuad](6)
        
        var posX = x
        var posY = y
        var posZ = z
        var posX2 = x + sizeX
        var posY2 = y + sizeY
        var posZ2 = z + sizeZ
        
        posX -= sizeModifier
        posY -= sizeModifier
        posZ -= sizeModifier
        posX2 += sizeModifier
        posY2 += sizeModifier
        posZ2 += sizeModifier
        
        if(modelRenderer.mirror) {
            val tmp = posX2
            posX2 = x
            posX = tmp
        }

        val positiontexturevertex7 = new PositionTextureVertex(posX, posY, posZ, 0.0F, 0.0F)
        val positiontexturevertex = new PositionTextureVertex(posX2, posY, posZ, 0.0F, 8.0F)
        val positiontexturevertex1 = new PositionTextureVertex(posX2, posY2, posZ, 8.0F, 8.0F)
        val positiontexturevertex2 = new PositionTextureVertex(posX, posY2, posZ, 8.0F, 0.0F)
        val positiontexturevertex3 = new PositionTextureVertex(posX, posY, posZ2, 0.0F, 0.0F)
        val positiontexturevertex4 = new PositionTextureVertex(posX2, posY, posZ2, 0.0F, 8.0F)
        val positiontexturevertex5 = new PositionTextureVertex(posX2, posY2, posZ2, 8.0F, 8.0F)
        val positiontexturevertex6 = new PositionTextureVertex(posX, posY2, posZ2, 8.0F, 0.0F)
        
        vertices(0) = positiontexturevertex7
        vertices(1) = positiontexturevertex
        vertices(2) = positiontexturevertex1
        vertices(3) = positiontexturevertex2
        vertices(4) = positiontexturevertex3
        vertices(5) = positiontexturevertex4
        vertices(6) = positiontexturevertex5
        vertices(7) = positiontexturevertex6

        texturedQuads(0) = new TexturedQuad(Array[PositionTextureVertex](positiontexturevertex4, positiontexturevertex, positiontexturevertex1, positiontexturevertex5), (textureOffsetX + sizeZ + sizeX).toInt, (textureOffsetY + sizeZ).toInt, (textureOffsetX + sizeZ + sizeX + sizeZ).toInt, (textureOffsetY + sizeZ + sizeY).toInt, modelRenderer.textureWidth, modelRenderer.textureHeight)
        texturedQuads(1) = new TexturedQuad(Array[PositionTextureVertex](positiontexturevertex7, positiontexturevertex3, positiontexturevertex6, positiontexturevertex2), textureOffsetX, (textureOffsetY + sizeZ).toInt, (textureOffsetX + sizeZ).toInt, (textureOffsetY + sizeZ + sizeY).toInt, modelRenderer.textureWidth, modelRenderer.textureHeight)
        texturedQuads(2) = new TexturedQuad(Array[PositionTextureVertex](positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex), (textureOffsetX + sizeZ).toInt, textureOffsetY, (textureOffsetX + sizeZ + sizeX).toInt, (textureOffsetY + sizeZ).toInt, modelRenderer.textureWidth, modelRenderer.textureHeight)
        texturedQuads(3) = new TexturedQuad(Array[PositionTextureVertex](positiontexturevertex1, positiontexturevertex2, positiontexturevertex6, positiontexturevertex5), (textureOffsetX + sizeZ + sizeX).toInt, (textureOffsetY + sizeZ).toInt, (textureOffsetX + sizeZ + sizeX + sizeX).toInt, textureOffsetY, modelRenderer.textureWidth, modelRenderer.textureHeight)
        texturedQuads(4) = new TexturedQuad(Array[PositionTextureVertex](positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1), (textureOffsetX + sizeZ).toInt, (textureOffsetY + sizeZ).toInt, (textureOffsetX + sizeZ + sizeX).toInt, (textureOffsetY + sizeZ + sizeY).toInt, modelRenderer.textureWidth, modelRenderer.textureHeight)
        texturedQuads(5) = new TexturedQuad(Array[PositionTextureVertex](positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6), (textureOffsetX + sizeZ + sizeX + sizeZ).toInt, (textureOffsetY + sizeZ).toInt, (textureOffsetX + sizeZ + sizeX + sizeZ + sizeX).toInt, (textureOffsetY + sizeZ + sizeY).toInt, modelRenderer.textureWidth, modelRenderer.textureHeight)

        if(modelRenderer.mirror) {
            for(quad <- texturedQuads) {
                quad.flipFace()
            }
        }

        GIReflectionHelper.setField(this, VariableLib.ModelBoxQuadList, texturedQuads)
        GIReflectionHelper.setField(this, VariableLib.ModelBoxVertexPositions, vertices)
        GIReflectionHelper.setField(this, VariableLib.ModelBoxPosX1, x1)
        GIReflectionHelper.setField(this, VariableLib.ModelBoxPosY1, y1)
        GIReflectionHelper.setField(this, VariableLib.ModelBoxPosZ1, z1)
        GIReflectionHelper.setField(this, VariableLib.ModelBoxPosX2, x2)
        GIReflectionHelper.setField(this, VariableLib.ModelBoxPosY2, y2)
        GIReflectionHelper.setField(this, VariableLib.ModelBoxPosZ2, z2)
    }

}
