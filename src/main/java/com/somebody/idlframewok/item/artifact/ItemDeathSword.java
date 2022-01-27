package com.somebody.idlframewok.item.artifact;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.ItemSwordBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.PlayerUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.UUID;

import static com.somebody.idlframewok.util.CommonDef.EMPTY;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.*;
import static net.minecraft.entity.EntityList.createEntityByIDFromName;

@Mod.EventBusSubscriber
public class ItemDeathSword extends ItemSwordBase implements IArtifact {
    UUID IMPRINT_UUID = UUID.fromString("617af05fc-90e3-4083-8b7e-abd5eb73ac41");

    float damage = 13f;
    public ItemDeathSword(String name, ToolMaterial material) {
        super(name, material);
        //logNBT = true;
        setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation("pic"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                boolean state = isImprinted(stack);
                if (state) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        });
        glitters = true;
        setRarity(EnumRarity.EPIC);
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    static boolean isImprinted(ItemStack stack)
    {
        NBTTagCompound tagCompound = stack.getTagCompound();
        return tagCompound != null && stack.getTagCompound().getString(IMPRINT).length() > 0;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {

        if (PlayerUtil.isCreative(playerIn))
        {
            //Note that in Creative, the item will not change its NBT due to EntityPlayer.interactOn
            stack = playerIn.getHeldItem(hand);
        }

        if (isImprinted(stack))
        {
            if (!playerIn.world.isRemote) {
                Idealland.Log("%s is unleashing creature.", playerIn.getDisplayNameString());
                unleashNBT(playerIn.world, playerIn.getPositionVector().addVector(0,1f,0), target, stack);
            }
            return true;
        }

        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @SubscribeEvent
    static void onDeath(LivingDeathEvent event)
    {
        World world = event.getEntity().world;
        if (!world.isRemote && event.getEntityLiving() instanceof EntityLiving)
        {
            Entity attacker =event.getSource().getTrueSource();
            if (attacker instanceof EntityLivingBase )
            {
                ItemStack stack = ((EntityLivingBase) attacker).getHeldItemMainhand();
                if (!(stack.getItem() instanceof ItemDeathSword) || isImprinted(stack))
                {
                    return;
                }

                EntityLivingBase attackerTrue = (EntityLivingBase) attacker;
                Idealland.Log("%s stores creature %s", attackerTrue.getName(), event.getEntity().getName());
                storeEntityToNBT((EntityLiving) event.getEntity(), stack);
            }
        }
    }

    static void storeEntityToNBT(EntityLiving livingBase, ItemStack stack)
    {
        net.minecraftforge.fml.common.registry.EntityEntry entry = net.minecraftforge.fml.common.registry.EntityRegistry.getEntry(livingBase.getClass());
        ResourceLocation classType = EntityList.getKey(livingBase.getClass());
        if (classType == null)
        {
            return;
        }
        IDLNBTUtil.SetString(stack,IMPRINT, classType.toString());

        IDLNBTUtil.SetString(stack,IMPRINT_DISP, livingBase.hasCustomName() ? livingBase.getCustomNameTag() : EntityList.getEntityString(livingBase));

        IDLNBTUtil.SetBoolean(stack, IMPRINT_CUSTOM_DISP, livingBase.hasCustomName());

        NBTTagCompound creatureNBT = new NBTTagCompound();
        livingBase.writeEntityToNBT(creatureNBT);
        stack.setTagInfo(IDLNBTDef.IMPRINT_NBT, creatureNBT);

        if (stack.getTagCompound() != null)
        {
            Idealland.Log(stack.getTagCompound().toString());
        }
    }

    void unleashNBT(World world, Vec3d pos, EntityLivingBase target, ItemStack stack)
    {
        String key = stack.getTagCompound().getString(IMPRINT);
        if (key.length() > 0)
        {
            Entity entity = createEntityByIDFromName(new ResourceLocation(key), world);
            if (entity instanceof EntityLiving)
            {
                EntityLiving living = (EntityLiving) entity;
                entity.getEntityData().merge(stack.getTagCompound().getCompoundTag(IDLNBTDef.IMPRINT_NBT));
                living.readEntityFromNBT(entity.getEntityData());
                living.setHealth(living.getMaxHealth());
                living.deathTime = 0;
                living.setPosition(pos.x,pos.y,pos.z);
                living.setAttackTarget(target);
                world.spawnEntity(living);
                IDLNBTUtil.setInt(entity, IDLNBTDef.DYING, 1);

                Idealland.Log("Summon success: %s", key);
            }
            else {
                if (entity != null)
                {
                    entity.setDead();
                }
                Idealland.Log("Tried to generate: %s", key);
                world.createExplosion(null, pos.x,pos.y,pos.z, 2, false);
            }

            IDLNBTUtil.SetString(stack, IMPRINT, EMPTY);
            IDLNBTUtil.SetString(stack, IDLNBTDef.IMPRINT_NBT, EMPTY);
        }
    }

    @SubscribeEvent
    static void onTick(LivingEvent.LivingUpdateEvent event)
    {
        World world= event.getEntityLiving().world;
        if (!world.isRemote && CommonFunctions.isSecondTick(world))
        {
            int counter =IDLNBTUtil.GetInt(event.getEntity(), IDLNBTDef.DYING, 0);
            if (counter > 0)
            {
                event.getEntityLiving().attackEntityFrom(DamageSource.STARVE, counter * 0.1f);
                IDLNBTUtil.setInt(event.getEntity(), IDLNBTDef.DYING, counter + 1);
            }
        }
    }

    //temp
    public static void setAttributeModifiersForced(AbstractAttributeMap map, NBTTagList list)
    {
        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = list.getCompoundTagAt(i);
            IAttributeInstance iattributeinstance = map.getAttributeInstanceByName(nbttagcompound.getString("Name"));

            if (iattributeinstance == null)
            {
                IAttribute attribute = new RangedAttribute(null, nbttagcompound.getString("Name"), 0.0D, -1024.0D, 2048.0D);
                map.registerAttribute(attribute);
                iattributeinstance = map.getAttributeInstanceByName(nbttagcompound.getString("Name"));
                //Idealland.LogWarning("Ignoring unknown attribute '{}'", (Object)nbttagcompound.getString("Name"));
            }
            if (iattributeinstance != null) {
                applyModifiersToAttributeInstance(iattributeinstance, nbttagcompound);
            }
            else {
                Idealland.LogWarning("Strange internal error: ItemDeathSword");
            }
        }
    }

    static final String NAME_POSTFIX = ".1.name";
    static final String DESC_POSTFIX = ".1.desc";

    static final String FIX_1 = "entity.";
    static final String FIX_2 = ".name";

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (isImprinted(stack))
        {
            String regName= IDLNBTUtil.GetString(stack,IMPRINT_DISP,"");

            if (!IDLNBTUtil.GetBoolean(stack, IMPRINT_CUSTOM_DISP))
            {
                int index = regName.indexOf(":");
                if (index >= 0 && index < regName.length() - 1)
                {
                    regName=regName.substring(index + 1);
                }
                regName = net.minecraft.client.resources.I18n.format(FIX_1 + regName + FIX_2);
            }

            return net.minecraft.client.resources.I18n.format(this.getUnlocalizedNameInefficiently(stack) + NAME_POSTFIX, regName).trim();
        }else {
            return super.getItemStackDisplayName(stack);
        }
    }

    @Override
    public String descGetKey(ItemStack stack, World world, boolean showFlavor) {
        if (isImprinted(stack))
        {
            return showFlavor ? (stack.getUnlocalizedName() + IDLNBTDef.FLAVOR_KEY)
                    : (stack.getUnlocalizedName() + DESC_POSTFIX);
        }else {
            return super.descGetKey(stack, world, showFlavor);
        }
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            NBTTagCompound tagCompound = stack.getTagCompound();

            if (tagCompound != null && stack.getTagCompound().getString(IMPRINT).length() > 0)
            {
                NBTTagCompound compound = stack.getSubCompound(IDLNBTDef.IMPRINT_NBT);
                if (compound != null && compound.hasKey("Attributes", 9))
                {
                    AttributeMap map = new AttributeMap();
                    NBTTagList tagList = compound.getTagList("Attributes", 10);
                    setAttributeModifiersForced(map, tagList);

                    for (IAttributeInstance attributeInstance: map.getAllAttributes()) {
                        multimap.put(attributeInstance.getAttribute().getName(), new AttributeModifier(IMPRINT_UUID, "imprint modifier", attributeInstance.getAttributeValue(), 0));
                    }
                }
            }
            else {
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)(damage), 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
            }
        }

        return multimap;
    }

    private static void applyModifiersToAttributeInstance(IAttributeInstance instance, NBTTagCompound compound)
    {
        instance.setBaseValue(compound.getDouble("Base"));

        if (compound.hasKey("Modifiers", 9))
        {
            NBTTagList nbttaglist = compound.getTagList("Modifiers", 10);

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                AttributeModifier attributemodifier = SharedMonsterAttributes.readAttributeModifierFromNBT(nbttaglist.getCompoundTagAt(i));

                if (attributemodifier != null)
                {
                    AttributeModifier attributemodifier1 = instance.getModifier(attributemodifier.getID());

                    if (attributemodifier1 != null)
                    {
                        instance.removeModifier(attributemodifier1);
                    }

                    instance.applyModifier(attributemodifier);
                }
            }
        }
    }
}
