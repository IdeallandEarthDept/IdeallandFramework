package com.somebody.idlframewok.item.goblet;

import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.google.common.primitives.Ints;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.ASSIGNED_BLOCK_NAME;

public class ItemDigGoblet extends ItemGobletBase {
    public ItemDigGoblet(String name) {
        super(name);
    }

    private void Test()
    {
        //Entity entity = AnvilChunkLoader.readWorldEntityPos(nbttagcompound, world, d0, d1, d2, false);
    }

    public String GetStoredBlockName(ItemStack stack)
    {
        NBTTagCompound compound = stack.getTagCompound();
        return compound == null ? "" : compound.getString(ASSIGNED_BLOCK_NAME);
    }

    public void SetStoredBlockName(ItemStack stack, Block block)
    {
        ResourceLocation resourceLocation = block.getRegistryName();
        IDLNBTUtil.SetString(stack, ASSIGNED_BLOCK_NAME, resourceLocation==null ? "" : block.getRegistryName().toString());
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if (isSelected && !worldIn.isRemote)
        {
            int level = GetLevelFromEXP(GetCacheEXP(stack));

            String storedName = GetStoredBlockName(stack);
            boolean hasSPBlock = !storedName.isEmpty();

            if (level > 0)
            {
                int maxRange = 5;
                int maxHeight = 3;

                int rangeXZ = Ints.min(maxRange, level);
                int rangeY = Ints.min(maxHeight, level);

                for (int x = -rangeXZ; x <= rangeXZ; x++)
                {
                    for (int z = -rangeXZ; z <= rangeXZ; z++)
                    {
                        for (int y = 0; y <= rangeY; y++)
                        {
                            Block block = worldIn.getBlockState(entityIn.getPosition().add(x, y, z)).getBlock();
                            ResourceLocation loc = block.getRegistryName();
                            if (block == Blocks.STONE
                            || (hasSPBlock && loc != null && loc.toString().equals(storedName)))
                            {
                                worldIn.setBlockState(entityIn.getPosition().add(x, y, z), Blocks.AIR.getDefaultState());
                            }
                        }
                    }
                }
            }
        }
    }

    //Desc
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        String mainDesc = I18n.format(stack.getUnlocalizedName() + ".desc");
        tooltip.add(mainDesc);

        String blockDesc = I18n.format( stack.getUnlocalizedName() +  ".desc2", GetStoredBlockName(stack));
        tooltip.add(blockDesc);
        addInformationLast(stack, world, tooltip, flag);
    }
}
