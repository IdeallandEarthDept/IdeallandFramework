package com.somebody.idlframewok.item.diagnostics;

import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.STATE;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil.GetInt;

public class ItemDiagnosticBase extends ItemBase {
    public ItemDiagnosticBase(String name) {
        super(name);
        useable = true;
        this.addPropertyOverride(new ResourceLocation(STATE), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)//when not in hand, world is null.
                {
                    return 1.0F;//set to 0 will cause no updating when not in hand. dunno why
                }
                else
                {
//                    if (worldIn == null)
//                    {
//                        worldIn = entityIn.world;
//                    }
//                    int state = GetInt(stack, STATE);
                    //Idealland.Log("State = " + state);
                    return (float)GetInt(stack, STATE);
                }
            }
        });
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    protected void SendMsg(EntityPlayer player, boolean isPositive)
    {
        if (isPositive)
        {
            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, player, "idlframewok.msg.diagnostic_positive");
        }
        else {
            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GREEN, player, "idlframewok.msg.diagnostic_negative");
        }
    }
}
