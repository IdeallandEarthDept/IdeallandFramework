package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.item.skills.classfit.EnumSkillClass;

public class ItemClassEssence extends ItemBase {
    public final EnumSkillClass skillClass;
    public ItemClassEssence(String name, EnumSkillClass skillClass) {
        super(name);
        this.skillClass = skillClass;
    }
}
