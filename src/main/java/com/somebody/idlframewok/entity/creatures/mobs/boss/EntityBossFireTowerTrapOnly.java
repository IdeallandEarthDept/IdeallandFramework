package com.somebody.idlframewok.entity.creatures.mobs.boss;

import com.somebody.idlframewok.designs.combat.ModDamageSourceList;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.CommonDef;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityBossFireTowerTrapOnly extends EntityBossBase {
    public EntityBossFireTowerTrapOnly(World worldIn) {
        super(worldIn);
        useBulletForRanged = false;
    }

    final int switchPeriod = CommonDef.TICK_PER_SECOND * 5;

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        setAttr(32, 0.3, 6.0, 0, 128);
        //2 hit kill. better set respawn early.
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if (world.getTotalWorldTime() % switchPeriod == 0) {
            if (rand.nextBoolean() || world.getDifficulty() != EnumDifficulty.HARD) {
                meleeMode();
            } else {
                rangedMode();//ranged is too powerful in such a small arena
            }
        }
    }

    public void meleeMode() {
        ItemStack stack = new ItemStack(ModItems.ITEM_WEARING_SWORD_FIRE);
        stack.addEnchantment(Enchantments.FIRE_ASPECT, Enchantments.FIRE_ASPECT.getMaxLevel());
        setHeldItem(EnumHand.MAIN_HAND, stack);

        stack = new ItemStack(Items.SHIELD);
        NBTTagCompound nbt = ItemBanner.makeBanner(EnumDyeColor.byDyeDamage(Color16Def.FIRE), null).getTagCompound();
        stack.setTagCompound(nbt);
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, stack);
    }

    public void rangedMode() {
        ItemStack stack = new ItemStack(Items.BOW);
        stack.addEnchantment(Enchantments.FLAME, Enchantments.FLAME.getMaxLevel());
        setHeldItem(EnumHand.MAIN_HAND, stack);

        stack = new ItemStack(Items.TIPPED_ARROW);
        PotionUtils.addPotionToItemStack(stack, ModPotions.BURN.getPotionType());
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, stack);
    }

    //only accept trap damage
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source != ModDamageSourceList.TRAP && source != ModDamageSourceList.TRAP_ABSOLUTE) {
            return false;
        }

        return super.attackEntityFrom(source, amount);
    }


}
