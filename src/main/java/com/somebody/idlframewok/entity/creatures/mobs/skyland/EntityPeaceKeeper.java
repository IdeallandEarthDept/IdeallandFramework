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
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;

import static com.somebody.idlframewok.util.Color16Def.FEMALE;
import static com.somebody.idlframewok.util.Color16Def.PEACE;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EntityPeaceKeeper extends EntityGeneralMob {
    public EntityPeaceKeeper(World worldIn) {
        super(worldIn);
        experienceValue = 40;
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        if (!world.isRemote)
        {
            ItemStack stack = new ItemStack(Items.SHIELD);
            NBTTagCompound nbt = ItemBanner.makeBanner(EnumDyeColor.byDyeDamage(FEMALE), null).getTagCompound();
            stack.setTagCompound(nbt);
            setItemStackToSlot(EntityEquipmentSlot.OFFHAND, stack);
            setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Blocks.RED_FLOWER, 1,7));
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16, 0.2, 6, 2, 40);
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        if (wasRecentlyHit)
        {
            entityDropItem(new ItemStack(Blocks.RED_FLOWER, 1,7), 1f);
            if (getRNG().nextFloat() < 0.1f * (1+lootingModifier))
            {
                ItemStack stack = new ItemStack(Items.SHIELD);

                NBTTagCompound nbt = ItemBanner.makeBanner(EnumDyeColor.byDyeDamage(FEMALE), null).getTagCompound();
                stack.setTagCompound(nbt);

                int ench = lootingModifier * 10;
                if (getRNG().nextFloat() < 0.1f)
                {
                    ench += 10;
                }

                if (getRNG().nextFloat() < 0.1f)
                {
                    ench += 10;
                }

                if (getRNG().nextFloat() < 0.1f)
                {
                    ench += 10;
                }
                EnchantmentHelper.addRandomEnchantment(getRNG(), stack, ench, true);
                entityDropItem(new ItemStack(Items.SHIELD), 1f);
            }
        }

    }

    @SubscribeEvent
    static void onHurt(LivingHurtEvent event)
    {
        //whoever attacks first will draw attention of peace keepers, unless the attackers believe in peace god
        World world = event.getEntity().world;
        if (!world.isRemote)
        {
            EntityLivingBase hurtOne = event.getEntityLiving();
            if (event.getSource().getTrueSource() instanceof EntityLivingBase && Init16Gods.NOT_GOD_BEILIVER[PEACE].apply((EntityLivingBase) event.getSource().getTrueSource()))
            {
                List<EntityPeaceKeeper> keepers = EntityUtil.getEntitiesWithinAABB(world, EntityPeaceKeeper.class, hurtOne.getPositionVector(), 32, null);
                for (EntityPeaceKeeper keeper:
                     keepers) {
                    if (keeper.getAttackTarget() == null)
                    {
                        keeper.setAttackTarget((EntityLivingBase) event.getSource().getTrueSource());
                    }
                }
            }
        }
    }

}
