package com.somebody.idlframewok.blocks;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

//    public static final BlockNullifyOrb NULLIFY_ORB = new BlockNullifyOrb("nullify_orb", Material.IRON);
//    public static final BlockNullifyOrb NULLIFY_ORB_MOR = new BlockNullifyOrb("nullify_orb_mor", Material.IRON).setAdvanced(true);
//    public static final BlockDeboomOrb DEBOOM_ORB = new BlockDeboomOrb("de_boom_orb", Material.IRON);
//    public static final BlockEarthMender EARTH_MENDER = new BlockEarthMender("earth_mender_basic", Material.IRON);
//    public static final BlockGeneralOrb DE_ARROW_ORB = new BlockGeneralOrb("de_arrow_orb", Material.IRON, TileEntityDeArrowOrb.class);
//    public static final BlockGeneralOrb DE_WATER_ORB = new BlockGeneralOrb("de_water_orb", Material.IRON, TileEntityDeWaterOrb.class);
//
//    public static final BlockDungeonCustomizedTrap BASE_TICK_TRAP = new BlockDungeonCustomizedTrap("base_tick_trap", Material.ROCK);
//
//
//    public static final IdeallandLight IDEALLAND_LIGHT_BASIC = new IdeallandLight("idealland_light_basic", Material.IRON);
//    public static final BlockExtractionDoorTest EXTRACTION_DOOR = new BlockExtractionDoorTest("idealland_extraction_door", Material.IRON);
//
//    public static final Block GRID_BLOCK_1 = new BlockBase("test", Material.CLAY).setCreativeTab(ModCreativeTabsList.IDL_BUILDING).setHardness(15f);
//    public static final Block GRID_BLOCK_2 = new BlockBase("grid_dark_2", Material.CLAY).setCreativeTab(ModCreativeTabsList.IDL_BUILDING).setHardness(15f);
//    public static final Block GRID_LAMP = new BlockBase("grid_lamp", Material.CLAY).setCreativeTab(ModCreativeTabsList.IDL_BUILDING).setHardness(15f).setLightLevel(1f);
//    public static final Block GRID_NORMAL = new BlockBase("grid_normal", Material.CLAY).setCreativeTab(ModCreativeTabsList.IDL_BUILDING).setHardness(15f);
//
//    public static final Block CONSTRUCTION_SITE = new BlockBase("construction_site", Material.GROUND).setCreativeTab(ModCreativeTabsList.IDL_BUILDING).setHardness(15f);
//    public static final Block IDL_GLASS = new ModBlockGlassBase("idl_glass", Material.GLASS).setCreativeTab(ModCreativeTabsList.IDL_BUILDING).setHardness(1f).setLightOpacity(0).setLightLevel(1f);
//
//    public static final Block IRON_PLANKS = new BlockBase("iron_planks", Material.IRON).setHardness(5.0F).setResistance(5.0F);
//
//    public static final BlockBuilderOne BUILDER_ONE = new BlockBuilderOne("builder_one", Material.CLAY);
//    public static final BlockBuilderHouse BUILDER_HOUSE = new BlockBuilderHouse("builder_house", Material.CLAY);
//    public static final BlockBuilderBase BUILDER_FARM = new BlockBuilderBase("builder_farm", Material.CLAY, TileEntityBuilderFarm.class);
//    public static final BlockBuilderBase BUILDER_FARM_SIN = new BlockBuilderBase("builder_farm_sin", Material.CLAY, TileEntityBuilderFarmSIN.class);
//
//    public static final BlockBrickPlacer BRICK_FENCE = new BlockBrickPlacer("fence_brick", Material.CLAY, Blocks.BRICK_BLOCK);
//    //public static final BlockPhasingOre BUFF_BLOCK_1 = new BuffBlock("buff_block_1", Material.IRON);
//
////	public static final DivineOre DIVINE_ORE = new DivineOre("divine_ore", Material.ROCK);
////	public static final PureOre PURE_ORE = new PureOre("pure_ore", Material.ROCK);
//    //public static final BlockEarthMender EARTH_MENDER = new BlockEarthMender("earth_mender_basic", Material.ROCK);
//
//    public static final BlockMoroonBase MORON_BLOCK = new BlockMoroonBase("moroon_block", Material.GROUND);
//    //public static final BlockBase TRASH_CAN_1 = new BlockTrashCanBase("trash_can_1", Material.GROUND);
//
//    public static final Block STAR_BLOCK = new ModBlockNightSky("star_block", Material.GLASS).setHarvestThis("pickaxe", 3).setSolid(true).setCreativeTab(ModCreativeTabsList.IDL_WORLD).setHardness(1f).setLightOpacity(0).setLightLevel(0.8f);
//
//    public static final Block SKY_STARRY_BLOCK = new ModBlockNightSky("night_sky_block", Material.AIR).setSolid(false).setCreativeTab(ModCreativeTabsList.IDL_WORLD).setHardness(1f).setLightOpacity(0).setLightLevel(0.3f);
//    public static final Block SKY_NIGHT_BLOCK = new ModBlockNightSky("night_sky_block_pure", Material.AIR).setSolid(false).setCreativeTab(ModCreativeTabsList.IDL_WORLD).setHardness(1f).setLightOpacity(0);
//
//    public static final Block SKYLAND_OOW_RUNESTONE = new BlockOOWProtectRuneStone("skyland_oow_runestone", Material.GROUND).setCreativeTab(ModCreativeTabsList.IDL_WORLD).setHardness(1f).setBlockUnbreakable().setLightLevel(1f);
//    public static final Block SKYLAND_FALL_RUNESTONE = new BlockFallProtectRuneStone("skyland_fall_runestone", Material.GROUND).setCreativeTab(ModCreativeTabsList.IDL_WORLD).setHardness(1f).setBlockUnbreakable().setLightLevel(1f);
//    public static final Block SKYLAND_BLANK_RUNESTONE = new BlockBase("skyland_dull_runestone", Material.GROUND).setCreativeTab(ModCreativeTabsList.IDL_WORLD).setHardness(1f).setBlockUnbreakable();
//
//    public static final Block PANTHEON_CHECKER = new BlockPantheon("block_checker_16", Material.GROUND).setCreativeTab(ModCreativeTabsList.IDL_WORLD).setBlockUnbreakable();
//    public static final Block PANTHEON_LOOP = new BlockPantheon("block_loop_16", Material.GROUND).setCreativeTab(ModCreativeTabsList.IDL_WORLD).setBlockUnbreakable();
//    public static final Block PANTHEON_VORTEX = new BlockPantheon("block_loop_16_b", Material.GROUND).setCreativeTab(ModCreativeTabsList.IDL_WORLD).setBlockUnbreakable();
//
//    public static final Block SKYLAND_PEBBLE = new BlockSkylandPebble("skyland_pebble", Material.GROUND).setPickable(true).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//
//    public static final BlockCrate CRATE = (BlockCrate) new BlockCrate("crate", Material.WOOD).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//
//    public static final Block[] SKYLAND_GOD_RUNSTONES = new BlockBase[]{
//            new BlockGodRuneStone("skyland_runestone_" + 0, Material.GROUND, 0),
//            new BlockGodRuneStone("skyland_runestone_" + 1, Material.GROUND, 1),
//            new BlockGodRuneStone("skyland_runestone_" + 2, Material.GROUND, 2),
//            new BlockGodRuneStone("skyland_runestone_" + 3, Material.GROUND, 3),
//            new BlockGodRuneStone("skyland_runestone_" + 4, Material.GROUND, 4),
//            new BlockGodRuneStone("skyland_runestone_" + 5, Material.GROUND, 5),
//            new BlockGodRuneStone("skyland_runestone_" + 6, Material.GROUND, 6),
//            new BlockGodRuneStone("skyland_runestone_" + 7, Material.GROUND, 7),
//            new BlockGodRuneStone("skyland_runestone_" + 8, Material.GROUND, 8),
//            new BlockGodRuneStone("skyland_runestone_" + 9, Material.GROUND, 9),
//            new BlockGodRuneStone("skyland_runestone_" + 10, Material.GROUND, 10),
//            new BlockGodRuneStone("skyland_runestone_" + 11, Material.GROUND, 11),
//            new BlockGodRuneStone("skyland_runestone_" + 12, Material.GROUND, 12),
//            new BlockGodRuneStone("skyland_runestone_" + 13, Material.GROUND, 13),
//            new BlockGodRuneStone("skyland_runestone_" + 14, Material.GROUND, 14),
//            new BlockGodRuneStone("skyland_runestone_" + 15, Material.GROUND, 15),
//    };
//
//    public static final Block TORCH_RUNE = new BlockTorchCreateRuneStone("torch_rune", Material.GROUND).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//    public static final Block REVIVE_STONE = new BlockReviveStone("revive_rune", Material.GROUND).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//
//    public static final Block RANDOM_TP = new BlockToRandom("random_tp", Material.GROUND).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//    public static final Block TELEPORTER_DIM_OW = new BlockTeleporter("teleporter_dim_ow", Material.GROUND, DimensionType.OVERWORLD.getId()).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//    public static final Block TELEPORTER_DIM_CUBIX = new BlockTeleporter("teleporter_dim_cubix", Material.GROUND, ModConfig.WORLD_GEN_CONF.DIM_TWO_ID).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//    public static final Block TELEPORTER_DIM_C16 = new BlockTeleporter("teleporter_dim_c16", Material.GROUND, ModConfig.WORLD_GEN_CONF.DIM_UNIV_ID).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//
//    public static final Block CLIMB_PILLAR = new BlockClimbPillar("climb_pillar", Material.WOOD).setCreativeTab(ModCreativeTabsList.IDL_WORLD).setHardness(0.2F);
//
//    public static final BlockFlesh FLESH_BLOCK_0 = (BlockFlesh) new BlockFlesh("flesh_block_0", Material.CLAY).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//    public static final BlockFlesh FLESH_BLOCK_SCAR = (BlockFlesh) new BlockFlesh("flesh_block_1", Material.CLAY).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//    public static final BlockFlesh FLESH_BLOCK_BRAIN = (BlockFlesh) new BlockFlesh("flesh_block_brain", Material.CLAY).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//    public static final BlockFleshEye FLESH_BLOCK_EYE = (BlockFleshEye) new BlockFleshEye("flesh_block_eye", Material.CLAY).setCreativeTab(ModCreativeTabsList.IDL_WORLD).setLightLevel(0.2f);
//    public static final BlockFleshMouth FLESH_BLOCK_MOUTH = (BlockFleshMouth) new BlockFleshMouth("flesh_block_mouth", Material.CLAY).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//
//    public static final Block JADE_ORE = new BlockBase("jade_ore", Material.ROCK).setHarvestThis(IDLNBTDef.TOOL_PICKAXE, 2).setCreativeTab(ModCreativeTabsList.IDL_WORLD).setHardness(1f).setLightOpacity(0);
//    public static final BlockFading FADE_PLANKS = (BlockFading) new BlockFading("fade_planks", Material.WOOD).setHarvestThis("axe", 0).setCreativeTab(ModCreativeTabsList.IDL_WORLD).setHardness(0.3f).setLightOpacity(0);
//
//    public static final BlockLockBase LOCK_ADJ_OFF = new BlockLockAdjacent("adj_lock_off", Material.IRON, false);
//    public static final BlockLockBase LOCK_ADJ_ON = new BlockLockAdjacent("adj_lock_on", Material.IRON, true).setAlternative(LOCK_ADJ_OFF);
//    public static final BlockLockBase LOCK_ADJ_PASSIVE = new BlockLockAdjacentPassive("adj_lock_passive", Material.IRON);
//
//    public static final Block DUNGEON_WALL_MOSS = new BlockDungeonWall("dungeon_brick_0", Material.ROCK);
//    public static final Block DUNGEON_WALL = new BlockDungeonWall("dungeon_brick_1", Material.ROCK);
//    public static final Block DUNGEON_WALL_CRACKED = new BlockDungeonWall("dungeon_brick_2", Material.ROCK);
//
//    public static final Block DUNGEON_GLASS = new ModBlockGlassBase("dungeon_glass", Material.GLASS).setBlockUnbreakable().setCreativeTab(ModCreativeTabsList.IDL_DUNGEON);
//
//    public static final Block MJDS_FLOOR = new BlockMJDS("mjds_floor", Material.ROCK).setCreativeTab(ModCreativeTabsList.IDL_DUNGEON);
//    public static final Block MJDS_WALL = new BlockMJDS("mjds_wall", Material.ROCK).setCreativeTab(ModCreativeTabsList.IDL_DUNGEON);
//
//    public static final Block INDES_ICE = new BlockBase("hard_ice", Material.ICE).setBlockUnbreakable().setCreativeTab(ModCreativeTabsList.IDL_DUNGEON);
//
//    public static final Block TRAP_FLAME = new BlockDungeonTrapFlame("trap_flame", Material.ROCK, 3);
//    public static final Block TRAP_FLAME_2 = new BlockDungeonTrapFlame("trap_flame_2", Material.ROCK, 5);
//    public static final Block TRAP_FLAME_3 = new BlockDungeonTrapFlame("trap_flame_3", Material.ROCK, 10);
//
//    public static final Block TRAP_SPIKE = new BlockDungeonTrapSpike("trap_spike", Material.ROCK, 2f);
//    public static final Block TRAP_SPIKE_2 = new BlockDungeonTrapSpike("trap_spike_2", Material.ROCK, 4f);
//    public static final Block TRAP_SPIKE_3 = new BlockDungeonTrapSpike("trap_spike_3", Material.ROCK, 6f);
//    public static final Block TRAP_SPIKE_POISON = new BlockDungeonTrapSpike("trap_spike_poison", Material.ROCK, 4f).setPosion(4f);
//    public static final Block OFFLINE_REDSTONE = new BlockFadingDungeonBlock("offline_redstone", Material.ROCK).setAlternative(Blocks.REDSTONE_BLOCK.getDefaultState());
//    public static final Block OFFLINE_BOOKSHELF = new BlockFadingDungeonBlock("offline_bookshelf", Material.WOOD).setAlternative(Blocks.BOOKSHELF.getDefaultState());
//
//    public static final Block FAKE_WALL = new BlockFakeWall("fake_wall", Material.VINE).setUnlocalizedName("dungeon_brick_1");
//
//    //public static final BlockPhasingOre INERT_CHEST = new BlockInertChest("chest_inert", Material.WOOD, BlockInertChest.Type.BASIC);
//    public static final Block CUSTOM_CHEST = new BlockCustomChest("chest_custom", Material.WOOD).setCreativeTab(ModCreativeTabsList.IDL_DUNGEON);
//    public static final Block DUNGEON_RAIL = new ModBlockRailBase("rail_dungeon").setCreativeTab(ModCreativeTabsList.IDL_DUNGEON);
//    public static final Block BRIC_COMP_RAIL = new ModBlockRailBase("bric_comp_rail").setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//
//    public static final Block DUNGEON_DOWN_DOOR = new BlockTriggeringDoor("door_enter", Material.ROCK);
//    public static final Block DUNGEON_SPAWN_MONSTER = new BlockTriggerSpawn("spawn_monster", Material.GROUND);
//    public static final Block DUNGEON_DOWN_DOOR_BOSS = new BlockTriggeringDoorBossRoom("door_enter_boss_room", Material.ROCK);
//    public static final Block DUNGEON_SPAWN_MONSTER_BOSS = new BlockTriggerSpawnBoss("spawn_monster_boss", Material.GROUND);
//
//    public static final Block STEP_LIGHT = new BlockDungeonStepLight("step_light", Material.GROUND, 0, 8);
//
//
//    public static final BlockGargoyleHead GARGOYLE_HEAD = new BlockGargoyleHead("gargoyle_head", Material.ROCK);
//    public static final BlockGargoyleBody GARGOYLE_BODY = new BlockGargoyleBody("gargoyle_body", Material.ROCK);
//
//    //change the block state.
//    public static final Block TRAFFIC_LIGHT = new BlockTrafficLight("x_green", Material.IRON).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//    public static final Block RALLY_HELPER = new BlockRallyHelper("rally_helper", Material.IRON).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//    //public static final BlockPhasingOre EXAMPLE =  new BlockVariantExample("example", Material.WOOD).setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//
//    //todo: reconsider what do they drop. now quite strange.
//    public static final BlockPlantBase FIRE_FLOWER_A = new BlockPlantBase("fire_flower_a", Material.PLANTS);
//    public static final BlockPlantBase FIRE_FLOWER_B = new BlockPlantBase("fire_flower_b", Material.PLANTS);
//
//    //hardness, resistance(default: hardness x5)
//    //setResistance sets argument x3. setResistance(5) => resistance = 10
//    //stone : 1.5, 30 (10)
//    //ore : 3.0, 15 (5)
//    //wool ： 0.8, =4
//    //concrete : 1.8, =9
//    static final float QUAD_HARDNESS = 6;
//    static final float QUAD_RESIST = 1;
//
//    public static final Block COBBLE_COMPOSITE = new BlockBase("cobble_composite", Material.ROCK).setHardness(1.5f).setResistance(10f);//= stone
//    public static final Block BRICKS_COMPOSITE = new BlockBase("bricks_composite", Material.ROCK).setHardness(QUAD_HARDNESS).setResistance(QUAD_RESIST);
//    public static final Block STAIRS_COMPOSITE = new BlockStairsBase("stairs_composite", BRICKS_COMPOSITE).setHardness(QUAD_HARDNESS).setResistance(QUAD_RESIST);
//
//
//    //todo: door. pressure plates
//    public static final Block BRIC_SMOOTH_GLASS = new ModBlockGlassBase("bric_smooth_glass", Material.GLASS).setHardness(QUAD_HARDNESS).setResistance(QUAD_RESIST);
//
//    public static final Block BRIC_COMP_SMOOTH = new BlockBase("bric_comp_smooth", Material.ROCK).setHardness(QUAD_HARDNESS).setResistance(QUAD_RESIST);
//    public static final Block BRIC_COMP_SMOOTH_STAIRS = new BlockStairsBase("bric_comp_smooth_stairs", BRICKS_COMPOSITE).setHardness(QUAD_HARDNESS).setResistance(QUAD_RESIST);
//
//    //
//    public static final Block CLAM = new BlockBase("clam_block", Material.CORAL);
//
//    public static final Block BLOCK_CONVEY = new BlockConveyBelt("conveyor_belt", Material.IRON, 0.5f);
//
//    //Ore
//    public static final Block RAIN_ORE = new BlockRainOre("rain_ore");
////    public static final Block RAIN_ORE_FIXED = new BlockOreBase("rain_ore_fixed").setHardness(3.0F).setResistance(5.0F);
//
//    public static final Block DEBUG = new BlockRainOre("debug_block");
//
//    //Uprising
//    public static final Block CLAIM_ZONE = new BlockCitadelCaller("citadel_claim");
//    public static final Block BUILD_ZONE = new BlockCitadelCaller("citadel_build");
//    public static final Block TURRET_ZONE = new BlockCitadelCaller("citadel_turret");
//
//    public static final BlockDungeonTouchLight TOUCH_LIGHT = new BlockDungeonTouchLight("touch_light", Material.CIRCUITS, 1, 15);
//
//    public static final BlockLandmine LANDMINE = new BlockLandmine("landmine", Material.CIRCUITS, EntityUtil.EnumFaction.MOB_VANILLA, 1);

}
