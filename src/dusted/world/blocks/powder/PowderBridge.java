package dusted.world.blocks.powder;

import arc.*;
import arc.struct.*;
import arc.util.io.*;
import dusted.type.*;
import dusted.world.interfaces.*;
import dusted.world.meta.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;

public class PowderBridge extends ItemBridge implements CustomReplacec {
    public int maxCharge = 16;
    public Bits powderFilters = new Bits(Vars.content.getBy(ContentType.effect_UNUSED).size);
    public float powderCapacity = 20f;

    public PowderBridge(String name) {
        super(name);
        hasItems = false;
    }

    @Override
    public boolean canReplace(Block other) {
        return customReplace(this, other);
    }

    @Override
    public void setStats() {
        super.setStats();

        DustedStatValues.customStats(stats, cstats -> {
            cstats.addCStat("powder-capacity", StatValues.number(powderCapacity, StatUnit.none));
            cstats.addCStat("max-charge", StatValues.number(maxCharge, StatUnit.none));
        });
    }

    @Override
    public void setBars() {
        super.setBars();

        bars.add("powders", build -> {
            PowderBlockc entity = (PowderBlockc) build;
            Powder powder = entity.powderModule().current();
            return new Bar(() -> entity.powderModule().get(powder) <= 0.001f ? Core.bundle.get("bar.powder") : powder.localizedName, () -> powder.color, () -> entity.powderModule().get(powder) / powderCapacity);
        });

        bars.add("charge", build -> {
            Chargedc charged = (Chargedc) build;
            return new Bar(() -> Core.bundle.format("bar.charge"), () -> Pal.accent, () -> ((float) charged.charge(build)) / ((float) charged.maxCharge()));
        });
    }

    @Override
    public String replaceType() {
        return "powder";
    }

    public class PowderBridgeBuild extends ItemBridgeBuild implements PowderBlockc, Chargedc {
        public PowderModule powders = new PowderModule();
        public int charge, properCharge;

        @Override
        public void updateTransport(Building other) {
            if (warmup >= 0.25f) {
                moved |= movePowder(other, powders.current()) > 0.05f;
            }
        }

        @Override
        public void updateTile() {
            super.updateTile();

            //TODO
        }

        @Override
        public void doDump() {
            dumpPowder(powders.current(), 1f);
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            powders.write(write);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            powders.read(read);
        }

        @Override
        public PowderModule powderModule() {
            return powders;
        }

        @Override
        public Building build() {
            return this;
        }

        @Override
        public float powderCapacity() {
            return powderCapacity;
        }

        @Override
        public Bits filters() {
            return powderFilters;
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
