package com.seremis.geninfusion.model

import java.awt.image.BufferedImage

import com.seremis.geninfusion.api.model.animation.IAnimator
import com.seremis.geninfusion.api.model.{IModel, IModelPart}
import org.lwjgl.opengl.GL11

class Model(parts: Array[IModelPart]) extends IModel {

    protected var texture = generateTexture()

    def render(): Unit = render(1F/16F)

    def render(scale: Float): Unit = {
        GL11.glPushMatrix()

        //Bind texture

        GL11.glScalef(scale, scale, scale)

        for(part <- parts) {
            part.render()
        }
        GL11.glPopMatrix()
    }

    def generateTexture(): BufferedImage = {

    }

    def copy() = new Model(parts)

    def getModelParts = parts

    def mutate() = ???

    def getAnimator: IAnimator = ???

    def resetInitialValues() {
        for(part <- parts) {
            part.resetInitialValues()
        }
    }

}
