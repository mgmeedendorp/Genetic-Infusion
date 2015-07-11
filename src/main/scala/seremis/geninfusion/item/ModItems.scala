package seremis.geninfusion.item

import cpw.mods.fml.common.registry.GameRegistry._
import seremis.geninfusion.lib.Items

object ModItems {

    val debugger = new ItemDebugger

    def init() {
        registerItem(debugger, Items.DebuggerName)
    }
}
