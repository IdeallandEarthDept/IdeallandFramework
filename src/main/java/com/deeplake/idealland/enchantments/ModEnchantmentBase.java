package com.deeplake.idealland.enchantments;

import com.deeplake.idealland.Idealland;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentVanishingCurse;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class ModEnchantmentBase extends Enchantment {
    private int maxLevel = 1;

    private float base_val = 0f;
    private float per_level = 0f;

    private float rarityBaseMultiplier = 1f;
    private float rarityDeltaMultiplier = 1f;

    private Enchantment[] conflicts = new Enchantment[]{};

    private boolean isHidden = false;

    //failed attempt to inter-mod compatible
    //private Enchantment shareConflicts = null;

    //values get set
//    public void setSimulateConflictForVanilla(Enchantment ench)
//    {//doesn't work since canApplyTogether is protected
//        shareConflicts = ench;
//    }

    public ModEnchantmentBase setHidden(boolean val)
    {
        isHidden = val;
        return this;
    }

    public ModEnchantmentBase setMaxLevel(int maxLevel)
    {
        this.maxLevel = maxLevel;
        return this;
    }

    public ModEnchantmentBase setConflicts(Enchantment[] conflicts)
    {
        this.conflicts = conflicts;
        return this;
    }

    public ModEnchantmentBase setRarityModifier(float baseFactor, float deltaFactor)
    {
        this.rarityBaseMultiplier = baseFactor;
        this.rarityDeltaMultiplier = deltaFactor;
        return this;
    }

    public ModEnchantmentBase setValue(float base_val, float per_level)
    {
        this.base_val = base_val;
        this.per_level = per_level;
        return this;
    }

    public float getValue(int level)
    {
        return base_val + (level - 1) * per_level;
    }

    public float getValue(EntityLivingBase creature)
    {
        return getValue(getLevelOnCreature(creature));
    }

    public int getLevelOnCreature(EntityLivingBase creature)
    {
        return EnchantmentHelper.getMaxEnchantmentLevel(this, creature);
    }

    //Constructors
    public ModEnchantmentBase(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots)
    {
        super(rarityIn, typeIn, slots);
        setRegistryName(Idealland.MODID, name);
        setName(name);
        ModEnchantmentInit.ENCHANTMENT_LIST.add(this);

        //additional enchantments: (modified level + 1) / 50, after applying,
        //modified level /= 2. This don't change the list, but only changes the chance of going on.

        //rarity:
//                COMMON(10),
//                UNCOMMON(5),
//                RARE(2),
//                VERY_RARE(1);
    }

    //not of much use. only fixed int damage
    @Override
    public int calcModifierDamage(int level, DamageSource source) {
        return super.calcModifierDamage(level, source);
    }

    //B = 1~30, depending on slots and bookshelves
    //L = B + R1 + R2 + 1
    //R = randomInteger(0, E / 4)
    //L *= 1 + random(-0.15,0.15)
    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return (int) (super.getMinEnchantability(enchantmentLevel) * rarityBaseMultiplier);
    }


//    // Generate a random number between 1 and 1+(enchantability/2), with a triangular distribution
//    int rand_enchantability = 1 + randomInt(enchantability / 4 + 1) + randomInt(enchantability / 4 + 1);
//
//    // Choose the enchantment level
//    int k = chosen_enchantment_level + rand_enchantability;
//
//    // A random bonus, between .85 and 1.15
//    float rand_bonus_percent = 1 + (randomFloat() + randomFloat() - 1) * 0.15;
//
//    // Finally, we calculate the level
//    int final_level = round(k * rand_bonus_percent);
//if ( final_level < 1 ) final_level = 1
    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return (int) (getMinEnchantability(enchantmentLevel) * rarityBaseMultiplier + 10 * rarityDeltaMultiplier);
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getMinLevel() {
        return super.getMinLevel();
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        for (Enchantment iter:
             conflicts) {
            if (ench == iter)
            {
                return  false;
            }
        }

        return super.canApplyTogether(ench);
    }

//    @Override
//    public boolean isCompatibleWith(Enchantment en)
//    {
//        //this won't work
//    }

    //Normally you won't care for this
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return !isHidden && super.canApplyAtEnchantingTable(stack);
    }

    //if it's treasure, it can NOT be applied at enchant table
    //villager will sell it for double price
    public boolean isTreasureEnchantment()
    {
        return false;
    }

    //    @Override
//    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
//        return super.calcDamageByCreature(level, creatureType);
//    }

    //other shorthands
    public int getEnchantmentLevel(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(this, stack);
    }
}
