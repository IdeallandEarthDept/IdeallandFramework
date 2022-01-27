package com.somebody.idlframewok.item.fading;

import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.item.ItemArmorBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class ItemFadingArmor extends ItemArmorBase {
    public ItemFadingArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        setCreativeTab(ModCreativeTabsList.IDL_MISC);
    }
    // private static final int[] MAX_DAMAGE_ARRAY = new int[] {13, 15, 16, 11};

//    LEATHER("leather", 5, new int[]{1, 2, 3, 1}, 15,  SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F),
//    CHAIN("chainmail", 15, new int[]{1, 4, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F),
//    IRON("iron",       15, new int[]{2, 5, 6, 2}, 9,  SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F),
//    GOLD("gold",        7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F),
//    DIAMOND("diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        //Note that this wont fade in creative!
        if (!worldIn.isRemote && (worldIn.getWorldTime() % TICK_PER_SECOND == 0) &&  entityIn instanceof EntityLivingBase)
        {
            stack.damageItem(1, (EntityLivingBase) entityIn);
            //worldIn.spawnParticle(EnumParticleTypes.SPELL);
        }
    }

    //Desc
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        String duraDesc = I18n.format("fading.duration.desc", (stack.getMaxDamage() - stack.getItemDamage()));
        tooltip.add(duraDesc);
    }
}
