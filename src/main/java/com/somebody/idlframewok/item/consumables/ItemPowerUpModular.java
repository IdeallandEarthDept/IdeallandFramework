package com.somebody.idlframewok.item.consumables;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.MARK_TOTAL_COUNT;

public class ItemPowerUpModular extends ItemBase {
    private IAttribute attrType;
    private float amountFixed;

    public ItemPowerUpModular(String name, IAttribute attrType, float amountFixed) {
        super(name);
        this.attrType = attrType;
        this.amountFixed = amountFixed;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {
        if (target instanceof EntityModUnit)
        {
            EntityModUnit legalTarget = (EntityModUnit) target;
            if (legalTarget.is_mechanic)
            {
                int requireLv = (IDLNBTUtil.GetInt(target, MARK_TOTAL_COUNT, 0));
                if (requireLv > playerIn.experienceLevel)
                {
                    CommonFunctions.SafeSendMsgToPlayer(playerIn, "idlframewok.msg.upgrade_module.req_lv", String.valueOf(requireLv));
                    return false;
                }

                if (!playerIn.world.isRemote)
                {
                    double valueBefore = EntityUtil.getAttr(legalTarget, attrType);
                    boolean success = EntityUtil.boostAttr(legalTarget, attrType, amountFixed, POWER_UP_MODIFIER);
                    if (success)
                    {
                        double valueAfter = EntityUtil.getAttr(legalTarget, attrType);
                        CommonFunctions.SafeSendMsgToPlayer(playerIn, "idlframewok.msg.upgrade_module.ok", valueBefore, valueAfter);
                        playerIn.swingArm(handIn);
                        target.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                        stack.shrink(1);
                        IDLNBTUtil.SetInt(target,MARK_TOTAL_COUNT, requireLv + 1);
                        if (attrType == SharedMonsterAttributes.MAX_HEALTH)
                        {
                            target.heal(amountFixed);
                        }
                    }
                    else {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        String key = stack.getUnlocalizedName() + ".desc";
        if (I18n.hasKey(key))
        {
            String mainDesc = I18n.format(key, amountFixed);
            return mainDesc;
        }
        return "";
    }
}
