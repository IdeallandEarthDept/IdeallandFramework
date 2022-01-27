package com.somebody.idlframewok.world.worldgen.structure;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class TutorialGenSturcture implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (chunkZ == 0) {
            MinecraftServer server = world.getMinecraftServer();
            TemplateManager manager = world.getSaveHandler().getStructureTemplateManager();

            Template template = manager.get(server, new ResourceLocation(Idealland.MODID, "quadstone/quad_rail_stop"));

            BlockPos pos = new BlockPos(chunkX * 16, 30, chunkZ * 16);

            if (template == null) {
                return;
            }

            WorldGenUtil.setStopUpdate(true);
            Idealland.Log("Begin Tutorial");
            template.addBlocksToWorld(world, pos, new PlacementSettings().setChunk(new ChunkPos(pos)), 2 | 4 | 16);
            Idealland.Log("End Tutorial");
            WorldGenUtil.setStopUpdate(false);
        }
    }
}
