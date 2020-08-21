package com.deeplake.idealland.item;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.init.ModCreativeTab;
import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.IDLSkillNBT;
import com.deeplake.idealland.util.IHasModel;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTDef;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil;
import com.deeplake.idealland.util.Reference;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.BIOMETAL_WARNED;

//try to sync with ItemBase
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemArmorLivingMetalBase extends ItemArmorBase implements IHasModel {

	public ItemArmorLivingMetalBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
        ignoreVanillaSystem = false;
	}

	public int getRepairAmount(ItemStack stack, Entity entityIn)
	{
		return 1;
	}

	public double getHealthPlus(ItemStack stack)
	{
		return 1;
	}

	@Override
	public void onUpdateWearing(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdateWearing(stack, worldIn, entityIn, itemSlot, isSelected);
		EntityPlayer player = (EntityPlayer) entityIn;

		if (worldIn.getWorldTime() % TICK_PER_SECOND == 0)
		{
            boolean flagRule = player.world.getGameRules().getBoolean("naturalRegeneration");
            FoodStats stats = player.getFoodStats();
            if (flagRule && stats.getFoodLevel() >= 20)
            {
                // && stats.getSaturationLevel() > 0.0F &&
                if (player.getHealth() >= player.getMaxHealth())
                {
                    CommonFunctions.RepairItem(stack, getRepairAmount(stack, entityIn));
                }
            }
		}

		if (!worldIn.isRemote)
        {
            if (((float)stack.getItemDamage() / stack.getMaxDamage()) >= 0.9f)
            {
                if (worldIn.getWorldTime() % TICK_PER_SECOND == 0 && !IDLNBTUtil.GetBoolean(stack, BIOMETAL_WARNED, false)) {
                    IDLNBTUtil.SetBoolean(stack, BIOMETAL_WARNED, true);
                    CommonFunctions.SendMsgToPlayer((EntityPlayerMP) player, "idealland.msg.low_dura", stack.getDisplayName());
                }
            }
            else {
                IDLNBTUtil.SetBoolean(stack, BIOMETAL_WARNED, false);
            }
        }

	}

	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);

		if (equipmentSlot == this.armorType)
		{
			//multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Armor modifier", (double)this.damageReduceAmount, 0));
			//multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Armor toughness", (double)this.toughness, 0));
			multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()],"Health modifier", getHealthPlus(stack), 0));
			//multimap.put(SharedMonsterAttributes.LUCK.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Luck", (double)1f, 0));
		}

		return multimap;
	}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void OnWearerHeal(LivingHealEvent event)
    {
        if (event.isCanceled())
        {
            return;
        }

        EntityLivingBase livingBase = event.getEntityLiving();
        for (EntityEquipmentSlot slot:
                EntityEquipmentSlot.values()){
            ItemStack stack = livingBase.getItemStackFromSlot(slot);
            if (stack.getItem() instanceof ItemArmorLivingMetalBase)
            {
                CommonFunctions.RepairItem(stack,Math.round(event.getAmount()));
            }
        }
    }

//    @SideOnly(Side.CLIENT)
//    @Override
//    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
//        super.addInformation(stack, world, tooltip, flag);
//
//        //tooltip.add(I18n.format("idealland.gua_enhance_total.desc", IDLSkillNBT.GetGuaEnhanceTotal(stack)));
//        tooltip.add(IDLNBTUtil.getNBT(stack).toString());
//    }
}
