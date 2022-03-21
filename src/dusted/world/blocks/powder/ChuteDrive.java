package dusted.world.blocks.powder;

import arc.math.*;
import mindustry.world.meta.*;

public class ChuteDrive extends Chute {
    public int outputCharge = 16;

    public ChuteDrive(String name) {
        super(name);
        hasPower = true;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.charge, outputCharge);
    }

    public class ChuteDriveBuild extends ChuteBuild {
        @Override
        public void updateTile() {
            smoothPowder = Mathf.lerpDelta(smoothPowder, powders.currentAmount() / powderCapacity, 0.05f);

            if (consValid()) {
                charge = outputCharge;
            } else {
                charge = 0;
            }

            if (charge > 0 && powders.total() > 0.001f && timer(timerFlow, 1)) {
                movePowderForward(powders.current());
            }
        }
    }
}
