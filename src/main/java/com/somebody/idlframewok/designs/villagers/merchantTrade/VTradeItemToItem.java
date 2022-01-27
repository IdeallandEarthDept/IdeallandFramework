package com.somebody.idlframewok.designs.villagers.merchantTrade;

import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import javax.annotation.Nullable;
import java.util.Random;

public class VTradeItemToItem implements EntityVillager.ITradeList {

    public static final EntityVillager.PriceInfo PRICE_ONE = new EntityVillager.PriceInfo(1,1);

    public enum CostType {
        NONE,
        EMRALD,
        DIAMOND,
        GOLD_NUGGET,
        GOLD_INGOT,
    }

    public static ItemStack getStackFromType(CostType type)
    {
        switch (type)
        {
            case EMRALD:
                return new ItemStack(Items.EMERALD);
            case DIAMOND:
                return new ItemStack(Items.DIAMOND);
            case GOLD_NUGGET:
                return new ItemStack(Items.GOLD_NUGGET);
            case GOLD_INGOT:
                return new ItemStack(Items.GOLD_INGOT);
            default:
                return ItemStack.EMPTY;
        }
    }

    public ItemStack buyingItemStack = ItemStack.EMPTY;
    public EntityVillager.PriceInfo buyingPriceInfo;

    public ItemStack buyingItemStack2 = ItemStack.EMPTY;
    public EntityVillager.PriceInfo buyingPriceInfo2;

    public ItemStack sellingItemstack = ItemStack.EMPTY;
    public EntityVillager.PriceInfo sellingPriceInfo;

    //Item Version
    public VTradeItemToItem(Item costA, EntityVillager.PriceInfo priceInfo,
                            Item costB, EntityVillager.PriceInfo priceInfo2,
                            Item result, EntityVillager.PriceInfo priceInfo3)
    {
        this( new ItemStack(costA), priceInfo, new ItemStack(costB), priceInfo2, new ItemStack(result), priceInfo3);
    }

    //Stack Version
    public VTradeItemToItem(ItemStack buyingItemStack, EntityVillager.PriceInfo buyingPriceInfo, @Nullable ItemStack buyingItemStack2, @Nullable EntityVillager.PriceInfo buyingPriceInfo2, ItemStack sellingItemstack, EntityVillager.PriceInfo sellingPriceInfo) {
        this.buyingItemStack = buyingItemStack;
        this.buyingPriceInfo = buyingPriceInfo;
        this.buyingItemStack2 = buyingItemStack2 == null ? ItemStack.EMPTY : buyingItemStack2;
        this.buyingPriceInfo2 = buyingPriceInfo2 == null ? PRICE_ONE : buyingPriceInfo2;
        this.sellingItemstack = sellingItemstack;
        this.sellingPriceInfo = sellingPriceInfo;
    }

    //Stack X-Z Version
    public VTradeItemToItem(ItemStack buyingItemStack, EntityVillager.PriceInfo buyingPriceInfo, ItemStack sellingItemstack, EntityVillager.PriceInfo sellingPriceInfo) {
        this(buyingItemStack,buyingPriceInfo,ItemStack.EMPTY, PRICE_ONE, sellingItemstack, sellingPriceInfo);
    }

    //Stack X1-Z1 Item Version
    public VTradeItemToItem(Item costStack, Item resultStack) {
        this(new ItemStack(costStack), PRICE_ONE, new ItemStack(resultStack), PRICE_ONE);
    }

    //Stack X1-Z1 Version
    public VTradeItemToItem(ItemStack costStack, ItemStack resultStack) {
        this(costStack, PRICE_ONE, resultStack, PRICE_ONE);
    }

    public VTradeItemToItem(CostType costType, EntityVillager.PriceInfo costPriceInfo, ItemStack resultStack) {
        this(costType, costPriceInfo, resultStack, PRICE_ONE);
    }

    public VTradeItemToItem(CostType costType, ItemStack resultStack, EntityVillager.PriceInfo resultPriceInfo) {
        this(costType, PRICE_ONE, resultStack, resultPriceInfo);
    }

    public VTradeItemToItem(CostType costType, ItemStack resultStack, int count) {
        this(costType, PRICE_ONE, resultStack, new EntityVillager.PriceInfo(count, count));
    }

    public VTradeItemToItem(CostType costType, EntityVillager.PriceInfo costPriceInfo, ItemStack resultStack, EntityVillager.PriceInfo sellingPriceInfo) {
        this(getStackFromType(costType), costPriceInfo, resultStack, sellingPriceInfo);
    }

    @Override
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
        int countA = buyingPriceInfo.getPrice(random);
        int countB = buyingPriceInfo2 == null ? 0 : buyingPriceInfo2.getPrice(random);
        int countResult = sellingPriceInfo.getPrice(random);
        recipeList.add(
                new MerchantRecipe(
                        CommonFunctions.copyAndSetCount(buyingItemStack, countA),
                        CommonFunctions.copyAndSetCount(buyingItemStack2, countB),
                        CommonFunctions.copyAndSetCount(sellingItemstack, countResult)
                )
        );
    }

}
