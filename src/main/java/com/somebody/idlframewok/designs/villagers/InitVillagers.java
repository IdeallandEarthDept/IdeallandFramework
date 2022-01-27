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

//    public static final ModVProfession CARD =
//            new ModVProfession( "card");
//
//    public static final ModVCareer CARD_0 = new ModVCareer(CARD, "card_player");
//    public static final ModVCareer CARD_1 = new ModVCareer(CARD, "card_player_pauper");
//    public static final ModVCareer CARD_2 = new ModVCareer(CARD, "card_player_spike");
//
//    public static final ModVProfession SCRYER =
//            new ModVProfession( "scryer");
//
//    public static final ModVCareer SCRYER_C0 = new ModVCareer(SCRYER, "scryer_0");
//    public static final ModVCareer SCRYER_C1 = new ModVCareer(SCRYER, "scryer_1");

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
//        SCRYER_C0.addTrade(1,
//                new EntityVillager.ListItemForEmeralds(ModItems.GUA[0], PRICE_1_32_INV),
//                new EntityVillager.ListItemForEmeralds(ModItems.GUA[1], PRICE_1_32_INV),
//                new EntityVillager.ListItemForEmeralds(ModItems.GUA[2], PRICE_1_32_INV),
//                new EntityVillager.ListItemForEmeralds(ModItems.GUA[3], PRICE_1_32_INV),
//                new VTradeItemToItem(ModItems.YANG_SIGN, ModItems.YIN_SIGN));
//        SCRYER_C0.addTrade(2,
//                new EntityVillager.EmeraldForItems(ModItems.GUA[0], PRICE_32),
//                new EntityVillager.EmeraldForItems(ModItems.GUA[1], PRICE_32),
//                new EntityVillager.EmeraldForItems(ModItems.GUA[2], PRICE_32),
//                new EntityVillager.EmeraldForItems(ModItems.GUA[3], PRICE_32));
//
//        SCRYER_C1.addTrade(1,
//                new EntityVillager.ListItemForEmeralds(ModItems.GUA[4], PRICE_1_32_INV),
//                new EntityVillager.ListItemForEmeralds(ModItems.GUA[5], PRICE_1_32_INV),
//                new EntityVillager.ListItemForEmeralds(ModItems.GUA[6], PRICE_1_32_INV),
//                new EntityVillager.ListItemForEmeralds(ModItems.GUA[7], PRICE_1_32_INV),
//                new VTradeItemToItem(ModItems.YIN_SIGN, ModItems.YANG_SIGN));
//        SCRYER_C1.addTrade(2,
//                new EntityVillager.EmeraldForItems(ModItems.GUA[4], PRICE_32),
//                new EntityVillager.EmeraldForItems(ModItems.GUA[5], PRICE_32),
//                new EntityVillager.EmeraldForItems(ModItems.GUA[6], PRICE_32),
//                new EntityVillager.EmeraldForItems(ModItems.GUA[7], PRICE_32));

        registry.registerAll(PROFESSION_LIST.toArray(new VillagerRegistry.VillagerProfession[0]));

    }
}
