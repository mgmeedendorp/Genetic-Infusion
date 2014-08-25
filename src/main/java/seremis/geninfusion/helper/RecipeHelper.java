package seremis.geninfusion.helper;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.block.ModBlocks;
import seremis.geninfusion.item.ModItems;

public class RecipeHelper {

    public static void initRecipes() {
        GameRegistry.addRecipe(new ItemStack(ModBlocks.monsterEgg), "xyx", "xzx", "xxx", 'z', Items.egg, 'x', ModItems.titaniumIngot, 'z', ModBlocks.crystal);
    }

    public static void initSmelting() {
        GameRegistry.addSmelting(ModBlocks.oreTitanium, new ItemStack(ModItems.titaniumIngot), 0.4f);
    }
}
