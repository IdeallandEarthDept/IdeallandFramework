package com.somebody.idlframewok.item.skills;

import java.util.List;

import com.somebody.idlframewok.util.IDLGeneral;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemSkillFireBlast extends ItemSkillBase {
    public float KBPower = 0.01f;
    public ItemSkillFireBlast(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (isStackReady(playerIn, stack))//mostly always ready
        {
            //IdlFramework.Log("Trigg'd:" + worldIn.getWorldTime());
            if (!worldIn.isRemote)
            {
                ItemSkillFireBlast skillThunderFall = ((ItemSkillFireBlast)stack.getItem());

                Vec3d basePos = playerIn.getPositionVector();
                List<EntityLiving> entities = worldIn.getEntitiesWithinAABB(EntityLiving.class, IDLGeneral.ServerAABB(basePos.addVector(-base_range, -base_range, -base_range), basePos.addVector(base_range, base_range, base_range)));
                for (EntityLiving living: entities
                ) {
                    living.attackEntityFrom(DamageSource.causePlayerDamage(playerIn).setFireDamage().setMagicDamage(), getVal(stack));
                    //make sure it's not 0 0 ratio, or the target vanishes
                    living.knockBack(playerIn, skillThunderFall.KBPower, (playerIn.posX - living.posX), (playerIn.posZ - living.posZ));
                    living.setFire((int)getVal(stack));
                }
                playerIn.swingArm(handIn);
                activateCoolDown(playerIn, stack);
            }else {
                playerIn.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, playerIn.posX, playerIn.posY, playerIn.posZ, 1.0D, 0.0D, 0.0D);
                playerIn.playSound(SoundEvents.ENTITY_FIREWORK_LARGE_BLAST, 1f, 1f);
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
//        else {
//            //wont run here in cool down.
//            if (playerIn instanceof EntityPlayerMP)
//            {
//                notifyCoolingDown((EntityPlayerMP)playerIn);
//                IdlFramework.Log("notified");
//            }
//            else {
//                IdlFramework.Log("wrong type");
//            }
//            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
//        }
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }
}
