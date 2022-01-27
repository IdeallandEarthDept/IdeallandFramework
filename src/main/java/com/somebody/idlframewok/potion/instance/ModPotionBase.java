package com.somebody.idlframewok.potion.instance;

import com.somebody.idlframewok.potion.ModPotionType;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModPotionBase extends Potion {
    protected static final ResourceLocation resource = new ResourceLocation("idlframewok","textures/misc/potions.png");
    protected final int iconIndex;

    public ModPotionType getPotionType() {
        return potionType;
    }

    ModPotionType potionType;

//    if (!this.world.isRemote)
//    {
//        layer.getPotion().applyAttributesModifiersToEntity(this, this.getAttributeMap(), layer.getAmplifier());
//    }

    //REGISTRY.register(18, new ResourceLocation("weakness"), (
    // new PotionAttackDamage(true, 4738376, -4.0D))
    // .setPotionName("effect.weakness")
    // .setIconIndex(5, 0)
    // .registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, 0));

   public static final String POTION_PREFIX = "idlframewok.potion.";

    public ModPotionBase(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn);
        setRegistryName(new ResourceLocation(Reference.MOD_ID, name));

        setPotionName(POTION_PREFIX + name);
        iconIndex = icon;

        ModPotions.INSTANCES.add(this);
        potionType = new ModPotionType(getName(), new PotionEffect(this, isBadEffectIn ? 1800 : 3600));

    }


//    public static void registerBottles()
//    {
//        registerPotionType("strength", new PotionType(new PotionEffect[] {new PotionEffect(MobEffects.STRENGTH, 3600)}));
//        registerPotionType("long_strength", new PotionType("strength", new PotionEffect[] {new PotionEffect(MobEffects.STRENGTH, 9600)}));
//        registerPotionType("strong_strength", new PotionType("strength", new PotionEffect[] {new PotionEffect(MobEffects.STRENGTH, 1800, 1)}));
//        registerPotionType("weakness", new PotionType(new PotionEffect[] {new PotionEffect(MobEffects.WEAKNESS, 1800)}));
//        registerPotionType("long_weakness", new PotionType("weakness", new PotionEffect[] {new PotionEffect(MobEffects.WEAKNESS, 4800)}));
//    }

    @SideOnly(Side.CLIENT)
    protected void render(int x, int y, float alpha) {
        Minecraft.getMinecraft().renderEngine.bindTexture(resource);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
        buf.begin(7, DefaultVertexFormats.POSITION_TEX);
        GlStateManager.color(1, 1, 1, alpha);

        int textureX = iconIndex % 14 * 18;
        int textureY = 198 - iconIndex / 14 * 18;

        buf.pos(x, y + 18, 0).tex(textureX * 0.00390625, (textureY + 18) * 0.00390625).endVertex();
        buf.pos(x + 18, y + 18, 0).tex((textureX + 18) * 0.00390625, (textureY + 18) * 0.00390625).endVertex();
        buf.pos(x + 18, y, 0).tex((textureX + 18) * 0.00390625, textureY * 0.00390625).endVertex();
        buf.pos(x, y, 0).tex(textureX * 0.00390625, textureY * 0.00390625).endVertex();

        tessellator.draw();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        render(x + 6, y + 7, 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        render(x + 3, y + 3, alpha);
    }

    /**
     * Returns true if the potion has a associated status icon to display in then inventory when active.
     */
//    @SideOnly(Side.CLIENT)
//    public boolean hasStatusIcon()
//    {
//        return this.statusIconIndex >= 0;
//    }
}
