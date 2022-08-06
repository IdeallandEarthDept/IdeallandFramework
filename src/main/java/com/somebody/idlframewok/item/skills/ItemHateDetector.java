package com.somebody.idlframewok.item.skills;

import java.util.List;
import javax.annotation.Nullable;

import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.IDLGeneral;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHateDetector extends ItemSkillBase {

    private Class<? extends EntityLiving> targetCategory = EntityLiving.class;
    private String msgKey = "idlframewok.msg.being_targeted";
    private SoundEvent alarm = SoundEvents.BLOCK_NOTE_BELL;

    public ItemHateDetector(String name, Class<? extends EntityLiving> targetCategory, String msgKey, SoundEvent alarm) {
        this(name);
        this.targetCategory = targetCategory;
        this.msgKey = msgKey;
        this.alarm = alarm;
    }

    public ItemHateDetector(String name) {
        super(name);
        showCDDesc = false;
        showDamageDesc = false;
        showRangeDesc = true;
        this.addPropertyOverride(new ResourceLocation(IDLNBTDef.STATE), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)//when not in hand, world is null.
                {
                    return 1.0F;//set to 0 will cause no updating when not in hand. dunno why
                }
                else
                {
//                    if (worldIn == null)
//                    {
//                        worldIn = entityIn.world;
//                    }
//                    int state = GetInt(stack, STATE);
                    //IdlFramework.Log("State = " + state);
                    return (float) IDLNBTUtil.GetInt(stack, IDLNBTDef.STATE);
                }
            }
        });
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

            List<EntityLiving> entities = worldIn.getEntitiesWithinAABB(targetCategory,
                    IDLGeneral.ServerAABB(pos.addVector(-XZRangeRadius, -YRangeRadius, -XZRangeRadius), pos.addVector(XZRangeRadius, YRangeRadius, XZRangeRadius)));
            for (EntityLiving entity : entities)
            {
                //IdlFramework.Log(String.format("[Active]Nearby %s -> %s" , entity.getName() ,entity.getAttackTarget()));
                if (entity.getAttackTarget() == entityIn)
                {
                    detection++;
                    //IdlFramework.Log("[Active]Detected!");
                }
            }

            int detectionPre = IDLNBTUtil.GetInt(stack, IDLNBTDef.STATE);
            if (detectionPre != detection)//optimize
            {
                IDLNBTUtil.SetInt(stack, IDLNBTDef.STATE, detection);
                if (detectionPre == 0)
                {
                    CommonFunctions.SendMsgToPlayerStyled((EntityPlayerMP) entityIn, msgKey, TextFormatting.YELLOW);
                }
                //worldIn.playSound();
                //entityIn.playSound(alarm, 0.7f, detection * 0.1f);
            }
        }
    }
}
