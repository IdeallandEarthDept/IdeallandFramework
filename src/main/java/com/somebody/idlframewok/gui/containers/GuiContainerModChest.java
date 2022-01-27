package com.somebody.idlframewok.gui.containers;

import com.somebody.idlframewok.blocks.container.ContainerModChest;
import com.somebody.idlframewok.blocks.tileEntity.TileEntityChestCustom;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiContainerModChest extends GuiContainer {
    //static final ResourceLocation GUI_CHEST = new ResourceLocation(Idealland.MODID, "textures/gui/custom_chest.png");
    static final ResourceLocation GUI_CHEST = new ResourceLocation("textures/gui/container/generic_54.png");
    //= new ResourceLocation("textures/gui/container/generic_54.png");
    final InventoryPlayer playerInventory;
    TileEntityChestCustom tileEntityChestCustom;

    private final int inventoryRows;

    public GuiContainerModChest(InventoryPlayer playerInventory, TileEntityChestCustom chestCustom, EntityPlayer player)
    {
        super(new ContainerModChest(playerInventory, chestCustom, player));
        this.playerInventory = playerInventory;
        tileEntityChestCustom = chestCustom;

        int i = 222;
        int j = 114;
        this.inventoryRows = tileEntityChestCustom.getSizeInventory() / 9;
        this.ySize = 114 + this.inventoryRows * 18;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(this.tileEntityChestCustom.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_CHEST);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }

//    @Override
//    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
//        this.fontRenderer.drawString(this.tileEntityChestCustom.getDisplayName().getUnformattedText(), 8, 6, 0x333333);
//        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 92, 0x333333);
//        //super.drawGuiContainerForegroundLayer(mouseX, mouseY);
//    }
//
//    @Override
//    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
//        GlStateManager.color(1f,1f,1f,1f);
//        mc.getTextureManager().bindTexture(GUI_CHEST);
//
//        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
//    }
}
