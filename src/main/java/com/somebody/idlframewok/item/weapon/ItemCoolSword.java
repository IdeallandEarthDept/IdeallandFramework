package com.somebody.idlframewok.item.weapon;

import com.somebody.idlframewok.item.ItemSwordBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemCoolSword extends ItemSwordBase {
    public ItemCoolSword(String name, ToolMaterial material) {
        super(name, material);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        if (!worldIn.isRemote) {
//            ItemStack stack = new ItemStack(Items.SPLASH_POTION);
            ItemStack stack = new ItemStack(Items.LINGERING_POTION);
            PotionUtils.addPotionToItemStack(stack, PotionTypes.SLOWNESS);

            Vec3d speed = playerIn.getLookVec();
            EntityPotion potion = new EntityPotion(worldIn, playerIn, stack);
            potion.shoot(speed.x, speed.y, speed.z, 0.75F, 8.0F);
            worldIn.spawnEntity(potion);
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    //    @Override
//    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
//    {
//        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
//        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
//        {
//            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
//                    new AttributeModifier(ATTACK_DAMAGE_MODIFIER,
//                            "Weapon modifier",
//                            (double)(damage),
//                            0));
//
//            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
//                    new AttributeModifier(ATTACK_SPEED_MODIFIER,
//                            "Weapon modifier",
//                            -2.4000000953674316D,
//                            0));
//        }
//
//        return multimap;
//    }
}
