package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.MetaUtil;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.NBTStrDef.TalkStrDef;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.NBTStrDef.TalkStrDef.MOD_INCRE;

public class ItemAITerminal extends ItemBase {
    static final String RAINING = "r1";
    static final String SNOWING = "s1";
    static final String LAST_USE = "last_use";

    static final String MOD_COUNT = "mod_c";

    static final String SELF_UUID = "this_uuid";
    static final String SELF_NAME = "this_uname";

    public ItemAITerminal(String name) {
        super(name);
        //your Cortana.
        useable = true;
        CommonFunctions.addToEventBus(this);
    }

    @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EntityEquipmentSlot.HEAD;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        //todo: talk hints. like ores are generated on chunks that have not been generated yet.
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (worldIn.isRemote)
        {
            return;
        }

        if (!IDLNBTUtil.GetBoolean(stack, IDLNBTDef.INIT_DONE))
        {
            return;
        }

        if (worldIn.getTotalWorldTime() % TICK_PER_SECOND == 0)
        {
            checkRain(stack,worldIn, (EntityPlayer) entityIn,itemSlot,isSelected);
        }
    }

    public void checkRain(ItemStack stack, World worldIn, EntityPlayer player, int itemSlot, boolean isSelected) {
        Biome biome = EntityUtil.getBiomeForEntity(player);
        boolean biomeCanRain = biome.canRain();
        boolean biomeSnowy = biome.isSnowyBiome();
        boolean worldRaining = worldIn.isRaining();

        boolean raining = worldRaining && biomeCanRain;
        boolean snowing = worldRaining && biomeSnowy;
        boolean lastRain = IDLNBTUtil.GetInt(stack, RAINING) > 0;
        boolean lastSnow = IDLNBTUtil.GetInt(stack, SNOWING) > 0;

        if (raining != lastRain)
        {
            IDLNBTUtil.setInt(stack, RAINING, raining ? 1 : 0);
        }

        if (snowing != lastSnow)
        {
            IDLNBTUtil.setInt(stack, SNOWING, snowing ? 1 : 0);
        }

        if (raining)
        {
            if (lastSnow)
            {
                talkTo(stack, player, TalkStrDef.SNOW_TO_RAIN, 0.5f);
            }else if (!lastRain)
            {
                talkTo(stack, player, TalkStrDef.RAIN_BEGIN, 0.5f);
            }
        }
        else {
            if (lastRain)
            {
                if (worldRaining)
                {
                    talkTo(stack, player, TalkStrDef.RAIN_STOP_BY_LOCA, 0.5f);
                }
                else {
                    talkTo(stack, player, TalkStrDef.RAIN_STOP_BY_TIME, 0.5f);
                }
            }
        }

        if (snowing)
        {
            if (lastRain)
            {
                talkTo(stack, player, TalkStrDef.RAIN_TO_SNOW, 0.5f);
            }else if (!lastSnow)
            {
                talkTo(stack, player, TalkStrDef.SNOW_BEGIN, 0.5f);
            }
        }
        else {
            if (lastSnow)
            {
                if (worldRaining)
                {
                    //talkTo(stack, player, TalkStrDef.SNOW_STOP, 0.5f);
                }
                else {
                    talkTo(stack, player, TalkStrDef.SNOW_STOP_BY_TIME, 0.5f);
                }

            }
        }
    }

    //target

    @SubscribeEvent
    void onLogIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        World world = event.player.world;
        if (!world.isRemote)
        {
            EntityPlayer player = event.player;
            int curMod = MetaUtil.GetModCount();

            ItemStack stack = PlayerUtil.FindStackInIvtr(player, this);
            if (stack.isEmpty())
            {
                return;
            }

            int lastMod = IDLNBTUtil.GetInt(stack, MOD_COUNT);

            if (curMod != lastMod)
            {
                IDLNBTUtil.setInt(stack, MOD_COUNT, curMod);
            }

            if (curMod > lastMod)
            {
                talkTo(stack, player, MOD_INCRE);
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        if (world.isRemote)
        {
            return super.onItemRightClick(world, player, hand);
        }

        ItemStack stack = player.getHeldItem(hand);
        int last_use = IDLNBTUtil.GetInt(stack, LAST_USE);
        IDLNBTUtil.SetLong(stack, LAST_USE, world.getTotalWorldTime());

        if (!IDLNBTUtil.GetBoolean(stack, IDLNBTDef.INIT_DONE))
        {
            String playerName = player.getDisplayNameString();
            String playerUUID = player.getUniqueID().toString();
            String selfUUID = UUID.randomUUID().toString();
            String selfName = selfUUID.substring(playerUUID.length() - 5);

            IDLNBTUtil.SetString(stack, IDLNBTDef.OWNER_NAME, playerName);
            IDLNBTUtil.SetString(stack, IDLNBTDef.OWNER_UUID, playerUUID);
            IDLNBTUtil.SetString(stack, SELF_UUID, selfUUID);
            IDLNBTUtil.SetString(stack, SELF_NAME, selfName);
            IDLNBTUtil.SetBoolean(stack, IDLNBTDef.INIT_DONE, true);

            IDLNBTUtil.setInt(stack, MOD_COUNT, MetaUtil.GetModCount());

            talkTo(player, TalkStrDef.INIT,
                    playerName,
                    playerUUID.substring(playerUUID.length() - 5),
                    selfUUID,
                    selfName);
        }else {
            talkTo(stack, player, String.format("%s.%d", TalkStrDef.ASK, player.getRNG().nextInt(4))  );
        }

        return super.onItemRightClick(world, player, hand);
    }

    public void talkTo(ItemStack stack, EntityPlayer player, String talkID, String... args)
    {
        if (!player.world.isRemote)
        {
            CommonFunctions.SafeSendMsgToPlayer(player, talkID, IDLNBTUtil.GetString(stack, SELF_NAME, ""), args);
        }
    }

    //for optimization
    public void talkTo(EntityPlayer player, String talkID, String... args)
    {
        if (!player.world.isRemote)
        {
            CommonFunctions.SafeSendMsgToPlayer(player, talkID, args);
        }
    }

    public void talkTo(ItemStack stack, EntityPlayer player, String talkID, float chance)
    {
        if (player.getRNG().nextFloat() < chance)
        {
            talkTo(stack, player, talkID);
        }
        else {
            Idealland.Log("talk failed:%s",talkID);
        }
    }

}
