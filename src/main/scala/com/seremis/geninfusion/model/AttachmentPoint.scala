package com.seremis.geninfusion.model

import com.seremis.geninfusion.api.model.{IAttachmentPoint, IModelPartType}
import net.minecraft.util.math.Vec3d

class AttachmentPoint(location: Vec3d, partTypes: Array[IModelPartType]) extends IAttachmentPoint {

    override def getConnectablePartTypes: Array[IModelPartType] = partTypes

    override def getLocation: Vec3d = location
}
