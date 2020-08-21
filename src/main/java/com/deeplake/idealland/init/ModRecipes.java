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
//		GameRegistry.addSmelting(ModItems.PURE_INGOT,
//				new ItemStack(ModItems.WEAPON_PEARL),
//				0.1f);
		
	}
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		IForgeRegistry<IRecipe> r = evt.getRegistry();
		//Example
		//r.register(new GobletFill().setRegistryName(new ResourceLocation(Reference.MOD_ID, "goblet_fill")));
	}
}
