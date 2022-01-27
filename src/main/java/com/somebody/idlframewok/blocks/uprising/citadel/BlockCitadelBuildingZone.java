package com.somebody.idlframewok.blocks.uprising.citadel;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCitadelBuildingZone extends BlockCitadelBase {
    public BlockCitadelBuildingZone(String name, Material material) {
        super(name, material);
    }

    @Override
    public void tryActivate(EntityPlayer player, World world, BlockPos pos) {
        //todo: try to build something here
    }
}
