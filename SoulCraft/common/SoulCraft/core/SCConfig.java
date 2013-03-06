package SoulCraft.core;

import net.minecraftforge.common.Configuration;

public class SCConfig {

	private static DefaultProps id;
	
	public static void configure(Configuration config) {
		config.load();
		//Blocks
		id.OreTitaniumID = config.getBlock("Titanium Ore", 1320).getInt();
		id.OreIsolatziumID = config.getBlock("Soul Crystal Ore", 1321).getInt();
		id.CompressorID = config.getBlock("Compressor", 1322).getInt();
		id.MonsterEggID = config.getBlock("Monster Eggs", 1325).getInt();
		//Items
		id.TitaniumIngotID = config.getItem("Titanium Ingot", 4520).getInt();
		id.ShardIsolatziumID = config.getItem("Isolatzium Shards", 4521).getInt();
		//Booleans
		id.fancyCompressorRenderer = config.get(Configuration.CATEGORY_GENERAL, "Fancy Compressor rendering", true).getBoolean(true);
		id.spawnTitanium = config.get(Configuration.CATEGORY_GENERAL, "Spawn Titanium Ore in the world", true).getBoolean(true);
		id.spawnOreSoulCrystal = config.get(Configuration.CATEGORY_GENERAL, "Spawn Soul Crystal Ore (all colours) in the world", true).getBoolean(true);
		//other ints
		id.CompressorRenderDistance = config.get(Configuration.CATEGORY_GENERAL, "Range the closest player must be in to render the item inside the Compressor", 16).getInt();
		
		config.save();
	}
	
}