package voidrunner101.SoulCraft.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.Configuration;
import voidrunner101.SoulCraft.common.blocks.ModBlocks;
import voidrunner101.SoulCraft.common.core.DamageCompressor;
import voidrunner101.SoulCraft.common.core.DefaultProps;
import voidrunner101.SoulCraft.common.core.SCConfig;
import voidrunner101.SoulCraft.common.core.SCCreativeTab;
import voidrunner101.SoulCraft.common.helper.RecipeHelper;
import voidrunner101.SoulCraft.common.helper.SCLogger;
import voidrunner101.SoulCraft.common.items.ModItems;
import voidrunner101.SoulCraft.common.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(modid = DefaultProps.ID, name = DefaultProps.name, version = DefaultProps.version, acceptedMinecraftVersions = DefaultProps.acceptedMinecraftVersions)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class mod_SoulCraft {
	
	@Instance(DefaultProps.ID)
	public static mod_SoulCraft instance;
	
	public static CreativeTabs CreativeTab = new SCCreativeTab("SoulCraft");
	
	public static DamageSource damageCompressor = new DamageCompressor("compressed");
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		SCLogger.init();
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		SCConfig.configure(config);
	}
	
	@Init
	public void init(FMLInitializationEvent event) {
		CommonProxy.proxy.registerRendering();
		ModBlocks.init();
		ModItems.init();
		RecipeHelper.initRecipes();
		RecipeHelper.initSmelting();
		LanguageRegistry.instance().addStringLocalization("itemGroup.SoulCraft", "en_US", "SoulCraft");
//		MinecraftForge.EVENT_BUS.register(new SoulLinker());
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {

	}
}
