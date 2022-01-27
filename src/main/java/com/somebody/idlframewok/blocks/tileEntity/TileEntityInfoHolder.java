package com.somebody.idlframewok.blocks.tileEntity;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import static com.somebody.idlframewok.util.CommonDef.ANY_TYPE;

//for those blocks who need to store some information.
public class TileEntityInfoHolder extends TileEntity {
    public int state1 = 0;
    public int state2 = 0;
    public String str = CommonDef.EMPTY;
    public String str2 = CommonDef.EMPTY;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        try {
            if (compound.hasKey(IDLNBTDef.STR, ANY_TYPE))
            {
                state1 = compound.getInteger(IDLNBTDef.STATE);
            }

            if (compound.hasKey(IDLNBTDef.STR_2, ANY_TYPE))
            {
                state2 = compound.getInteger(IDLNBTDef.STATE_2);
            }
        }
        catch (Exception e)
        {
            Idealland.LogWarning("TileEntityInfoHolder - Cannot parse states from: "+compound.toString());
        }

        if (compound.hasKey(IDLNBTDef.STR, 8))
        {
            str = compound.getString(IDLNBTDef.STR);
        }

        if (compound.hasKey(IDLNBTDef.STR_2, 8))
        {
            str2 = compound.getString(IDLNBTDef.STR_2);
        }
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setString(IDLNBTDef.STR, str);
        compound.setString(IDLNBTDef.STR_2, str2);
        compound.setInteger(IDLNBTDef.STATE, state1);
        compound.setInteger(IDLNBTDef.STATE_2, state2);

        return compound;
    }
}
