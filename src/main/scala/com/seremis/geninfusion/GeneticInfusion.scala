package com.seremis.geninfusion

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.lib.Genes
import com.seremis.geninfusion.proxy.CommonProxy
import com.seremis.geninfusion.register._
import com.seremis.geninfusion.registry.{DataTypeRegistry, GeneDefaultsRegistry, GeneRegistry}
import net.minecraft.entity.monster.EntityZombie
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
    def preInit(event: FMLPreInitializationEvent) {
        logger = event.getModLog

        GIApiInterface.dataTypeRegistry = new DataTypeRegistry
        GIApiInterface.geneDefaultsRegistry = new GeneDefaultsRegistry
        GIApiInterface.geneRegistry = new GeneRegistry
    }

    @EventHandler
    def init(event: FMLInitializationEvent) {
        register(event.getSide, RegisterDataTypes, RegisterPhase.Init)
        register(event.getSide, RegisterGenes, RegisterPhase.Init)
        register(event.getSide, RegisterGeneDefaults, RegisterPhase.Init)
    }

    @EventHandler
    def postInit(event: FMLPostInitializationEvent) {
        val geneName = Genes.GeneTest

        println(GIApiInterface.geneDefaultsRegistry.getDefaultValueForClass(classOf[EntityZombie], geneName))
        println(GIApiInterface.geneDefaultsRegistry.getSoulForClass(classOf[EntityZombie]))

        logger.log(Level.INFO, ModName + " is loaded successfully.")
    }

    def register(side: Side, register: Register, phase: RegisterPhase) {
        register.initPhase = phase

        register.register()
        if(side == Side.CLIENT) register.registerClient()
        if(side == Side.SERVER) register.registerServer()
    }
}
