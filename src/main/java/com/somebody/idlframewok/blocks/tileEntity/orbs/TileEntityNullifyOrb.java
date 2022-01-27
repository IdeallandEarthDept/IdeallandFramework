package com.somebody.idlframewok.blocks.tileEntity.orbs;

import com.somebody.idlframewok.Idealland;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Blocks;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class TileEntityNullifyOrb extends TileEntityOrbBase implements ITickable {

	@SubscribeEvent
	public void onSpawn(LivingSpawnEvent.CheckSpawn event) {
		if (world.isRemote)
		{
			return;
		}

		if (aabb == null)
		{
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			Idealland.LogWarning("A Nullify Orb @%s is not working correctly. Removing it.", pos);
			invalidate();
			return;
		}

		if(event.getResult() != Event.Result.DENY && event.getEntityLiving() instanceof IMob) {
			if (aabb.contains(new Vec3d(event.getX(), event.getY(), event.getZ())))
			{
				event.setResult(Event.Result.DENY);
				//Idealland.Log("Stopped spawning:"+event.getEntityLiving().getName());
				return;
			}
		}
	}
	
	static 
	{
		register("idlframewok:nullify_orb_basic", TileEntityNullifyOrb.class);
	}

}
