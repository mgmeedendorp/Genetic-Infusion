package com.seremis.geninfusion.genetics

import com.seremis.geninfusion.api.genetics.IAllele

class Allele[A](data: A, dominant: Boolean) extends IAllele[A] {
    override def getData: A = data

    override def isDominant: Boolean = dominant

    override def copy(): IAllele[A] = new Allele(data, dominant)

    override def toString = "Allele[data = '" + data + "', dominant = '" + dominant + "']"
}
