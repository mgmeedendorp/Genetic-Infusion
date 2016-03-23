package com.seremis.geninfusion.api.soulentity

import com.seremis.geninfusion.api.genetics.ISoul
import com.seremis.geninfusion.api.util.{INBTTagable, TypedName}
import net.minecraft.entity.EntityLiving

trait ISoulEntity extends INBTTagable {

    implicit def asLiving = this.asInstanceOf[EntityLiving]

    def getSoul: ISoul

    def getVar[A](name: TypedName[A]): A
    def setVar[A](name: TypedName[A], value: A)
    def makePersistent(name: TypedName[_])

    def callMethod[A](name: TypedName[A], args: Any*): A
}
