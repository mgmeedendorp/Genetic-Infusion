package seremis.geninfusion.world.generate

import net.minecraft.init.Blocks
import net.minecraft.world.World
import seremis.geninfusion.block.ModBlocks

import scala.util.Random

class WorldGenCrystal extends IWorldGen {

    override def generate(world: World, random: Random, x: Int, y: Int, z: Int): Boolean = {

        var yCoord = y

        while(!world.getBlock(x, yCoord, z).isAir(world, x, yCoord, z)){
            if(yCoord > 50) return false
            yCoord += 1
        }

        if(world.getBlock(x, yCoord - 1, z) == Blocks.stone) {
            if(!world.canBlockSeeTheSky(x, yCoord, z)) {
                world.setBlock(x, yCoord, z, ModBlocks.crystal)
                return true
            }
        }
        false
    }
}
