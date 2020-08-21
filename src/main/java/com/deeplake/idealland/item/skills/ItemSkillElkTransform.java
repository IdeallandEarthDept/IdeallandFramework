package com.deeplake.idealland.item.skills;

import com.deeplake.idealland.entity.creatures.misc.Entity33Elk;
import com.deeplake.idealland.util.AchvDef;
import com.deeplake.idealland.util.IDLGeneral;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static com.deeplake.idealland.util.CommonFunctions.TryGrantAchv;

public class ItemSkillElkTransform extends ItemSkillBase {
    public ItemSkillElkTransform(String name) {
        super(name);
        cool_down = 50f;
        base_range = 10f;
        range_per_level = 10f;
        setCD(50f, 5f);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {
        if (isStackReady(playerIn, stack))
        {
            World world = playerIn.world;
            if (!world.isRemote)
            {
                //oko cant transform players(planeswalkers)
                if (!(target instanceof EntityPlayer))
                {
                    //+1:
                    playerIn.addExperience(1);

                    //Target artifact or creature loses all abilities and becomes a green Elk creature with base power and toughness 3/3.
                    Entity33Elk elk = new Entity33Elk(world);
                    elk.setPosition(target.posX,target.posY,target.posZ);

                    target.setDead();

                    world.spawnEntity(elk);
                    world.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, target.posX, target.posY, target.posZ, 0,0,0);
                    world.playSound(null, target.getPosition(), SoundEvents.ENTITY_WITCH_AMBIENT, SoundCategory.PLAYERS,1f,1f);
                    TryGrantAchv(playerIn, AchvDef.ELK_TRANSFORM);


                }
            }
            playerIn.swingArm(handIn);
            activateCoolDown(playerIn, stack);
            target.playSound(SoundEvents.ENTITY_POLAR_BEAR_WARNING, 1f, 1f);
            return true;
        }

        return false;
    }
}
