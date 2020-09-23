package com.deeplake.idealland.proxy;

import com.deeplake.idealland.keys.ModKeyBinding;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy extends ProxyBase {
    public static final List<KeyBinding> KEY_BINDINGS = new ArrayList<KeyBinding>();
	/*
	 * 参数依次是 String description, IKeyConflictContext context, KeyModifier modifier, int key, String category。
	 * - description 是快捷键名称的本地化键。
	 * - context 是 Forge patch 进去的，用于决定快捷键在什么情况下和别的快捷键冲突。
	 *   Forge 提供了三个实现，即 KeyConflictContext 这个 enum 下的三个值：UNIVERSAL、
	 *   IN_GAME 和 GUI，分别代表“全局适用”，“只在实际游戏中，但没有打开 GUI 时适用”和“只在打
	 *   开 GUI 时适用”。如此一来，如果有两个快捷键设定的按键是一样的，但其中一个 context 是 GUI，
	 *   另一个是 IN_GAME，那么这两个快捷键就不会被看作是互相冲突的。
	 * - modifier 用于提供非常基础的组合键支持，只有四种可能：CTRL、ALT、SHIFT 和 NONE。
	 *   NONE 代表原版风格的没有组合键，其他三种都代表组合键支持。不支持三种及以上的组合键。
	 *   对于 CTRL，Forge 能正确将其处理为 macOS 上的 Command 键。CTRL、ALT 和 SHIFT 都不
	 *   对左右作区分。
	 *   举例：这里我们指定了 MY_HOTKEY 的默认键位是 CTRL+K，在 macOS 上则是 CMD+K。
	 * - key 参考 Keyboard 类下的常量字段们。
	 * - category 原版按键设定中把按键们分成了若干类别，就是这个了。
	 *   传入的字符串也充当该类别的本地化键。
	 */
	public static final KeyBinding CAST_MAINHAND = new ModKeyBinding("activate_skill_mainhand", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_R, "key.category.idealland");
	public static final KeyBinding CAST_OFFHAND = new ModKeyBinding("activate_skill_offhand", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_GRAVE, "key.category.idealland");

	public boolean isServer()
	{
		return false;
	}

	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
}
