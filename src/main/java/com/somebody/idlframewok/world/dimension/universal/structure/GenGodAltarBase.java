package com.somebody.idlframewok.world.dimension.universal.structure;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.designs.god16.Init16Gods;
import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

import static com.somebody.idlframewok.util.Color16Def.*;
import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;
import static com.somebody.idlframewok.util.CommonDef.MAX_BUILD_HEIGHT;

public class GenGodAltarBase {
    public final int index;
    public IBlockState GROUND;
    public IBlockState WALL;
    public IBlockState BLANK_RUNE = ModBlocks.SKYLAND_BLANK_RUNESTONE.getDefaultState();
    public IBlockState GOD_RUNE;
    public IBlockState GLASS;
    public IBlockState AIR = Blocks.AIR.getDefaultState();
    EnumDyeColor color;


    public GenGodAltarBase(int index) {
        this.index = index;

        color = EnumDyeColor.byDyeDamage(index);

        GROUND = Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, color);
        WALL = Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, color);
        GOD_RUNE = ModBlocks.SKYLAND_GOD_RUNSTONES[index].getDefaultState();
        GLASS = Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockColored.COLOR, color);
    }

    int minY = 16;
    int deltaY = MAX_BUILD_HEIGHT - minY - CHUNK_SIZE;

    public int getRandomProperY(Random rand, Chunk chunk)
    {
        int seaLevel = chunk.getWorld().getSeaLevel();
        switch (index)
        {
            case EARTH:
                return 1;
            case WATER:
                return minY + rand.nextInt(seaLevel - minY);
            case SKY:
                return chunk.getWorld().getSeaLevel() + rand.nextInt(deltaY - seaLevel);
            default:
                return minY + rand.nextInt(deltaY - minY);
        }
    }

    //build floor & ceilling
    int floorBegin = 6;
    int floorEnd = 9;
    int wallSide1 = 5;
    int wallSide2 = 10;
    int wallHeight = 6;
    int wallEnd = floorEnd+1;
    int mainRuneZ = wallEnd;
    int backWallZ = mainRuneZ + 1;

    public boolean generate(int y0, Chunk chunk) {
//        Idealland.Log("Gen Altar @%d,%d,%d", y0, chunk.x, chunk.z);
        int wallMax = y0 + wallHeight - 1;
        int ceilingY = y0 + wallHeight - 1;

        //revive stone
        WorldGenUtil.setBlockState(chunk, 7, y0, floorBegin - 1, ModBlocks.REVIVE_STONE.getDefaultState());
        WorldGenUtil.setBlockState(chunk, 8, y0, floorBegin - 1, ModBlocks.REVIVE_STONE.getDefaultState());

        for (int x = floorBegin; x <= floorEnd; x++)
        {
            for (int z = floorBegin; z <= floorEnd; z++)
            {
                //empty the room
                for (int y = y0 + 1; y <= ceilingY; y++)
                    WorldGenUtil.setBlockState(chunk, x, y, z, AIR);
            }
        }

        for (int x = floorBegin; x <= floorEnd; x++)
        {
            for (int z = floorBegin; z <= floorEnd; z++)
            {
                //floor
                WorldGenUtil.setBlockState(chunk, x, y0, z, GROUND);
                //ceiling
                WorldGenUtil.setBlockState(chunk, x, ceilingY, z, GLASS);
            }
        }

        //sidewall
        for (int y = y0; y <= wallMax; y++)
        {
            for (int z = floorBegin; z <= wallEnd; z++)
            {
                //sidewall Left
                WorldGenUtil.setBlockState(chunk, wallSide1 , y, z, WALL);
                //sidewall right
                WorldGenUtil.setBlockState(chunk, wallSide2 , y, z, WALL);
            }
        }

        //window
        for (int y = y0+1; y <= wallMax-1; y++)
        {
            for (int z = floorBegin+1; z <= wallEnd-2; z++)
            {
                WorldGenUtil.setBlockState(chunk, wallSide1 , y, z, GLASS);
                WorldGenUtil.setBlockState(chunk, wallSide2 , y, z, GLASS);
            }
        }

        //backwall
        for (int x = wallSide1; x <= wallSide2; x++)
        {
            //border of main rune
            WorldGenUtil.setBlockState(chunk, x , wallMax, wallEnd, WALL);
            WorldGenUtil.setBlockState(chunk, x , y0, wallEnd+1, WALL);
        }

        for (int y = 0; y <= 3; y++)
        {
            for (int x = 0; x <= 3; x++)
            {
                int x1 = floorBegin + x;
                int y1 = y + y0 + 1;
                //main rune
                WorldGenUtil.setBlockState(chunk, x1, y1, mainRuneZ, BLANK_RUNE);
                //backwall
                WorldGenUtil.setBlockState(chunk, x1, y1, backWallZ, WALL);
            }
        }

        genSpecial(y0, chunk);
        genSky(chunk);

        return true;
    }

    public void genSpecial(int y0, Chunk chunk)
    {
        //flag
        buildFlag(y0 + 3, chunk, floorBegin, floorBegin, EnumFacing.EAST);
        buildFlag(y0 + 3, chunk, floorEnd, floorBegin, EnumFacing.WEST);

        WorldGenUtil.setBlockState(chunk, floorBegin + (index & 3), y0 + 1 + index / 4, mainRuneZ, GOD_RUNE);
    }

    void genSky(Chunk chunk)
    {
        IBlockState PURE_SKY = ModBlocks.SKY_NIGHT_BLOCK.getDefaultState();
        IBlockState STAR_SKY = ModBlocks.SKY_STARRY_BLOCK.getDefaultState();

        int y = MAX_BUILD_HEIGHT;
        for (int x1 = 0; x1 < CHUNK_SIZE; x1++) {
            for (int z1 = 0; z1 < CHUNK_SIZE; z1++) {
                WorldGenUtil.setBlockState(chunk, x1, y, z1, Init16Gods.GODS[index].symbolMap[x1][z1] ? STAR_SKY : PURE_SKY);
            }
        }
    }


    private void buildFlag(int y, Chunk chunk, int x, int z, EnumFacing facing) {
//        IBlockState flag = Blocks.WALL_BANNER.getDefaultState();
//
//        WorldGenUtil.setBlockState(chunk, x , y, z, flag.withProperty(BlockWallSign.FACING, facing));
//        TileEntity tileentity = chunk.getTileEntity(new BlockPos(x,y,z), Chunk.EnumCreateEntityType.IMMEDIATE);
//        if (tileentity instanceof TileEntityBanner)
//        {
//            Idealland.LogWarning("Dye banner successful");
//            ((TileEntityBanner) tileentity).setItemValues(ItemBanner.makeBanner(color, (NBTTagList) null), false);
//        }
//        else {
//            Idealland.LogWarning("Banner is: ", tileentity);
//        }
    }
}
