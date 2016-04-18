package com.seremis.geninfusion.api.model

import java.awt.image.BufferedImage

import com.seremis.geninfusion.api.model.animation.IAnimator
import com.seremis.geninfusion.api.util.INBTTagable
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

trait IModel extends INBTTagable {

    def render(): Unit
    def render(scale: Float)

    @SideOnly(Side.CLIENT)
    def getTexture: BufferedImage

    def copy(): IModel

    def getModelParts: Array[IModelPart]

    def mutate(): IModel

    def getAnimator: IAnimator

    def resetInitialValues()

}
