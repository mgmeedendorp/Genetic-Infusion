package SoulCraft.helper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import SoulCraft.block.ModBlocks;
import SoulCraft.items.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeHelper {
	
	public static void initRecipes() {
			GameRegistry.addRecipe(new ItemStack(ModBlocks.MonsterEgg), "xyx", "xzx", "xxx", Character.valueOf('z'), Item.egg, Character.valueOf('x'), ModItems.IngotTitanium, Character.valueOf('z'), ModBlocks.Crystal);
	}
	
	public static void initSmelting() {
			GameRegistry.addSmelting(ModBlocks.OreTitanium.blockID, new ItemStack(ModItems.IngotTitanium), 0.4f);
	}
}
