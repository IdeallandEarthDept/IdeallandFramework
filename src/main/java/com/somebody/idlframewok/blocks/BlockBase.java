package com.somebody.idlframewok.blocks;

import com.somebody.idlframewok.init.RegistryHandler;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.IHasModel;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockBase extends Block implements IHasModel
{
	boolean isSolid = true;
	boolean canPickup = false;
    boolean logNBT = false;
    protected boolean showGuaSocketDesc = false;
    protected boolean shiftToShowDesc = false;
    protected boolean use_flavor = false;
	private float jumpFactor = 1f;

	public BlockBase(String name, Material material)
	{
		super(material);
		CommonFunctions.init(this, name);
		addToItems();

		setHardness(5.0F);
		//setResistance(1000.0F);
		//setHarvestLevel("pickaxe", 1);
		//setLightLevel(1f);
		setLightOpacity(15);
	}

	public void addToItems()
	{
		RegistryHandler.addToItems(this);
	}

	public BlockBase setHarvestThis(String toolClass, int level)
	{
		setHarvestLevel(toolClass, level);
		return this;
	}

	public boolean isSolid() {
		return isSolid;
	}

	public BlockBase setSolid(boolean isSolid)
	{
		this.isSolid = isSolid;
		return this;
	}

	public BlockBase setPickable(boolean val)
	{
		this.canPickup = val;
		return this;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (canPickup)
		{
			if (!worldIn.isRemote) {
				PlayerUtil.giveToPlayer(playerIn, new ItemStack(this, 1, getMetaFromState(state)));
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
			}

			return true;
		}
		else {
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
	}

	public boolean causesSuffocation(IBlockState state)
	{
		return isSolid;
	}

	@Deprecated
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		if (isSolid) {
			return blockState.getBoundingBox(worldIn, pos);
		}
		else
		{
			return null;
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return super.getItemDropped(state, rand, fortune);
	}

	@Override
	public int quantityDropped(Random rand) {
		return super.quantityDropped(rand);
	}

    //----------------

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {

        IDLSkillNBT.addInformation(stack, world, tooltip, flag, shiftToShowDesc, showGuaSocketDesc, use_flavor,
                getMainDesc(stack, world, tooltip, flag));

        if (logNBT) {
            tooltip.add(IDLNBTUtil.getNBT(stack).toString());
        }
    }

    @SideOnly(Side.CLIENT)
    public String descGetKey(ItemStack stack, World world, boolean showFlavor) {
        return showFlavor ? (stack.getUnlocalizedName() + IDLNBTDef.FLAVOR_KEY)
                : (stack.getUnlocalizedName() + IDLNBTDef.DESC_COMMON);
    }

    @SideOnly(Side.CLIENT)
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        //Makesure you only call this in Remote!

        if (CommonFunctions.isShiftPressed() || !shiftToShowDesc) {
            String key = descGetKey(stack, world, false);
            if (I18n.hasKey(key)) {
                return getFormatResult(key, stack, world, tooltip, flag);
            } else {
                return "";
            }
        }

        if (!CommonFunctions.isShiftPressed() && use_flavor) {
            String key = descGetKey(stack, world, true);
            if (I18n.hasKey(key)) {
                return I18n.format(key);
            } else {
                return "";
            }
        }

        return "";
    }

    @SideOnly(Side.CLIENT)
    public String getFormatResult(String key, ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        return I18n.format(key);
    }

	public float getJumpFactor() {
		return jumpFactor;
	}

	public void setJumpFactor(float jumpFactor) {
		this.jumpFactor = jumpFactor;
	}
}
