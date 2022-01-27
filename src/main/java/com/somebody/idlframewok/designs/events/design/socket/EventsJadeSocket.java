package com.somebody.idlframewok.designs.events.design.socket;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.util.SocketUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.JADE_SOCKET;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsJadeSocket {
    //todo: player-set: 2- heal when open chests; 4-heal when lv up

    //todo: check loot table when EntityPlayerMP.connectionsMicro.sendPacket(new SPacketOpenWindow(this.currentWindowId, ((IInteractionObject)chestInventory).getGuiID(), chestInventory.getDisplayName(), chestInventory.getSizeInventory()));

    //sendPacket RightClickBlock getUseBlock

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        if (!evt.getEntityLiving().world.isRemote && !evt.isCanceled())
        {
            EntityLivingBase hurtOne = evt.getEntityLiving();
            int defSocketCount = getJadeEquipCount(hurtOne);

            if (evt.getSource().getTrueSource() instanceof EntityLivingBase)
            {
                EntityLivingBase attacker = (EntityLivingBase) evt.getSource().getTrueSource();
                int atkSocketCount = getJadeEquipCount(attacker);

                if (defSocketCount > 0)
                {
                    if (attacker.isEntityUndead())
                    {
                        //2 items set effect: damage from undead -1f
                        evt.setAmount(evt.getAmount() - defSocketCount * 0.75f);
                    }
                    else if (attacker instanceof IMob){
                        evt.setAmount(evt.getAmount() - 1f);
                    }
                }

                //4 items set effect: damage to undead + 50%
                if (atkSocketCount >= 4)
                {
                    if (hurtOne.isEntityUndead())
                    {
                        evt.setAmount(evt.getAmount() * 1.5f);
                    }
                }
            }
        }
    }

    public static int getJadeEquipCount(@Nullable EntityLivingBase target)
    {
        return SocketUtil.getAnyEquipCount(target, JADE_SOCKET);
    }
}
