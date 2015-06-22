package seremis.geninfusion.block

import cpw.mods.fml.common.registry.GameRegistry._
import net.minecraft.block.material.Material
import net.minecraftforge.oredict.OreDictionary._
import seremis.geninfusion.lib.Blocks
import seremis.geninfusion.tileentity.TileCrystal
import seremis.geninfusion.world.GIWorldGenerator

object ModBlocks {

    var oreTitanium = new GIBlock(Material.rock).setHardness(20F).setUnlocalizedName(Blocks.OreTitaniumName)
    var crystal = new BlockCrystal(Material.coral)
    var connectedGlass = new BlockConnectedGlass(Material.glass)

    var worldGen = new GIWorldGenerator()

    def init() {
        registerBlock(oreTitanium, Blocks.OreTitaniumName)
        registerBlock(crystal, Blocks.CrystalName)
        registerBlock(connectedGlass, Blocks.ConnectedGlassName)

        registerTileEntity(classOf[TileCrystal], Blocks.CrystalName)

        registerOre(Blocks.OreTitaniumName, oreTitanium)

        registerWorldGenerator(worldGen, 0)
    }
}