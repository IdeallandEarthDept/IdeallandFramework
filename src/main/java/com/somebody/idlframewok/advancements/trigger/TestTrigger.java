package com.somebody.idlframewok.advancements.trigger;

import com.somebody.idlframewok.util.CommonFunctions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.Set;

public class TestTrigger implements ICriterionTrigger<TestTrigger.Instance> {
    public static final ResourceLocation ID = CommonFunctions.getResLoc("trigger_one");
    private final Map<PlayerAdvancements, Listeners> listeners = Maps.newHashMap();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<TestTrigger.Instance> listener) {
        TestTrigger.Listeners listeners = this.listeners.computeIfAbsent(playerAdvancementsIn, Listeners::new);
        listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<TestTrigger.Instance> listener) {
        TestTrigger.Listeners listeners = this.listeners.get(playerAdvancementsIn);
        if (listeners != null) {
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    @Override
    public TestTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return new TestTrigger.Instance();
    }

    //add any arguments, like trigger(ItemStack from, ItemStack to)
    public void trigger(EntityPlayerMP player) {
        TestTrigger.Listeners listeners = this.listeners.get(player.getAdvancements());
        if (listeners != null) {
            listeners.trigger();
        }
    }

    public static class Instance extends AbstractCriterionInstance {

        public Instance() {
            super(TestTrigger.ID);
        }
    }

    static class Listeners {

        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<Instance>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<TestTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<TestTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger() {
            for (ICriterionTrigger.Listener<TestTrigger.Instance> listener : Lists.newArrayList(this.listeners)) {
                listener.grantCriterion(this.playerAdvancements);
            }
        }
    }
}
