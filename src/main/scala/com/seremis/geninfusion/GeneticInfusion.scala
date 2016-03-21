package com.seremis.geninfusion

import com.seremis.geninfusion.api.registry.GIRegistry
import com.seremis.geninfusion.proxy.CommonProxy
import com.seremis.geninfusion.register.{Register, RegisterDataTypes, RegisterPhase}
import com.seremis.geninfusion.registry.DataTypeRegistry
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.{Mod, SidedProxy}
import net.minecraftforge.fml.relauncher.Side
import org.apache.logging.log4j.{Level, Logger}

@Mod(modid = GeneticInfusion.ModId, version = GeneticInfusion.ModVersion, name = GeneticInfusion.ModName, modLanguage = GeneticInfusion.ModLanguage, acceptedMinecraftVersions = GeneticInfusion.ModMinecraftVersion)
object GeneticInfusion {

    final val ModId = "geninfusion"
    final val ModName = "Genetic Infusion"
    final val ModVersion = "1.9-1.0.0"
    final val ModLanguage = "scala"
    final val ModMinecraftVersion = "1.9"
    final val ModCommonProxy = "com.seremis." + ModId + ".proxy.CommonProxy"
    final val ModClientProxy = "com.seremis." + ModId + ".proxy.ClientProxy"
    final val PacketChannel = ModId


    @SidedProxy(clientSide = ModCommonProxy, serverSide = ModClientProxy)
    var proxy: CommonProxy = _

    var logger: Logger = _

    @EventHandler
    def preInit(event: FMLPreInitializationEvent): Unit = {
        logger = event.getModLog

        GIRegistry.dataTypeRegistry = new DataTypeRegistry
    }

    @EventHandler
    def init(event: FMLInitializationEvent): Unit = {
        register(event.getSide, RegisterDataTypes, RegisterPhase.Init)
    }

    @EventHandler
    def postInit(event: FMLPostInitializationEvent): Unit = {
        logger.log(Level.INFO, ModName + " is loaded successfully.")
    }

    def register(side: Side, register: Register, phase: RegisterPhase) {
        register.initPhase = phase

        register.register()
        if(side == Side.CLIENT) register.registerClient()
        if(side == Side.SERVER) register.registerServer()
    }
}
