package com.somebody.idlframewok.util;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.item.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.Calendar;
import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_DAY;
import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class CommonFunctions {

    public static final int MOON_DIE = 167;
    public static final int NIGHT_BEGIN = 13000;

    public static void init(Block block, String name) {
        block.setUnlocalizedName(name);
        block.setRegistryName(name);
        block.setCreativeTab(ModCreativeTabsList.IDL_MISC);
        ModBlocks.BLOCKS.add(block);

    }

    public static float invLerpUnclamped(float val, float min, float max)
    {
        //prevent div0
        if (max == min)
        {
            return 1f;
        }
        return (val-min) / (max-min);
    }

    public static float lerpUnclamped(float val, float min, float max)
    {
        return min + val * (max - min);
    }

    public static float clamp(float val, float min, float max)
    {
        if (val <= min)
        {
            return min;
        }
        else if (val >= max)
        {
            return max;
        }
        return val;
    }

    public static int clamp(int val, int min, int max)
    {
        if (val <= min)
        {
            return min;
        }
        else if (val >= max)
        {
            return max;
        }
        return val;
    }

    public static EntityEquipmentSlot slotFromHand(EnumHand hand)
    {
        return hand == EnumHand.OFF_HAND ? EntityEquipmentSlot.OFFHAND : EntityEquipmentSlot.MAINHAND;
    }

    public static EnumHand handFromSlot(EntityEquipmentSlot hand)
    {
        return hand == EntityEquipmentSlot.OFFHAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
    }

    public static boolean isVertical(EnumFacing facing) {
        return facing == EnumFacing.DOWN || facing == EnumFacing.UP;
    }

    public static boolean isZDir(EnumFacing facing) {
        return facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH;
    }

    public static boolean isXDir(EnumFacing facing) {
        return facing == EnumFacing.EAST || facing == EnumFacing.WEST;
    }

    public static boolean isNegativeDir(EnumFacing facing) {
        return facing.getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;
    }

    public static boolean isPositiveDir(EnumFacing facing) {
        return facing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE;
    }

    public static ItemStack copyAndSetCount(@Nullable ItemStack stack, int count)
    {
        if (stack == null || stack.isEmpty())
        {
            return ItemStack.EMPTY;
        }

        ItemStack result = stack.copy();
        result.setCount(count);
        return result;
    }

    public static double flunctate(double ori, double radius, Random random)
    {
        return ori + (random.nextFloat() * 2 - 1) * radius;
    }

    public static boolean isSecondTick(World world)
    {
        return world.getTotalWorldTime() % TICK_PER_SECOND == 0;
    }

    public static boolean isMoonTime(World world) {
        int MOON_BIRTH = (int) (world.getWorldTime() % TICK_PER_DAY);
        return MOON_BIRTH <= MOON_DIE || MOON_BIRTH >= 11834;
    }

    public static boolean isNightTime(World world) {
        int tickInDay = (int) (world.getWorldTime() % TICK_PER_DAY);
        return tickInDay >= NIGHT_BEGIN;
    }

    static float divider = 52f/9f;
    public static float boomRange(int power)
    {
        return (float) (Math.floor( divider * power ) * 0.3f);
    }

    public static float boomRange(float power)
    {
        return (float) (Math.floor( divider * power ) * 0.3f);
    }
//    public static String writeItemStackToString()
//    {
//        if (stack.isEmpty())
//        {
//            this.writeShort(-1);
//        }
//        else
//        {
//            this.writeShort(Item.getIdFromItem(stack.getItem()));
//            this.writeByte(stack.getCount());
//            this.writeShort(stack.getMetadata());
//            NBTTagCompound nbttagcompound = null;
//
//            if (stack.getItem().isDamageable() || stack.getItem().getShareTag())
//            {
//                nbttagcompound = stack.getItem().getNBTShareTag(stack);
//            }
//
//            this.writeCompoundTag(nbttagcompound);
//        }
//    }

    public static void teleportToDimension(EntityPlayer player, int dimension, double x, double y, double z)
    {
        int oldDimension = player.getEntityWorld().provider.getDimension();
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
        MinecraftServer server = player.getEntityWorld().getMinecraftServer();
        WorldServer worldServer = server.getWorld(dimension);
        player.addExperienceLevel(0);

        if (worldServer == null || worldServer.getMinecraftServer() == null)
        {
            throw new IllegalArgumentException("Dimension: "+dimension+" doesn't exist!");
        }

        //todo
        //worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new CustomTeleporter(worldServer, x, y, z));
        player.setPositionAndUpdate(x, y, z);
    }

    public static float GetTemperatureHere(Entity creature)
    {
        BlockPos pos = creature.getPosition();
        World world = creature.getEntityWorld();
        Biome biome = world.getBiomeForCoordsBody(pos);
        return biome.getTemperature(pos);
    }

    public static float GetTemperatureHere(BlockPos pos, World world)
    {
        Biome biome = world.getBiomeForCoordsBody(pos);
        return biome.getTemperature(pos);
    }

    public static int SecondToTicks(int ticks) {
        return ticks * TICK_PER_SECOND;
    }

    public static int SecondToTicks(float ticks) {
        return (int)(ticks * TICK_PER_SECOND);
    }

    public static void broadcastbykey(String key, Object... args) {
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentTranslation(key, args));
    }

    public static void broadCastByKey(TextFormatting formatting, String key, Object... args) {
        TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(key, args);
        textcomponenttranslation.getStyle().setColor(formatting);
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(textcomponenttranslation);
    }

    public static void SafeSendMsgToPlayer(EntityLivingBase player, String key, Object... args)
    {
        //Please note that you can only put %s as arguments. If you put %d, it's not going to translate.
        if ((!player.world.isRemote) && player instanceof EntityPlayerMP)
        {
            player.sendMessage((new TextComponentTranslation(key, args)));
        }
    }

    public static void SafeSendMsgToPlayer(TextFormatting style, EntityLivingBase player, String key, Object... args)
    {
        //Please note that you can only put %s as arguments. If you put %d, it's not going to translate.
        if (player instanceof EntityPlayerMP)
        {
            TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(key, args);
            textcomponenttranslation.getStyle().setColor(style);
            player.sendMessage(textcomponenttranslation);
        }
    }

    public static void SendMsgToPlayer(EntityPlayerMP playerMP, String key)
    {
        playerMP.sendMessage(new TextComponentTranslation(key));
    }

    public static void SendMsgToPlayerStyled(EntityPlayerMP playerMP, String key, TextFormatting style, Object... args)
    {
        TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(key, args);
        textcomponenttranslation.getStyle().setColor(style);
        playerMP.sendMessage(textcomponenttranslation);
    }


    public static void SendMsgToPlayer(EntityPlayerMP playerMP, String key, Object... args)
    {
        playerMP.sendMessage((new TextComponentTranslation(key, args)));
    }

    @SideOnly(Side.CLIENT)
    public static String GetStringLocalTranslated(String key) {
        //return "WIP";
        return I18n.format(key);

    }

    public static void FillWithBlockCornered(World world, BlockPos origin, int lengthX, int lengthY, int lengthZ, IBlockState newState) {
        BlockPos target;
        for(int x = 0;
            x < lengthX;x++) {
            for (int y = 0; y < lengthY; y++) {
                for (int z = 0; z < lengthZ; z++) {
                    target = origin.add(x, y, z);
                    //IBlockState targetBlock = world.getBlockState(target);
                    world.setBlockState(target, newState);
                }
            }
        }
    }

    public static void FillWithBlockCentered(World world, BlockPos origin, int rangeX, int rangeY, int rangeZ, IBlockState newState) {
        BlockPos target;
        for(int x = -rangeX;
        x <=rangeX;x++) {
            for (int y = -rangeY; y <= rangeY; y++) {
                for (int z = -rangeZ; z <= rangeZ; z++) {
                    target = origin.add(x, y, z);
                    //IBlockState targetBlock = world.getBlockState(target);
                    world.setBlockState(target, newState);
                }
            }
        }
    }

    public static void BuildWallWithBlockCentered(World world, BlockPos origin, int rangeX, int height, int rangeZ, IBlockState newState) {
        BlockPos target;
        for(int x = -rangeX;
            x <=rangeX;x++) {
            for (int y = 0; y < height; y++) {
                for (int z = -rangeZ; z <= rangeZ; z++) {
                    target = origin.add(x, y, z);
                    //IBlockState targetBlock = world.getBlockState(target);
                    world.setBlockState(target, newState);
                }
            }
        }
    }

    public static void LogPlayerAction(EntityLivingBase living, String action){
        Idealland.Log(String.format("%s(%s): %s",living.getName(), living.getUniqueID(), action));
    }

    public static boolean repairItem(ItemStack stack, int amount)
    {
        if (!stack.isItemStackDamageable())
        {
            return false;
        }
        else {
            int newVal = stack.getItemDamage() - amount;
            //if (newVal < 0) newVal = 0;
            stack.setItemDamage(newVal);
            return true;
        }
    }

    public static void CopyNormalAttr(EntityLivingBase ori, EntityLivingBase to)
    {
        CopyAttr(ori, to, SharedMonsterAttributes.FOLLOW_RANGE);
        CopyAttr(ori, to, SharedMonsterAttributes.MAX_HEALTH);
        CopyAttr(ori, to, SharedMonsterAttributes.MOVEMENT_SPEED);
    }


    public static void CopyAttr(EntityLivingBase ori, EntityLivingBase to, IAttribute attrType)
    {
        to.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(ori.getEntityAttribute(attrType).getAttributeValue());
    }

    public static int XPForLevel(int lv)
    {
        /**
         * This method returns the cap amount of experience that the experience bar can hold. With each level, the
         * experience cap on the player's experience bar is raised by 10.
         */

            if (lv >= 30)
            {
                return 112 + (lv - 30) * 9;
            }
            else
            {
                return lv >= 15 ? 37 + (lv - 15) * 5 : 7 + lv * 2;
            }

    }


    public static boolean TryConsumePlayerXP(EntityPlayer player, int XP)
    {
        //float curXP = player.experience;
        float curLv = player.experienceLevel;
        float costLeft = XP;

        int playerTotalXP = 0;
        for (int i = 1; i <= curLv; i++)
        {
            playerTotalXP += XPForLevel(i);
        }
        playerTotalXP += player.experience;

        if (playerTotalXP < XP)
        {
            //not enough
            return false;
        }

        //todo not working here
        while (costLeft > 0 && (player.experienceLevel > 0 || player.experience > 0))
        {
            if (player.experience > costLeft)
            {
                player.experience -= costLeft;
                costLeft = 0;
                Idealland.Log("A");
            }
            else {
                costLeft -= player.experience;
                Idealland.Log("B");
                if (player.experienceLevel > 0)
                {
                    player.experienceLevel--;
                    player.experience = XPForLevel(player.experienceLevel);
                    Idealland.Log(String.format("player.experience = %d", XPForLevel(player.experienceLevel)));
                }
            }
        }
        Idealland.Log(String.format("Lv= %s, xp = %s", player.experienceLevel, player.experience));
        return true;
    }

    public static boolean isItemRangedWeapon(ItemStack stack)
    {
        if (stack.isEmpty())
        {
            return false;
        }

        Item item = stack.getItem();
        if (item instanceof ItemBow)
        {
            return true;
        }

        if (item instanceof ItemBase)
        {
            return ((ItemBase) item).isRangedWeaponItem();
        }
        return false;
    }

    public static void WriteGraveToSign(EntityPlayer player, World world, TileEntity tileEntity1) {
        if (tileEntity1 instanceof TileEntitySign)
        {
            WriteGraveToSign(player.getDisplayName(), world, tileEntity1);
        }
    }

    private static void WriteGraveToSign(ITextComponent name, World world, TileEntity tileEntity1) {
        if (tileEntity1 instanceof TileEntitySign)
        {
            ((TileEntitySign) tileEntity1).signText[0] = name;
            ((TileEntitySign) tileEntity1).signText[1] = new TextComponentString("R.I.P.");

            Calendar calendar = world.getCurrentDate();
            Idealland.Log("calendar:", calendar);
            ((TileEntitySign) tileEntity1).signText[2] = new TextComponentString(
                    CommonDef.formatDate.format(calendar.getTime())
            );

            ((TileEntitySign) tileEntity1).signText[3] = new TextComponentString(
                    CommonDef.formatTime.format(calendar.getTime())
            );
        }
    }

    @SideOnly(Side.CLIENT)
    public static boolean isShiftPressed()
    {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    public static void sendBasicMsg(ItemStack stack, EntityPlayer player, int index)
    {
        if (player.world.isRemote)
        {
            return;
        }

        SendMsgToPlayer((EntityPlayerMP) player, String.format("%s.msg.%d", stack.getUnlocalizedName(), index));
    }

    public static void addToEventBus(Object target)
    {
        MinecraftForge.EVENT_BUS.register(target);
    }

    public static ResourceLocation getResLoc(String key)
    {
        return new ResourceLocation(Idealland.MODID, key);
    }

    public static Vec3d getVecFromBlockPos(BlockPos pos)
    {
        return new Vec3d(pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f);
    }

    public static boolean isRangedWeaponItem(Item item)
    {
        return item instanceof ItemBow || (item instanceof ItemBase && ((ItemBase) item).isRangedWeaponItem());
    }

    public static boolean isRangedWeaponItem(ItemStack stack)
    {
        return isRangedWeaponItem(stack.getItem());
    }

    public static boolean canHarvestBlockAsPicaxe(int harvestLevel, IBlockState blockIn) {
        Block block = blockIn.getBlock();

        if (block == Blocks.OBSIDIAN) {
            return harvestLevel == 3;
        } else if (block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE) {
            if (block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK) {
                if (block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE) {
                    if (block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE) {
                        if (block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE) {
                            if (block != Blocks.REDSTONE_ORE && block != Blocks.LIT_REDSTONE_ORE) {
                                Material material = blockIn.getMaterial();

                                if (material == Material.ROCK) {
                                    return true;
                                } else if (material == Material.IRON) {
                                    return true;
                                } else {
                                    return material == Material.ANVIL;
                                }
                            } else {
                                return harvestLevel >= 2;
                            }
                        } else {
                            return harvestLevel >= 1;
                        }
                    } else {
                        return harvestLevel >= 1;
                    }
                } else {
                    return harvestLevel >= 2;
                }
            } else {
                return harvestLevel >= 2;
            }
        } else {
            return harvestLevel >= 2;
        }
    }

    public static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
        float f;

        for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F) {
            ;
        }

        while (f >= 180.0F) {
            f -= 360.0F;
        }

        return prevYawOffset + partialTicks * f;
    }
}
