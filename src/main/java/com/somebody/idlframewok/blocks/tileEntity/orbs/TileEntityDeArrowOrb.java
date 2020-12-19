package com.somebody.idlframewok.blocks.tileEntity.orbs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

import java.util.List;

//"Tonation Orb" de-detonation orb
public class TileEntityDeArrowOrb extends TileEntityOrbBase implements ITickable {

	@Override
	public void update() {
		super.update();
		disarmProjectiles();
	}

	private void disarmProjectiles()
	{
		if (world.isRemote) {
			return;
		}

		List<Entity> entities = world.getEntitiesWithinAABB(Entity.class,
				aabb);
		for (Entity entity : entities) {
			HandleProjectile(entity);
		}
	}

	private void HandleProjectile(Entity projectile)
	{
		if (projectile.isDead)
		{
			return;
		}

		if (projectile instanceof IProjectile ||
				projectile instanceof EntityFireball ||
				projectile instanceof EntityShulkerBullet) {
			ItemStack result = GetCorrespondingStack(projectile);
			if (result != null) {
				projectile.entityDropItem(result, 0.1F);

			}
			projectile.setDead();
		}
	}

	//Tried to get the arrow from the entity.
	//there is an implemented method, but it's protected.
	//The only way I can think of to support light arrow and tipped arrows are rewriting them from nbt.
	private ItemStack GetArrowStack(EntityArrow arrow) {
		return new ItemStack(Items.ARROW);
	}

	private ItemStack GetCorrespondingStack(Entity projectile)
	{
		if (projectile instanceof IProjectile) {
			if (projectile instanceof EntityArrow) {
				EntityArrow arrow = (EntityArrow) projectile;
				if (arrow.pickupStatus == EntityArrow.PickupStatus.ALLOWED)
				{
					return GetArrowStack(arrow);
				}
				else {
					return null;
				}
			}
		} else if (projectile instanceof EntityFireball)
		{
			return new ItemStack(Items.FIRE_CHARGE);
		}else if  (projectile instanceof EntityTNTPrimed) {
			return new ItemStack(Blocks.TNT.getItemDropped(Blocks.TNT.getDefaultState(), null, 0));
		}
		return ItemStack.EMPTY;
	}

	static
	{
		register("idlframewok:de_arrow_orb", TileEntityDeArrowOrb.class);
	}
}
