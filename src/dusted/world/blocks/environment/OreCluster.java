package dusted.world.blocks.environment;

import arc.*;
import mindustry.type.*;
import mindustry.world.blocks.environment.*;

public class OreCluster extends StaticWall {
    public Item itemDrop;
    public float dropMultiplier = 1f;

    public OreCluster(String name, Item ore) {
        super(name);
        itemDrop = ore;
        localizedName = ore.localizedName + " " + Core.bundle.get("cluster");
        mapColor = ore.color;
        useColor = true;
    }
}
