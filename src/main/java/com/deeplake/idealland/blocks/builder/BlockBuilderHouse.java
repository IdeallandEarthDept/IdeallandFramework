package com.deeplake.idealland.blocks.builder;

import com.deeplake.idealland.blocks.BlockBase;
import com.deeplake.idealland.blocks.tileEntity.builder.TileEntityBuilderHouse;
import com.deeplake.idealland.blocks.tileEntity.builder.TileEntityBuilderOne;
import com.deeplake.idealland.init.ModCreativeTab;
import com.deeplake.idealland.util.CommonDef;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBuilderHouse extends BlockBase implements ITileEntityProvider {

    public BlockBuilderHouse(String name, Material material) {
        super(name, material);
        setCreativeTab(ModCreativeTab.IDL_BUILDER);
        setSoundType(SoundType.METAL);
        setHardness(5.0F);
        setResistance(15.0F);
        setHarvestLevel("pickaxe", 3);
        setLightOpacity(1);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        TileEntityBuilderHouse t = new TileEntityBuilderHouse();
        t.buildRatePerTick = 4f;
        t.curBuildCounter = - CommonDef.TICK_PER_SECOND * 3;
        return t;
    }
}
