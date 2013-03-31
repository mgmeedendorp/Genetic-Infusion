package Seremis.SoulCraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Seremis.SoulCraft.core.DefaultProps;
import Seremis.SoulCraft.tileentity.TileCompressor;
import Seremis.SoulCraft.tileentity.TileCrystalStand;
import Seremis.SoulCraft.tileentity.TileIsolatziumCrystal;
import Seremis.SoulCraft.world.SCWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModBlocks {
	
	public static Block OreTitanium;
	public static BlockOreIsolatzium OreIsolatzium;
	public static BlockCompressor Compressor;
	public static BlockCrystal Crystal;
	public static BlockCrystalStand CrystalStand;
	public static BlockMonsterEgg MonsterEgg;
	
	public static SCWorldGenerator WorldGen;
	
	public static void init() {		
		OreTitanium =  new SCBlock(DefaultProps.OreTitaniumID, Material.rock).setHardness(20F).setUnlocalizedName("oreTitanium");
		OreIsolatzium = new BlockOreIsolatzium(DefaultProps.OreIsolatziumID, Material.rock);
		Compressor =  new BlockCompressor(DefaultProps.CompressorID, Material.rock);
		Crystal = new BlockCrystal(DefaultProps.IsolatziumCrystalID, Material.coral);
		CrystalStand = new BlockCrystalStand(DefaultProps.CrystalStandID, Material.wood);
		MonsterEgg = new BlockMonsterEgg(DefaultProps.MonsterEggID, Material.dragonEgg);
		
		
		
		GameRegistry.registerBlock(OreTitanium, "Titanium Ore");
		GameRegistry.registerBlock(OreIsolatzium, BlockOreIsolatziumItem.class, "Isolatzium Crystal Ore");
		GameRegistry.registerBlock(Compressor, "Compressor");
		GameRegistry.registerBlock(Crystal, "Isolazium Crystal");
		GameRegistry.registerBlock(CrystalStand, "Crystal Stand");
		GameRegistry.registerBlock(MonsterEgg, "Monster Egg");
		
		LanguageRegistry.addName(OreTitanium, "Titanium Ore");
		LanguageRegistry.addName(new ItemStack(OreIsolatzium, 1, 0), "Red Isolatzium Crystal Ore");
		LanguageRegistry.addName(new ItemStack(OreIsolatzium, 1, 1), "Green Isolatzium Crystal Ore");
		LanguageRegistry.addName(new ItemStack(OreIsolatzium, 1, 2), "Blue Isolatzium Crystal Ore");
		LanguageRegistry.addName(new ItemStack(OreIsolatzium, 1, 3), "Black Isolatzium Crystal Ore");
		LanguageRegistry.addName(Compressor, "Atomic Compressor");
		LanguageRegistry.addName(Crystal, "Isolatzium Crystal");
		LanguageRegistry.addName(CrystalStand, "Isolatzium Crystal Stand");
		LanguageRegistry.addName(MonsterEgg, "Monster Egg");

		WorldGen = new SCWorldGenerator();
		GameRegistry.registerWorldGenerator(WorldGen);
		oreDictionary();
		tileEntity();
	}
	
	public static void oreDictionary() {
		OreDictionary.registerOre("oreTitanium", ModBlocks.OreTitanium);
		OreDictionary.registerOre("oreIsolatzium", ModBlocks.OreIsolatzium);
	}
	
	public static void tileEntity() {
		GameRegistry.registerTileEntity(TileCompressor.class, "TileCompressor");
		GameRegistry.registerTileEntity(TileIsolatziumCrystal.class, "TileIsolatziumCrystal");
		GameRegistry.registerTileEntity(TileCrystalStand.class, "TileCrystalStand");
	}
}
