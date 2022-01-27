package com.deeplake.idlframewok.entity.creatures.ideallandTeam;

import com.deeplake.idlframewok.entity.creatures.EntityModUnit;
import com.deeplake.idlframewok.util.CommonFunctions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityDummy extends EntityModUnit {
    public EntityDummy(World worldIn) {
        super(worldIn);
        is_mechanic = true;
        CommonFunctions.addToEventBus(this);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(1.0D, 0D, 3.0D, 0.0D, 1024.0D);
    }

//    @Override
//    public boolean attackEntityFrom(DamageSource source, float amount) {
//        boolean result = super.attackEntityFrom(source, amount);
//        if (result) {
//            Entity attacker = source.getTrueSource();
//            if (attacker instanceof EntityPlayer)
//            {
//                String s = getUniqueID().toString();
//                CommonFunctions.SendMsgToPlayer((EntityPlayerMP) attacker, "idlframewok.msg.dummy_hurt", String.valueOf(amount), s.substring(s.length() - 5));
//            }
//        }
//        return result;
//    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHurt(LivingHurtEvent evt)
    {
        if (evt.isCanceled())
        {
            return;
        }

        if (!this.world.isRemote) {
            if (evt.getEntity() == this)
            {
                Entity attacker = evt.getSource().getTrueSource();
                if (attacker instanceof EntityPlayer)
                {
                    String s = getUniqueID().toString();
                    CommonFunctions.SendMsgToPlayer((EntityPlayerMP) attacker, "idlframewok.msg.dummy_hurt", String.valueOf(evt.getAmount()), s.substring(s.length() - 5));
                }
            }
        }
    }
}
