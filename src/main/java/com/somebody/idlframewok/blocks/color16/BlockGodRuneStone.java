package com.somebody.idlframewok.blocks.color16;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.designs.god16.Init16Gods;
import com.somebody.idlframewok.entity.creatures.mobs.skyland.EntityCatharVex;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.item.artifact.IArtifact;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.*;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

import static com.somebody.idlframewok.util.Color16Def.*;
import static com.somebody.idlframewok.util.CommonDef.TICK_PER_DAY;

public class BlockGodRuneStone extends BlockBase {
    final int index;

    float buffTime = 60f;

    public BlockGodRuneStone(String name, Material material, int index) {
        super(name, material);
        this.index = index;
        setCreativeTab(ModCreativeTabsList.IDL_WORLD);
        setBlockUnbreakable();
        setLightLevel(1f);
        setTickRandomly(true);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityWalk(worldIn, pos, entityIn);
        if (!worldIn.isRemote)
        {
            if (entityIn instanceof EntityLivingBase)
            {
                EntityLivingBase livingBase = (EntityLivingBase) entityIn;
                switch (index)
                {
                    case Color16Def.EARTH:
                        EntityUtil.ApplyBuff(livingBase, MobEffects.INVISIBILITY, 0, buffTime);
                        break;
                    case Color16Def.FIRE:
                        livingBase.setFire(Color16Def.FIRE);//burn for 1 sec
                        break;
                    case Color16Def.LIFE:
                        if (CommonFunctions.isSecondTick(worldIn))
                        {
                            if (livingBase.isEntityUndead())
                            {
                                livingBase.attackEntityFrom(DamageSource.STARVE, Color16Def.LIFE);
                            }
                            else {
                                livingBase.heal(Color16Def.LIFE);
                                if (livingBase instanceof EntityPlayer)
                                {
                                    PlayerUtil.addFoodLevel((EntityPlayer) livingBase, Color16Def.LIFE);
                                }
                            }
                        }
                        break;
                        //skip dirt
                    case Color16Def.WATER:
                        livingBase.extinguish();
                        EntityUtil.ApplyBuff(livingBase, MobEffects.WATER_BREATHING, 0, buffTime);
                        break;
                    case Color16Def.POISON:
                        livingBase.clearActivePotions();
                        break;
                    case Color16Def.MALE:
                        EntityUtil.ApplyBuff(livingBase, ModPotions.BERSERK, 0, buffTime);
                        break;
                    case Color16Def.IRON:
                        EntityUtil.ApplyBuff(livingBase, MobEffects.HASTE, 0, buffTime);
                        break;
                    case Color16Def.STONE:
                        EntityUtil.ApplyBuff(livingBase, MobEffects.RESISTANCE, 0, buffTime);
                        break;
                    case Color16Def.FEMALE:
//                        EntityUtil.ApplyBuff(livingBase, MobEffects.RESISTANCE, 0, buffTime);
                        break;
                    case Color16Def.WOOD:
//                        livingBase.clearActivePotions();
                        break;
                    case Color16Def.GOLD:
                        EntityUtil.ApplyBuff(livingBase, MobEffects.LUCK, 0, buffTime);
                        break;
                    case Color16Def.WIND:
                        EntityUtil.ApplyBuff(livingBase, MobEffects.SPEED, 0, buffTime);
                        EntityUtil.ApplyBuff(livingBase, MobEffects.JUMP_BOOST, 0, buffTime);
                        break;
                    case DEATH:
                        if (CommonFunctions.isSecondTick(worldIn))
                        {
                            if (livingBase.isEntityUndead()) {
                                livingBase.heal(Color16Def.LIFE);
                                if (livingBase instanceof EntityPlayer)
                                {
                                    PlayerUtil.addFoodLevel((EntityPlayer) livingBase, Color16Def.LIFE);
                                }
                            } else {
                                livingBase.attackEntityFrom(DamageSource.STARVE, Color16Def.LIFE);
                            }
                        }
                        break;
                    case Color16Def.LAVA:
                        EntityUtil.ApplyBuff(livingBase, MobEffects.FIRE_RESISTANCE, 0, buffTime);
                        break;
                    case Color16Def.SKY:
                        EntityUtil.ApplyBuff(livingBase, MobEffects.NIGHT_VISION, 0, buffTime);
                        break;

                        default:break;
                }
            }
        }
    }

    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos) {
        return index == WATER ? 30 : 0;
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        super.randomTick(worldIn, pos, state, random);
        if (!worldIn.isRemote && random.nextInt(4) == 0)
        {
            EntityCatharVex vex = new EntityCatharVex(worldIn);
            vex.setPosition(pos.getX(),pos.getY(),pos.getZ());
            worldIn.spawnEntity(vex);
            vex.onInitialSpawn(worldIn.getDifficultyForLocation(pos), null);
            //worldIn.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, pos.getX(), pos.getY(), pos.getZ(), 0,0,0);
        }

        if (!worldIn.isRemote)
        {
            IBlockState blockState = WorldGenUtil.AIR;
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(pos);
            switch (index)
            {
                case Color16Def.EARTH:
                    break;
                case Color16Def.FIRE:
                    TileEntity tileEntity = worldIn.getTileEntity(pos.down());
                    if (tileEntity instanceof TileEntityFurnace)
                    {
                        //keep burning.
                        ((TileEntityFurnace) tileEntity).setField(0, 999);
                        ((TileEntityFurnace) tileEntity).setField(1, 999);
                    }
                    break;
                case Color16Def.LIFE:
                    break;
                case Color16Def.SOIL:
                    blockState = worldIn.getBlockState(pos.up(2));
                    if (blockState.getBlock() instanceof IGrowable)
                    {
                        IGrowable growable = (IGrowable) blockState.getBlock();
                        if (growable.canGrow(worldIn, pos.up(2), blockState, false))
                        {
                            growable.grow(worldIn, random, pos, blockState);
                        }
                    }
                    break;
                case Color16Def.WATER:
                    break;
                case Color16Def.POISON:
                    break;
                case Color16Def.MALE:
                    break;
                case Color16Def.IRON:
                    blockState = worldIn.getBlockState(pos.up(1));
                    if (blockState.getBlock() instanceof BlockAnvil)
                    {
                        if (blockState.getValue(BlockAnvil.DAMAGE) != 0)
                        {
                            worldIn.setBlockState(pos, state.withProperty(BlockAnvil.DAMAGE, 0));
                        }
                    }
                    break;
                case Color16Def.STONE:
                    for (int i = 1; i <= STONE; i++)
                    {
                        mutableBlockPos.move(EnumFacing.UP);
                        blockState = worldIn.getBlockState(mutableBlockPos);
                        if (blockState.getMaterial().isReplaceable())
                        {
                            worldIn.setBlockState(mutableBlockPos,
                                    Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.byMetadata(random.nextInt(BlockStone.EnumType.values().length))));
                        }
                    }

                    break;
                case Color16Def.FEMALE:
                    break;
                case Color16Def.WOOD:
                    for (int i = 1; i <= WOOD; i++) {
                        mutableBlockPos.move(EnumFacing.UP);
                        blockState = worldIn.getBlockState(mutableBlockPos);
                        if (blockState.getMaterial().isReplaceable()) {
                            worldIn.setBlockState(mutableBlockPos,
                                    Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.byMetadata(random.nextInt(BlockPlanks.EnumType.values().length))));
                        }
                    }
                    break;
                case Color16Def.GOLD:
                    break;
                case Color16Def.WIND:
                    break;
                case Color16Def.DEATH:
                    break;
                case Color16Def.LAVA:
                    break;
                case Color16Def.SKY:
                    break;

                default:break;
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
        {
            return true;
        }

        ItemStack itemStack = playerIn.getHeldItem(hand);
        if (itemStack.getItem() instanceof IArtifact)
        {
            if (index == DEATH && itemStack.getItem() == ModItems.ARTIFACT_DEATH)
            {
                itemStack.setItemDamage(0);
                return true;
            }
            else {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
                Color16Def.increaseBelief(playerIn, index, -50);
                return true;
            }
        }

        int mask = (1 << 16) - 1;
        int tickMark = IDLNBTUtil.GetIntAuto(playerIn, Color16Def.KEY_PRAY_TIMESTAMP, 0) ;
        int curtick = (int) (worldIn.getWorldTime() / TICK_PER_DAY) & mask;
        if (tickMark != curtick)
        {
            int tribute = IDLNBTUtil.GetIntAuto(playerIn, getKeyTribute(index), 0);
            if (tribute < 0)
            {
                playerIn.attackEntityFrom(DamageSource.OUT_OF_WORLD, 5);
                CommonFunctions.SafeSendMsgToPlayer(playerIn, Color16Def.MSG_GOD_REJECT);
            }else {
                Color16Def.increaseBelief(playerIn, index, 10);
                IDLNBTUtil.setIntAuto(playerIn, Color16Def.KEY_PRAY_TIMESTAMP, curtick);
                PlayerUtil.giveToPlayer(playerIn, Init16Gods.GODS[index].tributeReward.GetItem(playerIn.getRNG()));
                CommonFunctions.SafeSendMsgToPlayer(playerIn, Color16Def.MSG_GOD_ACCEPT_PRAY);

                EntityCatharVex vex = new EntityCatharVex(worldIn);
                vex.setPosition(pos.getX(),pos.getY(),pos.getZ());
                worldIn.spawnEntity(vex);
                vex.onInitialSpawn(worldIn.getDifficultyForLocation(pos), null);
                vex.setOwner(playerIn);
            }
        }
        else {
            CommonFunctions.SafeSendMsgToPlayer(playerIn, Color16Def.MSG_GOD_PRAY_CD);
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @SubscribeEvent
    public static void onHurt(LivingAttackEvent event)
    {
        EntityLivingBase hurtOne = event.getEntityLiving();
        World world = hurtOne.getEntityWorld();

        Entity attacker = event.getSource().getTrueSource();
        if (attacker != null)
        {
            Block block = world.getBlockState(hurtOne.getPosition().down()).getBlock();
            if (isPeaceful(block))
            {
                event.setCanceled(true);
                return;
            }

            block = world.getBlockState(attacker.getPosition().down()).getBlock();
            if (isPeaceful(block))
            {
                event.setCanceled(true);
            }
        }
    }

    private static boolean isPeaceful(Block block) {
        return block instanceof BlockGodRuneStone && ((BlockGodRuneStone) block).index == FEMALE;
    }

}
