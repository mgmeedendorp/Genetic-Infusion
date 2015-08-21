package seremis.geninfusion.soul.traits

import java.io.{File, ByteArrayInputStream}
import javax.imageio.ImageIO

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{DamageSource, ResourceLocation}
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.lib.{DefaultProps, Localizations}

class TraitTexture extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        if(entity.getWorld_I.isRemote) {
            val textureCompound: NBTTagCompound = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneTexture)
            val textureBytes = textureCompound.getByteArray("textureBytes")
            val in = new ByteArrayInputStream(textureBytes)
            val image = ImageIO.read(in)

            ImageIO.write(image, "png", new File(System.getProperty("user.home").replace("\\", "/") + "/Desktop/image.png"))

            entity.setObject("texture", image)

            val dynamicTexture = new DynamicTexture(image)

            entity.setObject("dynamicTexture", dynamicTexture)
        }
    }

    @SideOnly(Side.CLIENT)
    override def getEntityTexture(entity: IEntitySoulCustom): ResourceLocation = {
        try {
            val dynamicTexture = entity.getObject("dynamicTexture").asInstanceOf[DynamicTexture]


            Minecraft.getMinecraft.renderEngine.getDynamicTextureLocation(DefaultProps.ID + ":customEntityTexture", dynamicTexture)
        } catch {
            case e: Exception => new ResourceLocation(Localizations.LocModelTextures + Localizations.ClayGolemTransformation)
        }
    }

    override def onDeath(entity: IEntitySoulCustom, source: DamageSource) {
        if(entity.getWorld_I.isRemote) {
            val dynamicTexture = entity.getObject("dynamicTexture").asInstanceOf[DynamicTexture]

            dynamicTexture.deleteGlTexture()
        }
    }

    def toResource(string: String): ResourceLocation = new ResourceLocation(string)
}