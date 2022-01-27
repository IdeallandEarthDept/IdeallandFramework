package com.somebody.idlframewok.util.proxy;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.tesr.RenderCustomChest;
import com.somebody.idlframewok.blocks.tileEntity.TileEntityChestCustom;
import com.somebody.idlframewok.client.InitParticles;
import com.somebody.idlframewok.client.keys.ModKeyBinding;
import com.somebody.idlframewok.item.ItemVariantBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy extends ProxyBase {
    public static final List<KeyBinding> KEY_BINDINGS = new ArrayList<KeyBinding>();

	public static final KeyBinding CAST_MAINHAND = new ModKeyBinding("activate_skill_mainhand", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_R, "key.category.idlframewok");
	public static final KeyBinding CAST_OFFHAND = new ModKeyBinding("activate_skill_offhand", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_GRAVE, "key.category.idlframewok");
	public static final KeyBinding CAST_HELMET = new ModKeyBinding("activate_skill_helmet", KeyConflictContext.IN_GAME, KeyModifier.ALT, Keyboard.KEY_GRAVE, "key.category.idlframewok");

	public boolean isServer()
	{
		return false;
	}

	public void registerItemRenderer(Item item, int meta, String id)
	{
		if (item instanceof ItemVariantBase)
		{
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName()+"_"+meta, id));
		}
		else {
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
		}
	}

	public void registerItemRenderer(Item item, int meta, String fileName, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Idealland.MODID, fileName), id));
	}

	@Override
	public void registerTESR(RegistryEvent.Register<Block> event) {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestCustom.class, new RenderCustomChest());
	}

    @Override
    public void registerParticles() {
        InitParticles.registerParticles();
    }
}
