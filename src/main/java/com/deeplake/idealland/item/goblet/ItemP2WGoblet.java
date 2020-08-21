package com.deeplake.idealland.item.goblet;

import com.deeplake.idealland.item.ItemBase;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.deeplake.idealland.util.IDLNBT.getTagSafe;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.*;

public class ItemP2WGoblet extends ItemGobletBase {
    public ItemP2WGoblet(String name) {
        super(name);
    }


}
