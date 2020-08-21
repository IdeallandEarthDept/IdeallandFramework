package com.deeplake.idealland.item.goblet;

import com.deeplake.idealland.item.ModItems;
import com.deeplake.idealland.potion.ModPotions;
import com.deeplake.idealland.util.IDLGeneral;
import com.deeplake.idealland.util.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemMountainGoblet extends ItemGobletBase {
    public ItemMountainGoblet(String name) {
        super(name);
    }

    public int getBuffLevel(int level)
    {
        int result = level / 3;
        return result > 4 ? 4 : result;
    }


    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent evt) {
        Entity hit = evt.getRayTraceResult().entityHit;
        if (hit instanceof EntityLivingBase)
        {
           ItemStack stack =  ((EntityLivingBase) hit).getHeldItemMainhand();
           if (stack.getItem() instanceof ItemMountainGoblet)
           {
               if (GetLevelFromItemStack(stack) >= 10)
               {
                   evt.setCanceled(true);
                   evt.getEntity().setDead();
               }
           }
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if (isSelected && !worldIn.isRemote)
        {
            int level = GetLevelFromEXP(GetCacheEXP(stack));

            if (level > 0 && worldIn.getWorldTime() % (TICK_PER_SECOND << 1) == 0)
            {
                EntityPlayer playerIn = (EntityPlayer) entityIn;
//                Vec3d basePos = playerIn.getPositionVector();
//                List<EntityLiving> entities = worldIn.getEntitiesWithinAABB(EntityLiving.class, IDLGeneral.ServerAABB(basePos.addVector(-level, -level, -level), basePos.addVector(level, level, level)));
//                for (EntityLiving living: entities
//                ) {
//                    living.knockBack(playerIn, level * 0.1f + 0.5f, (playerIn.posX - living.posX), (playerIn.posZ - living.posZ));
//                }

                playerIn.addPotionEffect(new PotionEffect(ModPotions.KB_RESIST, TICK_PER_SECOND, getBuffLevel(level)));
                if (playerIn.isSneaking())
                {
                    playerIn.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, TICK_PER_SECOND, getBuffLevel(level)));
                }
            }
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {
        int level = GetLevelFromEXP(GetCacheEXP(stack));
        target.addPotionEffect(new PotionEffect(ModPotions.KB_RESIST, TICK_PER_SECOND * level, getBuffLevel(level)));
        activateCoolDown(playerIn, stack);
        target.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1f, 1f);
        return true;
    }

    //Desc
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        String mainDesc = I18n.format(stack.getUnlocalizedName() + ".desc", GetLevelFromEXP(GetCacheEXP(stack)));
        tooltip.add(mainDesc);
        addInformationLast(stack, world, tooltip, flag);
    }
}
