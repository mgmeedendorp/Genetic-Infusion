package com.seremis.geninfusion.api.genetics

import com.seremis.geninfusion.api.util.{INBTTagable, TypedName}

trait IAlleleData[A] extends INBTTagable {

    def getName: TypedName[A]
    def getData: A
    def isDominant: Boolean

}
