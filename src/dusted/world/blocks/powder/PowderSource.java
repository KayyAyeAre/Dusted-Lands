package dusted.world.blocks.powder;

import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import dusted.type.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.world.blocks.*;

import static mindustry.Vars.*;

public class PowderSource extends PowderBlock {
    public PowderSource(String name) {
        super(name);
        update = true;
        solid = true;

        config(Powder.class, (PowderSourceBuild build, Powder powder) -> build.source = powder);
        configClear((PowderSourceBuild build) -> build.source = null);
    }

    public class PowderSourceBuild extends PowderBuild {
        public @Nullable Powder source = null;

        @Override
        public void updateTile() {
            if (source == null) {
                powders.clear();
            } else {
                powders.add(source, powderCapacity);
                dumpPowder(source);
            }
        }

        @Override
        public void buildConfiguration(Table table) {
            ItemSelection.buildTable(table, content.getBy(ContentType.effect_UNUSED), () -> source, this::configure);
        }

        @Override
        public boolean onConfigureTileTapped(Building other) {
            if (this == other) {
                deselect();
                configure(null);
                return false;
            }

            return true;
        }

        @Override
        public Powder config() {
            return source;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.s(source == null ? -1 : source.id);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            int id = revision == 1 ? read.s() : read.b();
            source = id == -1 ? null : Vars.content.getByID(ContentType.effect_UNUSED, id);
        }
    }
}
