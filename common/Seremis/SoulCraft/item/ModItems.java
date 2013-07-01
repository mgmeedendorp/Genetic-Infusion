package Seremis.SoulCraft.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Seremis.SoulCraft.core.lib.DefaultProps;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModItems {

	public static SCItem ingotTitanium;
	public static SCItem shardIsolatzium;
	public static SCItem plateTitanium;
	public static SCItem alloyIsolatzium;
	public static SCItem transporterEngines;
	public static SCItem transporterStorage;
	
	public static void init() {
		ingotTitanium = new SCItem(DefaultProps.TitaniumIngotID).setUnlocalizedName("ingotTitanium");
		shardIsolatzium = new ShardIsolatzium(DefaultProps.ShardIsolatziumID);
		plateTitanium = new SCItem(DefaultProps.TitaniumPlateID).setUnlocalizedName("plateTitanium");
		alloyIsolatzium = new AlloyIsolatzium(DefaultProps.IsolatziumAlloyID);
		transporterEngines = new SCItem(DefaultProps.TransporterEnginesID).setUnlocalizedName("transporterEngines");
		transporterStorage = new SCItem(DefaultProps.TransporterStorageID).setUnlocalizedName("transporterStorage");
		
		LanguageRegistry.addName(ingotTitanium, "Titanium Ingot");
		LanguageRegistry.addName(new ItemStack(shardIsolatzium, 1, 0), "Red Isolatzium shard");
		LanguageRegistry.addName(new ItemStack(shardIsolatzium, 1, 1), "Green Isolatzium shard");
		LanguageRegistry.addName(new ItemStack(shardIsolatzium, 1, 2), "Blue Isolatzium shard");
		LanguageRegistry.addName(new ItemStack(shardIsolatzium, 1, 3), "Black Isolatzium shard");
		LanguageRegistry.addName(plateTitanium, "Titanium Plate");
		LanguageRegistry.addName(new ItemStack(alloyIsolatzium, 1, 0), "Red Isolatzium Alloy");
		LanguageRegistry.addName(new ItemStack(alloyIsolatzium, 1, 1), "Green Isolatzium Alloy");
		LanguageRegistry.addName(new ItemStack(alloyIsolatzium, 1, 2), "Blue Isolatzium Alloy");
		LanguageRegistry.addName(new ItemStack(alloyIsolatzium, 1, 3), "Black Isolatzium Alloy");
		LanguageRegistry.addName(transporterEngines, "Engine Module");
		LanguageRegistry.addName(transporterStorage, "Storage Module");
		
		oreDictionary();
	}
	
	public static void oreDictionary() {
		OreDictionary.registerOre("ingotTitanium", ingotTitanium);
		OreDictionary.registerOre("shardIsolatziumRed", new ItemStack(shardIsolatzium, 1, 0));
		OreDictionary.registerOre("shardIsolatziumGreen", new ItemStack(shardIsolatzium, 1,1));
		OreDictionary.registerOre("shardIsolatziumBlue", new ItemStack(shardIsolatzium, 1, 2));
		OreDictionary.registerOre("shardIsolatziumBlack", new ItemStack(shardIsolatzium, 1, 3));
		OreDictionary.registerOre("plateTitanium", plateTitanium);
		OreDictionary.registerOre("alloyIsolatziumRed", new ItemStack(alloyIsolatzium, 1, 0));
		OreDictionary.registerOre("alloyIsolatziumGreen", new ItemStack(alloyIsolatzium, 1, 1));
		OreDictionary.registerOre("alloyIsolatziumBlue", new ItemStack(alloyIsolatzium, 1, 2));
		OreDictionary.registerOre("alloyIsolatziumBlack", new ItemStack(alloyIsolatzium, 1, 3));
	}
}
