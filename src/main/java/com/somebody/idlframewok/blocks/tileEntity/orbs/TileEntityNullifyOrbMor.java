package com.somebody.idlframewok.blocks.tileEntity.orbs;

import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class TileEntityNullifyOrbMor extends TileEntityNullifyOrb implements ITickable {

	protected int range = 15;

	void Init()
	{
		super.Init();
		SetRange(range);
	}

	@SubscribeEvent
	public void onSpawn(LivingSpawnEvent.CheckSpawn event) {
		if(event.getResult() != Event.Result.DENY &&
				(event.getEntityLiving() instanceof IMob ||  EntityUtil.isMoroonTeam(event.getEntityLiving()))
		) {
			if (aabb.contains(new Vec3d(event.getX(), event.getY(), event.getZ())))
			{
				event.setResult(Event.Result.DENY);
				//IdlFramework.Log("Stopped spawning:"+event.getEntityLiving().getName());
				return;
			}
		}
	}
	
	static 
	{
		register("idlframewok:nullify_orb_mor", TileEntityNullifyOrbMor.class);
	}

}
