package com.somebody.idlframewok.proxy;

import com.somebody.idlframewok.keys.ModKeyBinding;
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
	 * ���������� String description, IKeyConflictContext context, KeyModifier modifier, int key, String category��
	 * - description �ǿ�ݼ����Ƶı��ػ�����
	 * - context �� Forge patch ��ȥ�ģ����ھ�����ݼ���ʲô����ºͱ�Ŀ�ݼ���ͻ��
	 *   Forge �ṩ������ʵ�֣��� KeyConflictContext ��� enum �µ�����ֵ��UNIVERSAL��
	 *   IN_GAME �� GUI���ֱ����ȫ�����á�����ֻ��ʵ����Ϸ�У���û�д� GUI ʱ���á��͡�ֻ�ڴ�
	 *   �� GUI ʱ���á������һ���������������ݼ��趨�İ�����һ���ģ�������һ�� context �� GUI��
	 *   ��һ���� IN_GAME����ô��������ݼ��Ͳ��ᱻ�����ǻ����ͻ�ġ�
	 * - modifier �����ṩ�ǳ���������ϼ�֧�֣�ֻ�����ֿ��ܣ�CTRL��ALT��SHIFT �� NONE��
	 *   NONE ����ԭ�����û����ϼ����������ֶ�������ϼ�֧�֡���֧�����ּ����ϵ���ϼ���
	 *   ���� CTRL��Forge ����ȷ���䴦��Ϊ macOS �ϵ� Command ����CTRL��ALT �� SHIFT ����
	 *   �����������֡�
	 *   ��������������ָ���� MY_HOTKEY ��Ĭ�ϼ�λ�� CTRL+K���� macOS ������ CMD+K��
	 * - key �ο� Keyboard ���µĳ����ֶ��ǡ�
	 * - category ԭ�水���趨�аѰ����Ƿֳ���������𣬾�������ˡ�
	 *   ������ַ���Ҳ�䵱�����ı��ػ�����
	 */
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
