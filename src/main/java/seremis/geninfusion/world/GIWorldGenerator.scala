package seremis.geninfusion.world

import java.util.Random

import cpw.mods.fml.common.IWorldGenerator
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.feature.WorldGenMinable
import seremis.geninfusion.block.ModBlocks

class GIWorldGenerator extends IWorldGenerator {

    override def generate(random: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkProvider, chunkProvider: IChunkProvider) {
        world.provider.dimensionId match {
            case -1 => generateNether(world, random, chunkX * 16, chunkZ * 16)
            case 0 => generateSurface(world, random, chunkX * 8, chunkZ * 8)
            case 1 => generateEnd(world, random, chunkX * 16, chunkZ * 16)
        }
    }

    private def generateSurface(world: World, random: Random, blockX: Int, blockZ: Int) {
        for (i <- 0 until 7) {
            val xCoord = blockX + random.nextInt(1)
            val yCoord = random.nextInt(60)
            val zCoord = blockZ + random.nextInt(1)

            new WorldGenMinable(ModBlocks.oreTitanium, 10).generate(world, random, xCoord, yCoord, zCoord)
        }
    }

    private def generateNether(world: World, random: Random, blockX: Int, blockZ: Int) {}

    private def generateEnd(world: World, random: Random, blockX: Int, blockZ: Int) {}
}