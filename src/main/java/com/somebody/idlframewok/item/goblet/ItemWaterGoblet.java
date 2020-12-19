package com.somebody.idlframewok.item.goblet;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class ItemWaterGoblet extends ItemGobletBase {
    public ItemWaterGoblet(String name) {
        super(name);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        //todo : produce water, gives breathing and night vision buff in water; acquire those abilities one by one

        if (isSelected && !worldIn.isRemote)
        {
            int level = GetLevelFromEXP(GetCacheEXP(stack));

            if (level > 0 && worldIn.getWorldTime() % TICK_PER_SECOND == 0)
            {
//                EntityPlayer playerIn = (EntityPlayer) entityIn;
//                Vec3d basePos = playerIn.getPositionVector();
//                List<EntityLiving> entities = worldIn.getEntitiesWithinAABB(EntityLiving.class, IDLGeneral.ServerAABB(basePos.addVector(-level, -level, -level), basePos.addVector(level, level, level)));
//                for (EntityLiving living: entities
//                ) {
//                    living.attackEntityFrom(causePlayerDamage(playerIn).setFireDamage().setMagicDamage(), level);
//                    living.setFire(level * TICK_PER_SECOND);
//                }
//
//                playerIn.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, TICK_PER_SECOND, 0));
//                playerIn.setFire(level * TICK_PER_SECOND);
            }
        }
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
