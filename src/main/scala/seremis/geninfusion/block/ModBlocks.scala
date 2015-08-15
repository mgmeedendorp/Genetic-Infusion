package seremis.geninfusion.block

import cpw.mods.fml.common.registry.GameRegistry._
import net.minecraft.block.material.Material
import seremis.geninfusion.item.ItemBlockCrystal
import seremis.geninfusion.lib.Blocks
import seremis.geninfusion.tileentity.TileCrystal
import seremis.geninfusion.world.GIWorldGenerator

object ModBlocks {

    var crystal = new BlockCrystal(Material.coral)

    var worldGen = new GIWorldGenerator()

    def init() {
        registerBlock(crystal, classOf[ItemBlockCrystal], Blocks.CrystalName)

        registerTileEntity(classOf[TileCrystal], Blocks.CrystalName)

        registerWorldGenerator(worldGen, 0)
    }
}