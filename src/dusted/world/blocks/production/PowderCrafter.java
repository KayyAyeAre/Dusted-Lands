package dusted.world.blocks.production;

import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import dusted.type.*;
import dusted.world.consumers.*;
import dusted.world.interfaces.*;
import dusted.world.meta.DustedStatValues;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;

public class PowderCrafter extends GenericCrafter {
    public Bits powderFilters = new Bits(Vars.content.getBy(ContentType.effect_UNUSED).size);
    public float powderCapacity = 20f;
    public float powderPressure = 1f;
    public @Nullable PowderStack outputPowder;

    public PowderCrafter(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.init();
        consumes.each(cons -> {
            if (cons instanceof ConsumePowderBase pcons) pcons.addPowderFilters(powderFilters);
        });
    }

    @Override
    public void setStats() {
        super.setStats();
        DustedStatValues.customStats(stats, cstats -> {
            cstats.addCStat("powder-capacity", StatValues.number(powderCapacity, StatUnit.none));
        });

        if (outputPowder != null) {
            stats.add(Stat.output, DustedStatValues.powder(outputPowder.powder, outputPowder.amount * (60f / craftTime), true));
        }
    }

    public class PowderCrafterBuild extends GenericCrafterBuild implements PowderBlockc {
        public PowderModule powders = new PowderModule();

        @Override
        public boolean shouldConsume() {
            if (outputPowder != null) return !(powders.get(outputPowder.powder) >= powderCapacity - 0.001f) && enabled;
            return super.shouldConsume();
        }

        @Override
        public boolean outputsPowder() {
            return true;
        }

        @Override
        public void craft() {
            super.craft();
            if (outputPowder != null) {
                handlePowder(outputPowder.powder, outputPowder.amount);
            }
        }

        @Override
        public void dumpOutputs() {
            super.dumpOutputs();
            if (outputPowder != null) {
                dumpPowder(outputPowder.powder);
            }
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
        public float powderCapacity() {
            return powderCapacity;
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
        public Bits filters() {
            return powderFilters;
        }
    }
}
