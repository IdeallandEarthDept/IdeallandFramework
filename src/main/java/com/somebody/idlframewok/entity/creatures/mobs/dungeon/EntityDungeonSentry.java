package com.somebody.idlframewok.entity.creatures.mobs.dungeon;

import com.somebody.idlframewok.designs.EnumDamageResistance;
import com.somebody.idlframewok.designs.EnumDamageType;
import com.somebody.idlframewok.designs.IDamageResistor;
import com.somebody.idlframewok.entity.EntityMobRanged;
import com.somebody.idlframewok.item.ItemAdvancedSpawnEgg;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityDungeonSentry extends EntityMobRanged implements IDamageResistor {
    public EntityDungeonSentry(World worldIn) {
        super(worldIn);
        wander = false;
    }

    protected void applyEntityAI()
    {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        applyTargetAI();
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        setAttr(12.0D, 0.2D, 1.0D, 0, 28);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!player.world.isRemote && PlayerUtil.isCreative(player))
        {
            ItemStack stack = player.getHeldItem(hand);
            Item item = stack.getItem();
            if (item instanceof ItemArmor)
            {
                setItemStackToSlot(((ItemArmor) item).armorType, stack.copy());
            }
            else if (item instanceof ItemTool || item instanceof ItemSword || CommonFunctions.isRangedWeaponItem(item))
            {
                setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack.copy());
            }
            else if (!(item instanceof ItemAdvancedSpawnEgg))
            {
                setItemStackToSlot(EntityEquipmentSlot.OFFHAND, stack.copy());
            }

        }
        return super.processInteract(player, hand);
    }

    @Override
    public EnumDamageResistance getResistance(EnumDamageType type) {
        if (type == EnumDamageType.FIRE)
        {
            return EnumDamageResistance.HIGH_WEAK;
        }
        else {
            return EnumDamageResistance.NONE;
        }
    }
}
