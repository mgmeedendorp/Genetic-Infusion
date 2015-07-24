package seremis.geninfusion.soul.entity

import net.minecraft.entity.EntityAgeable

trait EntitySoulCustomAgeableTrait extends EntityAgeable with EntitySoulCustomCreatureTrait {

    override def createChild(ageable : EntityAgeable): EntityAgeable = createChild_I(ageable)

    override def setScaleForAge(isChild: Boolean) = setScaleForAge_I(isChild)

    override def getGrowingAge = getGrowingAge_I

    override def setGrowingAge(growingAge: Int) = setGrowingAge_I(growingAge)

    override def addGrowth(growingAge: Int) = addGrowth_I(growingAge)

    override def setSize_I(width: Float, height: Float) = super.setSize(width, height)
}
