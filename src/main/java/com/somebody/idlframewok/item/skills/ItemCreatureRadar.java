package com.somebody.idlframewok.item.skills;

import com.somebody.idlframewok.IdlFramework;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.IDLGeneral;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.STATE;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil.*;

public class ItemCreatureRadar extends ItemSkillBase {

    private String msgKey = "idlframewok.msg.radar_nearbycreatures";
    private SoundEvent alarm = SoundEvents.BLOCK_NOTE_BELL;

//    public ItemCreatureRadar(String name, Class<? extends EntityLiving> targetCategory, String msgKey, SoundEvent alarm) {
//        this(name);
//        this.msgKey = msgKey;
//        this.alarm = alarm;
//    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {

        World world = playerIn.world;
        if (!world.isRemote) {
            ItemStack cloneResult = target.getPickedResult(null);
            if (!cloneResult.isEmpty())
            {
                ResourceLocation name = EntityList.getKey(target);
                if (name != null && EntityList.ENTITY_EGGS.containsKey(name))
                {
                    //SetString(stack, "creature_id", name.toString());
                    NBTTagCompound tagCompound = getNBT(stack);
                    tagCompound.setString("creature_id", name.toString());

                    //stack.writeToNBT(tagCompound);
                    //net.minecraft.item.ItemMonsterPlacer.applyEntityIdToItemStack(stack, name);
                    CommonFunctions.SafeSendMsgToPlayer(playerIn, getUnlocalizedName() + ".msg.success");
                    IdlFramework.LogWarning(getNBT(stack).toString());
                    activateCoolDown(playerIn, stack);
                }

                return true;
            }
        }

        return false;
    }

    public ItemCreatureRadar(String name) {
        super(name);
        showCDDesc = false;
        showDamageDesc = false;
        showRangeDesc = true;
//        this.addPropertyOverride(new ResourceLocation(STATE), new IItemPropertyGetter()
//        {
//            @SideOnly(Side.CLIENT)
//            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
//            {
//                if (entityIn == null)//when not in hand, world is null.
//                {
//                    return 1.0F;//set to 0 will cause no updating when not in hand. dunno why
//                }
//                else
//                {
////                    if (worldIn == null)
////                    {
////                        worldIn = entityIn.world;
////                    }
////                    int state = GetInt(stack, STATE);
//                    //IdlFramework.Log("State = " + state);
//                    return (float)GetInt(stack, STATE);
//                }
//            }
//        });
    }

    public Class getCreatureFromStack(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = getNBT(stack);
        //NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("EntityTag");


        if (nbttagcompound.hasKey("creature_id", 8))
        {
            net.minecraftforge.fml.common.registry.EntityEntry entry = net.minecraftforge.fml.common.registry.ForgeRegistries.ENTITIES.getValue(new ResourceLocation( nbttagcompound.getString("creature_id")));
            if (entry != null)
            {
                return entry.getEntityClass();
            }
        }

        return EntityPlayer.class;
//        ResourceLocation resourcelocation = new ResourceLocation(nbt.getString("layer"));
//        Entity entity = EntityList.createEntityByIDFromName(resourcelocation, worldIn);

    }


    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote)
        {
            int detection = 0;
            float XZRangeRadius = getRange(stack);
            float YRangeRadius = getRange(stack);

            Vec3d pos = entityIn.getPositionEyes(1.0F);

            IdlFramework.Log("update:",getNBT(stack).toString());

            Class s = getCreatureFromStack(stack);
            if (s == null)
            {
                s = EntityPlayer.class;
            }

            List<EntityLivingBase> entities = worldIn.getEntitiesWithinAABB(EntityLivingBase.class,
                    IDLGeneral.ServerAABB(pos.addVector(-XZRangeRadius, -YRangeRadius, -XZRangeRadius), pos.addVector(XZRangeRadius, YRangeRadius, XZRangeRadius)));
            for (EntityLivingBase entity : entities)
            {
                //IdlFramework.Log(String.format("[Active]Nearby %s -> %s" , entity.getName() ,entity.getAttackTarget()));
                if (entity.getClass() == s && entity != entityIn)
                {
                    detection++;
                    //IdlFramework.Log("[Active]Detected!");
                }
            }

            SetInt(stack, STATE, detection);
            int detectionPre = GetInt(stack, STATE);
            if (detectionPre != detection)//optimize
            {
                IdlFramework.LogWarning("Changed to " + detection);
                SetInt(stack, STATE, detection);
                CommonFunctions.SendMsgToPlayerStyled((EntityPlayerMP) entityIn, msgKey, TextFormatting.YELLOW, detection);
                //worldIn.playSound();
                //entityIn.playSound(alarm, 0.7f, detection * 0.1f);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        String mainDesc = I18n.format(stack.getUnlocalizedName() + ".extra", getCreatureFromStack(stack).getName() );
        tooltip.add(mainDesc);
    }

}
