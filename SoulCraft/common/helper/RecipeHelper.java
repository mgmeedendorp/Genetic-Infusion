package voidrunner101.SoulCraft.common.helper;

import java.util.logging.Level;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import voidrunner101.SoulCraft.common.blocks.ModBlocks;
import voidrunner101.SoulCraft.common.items.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeHelper {
	
	public static void initRecipes() {
			GameRegistry.addRecipe(new ItemStack(ModBlocks.MonsterEgg), "xyx", "xzx", "xxx", Character.valueOf('z'), Item.egg, Character.valueOf('x'), ModItems.IngotTitanium, Character.valueOf('z'), ModItems.ShardIsolatzium);
	}
	
	public static void initSmelting() {
			GameRegistry.addSmelting(ModBlocks.OreTitanium.blockID, new ItemStack(ModItems.IngotTitanium), 0.4f);
	}
}
