package com.seremis.geninfusion.register

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.lib.FunctionLib
import com.seremis.geninfusion.soulentity.EntityMethodGetId

object RegisterEntityMethods extends Register {

    override def register() {
        GIApiInterface.entityMethodRegistry.register(FunctionLib.FuncEntityGetEntityId, new EntityMethodGetId())
    }

    override def registerClient() {}
    override def registerServer() {}
}
