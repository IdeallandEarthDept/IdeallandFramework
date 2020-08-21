package com.deeplake.idealland;

import com.deeplake.idealland.blocks.tileEntity.orbs.*;
import com.deeplake.idealland.gui.ModGuiElementLoader;
import com.deeplake.idealland.init.ModSpawn;
import com.deeplake.idealland.meta.MetaUtil;
import com.deeplake.idealland.research.IDLResearchTree;
import com.deeplake.idealland.util.CommonDef;
import com.deeplake.idealland.init.ModConfig;
import com.deeplake.idealland.init.RegistryHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.apache.logging.log4j.Logger;

import com.deeplake.idealland.init.ModRecipes;
import com.deeplake.idealland.proxy.ProxyBase;
import com.deeplake.idealland.util.Reference;

import static com.deeplake.idealland.init.RegistryHandler.initRegistries;

//To let the player be a traveling god who plays yin-yang magic.

@Mod(modid = Idealland.MODID, name = Idealland.NAME, version = Idealland.VERSION)//dependencies = "required-after:Forge@[14.23.5.2705,)"
public class Idealland
{
    public static final String MODID = "idealland";
    public static final String NAME = "Idealland";
    public static final String VERSION = "0.1.020";

    public static Logger logger;
    
    public static final boolean SHOW_WARN = true;

    @Mod.Instance
    public static Idealland instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static ProxyBase proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        RegistryHandler.preInitRegistries(event);

    }

    @EventHandler
	public static void Init(FMLInitializationEvent event)
	{
		ModRecipes.Init();
        RegisterTileEntity();
        initRegistries(event);
        new ModGuiElementLoader();
		Idealland.LogWarning("Init Idealland");
	}

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // Moved Spawning registry to last since forge doesn't auto-generate sub
        // "M' biomes until late
        if (ModConfig.SPAWN_CONF.SPAWN) {
            ModSpawn.registerSpawnList();
        }

        MetaUtil.isIRRLoaded = Loader.isModLoaded("itemrender");
        MetaUtil.isLoaded_TiC = Loader.isModLoaded("tconstruct");
        MetaUtil.isLoaded_Slashblade = Loader.isModLoaded("flammpfeil.slashblade");
        MetaUtil.isLoaded_Botania = Loader.isModLoaded("botania");
        MetaUtil.isLoaded_DWeapon = Loader.isModLoaded("dweapon");
        MetaUtil.isLoaded_AOA3 = Loader.isModLoaded(CommonDef.MOD_NAME_AOA3);
        MetaUtil.isLoaded_GC = Loader.isModLoaded("galacticraftcore");
        MetaUtil.isLoaded_Taoism = Loader.isModLoaded("taoism");
        MetaUtil.isLoaded_GOG = Loader.isModLoaded(CommonDef.MOD_NAME_GOG);

        TrashTalking();

        IDLResearchTree.InitTree();

        RegistryHandler.postInitReg();
    }

    @EventHandler
    public static void serverInit(FMLServerStartingEvent event)
    {
        RegistryHandler.serverRegistries(event);
    }


    private void TrashTalking()
    {
        if (MetaUtil.isIRRLoaded)
        {
            Idealland.Log("Item Renderer? Consider contributing to MCMod-dot-cn.");
        }
        else {
            //Idealland.Log("No Item Renderer found.");
        }

        if (MetaUtil.isLoaded_TiC)
        {
            Idealland.Log("Oh hi TiC.");
        }

        if (MetaUtil.isLoaded_Slashblade)
        {
            Idealland.Log("Oh, SlashB. Do you want a cup of customized nano mender?");
        }

        if (MetaUtil.isLoaded_Botania)
        {
            Idealland.Log("If that isn't Vazkii!");
        }

        if (MetaUtil.isLoaded_Taoism)
        {
            Idealland.Log("Hi Taoism, this is Taoism Deeplake speaking.");
        }

        if (MetaUtil.isLoaded_GOG || MetaUtil.isLoaded_AOA3)
        {
            Idealland.Log("Tons of monsters detected.");
        }

        if (MetaUtil.isLoaded_AOA3)
        {
            Idealland.Log("Gods from AOA3 detected.");
        }

        if (MetaUtil.isLoaded_DWeapon)
        {
            Idealland.Log("Hi my other creation, Nice to see you here.");
        }

        if (MetaUtil.isLoaded_GC)
        {
            Idealland.Log("With our help, this Galacti Craft will be easier... Hopefully.");
        }
    }

	private static void RegisterTileEntity()
    {
        GameRegistry.registerTileEntity(TileEntityDeBoomOrb.class, new ResourceLocation(MODID, "deboom_orb_basic"));
        GameRegistry.registerTileEntity(TileEntityNullifyOrb.class, new ResourceLocation(MODID, "nullify_orb_basic"));
        GameRegistry.registerTileEntity(TileEntityNullifyOrbMor.class, new ResourceLocation(MODID, "nullify_orb_mor"));
        GameRegistry.registerTileEntity(TileEntityEarthMender.class, new ResourceLocation(MODID, "earth_mender_basic"));
        GameRegistry.registerTileEntity(TileEntityDeArrowOrb.class, new ResourceLocation(MODID, "de_arrow_orb"));
        GameRegistry.registerTileEntity(TileEntityDeWaterOrb.class, new ResourceLocation(MODID, "de_water_orb"));

        //GameRegistry.registerTileEntity(TileEntityBuilderFarm.class, new ResourceLocation(MODID, "builder_farm_basic"));
        //GameRegistry.registerTileEntity(TileEntityBuilderOne.class, new ResourceLocation(MODID, "builder.builder_one"));
    }

    public static void LogWarning(String str, Object...args)
    {
        if (SHOW_WARN)
        {
            logger.warn(String.format(str, args));
        }
    }

    public static void LogWarning(String str)
    {
    	if (SHOW_WARN) 
    	{
    		logger.warn(str);
    	}
    }

    public static void Log(String str)
    {
        if (ModConfig.GeneralConf.LOG_ON)
        {
            logger.info(str);
        }
    }

    public static void Log(String str, Object...args)
    {
        if (ModConfig.GeneralConf.LOG_ON)
        {
            logger.info(String.format(str, args));
        }
    }
//    @EventHandler
//    public void init(FMLInitializationEvent event)
//    {
//    	ModRecipes.Init();
//    	Idealland.logger.warn("Init B");
//        // some example code
//        //logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
//    }
}
//https://minecraft-zh.gamepedia.com/%E8%BF%9B%E5%BA%A6/JSON%E6%A0%BC%E5%BC%8F
