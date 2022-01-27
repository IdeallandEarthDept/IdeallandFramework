package com.somebody.idlframewok.designs.god16;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.potion.InitPotionTypes;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.world.biome.GodBelieverSingle;
import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.Biome;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class Init16Gods {
    public static God16Base[] GODS;

    public static Predicate<EntityLivingBase> NOT_GOD_BEILIVER[] = new Predicate[CHUNK_SIZE];

    public static God16Base getGodByIndex(int index)
    {
        if (index < 0 || index >= GODS.length)
        {
            return null;
        }
        return GODS[index];
    }

    public static void init()
    {
        GODS = new God16Base[16];
        for (int i = 0; i < CHUNK_SIZE; i++)
        {
            GODS[i] = new God16Base(i);
            NOT_GOD_BEILIVER[i] = GODS[i].NOT_GOD_BEILIVER;
        }
        initSymbols();
        initTributeReward();
        initBuff();
    }

    static void initBuff()
    {
        GODS[0].setBuff(MobEffects.INVISIBILITY, MobEffects.BLINDNESS);
        GODS[0].setPotionType(PotionTypes.INVISIBILITY, InitPotionTypes.BLINDNESS);

        GODS[1].setBuff(null, ModPotions.BURN);
        GODS[1].setPotionType(ModPotions.BURN.getPotionType(), true);

        GODS[2].setBuff(MobEffects.REGENERATION, null);
        GODS[2].setPotionType(PotionTypes.LONG_REGENERATION, false);

        GODS[3].setBuff(null, MobEffects.UNLUCK);
        GODS[3].setPotionType(InitPotionTypes.UNLUCKY, false);

        GODS[4].setBuff(MobEffects.WATER_BREATHING, null);
        GODS[4].setPotionType(PotionTypes.WATER_BREATHING, false);

        GODS[5].setBuff(null, MobEffects.POISON);
        GODS[5].setPotionType(PotionTypes.STRONG_POISON, true);

        GODS[6].setBuff(MobEffects.STRENGTH, null);
        GODS[6].setPotionType(PotionTypes.STRONG_STRENGTH, false);

        GODS[7].setBuff(MobEffects.HASTE, null);
        GODS[7].setPotionType(InitPotionTypes.HASTE, false);

        GODS[8].setBuff(MobEffects.RESISTANCE, null);
        GODS[8].setPotionType(InitPotionTypes.RESISTANCE, false);

        GODS[9].setBuff(null, MobEffects.WEAKNESS);
        GODS[9].setPotionType(PotionTypes.WEAKNESS, true);

        GODS[10].setBuff(null, MobEffects.SLOWNESS);
        GODS[10].setPotionType(PotionTypes.SLOWNESS, true);

        GODS[11].setBuff(MobEffects.LUCK, null);
        GODS[11].setPotionType(InitPotionTypes.LUCKY, false);

        GODS[12].setBuff(MobEffects.SPEED, null);
        GODS[12].setPotionType(PotionTypes.STRONG_SWIFTNESS, false);

        GODS[13].setBuff(null, MobEffects.WITHER);
        GODS[13].setPotionType(InitPotionTypes.WITHER, true);

        GODS[14].setBuff(MobEffects.FIRE_RESISTANCE, null);
        GODS[14].setPotionType(PotionTypes.FIRE_RESISTANCE, false);

        GODS[15].setBuff(MobEffects.NIGHT_VISION, MobEffects.GLOWING);
        GODS[15].setPotionType(PotionTypes.NIGHT_VISION, InitPotionTypes.GLOWING);
    }

    static void initTributeReward()
    {
        ItemStack stack = new ItemStack(Items.SHIELD);

        for (int i = 0; i < CHUNK_SIZE; i++)
        {
            GODS[i].tributeReward.AddTuple(100, new ItemStack(Items.BANNER, 1, i));
            GODS[i].tributeReward.AddTuple(30, new ItemStack(Items.BED, 1, CHUNK_SIZE - i));
            GODS[i].tributeReward.AddTuple(100, new ItemStack(Items.DYE, 1, i));
            GODS[i].tributeReward.AddTuple(100, new ItemStack(Blocks.WOOL, 1, CHUNK_SIZE - i));
            GODS[i].tributeReward.AddTuple(100, new ItemStack(Blocks.CARPET, i, CHUNK_SIZE - i));
//            GODS[i].tributeReward.AddTuple(1, new ItemStack(ModBlocks.SKYLAND_GOD_RUNSTONES[i], 1));
        }

        GODS[0].tributeReward.AddTuple(1, new ItemStack(Blocks.BEDROCK, 1));

        int TOOL_WEIGHT = 50;
        GODS[0].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.DIAMOND_BOOTS, 1));
        GODS[1].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.FLINT_AND_STEEL, 1));
        GODS[2].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.DIAMOND_HOE, 1));
        GODS[3].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.DIAMOND_SHOVEL, 1));
        GODS[4].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.WATER_BUCKET, 1));
        GODS[5].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.SHEARS, 1));
        GODS[6].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.DIAMOND_SWORD, 1));
        NBTTagCompound nbt = ItemBanner.makeBanner(EnumDyeColor.byDyeDamage(7), null).getTagCompound();
        stack.setTagCompound(nbt);
        GODS[7].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.SHIELD, 1));
        GODS[8].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.DIAMOND_CHESTPLATE, 1));
        GODS[9].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.BOW, 1));
        GODS[10].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.DIAMOND_AXE, 1));
        GODS[11].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.GOLDEN_HORSE_ARMOR, 1));
        GODS[12].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.DIAMOND_LEGGINGS, 1));
        GODS[13].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.FISHING_ROD, 1));
        GODS[14].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.DIAMOND_PICKAXE, 1));
        GODS[15].tributeReward.AddTuple(TOOL_WEIGHT, new ItemStack(Items.DIAMOND_HELMET, 1));
    }

    static void initSymbols()
    {
        GODS[0].drawLineOnMap(1,1,1,14);
        GODS[0].drawLineOnMap(14,1,14,14);

        //----------------------------fire
        //need checking
        GODS[1].setSymmetric(God16Base.Symmetric.HORI, true);
        for (int i = 0; i <= 3; i++ )
        {
            GODS[1].drawPoint(1 + (i >> 0), (i << 1)+1);
            GODS[1].drawPoint(1 + (i >> 0), (i << 1)+2);

            GODS[1].drawPoint(7 - (i >> 0), (i << 1)+1);
            GODS[1].drawPoint(7 - (i >> 0), (i << 1)+2);

            GODS[1].drawPoint(1 + (i >> 0), 14 - (i << 1));
            GODS[1].drawPoint(1 + (i >> 0), 13 - (i << 1));
        }
        //----------------------------
        GODS[2].setSymmetric(God16Base.Symmetric.HORI, true);
        GODS[2].drawLineOnMap(1,1,1,14);
        GODS[2].drawLineOnMap(3,1,3,11);
        GODS[2].drawLineOnMap(5,3,5,14);
        GODS[2].drawLineOnMap(7,7,7,8);
        GODS[2].drawLineOnMap(3,1,7,1);
        GODS[2].drawLineOnMap(2,14,7,14);
        //---------------------------------
        GODS[3].setSymmetric(God16Base.Symmetric.HORI, true);
        GODS[3].drawLineOnMap(1,1,5,1);
        GODS[3].drawLineOnMap(6,14,6,1);
        GODS[3].drawPoint(7,14);
        GODS[3].drawLineOnMap(1,5,4,5);
        GODS[3].drawLineOnMap(1,10,4,10);
        GODS[3].drawLineOnMap(1,14,4,14);
        //---------------------------------
        //water
        GODS[4].drawLineOnMap(1,1,6,1);
        GODS[4].drawLineOnMap(1,6,6,6);
        GODS[4].drawLineOnMap(6,1,6,6);

        GODS[4].drawLineOnMap(9,1,9,6);

        GODS[4].drawLineOnMap(11,1,14,1);
        GODS[4].drawLineOnMap(14,1,14,6);
        GODS[4].drawLineOnMap(11,6,14,6);

        GODS[4].drawPoint(7,8);
        GODS[4].drawPoint(8,7);

        GODS[4].drawLineOnMap(1,9,4,9);
        GODS[4].drawLineOnMap(1,9,1,14);
        GODS[4].drawLineOnMap(1,14,4,14);

        GODS[4].drawLineOnMap(6,9,6,14);

        GODS[4].drawLineOnMap(9,9,14,9);
        GODS[4].drawLineOnMap(9,9,9,14);
        GODS[4].drawLineOnMap(9,14,14,14);
        //---------------------------------
        //poison
        GODS[5].drawRectOnMap(1,1,6,6);
        GODS[5].drawRectOnMap(9,5,14,10);
        GODS[5].drawRectOnMap(1,9,6,14);
        //--------------------------------
        //male
        GODS[6].drawRectOnMap(1,9,6,14);
        GODS[6].drawLineOnMap(7,8,14,1);
        GODS[6].drawLineOnMap(9,1,14,1);
        GODS[6].drawLineOnMap(14,6,14,1);
        //--------------------------------
        //iron
        GODS[7].drawLineOnMap(1,9,6,14);
        GODS[7].drawLineOnMap(1,14,14,1);
        //----------------------------------
        GODS[15].setSymmetric(God16Base.Symmetric.VERT, true);
        GODS[15].drawLineOnMap(1,1,14,1);
        GODS[15].drawLineOnMap(1,6,14,6);
        //todo: other symbols

        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
        {
            for (int god = 0; god <= 15; god++)
            {
                Idealland.Log("God %d:",god);
                for (int y = 0; y <= 15; y++)
                {
                    String s = "";
                    for (int x = 0; x <= 15; x++)
                    {
                        s = s + (GODS[god].symbolMap[x][y] ? "BB" : "  ");
                    }
                    Idealland.Log(s);
                }
            }
        }
    }

    public static int getBelief(EntityLivingBase livingBase, int godIndex)
    {
        if (godIndex < 0 || godIndex >= CHUNK_SIZE)
        {
            return 0;
        }
        else {
            return GODS[godIndex].getBeliefPoint(livingBase);
        }
    }

    public static void setBelief(EntityLivingBase livingBase, int godIndex, int value)
    {
        if (godIndex < 0 || godIndex >= CHUNK_SIZE)
        {
            return;
        }
        else {
            IDLNBTUtil.setIntAuto(livingBase, Color16Def.getKeyTribute(godIndex), value);
        }
    }

    //returns the same-diff in binary.
    // 4 same = 4,
    // 3 same = 2,
    // 2 same = 0,
    // 1 same = -2,
    // 0 same = -4
    public static int getRelationship(int index1, int index2) {
        int diff = index1 ^ index2;
        return ((diff & 8)>>3) +
                ((diff & 4)>>2) +
                ((diff & 2)>>1) +
                ((diff & 1)) - 2;

    }

    public static boolean isOfGod(int god, Biome biome) {
        if (biome instanceof GodBelieverSingle) {
            int index = ((GodBelieverSingle) biome).getGodIndex();
            return index == god;
        }
        return false;
    }
}
