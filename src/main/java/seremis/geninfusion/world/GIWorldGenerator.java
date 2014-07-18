package seremis.geninfusion.world;

import java.util.Random;

import seremis.geninfusion.block.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

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
            int Xcoord = blockX + random.nextInt(1);
            int Ycoord = random.nextInt(60);
            int Zcoord = blockZ + random.nextInt(1);
            new WorldGenMinable(ModBlocks.oreTitanium, 10).generate(world, random, Xcoord, Ycoord, Zcoord);
        }
        for(int i = 0; i < 7; i++) {
            int Xcoord = blockX + random.nextInt(2);
            int Ycoord = random.nextInt(256);
            int Zcoord = blockZ + random.nextInt(2);
            new WorldGenMinable(ModBlocks.oreIsolatzium, 0, 10, Blocks.stone).generate(world, random, Xcoord, Ycoord, Zcoord);
        }
        for(int i = 0; i < 7; i++) {
            int Xcoord = blockX + random.nextInt(2);
            int Ycoord = random.nextInt(256);
            int Zcoord = blockZ + random.nextInt(2);
            new WorldGenMinable(ModBlocks.oreIsolatzium, 1, 10, Blocks.stone).generate(world, random, Xcoord, Ycoord, Zcoord);
        }
        for(int i = 0; i < 7; i++) {
            int Xcoord = blockX + random.nextInt(2);
            int Ycoord = random.nextInt(256);
            int Zcoord = blockZ + random.nextInt(2);
            new WorldGenMinable(ModBlocks.oreIsolatzium, 2, 10, Blocks.stone).generate(world, random, Xcoord, Ycoord, Zcoord);
        }
        for(int i = 0; i < 7; i++) {
            int Xcoord = blockX + random.nextInt(2);
            int Ycoord = random.nextInt(256);
            int Zcoord = blockZ + random.nextInt(2);
            new WorldGenMinable(ModBlocks.oreIsolatzium, 3, 10, Blocks.stone).generate(world, random, Xcoord, Ycoord, Zcoord);
        }
    }

    private void generateNether(World world, Random random, int blockX, int blockZ) {

    }

    private void generateEnd(World world, Random random, int blockX, int blockZ) {

    }
}
