package com.somebody.idlframewok.potion.buff;

import net.minecraft.entity.EntityLivingBase;

import javax.annotation.Nonnull;

public class BasePotion extends BaseSimplePotion {
//    private static final ResourceLocation resource = new ResourceLocation("idlframewok","textures/misc/potions.png");
//    private final int iconIndex;

    protected float damageReductionRatioBase = 0.0f;
    protected float damageReductionRatioPerLevel = 0.0f;

    protected float attackIncreaseRatioBase = 0.0f;
    protected float attackIncreaseRatioPerLevel = 0.0f;

    protected float knockbackResistanceRatio = 0f;
    protected float knockbackResistanceRatioPerLevel = 0f;

    protected float hpRecoverPerTick = 0f;
    protected float hpRecoverPerLevel = 0f;

    protected float critRateBase = 0f;
    protected float critRatePerLevel = 0f;

    //default = 1.5x
    protected float critDmgRatioBase = 0f;
    protected float critDmgRatioPerLevel = 0f;

    protected float resistancePerLevel = 0f;//for any buff


    //-----------------------------------------
    public BasePotion setHPRevocer(float begin, float upgrade)
    {
        hpRecoverPerTick = begin;
        hpRecoverPerLevel = upgrade;
        return this;
    }

    public BasePotion setKBResistance(float begin, float upgrade)
    {
        knockbackResistanceRatio = begin;
        knockbackResistanceRatioPerLevel = upgrade;
        return this;
    }

    public BasePotion setAttackRatio(float begin, float upgrade)
    {
        attackIncreaseRatioBase = begin;
        attackIncreaseRatioPerLevel = upgrade;
        return this;
    }

    public BasePotion setProtectionRatio(float begin, float upgrade)
    {
        damageReductionRatioBase = begin;
        damageReductionRatioPerLevel = upgrade;
        return this;
    }

    public BasePotion setCritRateRatio(float begin, float upgrade)
    {
        critRateBase = begin;
        critRatePerLevel = upgrade;
        return this;
    }

    public BasePotion setCritDamageRatio(float begin, float upgrade)
    {
        critDmgRatioBase = begin;
        critDmgRatioPerLevel = upgrade;
        return this;
    }
    //----------------------------------------

    public BasePotion(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
    }
//    public BasePotion(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
//        super(isBadEffectIn, liquidColorIn);
//        setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
//        setPotionName("idlframewok.potion." + name);
//        iconIndex = icon;
//    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(@Nonnull EntityLivingBase living, int amplified) {
        if (living.getHealth() < living.getMaxHealth())
        {
            living.heal(hpRecoverPerTick + amplified * hpRecoverPerLevel);
        }
    }

    public void playOnHitEffect(EntityLivingBase entityLivingBase, float damage)
    {

    }

    //damage = (1-x)damage
    public float getDamageReductionMultiplier(int level)
    {
        //cant deal negative damage
        return Math.min(1f, damageReductionRatioBase + level * damageReductionRatioPerLevel);
    }

    public float getHealPerTick(int level)
    {
        //can be negative
        return  hpRecoverPerTick + level * hpRecoverPerLevel;
    }

    //damage = (1+x)damage
    public float getAttackMultiplier(int level)
    {
        return Math.max(-1f, attackIncreaseRatioBase + level * attackIncreaseRatioPerLevel);
    }

    //KB = (1+x)KB
    public float getKBResistanceMultiplier(int level)
    {
        return Math.max(-1f, knockbackResistanceRatio + level * knockbackResistanceRatioPerLevel);
    }

    public float getCritRate(int level)
    {
        //can be negative
        return critRateBase + level * critRatePerLevel;
    }

    //need to plus 1.5
    public float getCritDmgModifier(int level)
    {
        //can be negative
        return critDmgRatioBase + level * critDmgRatioPerLevel;
    }

//    @SideOnly(Side.CLIENT)
//    private void render(int x, int y, float alpha) {
//        Minecraft.getMinecraft().renderEngine.bindTexture(resource);
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder buf = tessellator.getBuffer();
//        buf.begin(7, DefaultVertexFormats.POSITION_TEX);
//        GlStateManager.color(1, 1, 1, alpha);
//
//        int textureX = iconIndex % 8 * 18;
//        int textureY = 198 + iconIndex / 8 * 18;
//
//        buf.pos(x, y + 18, 0).tex(textureX * 0.00390625, (textureY + 18) * 0.00390625).endVertex();
//        buf.pos(x + 18, y + 18, 0).tex((textureX + 18) * 0.00390625, (textureY + 18) * 0.00390625).endVertex();
//        buf.pos(x + 18, y, 0).tex((textureX + 18) * 0.00390625, textureY * 0.00390625).endVertex();
//        buf.pos(x, y, 0).tex(textureX * 0.00390625, textureY * 0.00390625).endVertex();
//
//        tessellator.draw();
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
//        render(x + 6, y + 7, 1);
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
//        render(x + 3, y + 3, alpha);
//    }
}
