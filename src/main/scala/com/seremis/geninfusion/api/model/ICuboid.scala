package com.seremis.geninfusion.api.model

import net.minecraft.client.renderer.VertexBuffer

trait ICuboid {
    def getX: Float
    def getY: Float
    def getZ: Float

    def getSizeX: Float
    def getSizeY: Float
    def getSizeZ: Float

    def getTexturedRects: Array[ITexturedRect]

    def draw(buffer: VertexBuffer, scale: Float)
    def mutate(): ICuboid
}
