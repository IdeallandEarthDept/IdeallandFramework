package com.somebody.idlframewok.world.biome.godlands.skylands;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.world.biome.BiomeBase;
import com.somebody.idlframewok.world.biome.GodBelieverSingle;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BiomeSkylandBase extends BiomeBase implements GodBelieverSingle {

    public static final float[] temperature = new float[]{
            1,2,1,1,
            1,1,0,0,
            1,1,1,1,
            -1,-1,-1,1
    };

    public final int index;

    public BiomeSkylandBase(int index) {
        super(new BiomePropertiesModified("skyland_"+index).setBaseHeight(0f).setHeightVariation(0.4f).setTemperature(temperature[index]).setWaterColor(Color16Def.getGodColor(index)));
        this.index = index;

        this.decorator.treesPerChunk = 1;
        this.decorator.flowersPerChunk = 5;
        this.decorator.grassPerChunk = 1;
        this.decorator.waterlilyPerChunk = 1;
        this.decorator.deadBushPerChunk = 8;
        this.decorator.mushroomsPerChunk = 1;
        this.decorator.reedsPerChunk = 1;

        CommonFunctions.addToEventBus(this);

        //spawnableCreatureList.clear();
        //spawnableCreatureList.add(new Biome.SpawnListEntry(EntitySheep.class, 100, 4, 4));
    }

    @SubscribeEvent
    void onGetGrassColor(BiomeEvent.GetGrassColor event)
   {
        if (event.getBiome() == this)
        {
            event.setNewColor(Color16Def.getGodColor(index));
        }
   }



    @Override
    public int getGodIndex() {
        return index;
    }
}
