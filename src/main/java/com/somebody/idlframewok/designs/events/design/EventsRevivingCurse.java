package com.somebody.idlframewok.designs.events.design;

import com.somebody.idlframewok.entity.creatures.moroon.EntityMoroonTainter;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsRevivingCurse {
    public static boolean isEntityBlackListed(EntityLivingBase living)
    {
        for ( String s:
                ModConfig.REVIVE_CURSE.REVIVE_BLACKLIST) {
            String registryName = EntityList.getEntityString(living);
            if ((registryName != null) && (registryName.equals(s)))
            {
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onCreatureDied(final LivingDeathEvent ev) {
        World world = ev.getEntityLiving().world;
        EntityLivingBase diedOne = ev.getEntityLiving();

        //Reviving curse
        if (ModConfig.REVIVE_CURSE.REVIVE_CURSE_ACTIVE)
        {
            if (!diedOne.isEntityUndead() && !EntityUtil.isMoroonTeam(diedOne) && !EntityUtil.isMechanical(diedOne) && !(EntityUtil.isElemental(diedOne))){
                if (!world.isRemote && (diedOne.getActivePotionEffect(ModPotions.RIP) == null) && (!isEntityBlackListed(diedOne))){
                    //Tainters have different logic
                    if (!ModConfig.REVIVE_CURSE.REVIVE_NON_VANILLA && !EntityUtil.isVanillaResident(diedOne)) {
                        return;
                    }

                    if (ev.getSource() != null) {
                        DamageSource source = ev.getSource();
                        if (source.getTrueSource() instanceof EntityMoroonTainter && !(diedOne instanceof EntityMoroonTainter)){
                            EntityMoroonTainter tainter = new EntityMoroonTainter(world);
                            tainter.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(
                                    diedOne.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() / 2);

                            world.spawnEntity(tainter);
                            return;
                        }
                    }

                    EntityLiving zombie = null;
                    boolean forceChild = false;
                    boolean dontSpawn = false;

                    if (diedOne instanceof EntityPig)
                    {
                        zombie = new EntityPigZombie(world);
                        zombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
                        forceChild = (diedOne.isChild());
                    }
                    else if (diedOne instanceof EntitySlime || diedOne instanceof EntityVex || diedOne instanceof EntityGhast)
                    {
                        dontSpawn = true;
                        //do nothing. It's quite strange to see slime split up and creates zombies at the same time.
                    }
                    else if (diedOne instanceof EntityHorse)
                    {
                        zombie = new EntitySkeletonHorse(world);
                        forceChild = (diedOne.isChild());
                    }
                    else if (diedOne instanceof EntityVillager)
                    {
                        //Prevent from creating two zombies with vanilla features.
                        if (ev.getSource() != null) {
                            DamageSource source = ev.getSource();
                            if (source.getTrueSource() instanceof EntityZombie
                            && (world.getDifficulty() == EnumDifficulty.NORMAL || world.getDifficulty() == EnumDifficulty.HARD)){
                                //  In normal mode, the chance is 1/2. I cannot get the random result, so I just cut it all down
                                dontSpawn = true;
                            }
                        }

                        if (!dontSpawn)
                        {
                            zombie = new EntityZombieVillager(world);
                            EntityZombieVillager entityzombievillager = new EntityZombieVillager(world);
                            entityzombievillager.setProfession(((EntityVillager)diedOne).getProfession());
                            forceChild = (diedOne.isChild());
                        }
                    }
                    else if (diedOne instanceof EntityPlayer){
                        zombie = new EntityZombie(world);
                        zombie.setCustomNameTag(diedOne.getName());
                    }
                    else {
                        zombie = new EntityZombie(world);
                    }
                    if (zombie != null)
                    {
                        boolean noAI = false;
                        if (diedOne instanceof EntityLiving)
                        {
                            noAI = ((EntityLiving)diedOne).isAIDisabled();
                        }

                        zombie.setPosition(diedOne.posX, diedOne.posY, diedOne.posZ);
                        zombie.setLocationAndAngles(diedOne.posX, diedOne.posY, diedOne.posZ, diedOne.rotationYaw, diedOne.rotationPitch);
                        zombie.setNoAI(noAI);

                        if (diedOne.hasCustomName())
                        {
                            zombie.setCustomNameTag(diedOne.getCustomNameTag());
                            zombie.setAlwaysRenderNameTag(diedOne.getAlwaysRenderNameTag());
                        }

                        float oriMaxHealth = diedOne.getMaxHealth();
                        zombie.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(
                                diedOne.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue());
                        zombie.setHealth(oriMaxHealth);
                        if (oriMaxHealth <= ModConfig.REVIVE_CURSE.REVIVE_CURSE_CHILD_THRESHOLD || forceChild)
                        {
                            if (zombie instanceof EntityZombie)
                            {
                                ((EntityZombie)zombie).setChild(true);
                            }
                        }

                        zombie.setCanPickUpLoot(true);
                        if (ModConfig.REVIVE_CURSE.REVIVE_CURSE_AVENGEFUL && ev.getSource() != null) {
                            DamageSource source = ev.getSource();
                            if (source.getTrueSource() instanceof EntityLivingBase){
                                zombie.setAttackTarget((EntityLivingBase) source.getTrueSource());
                            }
                        }
                        world.spawnEntity(zombie);
                    }
                }
            }
        }


    }
}
