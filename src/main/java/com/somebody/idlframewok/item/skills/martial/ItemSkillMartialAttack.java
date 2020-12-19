package com.somebody.idlframewok.item.skills.martial;

import com.somebody.idlframewok.item.skills.ItemSkillBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;

import static com.somebody.idlframewok.util.IDLSkillNBT.getLevel;

public class ItemSkillMartialAttack extends ItemSkillBase {
    public float KBPowerBase = 0.1f;
    public float KBPowerPerLevel = 0.1f;

    public boolean interrupts = true;
    public boolean isSlam = false;

    public float getKBPower(ItemStack stack)
    {
        return  (getLevel(stack) - 1) * KBPowerPerLevel + KBPowerBase;
    }

    public ItemSkillMartialAttack setKB(float val, float val_per_level)
    {
        KBPowerBase = val;
        this.KBPowerPerLevel = val_per_level;
        return this;
    }

    public ItemSkillMartialAttack setIsSlam(boolean slam)
    {
        this.isSlam = slam;
        return this;
    }


    public ItemSkillMartialAttack(String name) {
        super(name);
        cool_down = 1f;
        basic_val = 1f;
        val_per_level = 1f;
        isMartial = true;
    }

    public void OnHit(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn)
    {
        OnHitBasic(stack, playerIn, target, handIn);
        OnHitSound(stack, playerIn, target, handIn);
        OnHitExtra(stack, playerIn, target, handIn);
    }

    public void OnHitBasic(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn)
    {
        if (!playerIn.world.isRemote) {
            target.attackEntityFrom(GetDamageSource(stack, playerIn, target, handIn), getVal(stack));
            target.knockBack(playerIn, getKBPower(stack), (playerIn.posX - target.posX), (playerIn.posZ - target.posZ));
            playerIn.swingArm(handIn);
            if (interrupts) {
                if (target instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) target;
                    ItemStack inHand = player.getHeldItemMainhand();
                    if (inHand.getItem() != null && inHand.getItem() instanceof ItemSkillBase)
                    {
                        ItemSkillBase.activateCoolDown(player, inHand);
                    }
                }
            }
        }
    }

    public void OnHitSound(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn)
    {
        target.playSound(SoundEvents.BLOCK_PISTON_EXTEND, 1f, 1f);
    }

    public void OnHitExtra(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn)
    {

    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {
        if (isStackReady(playerIn, stack))
        {
            OnHit(stack, playerIn, target, handIn);
            if (!playerIn.world.isRemote)
            {
                activateCoolDown(playerIn, stack);
            }
            return true;
        }
        return false;
    }

    public DamageSource GetDamageSource(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn)
    {
        if (isSlam)
        {
            return new EntityDamageSource("slam", playerIn);
        }
        return new EntityDamageSource("martial", playerIn);
    }
}
