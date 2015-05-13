package seremis.geninfusion.helper

import cpw.mods.fml.common.registry.GameRegistry._
import net.minecraft.item.ItemStack
import seremis.geninfusion.block.ModBlocks
import seremis.geninfusion.item.ModItems

object RecipeHelper {

    def initRecipes() {}

    def initSmelting() {
        addSmelting(ModBlocks.oreTitanium, new ItemStack(ModItems.titaniumIngot), 0.4f)
    }
}