package com.somebody.idlframewok.entity.creatures.mobs.dungeon;

import com.somebody.idlframewok.advancements.AdvancementKeys;
import com.somebody.idlframewok.advancements.ModAdvancementsInit;
import com.somebody.idlframewok.designs.EnumDamageResistance;
import com.somebody.idlframewok.designs.EnumDamageType;
import com.somebody.idlframewok.designs.IDamageResistor;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.MessageDef.MSG_CLEANSE_FIREPROOF;

public class EntityFireSentry extends EntityDungeonSentry implements IDamageResistor {
    public EntityFireSentry(World worldIn) {
        super(worldIn);
    }

    @Override
    public EnumDamageResistance getResistance(EnumDamageType type) {
        if (type == EnumDamageType.FIRE) {
            return EnumDamageResistance.HIGH_WEAK;
        } else {
            return super.getResistance(type);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        boolean result = super.attackEntityAsMob(target);
        if (result
                && target instanceof EntityPlayer
                && !target.world.isRemote) {
            EntityPlayer player = (EntityPlayer) target;
            if (!ModAdvancementsInit.hasAdvancement(player, AdvancementKeys.H_NO_REMOVE_FIRE_PROOF)) {
                if (EntityUtil.getBuffLevelIDL(player, MobEffects.FIRE_RESISTANCE) > 0) {
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, player, MSG_CLEANSE_FIREPROOF);
                    EntityUtil.TryRemoveGivenBuff(player, MobEffects.FIRE_RESISTANCE);
                }
            }

            if (!ModAdvancementsInit.hasAdvancement(player, AdvancementKeys.H_NO_IGNITE)) {
                player.setFire((int) EntityUtil.getAttack(this));
            }
        }

        return result;
    }
}
