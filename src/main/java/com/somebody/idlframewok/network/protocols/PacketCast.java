package com.somebody.idlframewok.network.protocols;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.skills.ItemSkillBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class PacketCast implements IMessage {

    int testVal;

    public PacketCast() {
    }


    public PacketCast(int testVal) {
        this.testVal = testVal;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        testVal = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(testVal);
        // these methods may also be of use for your code:
        // for Itemstacks - ByteBufUtils.writeItemStack()
        // for NBT tags ByteBufUtils.writeTag();
        // for Strings: ByteBufUtils.writeUTF8String();

    }

    public static class Handler implements IMessageHandler<PacketCast, IMessage> {
        public IMessage onMessage(final PacketCast msg, final MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            player.getServerWorld().addScheduledTask(() -> {
                Idealland.Log("Packet:%d", msg.testVal);

                EntityEquipmentSlot slot = EntityEquipmentSlot.values()[msg.testVal];

                ItemStack item = player.getItemStackFromSlot(slot);
                if(item.isEmpty())
                {
                    Idealland.LogWarning("Trying to cast an empty item");
                }

                if(item.getItem() instanceof ItemSkillBase)
                {
                    ItemSkillBase skill = (ItemSkillBase) item.getItem();
                    if (skill.canCast(player.world, player, item, slot))
                    {
                        skill.applyCast(player.world, player, item, slot );
                    }
                }
            });
            return null;
        }
    }
}
