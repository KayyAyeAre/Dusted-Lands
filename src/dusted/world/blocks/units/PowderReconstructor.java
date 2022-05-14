package dusted.world.blocks.units;

import arc.struct.*;
import arc.util.io.*;
import dusted.world.consumers.*;
import dusted.world.interfaces.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.world.blocks.units.*;

public class PowderReconstructor extends Reconstructor {
    public Bits powderFilters = new Bits(Vars.content.getBy(ContentType.effect_UNUSED).size);
    public float powderCapacity = 20f;

    public PowderReconstructor(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.init();
        consumes.each(cons -> {
            if (cons instanceof ConsumePowderBase pcons) pcons.addPowderFilters(powderFilters);
        });
    }

    public class PowderReconstructorBuild extends ReconstructorBuild implements PowderBlockc {
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
        public Bits filters() {
            return powderFilters;
        }

        @Override
        public float powderCapacity() {
            return powderCapacity;
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
