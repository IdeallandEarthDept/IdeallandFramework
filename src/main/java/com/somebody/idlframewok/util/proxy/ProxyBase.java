package com.somebody.idlframewok.util.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ProxyBase {
	public boolean isServer()
	{
		return false;
	}

	public void registerItemRenderer(Item item, int meta, String id) {
		//Ignored
	}

	public void registerItemRenderer(Item item, int meta, String fileName, String id) {
		//Ignored
	}

	public void registerTESR(RegistryEvent.Register<Block> event) {

    }

    public void registerParticles() {

    }
}
