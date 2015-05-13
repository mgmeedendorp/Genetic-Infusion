package seremis.geninfusion.block

import cpw.mods.fml.common.registry.GameRegistry._
import net.minecraft.block.material.Material
import net.minecraftforge.oredict.OreDictionary._
import seremis.geninfusion.lib.Blocks
import seremis.geninfusion.tileentity.TileCrystal
import seremis.geninfusion.world.GIWorldGenerator

object ModBlocks {

    var oreTitanium = new GIBlock(Material.rock).setHardness(20F).setUnlocalizedName(Blocks.ORE_TITANIUM_UNLOCALIZED_NAME)
    var crystal = new BlockCrystal(Material.coral)
    var connectedGlass = new BlockConnectedGlass(Material.glass)

    var worldGen = new GIWorldGenerator()

    def init() {
        registerBlock(oreTitanium, Blocks.ORE_TITANIUM_UNLOCALIZED_NAME)
        registerBlock(crystal, Blocks.CRYSTAL_UNLOCALIZED_NAME)
        registerBlock(connectedGlass, Blocks.CONNECTED_GLASS_UNLOCALIZED_NAME)

        registerTileEntity(classOf[TileCrystal], Blocks.CRYSTAL_UNLOCALIZED_NAME)

        registerOre("oreTitanium", oreTitanium)

        registerWorldGenerator(worldGen, 0)
    }
}