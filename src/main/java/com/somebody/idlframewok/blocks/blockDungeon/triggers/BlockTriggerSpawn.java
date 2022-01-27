package com.somebody.idlframewok.blocks.blockDungeon.triggers;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.blockDungeon.BlockDungeonWall;
import com.somebody.idlframewok.blocks.blockDungeon.ITriggerable;
import com.somebody.idlframewok.blocks.tileEntity.TileEntityInfoHolder;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.CommonFunctions.getVecFromBlockPos;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.ENTITY_TAG;

public class BlockTriggerSpawn extends BlockDungeonWall implements ITriggerable, ITileEntityProvider {
    public BlockTriggerSpawn(String name, Material material) {
        super(name, material);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && PlayerUtil.isCreative(playerIn))
        {
            TileEntity te = worldIn.getTileEntity(pos);
            ItemStack stack = playerIn.getHeldItem(hand);
            if (te instanceof TileEntityInfoHolder && stack.getItem() instanceof ItemMonsterPlacer)
            {
                try {
                    ((TileEntityInfoHolder) te).str = stack.getTagCompound() == null ? "" : (stack.getTagCompound().getCompoundTag(ENTITY_TAG)).toString();
                    CommonFunctions.LogPlayerAction(playerIn, String.format("Set %s to %s", ((TileEntityInfoHolder) te).str, pos));
                }
                catch (Exception e)
                {
                    CommonFunctions.LogPlayerAction(playerIn,"Trying to apply empty spawn egg");
                }
            }
        }

        return true;
    }

    @Override
    public void triggerAt(World world, BlockPos pos) {
        if (!world.isRemote)
        {
            TileEntity te = world.getTileEntity(pos);
            Entity entity = null;
            try {
                if (te instanceof TileEntityInfoHolder)
                {
                    TileEntityInfoHolder holder = (TileEntityInfoHolder) te;
                    entity = EntityList.createEntityFromNBT(JsonToNBT.getTagFromJson(holder.str) , world);
                    Idealland.Log("str = " + holder.str);
                }
            }
            catch (NBTException e)
            {
                Idealland.LogWarning("BlockTriggerSpawn Error:" + e.toString());

            }

            if (entity == null)
            {
                entity = new EntityPig(world);
            }

            Vec3d posV = getVecFromBlockPos(pos);
            entity.setPosition(posV.x, posV.y+1f, posV.z);
            world.spawnEntity(entity);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityInfoHolder();
    }
}
