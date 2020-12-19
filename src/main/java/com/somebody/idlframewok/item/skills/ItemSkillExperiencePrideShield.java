package com.somebody.idlframewok.item.skills;

import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemSkillExperiencePrideShield extends ItemSkillBase {

    public ItemSkillExperiencePrideShield(String name) {
        super(name);
        setCD(0.1f, 0);
        maxLevel = 1;
        showCDDesc = false;
        //setVal(2f, -0.5f);//how many xp point 1 damage would cost;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCreatureDamaged(LivingDamageEvent evt) {
        if (evt.isCanceled())
        {
            return;
        }
        EntityLivingBase hurtOne = evt.getEntityLiving();
        //CrowFlight.Log(String.format("DMG:%s=%f",evt.getEntityLiving(), evt.getAmount()));
        if (hurtOne instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)hurtOne;
            ItemStack stack = AttemptPlayerHand(player, EnumHand.MAIN_HAND);
            if (stack == ItemStack.EMPTY)
            {
                stack = AttemptPlayerHand(player, EnumHand.OFF_HAND);
            }
            if (stack != ItemStack.EMPTY)
            {
                ItemSkillExperiencePrideShield skill = ((ItemSkillExperiencePrideShield)stack.getItem());

                float amount = evt.getAmount();
                if (!player.world.isRemote) {
                    if (player.experienceLevel >= amount)
                    {
                        player.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1f, 1f);
                        evt.setCanceled(true);
                    }
                    else {
                        evt.setAmount(amount * 2);
                        //player.experienceLevel -= 1;
                    }
                }
            }
        }
    }


    public static ItemStack AttemptPlayerHand(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() instanceof ItemSkillExperiencePrideShield && isStackReady(player, stack)){
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
