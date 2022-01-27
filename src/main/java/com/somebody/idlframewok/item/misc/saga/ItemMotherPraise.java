package com.somebody.idlframewok.item.misc.saga;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.somebody.idlframewok.util.CommonFunctions.sendBasicMsg;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.STATE;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ItemMotherPraise extends ItemBase {

    static final int MIN_USE = 3;
    static final int DISAPPEAR_FACTOR = 5;

    static final int REPAIR_MSG = 1;
    static final int DISAPPEAR_MSG = 2;

    public ItemMotherPraise(String name) {
        super(name);
        shiftToShowDesc = true;
        use_flavor = true;
        setMaxStackSize(1);
        setRarity(EnumRarity.RARE);
    }
    
    @SubscribeEvent
    public static void onAwake(PlayerWakeUpEvent evt) {

        EntityPlayer player = evt.getEntityPlayer();
        World world = player.getEntityWorld();
        if (!world.isRemote) {

            ItemStack stackSaga = PlayerUtil.FindStackInIvtrGeneralized(player, ItemMotherPraise.class);

            if (stackSaga.isEmpty())
            {
                //no saga found
                return;
            }

            boolean used = false;
            for (EntityEquipmentSlot slot:
                    EntityEquipmentSlot.values()) {

                if (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
                    continue;
                }

                ItemStack itemstack1 = player.getItemStackFromSlot(slot);
                if (!itemstack1.isEmpty() && itemstack1.isItemDamaged()) {
                    //Fix Dura
                    CommonFunctions.repairItem(itemstack1, itemstack1.getMaxDamage());
                    used = true;

                }
            }

            if (used)
            {
                int usedCount = IDLNBTUtil.GetInt(stackSaga, STATE);
                usedCount++;
                sendBasicMsg(stackSaga, player, REPAIR_MSG);
                if (usedCount >= MIN_USE)
                {
                    float randFactor = player.getRNG().nextFloat() * DISAPPEAR_FACTOR;
                    //todo: check whether worked
                    //Idealland.Log("Rand = %f", randFactor);
                    if (randFactor <= 1f && !PlayerUtil.isCreative(player))
                    {
                        sendBasicMsg(stackSaga, player, DISAPPEAR_MSG);
                        stackSaga.shrink(1);
                    }
                }
                //even if you are in creative, your parent still ages.
                IDLNBTUtil.setInt(stackSaga, STATE, usedCount);
            }
        }
    }
}
