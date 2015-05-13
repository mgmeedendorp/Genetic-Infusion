package seremis.geninfusion.item

import cpw.mods.fml.common.registry.GameRegistry._
import net.minecraftforge.oredict.OreDictionary._
import seremis.geninfusion.lib.Items

object ModItems {

    val titaniumIngot = new GIItem().setUnlocalizedName(Items.TITANIUM_INGOT_UNLOCALIZED_NAME)
    val debugger = new ItemDebugger

    def init() {
        registerItem(titaniumIngot, Items.TITANIUM_INGOT_UNLOCALIZED_NAME)
        registerItem(debugger, Items.DEBUGGER_UNLOCALIZED_NAME)

        registerOre(Items.TITANIUM_INGOT_OREDICTIONARY_NAME, titaniumIngot)
    }
}
