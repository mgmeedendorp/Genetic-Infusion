package seremis.geninfusion.soul.traits

import net.minecraft.entity.EntityLiving
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.lib.reflection.VariableLib
import seremis.geninfusion.api.lib.reflection.VariableLib._
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.api.util.DataWatcherHelper

class TraitNameTag extends Trait {

    override def entityInit(entity: IEntitySoulCustom) {
        if(!entity.getWorld_I.isRemote)
            DataWatcherHelper.addObjectAtUnusedId(entity.getDataWatcher_I, "", DataWatcherNameTag)
    }

    override def writeToNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        DataWatcherHelper.writeObjectToNBT(compound, entity.getDataWatcher_I, DataWatcherNameTag)
    }

    override def readFromNBT(entity: IEntitySoulCustom, compound: NBTTagCompound) {
        DataWatcherHelper.readObjectFromNBT(compound, entity.getDataWatcher_I, DataWatcherNameTag)
    }

    override def setCustomNameTag(entity: IEntitySoulCustom, nameTag: String) {
        if(DataWatcherHelper.isNameRegistered(entity.getDataWatcher_I, DataWatcherNameTag))
            DataWatcherHelper.updateObject(entity.getDataWatcher_I, DataWatcherNameTag, nameTag)
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
