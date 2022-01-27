package com.somebody.idlframewok.item.skills.classfit.alterego;

import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityAlterego;
import com.somebody.idlframewok.item.skills.classfit.EnumSkillClass;
import com.somebody.idlframewok.item.skills.classfit.ItemSkillCore;
import com.somebody.idlframewok.item.skills.classfit.SkillClassUtil;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.IDLSkillNBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;
import static com.somebody.idlframewok.util.MessageDef.MSG_ALREADY_SUMMONED;

public class ItemCoreAlterego extends ItemSkillCore {

    public ItemCoreAlterego(String name) {
        super(name, EnumSkillClass.EGO_TWIN);
        setCD(10, 0);//will also re-cooldown when player log-in and summoned-thing dies.
    }

    float[] baseAtk = {4,5,7,10,15};

    public double getEgoAtk(ItemStack stack, EntityLivingBase livingBase)
    {
        int newIndex = IDLSkillNBT.getLevel(stack);
        newIndex = CommonFunctions.clamp(newIndex, 0, baseAtk.length-1);
        return baseAtk[newIndex];
       //return baseAtk * (1+ SkillClassUtil.getCoreTotalModifierForLevel(getSelfClassLevel(livingBase)));
    }
    public double getEgoHPMax(ItemStack stack, EntityLivingBase livingBase)
    {
        return EntityUtil.getHPMax(livingBase) * (1+ SkillClassUtil.getCoreTotalModifierForLevel(getSelfClassLevel(livingBase)));
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (!canCast(worldIn, livingBase, stack, slot))
        {
            return false;
        }

        if (!worldIn.isRemote)
        {
            if (livingBase instanceof EntityPlayer)
            {
                List<EntityAlterego> alteregoList = worldIn.getEntities(EntityAlterego.class, EntityUtil.ALL_ALIVE);

                for (EntityAlterego instance :
                        alteregoList) {
                    if (instance.player == livingBase) {

                        float needHealth = instance.getMaxHealth() - instance.getHealth();
                        if (livingBase.getHealth() > 1f && (needHealth > 0.1f))
                        {
                            float spareHealth = livingBase.getHealth() - 1f;
                            float transferAmount = Math.min(needHealth, spareHealth);
                            livingBase.setHealth(livingBase.getHealth() - transferAmount);
                            instance.heal(transferAmount);
                            livingBase.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1f, 1f);
                        }
                        else {
                            livingBase.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1f, 1f);
                            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, livingBase, MSG_ALREADY_SUMMONED);
                        }

                        return true;//Will not summon another
                    }
                }
            }

            //Summon
            EntityAlterego alterego = new EntityAlterego(worldIn);
            alterego.setPosition(livingBase.posX, livingBase.posY+2, livingBase.posZ);
            worldIn.spawnEntity(alterego);

            alterego.imitatePlayer((EntityPlayer) livingBase);
            alterego.setAttr(CHUNK_SIZE,
                    CommonDef.SPEED_NORMAL,
                    getEgoAtk(stack, livingBase), 0,
                    getEgoHPMax(stack, livingBase));
            alterego.setBehaviorMode(EntityAlterego.EnumActionMode.DEFEND);
            alterego.setHealth(alterego.getMaxHealth());
        }
        return super.applyCast(worldIn, livingBase, stack, slot);
    }
}
