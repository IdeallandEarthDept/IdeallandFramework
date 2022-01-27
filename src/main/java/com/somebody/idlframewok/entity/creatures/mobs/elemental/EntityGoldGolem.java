package com.somebody.idlframewok.entity.creatures.mobs.elemental;

import com.somebody.idlframewok.designs.EnumDamageResistance;
import com.somebody.idlframewok.designs.EnumDamageType;
import com.somebody.idlframewok.designs.IDamageResistor;
import com.somebody.idlframewok.entity.EntityMobRanged;
import com.somebody.idlframewok.util.Color16Def;
import net.minecraft.init.Items;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.Color16Def.INIT_BELIEF;

public class EntityGoldGolem extends EntityMobRanged implements IDamageResistor {
    public EntityGoldGolem(World worldIn) {
        super(worldIn);
        attack_all_players = -1;
        is_elemental = true;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        setAttr(16.0D, 0.2D, 1.0D, 0, 20);
    }

    @Override
    public EnumDamageResistance getResistance(EnumDamageType type) {
        if (type == EnumDamageType.FIRE) {
            return EnumDamageResistance.RESIST;
        } else if (type == EnumDamageType.MAGIC) {
            return EnumDamageResistance.RESIST;
        } else if (type == EnumDamageType.NORMAL) {
            return EnumDamageResistance.SLIGHT_WEAK;
        }
        return EnumDamageResistance.NONE;
    }

    @Override
    public void onFirstTickInLife() {
        super.onFirstTickInLife();
        if (world.isRemote) {
            Color16Def.increaseBelief(this, Color16Def.GOLD, INIT_BELIEF);
        }
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        dropItem(Items.GOLD_INGOT, rand.nextInt(2));
        dropItem(Items.GOLD_NUGGET, rand.nextInt(4 + 2 * lootingModifier));
    }

    @Override
    public boolean isPotionApplicable(PotionEffect potioneffectIn) {
        return false;
    }
}
