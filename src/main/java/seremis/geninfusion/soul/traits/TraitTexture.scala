package seremis.geninfusion.soul.traits

import net.minecraft.util.ResourceLocation
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.helper.GITextureHelper
import seremis.geninfusion.lib.Localizations

class TraitTexture extends Trait {

    override def getEntityTexture(entity: IEntitySoulCustom): String = {
        try {
            SoulHelper.geneRegistry.getValueFromAllele[String](entity, Genes.GENE_TEXTURE)
        } catch {
            case e: NullPointerException => Localizations.LocModelTextures + Localizations.Blank
        }
    }

    override def setDead(entity: IEntitySoulCustom) {
        //TODO don't delete texture if it spawns soul
        if(entity.getWorld.isRemote) {
            GITextureHelper.deleteTexture(toResource(getEntityTexture(entity)))
            GITextureHelper.deleteTexture(toResource(SoulHelper.geneRegistry.getChromosomeFor(entity, Genes.GENE_TEXTURE).get.getRecessive.getAlleleData.asInstanceOf[String]))
        }
    }

    def toResource(string: String): ResourceLocation = new ResourceLocation(string)
}