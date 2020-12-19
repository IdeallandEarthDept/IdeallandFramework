package com.somebody.idlframewok.item;

import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.IHasModel;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.Reference;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.BIOMETAL_WARNED;

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
                    CommonFunctions.SendMsgToPlayer((EntityPlayerMP) player, "idlframewok.msg.low_dura", stack.getDisplayName());
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
//        //tooltip.add(I18n.format("idlframewok.gua_enhance_total.desc", IDLSkillNBT.GetGuaEnhanceTotal(stack)));
//        tooltip.add(IDLNBTUtil.getNBT(stack).toString());
//    }
}
