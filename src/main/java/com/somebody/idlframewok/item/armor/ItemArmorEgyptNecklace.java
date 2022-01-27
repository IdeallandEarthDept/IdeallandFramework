package com.somebody.idlframewok.item.armor;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.designs.god16.Init16Gods;
import com.somebody.idlframewok.item.ItemArmorBase;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.item.artifact.IArtifact;
import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.somebody.idlframewok.util.MessageDef.AMK_NECKLACE;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.*;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ItemArmorEgyptNecklace extends ItemArmorBase implements IArtifact {

    final int STANDARD_BELIEF = 100;
    static final int MAX_STATE = 5;

    public ItemArmorEgyptNecklace(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        setRarity(EnumRarity.EPIC);
    }

    @Override
    public String GetStringForThisByKey(String key) {
        return super.GetStringForThisByKey(key);
    }

    @Override
    public void onUpdateWearing(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdateWearing(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote && entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase creature = (EntityLivingBase) entityIn;
            switch (IDLNBTUtil.GetInt(stack, IDLNBTDef.STATE))
            {
                case 0:
                    IDLNBTUtil.setInt(stack, IDLNBTDef.STATE, 1);
                    initStack(stack);
                    if (Init16Gods.getBelief(creature, Color16Def.GOLD) < STANDARD_BELIEF)
                    {
                        Init16Gods.setBelief(creature, Color16Def.GOLD, STANDARD_BELIEF);
                    }
                    CommonFunctions.SafeSendMsgToPlayer(creature, AMK_NECKLACE, TextFormatting.GOLD);
                    break;
                case 1:

                    break;
                default:
                    break;
            }
            IDLNBTUtil.setIntOptimized(stack, STATE_2, Init16Gods.getBelief(creature, Color16Def.GOLD));
            CommonFunctions.repairItem(stack, 1);
        }
    }

    static void initStack(ItemStack stack)
    {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.BINDING_CURSE, stack) == 0)
        {
            stack.addEnchantment(Enchantments.BINDING_CURSE, 1);
        }
    }

    //last over death
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onDeath(LivingDeathEvent evt) {
        if (evt.getEntityLiving() instanceof EntityPlayer && !evt.getEntityLiving().world.isRemote && !evt.isCanceled()) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            if (stack.getItem() instanceof ItemArmorEgyptNecklace)
            {
                IDLNBTUtil.setIntAuto(player, EGYPT_KEY, Math.min(MAX_STATE, IDLNBTUtil.GetInt(stack, STATE) + 1));
                player.setItemStackToSlot(EntityEquipmentSlot.CHEST, ItemStack.EMPTY);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRevive(PlayerEvent.PlayerRespawnEvent evt) {
        if (!evt.player.world.isRemote && !evt.isCanceled()) {
            EntityPlayer player = evt.player;
            int state = IDLNBTUtil.GetIntAuto(player, EGYPT_KEY, 0);
            if (state > 0)
            {
                ItemStack stack = new ItemStack(ModItems.AMK_NECKLACE);
                initStack(stack);
                IDLNBTUtil.setInt(stack, STATE, state);
                player.setItemStackToSlot(EntityEquipmentSlot.CHEST, stack);

                CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GOLD,player, AMK_NECKLACE);
                IDLNBTUtil.setIntAuto(player, EGYPT_KEY, 0);
            }
        }
    }


    final float modifier = 1000f;
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if ((equipmentSlot == this.armorType))
        {
            int state = IDLNBTUtil.GetInt(stack, IDLNBTDef.STATE);
            int state2 = IDLNBTUtil.GetInt(stack, STATE_2);
            if (state != 0)
            {
                multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Charm", state2 / modifier, 2));
                if (state > 2)
                {
                    multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Charm", state2 / modifier, 2));
                    if (state > 3)
                    {
                        multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Charm", state2 / modifier, 2));
                        if (state > 4)
                        {
                            multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()],"Charm", state2 / modifier, 2));
                        }
                    }
                }

                multimap.put(SharedMonsterAttributes.LUCK.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Charm",  state > 1 ? 1f : -0.5f, 2));
            }
            else {
                multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Charm", 1, 2));
            }
        }

        return multimap;
    }

    @SideOnly(Side.CLIENT)
    public String descGetKey(ItemStack stack, World world, boolean showFlavor)
    {
        if (showFlavor)
        {
            return stack.getUnlocalizedName() + IDLNBTDef.FLAVOR_KEY;
        }else
        {
            if (IDLNBTUtil.GetInt(stack, STATE) == 0)
            {
                return UNKNOWN;
            }
            else {
                return stack.getUnlocalizedName() + IDLNBTDef.DESC_COMMON;
            }
        }
    }
}
