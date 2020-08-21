package com.deeplake.idealland.init;

import com.deeplake.idealland.enchantments.ModEnchantmentBase;
import com.deeplake.idealland.enchantments.ModEnchantmentInit;
import com.deeplake.idealland.item.IGuaEnhance;
import com.deeplake.idealland.item.ModItems;
import com.deeplake.idealland.item.misc.ItemBasicGua;
import com.deeplake.idealland.util.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.deeplake.idealland.util.CommonDef.*;
import static net.minecraft.inventory.EntityEquipmentSlot.FEET;
import static net.minecraft.inventory.EntityEquipmentSlot.HEAD;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModAnvilEvents {
//    @Cancelable
//    public class AnvilUpdateEvent extends Event
//    {
//        @Nonnull
//        private final ItemStack left;  // The left side of the input
//        @Nonnull
//        private final ItemStack right; // The right side of the input
//        private final String name;     // The name to set the item, if the user specified one.
//        @Nonnull
//        private ItemStack output;      // Set this to set the output stack
//        private int cost;              // The base cost, set this to change it if output != null
//        private int materialCost; // The number of items from the right slot to be consumed during the repair. Leave as 0 to consume the entire stack.
//
//        public AnvilUpdateEvent(@Nonnull ItemStack left, @Nonnull ItemStack right, String name, int cost)
//        {
//            this.left = left;
//            this.right = right;
//            this.output = ItemStack.EMPTY;
//            this.name = name;
//            this.setCost(cost);
//            this.setMaterialCost(0);
//        }
//
//        @Nonnull
//        public ItemStack getLeft() { return left; }
//        @Nonnull
//        public ItemStack getRight() { return right; }
//        public String getName() { return name; }
//        @Nonnull
//        public ItemStack getOutput() { return output; }
//        public void setOutput(@Nonnull ItemStack output) { this.output = output; }
//        public int getCost() { return cost; }
//        public void setCost(int cost) { this.cost = cost; }
//        public int getMaterialCost() { return materialCost; }
//        public void setMaterialCost(int materialCost) { this.materialCost = materialCost; }
//    }

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        checkGuaImbue(event);
    }
    public static void checkGuaImbue(AnvilUpdateEvent event)
    {
        if (event.getLeft() != ItemStack.EMPTY && event.getRight() != ItemStack.EMPTY  &&
                !(event.getLeft().getItem() instanceof IGuaEnhance)) {
            ItemStack left = event.getLeft();
            ItemStack right = event.getRight();

            int level = 0;
            int material_cost = 0;
            int exp_cost = 0;

            int cost_per_level = 4;
            int exp_cost_per_level = 4;
            int maxLv = 3;

            if (right.getItem() instanceof ItemBasicGua) {
                level = Math.min(maxLv, right.getCount() / cost_per_level);
                material_cost = level * cost_per_level;
                exp_cost = level * exp_cost_per_level;
            }

            if (left.isItemEnchantable() && level > 0) {
                boolean hasResult = true;
                ItemStack swordResult = left.copy();
                if (left.getItem() instanceof ItemSword || left.getItem() instanceof ItemBow) {
                    if (right.getItem() == ModItems.GUA[G_FIRE])//fire
                    {
                        //fire_aspect:20; flame:50
                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(left.getItem() instanceof ItemSword ? 20 : 50), level);//fire_aspect
                    } else if (right.getItem() == ModItems.GUA[G_WIND])//wind
                    {
                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(left.getItem() instanceof ItemSword ? 19 : 49), level);//19-knockback  49-punch
                    } else if (right.getItem() == ModItems.GUA[CommonDef.G_EARTH]) {
                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(34), level);//unbreaking
                    } else if (right.getItem() == ModItems.GUA[CommonDef.G_SKY]) {
                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(left.getItem() instanceof ItemSword ? 16 : 48), level);//sharp & power
                    } else if (right.getItem() == ModItems.GUA[G_LAKE])
                    {
                        if (left.getItem() instanceof ItemSword)
                        {
                            swordResult.addEnchantment(Enchantment.getEnchantmentByID(21), level);//looting
                        }
                        else {
                            cost_per_level = 4;
                            exp_cost_per_level = 20;
                            maxLv = 1;

                            level = Math.min(maxLv, right.getCount() / cost_per_level);
                            material_cost = level * cost_per_level;
                            exp_cost = level * exp_cost_per_level;

                            hasResult = level > 0;
                            if (hasResult)
                            {
                                swordResult.addEnchantment(Enchantment.getEnchantmentByID(51), level);//infinity
                            }
                        }
                    }
                    else {
                        hasResult = false;
                    }
                }
                 else if (left.getItem() instanceof ItemArmor) {
                    if (right.getItem() == ModItems.GUA[G_FIRE])//fire
                    {
                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(1), level);//fire_protection
                    }
                    else if (right.getItem() == ModItems.GUA[G_WATER])
                    {
                        if ((left.getItem()).getEquipmentSlot(left) == FEET)
                        {
                            swordResult.addEnchantment(Enchantment.getEnchantmentByID(8), level);//depth_strider
                        }
                        else if ((left.getItem()).getEquipmentSlot(left) == HEAD)
                        {
                            swordResult.addEnchantment(Enchantment.getEnchantmentByID(5), level);//respiration
                        }
                        else
                        {
                            hasResult = false;
                        }
                    }
                    else if (right.getItem() == ModItems.GUA[G_LAKE])
                    {
                        if ((left.getItem()).getEquipmentSlot(left) == HEAD)
                        {
                            swordResult.addEnchantment(Enchantment.getEnchantmentByID(6), level);//aqua_affinity
                        }
                        else
                        {
                            hasResult = false;
                        }
                    }
                    else if (right.getItem() == ModItems.GUA[G_MOUNTAIN])
                    {
                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(4), level);//projectile_protection
                    }
                    else if (right.getItem() == ModItems.GUA[CommonDef.G_EARTH])
                    {
                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(34), level);//unbreaking
                    }
                    else if (right.getItem() == ModItems.GUA[CommonDef.G_SKY])
                    {
                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(0), level);//protection
                    }
                    else if (right.getItem() == ModItems.GUA[G_THUNDER])
                    {
                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(3), level);//blast_protection
                    }
                    else if (right.getItem() == ModItems.GUA[G_WIND])//wind
                    {
                        if ((left.getItem()).getEquipmentSlot(left) == FEET)
                        {
                            swordResult.addEnchantment(Enchantment.getEnchantmentByID(6), level);//feather_falling
                        }
                        else
                        {
                            hasResult = false;
                        }
                    }
                    else {
                        hasResult = false;
                    }
                }
                if (hasResult) {
                    event.setMaterialCost(material_cost);
                    event.setCost(exp_cost);
                    event.setOutput(swordResult);
                }
            }


        }
    }

    @SubscribeEvent
    public static void checkGuaEnhance(AnvilUpdateEvent event)
    {
        if (event.getLeft() != ItemStack.EMPTY && event.getRight() != ItemStack.EMPTY ) {
            ItemStack left = event.getLeft();
            ItemStack right = event.getRight();

            if (left.getItem() instanceof IGuaEnhance)
            {
                IGuaEnhance leftItem = (IGuaEnhance) left.getItem();
                int guaIndex = IDLGeneral.returnGuaIndex(right);
                if (guaIndex >= 0 && leftItem.acceptGuaIndex(guaIndex))
                {
                    int curCount = IDLSkillNBT.GetGuaEnhanceTotal(left);
                    event.setMaterialCost(left.getCount());
                    event.setCost((curCount + 1)*left.getCount());

                    ItemStack result = left.copy();
                    IDLSkillNBT.AddGuaEnhance(result, guaIndex, 1);
                    event.setOutput(result);
                }
                else {
                    event.setOutput(ItemStack.EMPTY);
                    //Idealland.Log("right is not gua");
                }
            }
        }
    }

    @SubscribeEvent
    public static void checkEdictCraft(AnvilUpdateEvent event)
    {
        if (!ModConfig.GeneralConf.EDICT_CRAFTABLE)
        {
            return;
        }

        if (event.getLeft() != ItemStack.EMPTY && event.getRight() != ItemStack.EMPTY ) {
            ItemStack left = event.getLeft();
            ItemStack right = event.getRight();

            if (left.getItem() == Items.PAPER && right.getItem() == Item.getItemFromBlock(Blocks.GOLD_BLOCK))
            {
                event.setMaterialCost(1);
                event.setCost(30);

                ItemStack result = new ItemStack(ModItems.RANDOM_EDICT);
                event.setOutput(result);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void stopNormalRepair(AnvilUpdateEvent event)
    {
        //Some enchantments stops the player from repairing it traditionally
        if (event.getLeft() != ItemStack.EMPTY && event.getRight() != ItemStack.EMPTY ) {
            ItemStack left = event.getLeft();
            ItemStack right = event.getRight();

            Item leftItem = left.getItem();
            if (leftItem.getIsRepairable(left, right))
            {
                if (notOfSameKind(left, right))
                {
                    event.setCanceled(true);
                }
            }
        }
    }

    static boolean notOfSameKind(ItemStack stack1, ItemStack stack2)
    {
        return notOfSameKind(ModEnchantmentInit.FIRE_FORM, stack1, stack2) || notOfSameKind(ModEnchantmentInit.WATER_FORM, stack1, stack2);
    }

    static boolean notOfSameKind(ModEnchantmentBase enchantmentBase, ItemStack stack1, ItemStack stack2)
    {
        boolean is1 = enchantmentBase.getEnchantmentLevel(stack1) > 0;
        boolean is2 = enchantmentBase.getEnchantmentLevel(stack2) > 0;
        return is1 != is2;
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void testAnvilEvent(AnvilUpdateEvent event)
    {

        if (event.getLeft() != ItemStack.EMPTY && event.getRight() != ItemStack.EMPTY ) {
            ItemStack left = event.getLeft();
            ItemStack right = event.getRight();

            if (left.getItem() == ModItems.L_M_ARMOR_1[0] && right.getItem() == Item.getItemFromBlock(Blocks.GOLD_BLOCK))
            {

            }
        }
    }
}
