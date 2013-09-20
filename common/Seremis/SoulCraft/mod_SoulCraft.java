package Seremis.SoulCraft;

import java.util.logging.Level;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.DamageCompressor;
import Seremis.SoulCraft.core.SCConfig;
import Seremis.SoulCraft.core.SCCreativeTab;
import Seremis.SoulCraft.core.lib.DefaultProps;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.entity.ModEntity;
import Seremis.SoulCraft.handler.EventHandlerSC;
import Seremis.SoulCraft.handler.GuiHandler;
import Seremis.SoulCraft.handler.PlayerHandlerSC;
import Seremis.SoulCraft.handler.ServerTickHandler;
import Seremis.SoulCraft.helper.RecipeHelper;
import Seremis.SoulCraft.helper.SCLogger;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.misc.bush.BushManager;
import Seremis.SoulCraft.network.PacketHandler;
import Seremis.SoulCraft.util.structure.ModStructures;
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
public class mod_SoulCraft {

    @Instance(DefaultProps.ID)
    public static mod_SoulCraft instance;

    public static CreativeTabs CreativeTab = new SCCreativeTab("SoulCraft");

    public static DamageSource damageCompressor = new DamageCompressor("compressed");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        SCLogger.init();
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
        GameRegistry.registerPlayerTracker(new PlayerHandlerSC());
        RecipeHelper.initRecipes();
        RecipeHelper.initSmelting();
        LanguageRegistry.instance().addStringLocalization("itemGroup.SoulCraft", "en_US", "SoulCraft");
        MinecraftForge.EVENT_BUS.register(new EventHandlerSC());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        SCLogger.log(Level.INFO, DefaultProps.name + " is loaded successfully.");
        BushManager.init();
    }
}
