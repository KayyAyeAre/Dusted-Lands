package dusted.world.blocks.powder;

import dusted.type.*;
import mindustry.gen.*;

public class PowderVoid extends PowderBlock {
    public PowderVoid(String name) {
        super(name);

        update = true;
        solid = true;
    }

    public class PowderVoidBuild extends PowderBuild {
        @Override
        public boolean acceptPowder(Building source, Powder powder) {
            return enabled;
        }

        @Override
        public void handlePowder(Powder powder, float amount) {
        }
    }
}
