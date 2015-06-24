package seremis.geninfusion.soul.traits

import net.minecraft.util.{DamageSource, ResourceLocation}
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

    override def onDeath(entity: IEntitySoulCustom, source: DamageSource) {
        //TODO make some texture thingy to ensure no infinite textures
        if(!entity.getWorld.isRemote && !entity.getSoulPreserved) {
            GITextureHelper.deleteTexture(toResource(getEntityTexture(entity)))
            GITextureHelper.deleteTexture(toResource(SoulHelper.geneRegistry.getChromosomeFor(entity, Genes.GENE_TEXTURE).get.getRecessive.getAlleleData.asInstanceOf[String]))
        }
    }

    def toResource(string: String): ResourceLocation = new ResourceLocation(string)
}