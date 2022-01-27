package com.somebody.idlframewok.recipe;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.recipe.special.*;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModRecipes {

	public static void Init() {
		//Only smelting recipes

		ItemStack stackResult = new ItemStack(Items.GOLD_INGOT);
		stackResult.setCount(5);
		GameRegistry.addSmelting(ModItems.GOBLET_BLANK,
				stackResult,
				3f);

		stackResult = new ItemStack(ModItems.HELL_COIN);
		stackResult.setCount(1);
		GameRegistry.addSmelting(ModItems.HELL_COIN_BASE,
				stackResult,
				1f);

		stackResult = new ItemStack(ModItems.JADE_CRUDE);
		GameRegistry.addSmelting(ModBlocks.JADE_ORE,
				stackResult,
				1f);

		stackResult = new ItemStack(ModItems.CYCLE_STONE_SHARD);
		GameRegistry.addSmelting(ModBlocks.SKYLAND_PEBBLE,
				stackResult,
				1f);

		stackResult = new ItemStack(Items.COOKED_MUTTON);
		GameRegistry.addSmelting(ModBlocks.FLESH_BLOCK_0,
				stackResult,
				1f);

		GameRegistry.addSmelting(ModBlocks.IRON_PLANKS,
				new ItemStack(Items.IRON_INGOT, 2),
				1f);
//
//		GameRegistry.addSmelting(ModItems.WEAPON_PEARL,
//				new ItemStack(ModItems.PURE_INGOT),
//				0.1f);
//
//		GameRegistry.addSmelting(ModBlocks.DIVINE_ORE,
//				new ItemStack(ModItems.DIVINE_INGOT),
//				6f);
//
//		GameRegistry.addSmelting(ModBlocks.PURE_ORE,
//				new ItemStack(ModItems.PURE_INGOT),
//				3f);
		
	}
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		IForgeRegistry<IRecipe> r = evt.getRegistry();
		r.register(new BasicGua8().setRegistryName(new ResourceLocation(Reference.MOD_ID, "basic_gua_8")));
		r.register(new GobletFill().setRegistryName(new ResourceLocation(Reference.MOD_ID, "goblet_fill")));
		r.register(new SkillUpgrade().setRegistryName(new ResourceLocation(Reference.MOD_ID, "skill_upgrade")));
		r.register(new SkillUpgradeViaBadge().setRegistryName(new ResourceLocation(Reference.MOD_ID, "skill_upgrade_via_badge")));
        r.register(new GobletChangeMode().setRegistryName(new ResourceLocation(Reference.MOD_ID, "goblet_change_mode")));
		r.register(new GobletDigBlockAssign().setRegistryName(new ResourceLocation(Reference.MOD_ID, "goblet_dig_block_assign")));
		r.register(new GuaEnhanceByCrafting().setRegistryName(new ResourceLocation(Reference.MOD_ID, "gua_enhance_by_crafting")));
		r.register(new SaiSwordAnchorSet().setRegistryName(new ResourceLocation(Reference.MOD_ID, "sai_sword")));
		r.register(new TeleporterC16().setRegistryName(new ResourceLocation(Reference.MOD_ID, "teleport_c16_complicated")));
		r.register(new ClassifiedSkillUpgrade().setRegistryName(new ResourceLocation(Reference.MOD_ID, "classified_skill_upgrade")));
		r.register(new ObserverChangeClasses().setRegistryName(new ResourceLocation(Reference.MOD_ID, "ob_armor_change_class")));
		r.register(new LinearMerge().setRegistryName(new ResourceLocation(Reference.MOD_ID, "linear_merge")));

        //
//		//IForgeRegistry<IRecipe> r_eh = evt.getRegistry();
//		r.registerSpawnList(new EarthEnhance().setRegistryName(new ResourceLocation(Reference.MOD_ID, "sword_earth_enhance")));

	}
}
