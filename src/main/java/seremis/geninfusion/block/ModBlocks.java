package seremis.geninfusion.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import seremis.geninfusion.lib.Blocks;
import seremis.geninfusion.lib.DefaultProps;
import seremis.geninfusion.lib.Tiles;
import seremis.geninfusion.tileentity.*;
import seremis.geninfusion.world.GIWorldGenerator;

public class ModBlocks {

    public static Block oreTitanium;
    public static BlockOreIsolatzium oreIsolatzium;
    public static BlockCompressor compressor;
    public static BlockCrystal crystal;
    public static BlockCrystalStand crystalStand;
    public static BlockHeatIO heatIO;
    public static BlockConnectedGlass connectedGlass;
    public static Block titaniumBrick;
    public static BlockStationController stationController;
    public static BlockItemIO itemIO;
    public static BlockBiomeHeatGenerator biomeHeatGenerator;
    public static BlockMonsterEgg monsterEgg;

    public static GIWorldGenerator worldGen;

    public static void init() {

        oreTitanium = new GIBlock(Material.rock).setHardness(20F).setBlockName(Blocks.ORE_TITANIUM_UNLOCALIZED_NAME);
        oreIsolatzium = new BlockOreIsolatzium(Material.rock);
        compressor = new BlockCompressor(Material.rock);
        crystal = new BlockCrystal(Material.coral);
        crystalStand = new BlockCrystalStand(Material.wood);
        heatIO = new BlockHeatIO(Material.rock);
        connectedGlass = new BlockConnectedGlass(Material.glass);
        titaniumBrick = new GIBlock(Material.rock).setBlockName(Blocks.TITANIUM_BRICK_UNLOCALIZED_NAME);
        stationController = new BlockStationController(Material.rock);
        itemIO = new BlockItemIO(Material.rock);
        biomeHeatGenerator = new BlockBiomeHeatGenerator(Material.rock);
        monsterEgg = new BlockMonsterEgg(Material.dragonEgg);

        registerBlock(oreTitanium, Blocks.ORE_TITANIUM_LOCALIZED_NAME);
        registerBlock(oreIsolatzium, BlockOreIsolatziumItem.class, Blocks.ORE_ISOLATZIUM_LOCALIZED_NAME);
        registerBlock(compressor, Blocks.COMPRESSOR_LOCALIZED_NAME);
        registerBlock(crystal, Blocks.CRYSTAL_LOCALIZED_NAME);
        registerBlock(crystalStand, Blocks.CRYSTAL_STAND_LOCALIZED_NAME);
        registerBlock(heatIO, Blocks.HEAT_IO_LOCALIZED_NAME);
        registerBlock(connectedGlass, Blocks.CONNECTED_GLASS_LOCALIZED_NAME);
        registerBlock(titaniumBrick, Blocks.TITANIUM_BRICK_LOCALIZED_NAME);
        registerBlock(stationController, Blocks.STATION_CONTROLLER_LOCALIZED_NAME);
        registerBlock(itemIO, Blocks.ITEM_IO_LOCALIZED_NAME);
        registerBlock(biomeHeatGenerator, Blocks.BIOME_HEAT_GENERATOR_LOCALIZED_NAME);
        registerBlock(monsterEgg, Blocks.MONSTER_EGG_LOCALIZED_NAME);

//        LanguageRegistry.addName(oreTitanium, Blocks.ORE_TITANIUM_LOCALIZED_NAME);
//        LanguageRegistry.addName(new ItemStack(oreIsolatzium, 1, 0), Blocks.ORE_ISOLATZIUM_META_0_LOCALIZED_NAME);
//        LanguageRegistry.addName(new ItemStack(oreIsolatzium, 1, 1), Blocks.ORE_ISOLATZIUM_META_1_LOCALIZED_NAME);
//        LanguageRegistry.addName(new ItemStack(oreIsolatzium, 1, 2), Blocks.ORE_ISOLATZIUM_META_2_LOCALIZED_NAME);
//        LanguageRegistry.addName(new ItemStack(oreIsolatzium, 1, 3), Blocks.ORE_ISOLATZIUM_META_3_LOCALIZED_NAME);
//        LanguageRegistry.addName(compressor, Blocks.COMPRESSOR_LOCALIZED_NAME);
//        LanguageRegistry.addName(crystal, Blocks.CRYSTAL_LOCALIZED_NAME);
//        LanguageRegistry.addName(crystalStand, Blocks.CRYSTAL_STAND_LOCALIZED_NAME);
//        LanguageRegistry.addName(heatIO, Blocks.HEAT_IO_LOCALIZED_NAME);
//        LanguageRegistry.addName(connectedGlass, Blocks.CONNECTED_GLASS_LOCALIZED_NAME);
//        LanguageRegistry.addName(titaniumBrick, Blocks.TITANIUM_BRICK_LOCALIZED_NAME);
//        LanguageRegistry.addName(stationController, Blocks.STATION_CONTROLLER_LOCALIZED_NAME);
//        LanguageRegistry.addName(itemIO, Blocks.ITEM_IO_LOCALIZED_NAME);
//        LanguageRegistry.addName(biomeHeatGenerator, Blocks.BIOME_HEAT_GENERATOR_LOCALIZED_NAME);
//        LanguageRegistry.addName(monsterEgg, Blocks.MONSTER_EGG_LOCALIZED_NAME);

        worldGen = new GIWorldGenerator();
        GameRegistry.registerWorldGenerator(worldGen, 0);
        oreDictionary();
        tileEntity();
    }

    public static void oreDictionary() {
        OreDictionary.registerOre("oreTitanium", oreTitanium);
        OreDictionary.registerOre("oreIsolatziumRed", new ItemStack(oreIsolatzium, 1, 0));
        OreDictionary.registerOre("oreIsolatziumGreen", new ItemStack(oreIsolatzium, 1, 1));
        OreDictionary.registerOre("oreIsolatziumBlue", new ItemStack(oreIsolatzium, 1, 2));
        OreDictionary.registerOre("oreIsolatziumBlack", new ItemStack(oreIsolatzium, 1, 3));
    }

    public static void tileEntity() {
        GameRegistry.registerTileEntity(TileCompressor.class, Tiles.COMPRESSOR_UNLOCALIZED_NAME);
        GameRegistry.registerTileEntity(TileCrystal.class, Tiles.CRYSTAL_UNLOCALIZED_NAME);
        GameRegistry.registerTileEntity(TileCrystalStand.class, Tiles.CRYSTAL_STAND_UNLOCALIZED_NAME);
        GameRegistry.registerTileEntity(TileHeatIO.class, Tiles.HEAT_IO_UNLOCALIZED_NAME);
        GameRegistry.registerTileEntity(TileStationController.class, Tiles.STATION_CONTROLLER_UNLOCALIZED_NAME);
        GameRegistry.registerTileEntity(TileItemIO.class, Tiles.ITEM_IO_UNLOCALIZED_NAME);
        GameRegistry.registerTileEntity(TileBiomeHeatGenerator.class, Tiles.BIOME_HEAT_GENERATOR_UNLOCALIZED_NAME);
    }
    
    public static void registerBlock(Block block, String name) {
    	GameRegistry.registerBlock(block, DefaultProps.nameLower + "_block_" + name);
    }
    
    public static void registerBlock(Block block, Class<? extends ItemBlock> itemBlock, String name) {
    	GameRegistry.registerBlock(block, itemBlock, DefaultProps.nameLower + "_block_" + name);
    }
}
