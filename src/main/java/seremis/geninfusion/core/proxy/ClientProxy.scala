package seremis.geninfusion.core.proxy

import cpw.mods.fml.client.FMLClientHandler
import cpw.mods.fml.client.registry.{ClientRegistry, RenderingRegistry}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.Entity
import seremis.geninfusion.client.render.{RenderEntitySoul, RenderCrystal}
import seremis.geninfusion.entity.EntitySoul
import seremis.geninfusion.lib.RenderIds
import seremis.geninfusion.soul.entity.render.RenderEntitySoulCustom
import seremis.geninfusion.soul.entity.{EntitySoulCustom, EntitySoulCustomCreature}
import seremis.geninfusion.tileentity.TileCrystal

class ClientProxy extends CommonProxy {

    @SideOnly(Side.CLIENT)
    override def registerRendering() {
        RenderingRegistry.registerBlockHandler(RenderIds.crystalRenderID, new RenderCrystal())

        RenderingRegistry.registerEntityRenderingHandler(classOf[EntitySoul], new RenderEntitySoul)

        RenderingRegistry.registerEntityRenderingHandler(classOf[EntitySoulCustom], new RenderEntitySoulCustom)
        RenderingRegistry.registerEntityRenderingHandler(classOf[EntitySoulCustomCreature], new RenderEntitySoulCustom)

        ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileCrystal], new RenderCrystal())
    }

    override def removeEntity(entity: Entity) {
        super.removeEntity(entity)

        if(isRenderWorld(entity.worldObj)) {
            entity.worldObj.removeEntity(entity)
        }
    }

    override def playerName(): String = FMLClientHandler.instance().getClient.thePlayer.getDisplayName

    override def registerHandlers() {}
}
