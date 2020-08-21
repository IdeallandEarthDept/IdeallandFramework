package com.deeplake.idealland.entity.creatures.moroon;

import com.deeplake.idealland.entity.creatures.ideallandTeam.EntityIdeallandUnitBase;
import com.deeplake.idealland.potion.ModPotions;
import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.IDLGeneral;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;

public class EntityBossLifeHorde extends EntityMoroonUnitBase {
    private static final int DATA_BOSSHEALTH = 19;

    int stationaryCounter = 0;
    int stationaryToInvisible = TICK_PER_SECOND * 5;
    float base_range = 16f;

    int dragon_cost = 3600 * TICK_PER_SECOND;
    int dragon_counter = dragon_cost;


    //Avatar of all life
    public EntityBossLifeHorde(World worldIn) {
        super(worldIn);
        MinecraftForge.EVENT_BUS.register(this);
        is_god = true;
        experienceValue = 150;
        isImmuneToFire = true;
        setSize(3,3);

        addPotionEffect(new PotionEffect(ModPotions.INVINCIBLE, TICK_PER_SECOND * 3, 0));
    }

    public boolean isNonBoss()
    {
        return false;
    }

    public void updateAITasks()
    {
        setSneaking(true);
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);

    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        //this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMoroonBombBeacon.class, 16.0F, 0.6D, 1D));
//        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
//        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
//        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityLiving.class}));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIdeallandUnitBase.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityZombie.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));

        ((PathNavigateGround)this.getNavigator()).setEnterDoors(true);

    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(32, 0.15f, 20, 2, 666);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote)
        {
            stationaryCounter++;

            if (stationaryToInvisible == stationaryCounter)
            {
                int count = checkNearbyLegalCreatureCount();
                if (count <= 100)
                {
                    if (getRNG().nextInt(5) == 0)
                    {
                        SummonTroops();
                    }
                    else {
                        SummonRandomCreature();
                    }
                    stationaryCounter = 0;
                }
                else {
                    stationaryCounter--;
                }
            }

            if (world.getWorldTime() % TICK_PER_SECOND == 1) {
                int count = checkNearbyLegalCreatureCount();
                if (count > 0)
                {
                    setFire(10);
                }
            }

            dragon_counter++;
            if (dragon_counter >= dragon_cost)
            {
                dragon_counter -= dragon_cost;
                SummonEnderDragon();
            }
        }
    }

    void knockbackCreature(EntityLivingBase livingBase)
    {
        livingBase.knockBack(this, 1f, getRNG().nextFloat() - 0.5f, getRNG().nextFloat() - 0.5f);
    }

    public void SummonRandomCreature()
    {
        EntityLivingBase result = CreateRandomNormalCreature();
        result.setPosition(posX, posY, posZ);
        world.spawnEntity(result);
        knockbackCreature(result);
    }

    public void SummonEnderDragon()
    {
        EntityLivingBase result = new EntityDragon(world);
        result.setPosition(posX, posY + 10, posZ);
        world.spawnEntity(result);
    }

    public void SummonTroops()
    {
        EntityLivingBase result = CreateRandomMoroonUnit();
        result.setPosition(posX + 1, posY, posZ);
        world.spawnEntity(result);
        knockbackCreature(result);

        result = CreateRandomMoroonUnit();
        result.setPosition(posX - 1, posY, posZ);
        world.spawnEntity(result);
        knockbackCreature(result);

        result = CreateRandomMoroonUnit();
        result.setPosition(posX, posY, posZ - 1);
        world.spawnEntity(result);
        knockbackCreature(result);

        result = CreateRandomMoroonUnit();
        result.setPosition(posX, posY, posZ - 1);
        world.spawnEntity(result);
        knockbackCreature(result);
    }


    private EntityLivingBase CreateRandomNormalCreature()
    {
        EntityLivingBase creature;
        int rand = getRNG().nextInt(12);
        switch (rand)
        {
            case 0:
                creature = new EntitySheep(world);
                break;
            case 1:
                creature = new EntityPig(world);
                break;
            case 2:
                creature = new EntityRabbit(world);
                break;
            case 3:
                creature = new EntityHorse(world);
                break;
            case 4:
                creature = new EntityShulker(world);
                break;
            case 5:
                creature = new EntityWolf(world);
                break;
            case 6:
                creature = new EntityCow(world);
                break;
            case 7:
                creature = new EntityOcelot(world);
                break;
            case 8:
                creature = new EntityLlama(world);
                break;
            case 9:
                creature = new EntityPolarBear(world);
                break;
            case 10:
                creature = new EntityBat(world);
                break;
            case 11:
                creature = new EntityParrot(world);
                break;
            default:
                creature = new EntityPig(world);
                break;
        }
        return creature;
    }

    private EntityLivingBase CreateRandomMoroonUnit()
    {
        EntityLivingBase creature;
        int rand = getRNG().nextInt(12);
        switch (rand)
        {
            case 0:
                creature = new EntityMorBlindingAssassin(world);
                break;
            case 1:
                creature = new EntityMoroonTainter(world);
                break;
            case 2:
                creature = new EntityMoroonBastionWalker(world);
                break;
            case 3:
                creature = new EntityMoroonEliteMartialist(world);
                break;
            case 4:
                creature = new EntityMoroonGhostArcher(world);
                break;
            case 5:
                creature = new EntityMoroonFlickFighter(world);
                break;
            case 6:
                creature = new EntityMoroonTideMaker(world);
                break;
            default:
                creature = new EntityMoroonTainter(world);
                break;
        }
        return creature;
    }

//    public EntityLivingBase CreateRandomCreature(Class<? extends EntityLivingBase>[] validCreatures)
//    {
//        int rand = getRNG().nextInt(validCreatures.length);
//        try {
//            return validCreatures[rand].newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return new EntitySheep(world);
//    }

    @SubscribeEvent
    public void onCreatureDied(final LivingDeathEvent ev) {

        if (ev.getEntityLiving() == this)
        {
            int count = checkNearbyLegalCreatureCount();
            if (count > 0)
            {
                ev.setCanceled(true);
                this.setHealth(count * 6);
                if (ev.getSource().getTrueSource() instanceof EntityPlayer)
                {
                    CommonFunctions.SendMsgToPlayerStyled((EntityPlayerMP) ev.getSource().getTrueSource(), "idealland.msg.boss_revive", TextFormatting.DARK_PURPLE);
                    CommonFunctions.SendMsgToPlayerStyled((EntityPlayerMP) ev.getSource().getTrueSource(), "idealland.msg.boss_revive2", TextFormatting.YELLOW);
                }
            }
        }

    }

    @SubscribeEvent
    public void onCreatureHurt(final LivingHurtEvent ev) {
        if (ev.getEntityLiving() == this)
        {

        }
    }


    public int checkNearbyLegalCreatureCount()
    {
        int result = 0;
        Vec3d basePos = getPositionVector();
        List<EntityLiving> entities = world.getEntitiesWithinAABB(EntityLiving.class, IDLGeneral.ServerAABB(basePos.addVector(-base_range, -base_range, -base_range), basePos.addVector(base_range, base_range, base_range)));
        for (EntityLiving living: entities
        ) {
            if (living instanceof EntityZombie || living instanceof EntityVillager)
            {
                continue;
            }
            result++;
        }
        return result;
    }

    //todo boss health bar
    //todo beacon effect


//    enum WeaponIndex{
//        BLOOD_INDEX,
//        DEATH_INDEX,
//        SPACE_AF_INDEX,
//        BUILDER_INDEX,
//        SNOW_INDEX,
//        POWER_TRIANGLE_INDEX,
//        GOLD_INDEX,
//        TRUENAME_INDEX,
//        DISARMER_INDEX,
//        WATER_INDEX,
//        BEADS_INDEX,
//        LAST,
//    }

//    protected int[] GetFactorGroup()
//    {
//        int count = WeaponIndex.LAST.ordinal();
//        int[] factor = new int[count];
//        factor[WeaponIndex.BLOOD_INDEX.ordinal()] = 10;
//        factor[WeaponIndex.DEATH_INDEX.ordinal()] = 10;
//        factor[WeaponIndex.SPACE_AF_INDEX.ordinal()] = 10;
//        factor[WeaponIndex.BUILDER_INDEX.ordinal()] = 6;
//        factor[WeaponIndex.SNOW_INDEX.ordinal()] = 6;
//        factor[WeaponIndex.POWER_TRIANGLE_INDEX.ordinal()] = 6;
//        factor[WeaponIndex.GOLD_INDEX.ordinal()] = 6;
//        factor[WeaponIndex.TRUENAME_INDEX.ordinal()] = 10;
//        factor[WeaponIndex.DISARMER_INDEX.ordinal()] = 10;
//        factor[WeaponIndex.WATER_INDEX.ordinal()] = 10;
//        factor[WeaponIndex.BEADS_INDEX.ordinal()] = 6;
//        return  factor;
//    }
//
//    public ItemStack GetRandomWeapon()
//    {
//        ItemStack result;
//
//        int count = WeaponIndex.LAST.ordinal();
//        int[] factor = GetFactorGroup();
//        int factorSum = 0;
//        for (int i = 0; i < count; i++)
//        {
//            factorSum += factor[i];
//        }
//
//        int i = 0;
//        Random rand = new Random();
//        int random = rand.nextInt(factorSum);
//        for (; i < count; i++)
//        {
//            random -= factor[i];
//            if (random <= 0)
//            {
//                break;
//            }
//        }
//        if (i > count)
//        {
//            i = count;
//        }
//
//        WeaponIndex resultEnum = WeaponIndex.values()[i];
//
//        switch(resultEnum)
//        {
//            case BLOOD_INDEX:
//                result = new ItemStack(ModItems.BLOOD_SWORD);
//                break;
//            case DEATH_INDEX:
//                result = new ItemStack(ModItems.DEATH_SWORD);
//                break;
//            case SPACE_AF_INDEX:
//                result = new ItemStack(ModItems.SPACE_SWORD);
//                break;
//            case BUILDER_INDEX:
//                result = new ItemStack(ModItems.SAGE_BUILDER);
//                break;
//            case SNOW_INDEX:
//                result = new ItemStack(ModItems.SNOW_SWORD);
//                break;
//            case POWER_TRIANGLE_INDEX:
//                result = new ItemStack(ModItems.POWER_TRIANGLE);
//                break;
//            case GOLD_INDEX:
//                result = new ItemStack(ModItems.GOLD_SWORD);
//                break;
//            case TRUENAME_INDEX:
//                result = new ItemStack(ModItems.TRUE_NAME_SWORD);
//                break;
//            case DISARMER_INDEX:
//                result = new ItemStack(ModItems.DISARM_RING);
//                break;
//            case WATER_INDEX:
//                result = new ItemStack(ModItems.WATER_SWORD);
//                break;
//            case BEADS_INDEX:
//                result = new ItemStack(ModItems.MONK_BEADS);
//                break;
//            default:
//                result = new ItemStack(Items.IRON_SWORD);
//                break;
//        }
//
//        DWeapons.Log(result.getDisplayName());
//        //DWeapons��LogWarning(result.getDisplayName());
//        if (result.isEmpty()){
//            DWeapons.LogWarning("EMPTY!!!");
//        }
//
//        random = rand.nextInt(10);
//        if (random == 0)
//        {
//            ((DWeaponSwordBase)(result.getItem())).SetPearlCount(result, 1);
//        }
//
//        DWeapons.Log("Given weapon:" + result.getDisplayName());
//
//        return result;
//    }
}
