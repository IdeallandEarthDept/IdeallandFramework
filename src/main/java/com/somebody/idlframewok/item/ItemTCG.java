package com.somebody.idlframewok.item;

import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.util.CommonDef;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemTCG extends ItemBase {

    public static final String PREFIX_DESC_RARITY = "desc.tcg.";

    public static final String DESC_C = PREFIX_DESC_RARITY + "common";
    public static final String DESC_U = PREFIX_DESC_RARITY + "uncommon";
    public static final String DESC_R = PREFIX_DESC_RARITY + "rare";
    public static final String DESC_M = PREFIX_DESC_RARITY + "mythic";

    public static final String DESC_SHARED = PREFIX_DESC_RARITY + "shared";

    public static final List<ItemTCG> LIST_C = new ArrayList<>();
    public static final List<ItemTCG> LIST_U = new ArrayList<>();
    public static final List<ItemTCG> LIST_R = new ArrayList<>();
    public static final List<ItemTCG> LIST_M = new ArrayList<>();

    public static List<ItemTCG> getListFromRarity(EnumRarity rarity)
    {
        switch (rarity) {
            case COMMON:
                return LIST_C;
            case RARE://actually u
                return LIST_U;
            case EPIC://actually r
                return LIST_R;
            default://actually m
                return LIST_M;
        }
    }

    public static ItemTCG getRandomCard(Random random, EnumRarity rarity)
    {
        List<ItemTCG> list = getListFromRarity(rarity);
        return list.get(random.nextInt(list.size()));
    }

    int index;

    public ItemTCG(int index) {
        super("tcg_"+index);
        this.index = index;
        setCreativeTab(ModCreativeTabsList.IDL_TCG);
        LIST_C.add(this);
    }

//    @Override
//    public String getItemStackDisplayName(ItemStack stack) {
//        if (getRarity(stack) == CommonDef.RARITY_SSR)
//        {
//            return CommonDef.RARITY_SSR.rarityColor + super.getItemStackDisplayName(stack);
//        }
//        else {
//            return super.getItemStackDisplayName(stack);
//        }
//    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        EnumRarity rarity = getRarity(stack);
        switch (rarity)
        {
            case COMMON://c
                tooltip.add(rarity.rarityColor + I18n.format(DESC_C));
                break;
            case RARE://u
                tooltip.add(rarity.rarityColor + I18n.format(DESC_U));
                break;
            case EPIC://r
                tooltip.add(rarity.rarityColor + I18n.format(DESC_R));
                break;
            default:
                tooltip.add(rarity.rarityColor + I18n.format(DESC_M));
                break;
        }
        return I18n.format(DESC_SHARED);
    }

    public ItemTCG setRarityTCG(int rarity)
    {
        overrideRarity = true;
        switch (rarity)
        {
            case 1:
                setRarity(EnumRarity.RARE);
                LIST_C.remove(this);
                LIST_U.add(this);
                break;
            case 2:
                setRarity(EnumRarity.EPIC);
                LIST_C.remove(this);
                LIST_R.add(this);
                break;
            case 3:
                setRarity(CommonDef.RARITY_SSR);
                LIST_C.remove(this);
                LIST_M.add(this);
                break;

            default:
                setRarity(EnumRarity.COMMON);
                //throw new IllegalStateException("Unexpected value: " + rarity);
        }
        return this;
    }
}
