package com.somebody.idlframewok.item.skills;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.CommonFunctions.CopyNormalAttr;

public class ItemSkillSheepTransform extends ItemSkillBase {
    public ItemSkillSheepTransform(String name) {
        super(name);
        setCD(30f, 5f);
        showDamageDesc = false;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {
        if (isStackReady(playerIn, stack))
        {
            World world = playerIn.world;
            if (!world.isRemote)
            {
                if ((target instanceof EntityLiving))
                {
                    EntitySheep elk = new EntitySheep(world);
                    elk.setPosition(target.posX,target.posY,target.posZ);

                    CopyNormalAttr(target, elk);

                    target.setDead();

                    world.spawnEntity(elk);
                    world.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, target.posX, target.posY, target.posZ, 0,0,0);
                    world.playSound(null, target.getPosition(), SoundEvents.ENTITY_WITCH_AMBIENT, SoundCategory.PLAYERS,1f,1f);
                    //TryGrantAchv(playerIn, AchvDef.ELK_TRANSFORM);
                }
            }
            playerIn.swingArm(handIn);
            activateCoolDown(playerIn, stack);
            target.playSound(SoundEvents.ENTITY_SHEEP_AMBIENT, 1f, 1f);
            return true;
        }

        return false;
    }
}
