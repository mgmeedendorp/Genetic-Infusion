package SoulCraft.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import SoulCraft.core.DefaultProps;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModItems {

	public static Item IngotTitanium;
	public static Item ShardIsolatzium;
	
	public static void init() {
		IngotTitanium = new SCItem(DefaultProps.TitaniumIngotID).setUnlocalizedName("ingotTitanium");
		ShardIsolatzium = new ShardIsolatzium(DefaultProps.ShardIsolatziumID);
		
		LanguageRegistry.addName(IngotTitanium, "Titanium Ingot");
		LanguageRegistry.addName(new ItemStack(ShardIsolatzium, 1, 0), "Red Isolatzium shard");
		LanguageRegistry.addName(new ItemStack(ShardIsolatzium, 1, 1), "Green Isolatzium shard");
		LanguageRegistry.addName(new ItemStack(ShardIsolatzium, 1, 2), "Blue Isolatzium shard");
		LanguageRegistry.addName(new ItemStack(ShardIsolatzium, 1, 3), "Black Isolatzium shard");
		
		oreDictionary();
		registerItems();
	}
	
	public static void oreDictionary() {
		OreDictionary.registerOre("ingotTitanium", IngotTitanium);
		OreDictionary.registerOre("shardIsolatziumRed", new ItemStack(ShardIsolatzium, 1, 0));
		OreDictionary.registerOre("shardIsolatziumGreen", new ItemStack(ShardIsolatzium, 1,1));
		OreDictionary.registerOre("shardIsolatziumBlue", new ItemStack(ShardIsolatzium, 1, 2));
		OreDictionary.registerOre("shardIsolatziumBlack", new ItemStack(ShardIsolatzium, 1, 3));
	}
	
	public static void registerItems() {
		GameRegistry.registerItem(IngotTitanium, "Titanium Ingot", DefaultProps.ID);
		GameRegistry.registerItem(ShardIsolatzium, "Isolatzium shard", DefaultProps.ID);
	}
}
