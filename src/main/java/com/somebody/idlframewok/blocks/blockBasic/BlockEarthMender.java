package com.somebody.idlframewok.blocks.blockBasic;



import com.somebody.idlframewok.blocks.BlockBase;

import com.somebody.idlframewok.blocks.tileEntity.orbs.TileEntityEarthMender;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEarthMender extends BlockBase implements ITileEntityProvider {

	int range = 5;
	int depth = 4;
	
	public BlockEarthMender(String name, Material material) {
		super(name, Material.GROUND);
		
		setSoundType(SoundType.METAL);
		setHardness(1.0F);
		setResistance(15.0F);
		setHarvestLevel("pickaxe", 1);
		setLightLevel(0f);
		setLightOpacity(1);
		setCreativeTab(ModCreativeTabsList.IDL_BUILDING);
		setTickRandomly(false);
	}

	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityEarthMender();
	}

//	public boolean requiresUpdates()
//    {
//        return true;
//    }
	
//    @Override
//    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
//    {
//    	super.updateTick(worldIn, pos, state, rand);
//		if (!worldIn.isRemote)
//        {
//			Idealland.LogWarning("updateTick-------------------------");
//        }
//    }
    
    /**
     * How many world ticks before ticking
     */
//    public int tickRate(World worldIn)
//    {
//        return 5;
//    }
    
    /**
     * Called randomly when setTickRandomly is set to true (used by e.g. crops to grow, etc.)
     */
//    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
//    {
//    	Idealland.LogWarning("randomTick-------------------------");
//        super.randomTick(worldIn, pos, state, random);
//    }

    private boolean IsBlockStateEmpty(World worldIn, IBlockState blockState) {
    	boolean result = blockState.getBlock() == Blocks.AIR;
    	return result;
    }
    
    private boolean IsBlockPosEmpty(World worldIn, BlockPos targetPos) {
    	IBlockState block = worldIn.getBlockState(targetPos);
    	boolean result = (block.getBlock() == Blocks.AIR) ||
    			(block.getBlock().isReplaceable(worldIn, targetPos));
    	//&& block.isOpaqueCube();
    	return result;
    }
    
    private void PerformAction()
    {
    	
    }
    
    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if (!worldIn.isRemote)
        {
    		IBlockState block =  Blocks.DIRT.getDefaultState();
    		
    		int diameter = 2 * range + 1;
    		int zFactor = diameter;
    		int slowDownFactor = 5;
    		
    		long worldTime = worldIn.getTotalWorldTime() / slowDownFactor;
    		
    		int dx = (int) (worldTime % diameter) - range;
    		int dz = (int) (worldTime / zFactor % (diameter)) - range;
    		
    		BlockPos targetPos = pos.add(dx, -depth, dz);
    		
    		boolean isDownEmpty = IsBlockPosEmpty(worldIn, targetPos.down());
			boolean isCenterEmpty = IsBlockPosEmpty(worldIn, targetPos);
    		
    		for(int y = 1; y <= depth; y++)
    		{	
    			boolean[] nearbyOccupied = new boolean[4];
    			nearbyOccupied[0] = !IsBlockPosEmpty(worldIn, targetPos.east());
    			nearbyOccupied[1] = !IsBlockPosEmpty(worldIn, targetPos.south());
    			nearbyOccupied[2] = !IsBlockPosEmpty(worldIn, targetPos.west());
    			nearbyOccupied[3] = !IsBlockPosEmpty(worldIn, targetPos.north());
    			
    			boolean putDown = false;
   
    			if (!isDownEmpty && isCenterEmpty) 
    			{
    				//check the four directions
    				for (int i = 0; i <= 3; i++)
    				{
    					if (nearbyOccupied[i] && nearbyOccupied[(i + 1)%4])
						{
    						//	At first I want to make the block imitate a nearby block
    						//but soon I found this will allow players to get ores indefinitely.
    						worldIn.setBlockState(targetPos, Blocks.DIRT.getDefaultState());
    						putDown = true;
    						break;
						}
    				}		
    			}
    			
    			targetPos = targetPos.up();
    			isDownEmpty = isCenterEmpty && !putDown ;
    			isCenterEmpty = IsBlockPosEmpty(worldIn, targetPos);
    		}
    		
    		//Debug usage, for showing where has been checked
    		//worldIn.setBlockState(targetPos.up(3), Blocks.GLASS.getDefaultState());
    		
    		//Idealland.LogWarning("onBlockActivated-------------------------");
        }
    	
        return false;
    }


}
