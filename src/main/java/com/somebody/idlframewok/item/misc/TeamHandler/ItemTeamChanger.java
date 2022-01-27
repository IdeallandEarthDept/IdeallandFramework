package com.somebody.idlframewok.item.misc.TeamHandler;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.ItemBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemTeamChanger extends ItemBase {
    String teamName = "s";
    public ItemTeamChanger(String name)
    {
        super(name);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (playerIn.world.getScoreboard().getTeam(teamName) == null)
        {
            playerIn.world.getScoreboard().createTeam(teamName);
        }
        target.world.getScoreboard().addPlayerToTeam(target.getUniqueID().toString(), teamName);
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        if (playerIn.isSneaking())
        {
            if (worldIn.getScoreboard().getTeam(teamName) == null)
            {
                worldIn.getScoreboard().createTeam(teamName);
            }
            worldIn.getScoreboard().addPlayerToTeam(playerIn.getName(), teamName);
            Idealland.LogWarning(playerIn.getTeam().toString());
        }


//        public boolean isOnSameTeam(Entity entityIn)
//        {
//            return this.isOnScoreboardTeam(entityIn.getTeam());
//        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
