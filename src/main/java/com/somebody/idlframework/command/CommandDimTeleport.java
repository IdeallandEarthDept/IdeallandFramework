package com.somebody.idlframework.command;

import com.google.common.collect.Lists;
import com.somebody.idlframework.IdlFramework;
import com.somebody.idlframework.util.CommonFunctions;
import com.somebody.idlframework.util.Reference;
import com.somebody.idlframework.util.Teleport;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class CommandDimTeleport extends CommandBase {

    private final List<String> aliases = Lists.newArrayList(IdlFramework.MODID, "tp", "tpdim", "tpdimension", "chuansong");

    @Override
    public String getName() {
        return "tpdimension";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "tpdimension <id>";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }


    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            return;
        }

        String s = args[0];
        int dimensionID;

        try{
            dimensionID = Integer.parseInt(s);
        }catch (NumberFormatException e)
        {
            if (sender instanceof EntityPlayerMP)
            {
                CommonFunctions.SendMsgToPlayerStyled((EntityPlayerMP) sender, Reference.MOD_ID+".msg.dim_id_invalid", TextFormatting.RED, s);
            }
            return;
        }

        if (sender instanceof EntityPlayer)
        {
            try{
                Teleport.teleportToDim((EntityPlayer) sender, dimensionID, ((EntityPlayer) sender).posX, ((EntityPlayer) sender).posY, ((EntityPlayer) sender).posZ);
            }
            catch (IllegalArgumentException e)
            {
                CommonFunctions.SendMsgToPlayerStyled((EntityPlayerMP) sender, Reference.MOD_ID+".msg.dim_id_invalid", TextFormatting.RED, s);
            }
        }
    }
}
