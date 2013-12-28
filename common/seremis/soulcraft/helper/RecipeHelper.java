package seremis.soulcraft.helper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import seremis.soulcraft.block.ModBlocks;
import seremis.soulcraft.item.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeHelper {

    public static void initRecipes() {
        GameRegistry.addRecipe(new ItemStack(ModBlocks.monsterEgg), "xyx", "xzx", "xxx", Character.valueOf('z'), Item.egg, Character.valueOf('x'), ModItems.titaniumIngot, Character.valueOf('z'), ModBlocks.crystal);
    }

    public static void initSmelting() {
        GameRegistry.addSmelting(ModBlocks.oreTitanium.blockID, new ItemStack(ModItems.titaniumIngot), 0.4f);
    }
}
