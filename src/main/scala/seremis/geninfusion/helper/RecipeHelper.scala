package seremis.geninfusion.helper

import cpw.mods.fml.common.registry.GameRegistry._
import net.minecraft.block.Block
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import net.minecraftforge.oredict.RecipeSorter
import net.minecraftforge.oredict.RecipeSorter.Category
import seremis.geninfusion.api.soul.SoulHelper
import seremis.geninfusion.block.{BlockCrystal, ModBlocks}
import seremis.geninfusion.lib.DefaultProps

import scala.collection.mutable.ListBuffer

object RecipeHelper {

    def initRecipes() {
        addRecipe(RecipeCrystal)
        RecipeSorter.register(DefaultProps.ID + ".recipe.crystal", classOf[RecipeCrystal], Category.SHAPELESS, "after:minecraft:shapeless")
    }

    def initSmelting() {

    }


    final val RecipeCrystal = new RecipeCrystal {
        override def matches(inventory : InventoryCrafting, world : World): Boolean = {
            var crystals = 0

            for(i <- 0 until inventory.getSizeInventory) {
                val stack = inventory.getStackInSlot(i)

                if(stack != null && Block.getBlockFromItem(stack.getItem).isInstanceOf[BlockCrystal] && stack.hasTagCompound)
                    crystals += 1
                else if(stack != null)
                    return false
            }

            crystals == 2
        }

        override def getRecipeOutput: ItemStack = new ItemStack(ModBlocks.crystal)

        override def getRecipeSize: Int = 4

        override def getCraftingResult(inventory : InventoryCrafting): ItemStack = {
            val crystals: ListBuffer[ItemStack] = ListBuffer()

            for(i <- 0 until inventory.getSizeInventory) {
                val stack = inventory.getStackInSlot(i)

                if(stack != null && Block.getBlockFromItem(stack.getItem).isInstanceOf[BlockCrystal] && stack.hasTagCompound)
                    crystals += stack
            }

            val soul1 = SoulHelper.instanceHelper.getISoulInstance(crystals(0).getTagCompound).orNull
            val soul2 = SoulHelper.instanceHelper.getISoulInstance(crystals(1).getTagCompound).orNull

            val newSoul = SoulHelper.produceOffspring(soul1, soul2).get

            val resultCrystal = new ItemStack(ModBlocks.crystal)
            val compound = new NBTTagCompound

            newSoul.writeToNBT(compound)

            resultCrystal.setTagCompound(compound)
            resultCrystal
        }
    }

    abstract class RecipeCrystal extends IRecipe
}