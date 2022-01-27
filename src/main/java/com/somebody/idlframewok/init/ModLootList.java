package com.somebody.idlframewok.init;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.ChancePicker;
import com.google.common.collect.Sets;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Collections;
import java.util.Set;

import static com.somebody.idlframewok.util.Reference.MOD_ID;

public class ModLootList {

        private static final Set<ResourceLocation> LOOT_TABLES = Sets.<ResourceLocation>newHashSet();
        private static final Set<ResourceLocation> READ_ONLY_LOOT_TABLES = Collections.<ResourceLocation>unmodifiableSet(LOOT_TABLES);
        public static final ResourceLocation EMPTY = register("empty");
        public static final ResourceLocation M_O_B = register("entities/mor_orbital_beacon");
        public static final ResourceLocation SP_TEMP = new ResourceLocation(MOD_ID,"sp_temp");
        public static final ResourceLocation WORLD_SPINE_OCEAN = new ResourceLocation(MOD_ID,"world_spine");

        private static ResourceLocation register (String id)
        {
            return new ResourceLocation(MOD_ID, id);
        }

        public static void initLootTables() {
            LootTableList.register(SP_TEMP);
            LootTableList.register(WORLD_SPINE_OCEAN);
            //Idealland.Log("Loot register done, success = %s", LootTableList.test());
        }

        public static void initCrateList()
        {
            ChancePicker picker = ModBlocks.CRATE.tributeReward;
            int SSR = 1;
            int SR = 10;
            int R = 25;
            int U = 75;
            int N = 250;

            picker.AddTuple(SSR, Item.getItemFromBlock(Blocks.DIAMOND_BLOCK));

            picker.AddTuple(SR, Items.ELYTRA);
            picker.AddTuple(SR, Items.DIAMOND_SWORD);
            picker.AddTuple(SR, Items.DIAMOND_PICKAXE);
            picker.AddTuple(SR, Items.CHAINMAIL_BOOTS, 1);
            picker.AddTuple(SR, Items.CHAINMAIL_HELMET, 1);
            picker.AddTuple(SR, Items.CHAINMAIL_CHESTPLATE, 1);
            picker.AddTuple(SR, Items.CHAINMAIL_LEGGINGS, 1);
            picker.AddTuple(SR, Items.DIAMOND, 1, 3);

            picker.AddTuple(R, Item.getItemFromBlock(Blocks.TNT), 1);
            picker.AddTuple(R, Items.IRON_INGOT, 1, 3);
            picker.AddTuple(R, Items.GOLD_INGOT, 1, 3);
            picker.AddTuple(R, Items.GOLDEN_HELMET, 1);
            picker.AddTuple(R, Items.GOLDEN_CHESTPLATE, 1);
            picker.AddTuple(R, Items.GOLDEN_LEGGINGS, 1);
            picker.AddTuple(R, Items.GOLDEN_BOOTS, 1);
            picker.AddTuple(R, Items.GOLDEN_PICKAXE, 1);
            picker.AddTuple(R, Items.GOLDEN_SWORD, 1);
            picker.AddTuple(R, Items.GOLDEN_APPLE, 1);
            picker.AddTuple(R, ModItems.CLOVER, 1,4);

            picker.AddTuple(U, Items.LEATHER_HELMET, 1);
            picker.AddTuple(U, Items.LEATHER_CHESTPLATE, 1);
            picker.AddTuple(U, Items.LEATHER_LEGGINGS, 1);
            picker.AddTuple(U, Items.LEATHER_BOOTS, 1);
            picker.AddTuple(U, Items.STONE_PICKAXE, 1);
            picker.AddTuple(U, Items.STONE_SWORD, 1);
            picker.AddTuple(U, Items.CARROT, 1, 2);
            picker.AddTuple(U, Items.POTATO, 1, 2);
            picker.AddTuple(U, Items.WATER_BUCKET, 1);
            picker.AddTuple(U, Item.getItemFromBlock(Blocks.LADDER), 4, 16);

            picker.AddTuple(N, ModItems.FLOAT_FOOD, 1, 4);
            picker.AddTuple(N, Item.getItemFromBlock(Blocks.TORCH), 1, 4);
            picker.AddTuple(N, Item.getItemFromBlock(ModBlocks.CLIMB_PILLAR), 4, 16);
            picker.AddTuple(N, Items.ARROW, 1, 16);
            picker.AddTuple(N, Items.BREAD, 1, 4);
            picker.AddTuple(N, Items.STICK, 1, 4);
            picker.AddTuple(N, Items.POISONOUS_POTATO, 1, 4);
            picker.AddTuple(N, Items.ROTTEN_FLESH, 1, 4);
            picker.AddTuple(N, Items.BONE, 1, 4);
            picker.AddTuple(N, Item.getItemFromBlock(Blocks.PLANKS), 1, 4);
        }

}
