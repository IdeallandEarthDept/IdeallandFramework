package com.somebody.idlframewok.item.skills;

import com.somebody.idlframewok.util.IDLGeneral;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

import static net.minecraft.util.DamageSource.FALL;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemSkillThunderFall extends ItemSkillBase {
    private float KBPower = 1f;

    public ItemSkillThunderFall(String name) {
        super(name);
        maxLevel = 1;
        basic_val = 3f;
        showCDDesc = true;
    }

    @SubscribeEvent
    public static void onCreatureDamaged(LivingDamageEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();
        //CrowFlight.Log(String.format("DMG:%s=%f",evt.getEntityLiving(), evt.getAmount()));
        if (evt.getSource() == FALL && hurtOne instanceof EntityPlayer)
        {
            //IdlFramework.Log("player Falling");
            EntityPlayer player = (EntityPlayer)hurtOne;
            ItemStack stack = AttemptPlayerHand(player, EnumHand.MAIN_HAND);
            if (stack == ItemStack.EMPTY)
            {
                stack = AttemptPlayerHand(player, EnumHand.OFF_HAND);
            }
            if (stack != ItemStack.EMPTY)
            {
                ItemSkillThunderFall skillThunderFall = ((ItemSkillThunderFall)stack.getItem());

                float amount = evt.getAmount();
                float multiplier = skillThunderFall.getVal(stack);
                float range = skillThunderFall.getRange(stack);

                if (!player.world.isRemote) {
                    Vec3d mypos = player.getPositionEyes(0f);
                    player.playSound(SoundEvents.ENTITY_LIGHTNING_IMPACT, 1f, 1f);
                    //Damage nearby entities
                    List<EntityLivingBase> list = player.world.getEntitiesWithinAABB(EntityLivingBase.class, IDLGeneral.ServerAABB(mypos.addVector(-range, -range - hurtOne.getEyeHeight(), -range), mypos.addVector(range, range, range)));
                    for (EntityLivingBase creature : list) {
                        if (creature != hurtOne) {
                            if (creature.attackEntityFrom(DamageSource.ANVIL, multiplier * amount)) {
                                creature.knockBack(player, skillThunderFall.KBPower, (player.posX - creature.posX), (player.posZ - creature.posZ));
                            }
                        }
                    }
                }

                evt.setAmount(0);
            }
        }
    }

    public static ItemStack AttemptPlayerHand(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() instanceof ItemSkillThunderFall && isStackReady(player, stack)){
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
