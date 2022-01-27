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

//		ItemStack stackResult = new ItemStack(Items.GOLD_INGOT);
//		stackResult.setCount(5);
//		GameRegistry.addSmelting(ModItems.GOBLET_BLANK,
//				stackResult,
//				3f);
		
	}
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		IForgeRegistry<IRecipe> r = evt.getRegistry();
//		r.register(new BasicGua8().setRegistryName(new ResourceLocation(Reference.MOD_ID, "basic_gua_8")));
//
	}
}
