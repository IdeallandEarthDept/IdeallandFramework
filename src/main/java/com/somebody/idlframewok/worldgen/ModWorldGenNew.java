package com.somebody.idlframewok.worldgen;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ModWorldGenNew implements IWorldGenerator {

	//Please register this in preInitRegistries

	private WorldGenerator testGen1, testGen2;

	public ModWorldGenNew() {
//		testGen1 = new WorldGenMinable(Blocks.DIAMOND_BLOCK.getDefaultState(),
//				9, BlockMatcher.forBlock(Blocks.NETHERRACK));
//
//		testGen2 = new WorldGenMinable(Blocks.GOLD_BLOCK.getDefaultState(),
//				4);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.getDimension())
		{
			case -1:
				//nether
				//runGenOre(testGen1, world, random, chunkX, chunkZ, 60, 16, 38);
				break;
			case 0:
				//overworld
				//IdlFramework.Log("world gen running");

				//runGenOre(testGen2, world, random, chunkX, chunkZ, 32, 16, 18);

				break;
			case 1:
				//end
				break;
		}
	}

	//Utility

	//Ore
	void runGenOre(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chances, int minHeight, int maxHeight)
	{
		if (minHeight > maxHeight || minHeight < 0 || maxHeight > 256)
		{
			throw  new IllegalArgumentException("Ore gen out of bounds");
		}

		int heightDiff = maxHeight - minHeight + 1;

		for (int i = 0; i < chances; i++) {
			int x = chunkX * 16 + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = chunkZ * 16 + rand.nextInt(16);

			gen.generate(world, rand, new BlockPos(x,y,z));
		}
	}

	//Trees
	private void runGenerator(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15);
		int z = (chunkZ * 16) + random.nextInt(15);
		int y = calculateGroundHeight(world, x, z, topBlock);
		BlockPos pos = new BlockPos(x,y,z);

		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

		if(world.getWorldType() != WorldType.FLAT)
		{
			if(classesList.contains(biome))
			{
				if(random.nextInt(chance) == 0)
				{
					generator.generate(world, random, pos);
				}
			}
		}
	}

	private static int calculateGroundHeight(World world, int x, int z, Block groundBlock)
	{
		int y = world.getHeight();
		boolean foundGround = false;

		while(!foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x,y,z)).getBlock();
			foundGround = block == groundBlock;
		}

		return y;
	}
}
