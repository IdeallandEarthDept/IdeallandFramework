package com.somebody.idlframewok.designs.events.design.mjds;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.CommonDef;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsBlockColor {
//    static IBlockColor blockColor = new IBlockColor()
//    {
//        public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
//        {
//            if (pos != null && worldIn != null)
//            {
//                return Color16Def.getGodColor(pos.getY() % CommonDef.CHUNK_SIZE);
//            }
//            else {
//                return -1;
//            }
//        }
//    };
//
//    @SubscribeEvent
//    @SideOnly(Side.CLIENT)
//    public static void registerColor(ColorHandlerEvent.Block event)
//    {
//        event.getBlockColors().registerBlockColorHandler(blockColor, ModBlocks.MJDS_WALL);
//        Idealland.Log("Registered block color handler");
//    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerColor(ColorHandlerEvent.Block event) {
        //only works for particles, why? because you need to rewrite the json like leaves do.
        //more, -3%16 is negative.
//        event.getBlockColors().registerBlockColorHandler(new IBlockColor() {
//            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
//                if (pos != null && worldIn != null) {
//                    return Color16Def.getGodColor((pos.getY() / CommonDef.CHUNK_SIZE));
//                } else {
//                    return -1;
//                }
//            }
//        }, ModBlocks.MJDS_WALL);
        Idealland.Log("Registered block color handler");
    }
}
