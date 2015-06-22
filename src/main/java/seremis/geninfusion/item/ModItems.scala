package seremis.geninfusion.item

import cpw.mods.fml.common.registry.GameRegistry._
import net.minecraftforge.oredict.OreDictionary._
import seremis.geninfusion.lib.Items

object ModItems {

    val titaniumIngot = new GIItem().setUnlocalizedName(Items.TitaniumIngotName)
    val debugger = new ItemDebugger

    def init() {
        registerItem(titaniumIngot, Items.TitaniumIngotName)
        registerItem(debugger, Items.DebuggerName)

        registerOre(Items.TitaniumIngotName, titaniumIngot)
    }
}
