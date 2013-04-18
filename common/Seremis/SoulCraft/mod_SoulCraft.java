package Seremis.SoulCraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.Configuration;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.DamageCompressor;
import Seremis.SoulCraft.core.DefaultProps;
import Seremis.SoulCraft.core.SCConfig;
import Seremis.SoulCraft.core.SCCreativeTab;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.helper.RecipeHelper;
import Seremis.SoulCraft.helper.SCLogger;
import Seremis.SoulCraft.items.ModItems;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
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
		CommonProxy.proxy.registerHandlers();
		ModBlocks.init();
		ModItems.init();
        CommonProxy.proxy.registerRendering();
		RecipeHelper.initRecipes();
		RecipeHelper.initSmelting();
		LanguageRegistry.instance().addStringLocalization("itemGroup.SoulCraft", "en_US", "SoulCraft");
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {

	}
}
