package com.somebody.idlframewok.item.skills.arknights;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.skills.ItemSkillBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.MessageDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.somebody.idlframewok.util.CommonDef.METER_PER_BLOCK;
import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.*;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ItemSkillRuleOS extends ItemSkillBase {
    public ItemSkillRuleOS(String name) {
        super(name);
        maxLevel = 10;
        CommonFunctions.addToEventBus(this);
        offHandCast = true;
        cannotMouseCast = true;
        showRangeDesc =false;
        setCD(5f, 0f);
        maxDialogues = 4;
    }

    //Rules of Survival(SilverAsh)
    UUID uuid = UUID.fromString("535b9e15-f12b-4ef2-b667-9c2d52defbaa");

    //increases defence
    float[] defRatio = {0.35f, 0.40f, 0.45f, 0.50f, 0.55f, 0.60f, 0.65f, 0.75f, 0.85f, 1.00f};
    //percentage per second. 1 = 1% persecond
    double[] recovery = {3, 3, 3, 3.5, 3.5, 3.5, 4, 4.5, 5, 6};

    //if out of this range, attack will be ignored
    float maxSqDist = (1.5f * METER_PER_BLOCK) * (1.5f * METER_PER_BLOCK);

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase living = (EntityLivingBase) entityIn;
            if (isActive(living))
            {
                if (worldIn.isRemote)
                {
                    Random rand = ((EntityLivingBase) entityIn).getRNG();
                    for (int i = 0; i < 8; ++i)
                    {
                        worldIn.spawnParticle(EnumParticleTypes.SNOW_SHOVEL,
                                entityIn.posX + (((EntityLivingBase) entityIn).getRNG().nextDouble() - 0.5D) * (double)entityIn.width,
                                entityIn.posY + rand.nextDouble() * (double)entityIn.height - 0.25D,
                                entityIn.posZ + (rand.nextDouble() - 0.5D) * (double)entityIn.width,
                                0,//(rand.nextDouble() - 0.5D) * 2.0D,
                                0,//-rand.nextDouble(),
                                0);// (rand.nextDouble() - 0.5D) * 2.0D);
                    }
                }else {
                    float heal = (float) (recovery[IDLSkillNBT.getLevel(stack) - 1] * living.getMaxHealth() / (100f * TICK_PER_SECOND));

                    living.heal(heal);
                }

            }
        }
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {

        super.applyCast(worldIn, livingBase, stack, slot);

        int curState = IDLNBTUtil.GetInt(stack, STATE);

        IDLNBTUtil.setInt(stack, STATE, 1 - curState);

        trySayDialogue(livingBase, stack);

        return true;
    }

    public static boolean isActive(EntityLivingBase source)
    {
        if (source == null)
        {
            return false;
        }

        ItemStack stack = source.getHeldItemOffhand();
        Item offHand = source.getHeldItemOffhand().getItem();
        if (offHand instanceof ItemSkillRuleOS)
        {
            if (IDLNBTUtil.GetInt(stack, STATE, 0) > 0)
            {
                return true;
            }
        }
        return false;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        boolean isOn = IDLNBTUtil.GetInt(stack, STATE, 0) > 0;

        if (isOn && equipmentSlot == EntityEquipmentSlot.OFFHAND)
        {
            //todo: reduce movement speed
            int level = IDLNBTUtil.GetInt(stack, LEVEL);
            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(uuid, "Rule of Survival Modifier", (double)defRatio[level], 1));
        }

        return multimap;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onCreatureHurt(LivingHurtEvent evt) {
        EntityLivingBase victim = evt.getEntityLiving();
        if (victim.world.isRemote)
        {
            return;
        }

        Entity source = evt.getSource().getTrueSource();

        if (source instanceof  EntityLivingBase)
        {
            EntityLivingBase sourceCreature = (EntityLivingBase) source;
            if (isActive(sourceCreature) && source.getDistanceSq(victim) > maxSqDist)
            {
                if (sourceCreature instanceof EntityPlayer)
                {
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GRAY, (EntityPlayer) source, MessageDef.OUT_OF_RANGE);
                }

                evt.setAmount(0);
                evt.setCanceled(true);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        boolean isOn = IDLNBTUtil.GetInt(stack, STATE, 0) > 0;
        return super.getItemStackDisplayName(stack) + (isOn ? I18n.format(NAME_ON) : I18n.format(NAME_OFF));
    }

    @SideOnly(Side.CLIENT)
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        String key = stack.getUnlocalizedName() + ".desc";
        if (I18n.hasKey(key))
        {
            int level = IDLSkillNBT.getLevel(stack) - 1;
            String mainDesc = I18n.format(key, defRatio[level] * 100f , (float) (recovery[level]));
            return mainDesc;
        }
        return "";
    }
}
