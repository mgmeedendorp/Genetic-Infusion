package seremis.geninfusion.world

import java.util.Random

import cpw.mods.fml.common.IWorldGenerator
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import seremis.geninfusion.world.generate.WorldGenCrystal

class GIWorldGenerator extends IWorldGenerator {

    val crystalGen = new WorldGenCrystal()

    override def generate(random: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkProvider, chunkProvider: IChunkProvider) {
        world.provider.dimensionId match {
            case -1 => generateNether(world, random, chunkX * 16, chunkZ * 16)
            case 1 => generateEnd(world, random, chunkX * 16, chunkZ * 16)
            case default => generateSurface(world, random, chunkX * 8, chunkZ * 8)
        }
    }

    private def generateSurface(world: World, random: Random, blockX: Int, blockZ: Int) {
        for(i <- 0 until 1) {
            val x = blockX + random.nextInt(16)
            val z = blockZ + random.nextInt(16)

            crystalGen.generate(world, random, x, 10, z)
        }
    }

    private def generateNether(world: World, random: Random, blockX: Int, blockZ: Int) {}

    private def generateEnd(world: World, random: Random, blockX: Int, blockZ: Int) {}
}