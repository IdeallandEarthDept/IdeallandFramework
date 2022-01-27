package com.somebody.idlframewok.item.skills.arknights;

import com.somebody.idlframewok.Idealland;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemShenjiaTeleport extends ItemArknightsSkillBase {
    public ItemShenjiaTeleport(String name) {
        super(name);
        offHandCast = true;
        cannotMouseCast = true;
        maxDialogues = 4;
        maxLevel = 10;

        dura = new int[]{1,1,1,1,1, 1,1,1,1,1};
        max_charge = new int[]{2};
        //max_charge = new int[]{3};
        initPower = new int[]{0,0,0,0,0,0,0,0,0,0};
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {

        super.applyCast(worldIn, livingBase, stack, slot);

        if (worldIn.isRemote)
        {
            return true;
        }

        boolean result = false;
        //Vec3d speed = new Vec3d(livingBase.motionX, livingBase.motionY, livingBase.motionZ);
        for (float dist = 3f; dist > 0 && !result; dist -= 0.5f)
        {
            //EntityPlayer player = (EntityPlayer) livingBase;

            Vec3d dir = livingBase.getForward();//.scale(livingBase.moveForward);
            Idealland.Log("forward=%s",dir);
            //y is unknown.
            //can pass wall, need further testing. currently seems looking down goes near.

            for (float dy = 0f; dy <= 1f; dy += 0.1f)
            {
                result =
                        livingBase.attemptTeleport(livingBase.posX + dir.x * dist,
                                livingBase.posY + dir.y * dist + dy,
                                livingBase.posZ + dir.z * dist
                        );

                if (result)
                {
                    Idealland.Log("tp success, dist = %s, dy = %s", dist, dy);
                    break;
                }

                result =
                        livingBase.attemptTeleport(livingBase.posX + dir.x * dist,
                                livingBase.posY + dir.y * dist - dy,
                                livingBase.posZ + dir.z * dist
                        );

                if (result)
                {
                    Idealland.Log("tp success, dist = %s, dy = %s", dist, dy);
                    break;
                }
            }

        }

        //trySayDialogue(livingBase, stack);

        return true;
    }
}
