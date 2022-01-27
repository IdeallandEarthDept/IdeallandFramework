package com.somebody.idlframewok.item.consumables;

import com.somebody.idlframewok.item.fading.ItemFadingArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import static com.somebody.idlframewok.item.skills.combat.ItemSkillFadeArmor.onGivePackage;

public class ItemFadePackageArmor extends ItemConsumableBase {
    private ItemFadingArmor[] armors;

    public ItemFadePackageArmor(String name, ItemFadingArmor[] armors) {
        super(name);
        this.armors = armors;
    }

    @Override
    public ActionResult<ItemStack> onConsume(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return onGivePackage(armors, playerIn, handIn);
    }
}
