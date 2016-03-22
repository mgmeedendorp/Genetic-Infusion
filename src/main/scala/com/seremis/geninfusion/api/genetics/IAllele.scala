package com.seremis.geninfusion.api.genetics

trait IAllele[A] {

    def getData: A
    def isDominant: Boolean
    def copy(): IAllele[A]
}
