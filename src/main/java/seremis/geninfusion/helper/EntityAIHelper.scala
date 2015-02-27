package seremis.geninfusion.helper

import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.{EntityAITasks, EntityAIBase}

object EntityAIHelper {

    def entityAIContains(entity: EntityLiving, clss: Class[_ <: EntityAIBase]): Boolean = {
        getEntityAITask(entity, clss) != null
    }

    def getEntityAITask(entity: EntityLiving, clss: Class[_ <: EntityAIBase]): EntityAIBase = {
        val tasks = entity.tasks.taskEntries
        val targetTasks = entity.targetTasks.taskEntries

        for(i <- 0 until tasks.size()) {
            if(tasks.get(i).asInstanceOf[EntityAITasks#EntityAITaskEntry].action.getClass.isAssignableFrom(clss)) {
                return tasks.get(i).asInstanceOf[EntityAITasks#EntityAITaskEntry].action
            }
        }

        for(i <- 0 until targetTasks.size()) {
            if(targetTasks.get(i).asInstanceOf[EntityAITasks#EntityAITaskEntry].action.getClass.isAssignableFrom(clss)) {
                return targetTasks.get(i).asInstanceOf[EntityAITasks#EntityAITaskEntry].action
            }
        }

        null
    }

    def getEntityAITaskIndex(entity: EntityLiving, clss: Class[_ <: EntityAIBase]): Int = {
        val task = getEntityAITask(entity, clss)

        for(i <- 0 until entity.tasks.taskEntries.size()) {
            if(entity.tasks.taskEntries.get(i).asInstanceOf[EntityAITasks#EntityAITaskEntry].action == task) {
                return entity.tasks.taskEntries.get(i).asInstanceOf[EntityAITasks#EntityAITaskEntry].priority
            }
        }

        for(i <- 0 until entity.targetTasks.taskEntries.size()) {
            if(entity.targetTasks.taskEntries.get(i).asInstanceOf[EntityAITasks#EntityAITaskEntry].action == task) {
                return entity.targetTasks.taskEntries.get(i).asInstanceOf[EntityAITasks#EntityAITaskEntry].priority
            }
        }

        10
    }

    def getEntityAIVariable(entity: EntityLiving, clss: Class[_ <: EntityAIBase], name: String): Any = {
        val task = getEntityAITask(entity, clss)

        if(task != null)
            return GIReflectionHelper.getField(task, name)

        return 0
    }
}
