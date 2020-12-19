package com.somebody.idlframewok.init;

import com.somebody.idlframewok.util.*;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
    public static void checkEdictCraft(AnvilUpdateEvent event)
    {
//        if (event.getLeft() != ItemStack.EMPTY && event.getRight() != ItemStack.EMPTY ) {
//            ItemStack left = event.getLeft();
//            ItemStack right = event.getRight();
//
//            if (left.getItem() == Items.PAPER && right.getItem() == Item.getItemFromBlock(Blocks.GOLD_BLOCK))
//            {
//                event.setMaterialCost(1);
//                event.setCost(30);
//
//                ItemStack result = new ItemStack(ModItems.RANDOM_EDICT);
//                event.setOutput(result);
//            }
//        }
    }
}
