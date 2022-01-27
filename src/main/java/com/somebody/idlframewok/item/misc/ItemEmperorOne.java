package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.item.skills.ItemSkillBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

import static com.somebody.idlframewok.util.EntityUtil.UNDER_SKY;

public class ItemEmperorOne extends ItemSkillBase {
    static EntityLivingBase theEmperor = null;

    public static void forceSetEmperor(EntityLivingBase livingBase)
    {
        EntityLivingBase lastEmperor = theEmperor;
        if (!livingBase.isEntityAlive())
        {
            theEmperor = null;
            onEmperorChange(theEmperor, null);
        }
        else {
            theEmperor = livingBase;
            onEmperorChange(theEmperor, livingBase);
        }
    }

    public static boolean hasValidEmperor()
    {
        return theEmperor != null && theEmperor.isEntityAlive();
    }

    static void onEmperorChange(EntityLivingBase old, EntityLivingBase newOne)
    {
        if (old == newOne)
        {
            return;
        }
        //send msg to players
        if (newOne instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) newOne;
            player.capabilities.allowFlying = true;
        }

        if (old instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) old;
            player.capabilities.allowFlying = player.capabilities.isCreativeMode;
        }
    }

    public ItemEmperorOne(String name) {
        super(name);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if (hasValidEmperor() && theEmperor != entityIn)
        {
            //gain emperor failed
            return;
        } else if (!hasValidEmperor())
        {
            if (entityIn instanceof EntityLivingBase)
            {
                forceSetEmperor((EntityLivingBase) entityIn);
            }
        }

        if (!CommonFunctions.isSecondTick(worldIn))
        {
            return;
        }

        List<EntityLivingBase> aiList = worldIn.getEntities(EntityLiving.class, UNDER_SKY);
        List<EntityLivingBase> playerList = worldIn.getPlayers(EntityPlayer.class, UNDER_SKY);
        List<EntityItem> itemList = worldIn.getEntities(EntityItem.class, UNDER_SKY);

        for (EntityLivingBase living:
             aiList) {
            EntityUtil.ApplyBuff(living, MobEffects.GLOWING, 0, 3f);
        }

        for (EntityLivingBase living:
                playerList) {
            EntityUtil.ApplyBuff(living, MobEffects.GLOWING, 0, 3f);
        }

        for (EntityItem living:
                itemList) {
             Item item = living.getItem().getItem();
             if (item instanceof ItemBook)
             {
                 //burn!
             }
        }
    }

    @SubscribeEvent
    public static void onSetAttackTarget(LivingSetAttackTargetEvent event)
    {
        EntityLivingBase livingBase = event.getEntityLiving();
        World world = livingBase.world;

        if (world.isRemote || event.getTarget() == null)
        {
            return;
        }

        EntityLivingBase target = event.getTarget();
        if (target == null)
        {
            return;
        }

        if (UNDER_SKY.apply(livingBase) && target == theEmperor)
        {
            event.getEntityLiving().setFire(1);
        }
    }

    void createParticlesMain(EntityLivingBase center)
    {
        //double helix
        World world = center.world;

    }

}
