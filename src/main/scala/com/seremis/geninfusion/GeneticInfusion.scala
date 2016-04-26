package com.seremis.geninfusion

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.handler.GIEventHandler
import com.seremis.geninfusion.proxy.CommonProxy
import com.seremis.geninfusion.register._
import com.seremis.geninfusion.registry._
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.{Mod, SidedProxy}
import net.minecraftforge.fml.relauncher.Side
import org.apache.logging.log4j.{Level, Logger}

@Mod(modid = GeneticInfusion.ModId, version = GeneticInfusion.ModVersion, name = GeneticInfusion.ModName, modLanguage = GeneticInfusion.ModLanguage, acceptedMinecraftVersions = GeneticInfusion.ModMinecraftVersion)
object GeneticInfusion {

    final val ModId = "geninfusion"
    final val ModName = "Genetic Infusion"
    final val ModVersion = "@MOD_VERSION@"
    final val ModLanguage = "scala"
    final val ModMinecraftVersion = "@MC_VERSION@"
    final val ModCommonProxy = "com.seremis." + ModId + ".proxy.CommonProxy"
    final val ModClientProxy = "com.seremis." + ModId + ".proxy.ClientProxy"
    final val PacketChannel = ModId


    @SidedProxy(clientSide = ModCommonProxy, serverSide = ModClientProxy)
    var proxy: CommonProxy = _

    var logger: Logger = _

    @EventHandler
    def preInit(event: FMLPreInitializationEvent) {
        logger = event.getModLog

        GIApiInterface.GIModId = ModId

        GIApiInterface.animationRegistry = new AnimationRegistry
        GIApiInterface.dataTypeRegistry = new DataTypeRegistry
        GIApiInterface.entityMethodRegistry = new EntityMethodRegistry
        GIApiInterface.geneDefaultsRegistry = new GeneDefaultsRegistry
        GIApiInterface.geneRegistry = new GeneRegistry
    }

    @EventHandler
    def init(event: FMLInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(new GIEventHandler)

        register(event.getSide, RegisterDataTypes, RegisterPhase.Init)
        register(event.getSide, RegisterGenes, RegisterPhase.Init)
        register(event.getSide, RegisterGeneDefaults, RegisterPhase.Init)
        register(event.getSide, RegisterEntityMethods, RegisterPhase.Init)
    }

    @EventHandler
    def postInit(event: FMLPostInitializationEvent) {
        logger.log(Level.INFO, ModName + " is loaded successfully.")
    }

    def register(side: Side, register: Register, phase: RegisterPhase) {
        register.initPhase = phase

        register.register()
        if(side == Side.CLIENT) register.registerClient()
        if(side == Side.SERVER) register.registerServer()
    }
}
