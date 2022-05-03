package com.somebody.idlframewok.item;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.entity.EntityItemAntiBoom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber
public class ItemAntiBoomBase extends ItemBase {
    public ItemAntiBoomBase(String name) {
        super(name);
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        EntityItem entityItem = new EntityItemAntiBoom(world,
                location.posX, location.posY, location.posZ, itemstack);

        if (location instanceof EntityItem)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            location.writeToNBT(tagCompound);
            entityItem.setPickupDelay(tagCompound.getShort("PickupDelay"));
        }


        entityItem.motionX = location.motionX;
        entityItem.motionY = location.motionY;
        entityItem.motionZ = location.motionZ;

        return entityItem;
    }

    @SubscribeEvent
    static void onBoom(ExplosionEvent.Detonate explosionEvent)
    {
//        Iterator it = explosionEvent.getAffectedEntities().iterator();
//        while(it.hasNext()) {
//            Entity entity = (Entity) it.next();
//            if (entity instanceof EntityItem)
//            {
//                if (((EntityItem) entity).getItem().getItem()
//                        == ModItems.BIO_METAL_INGOT_1)
//                {
//                    explosionEvent.getAffectedEntities().remove(entity);
//                }
//            }
//        }
        HashSet<Entity> removal = new HashSet<>();
        for (Entity entity : explosionEvent.getAffectedEntities())
        {
            if (entity instanceof EntityItem)
            {
                if (((EntityItem) entity).getItem().getItem()
                        == ModItems.BIO_METAL_INGOT_1)
                {
                    removal.add(entity);
                }
            }
        }

        for (Entity entity : removal)
        {
            Idealland.Log("before = %d", explosionEvent.getAffectedEntities().size());
            explosionEvent.getAffectedEntities().remove(entity);
            Idealland.Log("after = %d", explosionEvent.getAffectedEntities().size());
        }
    }
}
