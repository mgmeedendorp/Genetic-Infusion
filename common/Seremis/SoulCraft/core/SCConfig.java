package Seremis.SoulCraft.core;

import net.minecraftforge.common.Configuration;
import Seremis.SoulCraft.core.lib.Blocks;
import Seremis.SoulCraft.core.lib.DefaultProps;
import Seremis.SoulCraft.core.lib.Items;

public class SCConfig {

    public static void configure(Configuration config) {
        config.load();
        // Blocks
        Blocks.oreTitaniumID = config.getBlock(Blocks.ORE_TITANIUM_LOCALIZED_NAME, 1320).getInt();
        Blocks.oreIsolatziumID = config.getBlock(Blocks.ORE_ISOLATZIUM_LOCALIZED_NAME, 1321).getInt();
        Blocks.compressorID = config.getBlock(Blocks.COMPRESSOR_LOCALIZED_NAME, 1322).getInt();
        Blocks.crystalID = config.getBlock(Blocks.CRYSTAL_LOCALIZED_NAME, 1323).getInt();
        Blocks.crystalStandID = config.getBlock(Blocks.CRYSTAL_STAND_LOCALIZED_NAME, 1324).getInt();
        Blocks.bushID = config.getBlock(Blocks.BUSH_LOCALIZED_NAME, 1325).getInt();
        Blocks.heatIOID = config.getBlock(Blocks.HEAT_IO_LOCALIZED_NAME, 1326).getInt();
        Blocks.connectedGlassID = config.getBlock(Blocks.CONNECTED_GLASS_LOCALIZED_NAME, 1327).getInt();
        Blocks.titaniumBrickID = config.getBlock(Blocks.TITANIUM_BRICK_LOCALIZED_NAME, 1328).getInt();
        Blocks.stationControllerID = config.getBlock(Blocks.STATION_CONTROLLER_LOCALIZED_NAME, 1329).getInt();
        Blocks.itemIOID = config.getBlock(Blocks.ITEM_IO_LOCALIZED_NAME, 1330).getInt();
        Blocks.biomeHeatGeneratorID = config.getBlock(Blocks.BIOME_HEAT_GENERATOR_LOCALIZED_NAME, 1331).getInt();
        Blocks.monsterEggID = config.getBlock(Blocks.MONSTER_EGG_LOCALIZED_NAME, 1332).getInt();
        // Items
        Items.titaniumIngotID = config.getItem(Items.TITANIUM_INGOT_LOCALIZED_NAME, 4520).getInt();
        Items.crystalShardID = config.getItem(Items.CRYSTAL_SHARD_LOCALIZED_NAME, 4521).getInt();
        Items.titaniumPlateID = config.getItem(Items.TITANIUM_PLATE_LOCALIZED_NAME, 4522).getInt();
        Items.crystalAlloyID = config.getItem(Items.CRYSTAL_ALLOY_LOCALIZED_NAME, 4523).getInt();
        Items.transporterModulesID = config.getItem(Items.TRANSPORTER_MODULES_LOCALIZED_NAME, 4534).getInt();
        Items.berryID = config.getItem(Items.BERRY_LOCALIZED_NAME, 4535).getInt();
        Items.kernelID = config.getItem(Items.KERNEL_LOCALIZED_NAME, 4536).getInt();
        Items.thermometerID = config.getItem(Items.THERMOMETER_LOCALIZED_NAME, 4537).getInt();
        // Booleans
        DefaultProps.fancyCompressorRenderer = config.get(Configuration.CATEGORY_GENERAL, "Fancy Compressor rendering", true).getBoolean(true);
        DefaultProps.spawnTitanium = config.get(Configuration.CATEGORY_GENERAL, "Spawn Titanium Ore in the world", true).getBoolean(true);
        DefaultProps.spawnOreSoulCrystal = config.get(Configuration.CATEGORY_GENERAL, "Spawn Soul Crystal Ore (all colours) in the world", true).getBoolean(true);

        config.save();
    }

}