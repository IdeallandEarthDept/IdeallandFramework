package com.somebody.idlframewok.item.goblet;

import com.somebody.idlframewok.util.IDLGeneral;
import com.somebody.idlframewok.util.Reference;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemKnockBackGoblet extends ItemGobletBase {
    public ItemKnockBackGoblet(String name) {
        super(name);
    }

    public int getBuffLevel(int level)
    {
        int result = level / 3;
        return result > 4 ? 4 : result;
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
                Vec3d basePos = playerIn.getPositionVector();
                List<EntityLiving> entities = worldIn.getEntitiesWithinAABB(EntityLiving.class, IDLGeneral.ServerAABB(basePos.addVector(-level, -level, -level), basePos.addVector(level, level, level)));
                for (EntityLiving living: entities
                ) {
                    living.knockBack(playerIn, level * 0.1f + 0.5f, (playerIn.posX - living.posX), (playerIn.posZ - living.posZ));
                }

                playerIn.addPotionEffect(new PotionEffect(MobEffects.SPEED, TICK_PER_SECOND, getBuffLevel(level)));
                if (playerIn.isSneaking())
                {
                    playerIn.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, TICK_PER_SECOND, getBuffLevel(level)));
                }
            }
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {
        int level = GetLevelFromEXP(GetCacheEXP(stack));
        target.addPotionEffect(new PotionEffect(MobEffects.SPEED, TICK_PER_SECOND * level, getBuffLevel(level)));
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
