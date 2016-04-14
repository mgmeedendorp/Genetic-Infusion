package com.seremis.geninfusion.api.model

import net.minecraft.client.renderer.VertexBuffer

trait ICuboid extends INBTTagable {
    def getX: Float
    def getY: Float
    def getZ: Float

    def getSizeX: Float
    def getSizeY: Float
    def getSizeZ: Float

    def getTexturedRects: Array[ITexturedRect]

    def draw(buffer: VertexBuffer)
    def mutate(): ICuboid
}
