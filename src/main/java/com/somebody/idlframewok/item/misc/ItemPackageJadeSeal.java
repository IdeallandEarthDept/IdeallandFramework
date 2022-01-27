package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.advancements.AdvancementKeys;
import com.somebody.idlframewok.advancements.ModAdvancementsInit;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.PlayerUtil;
import com.somebody.idlframewok.util.WorldUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemPackageJadeSeal extends ItemPackage {
    int pick = 1;

    public ItemPackageJadeSeal(String name, Item[] validItems) {
        super(name, validItems);
    }

    public ItemPackageJadeSeal(String name, Item[] validItems, int pick) {
        super(name, validItems, pick);
    }

    final String APPEND = ".1";

    @Override
    public String descGetKey(ItemStack stack, World world, boolean showFlavor) {
        boolean claimed = IDLNBTUtil.GetInt(stack, IDLNBTDef.STATE) > 0;
        if (claimed)
        {
            return super.descGetKey(stack, world, showFlavor) + APPEND;
        }
        return super.descGetKey(stack, world, showFlavor);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote)
        {
            IDLNBTUtil.setInt(stack, IDLNBTDef.STATE, WorldUtil.getDimDataInt(worldIn, IDLNBTDef.SEAL));
        }
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        player.setActiveHand(hand);
        ItemStack stack = player.getHeldItem(hand);

        if (!world.isRemote) {

            boolean claimed = WorldUtil.getDimDataInt(world, IDLNBTDef.SEAL) > 0;
            if (!claimed)
            {
                if (world.rand.nextFloat() < 0.01f)
                {
                    WorldUtil.setDimData(world, IDLNBTDef.SEAL, 1);

                    int dimID = world.provider.getDimension();

                    ItemStack itemStack = new ItemStack(ModItems.JADE_SEAL);
                    IDLNBTUtil.SetString(itemStack, IDLNBTDef.IMPRINT_DISP, player.getName());
                    IDLNBTUtil.setInt(itemStack, IDLNBTDef.IMPRINT, dimID);

                    PlayerUtil.giveToPlayer(player, itemStack);
                    ModAdvancementsInit.giveAdvancement(player, AdvancementKeys.M_LOYALTY_PROVED);
                    //world.playSound(player, player.getPosition(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.PLAYERS, 1f, 1f);
                    world.addWeatherEffect(new EntityLightningBolt(world, player.posX, player.posY, player.posZ, true));
                    return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
                }
            }

            return super.onItemRightClick(world, player, hand);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return EnumActionResult.PASS;
    }
}
