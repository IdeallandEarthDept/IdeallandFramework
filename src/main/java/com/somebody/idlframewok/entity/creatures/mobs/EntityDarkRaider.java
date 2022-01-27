package com.somebody.idlframewok.entity.creatures.mobs;

import com.somebody.idlframewok.entity.creatures.EntityGeneralMob;
import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityDarkRaider extends EntityGeneralMob {
    protected static final DataParameter<Byte> DARK_SHIELD = EntityDataManager.<Byte>createKey(EntityDarkRaider.class, DataSerializers.BYTE);
    public static final int PRIORITY_ATK_PLAYER = 2;

    EntityAITarget attackPlayer = new EntityAINearestAttackableTarget(this, EntityPlayer.class, true);

    public final byte MAX_SHIELD = 100;

    public EntityDarkRaider(World worldIn) {
        super(worldIn);
        attack_all_players = -1;
    }

    @Override
    protected void firstTickAI() {
        super.firstTickAI();
        if (CommonFunctions.isNightTime(world)) {
            targetTasks.addTask(PRIORITY_ATK_PLAYER, attackPlayer);
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(DARK_SHIELD, (byte) 0);
    }

    public float getShieldPecentage() {
        return (float) dataManager.get(DARK_SHIELD) / MAX_SHIELD;
    }

    @Override
    public void knockBack(Entity source, float strength, double xRatio, double zRatio) {
        super.knockBack(source, strength * (1 - getShieldPecentage()), xRatio, zRatio);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        byte shield = dataManager.get(DARK_SHIELD);
        if (CommonFunctions.isNightTime(getEntityWorld()) && !isBurning()) {
            //night-time, not burning
            if (shield < MAX_SHIELD) {
                if (world.getCurrentMoonPhaseFactor() < 0.1) {
                    shield = MAX_SHIELD;
                } else {
                    shield++;
                }
                dataManager.set(DARK_SHIELD, shield);
            } else {
                if (this.world.isRemote) {
                    emitParticles();
                }
            }

            if (!targetTasks.taskEntries.contains(attackPlayer)) {
                targetTasks.addTask(2, attackPlayer);
            }

        } else {
            //day-time or burning
            if (shield > 0) {
                shield--;
                dataManager.set(DARK_SHIELD, shield);
            }

            if (targetTasks.taskEntries.contains(attackPlayer)) {
                targetTasks.removeTask(attackPlayer);
            }
        }
    }

    private void emitParticles() {
        this.world.spawnParticle(EnumParticleTypes.REDSTONE,
                this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width,
                this.posY + this.rand.nextDouble() * (double) this.height - 0.25D,
                this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width,
                0.1f,
                0.1f,
                0.1f);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return super.attackEntityFrom(source, amount * (1 - getShieldPecentage()));
    }
}
