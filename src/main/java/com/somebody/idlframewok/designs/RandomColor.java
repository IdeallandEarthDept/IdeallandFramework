package com.somebody.idlframewok.designs;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.util.Color16Def;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;

import javax.vecmath.Color3f;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class RandomColor {

    //    @SideOnly(Side.CLIENT)
//    @SubscribeEvent
    public static void preRender(RenderLivingEvent.Pre event) {
        EntityLivingBase livingBase = event.getEntity();
        if (livingBase instanceof EntityPlayer || livingBase instanceof EntitySheep) {
            return;
        }

//        randomColorForEntity(livingBase);
    }

    public static void randomColorForEntity(EntityLivingBase livingBase) {
        UUID uuid = livingBase.getUniqueID();
        long numeric = uuid.getLeastSignificantBits();
//        long modifier = 256;
//        float modifierF = 256f;
//        GlStateManager.color(
//                (float) ((numeric>>8+numeric) % modifier) / modifierF,
//                (float)((numeric>>16+numeric>>24) % modifier) / modifierF,
//                (float)((numeric>>32+numeric>>40) % modifier) / modifierF
//        );

        int index = (int) (numeric % Color16Def.Color3f_16.length);
        index = index < 0 ? -index : index;
        Color3f color3f = Color16Def.Color3f_16[index];
        GlStateManager.color(color3f.x, color3f.y, color3f.z);

        //Idealland.Log("%d, color = %s", index, color3f);

//        float base = ((numeric % 2) == 0) ? 0.7f : 0.2f;
//        float plus = 0.15f;
//        long reminder = numeric % 3;
//        long reminder2 = (numeric >> 4) % 3;
//
//        Idealland.Log("%s,%s",reminder, reminder2);
//
//        GlStateManager.color(
//                base + (reminder == 0 ? plus : 0) + (reminder2 == 0 ? plus : 0),
//                base + ((-reminder == 1) ? plus : 0) + ((-reminder2 == 1) ? plus : 0),
//                base + ((-reminder == 2) ? plus : 0) + ((-reminder2 == 2) ? plus : 0)
//        );

    }


}
