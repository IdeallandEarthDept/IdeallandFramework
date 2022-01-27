package com.somebody.idlframewok;

import com.somebody.idlframewok.client.keys.KeyboardManager;
import com.somebody.idlframewok.gui.ModGuiElementLoader;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.init.ModSpawn;
import com.somebody.idlframewok.init.RegistryHandler;
import com.somebody.idlframewok.network.NetworkHandler;
import com.somebody.idlframewok.recipe.ModRecipes;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.MetaUtil;
import com.somebody.idlframewok.util.Reference;
import com.somebody.idlframewok.util.proxy.ProxyBase;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

import static com.somebody.idlframewok.init.RegistryHandler.initRegistries;

@Mod(modid = Idealland.MODID, name = Idealland.NAME, version = Idealland.VERSION)//dependencies = "required-after:Forge@[14.23.5.2705,)"
public class Idealland
{
    ///tp @p ~1000 ~ ~
    public static final String MODID = Reference.MOD_ID;
    public static final String NAME = "Idealland";
    public static final String VERSION = "0.4.1";

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
        RegistryHandler.RegisterTileEntity();
        initRegistries(event);
        new ModGuiElementLoader();
        if (!proxy.isServer())
        {
            KeyboardManager.init();
        }
        NetworkHandler.init();

		Idealland.LogWarning("Idealland has finished its initializations");

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
            Idealland.Log("Hi my first formal creation, Nice to see you here.");
        }

        if (MetaUtil.isLoaded_GC)
        {
            Idealland.Log("With our help, this Galacti Craft will be easier... Hopefully.");
        }
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
