package dusted.content;

import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.world.blocks.storage.*;

public class DustedLoadouts {
    public static Schematic basicAbate;

    public static void load() {
        basicAbate = Schematics.readBase64("bXNjaAF4nBXKQQ6AIAwF0Y81bjyAp+BExkWhXZAgNRTj9dVk8lYDAhHmxqeC7DGsop57uUaxBmCpnLQ6pv0I2OT2oRIrN/GYrWvkxEO/L/x9vDVAE8w=");

        //aaaAAAAAA
        Reflect.<ObjectMap<CoreBlock, Seq<Schematic>>>get(Vars.schematics, "loadouts").get((CoreBlock) DustedBlocks.coreAbate, Seq::new).add(basicAbate);
        Reflect.<ObjectMap<CoreBlock, Schematic>>get(Vars.schematics, "defaultLoadouts").put((CoreBlock) DustedBlocks.coreAbate, basicAbate);
    }
}
