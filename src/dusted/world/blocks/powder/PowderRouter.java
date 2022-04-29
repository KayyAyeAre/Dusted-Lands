package dusted.world.blocks.powder;

import arc.*;
import dusted.type.*;
import dusted.world.blocks.powder.Chute.*;
import dusted.world.interfaces.*;
import dusted.world.meta.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.meta.*;

public class PowderRouter extends PowderBlock {
    public int maxCharge = 16;

    public PowderRouter(String name) {
        super(name);
        hasPower = true;
    }

    @Override
    public void setStats() {
        super.setStats();

        DustedStatValues.customStats(stats, cstats -> {
            cstats.addCStat("max-charge", StatValues.number(maxCharge, StatUnit.none));
        });
    }

    @Override
    public void setBars() {
        super.setBars();
        bars.add("charge", build -> {
            ChuteBuild entity = (ChuteBuild) build;
            return new Bar(() -> Core.bundle.format("bar.charge"), () -> Pal.accent, () -> ((float) entity.charge) / ((float) ((Chute) entity.block).maxCharge));
        });
    }

    public class PowderRouterBuild extends PowderBuild implements Chargedc {
        public int charge;

        @Override
        public void updateTile() {
            updateCharge();

            if (charge > 0 && powders.total() > 0.01f) {
                dumpPowder(powders.current());
            }
        }

        @Override
        public boolean acceptPowder(Building source, Powder powder) {
            return (powders.current() == powder || powders.currentAmount() < 0.2f);
        }

        @Override
        public int charge(Building accessor) {
            return charge;
        }

        @Override
        public int maxCharge() {
            return maxCharge;
        }

        @Override
        public void setCharge(int charge) {
            this.charge = charge;
        }
    }
}
