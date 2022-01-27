package com.somebody.idlframewok.item;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.item.artifact.IArtifact;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.IHasModel;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.DESC_COMMON;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ItemBase extends Item implements IHasModel {

	protected boolean overrideRarity = false;
	private EnumRarity enumRarity = EnumRarity.COMMON;
	protected boolean showGuaSocketDesc = false;
	protected boolean shiftToShowDesc = false;
	protected boolean use_flavor = false;
	protected boolean useable = false;
	private boolean isRangedWeapon = false;
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
		setCreativeTab(ModCreativeTabsList.IDL_MISC);
		
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
		return CommonFunctions.GetStringLocalTranslated(getUnlocalizedName() + DESC_COMMON);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
		//Particle;
		super.onUsingTick(stack, living, count);
		//Idealland.LogWarning(String.format("base onUsingTick %s",count));

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
			boolean result = onUseSimple(player, stack);
			if (result)
			{
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
			}
			else {
				return ActionResult.newResult(EnumActionResult.FAIL, stack);
			}
		}
		else {
			return super.onItemRightClick(world, player, hand);
		}
	}

	public boolean onUseSimple(EntityPlayer player, ItemStack stack)
	{
		return true;
	}

	public void clientUseTick(ItemStack stack, EntityLivingBase living, int count)
	{

	}

	public void serverUseTick(ItemStack stack, EntityLivingBase living, int count)
	{

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {

		IDLSkillNBT.addInformation(stack,world,tooltip,flag,shiftToShowDesc, showGuaSocketDesc, use_flavor,
				getMainDesc(stack,world,tooltip,flag));

		if (logNBT)
		{
			tooltip.add(IDLNBTUtil.getNBT(stack).toString());
		}
	}

	@SideOnly(Side.CLIENT)
	public String descGetKey(ItemStack stack, World world, boolean showFlavor)
	{
		return showFlavor ? (stack.getUnlocalizedName() + IDLNBTDef.FLAVOR_KEY)
				: (stack.getUnlocalizedName() + IDLNBTDef.DESC_COMMON);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return super.getItemStackDisplayName(stack);
	}

	@SideOnly(Side.CLIENT)
	public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		//Makesure you only call this in Remote!

		if (CommonFunctions.isShiftPressed() || !shiftToShowDesc)
		{
			String key = descGetKey(stack,world,false);
			if (I18n.hasKey(key))
			{
				return getFormatResult(key, stack, world, tooltip, flag);
			}
			else
			{
				return "";
			}
		}

		if (!CommonFunctions.isShiftPressed() && use_flavor)
		{
			String key = descGetKey(stack,world,true);
			if (I18n.hasKey(key))
			{
				return I18n.format(key);
			}
			else
			{
				return "";
			}
		}

		return "";
	}

	@SideOnly(Side.CLIENT)
	public String getFormatResult(String key, ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		return I18n.format(key);
	}

	public void onMouseFire(EntityPlayer player)
	{

	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	static void onToolTip(ItemTooltipEvent event)
	{
		ItemStack stack = event.getItemStack();
		if (stack.getItem() instanceof IArtifact)
		{
			event.getToolTip().add(1, TextFormatting.GOLD + I18n.format(IDLNBTDef.ARTIFACT_KEY));
		}
	}

    public static boolean isStackReady(EntityPlayer player, ItemStack stack) {
        return !player.getCooldownTracker().hasCooldown(stack.getItem());
        //return stack.getItemDamage() == 0;
    }

//	/**
//	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
//	 */
//	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
//	{
//		if (this.isInCreativeTab(tab))
//		{
//			items.add(new ItemStack(this));
//			items.add(new ItemStack(this, 1, 1));
//		}
//	}

//	/**
//	 * Retrieves the normal 'lifespan' of this item when it is dropped on the ground as a EntityItem.
//	 * This is in ticks, standard result is 6000, or 5 mins.
//	 *
//	 * @param itemStack The current ItemStack
//	 * @param world The world the entity is in
//	 * @return The normal lifespan in ticks.
//	 */
//	public int getEntityLifespan(ItemStack itemStack, World world)
//	{
//		return 6000;
//	}
//
//	/**
//	 * Determines if this Item has a special entity for when they are in the world.
//	 * Is called when a EntityItem is spawned in the world, if true and Item#createCustomEntity
//	 * returns non null, the EntityItem will be destroyed and the new Entity will be added to the world.
//	 *
//	 * @param stack The current item stack
//	 * @return True of the item has a custom entity, If true, Item#createCustomEntity will be called
//	 */
//	public boolean hasCustomEntity(ItemStack stack)
//	{
//		return false;
//	}
//
//	/**
//	 * This function should return a new entity to replace the dropped item.
//	 * Returning null here will not kill the EntityItem and will leave it to function normally.
//	 * Called when the item it placed in a world.
//	 *
//	 * @param world The world object
//	 * @param location The EntityItem object, useful for getting the position of the entity
//	 * @param itemstack The current item stack
//	 * @return A new Entity object to spawn or null
//	 */
//	@Nullable
//	public Entity createEntity(World world, Entity location, ItemStack itemstack)
//	{
//		return null;
//	}
//
//	/**
//	 * Called by the default implemetation of EntityItem's onUpdate method, allowing for cleaner
//	 * control over the update of the item without having to write a subclass.
//	 *
//	 * @param entityItem The entity Item
//	 * @return Return true to skip any further update code.
//	 */
//	public boolean onEntityItemUpdate(net.minecraft.entity.item.EntityItem entityItem)
//	{
//		return false;
//	}
}
