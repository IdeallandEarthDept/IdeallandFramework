package com.deeplake.idealland.item.edicts;

import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.IDLGeneral;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ItemKillAllMortal extends ItemEdictBase {
    public float XZRangeRadius = 128f;
    public float YRangeRadius = 128f;

//    public ItemKillAllMortal()
//    {
//        super();
//    }

    public ItemKillAllMortal(String name)
    {
        super(name);
    }


    public void OnCastSuccess(ItemStack stack, World world, EntityLivingBase caster, int time)
    {
        if (!world.isRemote) {
            if (caster.world.isRemote){
                return;
            }

            World worldIn = caster.world;
            Vec3d pos = caster.getPositionEyes(1.0F);

            List<Entity> entities = worldIn.getEntitiesWithinAABB(EntityLivingBase.class,
                    IDLGeneral.ServerAABB(pos.addVector(-XZRangeRadius, -YRangeRadius, -XZRangeRadius), pos.addVector(XZRangeRadius, YRangeRadius, XZRangeRadius)));
            for (Entity entity : entities)
            {
                //if the caster is a non-player, it can kill itself.
                if (entity instanceof EntityPlayer){
                    continue;
                }

                ((EntityLivingBase)entity).setHealth(0);
            }
        }
        super.OnCastSuccess(stack, world, caster, time);
    }
}
