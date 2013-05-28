package Seremis.SoulCraft.core;

import Seremis.SoulCraft.core.lib.DefaultProps;
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
		DefaultProps.PlasmaConnectorToolID = config.getItem("Plasma Tool", 4522).getInt();
		DefaultProps.PlasmaID = config.getItem("Plasma Item", 4523).getInt();
		DefaultProps.PlasmaBucketID = config.getItem("Plasma Bucket", 4524).getInt();
		//Booleans
		DefaultProps.fancyCompressorRenderer = config.get(Configuration.CATEGORY_GENERAL, "Fancy Compressor rendering", true).getBoolean(true);
		DefaultProps.spawnTitanium = config.get(Configuration.CATEGORY_GENERAL, "Spawn Titanium Ore in the world", true).getBoolean(true);
		DefaultProps.spawnOreSoulCrystal = config.get(Configuration.CATEGORY_GENERAL, "Spawn Soul Crystal Ore (all colours) in the world", true).getBoolean(true);
		
		config.save();
	}
	
}