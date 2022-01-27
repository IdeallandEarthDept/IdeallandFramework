package com.somebody.idlframewok.item.diagnostics;

import com.somebody.idlframewok.designs.events.design.EventsParasiteCurse;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.MODE;

public class ItemDiagnosticParasite extends ItemDiagnosticBase{
    public ItemDiagnosticParasite(String name) {
        super(name);
    }

    @Override
    public boolean onUseSimple(EntityPlayer player, ItemStack stack) {
        if (!player.world.isRemote)
        {
            boolean infect = EventsParasiteCurse.getInfectionRate(player) > 0;
            IDLNBTUtil.setInt(stack, IDLNBTDef.STATE, infect ? 1 : 0);

            SendMsg(player, infect);
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    public String getNameKey(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        int mode = IDLNBTUtil.GetInt(stack, MODE);
        return stack.getUnlocalizedName() + ".desc." + String.valueOf(mode);
    }
}
