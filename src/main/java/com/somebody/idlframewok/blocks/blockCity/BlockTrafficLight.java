package com.somebody.idlframewok.blocks.blockCity;

import com.somebody.idlframewok.blocks.blockBasic.BlockRotatedPillarBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

//public class BlockTrafficLight extends BlockRotatedPillarBase implements ITileEntityProvider {
//    public Class<? extends TileEntity> tileEntity = TileEntityTrafficLight.class;
//    public BlockTrafficLight(String name,Material materialIn) {
//        super(name, materialIn);
//        setLightLevel(0.5f);
//    }
//
//    @Nullable
//    @Override
//    /**
//     * Returns a new instance of a block's tile entity class. Called on placing the block.
//     */
//    public TileEntity createNewTileEntity(World worldIn, int meta)
//    {
//        try {
//            return tileEntity.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//            return  null;
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//            return  null;
//        }
//    }
//}
