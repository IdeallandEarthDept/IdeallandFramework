package com.somebody.idlframewok.item;

import com.somebody.idlframewok.IdlFramework;
import com.somebody.idlframewok.entity.projectiles.EntityIdlProjectile;
import com.somebody.idlframewok.entity.projectiles.ProjectileArgs;
import com.somebody.idlframewok.gui.ModGuiElementLoader;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.IDLSkillNBT.GetGuaEnhance;

public class ItemDebug extends ItemBase{
    int index = 0;
    public ItemDebug SetIndex(int index){
        this.index = index;
        return this;
    }
    public ItemDebug(String name) {
        super(name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        //tooltip.add(I18n.format("idlframewok.gua_enhance_total.desc", IDLSkillNBT.GetGuaEnhanceTotal(stack)));
        tooltip.add(IDLNBTUtil.getNBT(stack).toString());
    }

//    public boolean isEnchantable(ItemStack stack)
//    {
//        return true;
//    }
//
//    @Override
//    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
//        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
//        if (stack.isItemEnchantable())
//        {
//            stack.addEnchantment(ModEnchantmentInit.fireBurn, 0);
//        }
//    }


    void TestEnchant(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        int p_185291_0_ = 30;
        ItemStack testItemStack = playerIn.getHeldItemOffhand();
        boolean allowTreasure = false;
        Item item = testItemStack.getItem();
        boolean flag = item == Items.BOOK;

        for (Enchantment enchantment : Enchantment.REGISTRY)
        {
            boolean judge1 = !enchantment.isTreasureEnchantment() || allowTreasure;
            boolean judge2 = enchantment.canApplyAtEnchantingTable(testItemStack);
            boolean judge3 = flag && enchantment.isAllowedOnBooks();

            IdlFramework.Log("[%s]Lv %d to %d, canApplyAtEnchantingTable = %s, judgement[%s,%s,%s]",
                    enchantment.getName(),
                    enchantment.getMinLevel(),
                    enchantment.getMaxLevel(),
                    enchantment.canApplyAtEnchantingTable(testItemStack),
                    judge1, judge2, judge3
            );
            if (judge1 && judge2 || judge3)
            {
                for (int i = enchantment.getMaxLevel(); i >= enchantment.getMinLevel() ; --i)
                {
                    IdlFramework.Log("[%s] Lv.%d available range = %d to %d", enchantment.getName(), i,
                            enchantment.getMinEnchantability(i), enchantment.getMaxEnchantability(i));
                    if (p_185291_0_ >= enchantment.getMinEnchantability(i) && p_185291_0_ <= enchantment.getMaxEnchantability(i))
                    {
                        IdlFramework.Log("[%s] successfully elected", enchantment.getName());
                        break;
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        playerIn.swingArm(handIn);

        ItemStack stack = playerIn.getHeldItem(handIn);

        if (index == 2 && !worldIn.isRemote) {
            EntityIdlProjectile bullet = new EntityIdlProjectile(worldIn, new ProjectileArgs(4f), playerIn, 0, 0.05, 0);
            worldIn.spawnEntity(bullet);
            IdlFramework.Log("bullet pos = %s", bullet.getPosition());
            worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1f, 1f);
            //return
        }

        if (!worldIn.isRemote)
        {
            //playerIn.getHeldItemOffhand().getItem() == ModItems.DEBUG_ITEM_3;
            if (index == 3)
            {
                BlockPos pos = playerIn.getPosition();
                playerIn.openGui(IdlFramework.instance, ModGuiElementLoader.GUI_RESEARCH, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
            //else

            //todo: optimize
            //playerIn.openGui(IdlFramework.instance, ModGuiElementLoader.GUI_DEMO, worldIn, pos.getX(), pos.getY(), pos.getZ());

//            for (int i = 1; i <= 1000; i++)
//            {
//                IDLNBTUtil.SetString(stack, "TEST", IDLNBTUtil.GetString(stack, "TEST", "") + "a");
//            }
        }
        //IdlFramework.Log("NBT len = %d", IDLNBTUtil.GetString(stack, "TEST", "").length());

        //IDLSkillNBT.SetGuaEnhanceFree(playerIn.getHeldItem(handIn), 2);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }


}
