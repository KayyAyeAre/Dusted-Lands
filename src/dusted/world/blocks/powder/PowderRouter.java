package dusted.world.blocks.powder;

import dusted.type.*;
import mindustry.gen.*;

public class PowderRouter extends PowderBlock {
    public PowderRouter(String name) {
        super(name);
        hasPower = true;
    }

    public class PowderRouterBuild extends PowderBuild {

        @Override
        public void updateTile() {
            dumpPowder(powders.current());
        }

        @Override
        public boolean acceptPowder(Building source, Powder powder) {
            return (powders.current() == powder || powders.currentAmount() < 0.2f);
        }
    }
}
