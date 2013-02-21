package voidrunner101.SoulCraft.common.world;

import java.util.Random;

import voidrunner101.SoulCraft.mod_SoulCraft;
import voidrunner101.SoulCraft.common.blocks.ModBlocks;
import voidrunner101.SoulCraft.common.core.DefaultProps;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class SCWorldGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId) {
			case -1: generateNether(world, random, chunkX * 16, chunkZ * 16);
			case 0: generateSurface(world, random, chunkX * 8, chunkZ * 8);
		}
	}
	
	private void generateSurface(World world, Random random, int blockX, int blockZ) {	

		if(DefaultProps.spawnTitanium) {
			for(int i = 0; i < 7; i++) {
				int Xcoord = blockX + random.nextInt(1);
				int Ycoord = random.nextInt(60);
				int Zcoord = blockZ + random.nextInt(1);
				(new WorldGenMinable(ModBlocks.OreTitanium.blockID, 10)).generate(world, random, Xcoord, Ycoord, Zcoord);
			}
		}
		if(DefaultProps.spawnOreSoulCrystal) {
			for(int i = 0; i < 7; i++) {
				int Xcoord = blockX + random.nextInt(2);
				int Ycoord = random.nextInt(256);
				int Zcoord = blockZ + random.nextInt(2);
				(new WorldGenMinable(ModBlocks.OreIsolatzium.blockID, 100)).generate(world, random, Xcoord, Ycoord, Zcoord);
				}
		}
	}
	
	private void generateNether(World world, Random random, int blockX, int blockZ) {
		
	}

}
