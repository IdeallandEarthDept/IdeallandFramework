package com.somebody.idlframewok.item.misc.customized;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.IWIP;
import com.somebody.idlframewok.item.ItemSwordBase;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.IDLGeneral;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.KILL_COUNT_DESC;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.KILL_COUNT_ITEM;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ItemBiaoSword extends ItemSwordBase implements IWIP {
    public ItemBiaoSword(String name, ToolMaterial material) {
        super(name, material);
        shiftToShowDesc = true;
    }

    int cd_tick = 10 & TICK_PER_SECOND;

    public float getAttackDamage(ItemStack stack)
    {
        int kill = getKillCount(stack);
        return (float) ((kill + 4) / 5) + kill * 0.2f + 4f;
    }

    float skill1radius = 4f;
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        int kill = getKillCount(stack);
        int stage = getStage(kill);

        EntityPlayer playerIn = (EntityPlayer) entityIn;
        EntityUtil.EnumFaction factionWielder = EntityUtil.getFaction(playerIn);

        if (stage > 0)
        {
            if (!worldIn.isRemote && worldIn.getWorldTime() % TICK_PER_SECOND == 0)
            {
                Vec3d basePos = playerIn.getPositionVector();
                List<EntityLivingBase> entities = worldIn.getEntitiesWithinAABB(EntityLiving.class,
                        IDLGeneral.ServerAABB(basePos.addVector(-skill1radius, -skill1radius, -skill1radius), basePos.addVector(skill1radius, skill1radius, skill1radius)));
                for (EntityLivingBase living: entities
                ) {
                    if (EntityUtil.getAttitude(factionWielder, living) == EntityUtil.EnumAttitude.HATE)
                    {
                        EntityUtil.ApplyBuff(living, MobEffects.SLOWNESS, 1, 2f);
                    }
                }
                //playerIn.swingArm(EnumHand.MAIN_HAND);
                //player.getCooldownTracker().setCooldown(stack.getItem(), ((ItemSkillBase) item).GetMaxTick(stack));
            }
        }
    }


    public int getKillCount(ItemStack stack)
    {
        return IDLNBTUtil.GetInt(stack, KILL_COUNT_ITEM);
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)getAttackDamage(stack), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }

    public int getStage(int kill)
    {
        if (kill < 1000)
        {
            return 0;
        }
        else if (kill < 3000) {
            return 1;
        }
        else if (kill < 10000) {
            return 2;
        }
        else if (kill < 100000){
            return 3;
        }
        else {
            return 4;
        }
    }


    @SideOnly(Side.CLIENT)
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        int kill = getKillCount(stack);
        int stage = getStage(kill);
        String key = stack.getUnlocalizedName() + ".desc." + stage;
        if (I18n.hasKey(key))
        {
            String mainDesc = I18n.format(key);
            mainDesc += " \n " + I18n.format(KILL_COUNT_DESC, kill);
            return mainDesc;
        }
        return "";
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onCreatureDied(final LivingDeathEvent ev) {
        Entity trueSource = ev.getSource().getTrueSource();

        if (trueSource != null)
        {
            if (trueSource instanceof EntityPlayer) {

                ItemStack stack = ((EntityPlayer) trueSource).getHeldItemMainhand();
                if (stack.getItem() instanceof ItemBiaoSword)
                {
                    stack.setItemDamage(0);
                    IDLNBTUtil.setInt(stack, KILL_COUNT_ITEM, IDLNBTUtil.GetInt(stack, KILL_COUNT_ITEM) + 1);
                    IDLNBTUtil.markPosToStack(stack, ev.getEntityLiving().getPosition());
                }
            }
        }
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        int kill = getKillCount(stack);
        int stage = getStage(kill);

        if (stage >= 2)
        {
            if (playerIn.isAirBorne)
            {
                if (stage >= 3)
                {
                    //dash & damage
                }
            }else if (playerIn.isSneaking()) {
                if (stage >= 4)
                {
                    //teleport
                }
            } else {
                //draw monsters near
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

}
