package com.deeplake.idealland.item;

import com.deeplake.idealland.IdlFramework;
import com.deeplake.idealland.init.ModCreativeTab;
import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.IDLSkillNBT;
import com.deeplake.idealland.util.IHasModel;

import com.deeplake.idealland.util.NBTStrDef.IDLNBTDef;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

import static com.deeplake.idealland.util.IDLSkillNBT.GetGuaEnhance;

public class ItemBase extends Item implements IHasModel {
	private boolean overrideRarity = false;
	private EnumRarity enumRarity = EnumRarity.COMMON;
	protected boolean showGuaSocketDesc = false;
	protected boolean shiftToShowDesc = false;
	protected boolean useable = false;
	boolean isRangedWeapon = false;
	protected boolean logNBT = false;
	protected boolean glitters = false;

	protected static final UUID OFF_HAND_MODIFIER = UUID.fromString("9271eeea-5f74-4e12-97b6-7cf3c60ef7a0");
	protected static final UUID MAIN_HAND_MODIFIER = UUID.fromString("7d766720-0695-46c6-b320-44529f3da63f");

	protected static final UUID POWER_UP_MODIFIER = UUID.fromString("dc8a0a25-24c4-43a9-bfc3-e31e431f4ebf");
	protected static final UUID POWER_UP_MODIFIER_PERCENT = UUID.fromString("9236a0fe-8f9b-4ede-80a3-05386216d06f");

	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTab.IDL_MISC);
		
		ModItems.ITEMS.add(this);

		InitItem();
	}

	protected ItemBase setGlitter()
	{
		glitters = true;
		return this;
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return stack.isItemEnchanted() || glitters;
	}


	public ItemBase setRangedWeapon()
	{
		isRangedWeapon = true;
		return this;
	}

	public ItemBase setRarity(EnumRarity enumRarity)
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

	public boolean isRangedWeaponItem()
	{
		return isRangedWeapon;
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

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		if (useable)
		{
			player.setActiveHand(hand);
			ItemStack stack = player.getHeldItem(hand);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		else {
			return super.onItemRightClick(world, player, hand);
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

		IDLSkillNBT.addInformation(stack,world,tooltip,flag,shiftToShowDesc,isShiftPressed(),showGuaSocketDesc,
				getMainDesc(stack,world,tooltip,flag));

		if (logNBT)
		{
			tooltip.add(IDLNBTUtil.getNBT(stack).toString());
		}
//		boolean shiftPressed = !shiftToShowDesc || isShiftPressed();
//		if (shiftPressed)
//		{
//			String desc = getMainDesc(stack, world, tooltip, flag);
//			if (!desc.isEmpty())
//			{
//				tooltip.add(desc);
//			}
//
//			if (showGuaSocketDesc)
//			{
//			    int guaTotal = IDLSkillNBT.GetGuaEnhanceTotal(stack);
//				tooltip.add(I18n.format(GUA_TOTAL_SOCKET_DESC, IDLSkillNBT.GetGuaEnhanceTotal(stack)));
//				if (guaTotal > 0)
//                {
//                    tooltip.add(I18n.format("idealland.gua_enhance_list.desc", GetGuaEnhance(stack, 0),
//							GetGuaEnhanceString(stack, 1),
//							GetGuaEnhanceString(stack, 2),
//							GetGuaEnhanceString(stack, 3),
//							GetGuaEnhanceString(stack, 4),
//							GetGuaEnhanceString(stack, 5),
//							GetGuaEnhanceString(stack, 6),
//							GetGuaEnhanceString(stack, 7)));
//                }
//
//				int freeSockets = IDLSkillNBT.GetGuaEnhanceFree(stack);
//				if (freeSockets > 0)
//				{
//					tooltip.add(TextFormatting.AQUA + I18n.format(IDLNBTDef.GUA_FREE_SOCKET_DESC, freeSockets));
//				}
//				else {
//					tooltip.add(TextFormatting.ITALIC + (TextFormatting.WHITE + I18n.format(IDLNBTDef.GUA_NO_FREE_SOCKET_DESC)));
//				}
//			}
//		}
//		else {
//			tooltip.add(TextFormatting.AQUA +  I18n.format("idealland.shared.press_shift"));
//		}
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
}
