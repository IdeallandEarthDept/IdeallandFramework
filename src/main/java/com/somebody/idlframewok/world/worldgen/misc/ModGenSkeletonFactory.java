package com.somebody.idlframewok.world.worldgen.misc;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.entity.creatures.misc.EntityHiredSkeleton;
import com.somebody.idlframewok.init.InitDimension;
import com.somebody.idlframewok.potion.InitPotionTypes;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.WorldGenUtil;
import com.somebody.idlframewok.world.biome.godlands.skylands.BiomeSkylandBase;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.DimensionType;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.*;
import static com.somebody.idlframewok.util.WorldGenUtil.BOX;
import static com.somebody.idlframewok.util.WorldGenUtil.MOB_SPAWNER;
import static net.minecraftforge.common.BiomeDictionary.Type.WATER;

public class ModGenSkeletonFactory implements IWorldGenerator {

    public ModGenSkeletonFactory() {

    }

    IBlockState SENTRY_BLOCK = Blocks.STONEBRICK.getDefaultState();
    IBlockState FENCE = Blocks.DARK_OAK_FENCE.getDefaultState();
    IBlockState CAGE = Blocks.IRON_BARS.getDefaultState();

    IBlockState WALL_MAIN = Blocks.PURPUR_BLOCK.getDefaultState();
    IBlockState WALL_MAIN_CORNER = Blocks.PURPUR_PILLAR.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y);

    IBlockState STAIR = Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH);

    IBlockState HOPPER = Blocks.HOPPER.getDefaultState();
    IBlockState LIGHT = Blocks.REDSTONE_LAMP.getDefaultState();
    IBlockState SWITCH = Blocks.LEVER.getDefaultState();

    IBlockState PLANKS = Blocks.PLANKS.getDefaultState();
    IBlockState PLANKS_FADE = ModBlocks.FADE_PLANKS.getDefaultState();

    IBlockState getStoneBrickBlock(Random random)
    {
        //3 looks too different, so the only first 3
        return SENTRY_BLOCK.withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.byMetadata(random.nextInt(2)));
    }

    IBlockState getPlankBlock(Random random)
    {
        if (random.nextInt(5) == 0)
        {
            return PLANKS_FADE;
        }
        else {
            return PLANKS;
        }
    }

    public void buildRoom(Random random, int chunkX, int chunkZ, World world, int y) {
        int min = 2;
        int max = 12;
        int y0 = y;
        int y1 = y + 10;

        //clear interior
        WorldGenUtil.buildForced(chunkX,chunkZ,world, WorldGenUtil.AIR,
                min + 1, y0 + 1, min + 1,
                max - 1, y1 - 1, max - 1);

        //four walls
        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN,
                min, y0, min,
                min, y1, max);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN,
                min, y0, min,
                max, y1, min);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN,
                max, y0, max,
                min, y1, max);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN,
                max, y0, max,
                max, y1, min);

        //pillars
        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN_CORNER,
                min, y0, min,
                min, y1, min);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN_CORNER,
                min, y0, max,
                min, y1, max);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN_CORNER,
                max, y0, min,
                max, y1, min);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN_CORNER,
                max, y0, max,
                max, y1, max);

        //Ground
        WorldGenUtil.buildForced(chunkX,chunkZ,world, getStoneBrickBlock(random),
                min, y0, min,
                max, y0, max);

        //Middle Floor
        WorldGenUtil.buildForced(chunkX,chunkZ,world, getPlankBlock(random),
                min+1, y0+5, min+1,
                max-1, y0+5, max-1);

        //Roof
        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN,
                min+1, y1, min+1,
                max-1, y1, max-1);

        //steps
        for (int step = 0; step <= 4; step++)
        {
            WorldGenUtil.buildForced(chunkX,chunkZ,world, STAIR,
                min+1, y0+1+step, min+4+step);
            WorldGenUtil.buildForced(chunkX,chunkZ,world, STAIR,
                    min+2, y0+1+step, min+4+step);

            WorldGenUtil.buildForced(chunkX,chunkZ,world, SENTRY_BLOCK,
                    min+1, y0+step, min+4+step);
            WorldGenUtil.buildForced(chunkX,chunkZ,world, SENTRY_BLOCK,
                    min+2, y0+step, min+4+step);
        }

        //hole in floor
        WorldGenUtil.buildForced(chunkX,chunkZ,world, WorldGenUtil.AIR,
                min+1, y0+5, min+5,
                min+2, y0+5, min+7);

        int centerX = 7;
        int centerZ = 7;

        //door
        for (int dy = 1; dy<= 2; dy++)
        {
//            WorldGenUtil.buildForced(chunkX,chunkZ,world, PLANKS_FADE,
//                min, y0+dy, centerZ);

            WorldGenUtil.buildForced(chunkX,chunkZ,world, PLANKS_FADE,
                    max, y0+dy, centerZ);

            WorldGenUtil.buildForced(chunkX,chunkZ,world, PLANKS_FADE,
                    centerX, y0+dy, min);

            WorldGenUtil.buildForced(chunkX,chunkZ,world, PLANKS_FADE,
                    centerX, y0+dy, max);
        }
    }

    public void buildCore(Random random, int chunkX, int chunkZ, World world, int y) {
        int centerX = 7;
        int centerZ = 7;

        WorldGenUtil.buildForced(chunkX,chunkZ,world, SENTRY_BLOCK, centerX, y+1, centerZ);
        WorldGenUtil.buildForced(chunkX,chunkZ,world, SENTRY_BLOCK, centerX, y+2, centerZ);
        WorldGenUtil.buildForced(chunkX,chunkZ,world, SENTRY_BLOCK, centerX, y+3, centerZ);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, CAGE, centerX-1, y+1, centerZ-1,
                centerX+1, y+5, centerZ+1,
                3
                );

        WorldGenUtil.buildForced(chunkX,chunkZ,world, MOB_SPAWNER, centerX, y+4, centerZ);
        TileEntity tileentity = world.getTileEntity(new BlockPos(CHUNK_SIZE * chunkX + centerX, y+4, CHUNK_SIZE * chunkZ + centerZ));

        if (tileentity instanceof TileEntityMobSpawner)
        {
            ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityId(EntityList.getKey(EntityHiredSkeleton.class));
            //readFromNBT to set more detail
        }
        else
        {
            Idealland.LogWarning("[Tomb]Failed to fetch mob spawner entity at (%s, %s, %s)", centerX, y+4, centerZ);
        }

        WorldGenUtil.buildForced(chunkX,chunkZ,world, HOPPER, centerX, y+5, centerZ);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, BOX, centerX, y+6, centerZ);

        DifficultyInstance difficultyInstance = world.getDifficultyForLocation(new BlockPos(CHUNK_SIZE * chunkX + centerX, y+4, CHUNK_SIZE * chunkZ + centerZ));
        //4 near hopper
        for (int i = 0; i < DIR.length; i++){

            int[] vec = DIR[i];
            int[] vec2 = DIR_L[i];
            WorldGenUtil.buildForced(chunkX,chunkZ,world, HOPPER.withProperty(BlockHopper.FACING, EnumFacing.getFacingFromVector(-vec[0],0,-vec[1])),
                    centerX+vec[0], y+6, centerZ+vec[1]);

            WorldGenUtil.buildForced(chunkX,chunkZ,world, LIGHT,
                    centerX+vec[0]*2, y+6, centerZ+vec[1]*2);

            WorldGenUtil.buildForced(chunkX,chunkZ,world, SWITCH.withProperty(BlockLever.FACING,
                    BlockLever.EnumOrientation.forFacings(EnumFacing.getFacingFromVector(vec[0],0,vec[1]).rotateY(), EnumFacing.NORTH)),
                    centerX+vec[0]*2+vec2[0], y+6, centerZ+vec[1]*2+vec2[1]);

            //big boxes
            BlockPos pos = new BlockPos(chunkX * CHUNK_SIZE + centerX+vec[0], y+7, chunkZ * CHUNK_SIZE + centerZ+vec[1]);
            WorldGenUtil.buildForced(chunkX,chunkZ,world, BOX,
                    centerX+vec[0], y+7, centerZ+vec[1]);

            IInventory container = Blocks.CHEST.getLockableContainer(world, pos);
            if (container!=null) {
                int sizeBox = container.getSizeInventory();

                for (int k = 0; k < sizeBox; ++k) {
                    container.setInventorySlotContents(k, getChestContent(random, i, difficultyInstance));
                }
            }
            else {
                Idealland.LogWarning("failed to create a chest @%s", pos);
            }

            pos = new BlockPos(chunkX * CHUNK_SIZE + centerX+vec[0]*2, y+7, chunkZ * CHUNK_SIZE + centerZ+vec[1]*2);
            WorldGenUtil.buildForced(chunkX,chunkZ,world, BOX,
                    centerX+vec[0]*2, y+7, centerZ+vec[1]*2);

            container = Blocks.CHEST.getLockableContainer(world, pos);
            if (container!=null) {
                int sizeBox = container.getSizeInventory();

                for (int k = 0; k < sizeBox; ++k) {
                    container.setInventorySlotContents(k, getChestContent(random, i, difficultyInstance));
                }
            }
            else {
                Idealland.LogWarning("failed to create a chest @%s", pos);
            }
        }
    }

    public ItemStack getChestContent(Random random, int index, DifficultyInstance difficulty)
    {
        float f = difficulty.getClampedAdditionalDifficulty();
        ItemStack result = ItemStack.EMPTY;
        switch (index)
        {
            case 0:
                //player or creeper
                result = new ItemStack(Items.SKULL);
                result.setItemDamage(random.nextInt(10) == 0 ? 3 : 0);
                result.setCount(1 + random.nextInt(64));
                break;
            case 1:
                result = new ItemStack(Items.BONE);
                result.setCount(1 + random.nextInt(64));
                break;
            case 2:
                result = new ItemStack(Items.BOW);
                EnchantmentHelper.addRandomEnchantment(random, result, (int) (20 + 80 * difficulty.getClampedAdditionalDifficulty() * random.nextFloat()), true);
                break;
            case 3:
                int arrowType = random.nextInt(10);
                switch (arrowType)
                {
                    case 0:
                        result = new ItemStack(Items.SPECTRAL_ARROW);
                        break;
                    case 1:
                        result = new ItemStack(Items.TIPPED_ARROW);
                        PotionUtils.addPotionToItemStack(result, getArrowPotionType(random));
                        break;
                    default:
                        result = new ItemStack(Items.ARROW);
                        break;
                }
                result.setCount(64);
            default:

        }
        return result;
        //this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItemMainhand(), (int)(5.0F + f * (float)this.rand.nextInt(18)), false));
        //this.setItemStackToSlot(entityequipmentslot, EnchantmentHelper.addRandomEnchantment(this.rand, itemstack, (int)(5.0F + f * (float)this.rand.nextInt(18)), false));
    }

    public PotionType getArrowPotionType(Random random)
    {
        int index = random.nextInt(10);
        switch (index)
        {
            case 0:
                return PotionTypes.SLOWNESS;
            case 1:
                return PotionTypes.WEAKNESS;
            case 2:
                return PotionTypes.POISON;
            case 3:
                return InitPotionTypes.BLINDNESS;
            case 4:
                return InitPotionTypes.WITHER;
            case 5:
                return InitPotionTypes.LEVITATION;
            case 6:
                return ModPotions.BURN.getPotionType();
            case 7:
                return ModPotions.HEAL_MINUS.getPotionType();
            case 8:
                return ModPotions.MINUS_ARMOR_PERCENTAGE.getPotionType();
            default:
                return PotionTypes.STRONG_POISON;
        }
    }


    public void buildBase(Random random, int chunkX, int chunkZ, World world, int y) {

    }
    public void buildSentry(Random random, int chunkX, int chunkZ, World world, int y) {
        BlockPos[] sentryPos = {
                new BlockPos(1,0,1),
                new BlockPos(1,0,13),
                new BlockPos(13,0,1),
                new BlockPos(13,0,13),
        };

        EnumDifficulty difficulty = world.getDifficulty();

        for (BlockPos posBase:
            sentryPos) {

            //floor
            final int x0 = posBase.getX();
            final int z0 = posBase.getZ();

            WorldGenUtil.buildForced(chunkX,chunkZ,world, getStoneBrickBlock(random), x0 -1, y, z0 -1,
                    x0 +1, y, z0 +1);

            //fence
            WorldGenUtil.buildForced(chunkX,chunkZ,world, FENCE, x0 -1, y+1, z0 -1,
                    x0 +1, y+1, z0 +1);

            //make a hole in the fence
            WorldGenUtil.buildForced(chunkX,chunkZ,world, WorldGenUtil.AIR, x0, y+1, z0);

            //clear the window
            WorldGenUtil.buildForced(chunkX,chunkZ,world, WorldGenUtil.AIR, x0 -1, y+2, z0 -1,
                    x0 +1, y+2, z0 +1);

            //roof
            WorldGenUtil.buildForced(chunkX,chunkZ,world, getStoneBrickBlock(random), x0 -1, y+3, z0 -1,
                    x0 +1, y+3, z0 +1);

            if (world.isRemote)
            {
                Idealland.Log("Generated Skeleton Base");
            }
            else {
                EntityHiredSkeleton skeleton = new EntityHiredSkeleton(world);
                skeleton.setPosition(x0 + CHUNK_SIZE * chunkX + 0.5f, y+1, z0 + CHUNK_SIZE * chunkZ+ 0.5f);
                //skeleton.forceSpawn = true;
                skeleton.enablePersistence();

                if (!world.spawnEntity(skeleton))
                {
                    Idealland.Log("[Generated Skeleton Base] Failed to spawn a skeleton");
                }
                else {
                    if (difficulty == EnumDifficulty.HARD || random.nextFloat() < 0.4f)
                    {
                        ItemStack stack = new ItemStack(Items.BOW);
                        stack.addEnchantment(Enchantments.FLAME, 1);
                        stack.addEnchantment(Enchantments.UNBREAKING, 3);
                        skeleton.setHeldItem(EnumHand.MAIN_HAND, stack);
                    }

                    if (difficulty == EnumDifficulty.HARD || random.nextFloat() < 0.4f)
                    {
                        ItemStack stack = new ItemStack(Items.TIPPED_ARROW);
                        PotionUtils.addPotionToItemStack(stack, getArrowPotionType(random));
                        skeleton.setHeldItem(EnumHand.OFF_HAND, stack);
                    }
                }
            }
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        //Chunk.populate
        //if (populating != null && net.minecraftforge.common.ForgeModContainer.logCascadingWorldGeneration) logCascadingWorldGeneration();
        BlockPos pos = new BlockPos(chunkX * CHUNK_SIZE, 0, chunkZ * CHUNK_SIZE);

        DimensionType dimensionType = world.provider.getDimensionType();
        if (dimensionType != DimensionType.OVERWORLD && dimensionType != InitDimension.DIM_UNIV) {
            //only in univ or overworld
            return;
        }
        Biome biome = world.getBiome(pos);
        if (biome instanceof BiomeSkylandBase || BiomeDictionary.getTypes(biome).contains(WATER)){
            //dont spawn in skylands
            return;
        }

        random.nextFloat();
        if (random.nextFloat() > 0.001f )
        {
            return;
        }

        int minHeight = 3;
        int buildHeightLimit = world.getActualHeight() - 20;

        int groundY = buildHeightLimit;
        BlockPos.MutableBlockPos tempPos = new BlockPos.MutableBlockPos(pos.add(7, 0, 7));
        while (groundY > minHeight) {
            tempPos.setY(groundY);
            if (WorldGenUtil.isSolid(world, tempPos)) {
                break;
            }
            groundY--;
        }

        if (groundY <= minHeight) {
            //failed to found solid GROUND
            return;
        }

        buildSentry(random, chunkX, chunkZ, world, groundY);
        buildSentry(random, chunkX, chunkZ, world, groundY+9);
        buildRoom(random, chunkX, chunkZ, world, groundY);
        buildCore(random, chunkX, chunkZ, world, groundY);
    }
}
