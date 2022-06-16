package dusted.world.blocks.production;

import arc.util.*;
import arc.util.io.*;
import dusted.type.*;
import dusted.world.interfaces.*;
import dusted.world.meta.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;

public class PowderCrafter extends GenericCrafter implements PowderBlockc {
    public boolean[] powderFilter = {};
    public float powderCapacity = 20f;
    public @Nullable PowderStack outputPowder;

    public PowderCrafter(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(DustedStats.powderCapacity, powderCapacity, DustedStatUnits.powderUnits);

        if (outputPowder != null) {
            stats.add(Stat.output, DustedStatValues.powder(outputPowder.powder, outputPowder.amount * (60f / craftTime), true));
        }
    }

    @Override
    public void init() {
        powderFilter = new boolean[Vars.content.getBy(ContentType.effect_UNUSED).size];
        super.init();
    }

    @Override
    public boolean[] powderFilters() {
        return powderFilter;
    }

    @Override
    public float powderCapacity() {
        return powderCapacity;
    }

    public class PowderCrafterBuild extends GenericCrafterBuild implements PowderBuildc {
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
        public PowderModule powderModule() {
            return powders;
        }

        @Override
        public Building build() {
            return this;
        }
    }
}
