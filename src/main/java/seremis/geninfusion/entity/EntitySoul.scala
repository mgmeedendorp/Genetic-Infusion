package seremis.geninfusion.entity

import net.minecraft.entity.Entity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import seremis.geninfusion.api.soul.{IEntitySoul, ISoul, SoulHelper}

class EntitySoul(world: World, var soul: ISoul) extends Entity(world) with IEntitySoul with GIEntity {

    boundingBox.setBounds(0.0D, 0.0D, 0.0D, 0.25D, 0.25D, 0.25D)

    override def entityInit() {

    }

    override def writeEntityToNBT(compound: NBTTagCompound) {
        compound.setTag("soul", soul.writeToNBT(new NBTTagCompound))
    }

    override def readEntityFromNBT(compound: NBTTagCompound) {
        soul = SoulHelper.instanceHelper.getISoulInstance(compound)
    }

    override def getSoul: ISoul = {
        soul
    }

    override def setSoul(soul: ISoul) = {
        this.soul = soul
    }
}
