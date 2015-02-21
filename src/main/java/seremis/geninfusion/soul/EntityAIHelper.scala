package seremis.geninfusion.soul

import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.EntityAIBase
import seremis.geninfusion.api.soul.IEntityAIHelper

class EntityAIHelper extends IEntityAIHelper {

    override def getEntityAIForEntitySoulCustom(aiClass: Class[_ <: EntityAIBase], args: AnyRef*): EntityAIBase = {
        var ai = tryStandardAI(aiClass, args)
        if(ai != null)
            return ai
        ai = tryCustomAI(aiClass, args)
        if(ai != null)
            return ai
        //TODO an AI registry?
        null
    }

    def tryStandardAI(ai: Class[_ <: EntityAIBase], args: AnyRef*): EntityAIBase = {
        val constructors = ai.getConstructors

        for(constructor <- constructors) {
            if(constructor.getParameterTypes()(0).isAssignableFrom(classOf[EntityLiving])) {
                return constructor.newInstance(args).asInstanceOf[EntityAIBase]
            }
        }
        null
    }

    def tryCustomAI(ai: Class[_ <: EntityAIBase], args: AnyRef*): EntityAIBase = {
        val nameArray = ai.getName.split(".")
        val name = "seremis.geninfusion.soul.entity.ai." + nameArray(nameArray.length-1) + "Custom"

        try {
            val customClass = Class.forName(name)

            if(customClass != null) {
                val customConstructor = customClass.getConstructor(args.map(s => s.getClass):_*)

                return customConstructor.newInstance(args).asInstanceOf[EntityAIBase]
            }
        } catch {
            case e: Exception => false
        }
        null
    }
}
