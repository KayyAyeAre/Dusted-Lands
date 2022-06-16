package dusted.world.blocks.powder;

import dusted.type.*;
import dusted.world.interfaces.*;
import mindustry.gen.*;

public class PowderJunction extends PowderBlock {
    public PowderJunction(String name) {
        super(name);
        hasPower = true;
    }


    public class PowderJunctionBuild extends PowderBuild {
        @Override
        public Building getPowderDestination(Building source, Powder powder) {
            if (!enabled) return this;

            int dir = source.relativeTo(tile.x, tile.y);
            dir = (dir + 4) % 4;
            Building next = nearby(dir);

            if (next instanceof PowderBuildc build) {
                return build.getPowderDestination(this, powder);
            }

            return this;
        }
    }
}
