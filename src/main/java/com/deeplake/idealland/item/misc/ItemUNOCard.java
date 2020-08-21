package com.deeplake.idealland.item.misc;

import com.deeplake.idealland.item.IGuaEnhance;
import com.deeplake.idealland.item.ItemBase;
import com.deeplake.idealland.item.ModItems;
import com.deeplake.idealland.item.skills.martial.BuffTuple;
import com.deeplake.idealland.util.IDLSkillNBT;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import java.util.List;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;
import static com.deeplake.idealland.util.IDLSkillNBT.GetGuaEnhance;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.CARD_SUIT;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.STATE;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil.GetInt;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil.SetInt;


public class ItemUNOCard extends ItemBase implements IGuaEnhance {
    public ItemUNOCard(String name) {
        super(name);
        setMaxStackSize(1);
        setMaxDamage(64);
        shiftToShowDesc = true;
        this.addPropertyOverride(new ResourceLocation(CARD_SUIT), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)//when not in hand, world is null.
                {
                    return 1.0F;//set to 0 will cause no updating when not in hand. dunno why
                }
                else {
                    return getTypeForCard(stack);
                }
            }
        });

       // BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
    }

    public float getAttackDamageMatch(ItemStack stack)
    {
        return 10 + IDLSkillNBT.GetGuaEnhance(stack, 7);
    }

    public float getBackfireDamage(ItemStack stack)
    {
        return 5 - IDLSkillNBT.GetGuaEnhance(stack, 0);
    }

    public int getMaxTypeIndex(ItemStack stack)
    {
        return 3;//0,1,2,3=R,G,U,Y
    }

    public int getTypeForCard(ItemStack stack)
    {
        return GetInt(stack, CARD_SUIT);
    }
    public void setTypeForCard(ItemStack stack, int suit)
    {
        SetInt(stack, CARD_SUIT, suit);
    }

    public void AssignRandomCardToEntityMainHand(EntityLivingBase creature)
    {
        ItemStack randomNewCard = new ItemStack(ModItems.ITEM_UNO_CARD);
        setTypeForCard(randomNewCard, creature.getRNG().nextInt(getMaxTypeIndex(randomNewCard) + 1));
        creature.setHeldItem(EnumHand.MAIN_HAND, randomNewCard);
    }

    public void PlayerDrawCard(EntityPlayer player)
    {
        ItemStack randomNewCard = new ItemStack(ModItems.ITEM_UNO_CARD);
        setTypeForCard(randomNewCard, player.getRNG().nextInt(getMaxTypeIndex(randomNewCard) + 1));
        player.addItemStackToInventory(randomNewCard);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity instanceof EntityLivingBase)
        {
            World world = player.world;
            boolean isRemote = player.world.isRemote;
            EntityLivingBase creatureOnHit = (EntityLivingBase) entity;
            ItemStack opponentCard = creatureOnHit.getHeldItemMainhand();
            if (opponentCard.isEmpty())
            {
                if (isRemote)
                {
                    world.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, entity.posX, entity.posY, entity.posZ, 0,0,0);
                }else {
                    AssignRandomCardToEntityMainHand(creatureOnHit);
                }
            }
            else if (opponentCard.getItem() instanceof ItemUNOCard)
            {
                int mySuit = getTypeForCard(stack);
                int theirSuit = getTypeForCard(opponentCard);
                if (mySuit == theirSuit)
                {
                    if (isRemote)
                    {
                        world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, entity.posX, entity.posY, entity.posZ, 0,0,0);
                    }else {
                        creatureOnHit.attackEntityFrom(DamageSource.causePlayerDamage(player), getAttackDamageMatch(stack));
                        AssignRandomCardToEntityMainHand(creatureOnHit);
                    }

                }else {
                    if (isRemote)
                    {
                        world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, player.posX, player.posY, player.posZ, 0,0,0);
                    }else {
                        player.attackEntityFrom(DamageSource.causeMobDamage(creatureOnHit), getBackfireDamage(stack));
                        PlayerDrawCard(player);
                    }
                }
            }
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public boolean acceptGuaIndex(int index) {
        return index == 0 || index == 7;
    }

    @SideOnly(Side.CLIENT)
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        String key = stack.getUnlocalizedName() + ".desc";
        if (I18n.hasKey(key))
        {
            return I18n.format(stack.getUnlocalizedName() + ".desc",
                    getAttackDamageMatch(stack),
                    getBackfireDamage(stack)
            );
        }
        return "";
    }
}
