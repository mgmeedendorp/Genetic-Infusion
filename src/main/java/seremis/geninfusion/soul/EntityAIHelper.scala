package seremis.geninfusion.soul

import net.minecraft.entity.ai.EntityAIBase
import seremis.geninfusion.api.soul.IEntityAIHelper

class EntityAIHelper extends IEntityAIHelper {

    override def getEntityAIForEntitySoulCustom(aiName: String, args: AnyRef*): EntityAIBase = {
        try {
            val nameArray = aiName.split(".")
            val name = "seremis.geninfusion.soul.entity.ai." + nameArray(nameArray.length-1) + "Custom"
            val aiClass = Class.forName(name)
            val aiConstructor = aiClass.getConstructor(args.map(s => s.getClass):_*)
            val aiInstance = aiConstructor.newInstance(args).asInstanceOf[EntityAIBase]

            return aiInstance
        }
        null
    }
}
