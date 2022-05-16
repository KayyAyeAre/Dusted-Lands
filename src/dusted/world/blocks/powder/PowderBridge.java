package dusted.world.blocks.powder;

import arc.struct.*;
import arc.util.io.*;
import dusted.world.interfaces.*;
import dusted.world.meta.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.meta.*;

public class PowderBridge extends ItemBridge implements CustomReplacec {
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
    }
}
