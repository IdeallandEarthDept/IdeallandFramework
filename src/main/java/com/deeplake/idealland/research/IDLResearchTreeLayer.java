package com.deeplake.idealland.research;

import com.deeplake.idealland.Idealland;

public class IDLResearchTreeLayer {
    public int layer;
    public IDLResearchNode[] nodes;

    public IDLResearchTreeLayer(int layer) {
        if (layer < 0)
        {
            Idealland.LogWarning("negative layer index");
            layer = 0;
        }
        this.layer = layer;

        nodes = new IDLResearchNode[1 << layer];
    }


}
