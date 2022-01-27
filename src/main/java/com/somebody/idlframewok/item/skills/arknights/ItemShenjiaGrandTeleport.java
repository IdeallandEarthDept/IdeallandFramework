package com.somebody.idlframewok.item.skills.arknights;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.skills.ItemSkillBase;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static com.somebody.idlframewok.util.EntityUtil.createTeleportEffect;

public class ItemShenjiaGrandTeleport extends ItemSkillBase {
    float trapRange = 2.5f;
    float tranRangeSq = trapRange * trapRange;
    public ItemShenjiaGrandTeleport(String name) {
        super(name);
        offHandCast = true;
        cannotMouseCast = true;
        maxDialogues = 4;
        maxLevel = 1;
        setCD(10, 0);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        super.applyCast(worldIn, livingBase, stack, slot);
        boolean result = false;
        float angle = livingBase.getRNG().nextFloat() * 6.282f;
        Vec3d dir = new Vec3d(Math.cos(angle), 0, Math.sin(angle));

        if (worldIn.isRemote)
        {
            createTeleportEffect(livingBase);
            return true;
        }

        Vec3d moving = new Vec3d(0,0,1);

        Vec3d oriPos = livingBase.getPositionEyes(0);
        for (float dist = 10; dist > 0 && !result; dist -= 0.5f)
        {
            //Idealland.Log("forward=%s",dir);

            for (float dy = 0f; dy <= 2f; dy += 0.1f)
            {
                result =
                        livingBase.attemptTeleport(livingBase.posX + dir.x * dist,
                                livingBase.posY + dir.y * dist + dy,
                                livingBase.posZ + dir.z * dist
                        );

                if (result)
                {
                    moving = livingBase.getPositionEyes(0f).subtract(oriPos);
                    //Idealland.Log("tp success, dist = %s, dy = %s", dist, dy);
                    break;
                }

                result =
                        livingBase.attemptTeleport(livingBase.posX + dir.x * dist,
                                livingBase.posY + dir.y * dist - dy,
                                livingBase.posZ + dir.z * dist
                        );

                if (result)
                {
                    moving = livingBase.getPositionEyes(0f).subtract(oriPos);
                    //Idealland.Log("tp success, dist = %s, dy = %s", dist, dy);
                    break;
                }
            }
        }

        //looks like if you do this after tp, it does not work? Reason unknown.
        double x = -moving.x;
        double z = -moving.z;
        livingBase.rotationYaw = (float)(-MathHelper.atan2(x, z) * (180D / Math.PI));
        livingBase.rotationPitch = (float)(MathHelper.atan2(moving.y, Math.sqrt(x*x + z*z)) * (180D / Math.PI));
        Idealland.Log("pitch = %s, moving.y = %s", livingBase.rotationPitch, moving.y);
        livingBase.setLocationAndAngles(livingBase.posX, livingBase.posY, livingBase.posZ, livingBase.rotationYaw, livingBase.rotationPitch);
        livingBase.setPositionAndUpdate(livingBase.posX, livingBase.posY, livingBase.posZ);

        List<EntityLivingBase> in_block = EntityUtil.getEntitiesWithinAABB(worldIn, EntityLivingBase.class, livingBase.getPositionEyes(0f), trapRange, null);
        for (EntityLivingBase target:
                in_block) {
            if (target != livingBase && livingBase.getDistanceSq(target) < tranRangeSq)
            {
                EntityUtil.ApplyBuff(target, MobEffects.SLOWNESS, 3, 2f);
                EntityUtil.ApplyBuff(target, MobEffects.MINING_FATIGUE, 3, 2f);
            }
        }

        if (result)
        {
            //tp success
            EntityUtil.ApplyBuff(livingBase, MobEffects.INVISIBILITY, 0, 10f);
            EntityUtil.ApplyBuff(livingBase, MobEffects.SPEED, 3, 10f);
            EntityUtil.ApplyBuff(livingBase, ModPotions.INVINCIBLE, 0, 1f);
        }
        else {
            Idealland.Log("%s tp failed.", livingBase.getName());
        }

        //trySayDialogue(livingBase, stack);

        return true;
    }
}
