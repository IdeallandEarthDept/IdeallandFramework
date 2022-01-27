package com.somebody.idlframewok.world.worldgen.structure;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.blocks.blockDungeon.BlockLockAdjacent;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.Reference;
import com.somebody.idlframewok.util.WorldGenUtil;
import com.somebody.idlframewok.world.worldgen.structure.dungeon.EnumDungeonTrap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class WorldGenDungeonTower extends WorldGenStructure implements IStructure {

    int heightPerFloor = 7;
    int maxFloors = 7;

    public IBlockState BARS = Blocks.IRON_BARS.getDefaultState();
    public IBlockState MOB_SPAWNER = Blocks.MOB_SPAWNER.getDefaultState();

    NBTTagCompound baby_zombie_spawnData;
    NBTTagCompound vindicator_spawnData;

    Vec3i[] spawnerPosList = new Vec3i[]
            {
                    new Vec3i(8,4,8),
                    new Vec3i(10,4,8),
                    new Vec3i(8,4,10),
                    new Vec3i(10,4,10),
            };

    int gateX = 12;
    int gateY = 4;
    int gateZ = 3;
    int gateSize = 3;

    IDLNBTDef.SPAWNER_TYPE[] spawnerList = new IDLNBTDef.SPAWNER_TYPE[]
    {
        //1
        IDLNBTDef.SPAWNER_TYPE.NONE,
        IDLNBTDef.SPAWNER_TYPE.NONE,
        IDLNBTDef.SPAWNER_TYPE.NONE,
        IDLNBTDef.SPAWNER_TYPE.NONE,
        //2
        IDLNBTDef.SPAWNER_TYPE.NONE,
        IDLNBTDef.SPAWNER_TYPE.NONE,
        IDLNBTDef.SPAWNER_TYPE.NONE,
        IDLNBTDef.SPAWNER_TYPE.NORMAL,
        //3
        IDLNBTDef.SPAWNER_TYPE.NONE,
        IDLNBTDef.SPAWNER_TYPE.NONE,
        IDLNBTDef.SPAWNER_TYPE.NONE,
        IDLNBTDef.SPAWNER_TYPE.NORMAL,
        //4
        IDLNBTDef.SPAWNER_TYPE.NONE,
        IDLNBTDef.SPAWNER_TYPE.NORMAL,
        IDLNBTDef.SPAWNER_TYPE.NONE,
        IDLNBTDef.SPAWNER_TYPE.NORMAL,
        //5
        IDLNBTDef.SPAWNER_TYPE.NONE,
        IDLNBTDef.SPAWNER_TYPE.NORMAL,
        IDLNBTDef.SPAWNER_TYPE.NORMAL,
        IDLNBTDef.SPAWNER_TYPE.NONE,
        //6
        IDLNBTDef.SPAWNER_TYPE.NONE,
        IDLNBTDef.SPAWNER_TYPE.NORMAL,
        IDLNBTDef.SPAWNER_TYPE.NORMAL,
        IDLNBTDef.SPAWNER_TYPE.NORMAL,
        //7
        IDLNBTDef.SPAWNER_TYPE.NORMAL,
        IDLNBTDef.SPAWNER_TYPE.NORMAL,
        IDLNBTDef.SPAWNER_TYPE.NORMAL,
        IDLNBTDef.SPAWNER_TYPE.NORMAL,
    };

    MinecraftServer mcServer;
    TemplateManager manager;
    ResourceLocation location;
    ResourceLocation location1F;
    ResourceLocation locationTop;
    Template templateMain;
    Template template1F;
    Template templateTop;

    public WorldGenDungeonTower(String main, String template1F, String templateTop) {
        super(main);
        location = new ResourceLocation(Reference.MOD_ID, main);
        location1F = new ResourceLocation(Reference.MOD_ID, template1F);
        locationTop = new ResourceLocation(Reference.MOD_ID, templateTop);

        try {
            baby_zombie_spawnData = JsonToNBT.getTagFromJson(IDLNBTDef.BABY_ZOMBIE_STR);
            vindicator_spawnData = JsonToNBT.getTagFromJson(IDLNBTDef.VINDICATOR_STRING);
        } catch (NBTException e) {
            e.printStackTrace();
        }
    }

    public void init(World worldIn)
    {
//        if (manager == null)
//        {
            mcServer = worldIn.getMinecraftServer();
            manager = worldServer.getStructureTemplateManager();
            templateMain = manager.get(mcServer, location);
            if (templateMain == null)
            {
                Idealland.LogWarning("Main template is null");
            }

            template1F = manager.get(mcServer, location1F);
            if (templateMain == null)
            {
                Idealland.LogWarning("1F template is null");
            }

            templateTop = manager.get(mcServer, locationTop);
//        }
    }


    //todo: simply wont work by readFromNBT. Everything looked correct except the result.
    //try template next time.
    public void setSpawner(World worldIn, BlockPos pos, IDLNBTDef.SPAWNER_TYPE type)
    {
        TileEntity tileentity;
        switch (type)
        {
            case NONE:
                WorldGenUtil.setBlockState(worldIn, pos, BARS);
                break;
            case ZOMBIE_BABY:
                WorldGenUtil.setBlockState(worldIn, pos, MOB_SPAWNER);
                tileentity = worldIn.getTileEntity(pos);
                if (tileentity instanceof TileEntityMobSpawner)
                {
                    tileentity.readFromNBT(baby_zombie_spawnData);
                }
                else
                {
                    Idealland.LogWarning("[Tower]Failed to fetch mob spawner entity at (%s, %s, %s)", pos.getX(), pos.getY(), pos.getZ());
                }
                break;
            case VINDICATOR:
                WorldGenUtil.setBlockState(worldIn, pos, MOB_SPAWNER);
                tileentity = worldIn.getTileEntity(pos);
                if (tileentity instanceof TileEntityMobSpawner)
                {
                    tileentity.readFromNBT(vindicator_spawnData);
                }
                else
                {
                    Idealland.LogWarning("[Tower]Failed to fetch mob spawner entity at (%s, %s, %s)", pos.getX(), pos.getY(), pos.getZ());
                }
                break;
            case ILLUSIONER:
                WorldGenUtil.setBlockState(worldIn, pos, MOB_SPAWNER);
                tileentity = worldIn.getTileEntity(pos);
                if (tileentity instanceof TileEntityMobSpawner)
                {
                    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntityId(EntityList.getKey(EntityIllusionIllager.class));
                }
                else
                {
                    Idealland.LogWarning("[Tower]Failed to fetch mob spawner entity at (%s, %s, %s)", pos.getX(), pos.getY(), pos.getZ());
                }
                break;
            case NORMAL:
                break;
        }

    }

    int maxTrap = 14;
    public void generateStructure(World worldIn, BlockPos pos) {
        init(worldIn);

        if (templateMain != null)
        {
            IBlockState state = worldIn.getBlockState(pos);
            worldIn.notifyBlockUpdate(pos.add(1,1,1), state, state, 3);

            genGroundBase(worldIn, pos);
            BlockPos.MutableBlockPos pos1 = new BlockPos.MutableBlockPos(pos);

            //1st floor
            if (template1F != null)
            {
                WorldGenUtil.generate(location1F, worldIn, pos1, null);
                //template1F.addBlocksToWorld(worldIn, pos1, settings, WorldGenUtil.PREVENT_CASCADE_FLAGS);
                genInterior(worldIn, pos1, 0);
                pos1.setY(pos1.getY() + heightPerFloor);
            }

            //middle. Note that template1F is i = 0
            for (int i = 1; i < maxFloors; i++)
            {
                WorldGenUtil.generate(location, worldIn, pos1, null);
                //templateMain.addBlocksToWorld(worldIn, pos1, settings, WorldGenUtil.PREVENT_CASCADE_FLAGS);
                genInterior(worldIn, pos1, i);

                pos1.setY(pos1.getY() + heightPerFloor);
            }

            //top floor
            genCeil(worldIn, pos1);
        }
        else {
            Idealland.LogWarning("Null Structure: %s", location);
        }
    }

    public void genInterior(World worldIn, BlockPos pos, int level)
    {
        int trapTypeCount = EnumDungeonTrap.values().length;
        int trapTypeSize = Math.min(3 + level, trapTypeCount);
        for (int x = 1; x <= maxTrap; x++)
        {
            for (int z = 1; z <= maxTrap; z++)
            {
                Block block = worldIn.getBlockState(pos.add(x,2,z)).getBlock();
                //Idealland.Log("block = %s", block);
                if (block == Blocks.MAGMA)
                {
                    EnumDungeonTrap.values()[worldIn.rand.nextInt(trapTypeSize)].build(worldIn, pos.add(x,2,z));
                }
            }
        }

        for (int i = 0; i <= 3; i++)
        {
            setSpawner(worldIn, pos.add(spawnerPosList[i]), spawnerList[level * 4 + i]);
        }

       //set up the puzzle
        for (int x = 0; x < gateSize; x++)
        {
            for (int y = 0; y < gateSize; y++)
            {
                //50% chance is the hardest
                if (worldIn.rand.nextInt(maxFloors) < level * 2)
                {
                    BlockPos pos1 = pos.add(gateX + x, gateY + y, gateZ);
                            Block block = worldIn.getBlockState(pos1).getBlock();
                    if (block instanceof BlockLockAdjacent)
                    {
                        ((BlockLockAdjacent) block).toggleSelfAndAdjcacent(worldIn, pos1);
                    }
                }
            }
        }
    }

    public void genCeil(World worldIn, BlockPos pos)
    {
        if (templateTop != null)
        {
            WorldGenUtil.generate(locationTop, worldIn, pos, null);
            //templateTop.addBlocksToWorld(worldIn, pos, settings, WorldGenUtil.PREVENT_CASCADE_FLAGS);
        }
    }

    public void genGroundBase(World worldIn, BlockPos pos)
    {
        int x0 = pos.getX();
        int y0 = pos.getY();
        int z0 = pos.getZ();
        BlockPos.MutableBlockPos pos1 = new BlockPos.MutableBlockPos(pos);

        IBlockState state = ModBlocks.DUNGEON_WALL_MOSS.getDefaultState();

        for (int x = 0; x <= CHUNK_SIZE; x++)
        {
            for (int z = 0; z <= CHUNK_SIZE; z++)
            {
                for (int y = 0; y <= CHUNK_SIZE; y++)
                {
                    pos1.setPos(x0+x,y0-y,z0+z);
                    WorldGenUtil.setBlockStateIfAir(worldIn, pos1, state);
                }
            }
        }
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        this.generateStructure(worldIn, position);
        return true;
    }
}
