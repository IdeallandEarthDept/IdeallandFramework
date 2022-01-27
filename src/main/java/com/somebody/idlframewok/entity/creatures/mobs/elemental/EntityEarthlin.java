package com.somebody.idlframewok.entity.creatures.mobs.elemental;

import com.somebody.idlframewok.designs.IDamageResistor;
import com.somebody.idlframewok.designs.god16.Init16Gods;
import com.somebody.idlframewok.entity.EntityMobRanged;
import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityIdeallandUnitBase;
import com.somebody.idlframewok.util.Color16Def;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.somebody.idlframewok.util.EntityUtil.NOT_WEAR_HELM;

//@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EntityEarthlin extends EntityMobRanged implements IDamageResistor {
    public EntityEarthlin(World worldIn) {
        super(worldIn);
        attack_all_players = -1;
    }

    public static class HeadOption extends WeightedRandom.Item
    {
        public ItemStack type;
        public HeadOption(int weight, ItemStack type)
        {
            super(weight);
            this.type = type;
        }

        @Override
        public boolean equals(Object target)
        {
            return target instanceof HeadOption && type.equals(((HeadOption)target).type);
        }
    }

    public static class HeadListOption extends WeightedRandom.Item
    {
        public List<HeadOption> type;
        public HeadListOption(int weight, List<HeadOption> type)
        {
            super(weight);
            this.type = type;
        }

        @Override
        public boolean equals(Object target)
        {
            return target instanceof HeadListOption && type.equals(((HeadOption)target).type);
        }
    }

    static List<HeadOption> headList_R = new ArrayList<>();
    static List<HeadOption> headList_SR = new ArrayList<>();
    static List<HeadOption> headList_SSR = new ArrayList<>();
    static List<HeadListOption> headList = new ArrayList<>();

    static List<HeadOption> headList_snowy = new ArrayList<>();
    static List<HeadOption> headList_ocean = new ArrayList<>();
    static List<HeadOption> headList_jungle = new ArrayList<>();
    static List<HeadOption> headList_desert = new ArrayList<>();

    static final int SSR = 1, SR = 10, R = 50;
    float variantChance = 0.3f;

    public static void initEarthlinHead()
    {
        headList_SSR.add(new HeadOption(1, new ItemStack(Blocks.DIAMOND_BLOCK)));
        headList_SSR.add(new HeadOption(1, new ItemStack(Blocks.GOLD_BLOCK)));
        headList_SSR.add(new HeadOption(1, new ItemStack(Blocks.IRON_BLOCK)));
        headList_SSR.add(new HeadOption(1, new ItemStack(Blocks.COAL_BLOCK)));
        headList_SSR.add(new HeadOption(1, new ItemStack(Blocks.LAPIS_BLOCK)));
        headList_SSR.add(new HeadOption(1, new ItemStack(Blocks.REDSTONE_BLOCK)));

        headList_SR.add(new HeadOption(1, new ItemStack(Items.DIAMOND_HELMET)));
        headList_SR.add(new HeadOption(1, new ItemStack(Items.IRON_HELMET)));
        headList_SR.add(new HeadOption(1, new ItemStack(Items.GOLDEN_HELMET)));

        headList_SR.add(new HeadOption(1, new ItemStack(Blocks.DIAMOND_ORE)));
        headList_SR.add(new HeadOption(1, new ItemStack(Blocks.GOLD_ORE)));
        headList_SR.add(new HeadOption(1, new ItemStack(Blocks.IRON_ORE)));
        headList_SR.add(new HeadOption(1, new ItemStack(Blocks.COAL_ORE)));
        headList_SR.add(new HeadOption(1, new ItemStack(Blocks.LAPIS_ORE)));
        headList_SR.add(new HeadOption(1, new ItemStack(Blocks.REDSTONE_ORE)));

        headList_R.add(new HeadOption(1, new ItemStack(Blocks.STONE)));
        headList_R.add(new HeadOption(1, new ItemStack(Blocks.STONE,1,2)));
        headList_R.add(new HeadOption(1, new ItemStack(Blocks.STONE,1,4)));
        headList_R.add(new HeadOption(1, new ItemStack(Blocks.STONE,1,6)));
        headList_R.add(new HeadOption(1, new ItemStack(Blocks.DIRT)));
        headList_R.add(new HeadOption(1, new ItemStack(Blocks.GRASS)));
        headList_R.add(new HeadOption(1, new ItemStack(Blocks.CLAY)));
        //headList_R.add(new HeadOption(1, new ItemStack(Blocks.SAND)));
        headList_R.add(new HeadOption(1, new ItemStack(Blocks.GRAVEL)));
        headList_R.add(new HeadOption(1, new ItemStack(Blocks.SANDSTONE)));

        headList.add(new HeadListOption(SSR, headList_SSR));
        headList.add(new HeadListOption(SR, headList_SR));
        headList.add(new HeadListOption(R, headList_R));

        headList_snowy.add(new HeadOption(R, new ItemStack(Blocks.ICE)));
        headList_snowy.add(new HeadOption(R, new ItemStack(Blocks.PACKED_ICE)));
        headList_snowy.add(new HeadOption(R, new ItemStack(Blocks.FROSTED_ICE)));
        headList_snowy.add(new HeadOption(R, new ItemStack(Blocks.SNOW)));
        headList_snowy.add(new HeadOption(SR, new ItemStack(Blocks.EMERALD_ORE)));
        headList_snowy.add(new HeadOption(SSR, new ItemStack(Blocks.EMERALD_BLOCK)));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        setAttr(16.0D, 0.2D, 1.0D, 0, 25);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        return super.onInitialSpawn(difficulty, livingdata);
    }

    public void setHeadRandom(BlockPos pos)
    {
        if (world.isRemote)
        {
            return;
        }

        Biome biome = world.getBiome(pos);
        ItemStack result = WeightedRandom.getRandomItem(rand, WeightedRandom.getRandomItem(rand, headList).type).type;
        if (rand.nextFloat() > variantChance)
        {
            if (biome.isSnowyBiome())
            {
                result = WeightedRandom.getRandomItem(rand, headList_snowy).type;
            }
            //todo: other biome variants
        }

        //pick a rarity tier, then pick up a type.
        setItemStackToSlot(EntityEquipmentSlot.HEAD, result.copy());
    }

    @Override
    public void onFirstTickInLife() {
        super.onFirstTickInLife();
        if (!world.isRemote)
        {
            setHeadRandom(new BlockPos(posX,posY,posZ));
            Init16Gods.setBelief(this, Color16Def.EARTH, 100);
            setCombatTask();
        }
    }

    protected void applyTargetAI()
    {
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true, true, NOT_WEAR_HELM));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityIdeallandUnitBase.class, 10, true, true, NOT_WEAR_HELM));
        ((PathNavigateGround)this.getNavigator()).setEnterDoors(false);
    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     * Copied from vanilla, so lots of magical numbers
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        if (this.rand.nextFloat() < 0.15F * difficulty.getClampedAdditionalDifficulty())
        {
            int i = this.rand.nextInt(2);
            if (this.rand.nextFloat() < 0.095F)
            {
                ++i;
            }

            if (this.rand.nextFloat() < 0.095F)
            {
                ++i;
            }

            if (this.rand.nextFloat() < 0.095F)
            {
                ++i;
            }

            ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);

            if (itemstack.isEmpty())
            {
                Item item = getMainHandByChance(i);

                if (item != null)
                {
                    this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(item));
                }
            }
        }
    }

    @Nullable
    public static Item getMainHandByChance(int chance)
    {
        switch (chance)
        {
            case 0:
                return Items.STONE_HOE;
            case 1:
                return Items.STONE_SHOVEL;
            case 2:
                return Items.IRON_SHOVEL;
            case 3:
                return Items.DIAMOND_HOE;
            case 4:
                return Items.DIAMOND_SHOVEL;
            default:
                return null;
        }
    }
}
