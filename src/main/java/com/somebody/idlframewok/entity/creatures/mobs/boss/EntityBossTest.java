package com.somebody.idlframewok.entity.creatures.mobs.boss;

import com.somebody.idlframewok.entity.projectiles.casting.EntityDelayBoomCast;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class EntityBossTest extends EntityBossBase {
    public EntityBossTest(World worldIn) {
        super(worldIn);
    }

    private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        setAttr(32, 0.4, 6.0, 2, 16);
    }

    int actionTick = 0;

    int actionCycle = 10 * TICK_PER_SECOND;
    final int trigger1 = 6 * TICK_PER_SECOND;
    final int trigger2 = 7 * TICK_PER_SECOND;
    final int trigger3 = 8 * TICK_PER_SECOND;
    final int trigger4 = 9 * TICK_PER_SECOND;

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!world.isRemote && getAttackTarget() != null) {
            EntityLivingBase target = getAttackTarget();
            switch (actionTick) {
                case trigger1:
                case trigger2:
                case trigger3:
                case trigger4:
                    EntityDelayBoomCast boomCast = new EntityDelayBoomCast(world, 3f);
                    boomCast.setPosition(target.posX, target.posY, target.posZ);
                    world.spawnEntity(boomCast);
                    world.playSound(target.posX, target.posY, target.posZ, SoundEvents.BLOCK_LAVA_EXTINGUISH, null, 1f, 1.3f, false);
                    break;
            }

            actionTick++;
            actionTick %= actionCycle;
        }
    }
}
