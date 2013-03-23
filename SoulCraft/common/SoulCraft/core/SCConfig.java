package SoulCraft.core;

import net.minecraftforge.common.Configuration;

public class SCConfig {

	public static void configure(Configuration config) {
		config.load();
		//Blocks
		DefaultProps.OreTitaniumID = config.getBlock("Titanium Ore", 1320).getInt();
		DefaultProps.OreIsolatziumID = config.getBlock("Soul Crystal Ore", 1321).getInt();
		DefaultProps.CompressorID = config.getBlock("Compressor", 1322).getInt();
		DefaultProps.IsolatziumCrystalID = config.getBlock("Isolatzium Crystal", 1323).getInt();
		DefaultProps.CrystalStandID = config.getBlock("Crystal Stand", 1324).getInt();
		DefaultProps.MonsterEggID = config.getBlock("Monster Egg", 1325).getInt();
		//Items
		DefaultProps.TitaniumIngotID = config.getItem("Titanium Ingot", 4520).getInt();
		DefaultProps.ShardIsolatziumID = config.getItem("Isolatzium Shards", 4521).getInt();
		//Booleans
		DefaultProps.fancyCompressorRenderer = config.get(Configuration.CATEGORY_GENERAL, "Fancy Compressor rendering", true).getBoolean(true);
		DefaultProps.spawnTitanium = config.get(Configuration.CATEGORY_GENERAL, "Spawn Titanium Ore in the world", true).getBoolean(true);
		DefaultProps.spawnOreSoulCrystal = config.get(Configuration.CATEGORY_GENERAL, "Spawn Soul Crystal Ore (all colours) in the world", true).getBoolean(true);
		//other ints
		DefaultProps.CompressorRenderDistance = config.get(Configuration.CATEGORY_GENERAL, "Range the closest player must be in to render the item inside the Compressor", 16).getInt();
		
		config.save();
	}
	
}