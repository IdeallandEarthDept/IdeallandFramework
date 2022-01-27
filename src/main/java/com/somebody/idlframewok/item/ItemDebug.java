package com.somebody.idlframewok.item;

import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.sounds.ModSoundHandler;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemDebug extends ItemBase implements IWIP{
    public ItemDebug(String name) {
        super(name);
        CommonFunctions.addToEventBus(this);
    }

    int index = 0;
    public ItemDebug SetIndex(int index){
        this.index = index;
        return this;
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        //tooltip.add(I18n.format("idlframewok.gua_enhance_total.desc", IDLSkillNBT.GetGuaEnhanceTotal(stack)));
        tooltip.add(IDLNBTUtil.getNBT(stack).toString());
    }

//    @Nullable
//    @Override
//    @SideOnly(Side.CLIENT)
//    public FontRenderer getFontRenderer(ItemStack stack) {
//        return MessupSandbox.getFontRenderer();
//    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void toolTipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() == this && event.getEntityPlayer() != null) {
//            (MessupSandbox.getFontRenderer()).colorCodeSyncWithWorld(event.getEntityPlayer().world);

            String[] formatter = new String[16];
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilderTime = new StringBuilder();

            for (int i = 0; i <= 15; i++) {
                formatter[i] = TextFormatting.values()[i] + "(龍" + ")§r";
                stringBuilder.append(formatter[i]);
            }

            for (int i = 0; i <= 15; i++) {
                int shiftedIndex = (int) (event.getEntityPlayer().getEntityWorld().getTotalWorldTime() + i) & 15;

                formatter[i] = TextFormatting.values()[i] + "(龍" + ")§r";
                stringBuilderTime.append(formatter[shiftedIndex]);
            }

            event.getToolTip().add(1, stringBuilder.toString());
            event.getToolTip().add(2, stringBuilderTime.toString());

            formatter = new String[32];
            for (int i = 0; i < 32; ++i) {
                int j = (i >> 3 & 1) * 85;
                int k = (i >> 2 & 1) * 170 + j;
                int l = (i >> 1 & 1) * 170 + j;
                int i1 = (i >> 0 & 1) * 170 + j;

                if (i == 6) {
                    k += 85;
                }

//                if (gameSettingsIn.anaglyph)
//                {
//                    int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
//                    int k1 = (k * 30 + l * 70) / 100;
//                    int l1 = (k * 30 + i1 * 70) / 100;
//                    k = j1;
//                    l = k1;
//                    i1 = l1;
//                }

                if (i >= 16) {
                    k /= 4;
                    l /= 4;
                    i1 /= 4;
                }

                formatter[i] = Integer.toHexString((k & 255) << 16 | (l & 255) << 8 | i1 & 255);
                stringBuilderTime.append(formatter[i] + " ");
            }
            event.getToolTip().add(3, stringBuilderTime.toString());
        }

    }

//    @SideOnly(Side.CLIENT)
//    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
//    {
//        tooltip.add(GetBasicDesc());
//    }

    @Override
    public String GetBasicDesc() {
        return super.GetBasicDesc();
    }

    //    public boolean isEnchantable(ItemStack stack)
//    {
//        return true;
//    }
//
//    @Override
//    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
//        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
//        if (stack.isItemEnchantable())
//        {
//            stack.addEnchantment(ModEnchantmentInit.fireBurn, 0);
//        }
//    }

    IBlockState getBlock(BlockPos pos) {
        int reminder = pos.getY() % 3;
        switch (reminder) {
            case 0:
                return Blocks.IRON_ORE.getDefaultState();
            case 1:
                return Blocks.GOLD_ORE.getDefaultState();
            case 2:
                return Blocks.DIAMOND_ORE.getDefaultState();

            default:
                throw new IllegalStateException("Unexpected value: " + reminder);
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

//        if (!worldIn.isRemote)
        {

            Block block = new Block(Material.GROUND);

            Entity entity = new EntityPig(worldIn);

            entity.setPosition(pos.getX(), pos.getY() + 1f, pos.getZ());

            worldIn.spawnEntity(entity);
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        playerIn.swingArm(handIn);

        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote) {
            worldIn.playSound(playerIn, playerIn.getPosition(), ModSoundHandler.SOUND_1, SoundCategory.PLAYERS, 1f, 1f);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }


    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        if (index != 0) {
            return multimap;
        }

        if (slot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 1024, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
}
