package com.deeplake.idealland.init;

import com.deeplake.idealland.recipe.special.*;
import com.deeplake.idealland.util.Reference;
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
		//only need to add here
//		GameRegistry.addSmelting(ModItems.PURE_INGOT,
//				new ItemStack(ModItems.WEAPON_PEARL),
//				0.1f);
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
        r.register(new GobletChangeMode().setRegistryName(new ResourceLocation(Reference.MOD_ID, "goblet_change_mode")));
		r.register(new GobletDigBlockAssign().setRegistryName(new ResourceLocation(Reference.MOD_ID, "goblet_dig_block_assign")));
		r.register(new GuaEnhanceByCrafting().setRegistryName(new ResourceLocation(Reference.MOD_ID, "gua_enhance_by_crafting")));
		r.register(new SaiSwordAnchorSet().setRegistryName(new ResourceLocation(Reference.MOD_ID, "sai_sword")));

        //
//		//IForgeRegistry<IRecipe> r_eh = evt.getRegistry();
//		r.registerSpawnList(new EarthEnhance().setRegistryName(new ResourceLocation(Reference.MOD_ID, "sword_earth_enhance")));

	}
}
