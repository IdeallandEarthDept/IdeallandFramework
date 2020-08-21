package com.deeplake.idealland.item;

import com.deeplake.idealland.IdlFramework;
import com.deeplake.idealland.init.ModCreativeTab;
import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.IDLSkillNBT;
import com.deeplake.idealland.util.IHasModel;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTDef;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import static com.deeplake.idealland.util.IDLSkillNBT.GetGuaEnhance;

//try to sync with ItemBase
public class ItemArmorBase extends ItemArmor implements IHasModel {
    private boolean overrideRarity = false;
    private EnumRarity enumRarity = EnumRarity.COMMON;
	protected boolean showGuaSocketDesc = false;
	protected boolean shiftToShowDesc = false;
	protected boolean ignoreVanillaSystem = false;

	protected static final UUID[] ARMOR_MODIFIERS_OVERRIDE = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
	private String texturePath;
	public ItemArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTab.IDL_MISC);

		ModItems.ITEMS.add(this);

		InitItem();
	}

	public void InitItem()
	{

	}

    public ItemArmorBase setRarity(EnumRarity enumRarity)
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

	public String GetStringForThisByKey(String key)
	{
		return CommonFunctions.GetStringLocalTranslated(getUnlocalizedName() + key);
	}

	public String GetBasicDesc()
	{
		return CommonFunctions.GetStringLocalTranslated(getUnlocalizedName() + IDLNBTDef.DESC_COMMON);
	}

//	@Override
//	public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
//		//Particle;
//		super.onUsingTick(stack, living, count);
//		//IdlFramework.LogWarning(String.format("base onUsingTick %s",count));
//
//		if (living.world.isRemote)
//		{
//			clientUseTick(stack, living, getMaxItemUseDuration(stack) - count);
//		}
//		else
//		{
//			serverUseTick(stack, living, getMaxItemUseDuration(stack) - count);
//		}
//	}
//
//	public void clientUseTick(ItemStack stack, EntityLivingBase living, int count)
//	{
//
//	}
//
//	public void serverUseTick(ItemStack stack, EntityLivingBase living, int count)
//	{
//
//	}

	/**
	 * Called by RenderBiped and RenderPlayer to determine the armor texture that
	 * should be use for the currently equipped item.
	 * This will only be called on instances of ItemArmor.
	 *
	 * Returning null from this function will use the default value.
	 *
	 * @param stack ItemStack for the equipped armor
	 * @param entity The entity wearing the armor
	 * @param slot The slot the armor is in
	 * @param type The subtype, can be null or "overlay"
	 * @return Path of texture to bind, or null to use default
	 */
	@Nullable
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		return null;
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
		super.addInformation(stack, world, tooltip, flag);
		IDLSkillNBT.addInformation(stack,world,tooltip,flag,shiftToShowDesc,isShiftPressed(),showGuaSocketDesc,
				getMainDesc(stack,world,tooltip,flag));
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

	//virtual
	public void onUpdateWearing(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

		EntityLivingBase living = (EntityLivingBase) entityIn;
		if (living.getItemStackFromSlot(armorType) == stack)
		{
			onUpdateWearing(stack, worldIn, entityIn, itemSlot, isSelected);
		}
	}

	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
	{
		if (ignoreVanillaSystem)
		{
			return HashMultimap.<String, AttributeModifier>create();
		}else {
			return super.getAttributeModifiers(equipmentSlot, stack);
		}
	}
}
