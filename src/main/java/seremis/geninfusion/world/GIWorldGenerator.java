package seremis.geninfusion.world;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import seremis.geninfusion.block.ModBlocks;

import java.util.Random;

public class GIWorldGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch(world.provider.dimensionId) {
            case -1:
                generateNether(world, random, chunkX * 16, chunkZ * 16);
            case 0:
                generateSurface(world, random, chunkX * 8, chunkZ * 8);
            case 1:
                generateEnd(world, random, chunkX * 16, chunkZ * 16);
        }
    }

    private void generateSurface(World world, Random random, int blockX, int blockZ) {
        for(int i = 0; i < 7; i++) {
            int xCoord = blockX + random.nextInt(1);
            int yCoord = random.nextInt(60);
            int zCoord = blockZ + random.nextInt(1);
            new WorldGenMinable(ModBlocks.oreTitanium, 10).generate(world, random, xCoord, yCoord, zCoord);
        }
    }

    private void generateNether(World world, Random random, int blockX, int blockZ) {

    }

    private void generateEnd(World world, Random random, int blockX, int blockZ) {

    }
}
