package Seremis.SoulCraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Seremis.SoulCraft.core.lib.Blocks;
import Seremis.SoulCraft.core.lib.Tiles;
import Seremis.SoulCraft.tileentity.TileBush;
import Seremis.SoulCraft.tileentity.TileCompressor;
import Seremis.SoulCraft.tileentity.TileCrystal;
import Seremis.SoulCraft.tileentity.TileCrystalStand;
import Seremis.SoulCraft.tileentity.TileHeatIO;
import Seremis.SoulCraft.tileentity.TileStationController;
import Seremis.SoulCraft.world.SCWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModBlocks {

    public static Block oreTitanium;
    public static BlockOreIsolatzium oreIsolatzium;
    public static BlockCompressor compressor;
    public static BlockCrystal crystal;
    public static BlockCrystalStand crystalStand;
    public static BlockBush bushBerry;
    public static BlockHeatIO heatIO;
    public static BlockConnectedGlass connectedGlass;
    public static Block titaniumBrick;
    public static BlockStationController stationController;
    public static BlockMonsterEgg monsterEgg;

    public static SCWorldGenerator worldGen;

    public static void init() {

        oreTitanium = new SCBlock(Blocks.oreTitaniumID, Material.rock).setHardness(20F).setUnlocalizedName("oreTitanium");
        oreIsolatzium = new BlockOreIsolatzium(Blocks.oreIsolatziumID, Material.rock);
        compressor = new BlockCompressor(Blocks.compressorID, Material.rock);
        crystal = new BlockCrystal(Blocks.crystalID, Material.coral);
        crystalStand = new BlockCrystalStand(Blocks.crystalStandID, Material.wood);
        bushBerry = new BlockBush(Blocks.bushID, Material.plants);
        heatIO = new BlockHeatIO(Blocks.heatIOID, Material.rock);
        connectedGlass = new BlockConnectedGlass(Blocks.connectedGlassID, Material.glass);
        titaniumBrick = new SCBlock(Blocks.titaniumBrickID, Material.rock).setUnlocalizedName("titaniumBrick");
        stationController = new BlockStationController(Blocks.stationControllerID, Material.rock);
        monsterEgg = new BlockMonsterEgg(Blocks.monsterEggID, Material.dragonEgg);

        GameRegistry.registerBlock(oreTitanium, Blocks.ORE_TITANIUM_LOCALIZED_NAME);
        GameRegistry.registerBlock(oreIsolatzium, BlockOreIsolatziumItem.class, Blocks.ORE_ISOLATZIUM_LOCALIZED_NAME);
        GameRegistry.registerBlock(compressor, Blocks.COMPRESSOR_LOCALIZED_NAME);
        GameRegistry.registerBlock(crystal, Blocks.CRYSTAL_LOCALIZED_NAME);
        GameRegistry.registerBlock(crystalStand, Blocks.CRYSTAL_STAND_LOCALIZED_NAME);
        GameRegistry.registerBlock(bushBerry, BlockBushItem.class, Blocks.BUSH_LOCALIZED_NAME);
        GameRegistry.registerBlock(heatIO, Blocks.HEAT_IO_LOCALIZED_NAME);
        GameRegistry.registerBlock(connectedGlass, Blocks.CONNECTED_GLASS_LOCALIZED_NAME);
        GameRegistry.registerBlock(titaniumBrick, Blocks.TITANIUM_BRICK_LOCALIZED_NAME);
        GameRegistry.registerBlock(stationController, Blocks.STATION_CONTROLLER_LOCALIZED_NAME);
        GameRegistry.registerBlock(monsterEgg, Blocks.MONSTER_EGG_LOCALIZED_NAME);

        LanguageRegistry.addName(oreTitanium, Blocks.ORE_TITANIUM_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(oreIsolatzium, 1, 0), Blocks.ORE_ISOLATZIUM_META_0_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(oreIsolatzium, 1, 1), Blocks.ORE_ISOLATZIUM_META_1_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(oreIsolatzium, 1, 2), Blocks.ORE_ISOLATZIUM_META_2_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(oreIsolatzium, 1, 3), Blocks.ORE_ISOLATZIUM_META_3_LOCALIZED_NAME);
        LanguageRegistry.addName(compressor, Blocks.COMPRESSOR_LOCALIZED_NAME);
        LanguageRegistry.addName(crystal, Blocks.CRYSTAL_LOCALIZED_NAME);
        LanguageRegistry.addName(crystalStand, Blocks.CRYSTAL_STAND_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(bushBerry, 1, 0), Blocks.BUSH_META_0_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(bushBerry, 1, 1), Blocks.BUSH_META_1_LOCALIZED_NAME);
        LanguageRegistry.addName(heatIO, Blocks.HEAT_IO_LOCALIZED_NAME);
        LanguageRegistry.addName(connectedGlass, Blocks.CONNECTED_GLASS_LOCALIZED_NAME);
        LanguageRegistry.addName(titaniumBrick, Blocks.TITANIUM_BRICK_LOCALIZED_NAME);
        LanguageRegistry.addName(stationController, Blocks.STATION_CONTROLLER_LOCALIZED_NAME);
        LanguageRegistry.addName(monsterEgg, Blocks.MONSTER_EGG_LOCALIZED_NAME);

        worldGen = new SCWorldGenerator();
        GameRegistry.registerWorldGenerator(worldGen);
        oreDictionary();
        tileEntity();
    }

    public static void oreDictionary() {
        OreDictionary.registerOre("oreTitanium", oreTitanium);
        OreDictionary.registerOre("oreIsolatziumRed", new ItemStack(oreIsolatzium, 1, 0));
        OreDictionary.registerOre("oreIsolatziumGreen", new ItemStack(oreIsolatzium, 1, 1));
        OreDictionary.registerOre("oreIsolatziumBlue", new ItemStack(oreIsolatzium, 1, 2));
        OreDictionary.registerOre("oreIsolatziumBlack", new ItemStack(oreIsolatzium, 1, 3));
        OreDictionary.registerOre("berryBush", bushBerry);
    }

    public static void tileEntity() {
        GameRegistry.registerTileEntity(TileCompressor.class, Tiles.COMPRESSOR_UNLOCALIZED_NAME);
        GameRegistry.registerTileEntity(TileCrystal.class, Tiles.CRYSTAL_UNLOCALIZED_NAME);
        GameRegistry.registerTileEntity(TileCrystalStand.class, Tiles.CRYSTAL_STAND_UNLOCALIZED_NAME);
        GameRegistry.registerTileEntity(TileBush.class, Tiles.BUSH_UNLOCALIZED_NAME);
        GameRegistry.registerTileEntity(TileHeatIO.class, Tiles.HEAT_IO_UNLOCALIZED_NAME);
        GameRegistry.registerTileEntity(TileStationController.class, Tiles.STATION_CONTROLLER_UNLOCALIZED_NAME);
    }
}
