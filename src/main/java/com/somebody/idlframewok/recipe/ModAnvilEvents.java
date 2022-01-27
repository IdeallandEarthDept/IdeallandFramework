package com.somebody.idlframewok.recipe;

import com.somebody.idlframewok.enchantments.ModEnchantmentBase;
import com.somebody.idlframewok.enchantments.ModEnchantmentInit;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.item.IGuaEnhance;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.item.misc.ItemBasicGua;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.IDLGeneral;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.somebody.idlframewok.util.CommonDef.*;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.*;
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
//        checkNormalSocket(event, ModItems.CYCLE_STONE, CYCLE_SOCKET);
//        checkNormalSocketArmorOnly(event, ModItems.JADE, JADE_SOCKET);
//        checkNormalSocket(event, ModItems.MISC, 0, FLESH_SOCKET);
        checkGuaImbue(event);
        checkEasyRepair(event);
    }

    public static void checkNormalSocketArmorOnly(AnvilUpdateEvent event, Item item, String key) {
        if (event.getLeft().getItem() instanceof ItemArmor)
        {
            checkNormalSocket(event, item, key);
        }
    }

    public static void checkNormalSocket(AnvilUpdateEvent event, Item item, int meta, String key) {
        ItemStack left = event.getLeft();

        if (!(left.isStackable())  &&
                (event.getRight().getItem() == item)
                && event.getRight().getMetadata() == meta) {

            //ItemStack right = event.getRight();

            if (IDLNBTUtil.GetInt(left, key) != 0 || IDLNBTUtil.GetInt(left, ANY_SOCKET) > 0)
            {
                return;
            }

            int level = 1 + left.getMaxDamage() / 255;

            ItemStack result = left.copy();
            IDLNBTUtil.setInt(result, key, 1);
            IDLNBTUtil.setInt(result, ANY_SOCKET, 1);

            event.setMaterialCost(1);
            event.setCost(level);

            event.setOutput(result);
        }
    }

    public static void checkNormalSocket(AnvilUpdateEvent event, Item item, String key) {
        ItemStack left = event.getLeft();

        if (!(left.isStackable())  &&
                (event.getRight().getItem() == item)) {

            //ItemStack right = event.getRight();

            if (IDLNBTUtil.GetInt(left, key) != 0 || IDLNBTUtil.GetInt(left, ANY_SOCKET) > 0)
            {
                return;
            }

            int level = 1 + left.getMaxDamage() / 255;

            ItemStack result = left.copy();
            IDLNBTUtil.setInt(result, key, 1);
            IDLNBTUtil.setInt(result, ANY_SOCKET, 1);

            event.setMaterialCost(1);
            event.setCost(level);

            event.setOutput(result);
        }
    }

    public static void checkGuaImbue(AnvilUpdateEvent event)
    {
//        if (event.getLeft() != ItemStack.EMPTY && event.getRight() != ItemStack.EMPTY  &&
//                !(event.getLeft().getItem() instanceof IGuaEnhance)) {
//            ItemStack left = event.getLeft();
//            ItemStack right = event.getRight();
//
//            int level = 0;
//            int material_cost = 0;
//            int exp_cost = 0;
//
//            int cost_per_level = 4;
//            int exp_cost_per_level = 4;
//            int maxLv = 3;
//
//            if (right.getItem() instanceof ItemBasicGua) {
//                level = Math.min(maxLv, right.getCount() / cost_per_level);
//                material_cost = level * cost_per_level;
//                exp_cost = level * exp_cost_per_level;
//            }
//
//            if (left.isItemEnchantable() && level > 0) {
//                boolean hasResult = true;
//                ItemStack swordResult = left.copy();
//                if (left.getItem() instanceof ItemSword || left.getItem() instanceof ItemBow) {
//                    if (right.getItem() == ModItems.GUA[G_FIRE])//fire
//                    {
//                        //fire_aspect:20; flame:50
//                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(left.getItem() instanceof ItemSword ? 20 : 50), level);//fire_aspect
//                    } else if (right.getItem() == ModItems.GUA[G_WIND])//wind
//                    {
//                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(left.getItem() instanceof ItemSword ? 19 : 49), level);//19-knockback  49-punch
//                    } else if (right.getItem() == ModItems.GUA[CommonDef.G_EARTH]) {
//                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(34), level);//unbreaking
//                    } else if (right.getItem() == ModItems.GUA[CommonDef.G_SKY]) {
//                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(left.getItem() instanceof ItemSword ? 16 : 48), level);//sharp & power
//                    } else if (right.getItem() == ModItems.GUA[G_LAKE])
//                    {
//                        if (left.getItem() instanceof ItemSword)
//                        {
//                            swordResult.addEnchantment(Enchantment.getEnchantmentByID(21), level);//looting
//                        }
//                        else {
//                            cost_per_level = 4;
//                            exp_cost_per_level = 20;
//                            maxLv = 1;
//
//                            level = Math.min(maxLv, right.getCount() / cost_per_level);
//                            material_cost = level * cost_per_level;
//                            exp_cost = level * exp_cost_per_level;
//
//                            hasResult = level > 0;
//                            if (hasResult)
//                            {
//                                swordResult.addEnchantment(Enchantment.getEnchantmentByID(51), level);//infinity
//                            }
//                        }
//                    }
//                    else {
//                        hasResult = false;
//                    }
//                }
//                 else if (left.getItem() instanceof ItemArmor) {
//                    if (right.getItem() == ModItems.GUA[G_FIRE])//fire
//                    {
//                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(1), level);//fire_protection
//                    }
//                    else if (right.getItem() == ModItems.GUA[G_WATER])
//                    {
//                        if ((left.getItem()).getEquipmentSlot(left) == FEET)
//                        {
//                            swordResult.addEnchantment(Enchantment.getEnchantmentByID(8), level);//depth_strider
//                        }
//                        else if ((left.getItem()).getEquipmentSlot(left) == HEAD)
//                        {
//                            swordResult.addEnchantment(Enchantment.getEnchantmentByID(5), level);//respiration
//                        }
//                        else
//                        {
//                            hasResult = false;
//                        }
//                    }
//                    else if (right.getItem() == ModItems.GUA[G_LAKE])
//                    {
//                        if ((left.getItem()).getEquipmentSlot(left) == HEAD)
//                        {
//                            swordResult.addEnchantment(Enchantment.getEnchantmentByID(6), level);//aqua_affinity
//                        }
//                        else
//                        {
//                            hasResult = false;
//                        }
//                    }
//                    else if (right.getItem() == ModItems.GUA[G_MOUNTAIN])
//                    {
//                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(4), level);//projectile_protection
//                    }
//                    else if (right.getItem() == ModItems.GUA[CommonDef.G_EARTH])
//                    {
//                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(34), level);//unbreaking
//                    }
//                    else if (right.getItem() == ModItems.GUA[CommonDef.G_SKY])
//                    {
//                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(0), level);//protection
//                    }
//                    else if (right.getItem() == ModItems.GUA[G_THUNDER])
//                    {
//                        swordResult.addEnchantment(Enchantment.getEnchantmentByID(3), level);//blast_protection
//                    }
//                    else if (right.getItem() == ModItems.GUA[G_WIND])//wind
//                    {
//                        if ((left.getItem()).getEquipmentSlot(left) == FEET)
//                        {
//                            swordResult.addEnchantment(Enchantment.getEnchantmentByID(6), level);//feather_falling
//                        }
//                        else
//                        {
//                            hasResult = false;
//                        }
//                    }
//                    else {
//                        hasResult = false;
//                    }
//                }
//                if (hasResult) {
//                    event.setMaterialCost(material_cost);
//                    event.setCost(exp_cost);
//                    event.setOutput(swordResult);
//                }
//            }
//
//
//        }
    }

    public static void checkEasyRepair(AnvilUpdateEvent event) {
        ItemStack stack = event.getLeft();
        ItemStack result = event.getOutput();
        if (stack.getItem() == result.getItem()) {
            int easyLevel = ModEnchantmentInit.EASY_REPAIR.getEnchantmentLevel(stack);
            switch (easyLevel) {
                case 1:
                    result.setRepairCost(stack.getRepairCost() + 1);
                    break;
                case 2:
                    result.setRepairCost(stack.getRepairCost());
                    break;
                default:
                    break;
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

    static final ModEnchantmentBase[] MATERIALS = {
            ModEnchantmentInit.FIRE_FORM,
            ModEnchantmentInit.WATER_FORM,
            ModEnchantmentInit.TIME_FORM,
            ModEnchantmentInit.SUN_FORM
    };

    static boolean notOfSameKind(ItemStack stack1, ItemStack stack2)
    {
        for (ModEnchantmentBase ench:
             MATERIALS) {
            if (notOfSameKind(ench, stack1, stack2))
            {
                return true;
            }
        }

        return false;
    }

    static boolean notOfSameKind(ModEnchantmentBase enchantmentBase, ItemStack stack1, ItemStack stack2)
    {
        boolean is1 = enchantmentBase.getEnchantmentLevel(stack1) > 0;
        boolean is2 = enchantmentBase.getEnchantmentLevel(stack2) > 0;
        return is1 != is2;
    }

}
