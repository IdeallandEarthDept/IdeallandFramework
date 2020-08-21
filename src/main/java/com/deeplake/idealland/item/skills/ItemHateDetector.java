package com.deeplake.idealland.item.skills;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.item.ItemBase;
import com.deeplake.idealland.item.ModItems;
import com.deeplake.idealland.item.skills.ItemSkillBase;
import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.IDLGeneral;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.STATE;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil.GetInt;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil.SetInt;

public class ItemHateDetector extends ItemSkillBase {

    private Class<? extends EntityLiving> targetCategory = EntityLiving.class;
    private String msgKey = "idealland.msg.being_targeted";
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
        this.addPropertyOverride(new ResourceLocation(STATE), new IItemPropertyGetter()
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
                    //Idealland.Log("State = " + state);
                    return (float)GetInt(stack, STATE);
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
                //Idealland.Log(String.format("[Active]Nearby %s -> %s" , entity.getName() ,entity.getAttackTarget()));
                if (entity.getAttackTarget() == entityIn)
                {
                    detection++;
                    //Idealland.Log("[Active]Detected!");
                }
            }

            int detectionPre = GetInt(stack, STATE);
            if (detectionPre != detection)//optimize
            {
                SetInt(stack, STATE, detection);
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
