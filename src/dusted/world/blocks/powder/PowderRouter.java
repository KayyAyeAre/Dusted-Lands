package dusted.world.blocks.powder;

import arc.*;
import dusted.type.*;
import dusted.world.blocks.powder.Chute.*;
import dusted.world.interfaces.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;

public class PowderRouter extends PowderBlock {
    public int maxCharge = 16;

    public PowderRouter(String name) {
        super(name);
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
        public int charge, properCharge;

        @Override
        public void updateTile() {
            properCharge = 0;
            proximity.each(build -> {
                if (build instanceof Chargedc entity && canCharge(build, this)) {
                    properCharge = Math.min(maxCharge, Math.max(properCharge, entity.charge(this) - 1));
                }
            });

            charge = properCharge;

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
