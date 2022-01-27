package com.somebody.idlframewok.designs.villagers.merchantTrade;

import com.somebody.idlframewok.item.ItemTCG;
import net.minecraft.entity.IMerchant;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

public class VTradeTCG extends VTradeItemToItem {
    EnumRarity rarity = EnumRarity.COMMON;

    public static Item getCostFromType(EnumRarity type)
    {
        switch (type)
        {
            case COMMON:
                return Items.GOLD_NUGGET;
            case RARE://actually u
                return Items.GOLD_INGOT;
            case EPIC://actually r
                return Items.EMERALD;
            default://actually m
                return Item.getItemFromBlock(Blocks.EMERALD_BLOCK);
        }
    }

    public VTradeTCG(EnumRarity rarity)
    {
        super(Items.AIR, PRICE_ONE, Items.AIR, PRICE_ONE, getCostFromType(rarity), PRICE_ONE);
        this.rarity = rarity;
    }

    @Override
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
        recipeList.add(
                new MerchantRecipe(
                        new ItemStack(ItemTCG.getRandomCard(random, rarity)),
                        ItemStack.EMPTY,
                        sellingItemstack,
                        0, 4
                )
        );
    }

}
