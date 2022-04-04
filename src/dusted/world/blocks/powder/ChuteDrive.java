package dusted.world.blocks.powder;

import arc.math.*;
import dusted.world.meta.CustomStatValue;
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
        new CustomStatValue("output-charge", StatValues.number(outputCharge, StatUnit.none)).add(stats);
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
                movePowderForward(true, powders.current());
            }
        }
    }
}
