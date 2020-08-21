package com.deeplake.idealland.research;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.item.ModItems;
import com.deeplake.idealland.util.CommonDef;
import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.IDLNBT;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTDef;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class IDLResearchTree {
    public static IDLResearchTree getInstance() {
        if (instance == null)
        {
            Idealland.LogWarning("Accessing Research Tree Too Early");
        }
        return instance;
    }

    public static void InitTree()
    {
        //Idealland.Log("Research Tree Initializing");
        instance = new IDLResearchTree();
        instance.ConnectAllNodes();
        //Idealland.Log("Research Tree Init Complete");
    }

    static IDLResearchTree instance;

    public IDLResearchTreeLayer[] layers;

    public static final int OFFSET = 32;
    static final int maxLayer = 10;

    public IDLResearchTree() {
        layers = new IDLResearchTreeLayer[maxLayer];
        for (int i = 0; i < maxLayer; i++)
        {
            layers[i] = new IDLResearchTreeLayer(i);
        }

        layers[0].nodes[0] = new IDLResearchNode(ModItems.ITEM_DISTURB_MEASURE_2, 10);

        layers[1].nodes[0] = new IDLResearchNode(ModItems.YIN_SIGN, 5);
        layers[1].nodes[1] = new IDLResearchNode(ModItems.YANG_SIGN, 5);

        layers[2].nodes[0] = new IDLResearchNode(ModItems.ITEM_FUTURE_PACKGE, 5);
        layers[2].nodes[1] = new IDLResearchNode(ModItems.ITEM_FUTURE_PACKGE, 5);
        layers[2].nodes[2] = new IDLResearchNode(ModItems.ITEM_FUTURE_PACKGE, 5);
        layers[2].nodes[3] = new IDLResearchNode(ModItems.ITEM_FUTURE_PACKGE, 5);

        for (int i = 0; i < CommonDef.GUA_TYPES; i++)
        {
            layers[3].nodes[i] = new IDLResearchNode(ModItems.GUA[i], 30, 3);
        }

    }

    public void ConnectAllNodes()
    {
        //register ID
        for (IDLResearchTreeLayer layer:
                layers
             ) {

            for (int i = 0; i < layer.nodes.length; i++)
            {
                IDLResearchNode node = layer.nodes[i];
                if (node != null)
                {
                    layer.nodes[i].pathIDLayer = layer.layer;
                    layer.nodes[i].pathID1 = i;
                }
            }
        }

        //connect
        for (int k = 0; k < maxLayer - 1; k++
        ) {
            IDLResearchTreeLayer layer = layers[k];
            IDLResearchTreeLayer layerNext = layers[k + 1];
            for (int i = 0; i < layer.nodes.length; i++)
            {
                if (layerNext.nodes[i] != null)
                {
                    layer.nodes[i].setChild(layerNext.nodes[i], 0);
                }

                if (layerNext.nodes[i + (1 << k)] != null) {
                    layer.nodes[i].setChild(layerNext.nodes[i + (1 << k)], 1);
                }
            }
        }
    }

    public IDLResearchNode getNodeFromID(int fullID)
    {
        return getNodeFromID(fullID / OFFSET, fullID % OFFSET);
    }

    public IDLResearchNode getNodeFromID(int id, int layer)
    {
        if (layer < layers.length && layer >= 0)
        {
            IDLResearchNode[] nodes = layers[layer].nodes;
            if (id < nodes.length && id >= 0)
            {
                return nodes[id];
            }
        }
        return null;
    }

    public int getParentIDForID(int fullID)
    {
        return getParentIDForID(fullID / OFFSET, fullID % OFFSET);
    }

    public int getParentIDForID(int id, int layer)
    {
        return (id % (1 << layer)) * OFFSET + layer - 1;
    }

    public int getChildIDForID(int fullID, int childIndex){
        return getChildIDForID(fullID / OFFSET, fullID % OFFSET, childIndex);
    }

    public int getChildIDForID(int id, int layer, int childIndex)
    {
        return id * OFFSET + layer + 1 + childIndex << (layer + 1);
    }

    public void onProduceComplete(EntityPlayer player, IDLResearchNode node)
    {
        ItemStack stack = node.grantItemStack();
        player.addItemStackToInventory(stack);
        CommonFunctions.SafeSendMsgToPlayer(player, "idealland.msg.produce_complete", node.pathIDLayer, Integer.toString(node.pathID1,2), stack.getDisplayName());
    }


    @SubscribeEvent
    public static void onTickPlayer(TickEvent.PlayerTickEvent event)
    {
        World world = event.player.world;
        if (!world.isRemote && (world.getWorldTime() % TICK_PER_SECOND == 0))
        {
//            EntityPlayer player = event.player;
//            int curLearnIndex = IDLNBT.getPlayerIdeallandIntSafe(player, IDLNBTDef.LEARNING_ID);
//            if (curLearnIndex >= 0)
//            {
//                IDLResearchNode node = IDLResearchTree.instance.getNodeFromID(curLearnIndex);
//                if (node != null)
//                {
//                    int curLearnProgress = IDLNBT.getPlayerIdeallandIntSafe(player, IDLNBTDef.LEARNING_PROGRESS);
//                    curLearnProgress += 1;
//
//                    boolean isLearnt = IDLNBTUtil.GetIsLearned(player, curLearnIndex);
//
//                    int cost = isLearnt ? node.produceTime : node.researchTime;
//
//                    while (curLearnProgress >= cost)
//                    {
//                        curLearnProgress -= cost;
//                        if (!isLearnt)
//                        {
//                            isLearnt = true;
//                            cost = node.produceTime;
//                            IDLNBTUtil.SetIsLearned(player, curLearnIndex, true);
//                            CommonFunctions.SafeSendMsgToPlayer(player, "idealland.msg.research_complete", node.pathIDLayer, Integer.toString(node.pathID1,2));
//                        }
//                        getInstance().onProduceComplete(player, node);
//                    }
//                    IDLNBT.setPlayerIdeallandTagSafe(player, IDLNBTDef.LEARNING_PROGRESS, curLearnProgress);
//                }
//            }
        }
    }

}
