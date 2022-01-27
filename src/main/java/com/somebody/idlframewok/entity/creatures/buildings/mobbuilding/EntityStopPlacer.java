package com.somebody.idlframewok.entity.creatures.buildings.mobbuilding;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.entity.creatures.ICustomFaction;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.MessageDef;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

import static com.somebody.idlframewok.util.EntityUtil.NOT_UNDER_EMP;

public class EntityStopPlacer extends EntityModUnit implements ICustomFaction {
    public EntityStopPlacer(World worldIn) {
        super(worldIn);
        setBuilding();
        setSize(2f, 2f);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        setAttr(4, 0.1f, 1f, 8f, 40f);
    }

    //Prevents nearby player digging
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockPlace(BlockEvent.PlaceEvent event) {
        if (event.isCanceled())
        {
            return;
        }

        World world = event.getWorld();
        List<EntityStopDigger> units =
                EntityUtil.getEntitiesWithinAABB(world, EntityStopDigger.class, event.getPos(), 8f, NOT_UNDER_EMP);
        if (units.size() > 0)
        {
            event.setCanceled(true);
            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, event.getPlayer(), MessageDef.MSG_PLACE_STOPPED_BY_DAMPER, units.get(0).getPosition());
        }
    }
}
