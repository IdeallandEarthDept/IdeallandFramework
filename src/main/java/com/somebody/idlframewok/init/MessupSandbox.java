package com.somebody.idlframewok.init;

import com.somebody.idlframewok.Idealland;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.WORLD_HEIGHT;
import static com.somebody.idlframewok.util.WorldGenUtil.PREVENT_CASCADE_FLAGS_2;

@Mod.EventBusSubscriber
public class MessupSandbox {

    static float someRandomValue = 0f;

    //ref: world gen fossil
    public static boolean generate(ResourceLocation res, World worldIn, BlockPos position, @Nullable PlacementSettings placementsettings, float integrity, boolean useMinorOffset, boolean useRandomRotation) {
        //stopUpdate = true;
        Random random = worldIn.getChunkFromBlockCoords(position).getRandomWithSeed(987234911L);
        MinecraftServer minecraftserver = worldIn.getMinecraftServer();

//        Rotation rotationIn = Rotation.NONE;
//        if (useRandomRotation)
//        {
//            rotationIn = WorldGenUtil.rotationIn[random.nextInt(WorldGenUtil.rotationIn.length)];
//        }

        TemplateManager templatemanager = worldIn.getSaveHandler().getStructureTemplateManager();
        Template template = templatemanager.get(minecraftserver, res);
        if (template == null) {
            Idealland.LogWarning("Trying to load Template that does not exist: %s", res);
//            stopUpdate = false;
            return false;
        }
        ChunkPos chunkpos = new ChunkPos(position);
//        StructureBoundingBox structureboundingbox = new StructureBoundingBox(chunkpos.getXStart(), 0, chunkpos.getZStart(), chunkpos.getXEnd(), WORLD_HEIGHT, chunkpos.getZEnd());
//        if (placementsettings == null) {
//            placementsettings = (new PlacementSettings()).setRotation(rotationIn).setBoundingBox(structureboundingbox).setRandom(random);
//        }
//        else {
//            placementsettings.setRotation(rotationIn).setBoundingBox(structureboundingbox).setRandom(random);
//        }
//        BlockPos blockpos = template.transformedSize(rotationIn);
        int x = 0;
        int z = 0;
        //lesser than 16 structurebig can have a little offset:
//        if (useMinorOffset)
//        {
//            if (blockpos.getX() < CHUNK_SIZE){
//                x = random.nextInt(16 - blockpos.getX());
//            }
//
//            if (blockpos.getZ() < CHUNK_SIZE)
//            {
//                z = random.nextInt(16 - blockpos.getZ());
//            }
//        }

        int y = WORLD_HEIGHT;

//        int coordMax = blockpos.getX();
//        for (int x1 = 0; x1 < coordMax; ++x1)
//        {
//            for (int z1 = 0; z1 < coordMax; ++z1)
//            {
//                y = Math.min(y, worldIn.getHeight(position.getX() + x1 + x, position.getZ() + z1 + z));
//            }
//        }

        //int k1 = Math.max(y - CHUNK_MAX - random.nextInt(10), 10);//random y delta. I don't need this
        BlockPos blockpos1 = template.getZeroPositionWithTransform(position.add(x, 0, z), Mirror.NONE, Rotation.NONE);
        placementsettings.setIntegrity(integrity);
        template.addBlocksToWorld(worldIn, blockpos1, placementsettings, PREVENT_CASCADE_FLAGS_2);
//        stopUpdate = false;
        return true;
    }


//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent
//    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
//    {
//        if (event.getEntityLiving() instanceof EntityPlayer)
//        {
//            EntityUtil.spawnCubeParticleAround(event.getEntityLiving(), InitParticles.type1, 1);
////            EntityUtil.spawnCubeParticleAround(event.getEntityLiving(), EnumParticleTypes.FLAME, 1);
//        }
//    }

//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent
//    public static void onToolTipColor(RenderTooltipEvent.Color event)
//    {
//        if (event.getStack().getItem() instanceof IWIP)
//        {
////            float r = (float) (0.5f + 0.5f * Math.sin(someRandomValue));
////            float g = (float) (0.5f + 0.5f * Math.sin(someRandomValue * 2));
////            float b = (float) (0.5f + 0.5f * Math.sin(someRandomValue * 3));
////
////            int rInt = (int) (r * 255);
////            int gInt = (int) (g * 255);
////            int bInt = (int) (b * 255);
////
////            int color = (rInt << 16) + (gInt << 8) + bInt;
////
////            // 0x AA RR GG BB
////            event.setBackground(color | 0xff000000);
//
//            event.setBackground(0xf0cc0000);
//            event.setBorderStart(0xf000cc00);
//            event.setBorderEnd(0xf00000cc);
//
//            someRandomValue += 0.1f;
//        }
//    }

//    @SubscribeEvent(priority = EventPriority.LOW)
//    public static void onCreatureHurt(LivingEvent.LivingUpdateEvent evt) {
//        if (evt.getEntityLiving() instanceof EntityPlayer) {
//            EntityLivingBase base = evt.getEntityLiving();
//
////            Idealland.Log("Motion=(%s,%s,%s)", base.motionX, base.motionY, base.motionZ);
//        }
//    }

//    @SideOnly(Side.CLIENT)
//    static ModFontRenderer fontRenderer;
//
//    @SideOnly(Side.CLIENT)
//    public static ModFontRenderer getFontRenderer() {
//        if (fontRenderer == null) {
//            Minecraft minecraft = Minecraft.getMinecraft();
//            fontRenderer = new ModFontRenderer(minecraft.gameSettings,
//                    new ResourceLocation("textures/font/ascii.png"),
//                    minecraft.renderEngine, false);
//        }
//        return fontRenderer;
//    }
}
