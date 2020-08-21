package com.deeplake.idealland.item;

import com.deeplake.idealland.IdlFramework;
import com.deeplake.idealland.init.ModCreativeTab;
import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.IDLSkillNBT;
import com.deeplake.idealland.util.IHasModel;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTDef;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

import static com.deeplake.idealland.util.IDLSkillNBT.GetGuaEnhance;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.GUA_TOTAL_SOCKET_DESC;

public class ItemSwordBase extends ItemSword implements IHasModel {
	private boolean overrideRarity = false;
	private EnumRarity enumRarity = EnumRarity.COMMON;
	protected boolean showGuaSocketDesc = false;
	protected boolean shiftToShowDesc = false;

	//for accessing the private value
	protected Item.ToolMaterial toolMaterial;

	public ItemSwordBase(String name, Item.ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTab.IDL_MISC);
		toolMaterial = material;
		ModItems.ITEMS.add(this);

		InitItem();
	}

	public ItemSwordBase setRarity(EnumRarity enumRarity)
	{
		overrideRarity = true;
		this.enumRarity = enumRarity;
		return this;
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		if (overrideRarity)
		{
			return enumRarity;
		}else {
			return super.getRarity(stack);
		}
	}

	public void InitItem()
	{
		if (this instanceof IGuaEnhance)
		{
			showGuaSocketDesc = true;
		}
	}

	public String GetStringForThisByKey(String key)
	{
		return CommonFunctions.GetStringLocalTranslated(getUnlocalizedName() + key);
	}

	public String GetBasicDesc()
	{
		return CommonFunctions.GetStringLocalTranslated(getUnlocalizedName() + IDLNBTDef.DESC_COMMON);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
		//Particle;
		super.onUsingTick(stack, living, count);
		//IdlFramework.LogWarning(String.format("base onUsingTick %s",count));

		if (living.world.isRemote)
		{
			clientUseTick(stack, living, getMaxItemUseDuration(stack) - count);
		}
		else
		{
			serverUseTick(stack, living, getMaxItemUseDuration(stack) - count);
		}
	}

	public void clientUseTick(ItemStack stack, EntityLivingBase living, int count)
	{

	}

	public void serverUseTick(ItemStack stack, EntityLivingBase living, int count)
	{

	}

	@Override
	public void registerModels() 
	{
		IdlFramework.proxy.registerItemRenderer(this, 0, "inventory");
	}

	protected static boolean isShiftPressed()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {

		boolean shiftPressed = !shiftToShowDesc || isShiftPressed();
		if (shiftPressed)
		{
			String desc = getMainDesc(stack, world, tooltip, flag);
			if (!desc.isEmpty())
			{
				tooltip.add(desc);
			}

			if (showGuaSocketDesc)
			{
				tooltip.add(I18n.format(GUA_TOTAL_SOCKET_DESC, IDLSkillNBT.GetGuaEnhanceTotal(stack)));
				int guaTotal = IDLSkillNBT.GetGuaEnhanceTotal(stack);
				if (guaTotal > 0)
				{
					tooltip.add(I18n.format("idealland.gua_enhance_list.desc", GetGuaEnhance(stack, 0),
							GetGuaEnhance(stack, 1),
							GetGuaEnhance(stack, 2),
							GetGuaEnhance(stack, 3),
							GetGuaEnhance(stack, 4),
							GetGuaEnhance(stack, 5),
							GetGuaEnhance(stack, 6),
							GetGuaEnhance(stack, 7)));
				}

				int freeSockets = IDLSkillNBT.GetGuaEnhanceFree(stack);
				if (freeSockets > 0)
				{
					tooltip.add(TextFormatting.AQUA + I18n.format(IDLNBTDef.GUA_FREE_SOCKET_DESC, freeSockets));
				}
				else {
					tooltip.add(TextFormatting.ITALIC + (TextFormatting.WHITE + I18n.format(IDLNBTDef.GUA_NO_FREE_SOCKET_DESC)));
				}
			}
		}
		else {
			tooltip.add(TextFormatting.AQUA +  I18n.format("idealland.shared.press_shift"));
		}
	}

	@SideOnly(Side.CLIENT)
	public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		String key = stack.getUnlocalizedName() + ".desc";
		if (I18n.hasKey(key))
		{
			String mainDesc = I18n.format(key);
			return mainDesc;
		}
		return "";
	}

	//for accessing private values
	protected float getBaseAttackDamage()
	{
		return 3.0F + toolMaterial.getAttackDamage();
	}
}
