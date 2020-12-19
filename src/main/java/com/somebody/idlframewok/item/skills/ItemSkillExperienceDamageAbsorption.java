package com.somebody.idlframewok.item.skills;

import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemSkillExperienceDamageAbsorption extends ItemSkillBase {

    public ItemSkillExperienceDamageAbsorption(String name) {
        super(name);
        setCD(0.1f, 0);
        setVal(2f, -0.5f);//how many xp point 1 damage would cost;
        maxLevel = 4;
    }

//    @SubscribeEvent
//    public static void onCreatureDamaged(LivingDamageEvent evt) {
//        if (evt.isCanceled())
//        {
//            return;
//        }
//
//        World world = evt.getEntity().getEntityWorld();
//        EntityLivingBase hurtOne = evt.getEntityLiving();
//        //CrowFlight.Log(String.format("DMG:%s=%f",evt.getEntityLiving(), evt.getAmount()));
//        if (hurtOne instanceof EntityPlayer)
//        {
//            EntityPlayer player = (EntityPlayer)hurtOne;
//            ItemStack stack = AttemptPlayerHand(player, EnumHand.MAIN_HAND);
//            if (stack == ItemStack.EMPTY)
//            {
//                stack = AttemptPlayerHand(player, EnumHand.OFF_HAND);
//            }
//            if (stack != ItemStack.EMPTY)
//            {
//                ItemSkillExperienceDamageAbsorption skill = ((ItemSkillExperienceDamageAbsorption)stack.getItem());
//
//                float amount = evt.getAmount();
//                float multiplier = skill.getVal(stack);
//                int xpCost = (int) (amount * multiplier);
//
//                //if (!player.world.isRemote) {
//                    if (CommonFunctions.TryConsumePlayerXP(player, xpCost))
//                    {
//                        player.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1f, 1f);
//                        evt.setAmount(0);
//                    }
//                //}
//            }
//        }
//    }


    public static ItemStack AttemptPlayerHand(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() instanceof ItemSkillExperienceDamageAbsorption && isStackReady(player, stack)){
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

    //Desc
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        String mainDesc = I18n.format(stack.getUnlocalizedName() + ".desc", getVal(stack));
        tooltip.add(mainDesc);
        tooltip.add(I18n.format("idlframewok.skill.shared.cool_down_desc", getCoolDown(stack)));
    }
}
