package com.somebody.idlframewok.item.skills.martial;

import java.util.List;

import com.somebody.idlframewok.item.IGuaEnhance;
import com.somebody.idlframewok.item.skills.ItemSkillBase;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemSkillGuaPalm extends ItemSkillMartialAttack implements IGuaEnhance {
    public float earthModifier = 0.02f;

    public ItemSkillGuaPalm(String name) {
        super(name);
        maxLevel = 1;
        setCD(2f,0);
        showGuaSocketDesc = true;
        shiftToShowDesc = true;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {
        if (isStackReady(playerIn, stack))
        {
            OnHit(stack, playerIn, target, handIn);
            if (!playerIn.world.isRemote)
            {
                activateCoolDown(playerIn, stack);
            }
            return true;
        }
        return false;
    }

    public DamageSource GetDamageSource(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn)
    {
        return new EntityDamageSource("martial", playerIn);
    }

    @Override
    public float getVal(ItemStack stack) {
        return super.getVal(stack) + IDLSkillNBT.GetGuaEnhance(stack,5) + IDLSkillNBT.GetGuaEnhance(stack,1);
    }

    //Gua
    //Sky 7
    public float getCoolDown(ItemStack stack) {
        float skyModifier = 0.1f;
        float result =  cool_down - IDLSkillNBT.GetGuaEnhance(stack, 7) * skyModifier;
        return result > 0.1f ? result : 0.1f;
    }

    //Earth 0
    @SubscribeEvent
    public static void onCreatureHurt(LivingHurtEvent evt) {

        if (evt.isCanceled())
        {
            return;
        }

        World world = evt.getEntity().getEntityWorld();
        if (!world.isRemote) {
            EntityLivingBase hurtOne = evt.getEntityLiving();

            if (hurtOne instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) hurtOne;
                ItemStack stack = player.getHeldItemMainhand();
                if (stack.getItem() instanceof ItemSkillGuaPalm)
                {
                    ItemSkillGuaPalm palm = (ItemSkillGuaPalm) stack.getItem();
                    float dmg = evt.getAmount();
                    //IdlFramework.Log("Damage reduct: %f -> %f", dmg, (1f - GetGuaEnhance(stack,0) * palm.earthModifier) * dmg);
                    evt.setAmount((1f - IDLSkillNBT.GetGuaEnhance(stack,0) * palm.earthModifier) * dmg);
                }
            }
        }
    }

    float windModifier = 0.1f;
    //6 wind
    public float getKBPower(ItemStack stack)
    {
        return  0.4f + windModifier * IDLSkillNBT.GetGuaEnhance(stack, 6);
    }

    public void OnHitBasic(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn)
    {
        if (!playerIn.world.isRemote) {
            float dmg = 0;
            int fire = IDLSkillNBT.GetGuaEnhance(stack, 5);
            int thunder = IDLSkillNBT.GetGuaEnhance(stack, 1);
            dmg = fire + (target.isWet() ? thunder * 2 : thunder);

            if (target.attackEntityFrom(GetDamageSource(stack, playerIn, target, handIn), dmg))
            {
                //wind
                target.knockBack(playerIn, getKBPower(stack), (playerIn.posX - target.posX), (playerIn.posZ - target.posZ));

                //fire
                target.setFire(fire);

                //thunder
                if (thunder >= 8)
                {
                    playerIn.world.addWeatherEffect(new EntityLightningBolt(playerIn.world, target.posX, target.posY, target.posZ, false));
                }

                playerIn.swingArm(handIn);
                if (interrupts) {
                    if (target instanceof EntityPlayer)
                    {
                        EntityPlayer player = (EntityPlayer) target;
                        ItemStack inHand = player.getHeldItemMainhand();
                        if (inHand.getItem() != null && inHand.getItem() instanceof ItemSkillBase)
                        {
                            ItemSkillBase.activateCoolDown(player, inHand);
                        }
                    }
                }
            }

        }
    }

    public void OnHitExtra(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn)
    {
        int mountain = IDLSkillNBT.GetGuaEnhance(stack, 4);
        int water = IDLSkillNBT.GetGuaEnhance(stack, 2);
        int lake = IDLSkillNBT.GetGuaEnhance(stack, 3);

        if (mountain > 0)
        {
            target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, CommonDef.TICK_PER_SECOND * mountain * 2, mountain / 4));
        }

        if (water > 0)
        {
            target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, CommonDef.TICK_PER_SECOND * water * 2, water / 4));
            target.extinguish();
        }

        if (lake > 0)
        {
            target.heal(lake);
            playerIn.heal(lake);
        }
    }

    @Override
    public boolean acceptGuaIndex(int index) {
        return true;
    }

    public BuffTuple GetWaterBuff(ItemStack stack)
    {
        int water = IDLSkillNBT.GetGuaEnhance(stack, 2);
        return new BuffTuple(water / 4, CommonDef.TICK_PER_SECOND * water * 2);
    }

    public BuffTuple GetMountainBuff(ItemStack stack)
    {
        int gua = IDLSkillNBT.GetGuaEnhance(stack, 4);
        return new BuffTuple(gua / 4, CommonDef.TICK_PER_SECOND * gua * 2);
    }

    @SideOnly(Side.CLIENT)
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        //todo:highlight while upgrading
        String key = stack.getUnlocalizedName() + ".desc";
        if (I18n.hasKey(key))
        {
            BuffTuple mountain = GetMountainBuff(stack);
            BuffTuple water = GetWaterBuff(stack);
            int fire = IDLSkillNBT.GetGuaEnhance(stack, 5);


            return I18n.format(stack.getUnlocalizedName() + ".desc",
                    (IDLSkillNBT.GetGuaEnhance(stack, 0) * earthModifier * 100f),
                    IDLSkillNBT.GetGuaEnhance(stack, 1),
                    water.power + 1, (water.tick / CommonDef.TICK_PER_SECOND),
                    IDLSkillNBT.GetGuaEnhance(stack, 3),
                    mountain.power + 1, (mountain.tick / CommonDef.TICK_PER_SECOND),
                    fire, fire,
                    IDLSkillNBT.GetGuaEnhance(stack, 6)
                    //GetGuaEnhance(stack, 7)
            );
        }
        return "";
    }
}
