package com.somebody.idlframewok.item.skills;

import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraft.util.DamageSource.FALL;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemSkillBlessedArmor extends ItemSkillBase {
    private float KBPower = 1f;

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return true;
    }

    public ItemSkillBlessedArmor(String name) {
        super(name);
        basic_val = 3f;
        showDamageDesc = false;
    }

    @SubscribeEvent
    public static void onCreatureDamaged(LivingDamageEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();
        if (evt.getSource() == FALL && hurtOne instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)hurtOne;
            ItemStack stack = AttemptPlayerHand(player, EnumHand.MAIN_HAND);
            if (stack == ItemStack.EMPTY)
            {
                stack = AttemptPlayerHand(player, EnumHand.OFF_HAND);
            }
            if (stack != ItemStack.EMPTY)
            {
                ItemSkillBlessedArmor skillThunderFall = ((ItemSkillBlessedArmor)stack.getItem());

                evt.setAmount(0);
                world.playSound(player, player.getPosition(), SoundEvents.BLOCK_NOTE_HARP, SoundCategory.PLAYERS, 1f, 3f);
            }
        }
    }

    public static ItemStack AttemptPlayerHand(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() instanceof ItemSkillBlessedArmor && isStackReady(player, stack)){
            activateCoolDown(player, stack);
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
//        ItemStack stack = playerIn.getHeldItem(handIn);
//        if (isStackReady(stack ))
//        {
//            Vec3d basePos = playerIn.getPositionVector();
//            List<EntityLiving> entities = worldIn.getEntitiesWithinAABB(EntityLiving.class, IDLGeneral.ServerAABB(basePos.addVector(-base_range, -base_range, -base_range), basePos.addVector(base_range, base_range, base_range)));
//            for (EntityLiving living: entities
//                 ) {
//                living.setAttackTarget(playerIn);
//            }
//            playerIn.swingArm(handIn);
//            activateCoolDown(stack);
//            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
//        }
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
