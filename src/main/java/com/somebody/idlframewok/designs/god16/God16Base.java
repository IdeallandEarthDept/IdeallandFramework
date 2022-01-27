package com.somebody.idlframewok.designs.god16;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.entity.creatures.mobs.skyland.EntityCatharVex;
import com.somebody.idlframewok.entity.creatures.mobs.skyland.EntityDarkElemental;
import com.somebody.idlframewok.entity.creatures.mobs.skyland.EntityPeaceKeeper;
import com.somebody.idlframewok.entity.creatures.mobs.skyland.EntityStoneElemental;
import com.somebody.idlframewok.util.ChancePicker;
import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;

import static com.somebody.idlframewok.util.Color16Def.*;

public class God16Base {
    public final int index;

    public Predicate<EntityLivingBase> NOT_GOD_BEILIVER;
    public boolean[][] symbolMap = new boolean[16][16];

    int MODIFIER = 1000;

    //pray gifts
    public ChancePicker tributeReward = new ChancePicker();

    public Potion debuff;
    public Potion buff;
    PotionType potionType;
    PotionType potionTypeBad;

    public God16Base(int index) {
        this.index = index;
        NOT_GOD_BEILIVER = input -> input != null && getBeliefPoint(input) <= 0;
    }

    public void setBuff(Potion buff, Potion debuff)
    {
        this.debuff = debuff;
        this.buff = buff;
    }

    public void setPotionType(PotionType good, PotionType bad)
    {
        setPotionType(good, false);
        setPotionType(bad, true);
    }

    public void setPotionType(PotionType type, boolean bad)
    {
        if (bad)
        {
            potionTypeBad = type;
        }else {
            potionType = type;
        }
    }

    public void setPotionType(String typeName, boolean bad)
    {
        PotionType type = PotionType.getPotionTypeForName(typeName);
        if (type == null)
        {
            Idealland.LogWarning("Trying to set non-existent potion type %s", typeName);
            return;
        }
        setPotionType(type, bad);
    }

    public PotionType getPotionType()
    {
        return potionType;
    }

    public PotionType getPotionTypeBad()
    {
        return potionTypeBad;
    }

    //icon
    boolean horiSym = false;
    boolean verSym = false;
    public enum Symmetric
    {
        HORI,
        VERT,
    }
    public void setSymmetric(Symmetric symmetric, boolean val)
    {
        switch (symmetric)
        {
            case HORI:
                horiSym = val;
                break;
            case VERT:
                verSym = val;
                break;
        }
    }

    public void drawPoint(int x, int y)
    {
        symbolMap[x][y] = true;
        if (horiSym)
        {
            symbolMap[15 - x][y] = true;
        }

        if (verSym)
        {
            symbolMap[x][15 - y] = true;
        }
    }

    public void drawRectOnMap(int x1, int y1, int x2, int y2)
    {
        drawLineOnMap(x1, y1, x2, y1);
        drawLineOnMap(x1, y1, x1, y2);
        drawLineOnMap(x1, y2, x2, y2);
        drawLineOnMap(x2, y1, x2, y2);
    }

    public void drawLineOnMap(int x1, int y1, int x2, int y2)
    {
        int t;
        if (x2 < x1)
        {
            t = x1;
            x1 = x2;
            x2 = t;
        }

        if (y2 < y1)
        {
            t = y1;
            y1 = y2;
            y2 = t;
        }

        int deltaX = x2 - x1 > 0 ? 1 : 0;
        int deltaY = y2 - y1 > 0 ? 1 : 0;

        //todo: check diagonal
        for (int x = x1; x <= x2; x+=deltaX)
        {
            for (int y = y1; y <= y2; y+=deltaY)
            {
                drawPoint(x, y);
                if (deltaY == 0)
                {
                    break;
                } else {
                    x += deltaX;//draw diagonal, only fits for 45 degrees
                }
            }
            if (deltaX == 0)
            {
                break;
            }
        }
    }

    public int getBeliefPoint(EntityLivingBase entityLivingBase)
    {
        int result = IDLNBTUtil.GetIntAuto(entityLivingBase, Color16Def.getKeyTribute(index), 0);

        if (entityLivingBase instanceof EntityCatharVex)
        {
            return result + MODIFIER;
        }

        if (index == DEATH && entityLivingBase.isEntityUndead())
        {
            result += MODIFIER;
        }
        else if (index == LIFE && entityLivingBase.isEntityUndead())
        {
            result -= MODIFIER;
        }
        else if (index == STONE && entityLivingBase instanceof EntityStoneElemental)
        {
            result += MODIFIER;
        }
        else if (index == EARTH && entityLivingBase instanceof EntityDarkElemental)
        {
            result += MODIFIER;
        }
        else if (index == FEMALE && entityLivingBase instanceof EntityPeaceKeeper)
        {
            result += MODIFIER;
        }

        return result;
    }

    public EntityEquipmentSlot getSlotForItem()
    {
        switch (index)
        {
            case DARKNESS: return EntityEquipmentSlot.FEET;
            case STONE: return EntityEquipmentSlot.CHEST;
            case FEMALE: return EntityEquipmentSlot.OFFHAND;
            case WIND: return EntityEquipmentSlot.LEGS;
            case SKY: return EntityEquipmentSlot.HEAD;
            default: return EntityEquipmentSlot.MAINHAND;
        }
    }

    public ItemStack getRepresentiveStack()
    {
        switch (index)
        {
            case DARKNESS:
                return new ItemStack(Items.DIAMOND_BOOTS);
            case FIRE:
                return new ItemStack(Items.FLINT_AND_STEEL);
            case LIFE:
                return new ItemStack(Items.DIAMOND_HOE);
            case SOIL:
                return new ItemStack(Items.DIAMOND_SHOVEL);
            case WATER:
                return new ItemStack(Items.WATER_BUCKET);
            case POISON:
                return new ItemStack(Items.SHEARS);
            case MALE:
                return new ItemStack(Items.DIAMOND_SWORD);
            case IRON:
                return new ItemStack(Items.DIAMOND_PICKAXE);
            case STONE:
                return new ItemStack(Items.DIAMOND_CHESTPLATE);
            case FEMALE:
                return new ItemStack(Items.SHIELD);
            case WOOD:
                return new ItemStack(Items.DIAMOND_AXE);
            case GOLD:
                return new ItemStack(Items.BOW);
            case WIND:
                return new ItemStack(Items.DIAMOND_LEGGINGS);
            case DEATH:
                return new ItemStack(Items.FISHING_ROD);
            case LAVA:
                return new ItemStack(Items.LAVA_BUCKET);
            case SKY:
                return new ItemStack(Items.DIAMOND_HELMET);
        }
        return ItemStack.EMPTY;
    }

}
