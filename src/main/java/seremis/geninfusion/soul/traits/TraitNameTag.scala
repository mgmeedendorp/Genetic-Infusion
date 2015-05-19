package seremis.geninfusion.soul.traits

import net.minecraft.entity.EntityLiving
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.util.DataWatcherHelper

class TraitNameTag extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, DATAWATCHER_NAME_TAG))
            DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, "", DATAWATCHER_NAME_TAG)
    }

    override def writeToNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        val living = entity.asInstanceOf[EntityLiving]

        if(DataWatcherHelper.isNameRegistered(living.getDataWatcher, DATAWATCHER_NAME_TAG))
            DataWatcherHelper.writeObjectToNBT(compound, living.getDataWatcher, DATAWATCHER_NAME_TAG)
    }

    override def readFromNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        val living = entity.asInstanceOf[EntityLiving]

        if(!DataWatcherHelper.isNameRegistered(living.getDataWatcher, DATAWATCHER_NAME_TAG))
            DataWatcherHelper.addObjectAtUnusedId(living.getDataWatcher, "", DATAWATCHER_NAME_TAG)

        DataWatcherHelper.readObjectFromNBT(compound, living.getDataWatcher, DATAWATCHER_NAME_TAG)
    }

    override def setCustomNameTag(entity: IEntitySoulCustom, nameTag: String) {
        val living = entity.asInstanceOf[EntityLiving]

        DataWatcherHelper.updateObject(living.getDataWatcher, DATAWATCHER_NAME_TAG, nameTag)
    }

    override def getCustomNameTag(entity: IEntitySoulCustom): String = {
        val living = entity.asInstanceOf[EntityLiving]

        if(DataWatcherHelper.isNameRegistered(living.getDataWatcher, DATAWATCHER_NAME_TAG))
            DataWatcherHelper.getObjectFromDataWatcher(living.getDataWatcher, DATAWATCHER_NAME_TAG).asInstanceOf[String]
        else
            ""
    }

    override def hasCustomNameTag(entity: IEntitySoulCustom): Boolean = {
        val living = entity.asInstanceOf[EntityLiving]

        DataWatcherHelper.isNameRegistered(living.getDataWatcher, DATAWATCHER_NAME_TAG) && DataWatcherHelper.getObjectFromDataWatcher(living.getDataWatcher, DATAWATCHER_NAME_TAG).asInstanceOf[String].length > 0
    }
}
