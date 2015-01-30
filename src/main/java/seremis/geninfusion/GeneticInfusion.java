package seremis.geninfusion;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.block.ModBlocks;
import seremis.geninfusion.core.GIConfig;
import seremis.geninfusion.core.GICreativeTab;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.entity.ModEntity;
import seremis.geninfusion.handler.GIEventHandler;
import seremis.geninfusion.helper.RecipeHelper;
import seremis.geninfusion.item.ModItems;
import seremis.geninfusion.lib.DefaultProps;
import seremis.geninfusion.network.PacketPipeline;
import seremis.geninfusion.soul.*;

@Mod(modid = DefaultProps.ID, name = DefaultProps.name, version = DefaultProps.version, acceptedMinecraftVersions = DefaultProps.acceptedMinecraftVersions)
public class GeneticInfusion {

    @Instance(DefaultProps.ID)
    public static GeneticInfusion instance;

    public static final PacketPipeline packetPipeline = new PacketPipeline();

    public static CreativeTabs CreativeTab = new GICreativeTab(DefaultProps.ID);

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        GIConfig.configure(config);

        SoulHelper.geneRegistry = new GeneRegistry();
        SoulHelper.traitRegistry = new TraitRegistry();
        SoulHelper.standardSoulRegistry = new StandardSoulRegistry();
        SoulHelper.instanceHelper = new InstanceHelper();
        SoulHelper.traitHandler = new TraitHandler();
        SoulHelper.entityAIHelper = new EntityAIHelper();

        ModBlocks.init();
        ModItems.init();
        ModEntity.init();
        ModSouls.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        CommonProxy.instance.registerRendering();
        CommonProxy.instance.registerHandlers();

        RecipeHelper.initRecipes();
        RecipeHelper.initSmelting();
        FMLCommonHandler.instance().bus().register(new GIEventHandler());
        MinecraftForge.EVENT_BUS.register(new GIEventHandler());
        packetPipeline.initialise();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        packetPipeline.postInitialise();
        logger.log(Level.INFO, DefaultProps.name + " is loaded successfully.");
    }
}
