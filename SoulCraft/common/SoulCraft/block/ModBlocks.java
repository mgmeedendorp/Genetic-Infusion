package SoulCraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import SoulCraft.core.DefaultProps;
import SoulCraft.items.OreSoulCrystalItem;
import SoulCraft.tileentity.TileCompressor;
import SoulCraft.world.SCWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModBlocks {
	
	public static Block OreTitanium;
	public static OreIsolatzium OreIsolatzium;
	public static Compressor Compressor;
	public static MonsterEgg MonsterEgg;
	
	public static SCWorldGenerator WorldGen;
	
	private static Random rnd = new Random();
	
	public static void init() {		
		OreTitanium =  new SCBlock(DefaultProps.OreTitaniumID, 0, Material.rock).setHardness(20F);
		OreIsolatzium = new OreIsolatzium(DefaultProps.OreIsolatziumID, 1, Material.rock);
		Compressor =  new Compressor(DefaultProps.CompressorID, Material.rock);
		MonsterEgg = new MonsterEgg(DefaultProps.MonsterEggID, 1, Material.dragonEgg);
		
		
		
		GameRegistry.registerBlock(OreTitanium, "Titanium Ore");
		GameRegistry.registerBlock(OreIsolatzium, OreSoulCrystalItem.class, "Isolatzium Crystal Ore");
		GameRegistry.registerBlock(Compressor, "Compressor");
		GameRegistry.registerBlock(MonsterEgg, "Monster Egg");
		
		LanguageRegistry.addName(OreTitanium, "Titanium Ore");
		LanguageRegistry.addName(new ItemStack(OreIsolatzium, 1, 0), "Red Isolatzium Crystal Ore");
		LanguageRegistry.addName(new ItemStack(OreIsolatzium, 1, 1), "Green Isolatzium Crystal Ore");
		LanguageRegistry.addName(new ItemStack(OreIsolatzium, 1, 2), "Blue Isolatzium Crystal Ore");
		LanguageRegistry.addName(new ItemStack(OreIsolatzium, 1, 3), "Black Isolatzium Crystal Ore");
		LanguageRegistry.addName(Compressor, "Atomic Compressor");
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
	}

}
