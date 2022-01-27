package com.somebody.idlframewok.potion;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.potion.instance.*;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModPotions {
    public static final List<Potion> INSTANCES = new ArrayList<Potion>();
    public static final List<PotionType> TYPE_INSTANCES = new ArrayList<>();

    //Legacy
    public static final PotionDeadly DEADLY = new PotionDeadly(false, 0x333333, "deadly", 0);
    public static final PotionZenHeart ZEN_HEART = new PotionZenHeart(false, 0xcccc00, "zen_heart", 1);
    public static final PotionInsidiousDisease VIRUS_ONE = new PotionInsidiousDisease(true, 0xffffff, "virus_one", 2);

    public static final PotionUndying UNDYING = new PotionUndying(false, 0xffff00, "undying", 3);

    public static final PotionMagicResist MAGIC_RESIST = new PotionMagicResist(false, 0x0000ff, "magic_resist", 4);
    public static final PotionBlastResist BLAST_RESIST = new PotionBlastResist(false, 0x440000, "blast_resist", 5);
    public static final ModPotionAttr KB_RESIST = new ModPotionAttr(false, 0x99ff99, "knockback_resist", 6).setKBResistance(0.25f, 0.25f);
    public static final ModPotionAttr RIP = new ModPotionAttr(false, 0xffffff, "rest_in_peace", 7);

    public static final PotionErosion EROSION = new PotionErosion(true, 0x22ff33, "erosion", 8);
    public static final ModPotionAttr INVINCIBLE = new ModPotionAttr(false, 0xffffbb, "invincible", 9);

    public static final ModPotionBase INTERFERENCE = new ModPotionBase(true, 0x2266ff, "interference", 10);

    public static final ModPotionBase NOTICED_BY_MOR = new ModPotionBase(true, 0x882288, "noticed_by_mor", 11);

    public static final ModPotionAttr CRIT_CHANCE_PLUS = new ModPotionAttr(true, 0xff3333, "crit_chance_plus", 12).setCritRateRatio(0.25f, 0.25f);
    public static final ModPotionAttr CRIT_CHANCE_MINUS = new ModPotionAttr(true, 0xffffff, "crit_chance_minus", 13).setCritRateRatio(-0.25f, -0.25f);

    public static final ModPotionAttr CRIT_DMG_PLUS = new ModPotionAttr(true, 0xff3333, "crit_dmg_plus", 14).setCritDamageRatio(0.25f, 0.25f);
    public static final ModPotionAttr CRIT_DMG_MINUS = new ModPotionAttr(true, 0xffffff, "crit_dmg_minus", 15).setCritDamageRatio(-0.25f, -0.25f);

    public static final ModPotionAttr BERSERK = new ModPotionAttr(true, 0xffffff, "berserk", 16).setAttackRatio(0.25f, 0.25f).setProtectionRatio(-0.25f, -0.25f);

    public static final ModPotionBase HOT = new ModPotionBase(true, 0xff6622, "hot", 17);
    public static final ModPotionBase COLD = new ModPotionBase(true, 0x3333ff, "cold", 18);

    public static final ModPotionBase HEAL_PLUS = new ModPotionBase(false, 0x3366ff, "heal_plus", 19);
    public static final ModPotionBase HEAL_MINUS = new ModPotionBase(true, 0x113366, "heal_minus", 20);

    public static final ModPotionBase SPELL_PLUS = new ModPotionBase(false, 0x33ff66, "spell_plus", 21);
    public static final ModPotionBase SPELL_MINUS = new ModPotionBase(true, 0x116633, "spell_minus", 22);

    public static final ModPotionBase WATER_BLESS = new ModPotionBase(false, 0x3c44aa, "water_bless", 23);
    public static final ModPotionBase OOW_BLESS = new ModPotionBase(false, 0xcccccc, "oow_bless", 24);

    public static final ModPotionBase BURN = new ModPotionBase(true, 0xcc3333, "burning", 25);

    public static final ModPotionAttrClassicalBase MINUS_ARMOR_PERCENTAGE = (ModPotionAttrClassicalBase) new ModPotionAttrClassicalBase(true, 0xffffff, "armor_minus_percent", 26)
            .setVal(-0.25f)
            .registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR, "f4420891-431e-4539-9253-c23ef72feba7", 0.0D, 1);//todo: need pics and testing

    public static final ModPotionAttrClassicalBase PLUS_ARMOR_PERCENTAGE = (ModPotionAttrClassicalBase) new ModPotionAttrClassicalBase(false, 0xffffff, "armor_plus_percent", 27)
            .setVal(0.25f)
            .registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR, "f2c40362-63c4-4bc7-9142-a475b10d1030", 0.0D, 1);

    //todo: uuid
    public static final ModPotionBase FROZEN = (ModPotionBase) new ModPotionAttrClassicalBase(true, 0x33cc33, "frozen", 18)
            .setVal(-0.25f)
            .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "866044f9-f0fc-4249-a293-d5122df71063", 0.0D, 2);

    public static final ModPotionBase HAPPINESS = (ModPotionBase) new ModPotionAttrClassicalBase(false, 0xeeee22, "happiness", 28)
            .setVal(0.15f)
            .registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, "8e73cb0a-4223-4c74-b9b8-89346fd5c23a", 0.0D, 1)
            .registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR, "8e73cb0a-4223-4c74-b9b8-89346fd5c23a", -1f, 1);//-1 here just indicate the sign. value is decided by setVal

    public static final ModPotionBase ANGER = (ModPotionBase) new ModPotionAttrClassicalBase(false, 0xee2222, "anger", 29)
            .setVal(0.15f)
            .registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "3fdc7674-d0bf-43e9-9fe4-927032a0ef8a", 0.0D, 1)
            .registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, "3fdc7674-d0bf-43e9-9fe4-927032a0ef8a", -1f, 1);

    public static final ModPotionBase SADNESS = (ModPotionBase) new ModPotionAttrClassicalBase(true, 0x2222ee, "sadness", 30)
            .setVal(0.15f)
            .registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR, "5ea55210-beac-4a85-a4c7-f2786ae43703", 0.0D, 1)
            .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "5ea55210-beac-4a85-a4c7-f2786ae43703", -1f, 1);

    public static final ModPotionBase FEAR = (ModPotionBase) new ModPotionAttrClassicalBase(true, 0xee22ee, "fear", 31)
            .setVal(0.15f)
            .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "8ead7042-5a7e-4ce4-b282-08db8cddf3e7", 0.0D, 1)
            .registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "8ead7042-5a7e-4ce4-b282-08db8cddf3e7", -1f, 1);

    public static final ModPotionBase STINK = new ModPotionBase(true, 0x66cc22, "stink", 33);

    public static boolean hasFeeling(EntityLivingBase livingBase)
    {
        return livingBase.getActivePotionEffect(HAPPINESS) != null ||
                livingBase.getActivePotionEffect(ANGER) != null ||
                livingBase.getActivePotionEffect(SADNESS) != null ||
                livingBase.getActivePotionEffect(FEAR) != null;
    }

    //todo:follow range bonus

    @Nullable
    private static Potion getRegisteredMobEffect(String id)
    {
        Potion potion = Potion.REGISTRY.getObject(new ResourceLocation(id));

        if (potion == null)
        {
            throw new IllegalStateException("Invalid MobEffect requested: " + id);
        }
        else
        {
            return potion;
        }
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> evt)
    {
        modifyVanillaPotions();

        VIRUS_ONE.tuples.add(new EffectTuple(0.2f, MobEffects.NAUSEA, 100));

        evt.getRegistry().registerAll(INSTANCES.toArray(new Potion[0]));

        Idealland.Log("registered %d potion effect(s)", INSTANCES.size());
    }

    static void modifyVanillaPotions(){
        MobEffects.BLINDNESS
                .registerPotionAttributeModifier(SharedMonsterAttributes.FOLLOW_RANGE, "0a33a75f-a15b-445e-90b1-c96817cdd63b", -ModConfig.GeneralConf.BLIND_FOR_MOBS, 2);
    }

    @SubscribeEvent
    public static void registerPotionTypes(RegistryEvent.Register<PotionType> evt) {
        InitPotionTypes.init();
        evt.getRegistry().registerAll(TYPE_INSTANCES.toArray(new PotionType[0]));
        Idealland.Log("registered %d potion item(s)", TYPE_INSTANCES.size());
    }
}
