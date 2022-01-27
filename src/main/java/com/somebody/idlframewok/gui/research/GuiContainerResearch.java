package com.somebody.idlframewok.gui.research;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.designs.research.IDLResearchNode;
import com.somebody.idlframewok.designs.research.IDLResearchTree;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiContainerResearch extends GuiContainer {
    private static final String TEXTURE_PATH =  "textures/gui/container/gui_research.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(Idealland.MODID, TEXTURE_PATH);

    private Slot mainSlot;

    private static final int BUTTON_CHILD_L = 0;
    private static final int BUTTON_CHILD_R = 1;
    private static final int BUTTON_PARENT = 2;
    private static final int BUTTON_RESEARCH = 3;

    IDLResearchNode curNode;

    public GuiContainerResearch(ContainerResearch inventorySlotsIn)
    {
        super(inventorySlotsIn);
        this.xSize = 176;
        this.ySize = 133;
        this.mainSlot = inventorySlotsIn.curItem;
        curNode = IDLResearchTree.getInstance().getNodeFromID(0,0);
        //refreshItem();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        //this.drawVerticalLine(30, 19, 36, 0xFF000000);
        //this.drawHorizontalLine(8, 167, 43, 0xFF000000);

        //String title = I18n.format("container.fmltutor.demo");
        //this.fontRenderer.drawString(title, (this.xSize - this.fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);

        //ItemStack item = new ItemStack(ModItems.P_2_W_GOBLET);
        //this.itemRender.renderItemAndEffectIntoGUI(item, 8, 20);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        IDLResearchNode nextNode;

        switch (button.id)
        {
            case BUTTON_CHILD_L:
                ((ContainerResearch)inventorySlots).tryMoveToChild(0);
                break;
            case BUTTON_CHILD_R:
                ((ContainerResearch)inventorySlots).tryMoveToChild(1);
                break;
            case BUTTON_PARENT:
                ((ContainerResearch)inventorySlots).tryMoveToParent();
                break;
            default:
                super.actionPerformed(button);
                return;
        }


    }

    //L Child 44,5
    //R Child 117 5

    //Start 61,53

    //parent 81 121

    @Override
    public void initGui()
    {
        super.initGui();
        addButtonDefault(BUTTON_CHILD_L, 44,5);
        addButtonDefault(BUTTON_CHILD_R, 117,5);
        addButtonDefault(BUTTON_RESEARCH, 61,53);
        addButtonDefault(BUTTON_PARENT, 81,121);
    }

    public void addButtonDefault(int id, int x, int y)
    {
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(id, x + offsetX,  y + offsetY, 15, 10, "")
        {
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
            {
                if (this.visible)
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);

                    mc.getTextureManager().bindTexture(TEXTURE);
                    int x = mouseX - this.x, y = mouseY - this.y;

                    if (x >= 0 && y >= 0 && x < this.width && y < this.height)
                    {
                        this.drawTexturedModalRect(this.x , this.y, 1, 146, this.width, this.height);
                    }
                    else
                    {
                        this.drawTexturedModalRect(this.x, this.y, 1, 134, this.width, this.height);
                    }
                }
            }
        });
    }

}
