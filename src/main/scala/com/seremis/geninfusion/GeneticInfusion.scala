package com.seremis.geninfusion

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.genetics.ISoul
import com.seremis.geninfusion.api.lib.{FunctionLib, Genes}
import com.seremis.geninfusion.api.util.TypedName
import com.seremis.geninfusion.proxy.CommonProxy
import com.seremis.geninfusion.register._
import com.seremis.geninfusion.registry.{DataTypeRegistry, EntityMethodRegistry, GeneDefaultsRegistry, GeneRegistry}
import com.seremis.geninfusion.soulentity.SoulEntityLiving
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.nbt.NBTTagCompound
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

        GIApiInterface.dataTypeRegistry = new DataTypeRegistry
        GIApiInterface.entityMethodRegistry = new EntityMethodRegistry
        GIApiInterface.geneDefaultsRegistry = new GeneDefaultsRegistry
        GIApiInterface.geneRegistry = new GeneRegistry
    }

    @EventHandler
    def init(event: FMLInitializationEvent) {
        register(event.getSide, RegisterDataTypes, RegisterPhase.Init)
        register(event.getSide, RegisterGenes, RegisterPhase.Init)
        register(event.getSide, RegisterGeneDefaults, RegisterPhase.Init)
        register(event.getSide, RegisterEntityMethods, RegisterPhase.Init)
    }

    @EventHandler
    def postInit(event: FMLPostInitializationEvent) {
        val method = FunctionLib.FuncEntityGetEntityId

        println(method)

        val m = GIApiInterface.entityMethodRegistry.getMethodsForName(method)

        println(m)

        val entity = new SoulEntityLiving(null, GIApiInterface.geneDefaultsRegistry.getSoulForClass(classOf[EntityZombie]))

        println(entity)

        entity.getEntityId


        val geneName = Genes.GeneTest

        println(geneName)


        val compound = new NBTTagCompound
        GIApiInterface.dataTypeRegistry.writeValueToNBT(compound, "1", classOf[TypedName[_]], geneName)

        println(compound)

        val out = GIApiInterface.dataTypeRegistry.readValueFromNBT(compound, "1", classOf[TypedName[_]])

        println(out)

        println(out == geneName)

        println(GIApiInterface.geneDefaultsRegistry.getDefaultValueForClass(classOf[EntityZombie], geneName))

        val soul = GIApiInterface.geneDefaultsRegistry.getSoulForClass(classOf[EntityZombie])
        println(soul)

        val nbt = new NBTTagCompound

        GIApiInterface.dataTypeRegistry.writeValueToNBT(nbt, "soul", classOf[ISoul], soul)

        println(nbt)

        val out2 = GIApiInterface.dataTypeRegistry.readValueFromNBT(nbt, "soul", classOf[ISoul])

        println(out2)

        println(soul == out2)

        logger.log(Level.INFO, ModName + " is loaded successfully.")
    }

    def register(side: Side, register: Register, phase: RegisterPhase) {
        register.initPhase = phase

        register.register()
        if(side == Side.CLIENT) register.registerClient()
        if(side == Side.SERVER) register.registerServer()
    }
}
