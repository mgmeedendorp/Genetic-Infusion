package com.seremis.geninfusion.api.model

import com.seremis.geninfusion.api.util.INBTTagable
import net.minecraft.util.math.Vec3d

trait IAttachmentPoint extends INBTTagable {

    def getConnectableCuboidTypes: Array[IModelPartType]
    def getLocation: Vec3d
}
