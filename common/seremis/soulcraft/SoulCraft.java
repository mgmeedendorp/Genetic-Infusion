package seremis.soulcraft;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import seremis.soulcraft.block.ModBlocks;
import seremis.soulcraft.core.SCConfig;
import seremis.soulcraft.core.SCCreativeTab;
import seremis.soulcraft.core.lib.DefaultProps;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.entity.ModEntity;
import seremis.soulcraft.handler.EventHandlerSC;
import seremis.soulcraft.handler.GuiHandler;
import seremis.soulcraft.handler.PlayerTrackerSC;
import seremis.soulcraft.handler.ServerTickHandler;
import seremis.soulcraft.helper.RecipeHelper;
import seremis.soulcraft.item.ModItems;
import seremis.soulcraft.misc.DamageCompressor;
import seremis.soulcraft.misc.bush.BushManager;
import seremis.soulcraft.network.PacketHandler;
import seremis.soulcraft.util.structure.ModStructures;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = DefaultProps.ID, name = DefaultProps.name, version = DefaultProps.version, acceptedMinecraftVersions = DefaultProps.acceptedMinecraftVersions)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {DefaultProps.PACKET_CHANNEL}, packetHandler = PacketHandler.class)
public class SoulCraft {

    @Instance(DefaultProps.ID)
    public static SoulCraft instance;

    public static CreativeTabs CreativeTab = new SCCreativeTab("SoulCraft");

    public static DamageSource damageCompressor = new DamageCompressor("compressed");
    
    public static Logger logger = Logger.getLogger("SoulCraft");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger.setParent(FMLCommonHandler.instance().getFMLLogger());
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        SCConfig.configure(config);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ModBlocks.init();
        ModItems.init();
        ModEntity.init();
        ModStructures.init();
        CommonProxy.proxy.registerRendering();
        CommonProxy.proxy.registerHandlers();
        TickRegistry.registerTickHandler(ServerTickHandler.instance, Side.SERVER);
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
        GameRegistry.registerPlayerTracker(new PlayerTrackerSC());
        RecipeHelper.initRecipes();
        RecipeHelper.initSmelting();
        LanguageRegistry.instance().addStringLocalization("itemGroup.SoulCraft", "en_US", "SoulCraft");
        MinecraftForge.EVENT_BUS.register(new EventHandlerSC());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.log(Level.INFO, DefaultProps.name + " is loaded successfully.");
        BushManager.init();
    }
}
