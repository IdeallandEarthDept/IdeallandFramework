package com.somebody.idlframewok.proxy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends ProxyBase {
    public static final List<KeyBinding> KEY_BINDINGS = new ArrayList<KeyBinding>();
	//public static final KeyBinding CAST_MAINHAND = new ModKeyBinding("activate_skill_mainhand", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_R, "key.category.idlframewok");
	//public static final KeyBinding CAST_OFFHAND = new ModKeyBinding("activate_skill_offhand", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_GRAVE, "key.category.idlframewok");

	public boolean isServer()
	{
		return false;
	}

	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
}
