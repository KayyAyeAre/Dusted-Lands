package dusted.world.blocks.environment;

import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class VolcanoZone extends OverlayFloor {
    public VolcanoZone(String name) {
        super(name);
        variants = 0;
        needsSurface = false;
    }

    @Override
    public void drawBase(Tile tile) {}
}
