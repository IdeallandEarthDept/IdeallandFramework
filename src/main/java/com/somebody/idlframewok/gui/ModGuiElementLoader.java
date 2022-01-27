package com.somebody.idlframewok.gui;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.container.ContainerModChest;
import com.somebody.idlframewok.blocks.tileEntity.TileEntityChestCustom;
import com.somebody.idlframewok.gui.containers.GuiContainerModChest;
import com.somebody.idlframewok.gui.expOne.ContainerDemo;
import com.somebody.idlframewok.gui.expOne.GuiContainerDemo;
import com.somebody.idlframewok.gui.research.ContainerResearch;
import com.somebody.idlframewok.gui.research.GuiContainerResearch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

//todo
//https://fmltutor.ustc-zzzz.net/3.4.3-GUI%E7%95%8C%E9%9D%A2%E4%B8%AD%E7%9A%84%E4%BA%A4%E4%BA%92.html
public class ModGuiElementLoader implements IGuiHandler {

    public static final int GUI_DEMO = 1;
    public static final int GUI_RESEARCH = 2;
    public static final int GUI_CUSTOM_CHEST = 3;

    public ModGuiElementLoader()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(Idealland.instance, this);
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID)
        {
            case GUI_DEMO:
                return new ContainerDemo(player);
            case GUI_RESEARCH:
                return new ContainerResearch(player);
            case GUI_CUSTOM_CHEST:
                return new ContainerModChest(player.inventory, (TileEntityChestCustom) world.getTileEntity(new BlockPos(x,y,z)), player);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID)
        {
            case GUI_DEMO:
                return new GuiContainerDemo(new ContainerDemo(player));
            case GUI_RESEARCH:
                return new GuiContainerResearch(new ContainerResearch(player));
            case GUI_CUSTOM_CHEST:
                return new GuiContainerModChest(player.inventory, (TileEntityChestCustom) world.getTileEntity(new BlockPos(x,y,z)), player);
            default:
                return null;
        }
    }
}
