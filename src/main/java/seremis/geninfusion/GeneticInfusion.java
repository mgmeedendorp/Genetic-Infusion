package seremis.geninfusion;



import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import seremis.geninfusion.api.soul.TraitRegistry;
import seremis.geninfusion.block.ModBlocks;
import seremis.geninfusion.core.GIConfig;
import seremis.geninfusion.core.GICreativeTab;
import seremis.geninfusion.lib.DefaultProps;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.entity.ModEntity;
import seremis.geninfusion.handler.GIEventHandler;
import seremis.geninfusion.handler.GuiHandler;
import seremis.geninfusion.handler.ServerTickHandler;
import seremis.geninfusion.helper.RecipeHelper;
import seremis.geninfusion.item.ModItems;
import seremis.geninfusion.misc.DamageCompressor;
import seremis.geninfusion.network.PacketPipeline;
import seremis.geninfusion.soul.ModSouls;
import seremis.geninfusion.util.structure.ModStructures;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = DefaultProps.ID, name = DefaultProps.name, version = DefaultProps.version, acceptedMinecraftVersions = DefaultProps.acceptedMinecraftVersions)
public class GeneticInfusion {

    @Instance(DefaultProps.ID)
    public static GeneticInfusion instance;

    public static final PacketPipeline packetPipeline = new PacketPipeline();
    
    public static CreativeTabs CreativeTab = new GICreativeTab(DefaultProps.ID);

    public static DamageSource damageCompressor = new DamageCompressor("compressed");
    
    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        GIConfig.configure(config);
        
        ModBlocks.init();
        ModItems.init();
        ModEntity.init();
        ModSouls.init();
        ModStructures.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        CommonProxy.instance.registerRendering();
        CommonProxy.instance.registerHandlers();
        
        if(event.getSide() == Side.SERVER)
        	FMLCommonHandler.instance().bus().register(ServerTickHandler.instance);
        
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        RecipeHelper.initRecipes();
        RecipeHelper.initSmelting();
        MinecraftForge.EVENT_BUS.register(new GIEventHandler());
        packetPipeline.initialise();
        TraitRegistry.orderTraits();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        packetPipeline.postInitialise();
        logger.log(Level.INFO, DefaultProps.name + " is loaded successfully.");
    }
}
