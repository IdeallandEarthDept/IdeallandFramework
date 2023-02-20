package com.somebody.idlframewok.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class IDLGeneral {
    //server side dont have this constructor.
    public static AxisAlignedBB ServerAABB(Vec3d from, Vec3d to)
    {
        return new AxisAlignedBB(from.x, from.y, from.z, to.x, to.y, to.z);
    }

    public static AxisAlignedBB ServerAABB(Vec3d origin, float range)
    {
        return new AxisAlignedBB(origin.x - range, origin.y - range, origin.z - range,
                origin.x + range, origin.y + range, origin.z + range);
    }

    public static boolean EntityHasBuff(EntityLivingBase livingBase, Potion buff)
    {
        return livingBase.getActivePotionEffect(buff) != null;
    }

    public static int EntityBuffCounter(EntityLivingBase livingBase, Potion buff)
    {
        PotionEffect effect = livingBase.getActivePotionEffect(buff);
        return effect == null ? -1 : effect.getDuration();
    }

    public static int returnGuaIndex(ItemStack stack)
    {
        Item item = stack.getItem();
//        for (int i = 0; i < GUA_TYPES; i++)
//        {
//            if (item == ModItems.GUA[i])
//            {
//                return i;
//            }
//        }
        return -1;
    }
}
