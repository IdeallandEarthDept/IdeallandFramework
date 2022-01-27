package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.item.IWIP;
import com.somebody.idlframewok.item.ItemBase;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Random;

public class ItemTorpedo extends ItemBase implements IWIP {
    public ItemTorpedo(String name) {
        super(name);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new BehaviorDefaultDispenseItem() {
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                EnumFacing enumfacing = (EnumFacing) source.getBlockState().getValue(BlockDispenser.FACING);
                IPosition iposition = BlockDispenser.getDispensePosition(source);
                double d0 = iposition.getX() + (double) ((float) enumfacing.getFrontOffsetX() * 0.3F);
                double d1 = iposition.getY() + (double) ((float) enumfacing.getFrontOffsetY() * 0.3F);
                double d2 = iposition.getZ() + (double) ((float) enumfacing.getFrontOffsetZ() * 0.3F);
                World world = source.getWorld();
                Random random = world.rand;
                double d3 = random.nextGaussian() * 0.05D + (double) enumfacing.getFrontOffsetX();
                double d4 = random.nextGaussian() * 0.05D + (double) enumfacing.getFrontOffsetY();
                double d5 = random.nextGaussian() * 0.05D + (double) enumfacing.getFrontOffsetZ();
                world.spawnEntity(new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5));
                stack.shrink(1);
                return stack;
            }

            /**
             * Play the dispense sound from the specified block.
             */
            protected void playDispenseSound(IBlockSource source) {
                source.getWorld().playEvent(1018, source.getBlockPos(), 0);
            }
        });
    }
}
