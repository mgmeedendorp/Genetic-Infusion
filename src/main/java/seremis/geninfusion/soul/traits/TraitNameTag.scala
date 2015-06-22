package seremis.geninfusion.soul.traits

import net.minecraft.entity.EntityLiving
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.util.DataWatcherHelper

class TraitNameTag extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, DataWatcherNameTag))
            DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, "", DataWatcherNameTag)
    }

    override def writeToNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        val living = entity.asInstanceOf[EntityLiving]

        if(DataWatcherHelper.isNameRegistered(living.getDataWatcher, DataWatcherNameTag))
            DataWatcherHelper.writeObjectToNBT(compound, living.getDataWatcher, DataWatcherNameTag)
    }

    override def readFromNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        val living = entity.asInstanceOf[EntityLiving]

        if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, DataWatcherNameTag))
            DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, "", DataWatcherNameTag)

        DataWatcherHelper.readObjectFromNBT(compound, living.getDataWatcher, DataWatcherNameTag)
    }

    override def setCustomNameTag(entity: IEntitySoulCustom, nameTag: String) {
        val living = entity.asInstanceOf[EntityLiving]

        DataWatcherHelper.updateObject(living.getDataWatcher, DataWatcherNameTag, nameTag)
    }

    override def getCustomNameTag(entity: IEntitySoulCustom): String = {
        val living = entity.asInstanceOf[EntityLiving]

        if(DataWatcherHelper.isNameRegistered(living.getDataWatcher, DataWatcherNameTag))
            DataWatcherHelper.getObjectFromDataWatcher(living.getDataWatcher, DataWatcherNameTag).asInstanceOf[String]
        else
            ""
    }

    override def hasCustomNameTag(entity: IEntitySoulCustom): Boolean = {
        val living = entity.asInstanceOf[EntityLiving]

        DataWatcherHelper.isNameRegistered(living.getDataWatcher, DataWatcherNameTag) && DataWatcherHelper.getObjectFromDataWatcher(living.getDataWatcher, DataWatcherNameTag).asInstanceOf[String].length > 0
    }
}
