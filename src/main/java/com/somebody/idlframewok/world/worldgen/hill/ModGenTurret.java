package com.somebody.idlframewok.world.worldgen.hill;

import com.somebody.idlframewok.entity.creatures.buildings.uprising.EntityMediumTurret;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.WorldGenUtil;
import com.somebody.idlframewok.util.WorldUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static net.minecraftforge.common.BiomeDictionary.Type.WATER;

public class ModGenTurret implements IWorldGenerator {

    int minHeight = 2;
    int heightDeltaRange = 8;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        BlockPos pos = new BlockPos(chunkX << 4, 64, chunkZ << 4);
        Biome biome = world.getBiome(pos);
        if (BiomeDictionary.getTypes(biome).contains(WATER)) {
            //dont spawn in water
            return;
        }

        if (!WorldUtil.isNotFlat(biome)) {
            //must spawn in hilly places
            return;
        }

        if (random.nextInt(3) != 0)
        {
            return;
        }

        BlockPos target = WorldGenUtil.getGroundPos(random, world, pos);
        if (target == null) return;

//        Idealland.Log("Build turret @%s", target);

        int height = minHeight + random.nextInt(heightDeltaRange);
        for (int i = 0; i < height; i++) {
            WorldGenUtil.setBlockState(world, target.add(0, i, 0), Blocks.STONEBRICK.getDefaultState());
        }
        WorldGenUtil.setBlockState(world, target.add(0, height, 0), Blocks.IRON_BLOCK.getDefaultState());

        EntityMediumTurret creature = new EntityMediumTurret(world);
        creature.setFaction(EntityUtil.EnumFaction.MOB_VANILLA);
        EntityUtil.setPosition(creature, target.add(0, height + 1, 0));

        world.spawnEntity(creature);
    }

}
