package seremis.geninfusion

import cpw.mods.fml.common.Mod.{EventHandler, Instance}
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.{FMLCommonHandler, Mod, SidedProxy}
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
import org.apache.logging.log4j.{Level, Logger}
import seremis.geninfusion.api.soul.SoulHelper
import seremis.geninfusion.block.ModBlocks
import seremis.geninfusion.core.proxy.CommonProxy
import seremis.geninfusion.core.{GIConfig, GICreativeTab}
import seremis.geninfusion.entity.ModEntity
import seremis.geninfusion.handler.GIEventHandler
import seremis.geninfusion.helper.{MCPNames, RecipeHelper}
import seremis.geninfusion.item.ModItems
import seremis.geninfusion.lib.{DefaultProps, Localizations}
import seremis.geninfusion.network.ModPackets
import seremis.geninfusion.soul._
import seremis.geninfusion.soul.entity.render.ModelEntitySoulCustom

@Mod(modid = DefaultProps.ID, name = DefaultProps.Name, version = DefaultProps.Version, acceptedMinecraftVersions = DefaultProps.AcceptedMinecraftVersions, modLanguage = DefaultProps.ModLanguage)
object GeneticInfusion {

    val creativeTab = new GICreativeTab(DefaultProps.ID)

    var logger: Logger = null

    @Instance(DefaultProps.ID)
    val instance = this

    @SidedProxy(clientSide = Localizations.LocClientProxy, serverSide = Localizations.LocCommonProxy)
    var commonProxy: CommonProxy = null

    @EventHandler
    def preInit(event: FMLPreInitializationEvent): Unit = {
        logger = event.getModLog
        GIConfig.configure(new Configuration(event.getSuggestedConfigurationFile))

        MCPNames.init()

        SoulHelper.geneRegistry = new GeneRegistry
        SoulHelper.traitRegistry = new TraitRegistry
        SoulHelper.standardSoulRegistry = StandardSoulRegistry
        SoulHelper.instanceHelper = new InstanceHelper
        SoulHelper.entityModel = new ModelEntitySoulCustom
        SoulHelper.animationRegistry = new AnimationRegistry
        SoulHelper.alleleTypeRegistry = new AlleleTypeRegistry

        ModPackets.init()
        ModBlocks.init()
        ModItems.init()
        ModEntity.init()
        ModSouls.init()
    }

    @EventHandler
    def init(event: FMLInitializationEvent) {
        commonProxy.registerRendering()
        commonProxy.registerHandlers()

        RecipeHelper.initRecipes()
        RecipeHelper.initSmelting()
        FMLCommonHandler.instance.bus.register(new GIEventHandler)
        MinecraftForge.EVENT_BUS.register(new GIEventHandler)
        ModSouls.postInit()
    }

    @EventHandler
    def postInit(event: FMLPostInitializationEvent) {
        logger.log(Level.INFO, DefaultProps.Name + " is loaded successfully.")
    }
}
