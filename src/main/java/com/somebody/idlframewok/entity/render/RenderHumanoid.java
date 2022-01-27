package com.somebody.idlframewok.entity.render;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHumanoid extends RenderBiped<EntityModUnit> {
    private ResourceLocation RES_LOC;
    private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation(  ":textures/entity/steve.png");

    public RenderHumanoid(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelPlayer(0f, false), 0.5F);
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
//            protected void initArmor()
//            {
//                this.modelLeggings = new ModelPlayer(0.5F, false);
//                this.modelArmor = new ModelPlayer(1.0F, false);
//            }
        };
        this.addLayer(layerbipedarmor);
        RES_LOC = DEFAULT_RES_LOC;
    }

    public RenderHumanoid(RenderManager renderManagerIn, String TexturePath)
    {
        this(renderManagerIn);
        RES_LOC = new ResourceLocation(Reference.MOD_ID + ":textures/entity/"+TexturePath+".png");
    }

    public ModelPlayer getMainModel()
    {
        return (ModelPlayer)super.getMainModel();
    }

    public void doRender(EntityModUnit entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        //if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderPlayerEvent.Pre(entity, this, partialTicks, x, y, z))) return;
        //if (this.renderManager.renderViewEntity == entity)
        {
            double d0 = y;

            if (entity.isSneaking())
            {
                d0 = y - 0.125D;
            }

            this.setModelVisibilities(entity);
            //GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
            super.doRender(entity, x, d0, z, entityYaw, partialTicks);
            //GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        }
        //net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderPlayerEvent.Post(entity, this, partialTicks, x, y, z));
    }

    private void setModelVisibilities(EntityLivingBase clientPlayer)
    {
        ModelPlayer modelplayer = this.getMainModel();

        ItemStack itemstack = clientPlayer.getHeldItemMainhand();
        ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
        modelplayer.setVisible(true);
        modelplayer.bipedHeadwear.showModel =     !clientPlayer.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty();
        modelplayer.bipedBodyWear.showModel =     !clientPlayer.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty();
        modelplayer.bipedLeftLegwear.showModel =  !clientPlayer.getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty();
        modelplayer.bipedRightLegwear.showModel = !clientPlayer.getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty();
        modelplayer.bipedLeftArmwear.showModel =  !clientPlayer.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty();
        modelplayer.bipedRightArmwear.showModel = !clientPlayer.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty();
        modelplayer.isSneak = clientPlayer.isSneaking();
        ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
        ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;

        if (!itemstack.isEmpty())
        {
            modelbiped$armpose = ModelBiped.ArmPose.ITEM;

            if (clientPlayer.getItemInUseCount() > 0)
            {
                EnumAction enumaction = itemstack.getItemUseAction();

                if (enumaction == EnumAction.BLOCK)
                {
                    modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
                }
                else if (enumaction == EnumAction.BOW)
                {
                    modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
                }
            }
        }

        if (!itemstack1.isEmpty())
        {
            modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;

            if (clientPlayer.getItemInUseCount() > 0)
            {
                EnumAction enumaction1 = itemstack1.getItemUseAction();

                if (enumaction1 == EnumAction.BLOCK)
                {
                    modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
                }
                // FORGE: fix MC-88356 allow offhand to use bow and arrow animation
                else if (enumaction1 == EnumAction.BOW)
                {
                    modelbiped$armpose1 = ModelBiped.ArmPose.BOW_AND_ARROW;
                }
            }
        }

        if (clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT)
        {
            modelplayer.rightArmPose = modelbiped$armpose;
            modelplayer.leftArmPose = modelbiped$armpose1;
        }
        else
        {
            modelplayer.rightArmPose = modelbiped$armpose1;
            modelplayer.leftArmPose = modelbiped$armpose;
        }

    }

//    public RenderMoroonHumanoid(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize) {
//        super(renderManagerIn, modelBipedIn, shadowSize);
//    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityModUnit entity)
    {
        return RES_LOC;
    }
}
