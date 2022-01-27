package com.somebody.idlframewok.item.tool;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.IHasModel;
import com.somebody.idlframewok.util.WorldGenUtil;
import com.google.common.collect.Sets;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Set;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.TOOL_CLASS_HAMMER;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ItemHammerBase extends ItemTool implements IHasModel {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.IRON_DOOR);
    public static final double HAMMER_ATTR_ATK_SPD = -3.2D;

    public ItemHammerBase(String name, ToolMaterial materialIn) {
        super(materialIn, EFFECTIVE_ON);
        this.attackDamage = materialIn.getAttackDamage() * 4f;
        this.attackSpeed = (float) HAMMER_ATTR_ATK_SPD;

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTabsList.IDL_MISC);
        toolMaterial = materialIn;
        ModItems.ITEMS.add(this);
    }

    public float getKnockBackFactor(ItemStack stack) {
        return 4f;
    }

    public void activateCoolDown(ItemStack stack, EntityPlayer player) {
        player.getCooldownTracker().setCooldown(stack.getItem(), (int) (TICK_PER_SECOND / EntityUtil.getAtkSpeed(player)));
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker) {
        return true;
    }

    @SubscribeEvent
    public static void onKB(LivingKnockBackEvent event) {
        if (event.getOriginalAttacker() instanceof EntityLivingBase) {
            EntityLivingBase livingBase = (EntityLivingBase) event.getOriginalAttacker();
            ItemStack stack = livingBase.getHeldItemMainhand();
            if (stack.getItem() instanceof ItemHammerBase) {
                event.setStrength(event.getStrength() * ((ItemHammerBase) stack.getItem()).getKnockBackFactor(stack));
            }
        }
    }

    public boolean trySmash(ItemStack stack, World worldIn, BlockPos pos, EntityPlayer player) {
        IBlockState state = worldIn.getBlockState(pos);
        return itemRand.nextFloat() <
                ((efficiency * 5) * (getPowerLevel(player) + 1) / state.getBlockHardness(worldIn, pos));

    }

    //base = 1
    public int getPowerLevel(EntityLivingBase player) {
        int result = 1
                + EntityUtil.getBuffLevelIDL(player, MobEffects.STRENGTH)
                - EntityUtil.getBuffLevelIDL(player, MobEffects.WEAKNESS);
        if (EntityUtil.getBuffLevelIDL(player, ModPotions.ANGER) > 0 || EntityUtil.getBuffLevelIDL(player, ModPotions.HAPPINESS) > 0) {
            result++;
        } else if (EntityUtil.getBuffLevelIDL(player, ModPotions.SADNESS) > 0 || EntityUtil.getBuffLevelIDL(player, ModPotions.FEAR) > 0) {
            result--;
        }

        return result;
    }

    @Override
    @MethodsReturnNonnullByDefault
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (hand == EnumHand.MAIN_HAND) {
            if (!worldIn.isRemote) {
                IBlockState state = worldIn.getBlockState(pos);
                Block block = state.getBlock();
                if (trySmash(player.getHeldItem(hand), worldIn, pos, player)) {
                    worldIn.setBlockState(pos, WorldGenUtil.AIR);
                    block.dropBlockAsItem(worldIn, pos, state, (int) player.getLuck());
                }
                activateCoolDown(player.getHeldItem(hand), player);
            } else {
                worldIn.playSound(player, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5f, 0.5f);
                worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 0, 0, 0);
            }
            return EnumActionResult.SUCCESS;
        }

        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return com.google.common.collect.ImmutableSet.of(TOOL_CLASS_HAMMER);
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        return CommonFunctions.canHarvestBlockAsPicaxe(toolMaterial.getHarvestLevel(), state);
    }

    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        Material material = state.getMaterial();
        return material != Material.ROCK && material != Material.IRON && material != Material.ICE ? super.getDestroySpeed(stack, state) : this.efficiency;
    }
}
