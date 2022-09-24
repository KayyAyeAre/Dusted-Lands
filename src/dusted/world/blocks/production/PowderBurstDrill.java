package dusted.world.blocks.production;

import arc.util.io.*;
import dusted.world.interfaces.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.world.blocks.production.*;

//burstdrill with a powder module
public class PowderBurstDrill extends BurstDrill implements PowderBlockc {
    public boolean[] powderFilter = {};
    public float powderCapacity = 20f;

    public PowderBurstDrill(String name) {
        super(name);
    }

    @Override
    public boolean[] powderFilters() {
        return powderFilter;
    }

    @Override
    public float powderCapacity() {
        return powderCapacity;
    }

    @Override
    public void init() {
        powderFilter = new boolean[Vars.content.getBy(ContentType.effect_UNUSED).size];
        super.init();
    }

    public class PowderBurstDrillBuild extends BurstDrillBuild implements PowderBuildc {
        public PowderModule powders = new PowderModule();

        @Override
        public Building build() {
            return this;
        }

        @Override
        public PowderModule powderModule() {
            return powders;
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
    }
}
