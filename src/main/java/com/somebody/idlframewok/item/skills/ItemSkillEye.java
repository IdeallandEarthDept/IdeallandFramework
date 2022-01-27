package com.somebody.idlframewok.item.skills;

import com.somebody.idlframewok.item.IWIP;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.MessageDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.STATE;

public abstract class ItemSkillEye extends ItemSkillBase implements IWIP {
    public ItemSkillEye(String name) {
        super(name);
        setDura(20, 10f);
        setVal(0, 2f);
        setRange(32, 8f);
        setCD(50, 5f);
        cannotMouseCast = true;
        mainHandCast = false;
        offHandCast = false;

        showCDDesc = true;
        showDamageDesc = true;
        showRangeDesc = true;

        useXP_level = true;
    }

    public float getCoolDown(ItemStack stack) {
        float result = -(IDLNBTUtil.GetInt(stack, IDLNBTDef.GAZE_CD)) * cool_down_reduce_per_lv + cool_down;
        return result > 0.1f ? result : 0.1f;
    }
    public class SkillEyeArgs
    {
        public int damagePlus = 0;
        public int rangePlus = 0;
        public int timePlus = 0;
        public int buffPlus = 0;
        public int level = 1;

        public SkillEyeArgs(ItemStack stack)
        {
            damagePlus = IDLNBTUtil.GetInt(stack, IDLNBTDef.GAZE_DAMAGE);
            rangePlus = IDLNBTUtil.GetInt(stack, IDLNBTDef.GAZE_RANGE);
            timePlus = IDLNBTUtil.GetInt(stack, IDLNBTDef.GAZE_TIME);
            buffPlus = IDLNBTUtil.GetInt(stack, IDLNBTDef.GAZE_BUFF);
            level = IDLSkillNBT.getLevel(stack);
        }
    }

    public DamageSource getDamageType(EntityLivingBase source, EntityLivingBase target, SkillEyeArgs args, float modifier)
    {
        return EntityUtil.attack(source).setMagicDamage();
    }

    @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EntityEquipmentSlot.HEAD;
    }

    @Override
    public boolean canCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot, boolean showMsg) {
        return livingBase.getActivePotionEffect(MobEffects.BLINDNESS) == null && super.canCast(worldIn, livingBase, stack, slot, showMsg);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        cast(livingBase, new SkillEyeArgs(stack), livingBase.isSneaking());
        EntityUtil.ApplyBuff(livingBase, MobEffects.NIGHT_VISION, 0, 0.5f);
        return super.applyCast(worldIn, livingBase, stack, slot);
    }

    static final float NORMAL = 1.0f;
    static final float SEEN = 1.5f;

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (entityIn instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityIn;
            int state = IDLNBTUtil.GetInt(stack, STATE);
            int now = player.getCooldownTracker().hasCooldown(this) ? 1 : 0;
            if (state != now)
            {
                if (now == 0)
                {
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GREEN, player, MessageDef.MAGICAL_EYE_READY);
                    player.playSound(SoundEvents.BLOCK_NOTE_BELL, 1.8f, 1.7f);
                }
            }
        }
    }

    public float getRange(ItemStack stack)
    {
        SkillEyeArgs args = new SkillEyeArgs(stack);
        return getDura(args.rangePlus + 1);
    }

    public void cast(EntityLivingBase source, SkillEyeArgs args, boolean focused)
    {
        World world =source.world;

        float range = getRange(args.rangePlus + 1);
        List<EntityLivingBase> livingBases = EntityUtil.getEntitiesWithinAABB(world, EntityLivingBase.class, source.getPositionVector(), range, EntityUtil.NON_HALF_CREATURE);
        //Idealland.Log("Caught %d pre targets. focused = %s", livingBases.size(), focused);

        CommonFunctions.SafeSendMsgToPlayer(source, focused ? MessageDef.MAGICAL_EYE_CAST_BROAD : MessageDef.MAGICAL_EYE_CAST_NARROW);
        source.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.4f, 0.4f);

        if (focused)
        {
//            RayTraceResult raytraceresult = forwardsRaycast(world, source.getPositionEyes(1f), source.getLookVec(), range,true, source);
//            if (raytraceresult != null && raytraceresult.entityHit instanceof EntityLivingBase)
//            {
//                apply(source, (EntityLivingBase) raytraceresult.entityHit, args, (EntityUtil.canSee(source, raytraceresult.entityHit) ? SEEN : NORMAL));
//                return;
//            }
            for (EntityLivingBase target:
                    livingBases) {
                //Idealland.Log("Applying to %s, narrow can see = %s", target.getName(), EntityUtil.isSeeing(source, target));
                if (target != source && EntityUtil.isSeeing(source, target))
                {
                    apply(source, target, args,  (EntityUtil.isSeeing(source, target) ? SEEN : NORMAL));
                }
            }
        }
        else {
            for (EntityLivingBase target:
                    livingBases) {
                //Idealland.Log("Applying to %s, can see = %s", target.getName(), EntityUtil.canSee(source, target));
                if (target != source && EntityUtil.canSee(source, target))
                {
                    apply(source, target, args, 0.5f * (1 - target.getDistance(source) / range * 0.8f) * (EntityUtil.isSeeing(source, target) ? SEEN : NORMAL));
                }
            }
        }


    }

    void apply(EntityLivingBase source, EntityLivingBase target, SkillEyeArgs args, float modifier)
    {
        CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, target, MessageDef.MAGICAL_EYE_GAZED);
        applyingEffects(source, target, args, modifier);
    }

    public abstract void applyingEffects(EntityLivingBase source, EntityLivingBase target, SkillEyeArgs args, float modifier);

    public static RayTraceResult forwardsRaycast(World world, Vec3d from, Vec3d dir, float range, boolean includeEntities, Entity excludedEntity)
    {
        double d0 = from.x;
        double d1 = from.y;
        double d2 = from.z;
        double d3 = dir.x;
        double d4 = dir.y;
        double d5 = dir.z;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        Vec3d vec3d1 = new Vec3d(d0 + d3, d1 + d4, d2 + d5);
        RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec3d1, false, true, false);

        if (includeEntities)
        {
            if (raytraceresult != null)
            {
                vec3d1 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
            }

            Entity entity = null;
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(excludedEntity, excludedEntity.getEntityBoundingBox().expand(d3*range, d4*range, d5*range).grow(1.0D));
            double d6 = 0.0D;

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity1 = list.get(i);

                if (entity1.canBeCollidedWith() && !entity1.noClip)
                {
                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
                    RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);

                    if (raytraceresult1 != null)
                    {
                        double d7 = vec3d.squareDistanceTo(raytraceresult1.hitVec);

                        if (d7 < d6 || d6 == 0.0D)
                        {
                            entity = entity1;
                            d6 = d7;
                        }
                    }
                }
            }

            if (entity != null)
            {
                raytraceresult = new RayTraceResult(entity);
            }
        }

        return raytraceresult;
    }
}
