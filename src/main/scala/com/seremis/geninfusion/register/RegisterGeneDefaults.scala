package com.seremis.geninfusion.register

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.lib.Genes
import com.seremis.geninfusion.genetics.{Allele, Chromosome}
import net.minecraft.entity.monster.EntityZombie


object RegisterGeneDefaults extends Register {

    override def register() {
        GIApiInterface.geneDefaultsRegistry.register(classOf[EntityZombie], Genes.GeneTest, geneData(false, false, true, false))
    }

    def geneData[A](default1: A, default2: A, dominant1: Boolean, dominant2: Boolean): Chromosome[A] = Chromosome(new Allele(default1, dominant1), new Allele(default2, dominant2))

    override def registerClient() {}
    override def registerServer() {}
}
