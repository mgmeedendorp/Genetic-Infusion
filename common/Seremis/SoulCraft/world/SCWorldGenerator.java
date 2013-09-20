package Seremis.SoulCraft.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.lib.DefaultProps;
import cpw.mods.fml.common.IWorldGenerator;

public class SCWorldGenerator implements IWorldGenerator {

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

        if(DefaultProps.spawnTitanium) {
            for(int i = 0; i < 7; i++) {
                int Xcoord = blockX + random.nextInt(1);
                int Ycoord = random.nextInt(60);
                int Zcoord = blockZ + random.nextInt(1);
                new WorldGenMinable(ModBlocks.oreTitanium.blockID, 10).generate(world, random, Xcoord, Ycoord, Zcoord);
            }
        }
        if(DefaultProps.spawnOreSoulCrystal) {
            for(int i = 0; i < 7; i++) {
                int Xcoord = blockX + random.nextInt(2);
                int Ycoord = random.nextInt(256);
                int Zcoord = blockZ + random.nextInt(2);
                new WorldGenMinable(ModBlocks.oreIsolatzium.blockID, 0, 8, Block.stone.blockID).generate(world, random, Xcoord, Ycoord, Zcoord);
            }
            for(int i = 0; i < 7; i++) {
                int Xcoord = blockX + random.nextInt(2);
                int Ycoord = random.nextInt(256);
                int Zcoord = blockZ + random.nextInt(2);
                new WorldGenMinable(ModBlocks.oreIsolatzium.blockID, 1, 8, Block.stone.blockID).generate(world, random, Xcoord, Ycoord, Zcoord);
            }
            for(int i = 0; i < 7; i++) {
                int Xcoord = blockX + random.nextInt(2);
                int Ycoord = random.nextInt(256);
                int Zcoord = blockZ + random.nextInt(2);
                new WorldGenMinable(ModBlocks.oreIsolatzium.blockID, 2, 8, Block.stone.blockID).generate(world, random, Xcoord, Ycoord, Zcoord);
            }
            for(int i = 0; i < 7; i++) {
                int Xcoord = blockX + random.nextInt(2);
                int Ycoord = random.nextInt(256);
                int Zcoord = blockZ + random.nextInt(2);
                new WorldGenMinable(ModBlocks.oreIsolatzium.blockID, 3, 8, Block.stone.blockID).generate(world, random, Xcoord, Ycoord, Zcoord);
            }
        }
    }

    private void generateNether(World world, Random random, int blockX, int blockZ) {

    }

    private void generateEnd(World world, Random random, int blockX, int blockZ) {

    }
}
