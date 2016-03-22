package com.seremis.geninfusion.register

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.lib.Genes
import com.seremis.geninfusion.api.util.TypedName
import com.seremis.geninfusion.genetics.{Allele, GeneData}
import net.minecraft.entity.monster.EntityZombie


object RegisterGeneDefaults extends Register {

    override def register() {
        GIApiInterface.geneDefaultsRegistry.register(classOf[EntityZombie], geneData(Genes.GeneTest, false, false, true, false))
    }

    def geneData[A](name: TypedName[A], default1: A, default2: A, dominant1: Boolean, dominant2: Boolean): GeneData[A] = GeneData(name, new Allele(default1, dominant1), new Allele(default2, dominant2))

    override def registerClient() {}
    override def registerServer() {}
}
