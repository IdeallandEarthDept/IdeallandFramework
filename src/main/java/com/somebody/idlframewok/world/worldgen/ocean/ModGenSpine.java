package com.somebody.idlframewok.world.worldgen.ocean;

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.init.ModLootList;
import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class ModGenSpine implements IWorldGenerator {

    public ModGenSpine() {

    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

        if (random.nextFloat() > ModConfig.WORLD_GEN_CONF.SPINE_CHANCE)
        {
            return;
        }

        BlockPos pos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        Biome biome = world.getBiome(pos);

        int radius = 1;

        if (BiomeManager.oceanBiomes.contains(biome))
        {
            //avoid chunk border
            BlockPos posGen = pos.add(random.nextInt(CHUNK_SIZE - radius * 2) + radius, 0, random.nextInt(CHUNK_SIZE - radius * 2) + radius) ;

            int buildHeightLimit = world.getActualHeight();
            float halfHeight = buildHeightLimit/2f;

            if (buildHeightLimit == 0)
            {
                //prevent div 0 exception
                return;
            }
            int yMax = random.nextInt(buildHeightLimit);

            for (int y = 1; y < yMax; y++)
            {
                if (yMax - y > 5)
                {
                    CreateLogAt(random, world, posGen.add(0,y,0), EnumFacing.UP);
                }
                else{
                    CreateBoneAt(random, world, posGen.add(0,y,0), EnumFacing.UP);
                }

                if (y % 2 == 0 && (yMax - y > 5))
                {
                    CreateLogAt(random, world, posGen.add(1,y,0), EnumFacing.EAST);
                    CreateLogAt(random, world, posGen.add(0,y,1), EnumFacing.WEST);
                    CreateLogAt(random, world, posGen.add(-1,y,0), EnumFacing.SOUTH);
                    CreateLogAt(random, world, posGen.add(0,y,-1), EnumFacing.NORTH);

                    if (random.nextFloat() < 0.1f * (Math.abs(y - halfHeight) / halfHeight ))
                    {
                        BlockPos basePos = posGen.add(0,y,0);
                        world.setBlockState(posGen.add(0,y,0), Blocks.CHEST.getDefaultState(), WorldGenUtil.PREVENT_CASCADE_FLAGS_2);
                        TileEntity tileEntity1 = world.getTileEntity(basePos);

                        if (tileEntity1 instanceof TileEntityChest) {
                            ((TileEntityChest) tileEntity1).setLootTable(ModLootList.WORLD_SPINE_OCEAN, random.nextLong());
                        }
                    }
                }
            }
        }
    }

    void CreateBoneAt(Random random, World worldIn, BlockPos pos, EnumFacing facing)
    {
        if (worldIn.getBlockState(pos).getBlock() != Blocks.BEDROCK)
        {
            worldIn.setBlockState(pos, Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, facing.getAxis()), WorldGenUtil.PREVENT_CASCADE_FLAGS_2);
        }
    }

    void CreateLogAt(Random random, World worldIn, BlockPos pos, EnumFacing facing)
    {
        if (worldIn.getBlockState(pos).getBlock() != Blocks.BEDROCK)
        {
            int type = random.nextInt(100);
            if (type < 10)
            {
                worldIn.setBlockState(pos, Blocks.COAL_ORE.getDefaultState(), WorldGenUtil.PREVENT_CASCADE_FLAGS_2);
            }
            else if (type <= 20)
            {
                worldIn.setBlockState(pos, Blocks.STONE.getDefaultState(), WorldGenUtil.PREVENT_CASCADE_FLAGS_2);
            }
            else if (type <= 35)
            {
                worldIn.setBlockState(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState(), WorldGenUtil.PREVENT_CASCADE_FLAGS_2);
            }
            else if (type <= 40)
            {
                worldIn.setBlockState(pos, Blocks.PRISMARINE.getDefaultState(), WorldGenUtil.PREVENT_CASCADE_FLAGS_2);
            }
            else {
                worldIn.setBlockState(pos, Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, facing.getAxis()), WorldGenUtil.PREVENT_CASCADE_FLAGS_2);
            }
        }
    }

    void CreateVineAt(Random random, World worldIn, BlockPos pos, EnumFacing facing)
    {
        if (worldIn.getBlockState(pos).getBlock() == Blocks.AIR)
        {
            int type = random.nextInt(100);
            if (type < 10)
            {
                //worldIn.setBlockState(pos, Blocks.VINE.getDefaultState().withProperty(BlockRotatedPillar.AXIS, facing.getAxis(), 2);
            }
            else {
                worldIn.setBlockState(pos, Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, facing.getAxis()), WorldGenUtil.PREVENT_CASCADE_FLAGS_2);
            }
        }
    }
}
