package com.somebody.idlframewok.enchantments;

import com.somebody.idlframewok.IdlFramework;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.DamageSource;

public class ModEnchantmentBase extends Enchantment {
    private boolean isTreasure = false;

    private int maxLevel = 1;

    private float base_val = 0f;
    private float per_level = 0f;

    private float rarityBaseMultiplier = 1f;
    private float rarityDeltaMultiplier = 1f;

    private Enchantment[] conflicts = new Enchantment[]{};

    private boolean isHidden = false;

    private boolean isCurseEnch = false;

    //make this accessible
    protected final EntityEquipmentSlot[] applicableEquipmentTypesOpen;

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

    public ModEnchantmentBase setAsTreasure()
    {
        this.isTreasure = true;
        return this;
    }
    public ModEnchantmentBase setAsCurse()
    {
        this.isCurseEnch = true;
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

    @Override
    public boolean isCurse() {
        return isCurseEnch;
    }

    public float getValue(int level)
    {
        if (level <= 0)
        {
            return 0;
        }
        return base_val + (level - 1) * per_level;
    }

    public float getValue(EntityLivingBase creature)
    {
        return getValue(getLevelOnCreature(creature));
    }

    public int getLevelOnCreature(EntityLivingBase creature)
    {
        if (creature == null)
        {
            return 0;
        }
        return EnchantmentHelper.getMaxEnchantmentLevel(this, creature);
    }

    public boolean appliedOnCreature(EntityLivingBase creature)
    {
        if (creature == null)
        {
            return false;
        }
        return EnchantmentHelper.getMaxEnchantmentLevel(this, creature) > 0;
    }

    //Constructors
    public ModEnchantmentBase(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots)
    {
        super(rarityIn, typeIn, slots);
        setRegistryName(IdlFramework.MODID, name);
        setName(name);
        ModEnchantmentInit.ENCHANTMENT_LIST.add(this);
        applicableEquipmentTypesOpen = slots;
        //note this slots arguments. Only enchantments in those slots will be counted!

        //additional enchantments: (modified level + 1) / 50, after applying,
        //modified level /= 2. This don't change the list, but only changes the chance of going on.

        //rarity:
//                COMMON(10),
//                UNCOMMON(5),
//                RARE(2),
//                VERY_RARE(1);
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

    // max: (30 + MaterialEnchantability/2) * 1.15 =  34.5 + 0.575 * ench
    //diamond:

    //B = 1~30, depending on slots and bookshelves
    //L = B + R1 + R2 + 1
    //R = randomInteger(0, E / 4)
    //L *= 1 + random(-0.15,0.15)
    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return (int) (super.getMinEnchantability(enchantmentLevel) * rarityBaseMultiplier);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return (int) (getMinEnchantability(enchantmentLevel) * rarityBaseMultiplier + 50 * rarityDeltaMultiplier);
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
        //special requirement: tool or sword
        if (applicableEquipmentTypesOpen == ModEnchantmentInit.mainHand)
        {
            Item itemType = stack.getItem();
            if (!(itemType instanceof ItemSword) && !(itemType instanceof ItemTool)) {
                return false;
            }
        }
        return !isHidden && super.canApplyAtEnchantingTable(stack);
    }

    //if it's treasure, it can NOT be applied at enchant table
    //villager will sell it for double price
    public boolean isTreasureEnchantment()
    {
        return this.isTreasure;
    }

    //    @Override
//    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
//        return super.calcDamageByCreature(level, creatureType);
//    }

    //other shorthands
    public int getEnchantmentLevel(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(this, stack);
    }

    /**
     * Called whenever a mob is damaged with an item that has this enchantment on it.
     */
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level)
    {
    }

    /**
     * Whenever an entity that has this enchantment on one of its associated items is damaged this method will be
     * called.
     */
    public void onUserHurt(EntityLivingBase user, Entity attacker, int level)
    {
    }

    /**
     * Calculates the damage protection of the enchantment based on level and damage source passed.
     */
    public int calcModifierDamage(int level, DamageSource source)
    {
        //Note that this can't judge the victim's attributes as it does not contain the ref of that living base.
        //it returns an int, but at last it calculates as a float.
        //1 = 8% Damage. 10 = 80% (max reduction)
        //note it's not Enchantment.calcDamageByCreature(int level, EnumCreatureAttribute creatureType)

        return super.calcModifierDamage(level, source);

        //EnchantmentProtection:
//        if (source.canHarmInCreative())
//        {
//            return 0;
//        }
//        else if (this.protectionType == EnchantmentProtection.Type.ALL)
//        {
//            return level;
//        }
//        else if (this.protectionType == EnchantmentProtection.Type.FIRE && source.isFireDamage())
//        {
//            return level * 2;
//        }
//        else if (this.protectionType == EnchantmentProtection.Type.FALL && source == DamageSource.FALL)
//        {
//            return level * 3;
//        }
//        else if (this.protectionType == EnchantmentProtection.Type.EXPLOSION && source.isExplosion())
//        {
//            return level * 2;
//        }
//        else
//        {
//            return this.protectionType == EnchantmentProtection.Type.PROJECTILE && source.isProjectile() ? level * 2 : 0;
//        }
    }
}
