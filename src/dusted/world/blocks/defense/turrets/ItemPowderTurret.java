package dusted.world.blocks.defense.turrets;

import arc.util.io.*;
import dusted.world.interfaces.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.world.blocks.defense.turrets.*;

//itemturret that requires powder to operate
public class ItemPowderTurret extends ItemTurret implements PowderBlockc {
    public boolean[] powderFilter = {};
    public float powderCapacity = 10f;

    public ItemPowderTurret(String name) {
        super(name);
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

    public class ItemPowderTurretBuild extends ItemTurretBuild implements PowderBuildc {
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
