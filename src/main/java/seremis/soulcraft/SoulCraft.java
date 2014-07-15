package seremis.soulcraft;



import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import seremis.soulcraft.api.soul.TraitRegistry;
import seremis.soulcraft.block.ModBlocks;
import seremis.soulcraft.core.SCConfig;
import seremis.soulcraft.core.SCCreativeTab;
import seremis.soulcraft.core.lib.DefaultProps;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.entity.ModEntity;
import seremis.soulcraft.handler.EventHandlerSC;
import seremis.soulcraft.handler.GuiHandler;
import seremis.soulcraft.handler.ServerTickHandler;
import seremis.soulcraft.helper.RecipeHelper;
import seremis.soulcraft.item.ModItems;
import seremis.soulcraft.misc.DamageCompressor;
import seremis.soulcraft.network.PacketPipeline;
import seremis.soulcraft.soul.ModSouls;
import seremis.soulcraft.util.structure.ModStructures;
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
public class SoulCraft {

    @Instance(DefaultProps.ID)
    public static SoulCraft instance;

    public static final PacketPipeline packetPipeline = new PacketPipeline();
    
    public static CreativeTabs CreativeTab = new SCCreativeTab("SoulCraft");

    public static DamageSource damageCompressor = new DamageCompressor("compressed");
    
    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        SCConfig.configure(config);
        
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
        MinecraftForge.EVENT_BUS.register(new EventHandlerSC());
        packetPipeline.initialise();
        TraitRegistry.orderTraits();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        packetPipeline.postInitialise();
        logger.log(Level.INFO, DefaultProps.name + " is loaded successfully.");
    }
}
