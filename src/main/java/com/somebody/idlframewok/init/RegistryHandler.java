package com.somebody.idlframewok.init;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.advancements.ModAdvancementsInit;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.blocks.tileEntity.TileEntityChestCustom;
import com.somebody.idlframewok.blocks.tileEntity.TileEntityInfoHolder;
import com.somebody.idlframewok.blocks.tileEntity.TileEntityTrafficLight;
import com.somebody.idlframewok.blocks.tileEntity.dungeon.TileEntityTrapTickBase;
import com.somebody.idlframewok.blocks.tileEntity.orbs.*;
import com.somebody.idlframewok.debug.command.CommandDimTeleport;
import com.somebody.idlframewok.designs.god16.Init16Gods;
import com.somebody.idlframewok.enchantments.ModEnchantmentInit;
import com.somebody.idlframewok.entity.ModEntityInit;
import com.somebody.idlframewok.entity.RenderHandler;
import com.somebody.idlframewok.entity.creatures.mobs.elemental.EntityEarthlin;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.IHasModel;
import com.somebody.idlframewok.util.sounds.ModSoundHandler;
import com.somebody.idlframewok.world.worldgen.InitWorldGen;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RegistryHandler {
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		Idealland.Log("Registering Items");
//		for (Item item : ModItems.ITEMS) {
//			Idealland.Log("registering : %s", item.getUnlocalizedName());
//			event.getRegistry().register(item);
//		}
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
		Idealland.proxy.registerTESR(event);
	}

	@SubscribeEvent
	public static void onEnchantmentRegister(RegistryEvent.Register<Enchantment> event)
	{
		ModEnchantmentInit.BeforeRegister();
		event.getRegistry().registerAll(ModEnchantmentInit.ENCHANTMENT_LIST.toArray(new Enchantment[0]));

//		for (Enchantment enchantment : Enchantment.REGISTRY) {
//			Idealland.Log("registered enchantments: %s", enchantment.getName());
//		}
	}

	public static void addToItems(Block block)
	{
		ModItems.ITEMS.add(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : ModItems.ITEMS)
		{
			if (item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : ModBlocks.BLOCKS)
		{
			if (block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}

		RenderHandler.registerEntityRenders();
	}

	public static void preInitRegistries(FMLPreInitializationEvent event)
	{
		InitBiome.registerBiomes();
		InitDimension.registerDimensions();
		ModEntityInit.registerEntities();

		InitWorldGen.registerWorldGen();
	}

	public static void postInitReg()
	{
		//WorldType TYPE_ONE = new WorldTypeOne();
		//WorldType TYPE_TWO = new WorldTypeCustom("TYPE_TWO");
		Init16Gods.init();
		EntityEarthlin.initEarthlinHead();

        Idealland.proxy.registerParticles();
	}

	public static void initRegistries(FMLInitializationEvent event)
	{
		ModLootList.initLootTables();
		ModLootList.initCrateList();
		ModSoundHandler.soundRegister();
		ModAdvancementsInit.initialization();
	}

	public static void serverRegistries(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandDimTeleport());
    }

    public static void RegisterTileEntity()
	{
		GameRegistry.registerTileEntity(TileEntityDeBoomOrb.class, new ResourceLocation(Idealland.MODID, "deboom_orb_basic"));
		GameRegistry.registerTileEntity(TileEntityNullifyOrb.class, new ResourceLocation(Idealland.MODID, "nullify_orb_basic"));
		GameRegistry.registerTileEntity(TileEntityNullifyOrbMor.class, new ResourceLocation(Idealland.MODID, "nullify_orb_mor"));
		GameRegistry.registerTileEntity(TileEntityEarthMender.class, new ResourceLocation(Idealland.MODID, "earth_mender_basic"));
		GameRegistry.registerTileEntity(TileEntityDeArrowOrb.class, new ResourceLocation(Idealland.MODID, "de_arrow_orb"));
		GameRegistry.registerTileEntity(TileEntityDeWaterOrb.class, new ResourceLocation(Idealland.MODID, "de_water_orb"));
		GameRegistry.registerTileEntity(TileEntityChestCustom.class, new ResourceLocation(Idealland.MODID, "chest_custom"));
		GameRegistry.registerTileEntity(TileEntityTrafficLight.class, new ResourceLocation(Idealland.MODID, "te_traffic_light"));
		GameRegistry.registerTileEntity(TileEntityTrapTickBase.class, new ResourceLocation(Idealland.MODID, "te_trap_tick_basic"));
		GameRegistry.registerTileEntity(TileEntityInfoHolder.class, new ResourceLocation(Idealland.MODID, "te_info_holder"));

		//GameRegistry.registerTileEntity(TileEntityBuilderFarm.class, new ResourceLocation(MODID, "builder_farm_basic"));
		//GameRegistry.registerTileEntity(TileEntityBuilderOne.class, new ResourceLocation(MODID, "builder.builder_one"));
	}
}
