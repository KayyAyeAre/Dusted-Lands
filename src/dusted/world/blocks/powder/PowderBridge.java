package dusted.world.blocks.powder;

import arc.*;
import arc.struct.*;
import arc.util.io.*;
import dusted.type.*;
import dusted.world.blocks.powder.PowderBlock.*;
import dusted.world.interfaces.*;
import dusted.world.meta.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.meta.*;

public class PowderBridge extends ItemBridge implements CustomReplacec {
    public Bits powderFilters = new Bits(Vars.content.getBy(ContentType.effect_UNUSED).size);
    public float powderCapacity = 10f;
    public float powderPressure = 1f;

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
        });
    }

    @Override
    public void setBars() {
        super.setBars();

        bars.add("powders", build -> {
            PowderBridgeBuild entity = (PowderBridgeBuild) build;
            Powder powder = entity.powders.current();
            return new Bar(() -> entity.powders.get(powder) <= 0.001f ? Core.bundle.get("bar.powder") : powder.localizedName, () -> powder.color, () -> entity.powders.get(powder) / powderCapacity);
        });
    }

    @Override
    public String replaceType() {
        return "powder";
    }

    public class PowderBridgeBuild extends ItemBridgeBuild implements PowderBlockc {
        public PowderModule powders = new PowderModule();

        @Override
        public void updateTransport(Building other) {
            if (warmup >= 0.25f) {
                moved |= movePowder(other, powders.current()) > 0.05f;
            }
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
        public boolean acceptPowder(Building source, Powder powder) {
            return team == source.team && (this.powders.current() == powder || this.powders.currentAmount() < 0.2F) && this.checkAccept(source, Vars.world.tile(this.link));
        }

        @Override
        public boolean outputsPowder() {
            return true;
        }

        @Override
        public PowderModule powderModule() {
            return powders;
        }

        @Override
        public float powderPressure() {
            return powderPressure;
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
    }
}
