package com.somebody.idlframewok.network.protocols;

import com.somebody.idlframewok.enchantments.ModEnchantmentInit;
import com.somebody.idlframewok.entity.EntityProjectileBlade;
import com.somebody.idlframewok.entity.projectiles.EntityIdlProjectile;
import com.somebody.idlframewok.entity.projectiles.ProjectileArgs;
import com.somebody.idlframewok.util.EntityUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;


public class PacketMouseFire implements IMessage {

    public PacketMouseFire() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // these methods may also be of use for your code:
        // for Itemstacks - ByteBufUtils.writeItemStack()
        // for NBT tags ByteBufUtils.writeTag();
        // for Strings: ByteBufUtils.writeUTF8String();
    }

    static float FAM_DELTA = (float) (Math.PI / 12f);
    public static class Handler implements IMessageHandler<PacketMouseFire, IMessage> {
        public IMessage onMessage(final PacketMouseFire msg, final MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            player.getServerWorld().addScheduledTask(() -> {

                boolean isBurning = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FIRE_ASPECT, player) > 0;

                if (player.getCooledAttackStrength(0) > 0.99f ||
                        (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.SWEEPING, player) > 0 &&
                                player.getCooledAttackStrength(0) > 0.1f)) {
                    Vec3d look = player.getLookVec();
                    if (ModEnchantmentInit.BLADE_FAN.getLevelOnCreature(player) > 0) {
                        Vec3d lookTemp;

                        float yaw = player.rotationYaw;
                        float x = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
                        float z = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
                        Vec3d left = new Vec3d(-x, 0 ,z);

                        int radius = ModEnchantmentInit.BLADE_FAN.getLevelOnCreature(player);
                        for (int i = -radius; i <=radius; i++)
                        {
                            float angle = i * FAM_DELTA;
                            lookTemp = look.scale(MathHelper.cos(angle)).
                                    add(left.scale(MathHelper.sin(angle)));
                            //lookTemp = look.rotateYaw(i * FAM_DELTA);
                            EntityIdlProjectile projectile =
                                    new EntityProjectileBlade(player.world,
                                            new ProjectileArgs((float) EntityUtil.getAttack(player)).setTTL(TICK_PER_SECOND),
                                            player, lookTemp.x, lookTemp.y, lookTemp.z);
                            if (isBurning)
                            {
                                projectile.setFire(999);
                            }
                            player.world.spawnEntity(projectile);

                        }
                    }else if (ModEnchantmentInit.BLADE_BULLET.getLevelOnCreature(player) > 0) {
                        EntityIdlProjectile projectile =
                                new EntityProjectileBlade(player.world,
                                        new ProjectileArgs((float) EntityUtil.getAttack(player)),
                                        player, look.x, look.y, look.z);
                        if (isBurning)
                        {
                            projectile.setFire(999);
                        }
                        player.world.spawnEntity(projectile);
                    }
                }
            });
            return null;
        }
    }
}
