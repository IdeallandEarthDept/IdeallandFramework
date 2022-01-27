package com.somebody.idlframewok.entity;

import com.somebody.idlframewok.entity.creatures.mobs.skyland.EntityCatharVex;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHandler {

    public static void registerEntityRenders() {
//        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonUnitBase.class, renderManager -> new RenderHumanoid(renderManager, "moroon_humanoid"));

//        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonBombBeacon.class, RenderMoroonBeacon::new);

//        RenderingRegistry.registerEntityRenderingHandler(EntityExpOne.class, RenderExpOne::new);

        //RenderingRegistry.registerEntityRenderingHandler(EntityTurretPrototype.class, manager -> new RenderTurret(manager));

//        RenderingRegistry.registerEntityRenderingHandler(EntityCatharVex.class, renderManager -> new RenderModVex(renderManager,
//                new ResourceLocation(Reference.MOD_ID + ":textures/entity/cathar_vex.png"),
//                new ResourceLocation(Reference.MOD_ID + ":textures/entity/cathar_vex_charging.png")));
    }
}
