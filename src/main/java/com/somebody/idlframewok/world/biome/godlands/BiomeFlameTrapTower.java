package com.somebody.idlframewok.world.biome.godlands;

import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.world.biome.BiomeBase;
import com.somebody.idlframewok.world.biome.GodBelieverSingle;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

import static net.minecraft.block.BlockColored.COLOR;

public class BiomeFlameTrapTower extends BiomeBase implements GodBelieverSingle {
    public BiomeFlameTrapTower() {
        super(new BiomePropertiesModified("flame_trap_tower")
                .setBaseHeight(0.4f)
                .setHeightVariation(0.1f)
                .setTemperature(1.5f)
                .setWaterColor(Color16Def.getGodColor(Color16Def.FIRE)));
        fillerBlock = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.RED);
        topBlock = Blocks.CONCRETE.getDefaultState().withProperty(COLOR, EnumDyeColor.RED);
    }

    @Override
    public int getGodIndex() {
        return Color16Def.FIRE;
    }
}
