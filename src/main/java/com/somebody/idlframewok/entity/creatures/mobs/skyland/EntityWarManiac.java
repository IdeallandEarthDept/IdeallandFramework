package com.somebody.idlframewok.entity.creatures.mobs.skyland;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.designs.god16.Init16Gods;
import com.somebody.idlframewok.entity.creatures.EntityGeneralMob;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;

import static com.somebody.idlframewok.util.Color16Def.WAR;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EntityWarManiac extends EntityGeneralMob {
    public EntityWarManiac(World worldIn) {
        super(worldIn);
        experienceValue = 40;
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        if (!world.isRemote)
        {
            ItemStack stack = new ItemStack(Items.DIAMOND_SWORD);
            EnchantmentHelper.addRandomEnchantment(getRNG(), stack, getLevel() * 10, true);
            setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);

            setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Blocks.RED_FLOWER, 1,1));
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16, 0.2, 8, -2, 20);
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        if (wasRecentlyHit)
        {
            entityDropItem(new ItemStack(Blocks.RED_FLOWER, 1,1), 1f);
        }
    }

    @SubscribeEvent
    static void onHurt(LivingHurtEvent event)
    {
        //whoever gets hurt first will draw attention of peace keepers, unless the hurt one believe in war god
        World world = event.getEntity().world;
        if (!world.isRemote)
        {
            EntityLivingBase hurtOne = event.getEntityLiving();
            if (event.getSource().getTrueSource() instanceof EntityLivingBase && Init16Gods.NOT_GOD_BEILIVER[WAR].apply(hurtOne))
            {
                List<EntityWarManiac> keepers = EntityUtil.getEntitiesWithinAABB(world, EntityWarManiac.class, hurtOne.getPositionVector(), 32, null);
                for (EntityWarManiac keeper:
                     keepers) {
                    if (keeper.getAttackTarget() == null)
                    {
                        keeper.setAttackTarget(hurtOne);
                    }
                }
            }
        }
    }

}
