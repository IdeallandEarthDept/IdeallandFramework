package com.deeplake.idealland.item.food;

import com.deeplake.idealland.IdlFramework;
import com.deeplake.idealland.init.ModCreativeTab;
import com.deeplake.idealland.item.ModItems;
import com.deeplake.idealland.util.IHasModel;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemFoodBase extends ItemFood implements IHasModel {

    public int addXP = 0;

    Potion potion;
    int level;
    int duration;

    public ItemFoodBase SetXP(int addXP)
    {
        this.addXP = addXP;
        return this;
    }


    @Override
    public void registerModels()
    {
        IdlFramework.proxy.registerItemRenderer(this, 0, "inventory");
    }

    public ItemFoodBase(String name, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTab.IDL_MISC);

        ModItems.ITEMS.add(this);

        InitItem();
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        //IdlFramework.Log("%s:on Food Eaten", getUnlocalizedName());
        super.onFoodEaten(stack, worldIn, player);
        if (addXP > 0)
        {
            player.addExperience(addXP);
        }
    }

    public void InitItem()
    {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        String key = stack.getUnlocalizedName() + ".desc";
        if (I18n.hasKey(key))
        {
            String mainDesc = I18n.format(key);
            tooltip.add(mainDesc);
        }
        if (addXP > 0)
            tooltip.add(I18n.format("idealland.food.shared.xp_desc", addXP));
    }
}
