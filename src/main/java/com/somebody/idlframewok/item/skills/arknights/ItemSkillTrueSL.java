package com.somebody.idlframewok.item.skills.arknights;

import com.somebody.idlframewok.IdlFramework;
import com.somebody.idlframewok.util.*;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.UUID;

public class ItemSkillTrueSL extends ItemArknightsSkillBase {

    private UUID uuid = UUID.fromString("a7d91a0a-3922-4467-a114-f476ca4daf3e");
    private float range = 3.5f * CommonDef.METER_PER_BLOCK;

    private double defReduce = -0.7;
    double[] atkPlus = {1.1, 1.15, 1.2, 1.25, 1.3, 1.35, 1.4, 1.6, 1.8, 2.0};

    private int[] atkCount = {3,3,3,4,4,4,5,5,5,6};

    public ItemSkillTrueSL(String name) {
        super(name);
        setRange(range,0);
        offHandCast = true;
        cannotMouseCast = true;
        maxDialogues = 4;
        maxLevel = 10;

        dura = new int[]{20, 21, 22, 23, 24, 25, 26, 27, 28, 30};
        max_charge = new int[]{90};
        //max_charge = new int[]{3};
        initPower = new int[]{50, 50, 50, 55, 55, 55, 60, 65, 70, 75};
        showDuraDesc = true;
        CommonFunctions.addToEventBus(this);
    }



    @Override
    public int getMaxDamage(ItemStack stack) {
        return 100;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        boolean isOn = IDLSkillNBT.IsCasting(stack);

        if (isOn && equipmentSlot == EntityEquipmentSlot.OFFHAND)
        {
            int level = IDLSkillNBT.getLevel(stack) - 1;
            //in arknights, the modifier is type 1, but it makes no sense for players.
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(uuid, "Truesilver Slash Modifier", atkPlus[level], 2));
            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(uuid, "Truesilver Slash Modifier", defReduce, 2));
        }

        return multimap;
    }

    @Override
    public void onMouseFire(EntityPlayer player) {
        ItemStack stack = player.getHeldItemOffhand();
        if (stack.getItem() == this)
        {
            if (IDLSkillNBT.IsCasting(stack) && player.getCooledAttackStrength(0f) >= 0.99f)
            {
                playerSlash(player, IDLSkillNBT.getLevel(stack) - 1);
            }
        }
    }

    public void playerSlash(EntityPlayer player, int level)
    {
        World world = player.world;
        if (world.isRemote)
        {
            float scaleFactor = 2f;

            //create particle
            Vec3d forward = player.getForward().scale(scaleFactor);

            double fx =  forward.x;
            double fy =  forward.y;
            double fz =  forward.z;

            Vec3d right =  forward.crossProduct(new Vec3d(0,1,0)).normalize().scale(scaleFactor);

            double rx =  right.x;
            double ry =  right.y;
            double rz =  right.z;

            int max = 3;
            for (int z = 0; z <= max; z++)
            {
                for (int x = - (max - z); x <= max - z; x++)
                {
                    world.spawnParticle(EnumParticleTypes.SWEEP_ATTACK,
                            player.posX + fx * z + rx * x,
                            player.posY + player.getEyeHeight() + fy * z + ry * x,
                            player.posZ + fz * z + rz * x,
                            player.getForward().x,
                            player.getForward().y,
                            player.getForward().z);
                }
            }

        }else {
            int targetLeft = atkCount[level];

            Vec3d basePos = player.getPositionVector();
            List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, IDLGeneral.ServerAABB(basePos.addVector(-base_range, -base_range, -base_range), basePos.addVector(base_range, base_range, base_range)));
            for (EntityLivingBase living: entities
            ) {
                if (EntityUtil.getAttitude(player, living) == EntityUtil.ATTITUDE.HATE)
                {
                    boolean isForwad = player.getForward().dotProduct(living.getPositionVector().subtract(player.getPositionVector())) >= 0;
                    if (isForwad)
                    {
                        living.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) EntityUtil.getAttack(player));
                        targetLeft--;
                        if (targetLeft <= 0)
                        {
                            break;
                        }
                    }
                    else {
                        IdlFramework.Log("%s is not in front");
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        String key = stack.getUnlocalizedName() + ".desc";
        if (I18n.hasKey(key))
        {
            int level = IDLSkillNBT.getLevel(stack) - 1;
            String mainDesc = I18n.format(key, (int)(defReduce * 100f) , (int)(atkPlus[level] * 100f), atkCount[level]);
            return mainDesc;
        }
        return "";
    }
}
