package com.somebody.idlframewok.designs.villagers;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.designs.villagers.merchantTrade.VTradeItemToItem;
import com.somebody.idlframewok.designs.villagers.merchantTrade.VTradeTCG;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.potion.InitPotionTypes;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.CommonDef;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

import static com.somebody.idlframewok.designs.villagers.merchantTrade.VTradeItemToItem.PRICE_ONE;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class InitVillagers {

    public static final List<VillagerRegistry.VillagerProfession> PROFESSION_LIST = new ArrayList<>();

    public static final ModVProfession CARD =
            new ModVProfession( "card");

    public static final ModVCareer CARD_0 = new ModVCareer(CARD, "card_player");
    public static final ModVCareer CARD_1 = new ModVCareer(CARD, "card_player_pauper");
    public static final ModVCareer CARD_2 = new ModVCareer(CARD, "card_player_spike");

    public static final ModVProfession SCRYER =
            new ModVProfession( "scryer");

    public static final ModVCareer SCRYER_C0 = new ModVCareer(SCRYER, "scryer_0");
    public static final ModVCareer SCRYER_C1 = new ModVCareer(SCRYER, "scryer_1");

    public static final ModVProfession VENDOR =
            new ModVProfession( "vendor");

    public static final ModVCareer VENDOR_C0 = new ModVCareer(VENDOR, "vendor_0");
    public static final ModVCareer VENDOR_C1 = new ModVCareer(VENDOR, "vendor_1");
    public static final ModVCareer VENDOR_C2 = new ModVCareer(VENDOR, "vendor_2");
    public static final ModVCareer VENDOR_AMMO = new ModVCareer(VENDOR, "ammo_dealer");
    public static final ModVCareer VENDOR_STONE = new ModVCareer(VENDOR, "stone_dealer");

    public static final ModVProfession ENGINEER =
            new ModVProfession( "engineer");

    public static final ModVCareer VENDOR_PHOTO_ELECTRONICS = new ModVCareer(ENGINEER, "engineer_light");
    public static final ModVCareer VENDOR_LOGISTICS = new ModVCareer(ENGINEER, "engineer_logistics");
    public static final ModVCareer VENDOR_ELECTRO_DYNAMICS = new ModVCareer(ENGINEER, "engineer_electro_dynamics");
    public static final ModVCareer VENDOR_MICRO_ELECTRONICS = new ModVCareer(ENGINEER, "engineer_logical");
    public static final ModVCareer VENDOR_RAIL = new ModVCareer(ENGINEER, "engineer_rail");


    public static final EntityVillager.PriceInfo PRICE_32 = new EntityVillager.PriceInfo(32, 32);
    public static final EntityVillager.PriceInfo PRICE_64 = new EntityVillager.PriceInfo(64, 64);
    public static final EntityVillager.PriceInfo PRICE_1_32_INV = new EntityVillager.PriceInfo(-32, -1);

    public static final ItemStack GOLD_INGOT = new ItemStack(Items.GOLD_INGOT);

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event)
    {

        IForgeRegistry<VillagerRegistry.VillagerProfession> registry = event.getRegistry();
        SCRYER_C0.addTrade(1,
                new EntityVillager.ListItemForEmeralds(ModItems.GUA[0], PRICE_1_32_INV),
                new EntityVillager.ListItemForEmeralds(ModItems.GUA[1], PRICE_1_32_INV),
                new EntityVillager.ListItemForEmeralds(ModItems.GUA[2], PRICE_1_32_INV),
                new EntityVillager.ListItemForEmeralds(ModItems.GUA[3], PRICE_1_32_INV),
                new VTradeItemToItem(ModItems.YANG_SIGN, ModItems.YIN_SIGN));
        SCRYER_C0.addTrade(2,
                new EntityVillager.EmeraldForItems(ModItems.GUA[0], PRICE_32),
                new EntityVillager.EmeraldForItems(ModItems.GUA[1], PRICE_32),
                new EntityVillager.EmeraldForItems(ModItems.GUA[2], PRICE_32),
                new EntityVillager.EmeraldForItems(ModItems.GUA[3], PRICE_32));

        SCRYER_C1.addTrade(1,
                new EntityVillager.ListItemForEmeralds(ModItems.GUA[4], PRICE_1_32_INV),
                new EntityVillager.ListItemForEmeralds(ModItems.GUA[5], PRICE_1_32_INV),
                new EntityVillager.ListItemForEmeralds(ModItems.GUA[6], PRICE_1_32_INV),
                new EntityVillager.ListItemForEmeralds(ModItems.GUA[7], PRICE_1_32_INV),
                new VTradeItemToItem(ModItems.YIN_SIGN, ModItems.YANG_SIGN));
        SCRYER_C1.addTrade(2,
                new EntityVillager.EmeraldForItems(ModItems.GUA[4], PRICE_32),
                new EntityVillager.EmeraldForItems(ModItems.GUA[5], PRICE_32),
                new EntityVillager.EmeraldForItems(ModItems.GUA[6], PRICE_32),
                new EntityVillager.EmeraldForItems(ModItems.GUA[7], PRICE_32));

        ItemStack crate = new ItemStack(ModBlocks.CRATE);

        VENDOR_C0.addTrade(1,
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, PRICE_ONE, crate),
                new VTradeItemToItem(VTradeItemToItem.CostType.EMRALD, crate, 10));
        VENDOR_C0.addTrade(2,
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_NUGGET, new EntityVillager.PriceInfo(3, 9), crate));

        VENDOR_C1.addTrade(1,
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, new EntityVillager.PriceInfo(9,9), new ItemStack(Items.EMERALD)),
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, new EntityVillager.PriceInfo(9,9), new ItemStack(Items.DIAMOND)),
                new VTradeItemToItem(VTradeItemToItem.CostType.EMRALD, new ItemStack(Items.GOLD_INGOT), 8),
                new VTradeItemToItem(VTradeItemToItem.CostType.DIAMOND, new ItemStack(Items.GOLD_INGOT), 8));

        VENDOR_C2.addTrade(1,
                new VTradeItemToItem(new ItemStack(ModItems.JADE_CRUDE), new ItemStack(ModItems.JADE_SEAL_PACK)));
                new VTradeItemToItem(new ItemStack(ModItems.JADE), PRICE_ONE, new ItemStack(Items.QUARTZ), new EntityVillager.PriceInfo(12,16));
        VENDOR_C2.addTrade(2,
                new VTradeItemToItem(new ItemStack(ModItems.JADE_CRUDE), PRICE_ONE, new ItemStack(Items.GOLD_NUGGET), new EntityVillager.PriceInfo(7,9)));
                new VTradeItemToItem(new ItemStack(ModItems.JADE_CRUDE), PRICE_ONE, new ItemStack(Items.QUARTZ), new EntityVillager.PriceInfo(2,4));


        registerRedstone(VENDOR_PHOTO_ELECTRONICS, new ItemStack[]{new ItemStack(Blocks.REDSTONE_LAMP), new ItemStack(Blocks.DAYLIGHT_DETECTOR), new ItemStack(Blocks.OBSERVER)});
        registerRedstone(VENDOR_LOGISTICS, new ItemStack[]{new ItemStack(Blocks.DISPENSER), new ItemStack(Blocks.DROPPER), new ItemStack(Blocks.HOPPER)});
        registerRedstone(VENDOR_ELECTRO_DYNAMICS, new ItemStack[]{new ItemStack(Blocks.SLIME_BLOCK), new ItemStack(Blocks.PISTON), new ItemStack(Blocks.STICKY_PISTON)});
        registerRedstone(VENDOR_MICRO_ELECTRONICS, new ItemStack[]{new ItemStack(Blocks.UNPOWERED_COMPARATOR), new ItemStack(Blocks.UNPOWERED_REPEATER), new ItemStack(Blocks.LEVER), new ItemStack(Blocks.TORCH)});
        registerRedstone(VENDOR_RAIL, new ItemStack[]{new ItemStack(Items.MINECART), new ItemStack(Blocks.RAIL), new ItemStack(Blocks.ACTIVATOR_RAIL), new ItemStack(Blocks.DETECTOR_RAIL), new ItemStack(Blocks.GOLDEN_RAIL)});

        registerMassive(VENDOR_AMMO, new ItemStack(Items.FIRE_CHARGE), 4);
        registerMassiveTiny(VENDOR_AMMO, new ItemStack(Items.ARROW), 2);

        ItemStack stack = new ItemStack(Items.TIPPED_ARROW);
        PotionUtils.addPotionToItemStack(stack, PotionTypes.STRONG_POISON);
        registerMassive(VENDOR_AMMO, stack, 3);

        stack = new ItemStack(Items.TIPPED_ARROW);
        PotionUtils.addPotionToItemStack(stack, ModPotions.BURN.getPotionType());
        registerMassive(VENDOR_AMMO, stack, 3);

        stack = new ItemStack(Items.TIPPED_ARROW);
        PotionUtils.addPotionToItemStack(stack, InitPotionTypes.WITHER);
        registerMassive(VENDOR_AMMO, stack, 3);

        VENDOR_STONE.addTrade(1,
                        new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, new ItemStack(Blocks.STONE), 32),
                        new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, new ItemStack(Blocks.STONE, 1, 1), 32),
                        new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, new ItemStack(Blocks.STONE, 1, 2), 32),
                        new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, new ItemStack(Blocks.STONE, 1, 3), 32),
                        new VTradeItemToItem(new ItemStack(Blocks.STONE), PRICE_64, GOLD_INGOT, PRICE_ONE),
                        new VTradeItemToItem(new ItemStack(Blocks.STONE, 1, 1), PRICE_64, GOLD_INGOT, PRICE_ONE),
                        new VTradeItemToItem(new ItemStack(Blocks.STONE, 1, 2), PRICE_64, GOLD_INGOT, PRICE_ONE),
                        new VTradeItemToItem(new ItemStack(Blocks.STONE, 1, 3), PRICE_64, GOLD_INGOT, PRICE_ONE));

        VENDOR_STONE.addTrade(2,
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, new ItemStack(Blocks.BRICK_BLOCK), 32),
                new VTradeItemToItem(new ItemStack(Blocks.BRICK_BLOCK), PRICE_64, GOLD_INGOT, PRICE_ONE),
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, new ItemStack(Blocks.QUARTZ_BLOCK), 32),
                new VTradeItemToItem(new ItemStack(Blocks.QUARTZ_BLOCK), PRICE_64, GOLD_INGOT, PRICE_ONE)
        );

        VENDOR_STONE.addTrade(3,
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, new ItemStack(Blocks.TNT), new EntityVillager.PriceInfo(4,8)),
                new VTradeItemToItem(new ItemStack(Blocks.TNT), new EntityVillager.PriceInfo(16,32), GOLD_INGOT, PRICE_ONE)
        );

        CARD_0.addTrade(
                1,
                new VTradeTCG(EnumRarity.COMMON),
                new VTradeTCG(EnumRarity.RARE),
                new VTradeTCG(EnumRarity.EPIC),
                new VTradeTCG(CommonDef.RARITY_SSR)
        );

        CARD_1.addTrade(
                1,
                new VTradeTCG(EnumRarity.COMMON),
                new VTradeTCG(EnumRarity.COMMON),
                new VTradeTCG(EnumRarity.COMMON)
        );

        CARD_1.addTrade(
                2,
                new VTradeTCG(EnumRarity.COMMON),
                new VTradeTCG(EnumRarity.COMMON)
                );

        CARD_2.addTrade(
                1,
                new VTradeTCG(EnumRarity.EPIC),
                new VTradeTCG(EnumRarity.EPIC),
                new VTradeTCG(CommonDef.RARITY_SSR)
        );

        registry.registerAll(PROFESSION_LIST.toArray(new VillagerRegistry.VillagerProfession[0]));

    }

    static final EntityVillager.PriceInfo PRICE_36 = new EntityVillager.PriceInfo(36,36);

    static void registerRedstone(ModVCareer career, ItemStack[] stacks)
    {
        career.addTrade(1,
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_NUGGET, new EntityVillager.PriceInfo(1,2), new ItemStack(Items.REDSTONE)),
                new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, new EntityVillager.PriceInfo(4,4), new ItemStack(Blocks.REDSTONE_BLOCK), new EntityVillager.PriceInfo(4,4)));

        career.addTrade(2,
                new VTradeItemToItem(new ItemStack(Blocks.GOLD_BLOCK), PRICE_ONE, new ItemStack(Items.REDSTONE), new EntityVillager.PriceInfo(27,27)));

        registerMassive(career, stacks);
    }

    static void registerMassive(ModVCareer career, ItemStack[] stacks)
    {
        for (ItemStack stack : stacks) {
            career.addTrade(1, new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, stack, 4));
            career.addTrade(2, new VTradeItemToItem(new ItemStack(Blocks.GOLD_BLOCK), PRICE_ONE, stack, PRICE_36));
        }
    }

    static void registerMassive(ModVCareer career, ItemStack stack, int countPerGoldIngot)
    {
        if (countPerGoldIngot > 7)
        {
            Idealland.LogWarning("An villager trading is not satisfying: 1 ingot for %d %s, 1 block will cut down to 64", countPerGoldIngot, stack.getDisplayName());
        }
        career.addTrade(1, new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, stack, countPerGoldIngot));
        career.addTrade(2, new VTradeItemToItem(new ItemStack(Blocks.GOLD_BLOCK), PRICE_ONE, stack, new EntityVillager.PriceInfo(countPerGoldIngot * 9, countPerGoldIngot * 9)));
    }

    static void registerMassiveTiny(ModVCareer career, ItemStack stack, int countPerGoldNugget)
    {
        if (countPerGoldNugget > 7)
        {
            Idealland.LogWarning("An villager trading is not satisfying: 1 nugget for %d %s, 1 ingot will cut down to 64", countPerGoldNugget, stack.getDisplayName());
        }
        career.addTrade(1, new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_NUGGET, stack, countPerGoldNugget));
        career.addTrade(2, new VTradeItemToItem(VTradeItemToItem.CostType.GOLD_INGOT, PRICE_ONE, stack, new EntityVillager.PriceInfo(countPerGoldNugget * 9, countPerGoldNugget * 9)));
    }
}
