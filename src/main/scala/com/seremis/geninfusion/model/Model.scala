package com.seremis.geninfusion.model

import com.seremis.geninfusion.api.model.IModel
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

class Model(parts: Array[ModelPart]) extends IModel {

    @SideOnly(Side.CLIENT)
    def render(): Unit = render(1F/16F)

    @SideOnly(Side.CLIENT)
    def render(scale: Float): Unit = {
        //Bind texture

        //render cuboids
    }

    def copy() = ???

    def getModelParts = parts

    def mutate() {}

    def getAnimator = ???

    def resetInitialValues() {}

}
