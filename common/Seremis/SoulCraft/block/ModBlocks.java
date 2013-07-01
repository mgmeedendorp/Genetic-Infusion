package Seremis.SoulCraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Seremis.SoulCraft.core.lib.DefaultProps;
import Seremis.SoulCraft.tileentity.TileCompressor;
import Seremis.SoulCraft.tileentity.TileCrystalStand;
import Seremis.SoulCraft.tileentity.TileIsolatziumCrystal;
import Seremis.SoulCraft.tileentity.TileTransporter;
import Seremis.SoulCraft.world.SCWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModBlocks {
	
	public static Block oreTitanium;
	public static BlockOreIsolatzium oreIsolatzium;
	public static BlockCompressor compressor;
	public static BlockCrystal crystal;
	public static BlockCrystalStand crystalStand;
	public static BlockTransporter transporter;
	public static BlockMonsterEgg monsterEgg;
	
	public static SCWorldGenerator worldGen;
	
	public static void init() {		
		oreTitanium =  new SCBlock(DefaultProps.OreTitaniumID, Material.rock).setHardness(20F).setUnlocalizedName("oreTitanium");
		oreIsolatzium = new BlockOreIsolatzium(DefaultProps.OreIsolatziumID, Material.rock);
		compressor =  new BlockCompressor(DefaultProps.CompressorID, Material.rock);
		crystal = new BlockCrystal(DefaultProps.IsolatziumCrystalID, Material.coral);
		crystalStand = new BlockCrystalStand(DefaultProps.CrystalStandID, Material.wood);
		transporter = new BlockTransporter(DefaultProps.TransporterID, Material.iron);
		monsterEgg = new BlockMonsterEgg(DefaultProps.MonsterEggID, Material.dragonEgg);
		
		GameRegistry.registerBlock(oreTitanium, "Titanium Ore");
		GameRegistry.registerBlock(oreIsolatzium, BlockOreIsolatziumItem.class, "Isolatzium Crystal Ore");
		GameRegistry.registerBlock(compressor, "Compressor");
		GameRegistry.registerBlock(crystal, "Isolazium Crystal");
		GameRegistry.registerBlock(crystalStand, "Crystal Stand");
		GameRegistry.registerBlock(transporter, "Plasma Transporter");
		GameRegistry.registerBlock(monsterEgg, "Monster Egg");
		
		LanguageRegistry.addName(oreTitanium, "Titanium Ore");
		LanguageRegistry.addName(new ItemStack(oreIsolatzium, 1, 0), "Red Isolatzium Crystal Ore");
		LanguageRegistry.addName(new ItemStack(oreIsolatzium, 1, 1), "Green Isolatzium Crystal Ore");
		LanguageRegistry.addName(new ItemStack(oreIsolatzium, 1, 2), "Blue Isolatzium Crystal Ore");
		LanguageRegistry.addName(new ItemStack(oreIsolatzium, 1, 3), "Black Isolatzium Crystal Ore");
		LanguageRegistry.addName(compressor, "Atomic Compressor");
		LanguageRegistry.addName(crystal, "Isolatzium Crystal");
		LanguageRegistry.addName(crystalStand, "Isolatzium Crystal Stand");
		LanguageRegistry.addName(transporter, "Plasmatic Transporter");
		LanguageRegistry.addName(monsterEgg, "Monster Egg");

		worldGen = new SCWorldGenerator();
		GameRegistry.registerWorldGenerator(worldGen);
		oreDictionary();
		tileEntity();
	}
	
	public static void oreDictionary() {
		OreDictionary.registerOre("oreTitanium", ModBlocks.oreTitanium);
		OreDictionary.registerOre("oreIsolatzium", ModBlocks.oreIsolatzium);
	}
	
	public static void tileEntity() {
		GameRegistry.registerTileEntity(TileCompressor.class, "TileCompressor");
		GameRegistry.registerTileEntity(TileIsolatziumCrystal.class, "TileIsolatziumCrystal");
		GameRegistry.registerTileEntity(TileCrystalStand.class, "TileCrystalStand");
		GameRegistry.registerTileEntity(TileTransporter.class, "TileTransporter");
	}
}
