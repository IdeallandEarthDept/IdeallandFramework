package com.somebody.idlframewok.item.artifact;

import com.somebody.idlframewok.advancements.AdvancementKeys;
import com.somebody.idlframewok.advancements.ModAdvancementsInit;
import com.somebody.idlframewok.item.skills.ItemSkillBase;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.SECOND_PER_TURN;
import static com.somebody.idlframewok.util.MessageDef.MSG_SOVEREIGN_FAIL;

public class ItemJadeSeal extends ItemSkillBase implements IArtifact{
    public ItemJadeSeal(String name) {
        super(name);
        useable = true;
        setMaxLevel(1);
        setCD(SECOND_PER_TURN * 2f,0);
        setGlitter();
        setRarity(EnumRarity.EPIC);
    }

    public void fortifyBuff(EntityLivingBase livingBase, Potion effect)
    {
        int level = EntityUtil.getBuffLevelIDL(livingBase, effect);
        EntityUtil.ApplyBuff(livingBase, effect, Math.min(level, 3), SECOND_PER_TURN * 3);
    }


    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot)
    {
        World world = livingBase.world;

        if (world.isRemote)
        {
            return true;
        }

        int dimID = world.provider.getDimension();
        if (dimID == IDLNBTUtil.GetInt(stack, IDLNBTDef.IMPRINT, 0))
        {
            boolean result = super.applyCast(worldIn,livingBase, stack, slot);
            if (result)
            {
                fortifyBuff(livingBase, ModPotions.CRIT_DMG_PLUS);
                fortifyBuff(livingBase, ModPotions.CRIT_CHANCE_PLUS);
                fortifyBuff(livingBase, MobEffects.STRENGTH);

                EntityUtil.ApplyBuff(livingBase, ModPotions.INVINCIBLE,0, SECOND_PER_TURN);
                livingBase.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 1f, 1f);
                if (livingBase instanceof EntityPlayer)
                {
                    ModAdvancementsInit.giveAdvancement((EntityPlayer) livingBase, AdvancementKeys.H_USE_SEAL);
                }
            }
            return result;
        }
        else {
            CommonFunctions.SafeSendMsgToPlayer(livingBase, MSG_SOVEREIGN_FAIL);
            return false;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getFormatResult(String key, ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        return I18n.format(key, IDLNBTUtil.GetInt(stack, IDLNBTDef.IMPRINT, 0));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return TextFormatting.GOLD + I18n.format( this.getUnlocalizedNameInefficiently(stack) + ".name", IDLNBTUtil.GetString(stack, IDLNBTDef.IMPRINT_DISP, CommonDef.EMPTY)).trim();
    }

    //    public void
//
//    public static boolean getDimNbtIDL(World world)
//    {
//        NBTTagCompound nbttagcompound = world.getWorldInfo().getDimensionData(world.provider.getDimension());
//        this.dragonFightManager = world instanceof WorldServer ? new DragonFightManager((WorldServer)world, nbttagcompound.getCompoundTag("DragonFight")) : null;
//    }
}
