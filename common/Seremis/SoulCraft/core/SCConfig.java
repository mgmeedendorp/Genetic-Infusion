package Seremis.SoulCraft.core;

import net.minecraftforge.common.Configuration;
import Seremis.SoulCraft.core.lib.Blocks;
import Seremis.SoulCraft.core.lib.DefaultProps;
import Seremis.SoulCraft.core.lib.Items;

public class SCConfig {

    public static void configure(Configuration config) {
        config.load();
        // Blocks
        Blocks.oreTitaniumID = config.getBlock("Titanium Ore", 1320).getInt();
        Blocks.oreIsolatziumID = config.getBlock("Soul Crystal Ore", 1321).getInt();
        Blocks.compressorID = config.getBlock("Compressor", 1322).getInt();
        Blocks.crystalID = config.getBlock("Isolatzium Crystal", 1323).getInt();
        Blocks.crystalStandID = config.getBlock("Crystal Stand", 1324).getInt();
        Blocks.bushID = config.getBlock("Berry bush", 1325).getInt();
        Blocks.heatIOID = config.getBlock("Heat I/O", 1326).getInt();
        Blocks.connectedGlassID = config.getBlock("Connected Glass", 1327).getInt();
        Blocks.titaniumBrickID = config.getBlock("Titanium Reinforced Brick", 1328).getInt();
        Blocks.stationControllerID = config.getBlock("Magnetic Station Controller", 1329).getInt();
        Blocks.monsterEggID = config.getBlock("Monster Egg", 1330).getInt();
        // Items
        Items.titaniumIngotID = config.getItem("Titanium Ingot", 4520).getInt();
        Items.crystalShardID = config.getItem("Isolatzium Shards", 4521).getInt();
        Items.titaniumPlateID = config.getItem("Titanium Plate", 4522).getInt();
        Items.crystalAlloyID = config.getItem("Isolatzium Alloy", 4523).getInt();
        Items.transporterModulesID = config.getItem("Engine Module", 4534).getInt();
        Items.berryID = config.getItem("Berry", 4535).getInt();
        Items.kernelID = config.getItem("Kernels", 4536).getInt();
        Items.thermometerID = config.getItem("Thermometer", 4537).getInt();
        // Booleans
        DefaultProps.fancyCompressorRenderer = config.get(Configuration.CATEGORY_GENERAL, "Fancy Compressor rendering", true).getBoolean(true);
        DefaultProps.spawnTitanium = config.get(Configuration.CATEGORY_GENERAL, "Spawn Titanium Ore in the world", true).getBoolean(true);
        DefaultProps.spawnOreSoulCrystal = config.get(Configuration.CATEGORY_GENERAL, "Spawn Soul Crystal Ore (all colours) in the world", true).getBoolean(true);

        config.save();
    }

}