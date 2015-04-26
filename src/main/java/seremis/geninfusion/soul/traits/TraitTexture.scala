package seremis.geninfusion.soul.traits

import net.minecraft.util.DamageSource
import net.minecraft.util.ResourceLocation
import seremis.geninfusion.api.soul.IEntitySoulCustom
import seremis.geninfusion.api.soul.SoulHelper
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.helper.GITextureHelper

class TraitTexture extends Trait {

    override def getEntityTexture(entity: IEntitySoulCustom): String = {
        try {
            SoulHelper.geneRegistry.getValueFromAllele[String](entity, Genes.GENE_TEXTURE)
        } catch {
            case e: NullPointerException => "textures/entity/zombie/zombie.png"
        }
    }

    override def onDeath(entity: IEntitySoulCustom, source: DamageSource) {
        //TODO don't delete texture if it spawns soul
        GITextureHelper.deleteTexture(toResource(getEntityTexture(entity)))
        GITextureHelper.deleteTexture(toResource(SoulHelper.geneRegistry.getChromosomeFor(entity, Genes.GENE_TEXTURE).getRecessive.getAlleleData.asInstanceOf[String]))
    }

    def toResource(string: String): ResourceLocation = new ResourceLocation(string)
}