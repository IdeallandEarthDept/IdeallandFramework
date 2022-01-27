package com.somebody.idlframewok.blocks.uprising.citadel;

import com.somebody.idlframewok.blocks.uprising.citadel.util.CitadelUtil;
import com.somebody.idlframewok.entity.creatures.buildings.uprising.EntityCitadelTurret;
import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockCitadelTurretBuilder extends BlockCitadelBase {
    public BlockCitadelTurretBuilder(String name, Material material) {
        super(name, material);
    }

    String MSG_BUILD_CITADEL_FAIL_OCCUPIED = "uprising.msg.citadel.fail.occupied";
    String MSG_BUILD_CITADEL_FAIL_LACK_POWER = "uprising.msg.citadel.fail.no_power";
    String MSG_BUILD_CITADEL_SUCCESS = "uprising.msg.citadel.success";

    @Override
    public void tryActivate(EntityPlayer player, World world, BlockPos pos) {
        if (world.isRemote) {
            return;
        }

        if (CitadelUtil.checkZoneState(world, pos) == CitadelUtil.EnumSingleZoneState.CLEAR) {
            EntityCitadelTurret livingBase = new EntityCitadelTurret(world);
            livingBase.setPosition(pos.getX() + 0.5f, pos.getY() + 3f, pos.getZ() + 0.5f);
            livingBase.
                    world.spawnEntity(livingBase);
            livingBase.initiateWarmupProcess();

            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GREEN, player, MSG_BUILD_CITADEL_SUCCESS);
        } else {
            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, player, MSG_BUILD_CITADEL_FAIL_LACK_POWER);
        }
    }
}
