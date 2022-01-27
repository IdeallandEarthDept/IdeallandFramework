package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.IDLGeneral;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ItemDisturbMeasure extends ItemBase {
    public ItemDisturbMeasure(String name, int maxValue, Item itemToGive, int giveCount) {
        super(name);
        max = maxValue;
        this.itemToGive = itemToGive;
        this.giveCount = giveCount;
    }

    Item itemToGive = Items.PAPER;
    int max = 128;
    int giveCount = 1;

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        int curVal = IDLNBTUtil.GetInt(stack, IDLNBTDef.CHARGE_VALUE);
        if (curVal > max)
        {
            IDLNBTUtil.setInt(stack, IDLNBTDef.CHARGE_VALUE, curVal - max);
            EntityPlayer player = (EntityPlayer) entityIn;
            player.addItemStackToInventory(new ItemStack(itemToGive));
        }

    }

    public void IncreaseMeterValue(ItemStack stack, int val)
    {
        int curVal = IDLNBTUtil.GetInt(stack, IDLNBTDef.CHARGE_VALUE);
        curVal += val;
        IDLNBTUtil.setInt(stack, IDLNBTDef.CHARGE_VALUE, curVal);
    }

    @SideOnly(Side.CLIENT)
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        String key = stack.getUnlocalizedName() + ".desc";
        if (I18n.hasKey(key))
        {
            return I18n.format(stack.getUnlocalizedName() + ".desc",
                    IDLNBTUtil.GetInt(stack, IDLNBTDef.CHARGE_VALUE),
                    max
                    //GetGuaEnhance(stack, 7)
            );
        }
        return "";
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        if (evt.isCanceled())
        {
            return;
        }
        //todo: also applies to moroon block changing

        World world = evt.getEntity().getEntityWorld();
        if (!world.isRemote) {
            EntityLivingBase hurtOne = evt.getEntityLiving();
            Entity trueSource = evt.getSource().getTrueSource();

//            Idealland.Log("Damage Event in Disturb Measure. %s to %s, isVanillaResident = %s, isOtherworldAggression = %s,",
//                    trueSource != null ? trueSource.getName() : "--", hurtOne.getName(),
//                    EntityUtil.isVanillaResident(hurtOne),  EntityUtil.isOtherworldAggression((EntityLivingBase) trueSource));

            if (trueSource instanceof EntityLivingBase)
            {
                if (EntityUtil.isVanillaResident(hurtOne) && EntityUtil.isOtherworldAggression((EntityLivingBase) trueSource))
                {
                    float base_range = 16f;
                    Vec3d basePos = hurtOne.getPositionVector();
                    List<EntityPlayer> entities = hurtOne.world.getEntitiesWithinAABB(EntityPlayer.class, IDLGeneral.ServerAABB(basePos.addVector(-base_range, -base_range, -base_range), basePos.addVector(base_range, base_range, base_range)));

                    int interferenceValue = (int) evt.getAmount();
                    boolean found = false;
                    for (EntityPlayer player: entities
                    ) {
                        //Idealland.Log("player found");
                        //todo
                        for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
                        {
                            ItemStack itemstack = player.inventory.getStackInSlot(i);
                            if (!itemstack.isEmpty())
                            {
                                Item itemType =  itemstack.getItem();
                                if (itemType instanceof ItemDisturbMeasure)
                                {
                                    ItemDisturbMeasure itemDisturbMeasure = (ItemDisturbMeasure) itemType;
                                    itemDisturbMeasure.IncreaseMeterValue(itemstack, interferenceValue);
                                    //Idealland.Log("Item Add value:%s", interferenceValue);
                                    //Idealland.Log(itemstack.getTagCompound().toString());
                                    found = true;
                                    break;
                                }
                            }

                            if (found)
                            {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


}
