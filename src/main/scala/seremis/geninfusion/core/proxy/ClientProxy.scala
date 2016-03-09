package seremis.geninfusion.core.proxy

import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraftforge.client.MinecraftForgeClient
import seremis.geninfusion.block.ModBlocks
import seremis.geninfusion.client.render.{RenderClayGolem, RenderCrystal}
import seremis.geninfusion.entity.EntityClayGolem
import seremis.geninfusion.lib.RenderIds
import seremis.geninfusion.soul.entity.render.RenderEntitySoulCustom
import seremis.geninfusion.soul.entity.{EntitySoulCustom, EntitySoulCustomCreature}
import seremis.geninfusion.tileentity.TileCrystal

class ClientProxy extends CommonProxy {

    @SideOnly(Side.CLIENT)
    override def registerRendering() {
        RenderingRegistry.registerBlockHandler(RenderIds.CrystalRenderID, new RenderCrystal)

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.crystal), new RenderCrystal)

        RenderingRegistry.registerEntityRenderingHandler(classOf[EntitySoulCustom], new RenderEntitySoulCustom)
        RenderingRegistry.registerEntityRenderingHandler(classOf[EntitySoulCustomCreature], new RenderEntitySoulCustom)

        RenderingRegistry.registerEntityRenderingHandler(classOf[EntityClayGolem], new RenderClayGolem)

        ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileCrystal], new RenderCrystal)
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
