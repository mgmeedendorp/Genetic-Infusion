package com.seremis.geninfusion.api.soulentity

import com.seremis.geninfusion.api.genetics.ISoul
import com.seremis.geninfusion.api.util.{FunctionName, VariableName}
import net.minecraft.entity.EntityLiving

trait ISoulEntity {

    implicit def asLiving = this.asInstanceOf[EntityLiving]

    def getSoul: ISoul

    def getVar[A](name: VariableName[A]): A
    def setVar[A](name: VariableName[A], value: A)
    def makePersistent(name: VariableName[_])

    def callMethod[A](name: FunctionName[A], args: Any*): A
}
