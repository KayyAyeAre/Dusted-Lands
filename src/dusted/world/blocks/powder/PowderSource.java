package dusted.world.blocks.powder;

import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import dusted.type.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.entities.units.*;
import mindustry.world.blocks.*;

public class PowderSource extends PowderBlock {
    public PowderSource(String name) {
        super(name);
        update = true;
        solid = true;
        configurable = true;
        saveConfig = true;

        config(Powder.class, (PowderSourceBuild build, Powder powder) -> build.source = powder);
        configClear((PowderSourceBuild build) -> build.source = null);
    }

    @Override
    public void drawPlanConfig(BuildPlan plan, Eachable<BuildPlan> list) {
        drawPlanConfigCenter(plan, plan.config, "center", true);
    }

    public class PowderSourceBuild extends PowderBuild {
        public @Nullable
        Powder source = null;

        @Override
        public void draw() {
            super.draw();

            if (source == null) {
                Draw.rect("cross", x, y);
            } else {
                Draw.color(source.color);
                Draw.rect("center", x, y);
                Draw.color();
            }
        }

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
            ItemSelection.buildTable(table, Vars.content.getBy(ContentType.effect_UNUSED), () -> source, this::configure);
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
            int id = read.s();
            source = id == -1 ? null : Vars.content.getByID(ContentType.effect_UNUSED, id);
        }
    }
}
